package com.example.vknewsclient.ui.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vknewsclient.presentation.news.NewsFeedViewModel
import com.example.vknewsclient.domain.entity.FeedPost
import com.example.vknewsclient.getApplicationComponent
import com.example.vknewsclient.presentation.news.NewsFeedScreenState

@Composable
fun NewsFeedScreen(
    paddingValues: PaddingValues,
    onCommentClickListener: (FeedPost) -> Unit
) {
    val component = getApplicationComponent()

    val viewModel: NewsFeedViewModel = viewModel(factory = component.getViewModelFactory())

    val screenState = viewModel.screenState.collectAsState(NewsFeedScreenState.Initial)

    NewsFeedScreenContent(
        screenState = screenState,
        viewModel = viewModel,
        paddingValues = paddingValues,
        onCommentClickListener = onCommentClickListener
    )

}

@Composable
fun NewsFeedScreenContent(
    screenState: State<NewsFeedScreenState>,
    viewModel: NewsFeedViewModel,
    paddingValues: PaddingValues,
    onCommentClickListener: (FeedPost) -> Unit

) {
    when (val currentState = screenState.value) {
        is NewsFeedScreenState.FeedPosts -> {
            FeedPosts(
                viewModel = viewModel,
                paddingValues = paddingValues,
                posts = currentState.posts,
                onCommentClickListener = onCommentClickListener,
                nextDataIsLoading = currentState.nextDataIsLoading
            )
        }
        is NewsFeedScreenState.Initial -> {}
        NewsFeedScreenState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = DarkBlue)
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
private fun FeedPosts(
    viewModel: NewsFeedViewModel,
    paddingValues: PaddingValues,
    posts: List<FeedPost>,
    onCommentClickListener: (FeedPost) -> Unit,
    nextDataIsLoading: Boolean
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(
            top = 8.dp,
            bottom = 16.dp
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
                    onCommentClickListener = {
                        onCommentClickListener(feedPost)
                    },
                    onLikeClickListener = {
                        viewModel.changeLikeStatus(feedPost)
                    },
                )
            }
        }
        item {
            if (nextDataIsLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = DarkBlue)
                }
            } else {
                SideEffect {
                    viewModel.loadNextRecommendations()
                }
            }
        }
    }
}