package com.example.takeout.ui.model

import androidx.lifecycle.ViewModel
import com.example.takeout.ui.Food
import com.example.takeout.ui.services.FoodStorageService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TakeoutViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(TakeOutUiState())
    val uiState = _uiState.asStateFlow()
    val foodStorageService = FoodStorageService()

    fun setNote(newNote: String) {
        _uiState.value = _uiState.value.copy(note = newNote)
    }

    fun setCost(newCost: String) {
        _uiState.value = _uiState.value.copy(cost = newCost)
    }

    fun setDate(newDate: Long) {
        _uiState.value = _uiState.value.copy(date = newDate)
    }

    fun showDatePicker(show: Boolean) {
        _uiState.value = _uiState.value.copy(showDatePicker = show)
    }

    suspend fun load(foodId: String) {
        val food = foodStorageService.get(foodId)
        if (food == null) {
            _uiState.value = _uiState.value.copy(errorMessage = "Food does not exist.")
        } else {
            _uiState.value = _uiState.value.copy(
                cost = food.cost.toString(),
                date = food.date,
                note = food.note
            )
        }
    }

    suspend fun save(foodId: String?) {
        val food = Food(
            id = foodId ?: "",
            date = _uiState.value.date,
            cost = _uiState.value.cost.toDouble(),
            note = _uiState.value.note
        )
        if (foodId == null) {
            foodStorageService.add(food)
        } else {
            foodStorageService.update(food)
        }
    }
}

data class TakeOutUiState(
    val cost: String = "",
    val note: String = "",
    val date: Long = System.currentTimeMillis(),
    val errorMessage: String = "",
    val showDatePicker: Boolean = false
)