package com.example.vknewsclient.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.vknewsclient.domain.FeedPost

class NavigationState(
    val navHostController: NavHostController
) {

    fun navigateTo(route:String){
        navHostController.navigate(route) {
            launchSingleTop = true
            popUpTo(navHostController.graph.findStartDestination().id){
                saveState  = true
            }
            restoreState = true
        }
    }

    fun navigateToComments(feedPost: FeedPost){
        navHostController.navigate(Screen.Comments.getRouteWithArgs(feedPost = feedPost))
    }
}

@Composable
fun rememberNavigationState(
    navHostController : NavHostController = rememberNavController()
):NavigationState{
    return remember {
        NavigationState(navHostController)
    }
}