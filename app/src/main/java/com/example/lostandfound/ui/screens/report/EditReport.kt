package com.example.lostandfound.ui.screens.report

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lostandfound.ui.components.*
import com.example.lostandfound.ui.viewmodels.EditReportViewModel
import kotlinx.coroutines.launch

@Composable
fun EditReport(
    reportId: String,
    navController: NavHostController,
) {
    val viewModel: EditReportViewModel = viewModel()
    val state = viewModel.state
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        viewModel.getReport(reportId)
    }

    if (state.initialReport != null && !state.loadingImage) {
        EditReportView(navController = navController,
            initialReport = state.initialReport!!,
            imageBitmap = state.imageBitmap,
            updateReport = { reportStats ->
                scope.launch {
                    viewModel.editReport(reportStats)
                    navController.popBackStack()
                }
            })
    } else {
        LAFLoadingCircle()
    }
}
