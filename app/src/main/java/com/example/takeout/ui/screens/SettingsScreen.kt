package com.example.takeout.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.takeout.ui.services.AuthenticationService

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onLogOut: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserInfo(modifier = Modifier.fillMaxWidth())
        Button(onClick = {
            AuthenticationService().logOut()
            onLogOut()
        }) {
            Text(text = "Log Out")

        }
    }

}

@Composable
fun UserInfo(
    modifier: Modifier = Modifier
) {
    val authenticationService = AuthenticationService()
    val userName = authenticationService.getUserName()
    val userEmail = authenticationService.getUserEmail()
    val photoUrl = authenticationService.getDisplayPicture()

    Row(
        modifier = modifier
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(64.dp)
                .height(64.dp)
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier
                .padding(start = 14.dp)
        ) {
            Text(
                text = userName,
                fontSize = 16.sp
            )
            Text(
                text = userEmail,
                fontSize = 14.sp
            )
        }
    }
}