package com.sumin.vknewsclient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sumin.vknewsclient.domain.FeedPost
import com.sumin.vknewsclient.ui.theme.CommentsViewModel

class CommentsViewModelFactory(
    private val feedPost: FeedPost
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsViewModel(feedPost) as T
    }
}