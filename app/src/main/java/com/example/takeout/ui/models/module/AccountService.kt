package com.example.takeout.ui.models.module

import kotlinx.coroutines.flow.Flow
import com.example.takeout.ui.User

interface AccountService {
    val currentUserId: String
    val hasUser: Boolean
    val currentUser: Flow<User>

    suspend fun authenticate(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)
    suspend fun deleteAccount()
    suspend fun signOut()
}