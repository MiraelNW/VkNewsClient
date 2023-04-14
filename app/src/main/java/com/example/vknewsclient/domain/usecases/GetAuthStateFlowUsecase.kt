package com.example.vknewsclient.domain.usecases

import com.example.vknewsclient.domain.repository.NewsFeedRepository
import javax.inject.Inject

class GetAuthStateFlowUsecase @Inject constructor(private val repository: NewsFeedRepository) {
    operator fun invoke() = repository.getAuthStateFlow()
}