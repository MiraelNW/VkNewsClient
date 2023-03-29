package com.example.vknewsclient.ui.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.navigation.AppNavGraph
import com.example.vknewsclient.navigation.rememberNavigationState
import com.example.vknewsclient.presentation.comments.CommentsScreen
import com.example.vknewsclient.presentation.main.NavigationItem

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen() {

    val navigationState = rememberNavigationState()

    val commentsToPost: MutableState<FeedPost?> = remember {
        mutableStateOf(null)
    }

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val backStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
                val items =
                    listOf(
                        NavigationItem.Home,
                        NavigationItem.Favourite,
                        NavigationItem.Profile
                    )
                items.forEach() { item ->
                    val selected = backStackEntry?.destination?.hierarchy?.any {
                        it.route == item.screen.route
                    } ?: false
                    BottomNavigationItem(
                        selected = selected,
                        onClick = {
                            if(!selected){
                                navigationState.navigateTo(item.screen.route)
                            }
                        },
                        icon = {
                            Icon(item.icon, contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(id = item.title))
                        },

                        selectedContentColor = MaterialTheme.colors.onPrimary,
                        unselectedContentColor = MaterialTheme.colors.onSecondary
                    )
                }

            }
        }) { paddingValues ->
        AppNavGraph(
            navHostController = navigationState.navHostController,
            newsFeedScreenContent = {
                NewsFeedScreen(paddingValues = paddingValues) { feedPost ->
                    commentsToPost.value = feedPost
                    navigationState.navigateToComments(feedPost)
                }
            },
            commentsScreenContent = {
                CommentsScreen(
                    feedPost = commentsToPost.value!!,
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    })
            },
            favouriteScreenContent = { Text(text = "Favourite") },
            profileScreenContent = { Text(text = "Profile") }
        )
    }


}