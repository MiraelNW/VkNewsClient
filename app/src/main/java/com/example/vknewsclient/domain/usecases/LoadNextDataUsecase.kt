package com.example.vknewsclient.domain.usecases

import com.example.vknewsclient.domain.repository.NewsFeedRepository
import javax.inject.Inject

class LoadNextDataUsecase @Inject constructor(private val repository: NewsFeedRepository) {
    suspend operator fun invoke() = repository.loadNextData()
}