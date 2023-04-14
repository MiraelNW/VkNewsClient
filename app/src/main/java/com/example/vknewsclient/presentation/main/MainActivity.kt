package com.example.vknewsclient.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vknewsclient.ViewModelFactory
import com.example.vknewsclient.VkNewsClientApplication
import com.example.vknewsclient.getApplicationComponent
import com.example.vknewsclient.ui.theme.LoginScreen
import com.example.vknewsclient.ui.theme.MainScreen
import com.example.vknewsclient.ui.theme.VkNewsClientTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import javax.inject.Inject

//51593814
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val component = getApplicationComponent()
            val viewModel: MainViewModel = viewModel(factory = component.getViewModelFactory())
            val authState = viewModel.authState.collectAsState(AuthState.Initial)

            val launcher = rememberLauncherForActivityResult(
                contract = VK.getVKAuthActivityResultContract()
            ) {
                viewModel.performAuthResult()
            }
            VkNewsClientTheme {

                when (authState.value) {
                    AuthState.Authorized -> {
                        MainScreen()
                    }

                    AuthState.NotAuthorized -> {
                        LoginScreen {
                            launcher.launch(listOf(VKScope.WALL, VKScope.FRIENDS))
                        }
                    }

                    AuthState.Initial -> {

                    }
                }
            }
        }
    }
}


