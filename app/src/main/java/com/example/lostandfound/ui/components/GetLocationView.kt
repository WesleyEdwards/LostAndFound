package com.example.lostandfound.ui.components

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Looper
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun GetLocationView(
    changeLocation: (newLocation: LatLng) -> Unit,
    exitGetLocation: () -> Unit,
    latLng: LatLng = LatLng(0.0, 0.0)
) {

    val currentLocation = remember { mutableStateOf(latLng) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight(.9f)
            .padding(16.dp)
    ) {
        LAFHeader(title = "Location", onBack = { exitGetLocation() })


        if (currentLocation.value.latitude == 0.0 || currentLocation.value.longitude == 0.0) {
            GetMyLocation { currentLocation.value = it }
            LAFLoadingCircle(); return@Column
        }

        MapsView(currentLocation, changeLocation)

        changeLocation(currentLocation.value)


    }
}

@SuppressLint("MissingPermission")
@Composable
fun GetMyLocation(setLocation: (location: LatLng) -> Unit) {

    val context = LocalContext.current
    DisposableEffect(true) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            //request location
            val client = LocationServices.getFusedLocationProviderClient(context)

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    val location = result.lastLocation
                    setLocation(LatLng(location?.latitude ?: 0.0, location?.longitude ?: 0.0))
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