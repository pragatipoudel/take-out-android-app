package com.example.takeout.ui.services

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthenticationService
{
    fun getUserId(): String {
        return Firebase.auth.currentUser!!.uid
    }
}