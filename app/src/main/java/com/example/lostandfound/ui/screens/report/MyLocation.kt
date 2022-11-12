package com.example.lostandfound.ui.screens.report

import android.Manifest
import android.content.pm.PackageManager
import android.os.Looper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.example.lostandfound.ui.components.GivePermission
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

@Composable
fun MyLocation(setLocation: (String) -> Unit) {
    val context = LocalContext.current

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

    DisposableEffect(permissionGranted) {
        if (permissionGranted.value && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            //request location
            val client = LocationServices.getFusedLocationProviderClient(context)

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    val location = result.lastLocation
                    setLocation("Lat: ${location?.latitude} Long: ${location?.longitude}")
                }
            }
            client.requestLocationUpdates(
                LocationRequest.Builder(10_000).build(),
                locationCallback,
                Looper.getMainLooper()
            )
            onDispose { // clean up event listener
                client.removeLocationUpdates(locationCallback)
            }
        } else {
            onDispose { }
        }
    }


}