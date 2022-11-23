package com.example.lostandfound.ui.screens.report

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lostandfound.ui.components.LAFFormField
import com.example.lostandfound.ui.components.LAFLoadingButton
import com.example.lostandfound.ui.components.GetLocationView
import com.example.lostandfound.ui.components.LAFHeader
import com.example.lostandfound.ui.models.Report
import com.example.lostandfound.ui.models.ReportStats
import com.example.lostandfound.ui.navigation.GraphRoutes
import com.example.lostandfound.ui.viewmodels.CreateReportViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun CreateReportView(
    navController: NavHostController,
    initialReport: Report? = null,
    updateReport: ((ReportStats) -> Job)? = null
) {

    val viewModel: CreateReportViewModel = viewModel()
    val state = viewModel.state
    val scope = rememberCoroutineScope()
    val getLocation = remember { mutableStateOf(false) }

    GrantPermission()

    LaunchedEffect(state.creationSuccess) {
        if (state.creationSuccess) {
            navController.navigate(GraphRoutes.Auth)
        }
    }
    LaunchedEffect(true) {
        if (initialReport != null) {
            viewModel.setReport(initialReport)
        }
    }

    if (getLocation.value) {
        GetLocationView(changeLocation = {
            viewModel.setReportStats(
                state.reportStats.copy(
                    latitude = it.latitude,
                    longitude = it.longitude
                )
            )
        }) {
            getLocation.value = false
        }; return
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
                    if (it.length < 25) viewModel.setReportStats(state.reportStats.copy(title = it))
                },
                label = "Title",
                placeholder = "Dog"
            )

            LAFFormField(
                value = state.reportStats.description,
                onValueChange = { viewModel.setReportStats(state.reportStats.copy(description = it)) },
                label = "Description",
                placeholder = "Very Kind and wouldn't hurt a soul",
                multiline = true
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(.5f)
                .align(Alignment.Start)
        ) {
            LAFLoadingButton(
                onClick = { getLocation.value = true },
                text = "Select Location",
                showIcon = state.reportStats.latitude != 0.0
            )
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        if (updateReport != null) {
            LAFLoadingButton(
                onClick = { scope.launch { updateReport(state.reportStats) } },
                text = "Update",
                loading = state.loading
            )
        } else {
            LAFLoadingButton(
                onClick = { scope.launch { viewModel.createReport() } },
                text = "Create",
                loading = state.loading,
                disabled = viewModel.isDirty()
            )
        }
    }
}
