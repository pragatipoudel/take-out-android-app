package com.example.takeout.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.takeout.R
import com.example.takeout.ui.Food
import com.example.takeout.ui.Screen
import com.example.takeout.ui.services.FoodStorageService
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier) {

    val circleBackgroundColor = MaterialTheme.colorScheme.secondaryContainer
    val currentDate = LocalDate.now()
    val currentMonth = currentDate.month
    val currentYear = currentDate.year

    val foodStorageService = FoodStorageService()
    val foodList = foodStorageService.getByMonth(currentYear, currentMonth).collectAsState(listOf()).value
    val total = getTotal(foodList)



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
                    text = "$" + String.format("%.2f", total),
                    fontSize = 32.sp

                )
                Text(
                    text = currentMonth.toString(),
                    fontSize = 22.sp,
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_small))
                        .alpha(0.5f)
                )
            }
        }

        Button(
            onClick = {
            navController.navigate(Screen.AddFoodItem.name)
        },
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_medium))
        ) {
            Text(text = stringResource(id = R.string.add_new_entry))
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(onClick = {
            navController.navigate(Screen.FoodList.name)
        }) {
            Text(text = stringResource(id = R.string.food_list))
            
        }
    }
}

fun getTotal(
    foodList: List<Food>
): Double {
    return foodList.map { it.cost }.sum()
}