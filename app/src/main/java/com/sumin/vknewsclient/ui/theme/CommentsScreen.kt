package com.sumin.vknewsclient.ui.theme

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sumin.vknewsclient.domain.FeedPost
import com.sumin.vknewsclient.R
import com.sumin.vknewsclient.domain.PostComment
import org.w3c.dom.Comment

@Composable
fun CommentsScreen(
    feedPost: FeedPost,
    comments: List<PostComment>,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Comments for feedPost id : ${feedPost.id}") },
                navigationIcon = {
                    IconButton(onClick = {
                        onBackPressed()
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )

        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            contentPadding = PaddingValues(
                top = 16.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 72.dp
            )
        ) {
            items(comments,key = {it.id}){ comment ->
                CommentItem(postComment = comment)
            }
        }
    }
}

@Composable
fun CommentItem(
    postComment: PostComment
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(
                start = 4.dp,
                end = 4.dp,
                top = 4.dp,
                bottom = 4.dp
            )
    ) {
        Image(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            painter = painterResource(id = R.drawable.post_comunity_thumbnail),
            contentDescription = null,
        )
        Spacer(Modifier.width(8.dp))
        Column() {
            Text(text = postComment.authorName, fontSize = 16.sp)
            Spacer(Modifier.height(3.dp))
            Text(text = postComment.commentText, fontSize = 16.sp)
            Spacer(Modifier.height(3.dp))
            Text(
                text = postComment.publicationDate,
                color = MaterialTheme.colors.onSecondary,
                fontSize = 14.sp
            )

        }
    }
}
