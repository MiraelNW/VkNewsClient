package com.example.vknewsclient.data.mapper

import android.util.Log
import com.example.vknewsclient.data.model.CommentsResponseDto
import com.example.vknewsclient.data.model.NewsFeedResponseDto
import com.example.vknewsclient.domain.entity.FeedPost
import com.example.vknewsclient.domain.entity.PostComment
import com.example.vknewsclient.domain.entity.StatisticItem
import com.example.vknewsclient.domain.entity.StatisticType
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.absoluteValue

class NewsFeedMapper @Inject constructor() {

    fun mapResponseToPosts(responseDto: NewsFeedResponseDto): List<FeedPost> {
        val result = mutableListOf<FeedPost>()
        val posts = responseDto.newsFeedContent.posts
        val groups = responseDto.newsFeedContent.groups
        for (post in posts) {
            Log.d("mapper",post.toString())
            val group = groups.find { it.id == post.communityId.absoluteValue } ?: continue
            val feedPost = FeedPost(
                id = post.id,
                communityId = post.communityId,
                communityName = group.name,
                publicationDate = mapTimestampToDate(post.date),
                communityImageUrl = group.imageUrl,
                contentText = post.text,
                contentImageUrl = post.attachments?.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.photoUrl,
                statistics = listOf(
                    StatisticItem(type = StatisticType.LIKES, post.likes.count),
                    StatisticItem(type = StatisticType.COMMENTS, post.comments.count),
                    StatisticItem(type = StatisticType.VIEWS, post.views.count),
                    StatisticItem(type = StatisticType.SHARES, post.reposts.count),
                ),
                isLiked = post.likes.userLikes > 0
            )
            result.add(feedPost)
        }
        return result
    }

    fun mapCommentResponseToComments(response: CommentsResponseDto): List<PostComment> {
        val result = mutableListOf<PostComment>()
        val comments = response.content.comments
        val authors = response.content.profiles

        for (comment in comments) {
            if (comment.text.isBlank()) continue
            val author = authors.firstOrNull {it.id == comment.authorId } ?: continue
            val postComment = PostComment(
                id = comment.id,
                authorName = "${author.firstName} ${ author.lastName}",
                authorAvatarUrl = author.photoUrl,
                commentText = comment.text,
                publicationDate = mapTimestampToDate(comment.date)
            )
            result.add(postComment)

        }
        return result
    }


    private fun mapTimestampToDate(time: Long): String {
        val date = Date(time * 1000)
        return SimpleDateFormat("d MMMM yyyy, hh:mm", Locale.getDefault()).format(date)
    }
}