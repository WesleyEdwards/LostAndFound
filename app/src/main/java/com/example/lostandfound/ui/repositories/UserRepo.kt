package com.example.lostandfound.ui.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class CreateAccountException(message: String?) : RuntimeException(message)

object UserRepo {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun createUser(email: String, password: String) {
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
        } catch (e: FirebaseAuthException) {
            throw CreateAccountException(e.message)
        }
    }

    suspend fun loginUser(email: String, password: String) {
        try {
            auth.signInWithEmailAndPassword(email, password).await()
        } catch (e: FirebaseAuthException) {
            throw CreateAccountException(e.message)
        }
    }

    fun logoutUser() {
        ReportRepo.clearCache()
        auth.signOut()
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    fun getUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun updateUserDisplayName(name: String) {
        UserProfileChangeRequest.Builder()
            .setDisplayName(name).build()
            .let {
                println("Updating user display name to $name")
                auth.currentUser?.updateProfile(it)
            }
    }
}