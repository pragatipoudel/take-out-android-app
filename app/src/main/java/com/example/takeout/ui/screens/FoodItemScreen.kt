package com.example.takeout.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.takeout.R
import com.example.takeout.ui.Food

@Composable
fun FoodItemScreen(
    food: Food,
    modifier: Modifier = Modifier
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth(),
            backgroundColor = MaterialTheme.colorScheme.errorContainer
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Date: ${food.date}",
                    fontFamily = FontFamily.SansSerif
                )
                Text(
                    text = "Cost: $${food.cost}",
                    fontFamily = FontFamily.SansSerif
                )
                Text(
                    text = "Details: ${food.note}",
                    fontFamily = FontFamily.SansSerif
                )

            }
        }


}