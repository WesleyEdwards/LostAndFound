package com.example.lostandfound.ui.components

import android.Manifest
import android.content.pm.PackageManager
import android.os.Looper
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

@Composable
fun GetLocationView(changeLocation: (newLocation: String) -> Unit, exitGetLocation: () -> Unit) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        LAFHeader(title = "Get Location", onBack = { exitGetLocation() })

        DisposableEffect(true) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                //request location
                val client = LocationServices.getFusedLocationProviderClient(context)

                val locationCallback = object : LocationCallback() {
                    override fun onLocationResult(result: LocationResult) {
                        val location = result.lastLocation
                        changeLocation("Lat: ${location?.latitude} Long: ${location?.longitude}")
                    }
                }
                client.requestLocationUpdates(
                    LocationRequest.create().apply {
                        interval = 10000
                        fastestInterval = 5000
                        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                    },
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
}