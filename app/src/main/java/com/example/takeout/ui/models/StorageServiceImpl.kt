package com.example.takeout.ui.models

import com.example.takeout.ui.Food
import com.example.takeout.ui.models.module.AccountService
import com.example.takeout.ui.models.module.StorageService
import com.google.firebase.firestore.FirebaseFirestore

import com.google.firebase.firestore.ktx.dataObjects
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

//class StorageServiceImpl
//@Inject
//constructor(private val firestore: FirebaseFirestore, private val auth:AccountService) :
//        StorageService {
//        override val foods: Flow<List<Food>>
//                get() =
//                        auth.currentUser.flatMapLatest { user ->
//                                firestore.collection(FOOD_COLLECTION).whereEqualTo(USER_ID_FIELD, user.id).dataObjects()
//                        }
//
////        override suspend fun getFood(foodId: String): Food? {
////                firestore.collection(FOOD_COLLECTION).document(foodId).get().await().toObject()
////        }
//
//
//
//        companion object {
//                private const val USER_ID_FIELD = "userId"
//                private const val FOOD_COLLECTION = "foods"
//                private const val SAVE_FOOD_TRACE = "saveFood"
//                private const val UPDATE_FOOD_TRACE = "updateFood"
//        }
//}