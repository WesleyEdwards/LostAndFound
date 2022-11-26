package com.example.lostandfound.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun MapsView(
    currentLocation: MutableState<LatLng>,
    changeLocation: ((newLocation: LatLng) -> Unit)? = null
) {

    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.SATELLITE))
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation.value, 10f)
    }


    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        properties = properties,
        cameraPositionState = cameraPositionState,
        onMapLongClick = {
            currentLocation.value = it
            changeLocation?.let { changeLocation ->
                changeLocation(it)
            }
        }

    ) {
        Marker(
            state = MarkerState(position = currentLocation.value),
            title = "Location",
        )
    }
}