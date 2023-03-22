package com.sumin.vknewsclient.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.sumin.vknewsclient.R

sealed class NavigationItem(
    val icon : ImageVector,
    val title : Int
) {

    object Home : NavigationItem(
        icon = Icons.Filled.Home,
        title = R.string.Main
    )

    object Favourite : NavigationItem(
        icon = Icons.Filled.Favorite,
        title = R.string.Favourite
    )

    object Profile : NavigationItem(
        icon = Icons.Filled.Person,
        title = R.string.Profile
    )
}