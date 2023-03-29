package com.example.vknewsclient.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.vknewsclient.R
import com.example.vknewsclient.navigation.Screen

sealed class NavigationItem(
    val screen: Screen,
    val icon : ImageVector,
    val title : Int
) {

    object Home : NavigationItem(
        screen = Screen.Home,
        icon = Icons.Filled.Home,
        title = R.string.Main
    )

    object Favourite : NavigationItem(
        screen = Screen.Favourite,
        icon = Icons.Filled.Favorite,
        title = R.string.Favourite
    )

    object Profile : NavigationItem(
        screen = Screen.Profile,
        icon = Icons.Filled.Person,
        title = R.string.Profile
    )
}