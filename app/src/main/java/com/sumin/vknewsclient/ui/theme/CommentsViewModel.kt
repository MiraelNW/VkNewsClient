package com.sumin.vknewsclient.ui.theme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sumin.vknewsclient.domain.FeedPost
import com.sumin.vknewsclient.domain.PostComment

class CommentsViewModel(
    feedPost: FeedPost
) : ViewModel() {

    private val commentsList = mutableListOf<PostComment>().apply {
        repeat(15) {
            add(
                PostComment(it)
            )
        }
    }

    private val initialState = CommentsScreenState.Initial

    private val _screenState = MutableLiveData<CommentsScreenState>(initialState)
    val screenState: LiveData<CommentsScreenState> get() = _screenState

    init {
        loadComments(feedPost)
    }



    fun loadComments(feedPost: FeedPost) {
        _screenState.value = CommentsScreenState.Comments(
            feedPost,
            comments = commentsList
        )
    }
}