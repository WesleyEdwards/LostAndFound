package com.example.lostandfound.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.lostandfound.ui.repositories.UserRepo
import com.google.firebase.auth.FirebaseUser

class ProfileState {
    var loading by mutableStateOf(false)
    var logoutSuccess by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var user: FirebaseUser? by mutableStateOf(null)
}

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    val state = ProfileState()

    fun logout() {
        try {
            UserRepo.logoutUser()
        } catch (e: Exception) {
            state.errorMessage = e.message ?: "Unknown error"
        } finally {
            state.loading = false
            state.logoutSuccess = true
        }
    }

    fun getUser() {
        try {
            state.user = UserRepo.getUser()
        } catch (e: Exception) {
            state.errorMessage = e.message ?: "Unknown error"
        } finally {
            state.loading = false
        }
    }
}