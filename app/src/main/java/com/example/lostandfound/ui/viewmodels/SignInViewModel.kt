package com.example.lostandfound.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.lostandfound.ui.repositories.ReportRepo
import com.example.lostandfound.ui.repositories.UserRepo

class SignInState {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var loginSuccess by mutableStateOf(false)
    var signInError by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var loading by mutableStateOf(false)
}

class SignInViewModel(application: Application) : AndroidViewModel(application) {

    val state = SignInState()

    suspend fun signIn() {
        state.signInError = false
        state.errorMessage = ""

        if (state.email.isEmpty() || state.password.isEmpty()) {
            state.signInError = true
            state.errorMessage = "Please fill in all fields"
            return
        }
        state.loading = true

        try {
            UserRepo.loginUser(state.email, state.password)
        } catch (e: Exception) {
            state.errorMessage = e.message ?: "Unknown error"
            println("Error: ${e.message}")
        } finally {
            state.loading = false
            if (UserRepo.isUserLoggedIn()) { state.loginSuccess = true }
        }
    }
}