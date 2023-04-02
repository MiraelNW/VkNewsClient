package com.example.vknewsclient.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.StatisticType
import com.example.vknewsclient.domain.StatisticItem
import com.example.vknewsclient.R

@Composable
fun Post(
    feedPost: FeedPost,
    onLikeClickListener: (StatisticItem) -> Unit,
    onShareClickListener: (StatisticItem) -> Unit,
    onCommentClickListener: (StatisticItem) -> Unit,
    onViewsClickListener: (StatisticItem) -> Unit,
) {
    Column {
        Card(modifier = Modifier) {
            Column() {
                PostHeader(feedPost)
                Text(
                    text = feedPost.contentText,
                    Modifier.padding(start = 4.dp, end = 4.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                AsyncImage(
                    model = feedPost.communityImageUrl,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    contentScale = ContentScale.FillWidth
                )
                UserReaction(
                    feedPost.statistics,
                    onLikeClickListener = onLikeClickListener,
                    onShareClickListener = onShareClickListener,
                    onCommentClickListener = onCommentClickListener,
                    onViewsClickListener = onViewsClickListener,
                    isLiked = feedPost.isLiked
                )
            }
        }
        Spacer(
            modifier = Modifier
                .height(4.dp)
                .fillMaxWidth()
                .background(Color.Gray.copy(alpha = 0.2f))

        )
    }

}

@Composable
fun UserReaction(
    statistics: List<StatisticItem>,
    onLikeClickListener: (StatisticItem) -> Unit,
    onShareClickListener: (StatisticItem) -> Unit,
    onCommentClickListener: (StatisticItem) -> Unit,
    onViewsClickListener: (StatisticItem) -> Unit,
    isLiked: Boolean
) {
    Row(
        modifier = Modifier.padding(4.dp)
    ) {
        Row(
            modifier = Modifier.weight(1f)
        ) {
            val views = statistics.getItemByType(StatisticType.VIEWS)
            TextWithImage(
                imageResIs = R.drawable.ic_views_count,
                str = formatStatisticCount(views.count),
                onItemClickListener = {
                    onViewsClickListener(views)
                }
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.weight(1f),
        ) {
            val shares = statistics.getItemByType(StatisticType.SHARES)
            TextWithImage(
                imageResIs = R.drawable.ic_share,
                str = formatStatisticCount(shares.count),
                onItemClickListener = {
                    onShareClickListener(shares)
                }
            )
            val comments = statistics.getItemByType(StatisticType.COMMENTS)
            TextWithImage(
                imageResIs = R.drawable.ic_comment,
                str = formatStatisticCount(comments.count),
                onItemClickListener = {
                    onCommentClickListener(comments)
                }
            )
            val likes = statistics.getItemByType(StatisticType.LIKES)
            TextWithImage(
                imageResIs = if (isLiked) R.drawable.ic_like_set else R.drawable.ic_like,
                str = formatStatisticCount(likes.count),
                onItemClickListener = {
                    onLikeClickListener(likes)
                },
                tint = if (isLiked) DarkRed else MaterialTheme.colors.onSecondary
            )
        }

    }
}

private fun formatStatisticCount(count: Int): String {
    return if (count > 100_000) {
        String.format("%sK", count / 1000)
    } else if (count > 1000) {
        String.format("%.1fK", count / 1000f)
    } else {
        count.toString()
    }
}

private fun List<StatisticItem>.getItemByType(type: StatisticType): StatisticItem {
    return this.find { (it.type == type) } ?: throw IllegalArgumentException()
}

@Composable
private fun TextWithImage(
    imageResIs: Int,
    str: String,
    onItemClickListener: () -> Unit,
    tint: Color = MaterialTheme.colors.onSecondary
) {
    Row(
        modifier = Modifier.clickable {
            onItemClickListener()
        }
    ) {
        Icon(
            painter = painterResource(id = imageResIs),
            contentDescription = "",
            tint = tint,
            modifier = Modifier
                .size(20.dp)

        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(text = str, fontSize = 16.sp)
    }
}

@Composable
private fun PostHeader(feedPost: FeedPost) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        AsyncImage(
            model = feedPost.communityImageUrl,
            contentDescription = "",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(4.dp)
        ) {
            Text(text = feedPost.communityName)
            Text(text = feedPost.publicationDate)

        }
        Icon(
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = "",
            tint = MaterialTheme.colors.onSecondary
        )
    }

}