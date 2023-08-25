package com.example.takeout.ui

import com.google.firebase.firestore.DocumentId

data class Food(
    @DocumentId val id: String = "",
    val date: String = "",
    val cost: Double = 0.0,
    val note: String = ""

)