package com.example.vknewsclient.presentation.comments

import android.app.Application
import androidx.lifecycle.*
import com.example.vknewsclient.data.repository.NewsFeedRepositoryImpl
import com.example.vknewsclient.domain.entity.FeedPost
import com.example.vknewsclient.domain.usecases.GetCommentsUsecase
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CommentsViewModel @Inject constructor(
    feedPost: FeedPost,
    private val getComments: GetCommentsUsecase
) : ViewModel() {


    val screenState = getComments(feedPost)
        .map {
            CommentsScreenState.Comments(
                feedPost = feedPost,
                comments = it
            )
        }
}