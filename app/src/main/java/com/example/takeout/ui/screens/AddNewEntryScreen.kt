package com.example.takeout.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collect
import java.text.DateFormat


@ExperimentalMaterial3Api
@Composable
fun AddNewEntryScreen(
    modifier: Modifier = Modifier
) {

    var cost by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var date by remember { mutableLongStateOf(System.currentTimeMillis()) }
    var showDatePicker by remember {
        mutableStateOf(false)
    }

    val dateString = DateFormat.getDateInstance().format(date)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

//        OutlinedButton(onClick = { showDatePicker = true }) {
//            Text(dateString)
//        }
        OutlinedTextField(
            value = dateString,
            onValueChange = {},
            label = { Text(text = "Date")},
//            modifier = Modifier.clickable { showDatePicker = true },
            interactionSource = remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect() {
                            if (it is PressInteraction.Release) {
                                showDatePicker = true
                            }
                        }
                    }
                }
        )

        OutlinedTextField(
            value = cost,
            onValueChange = { cost = it },
            label = { Text("Cost ($)") }
        )

        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("Note") }
        )



        Button(
            modifier = Modifier.padding(8.dp),

            onClick = { /*TODO*/ }) {
            Text(text = "Save")

        }
    }



    if (showDatePicker) {
        MyDatePickerDialog(
            value = date,
            onDateSelected = { date = it },
            onDismiss = { showDatePicker = false }
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


