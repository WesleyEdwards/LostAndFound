package com.example.lostandfound.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng

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