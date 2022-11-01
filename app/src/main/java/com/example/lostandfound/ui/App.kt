package com.example.lostandfound.ui

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.pm.PackageManager
import android.os.Looper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.example.lostandfound.ui.components.GivePermission
import com.example.lostandfound.ui.components.SplashScreen
import com.example.lostandfound.ui.navigation.LandingPage
import com.example.lostandfound.ui.theme.LostAndFoundTheme
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
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

            LandingPage()
        }
    }
}