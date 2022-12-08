package com.example.lostandfound.ui.screens.report

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lostandfound.ui.components.*
import com.example.lostandfound.ui.models.ReportStatus
import com.example.lostandfound.ui.navigation.Routes
import com.example.lostandfound.ui.repositories.ReportRepo
import com.example.lostandfound.ui.viewmodels.LostReportViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

@Composable
fun LostReportView(reportId: String, navController: NavHostController) {
    val viewModel: LostReportViewModel = viewModel()
    val state = viewModel.state
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        viewModel.getReport(reportId)
    }
    val mine = ReportRepo.isMyReport(reportId)

    LaunchedEffect(state.loading) {
        if (state.confirmDelete) {
            navController.popBackStack()
            return@LaunchedEffect
        }
    }

    if (state.contactModal) {
        AlertDialog(
            onDismissRequest = { state.contactModal = false },
            title = {
                Text("Found")
            },
            text = {
                Text(
                    fontStyle = Italic,
                    text = "If found, please contact: ${state.report?.userName} ${state.report?.userEmail}"
                )
            },
            confirmButton = {
                TextButton(onClick = { state.contactModal = false }) {
                    Text("OK")
                }
            },
        )
    }
    if (state.foundModal) {
        AlertDialog(
            onDismissRequest = { state.foundModal = false },
            title = {
                Text("Found")
            },
            text = {
                Text(
                    fontStyle = Italic,
                    text = "Mark as found?"
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    scope.launch { viewModel.markAsFound(); navController.popBackStack() }
                }) {
                    Text("Found")
                }
            },
            dismissButton = {
                TextButton(onClick = { state.foundModal = false }) {
                    Text("Cancel")
                }
            },
        )
    }

    if (state.report == null) {
        LAFLoadingCircle(); return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (mine) {
            LAFHeader(
                state.report!!.reportStats.title,
                onBack = { navController.popBackStack() },
                onEdit = { navController.navigate(Routes.getReportEdit(reportId)) },
                reportId,
                onDelete = { scope.launch { viewModel.deleteReport(reportId) } },
                onMarkAsFound = { state.foundModal = true },
                markAsFound = state.report!!.reportStats.status == ReportStatus.LOST
            )
        } else {
            LAFHeader(state.report!!.reportStats.title, onBack = { navController.popBackStack() })
        }

        state.report?.let {
            Text(
                modifier = Modifier.padding(vertical = 16.dp),
                text = it.reportStats.description,
                style = MaterialTheme.typography.body1,
                fontStyle = Italic,
            )
        }

        if (state.report!!.reportStats.longitude != 0.0) {
            DisplayLocation(
                LatLng(
                    state.report!!.reportStats.latitude,
                    state.report!!.reportStats.longitude
                )
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(vertical = 16.dp)
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

        LAFAd()

        if (!mine) {
            Button(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp),
                onClick = { state.contactModal = true }
            ) {
                Text("Found")
            }
        }

        if (mine && state.report!!.reportStats.status == ReportStatus.LOST) {
            Button(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp),
                onClick = { state.foundModal = true },
            ) {
                Text("Mark as found")
            }
        }

        if (mine && state.report!!.reportStats.status == ReportStatus.FOUND) {
            Text(
                text = "Status: ${state.report!!.reportStats.status}",
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(bottom = 24.dp),
                color = Color.Gray
            )
        }
    }
}
