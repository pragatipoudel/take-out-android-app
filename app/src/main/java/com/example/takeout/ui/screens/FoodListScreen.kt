package com.example.takeout.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.takeout.ui.Food
import com.example.takeout.ui.Screen
import com.example.takeout.ui.services.FoodStorageService
import java.time.Instant
import java.time.ZoneOffset

@Composable
fun FoodListScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val foodStorageService = FoodStorageService()
    val foodList = foodStorageService.getAll().collectAsStateWithLifecycle(listOf()).value

    LazyColumn(modifier = modifier) {
        items(foodList) { food ->
            FoodItem(
                food = food,
                navController = navController,
            )
        }
    }
}



@Composable
fun FoodItem(
    food: Food,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val date = Instant.ofEpochMilli(food.date).atZone(ZoneOffset.UTC).toLocalDate()
    val dateString = date.format(dateFormatter)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(Screen.EditFoodItem.name + "/" + food.id)
            },
//        backgroundColor = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = food.note,
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 16.sp
                    )
                    Text(
                        text = dateString,
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 12.sp
                    )

                }

                Column(
                    modifier = modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "$" + food.cost,
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 20.sp
                    )
                }

            }
        }

        Divider(modifier = Modifier.padding(horizontal = 8.dp))
    }


}