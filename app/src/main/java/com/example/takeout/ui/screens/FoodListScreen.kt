package com.example.takeout.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.takeout.ui.Food
import com.example.takeout.ui.data.DataSource

@Composable
fun FoodListScreen(
    modifier: Modifier = Modifier
) {
    val foodList = DataSource.foods
    LazyColumn(modifier = modifier) {
        items(foodList) { food ->
            FoodItemScreen(
                food = food,
                modifier = Modifier.padding(8.dp)
                )

        }
    }


}