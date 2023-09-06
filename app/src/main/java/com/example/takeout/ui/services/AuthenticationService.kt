package com.example.takeout.ui.services

import android.net.Uri
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthenticationService
{
    private val auth = Firebase.auth
    private val currentUser = auth.currentUser

    fun getUserId(): String {
        return currentUser!!.uid
    }

    fun getUserName(): String {
        return currentUser!!.displayName ?: currentUser.uid
    }

    fun getUserEmail(): String {
        return currentUser!!.email ?: ""
    }

    fun getDisplayPicture(): Uri? {
        return currentUser!!.photoUrl
    }

    fun logOut() {
        auth.signOut()
    }
}