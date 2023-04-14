package com.example.vknewsclient.data.repository

import android.app.Application
import com.example.vknewsclient.data.mapper.NewsFeedMapper
import com.example.vknewsclient.data.network.ApiFactory
import com.example.vknewsclient.data.network.ApiService
import com.example.vknewsclient.domain.entity.FeedPost
import com.example.vknewsclient.domain.entity.StatisticItem
import com.example.vknewsclient.domain.entity.StatisticType
import com.example.vknewsclient.domain.repository.NewsFeedRepository
import com.example.vknewsclient.exstensions.mergeWith
import com.example.vknewsclient.presentation.main.AuthState
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class NewsFeedRepositoryImpl @Inject constructor(
    private val storage : VKPreferencesKeyValueStorage,
    private val apiService : ApiService,
    private val mapper : NewsFeedMapper
) : NewsFeedRepository {

    private val token
        get() = VKAccessToken.restore(storage)

    private val scope = CoroutineScope(Dispatchers.IO)


    private val _feedPosts = mutableListOf<FeedPost>()
    private val feedPosts: List<FeedPost> get() = _feedPosts.toList()

    private var nextFrom: String? = null

    private val nextDataIsNeeded = MutableSharedFlow<Unit>(replay = 1)
    private val refreshList = MutableSharedFlow<List<FeedPost>>()

    private val loadListData = flow {
        nextDataIsNeeded.emit(Unit)
        nextDataIsNeeded.collect {
            val startFrom = nextFrom
            if (startFrom == null && feedPosts.isNotEmpty()) {
                emit(feedPosts)
                return@collect
            }
            val response = if (startFrom == null) {
                apiService.loadRecommendations(getAccessToken())
            } else {
                apiService.loadRecommendations(getAccessToken(), startFrom)
            }
            nextFrom = response.newsFeedContent.startFrom
            val posts = mapper.mapResponseToPosts(response)
            _feedPosts.addAll(posts)
            emit(feedPosts)
        }
    }
        .retry() {
            delay(TIMEOUT_IN_MILLIS)
            true
        }

    private val recommendations = loadListData
        .mergeWith(refreshList)
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = feedPosts
        )

    override suspend fun loadNextData() {
        nextDataIsNeeded.emit(Unit)
    }


    override suspend fun deletePost(feedPost: FeedPost) {
        apiService.ignorePost(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            itemId = feedPost.id
        )
        _feedPosts.remove(feedPost)
        refreshList.emit(feedPosts)
    }

    override suspend fun changeLikeStatus(feedPost: FeedPost) {
        val response = if (feedPost.isLiked) {
            apiService.deleteLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        } else {
            apiService.addLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        }
        val newLikesCount = response.likes.count
        val newStatistics = feedPost.statistics.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKES }
            add(StatisticItem(type = StatisticType.LIKES, newLikesCount))
        }
        val newPost = feedPost.copy(statistics = newStatistics, isLiked = !feedPost.isLiked)
        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = newPost
        refreshList.emit(feedPosts)
    }

    private val checkAuthStateEvents = MutableSharedFlow<Unit>(replay = 1)

    private val authStateFlow = flow {
        checkAuthStateEvents.emit(Unit)
        checkAuthStateEvents.collect {
            val currToken = token
            val loggedIn = currToken != null && currToken.isValid
            val authState = if (loggedIn) AuthState.Authorized else AuthState.NotAuthorized
            emit(authState)
        }
    }
        .stateIn(
            scope = scope,
            SharingStarted.Lazily,
            AuthState.Initial
        )

    override suspend fun checkAuthState() {
        checkAuthStateEvents.emit(Unit)
    }

    override fun getComments(feedPost: FeedPost) = flow {
        val response = apiService.getComments(getAccessToken(), feedPost.communityId, feedPost.id)

        emit(mapper.mapCommentResponseToComments(response))
    }
        .retry {
            delay(TIMEOUT_IN_MILLIS)
            true
        }
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = listOf()
        )

    override fun getAuthStateFlow(): StateFlow<AuthState> = authStateFlow
    override fun getRecommendations(): StateFlow<List<FeedPost>> = recommendations

    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("token is null")
    }

    companion object {
        private const val TIMEOUT_IN_MILLIS = 3000L
    }

}