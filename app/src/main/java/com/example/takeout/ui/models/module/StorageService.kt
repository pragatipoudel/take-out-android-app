package com.example.takeout.ui.models.module

import com.example.takeout.ui.Food
import kotlinx.coroutines.flow.Flow

interface StorageService {

    val foods: Flow<List<Food>>
    suspend fun getFood(foodId: String): Food?
    suspend fun save(food: Food): String
    suspend fun update(food: Food)
    suspend fun delete(foodId: String)
}