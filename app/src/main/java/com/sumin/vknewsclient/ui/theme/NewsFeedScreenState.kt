package com.sumin.vknewsclient.ui.theme

import com.sumin.vknewsclient.domain.FeedPost
import com.sumin.vknewsclient.domain.PostComment

sealed class NewsFeedScreenState {

    object Initial : NewsFeedScreenState()

    data class FeedPosts(val posts: List<FeedPost>) : NewsFeedScreenState()
}
