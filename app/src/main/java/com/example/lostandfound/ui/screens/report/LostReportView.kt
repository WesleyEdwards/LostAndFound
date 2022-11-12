package com.example.lostandfound.ui.screens.report

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lostandfound.ui.components.LAFLoadingCircle
import com.example.lostandfound.ui.viewmodels.LostReportViewModel

@Composable
fun LostReportView(reportId: String, navController: NavHostController) {

    val viewModel: LostReportViewModel = viewModel()
    val state = viewModel.state
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        viewModel.getReport(reportId)
    }

    LaunchedEffect(state.loading) {
        if (state.confirmDelete) {
            navController.popBackStack()
            return@LaunchedEffect
        }
    }

    if (state.report == null) {
        LAFLoadingCircle(); return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back button",
                modifier = Modifier
                    .size(28.dp)
                    .clickable { navController.popBackStack() }
            )

            Text(
                text = state.report!!.reportStats.title,
                style = if (state.report!!.reportStats.title.length < 15) {
                    MaterialTheme.typography.h4
                } else {
                    MaterialTheme.typography.h6
                }
            )

            Box(modifier = Modifier.size(28.dp))
        }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            if (state.report != null) {
                Text(
                    text = state.report!!.reportStats.title,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(4.dp)
                )
                Text(
                    text = state.report!!.reportStats.description,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
        Button(
            modifier = Modifier
                .padding(16.dp, 32.dp)
                .align(alignment = Alignment.CenterHorizontally),
            onClick = { print("TODO") }) {
            Text("Contact Victim")
        }
    }
}