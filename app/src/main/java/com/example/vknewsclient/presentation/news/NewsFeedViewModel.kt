package com.example.vknewsclient.presentation.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.vknewsclient.data.repository.NewsFeedRepositoryImpl
import com.example.vknewsclient.domain.entity.FeedPost
import com.example.vknewsclient.domain.usecases.ChangeLikeStatusUsecase
import com.example.vknewsclient.domain.usecases.DeletePostUsecase
import com.example.vknewsclient.domain.usecases.GetRecommendationsUsecase
import com.example.vknewsclient.domain.usecases.LoadNextDataUsecase
import com.example.vknewsclient.exstensions.mergeWith
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsFeedViewModel @Inject constructor(
    private val getRecommendations: GetRecommendationsUsecase,
    private val loadNextDataUsecase: LoadNextDataUsecase,
    private val changeLikeStatusUsecase: ChangeLikeStatusUsecase,
    private val deletePost: DeletePostUsecase,
    ) : ViewModel() {

    private val recommendationsFlow = getRecommendations()

    private val loadNextData = MutableSharedFlow<NewsFeedScreenState>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("NewsFeedViewModel", throwable.message.toString())
    }

    val screenState = recommendationsFlow
        .filter { it.isNotEmpty() }
        .map { NewsFeedScreenState.FeedPosts(posts = it) as NewsFeedScreenState }
        .onStart { emit(NewsFeedScreenState.Loading) }
        .mergeWith(loadNextData)


    fun loadNextRecommendations() {
        viewModelScope.launch(exceptionHandler) {
            loadNextData.emit(
                NewsFeedScreenState.FeedPosts(
                    posts = recommendationsFlow.value,
                    nextDataIsLoading = true
                )
            )
            loadNextDataUsecase
        }
    }

    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            changeLikeStatusUsecase(feedPost)
        }
    }

    fun remove(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            deletePost(feedPost)
        }
    }
}