package com.example.lostandfound.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.lostandfound.ui.components.SplashScreen
import com.example.lostandfound.ui.navigation.LandingPage
import com.example.lostandfound.ui.screens.report.GrantPermission
import com.example.lostandfound.ui.theme.LostAndFoundTheme
import kotlinx.coroutines.delay

@Composable
fun App() {
    LostAndFoundTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {

            var showSplash by remember { mutableStateOf(true) }

            LaunchedEffect(true) {
                delay(3_000)
                showSplash = false
            }

            if (showSplash) {
                SplashScreen(); return@Surface
            }

            GrantPermission()
            LandingPage()
        }
    }
}