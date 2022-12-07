package com.example.lostandfound.ui.screens.report

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lostandfound.ui.components.*
import com.example.lostandfound.ui.navigation.GraphRoutes
import com.example.lostandfound.ui.viewmodels.CreateReportViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

@Composable
fun CreateReportView(navController: NavHostController) {

    val viewModel: CreateReportViewModel = viewModel()
    val state = viewModel.state
    val scope = rememberCoroutineScope()

    val pickMedia =
        rememberLauncherForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            if (uri != null) {
                viewModel.setReportStats(state.reportStats, uri)
            }
        }

    LaunchedEffect(state.creationSuccess) {
        if (state.creationSuccess) {
            navController.navigate(GraphRoutes.Auth)
        }
    }

    if (state.getLocation) {
        GetLocationView(changeLocation = {
            viewModel.setReportStats(
                state.reportStats.copy(
                    latitude = it.latitude,
                    longitude = it.longitude
                )
            )
        }, exitGetLocation = {
            state.getLocation = false
        }, latLng = LatLng(0.0, 0.0)); return
    }

    if (state.errorMessage.isNotEmpty()) {
        AlertDialog(
            onDismissRequest = { state.errorMessage = "" },
            confirmButton = {
                Button(onClick = { state.errorMessage = "" }) { Text("Dismiss") }
            },
            title = { Text(text = "Error") },
            text = { Text(text = state.errorMessage) },
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LAFHeader(title = "Create Report", onBack = { navController.popBackStack() })

            LAFFormField(
                value = state.reportStats.title,
                onValueChange = {
                    if (it.length < 25) viewModel.setReportStats(
                        state.reportStats.copy(
                            title = it
                        )
                    )
                },
                label = "Title",
                placeholder = "Dog"
            )

            LAFFormField(
                value = state.reportStats.description,
                onValueChange = {
                    viewModel.setReportStats(
                        state.reportStats.copy(
                            description = it
                        )
                    )
                },
                label = "Description",
                placeholder = "Very Kind and wouldn't hurt a soul",
                multiline = true
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(.6f)
                .align(Alignment.Start)
        ) {
            LAFLoadingButton(
                onClick = { state.getLocation = true },
                text = "General Location",
                showIcon = state.reportStats.latitude != 0.0
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(.6f)
                .align(Alignment.Start)
        ) {
            LAFLoadingButton(
                onClick = {
                    pickMedia.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },
                text = if (state.bitmap != null) {
                    "Change Image"
                } else {
                    "Add Image"
                },
                showIcon = false
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            if (state.loadingImage) {
                LAFLoadingCircle()
            } else {
                if (state.bitmap != null) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        bitmap = state.bitmap!!.asImageBitmap(),
                        contentDescription = "Image"
                    )
                }
            }
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp))


        LAFLoadingButton(
            onClick = { scope.launch { viewModel.createReport() } },
            text = "Create",
            loading = state.loading,
            disabled = viewModel.isDirty(),
        )
    }
}
