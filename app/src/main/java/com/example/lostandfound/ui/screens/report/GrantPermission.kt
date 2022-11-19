package com.example.lostandfound.ui.screens.report

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import com.example.lostandfound.ui.components.GivePermission

@Composable
fun GrantPermission() {
    val permissionGranted = remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) permissionGranted.value = true
    }
    LaunchedEffect(true) {
        launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
    if (!permissionGranted.value) {
        GivePermission(); return
    }
}