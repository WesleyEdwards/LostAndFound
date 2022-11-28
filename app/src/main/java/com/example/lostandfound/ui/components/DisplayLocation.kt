package com.example.lostandfound.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun DisplayLocation(location: LatLng) {

    val displayLocation: MutableState<LatLng> = remember { mutableStateOf(location) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        MapsView(currentLocation = displayLocation)
    }
}