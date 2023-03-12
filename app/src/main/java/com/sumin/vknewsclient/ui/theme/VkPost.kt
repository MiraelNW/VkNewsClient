package com.sumin.vknewsclient.ui.theme

import android.widget.Space
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sumin.vknewsclient.R

@Composable
fun Post() {
    Card(modifier = Modifier.padding(4.dp)) {
        Column() {
            PostHeader()
            Text(
                text = "Something went wrong or not i don't know it is text for example",
                Modifier.padding(start = 4.dp, end = 4.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = painterResource(id = R.drawable.post_content_image),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
            UserReaction()
        }
    }
}

@Composable
fun UserReaction() {
    Row(
        modifier = Modifier.padding(start = 4.dp, end = 4.dp)
    ) {
        Row(
            modifier = Modifier.weight(1f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_views_count),
                contentDescription = ""
            )
            Text(text = "960")
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.weight(1f),
        ) {
            TextWithImage(imageResIs = R.drawable.ic_share, str = "7")
            TextWithImage(imageResIs = R.drawable.ic_comment, str = "8")
            TextWithImage(imageResIs = R.drawable.ic_like, str = "23")
        }

    }
}

@Composable
private fun TextWithImage(imageResIs: Int, str: String) {
    Row {
        Image(
            painter = painterResource(id = imageResIs),
            contentDescription = "",
            modifier = Modifier
                .size(20.dp)
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(text = str, fontSize = 16.sp)
    }

}

@Composable
private fun PostHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.post_comunity_thumbnail),
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
            Text(text = "Уволено")
            Text(text = "14:00")

        }
        Icon(
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = "",
            tint = MaterialTheme.colors.onSecondary
            )
    }

}

@Composable
@Preview
private fun DarkThemePreview() {
    VkNewsClientTheme(darkTheme = true) {
        Post()
    }

}

@Composable
@Preview
private fun LightThemePreview() {
    VkNewsClientTheme(darkTheme = false) {
        Post()
    }
}