package com.example.takeout.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.takeout.ui.services.FoodStorageService
import java.time.LocalDate
import java.time.Month

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthViewScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val currentDate = LocalDate.now()
    val currentMonth = currentDate.month
    val currentYear = currentDate.year

    val optionsMonth = Month.values()
    val optionsYear = (1970..currentYear).reversed()
    var selectedOptionTextMonth by remember { mutableStateOf(currentMonth) }
    var selectedOptionTextYear by remember { mutableStateOf(currentYear) }
    var expandedMonth by remember { mutableStateOf(false) }
    var expandedYear by remember { mutableStateOf(false) }

    val year = selectedOptionTextYear
    val month = selectedOptionTextMonth
    val foodStorageService = FoodStorageService()

    val foodList = foodStorageService.getByMonth(year, month).collectAsStateWithLifecycle(listOf()).value

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ExposedDropdownMenuBox(
                expanded = expandedYear,
                onExpandedChange = {
                    expandedYear = !expandedYear
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 4.dp)
            ) {
                TextField(
                    readOnly = true,
                    value = selectedOptionTextYear.toString(),
                    onValueChange = {},
                    modifier = Modifier.menuAnchor(),
                    label = { Text(text = "Year") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedYear)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                )
                ExposedDropdownMenu(
                    expanded = expandedYear,
                    onDismissRequest = {
                        expandedYear = false
                    }
                ) {
                    optionsYear.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Text(text = option.toString())

                            },
                            onClick = {
                                selectedOptionTextYear = option
                                expandedYear = false
                            }
                        )

                    }
                }

            }
            ExposedDropdownMenuBox(
                expanded = expandedMonth,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp, top = 8.dp, bottom = 8.dp, end = 8.dp),
                onExpandedChange = {
                    expandedMonth = !expandedMonth
                }) {
                TextField(
                    readOnly = true,
                    value = selectedOptionTextMonth.toString(),
                    onValueChange = {},
                    modifier = Modifier.menuAnchor(),
                    label = { Text(text = "Month") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedMonth)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = expandedMonth,
                    onDismissRequest = {
                        expandedMonth = false
                    }
                ) {
                    optionsMonth.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Text(text = option.toString())

                            },
                            onClick = {
                                selectedOptionTextMonth = option
                                expandedMonth = false
                            }
                        )

                    }
                }

            }
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            if (foodList.isEmpty()) {
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxSize(),
                        textAlign = TextAlign.Center,
                        text = "No takeouts for this month."
                    )
                }
            }
            items(foodList) { food ->
                FoodItem(food = food, navController = navController)
            }
        }
    }


}