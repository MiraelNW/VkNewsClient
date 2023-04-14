package com.example.vknewsclient.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vknewsclient.data.repository.NewsFeedRepositoryImpl
import com.example.vknewsclient.domain.usecases.CheckAuthState
import com.example.vknewsclient.domain.usecases.GetAuthStateFlowUsecase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getAuthStateFlowUsecase: GetAuthStateFlowUsecase,
    private val checkAuthState: CheckAuthState,
) : ViewModel() {


    val authState = getAuthStateFlowUsecase()

    fun performAuthResult() {
        viewModelScope.launch {
            checkAuthState()
        }

    }


}