package com.example.takeout.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Colors
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.takeout.R
import com.example.takeout.ui.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier) {

    val circleBackgroundColor = MaterialTheme.colorScheme.secondaryContainer

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier
            .drawBehind {
                drawCircle(
                    color = circleBackgroundColor,
                )
            }) {

            Column(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_xlarge)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center

            ) {
                Text(
                    text = "$1000",
                    fontSize = 32.sp

                )
                Text(
                    text = "June",
                    fontSize = 22.sp,
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_small))
                        .alpha(0.5f)
                )
            }
        }

        Button(
            onClick = {
            navController.navigate(Screen.AddNewEntry.name)
        },
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_medium))
        ) {
            Text(text = "Add New Entry")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {
            navController.navigate(Screen.FoodList.name)
        }) {
            Text(text = "View All Entries")
            
        }
    }
}