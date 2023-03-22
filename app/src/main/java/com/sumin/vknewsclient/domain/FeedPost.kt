package com.sumin.vknewsclient.domain

import com.sumin.vknewsclient.R

data class FeedPost(
    val id:Int = 0,
    val communityName: String = "Уволено",
    val publicationDate: String = "14:00",
    val avatarResId: Int = R.drawable.post_comunity_thumbnail,
    val contentText : String ="Something went wrong or not i don't know it is text for example",
    val contentImageResId:Int=R.drawable.post_content_image,
    val statistics : List<StatisticItem> = listOf(
        StatisticItem(type = StatisticType.VIEWS, count = 966),
        StatisticItem(type = StatisticType.SHARES, count = 7),
        StatisticItem(type = StatisticType.COMMENTS, count = 8),
        StatisticItem(type = StatisticType.LIKES, count = 27),
    )
)