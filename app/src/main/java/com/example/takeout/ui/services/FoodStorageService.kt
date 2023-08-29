package com.example.takeout.ui.services

import com.example.takeout.ui.Food
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.dataObjects
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.LocalTime
import java.time.Month
import java.time.Year
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters

class FoodStorageService {

    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val auth = AuthenticationService()

    suspend fun add(food: Food) {
        val userId = auth.getUserId()
        val foods = db.collection(USER_COLLECTION)
            .document(userId)
            .collection(FOOD_COLLECTION)

        foods.add(food).await()
    }

    suspend fun update(food: Food) {
        val userId = auth.getUserId()
        db.collection(USER_COLLECTION)
            .document(userId)
            .collection(FOOD_COLLECTION)
            .document(food.id)
            .set(food)
            .await()
    }

    fun getAll(): Flow<List<Food>> {
        val userId = auth.getUserId()
        return db.collection(USER_COLLECTION)
            .document(userId)
            .collection(FOOD_COLLECTION)
            .orderBy(DATE_FIELD, Query.Direction.DESCENDING)
            .dataObjects()
    }

    fun getByMonth(year: Int, month: Month): Flow<List<Food>> {
        val startDate = getStartDate(year, month)
        val endDate = getEndDate(year, month)

        val userId = auth.getUserId()
        return db.collection(USER_COLLECTION)
            .document(userId)
            .collection(FOOD_COLLECTION)
            .orderBy(DATE_FIELD, Query.Direction.DESCENDING)
            .whereGreaterThanOrEqualTo(DATE_FIELD, startDate)
            .whereLessThanOrEqualTo(DATE_FIELD, endDate)
            .dataObjects()
    }

    suspend fun get(foodId: String): Food? {
        val userId = auth.getUserId()
        return db.collection(USER_COLLECTION)
            .document(userId)
            .collection(FOOD_COLLECTION)
            .document(foodId)
            .get().await().toObject()
    }

    suspend fun delete(foodId: String) {
        val userId = auth.getUserId()
        db.collection(USER_COLLECTION)
            .document(userId)
            .collection(FOOD_COLLECTION)
            .document(foodId)
            .delete()
            .await()
    }

    companion object {
        private const val FOOD_COLLECTION = "foods"
        private const val USER_COLLECTION = "users"
        private const val DATE_FIELD = "date"

        fun getStartDate (
            currentYear: Int,
            currentMonth: Month): Long {
            return LocalDate.of(currentYear, currentMonth, 1)
                .atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        }


        fun getEndDate (
            currentYear: Int,
            currentMonth: Month): Long {
            return LocalDate.of(currentYear, currentMonth, 1)
                .with(TemporalAdjusters.lastDayOfMonth())
                .atTime(LocalTime.MAX).atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli()
        }
    }
}