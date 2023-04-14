package com.example.vknewsclient.domain.usecases

import com.example.vknewsclient.domain.entity.FeedPost
import com.example.vknewsclient.domain.repository.NewsFeedRepository
import javax.inject.Inject

class DeletePostUsecase @Inject constructor(private val repository: NewsFeedRepository) {
    suspend operator fun invoke(feedPost: FeedPost) = repository.deletePost(feedPost)
}