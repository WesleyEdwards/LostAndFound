package com.example.lostandfound.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.lostandfound.ui.navigation.GraphRoutes
import com.example.lostandfound.ui.repositories.UserRepo

class LandingPageViewModel(application: Application) : AndroidViewModel(application) {
    fun getAuthRoute(): String {
        return if (UserRepo.isUserLoggedIn()) {
            GraphRoutes.Auth
        } else {
            return GraphRoutes.Unauth
        }
    }
}