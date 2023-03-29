package com.example.vknewsclient.domain

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import com.example.vknewsclient.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedPost(
    val id: Int = 0,
    val communityName: String = "Уволено",
    val publicationDate: String = "14:00",
    val avatarResId: Int = R.drawable.post_comunity_thumbnail,
    val contentText: String = "Something went wrong or not i don't know it is text for example",
    val contentImageResId: Int = R.drawable.post_content_image,
    val statistics: List<StatisticItem> = listOf(
        StatisticItem(type = StatisticType.VIEWS, count = 966),
        StatisticItem(type = StatisticType.SHARES, count = 7),
        StatisticItem(type = StatisticType.COMMENTS, count = 8),
        StatisticItem(type = StatisticType.LIKES, count = 27),
    )
) : Parcelable {

    companion object {

        val NavigationType: NavType<FeedPost> = object : NavType<FeedPost>(false) {

            override fun get(bundle: Bundle, key: String): FeedPost? {
                return bundle.getParcelable(key)
            }

            override fun parseValue(value: String): FeedPost {
                return Gson().fromJson(value, FeedPost::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: FeedPost) {
                bundle.putParcelable(key, value)
            }
        }
    }

}