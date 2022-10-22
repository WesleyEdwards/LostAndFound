package com.example.lostandfound.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.lostandfound.ui.repositories.UserRepo

class CreateAccountScreenState {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var name by mutableStateOf("")
    var loading by mutableStateOf(false)
    var loginSuccess by mutableStateOf(false)
    var emailError by mutableStateOf(false)
    var passwordError by mutableStateOf(false)
    var confirmPasswordError by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var passwordVisible by mutableStateOf(false)
    var confirmVisible by mutableStateOf(false)
}

class CreateAccountViewModel(application: Application) : AndroidViewModel(application) {
    var state = CreateAccountScreenState()

    suspend fun createAccount() {
        state.loading = true
        state.emailError = false
        state.confirmPasswordError = false
        state.passwordError = false
        state.passwordVisible = false
        state.confirmVisible = false
        if (state.email.isEmpty() || state.password.isEmpty() ||
            state.confirmPassword.isEmpty() || state.name.isEmpty()
        ) {
            state.errorMessage = "Please fill out all fields"
            state.loading = false
            return
        }
        if (!state.email.contains("@")) {
            state.errorMessage = "Invalid email"
            state.emailError = true
            state.loading = false
            return
        }
        if (state.password != state.confirmPassword) {
            state.errorMessage = "Passwords do not match"
            state.confirmPasswordError = true
            state.loading = false
            return
        }
        if (state.password.length < 6) {
            state.errorMessage = "Password must be at least 6 characters"
            state.loading = false
            state.passwordError = true
            return
        }
        try {
            UserRepo.createUser(state.email, state.password)
        } catch (e: Exception) {
            state.errorMessage = e.message ?: "Unknown error"
        } finally {
            setUserDisplayName(state.name)
            state.loading = false
            if (UserRepo.isUserLoggedIn()) {
                state.loginSuccess = true
            }
        }
    }

    private suspend fun setUserDisplayName(displayName: String) {
        try {
            UserRepo.updateUserDisplayName(displayName)
        } catch (e: Exception) {
            state.errorMessage = e.message ?: "Unknown error"
        } finally {
            state.loading = false
        }
    }
}