package com.example.vknewsclient.domain

import com.example.vknewsclient.R

data class PostComment(
    val id :Int,
    val authorName :String = "name surname",
    val authorAvatarId:Int= R.drawable.post_comunity_thumbnail,
    val commentText : String = "My opinion is very important",
    val publicationDate:String="14:00"
)