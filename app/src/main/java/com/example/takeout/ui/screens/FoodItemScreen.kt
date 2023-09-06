package com.example.takeout.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.takeout.ui.Food
import com.example.takeout.ui.model.TakeoutViewModel
import com.example.takeout.ui.services.FoodStorageService
import kotlinx.coroutines.launch
import java.text.DateFormat


@ExperimentalMaterial3Api
@Composable
fun FoodItemScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    foodId: String? = null,
    takeoutViewModel: TakeoutViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()
    val takeOutUiState by takeoutViewModel.uiState.collectAsStateWithLifecycle()

    val foodStorageService = FoodStorageService()
    if (foodId != null) {
        LaunchedEffect(foodId) {
            takeoutViewModel.load(foodId)
        }
    }

    if (takeOutUiState.errorMessage != "") {
        Text(takeOutUiState.errorMessage)
        return
    }

    val dateString = DateFormat.getDateInstance().format(takeOutUiState.date)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = takeOutUiState.note,
            onValueChange = { takeoutViewModel.setNote(it) },
            label = { Text("Note") }
        )

        OutlinedTextField(
            value = takeOutUiState.cost,
            onValueChange = { takeoutViewModel.setCost(it) },
            label = { Text("Cost ($)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        OutlinedTextField(
            value = dateString,
            onValueChange = {},
            label = { Text(text = "Date")},
            interactionSource = remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect() {
                            if (it is PressInteraction.Release) {
                                takeoutViewModel.showDatePicker(true)
                            }
                        }
                    }
                }
        )

        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            if (foodId != null) {
                Button(
                    onClick = {
                        scope.launch {
                            foodStorageService.delete(foodId)
                            navController.popBackStack()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(text = "Delete")

                }
            }
            Button(
                onClick = {
                    scope.launch {
                        takeoutViewModel.save(foodId)
                        navController.popBackStack()
                    }
                }) {
                if (foodId == null) {
                    Text(text = "Add")
                } else {
                    Text(text = "Update")
                }

            }

        }
    }



    if (takeOutUiState.showDatePicker) {
        MyDatePickerDialog(
            value = takeOutUiState.date,
            onDateSelected = { takeoutViewModel.setDate(it) },
            onDismiss = { takeoutViewModel.showDatePicker(false) }
        )
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    value: Long,
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = value)

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = { 
            Button(onClick = {
                if (datePickerState.selectedDateMillis != null) {
                    onDateSelected(datePickerState.selectedDateMillis!!)
                }
                onDismiss()
            }) {
                Text(text = "Ok")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}


