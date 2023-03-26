package com.sumin.vknewsclient.ui.theme

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sumin.vknewsclient.NewsFeedViewModel
import com.sumin.vknewsclient.domain.FeedPost


@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    onCommentClickListener: (FeedPost) -> Unit
) {

    val viewModel: NewsFeedViewModel = viewModel()

    val screenState = viewModel.screenState.observeAsState(NewsFeedScreenState.Initial)

    when (val currentState = screenState.value) {
        is NewsFeedScreenState.FeedPosts -> {
            FeedPosts(
                viewModel = viewModel,
                paddingValues = paddingValues,
                posts = currentState.posts,
                onCommentClickListener = onCommentClickListener
            )
        }
        NewsFeedScreenState.Initial -> {}
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
private fun FeedPosts(
    viewModel: NewsFeedViewModel,
    paddingValues: PaddingValues,
    posts: List<FeedPost>,
    onCommentClickListener: (FeedPost) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(
            top = 8.dp,
            bottom = 72.dp
        )
    ) {
        items(items = posts, key = { it.id }) { feedPost ->
            val dismissState = rememberDismissState()
            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                viewModel.remove(feedPost)
            }
            SwipeToDismiss(
                modifier = Modifier.animateItemPlacement(),
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart),
                background = {
                    Box(Modifier.background(Color.White)) {

                    }
                }) {
                Post(
                    feedPost,
                    onViewsClickListener = {
                        viewModel.updateCount(feedPost, it)
                    },
                    onShareClickListener = {
                        viewModel.updateCount(feedPost, it)
                    },
                    onCommentClickListener = {
                        onCommentClickListener(feedPost)
                    },
                    onLikeClickListener = {
                        viewModel.updateCount(feedPost, it)
                    },
                )
            }
        }
    }

}