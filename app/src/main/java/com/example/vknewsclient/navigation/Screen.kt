package com.example.vknewsclient.navigation

import android.net.Uri
import com.google.gson.Gson
import com.example.vknewsclient.domain.entity.FeedPost

sealed class Screen(
    val route: String
) {
    object NewsFeed : Screen(ROUTE_NEWS_FEED)

    object Favourite : Screen(ROUTE_FAVOURITE)

    object Profile : Screen(ROUTE_PROFILE)

    object Home : Screen(ROUTE_HOME)

    object Comments : Screen(ROUTE_COMMENTS) {

        private const val ROUTE_FOR_ARGS = "comments"
        fun getRouteWithArgs(feedPost: FeedPost): String {
            val feedPostToJson = Gson().toJson(feedPost)
            return "$ROUTE_FOR_ARGS/${feedPostToJson.encode()}"
        }
    }

    companion object {
        const val KEY_FOR_ARGUMENTS = "feed_post"
        private const val ROUTE_COMMENTS = "comments/{$KEY_FOR_ARGUMENTS}"
        private const val ROUTE_HOME = "home"
        private const val ROUTE_NEWS_FEED = "news_feed"
        private const val ROUTE_FAVOURITE = "favourite"
        private const val ROUTE_PROFILE = "profile"
    }

    fun String.encode(): String {
        return Uri.encode(this)
    }

}