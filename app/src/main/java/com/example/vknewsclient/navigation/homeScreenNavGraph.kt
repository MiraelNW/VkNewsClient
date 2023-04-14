package com.example.vknewsclient.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.vknewsclient.domain.entity.FeedPost


fun NavGraphBuilder.homeScreenNavGraph(
    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (FeedPost) -> Unit
) {
    navigation(
        startDestination = Screen.NewsFeed.route,
        route = Screen.Home.route
    ) {
        composable(Screen.NewsFeed.route) {
            newsFeedScreenContent()
        }
        composable(
            route = Screen.Comments.route,
            arguments = listOf(
                navArgument(name = Screen.KEY_FOR_ARGUMENTS) {
                    type = FeedPost.NavigationType
                },
            )
        ) {
            val feedPost = it.arguments?.getParcelable<FeedPost>(Screen.KEY_FOR_ARGUMENTS)
                ?: throw RuntimeException("Args is null")
            commentsScreenContent(feedPost)
        }
    }


}