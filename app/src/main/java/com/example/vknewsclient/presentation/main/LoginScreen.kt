package com.example.vknewsclient.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.vknewsclient.R
import com.example.vknewsclient.ui.theme.DarkBlue

@Composable
fun LoginScreen(
    onLoginListener: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.vk_logo),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(100.dp))
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = DarkBlue
                ),
                onClick = { onLoginListener() }
            ) {
                Text(text = "Log in")
            }

        }
    }
}