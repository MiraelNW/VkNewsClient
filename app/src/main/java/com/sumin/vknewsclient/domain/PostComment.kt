package com.sumin.vknewsclient.domain

import com.sumin.vknewsclient.R

data class PostComment(
    val id :Int,
    val authorName :String = "name surname",
    val authorAvatarId:Int= R.drawable.post_comunity_thumbnail,
    val commentText : String = "My opinion is very important",
    val publicationDate:String="14:00"
)