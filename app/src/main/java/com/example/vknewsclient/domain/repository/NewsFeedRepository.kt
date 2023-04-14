package com.example.vknewsclient.domain.repository

import com.example.vknewsclient.domain.entity.FeedPost
import com.example.vknewsclient.domain.entity.PostComment
import com.example.vknewsclient.presentation.main.AuthState
import kotlinx.coroutines.flow.*

interface NewsFeedRepository {

    fun getComments(feedPost: FeedPost): StateFlow<List<PostComment>>

    fun getAuthStateFlow(): StateFlow<AuthState>

    fun getRecommendations(): StateFlow<List<FeedPost>>

    suspend fun loadNextData()

    suspend fun checkAuthState()

    suspend fun deletePost(feedPost: FeedPost)

    suspend fun changeLikeStatus(feedPost: FeedPost)




}