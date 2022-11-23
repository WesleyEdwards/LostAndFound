package com.example.lostandfound.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.lostandfound.ui.components.LAFHeader
import com.example.lostandfound.ui.components.LAFLoadingCircle
import com.example.lostandfound.ui.components.ReportCard
import com.example.lostandfound.ui.navigation.Routes
import com.example.lostandfound.ui.viewmodels.MyReportsViewModel

@Composable
fun MyReports(navController: NavController) {

    val viewModel: MyReportsViewModel = viewModel()
    val state = viewModel.state

    LaunchedEffect(true) {
        viewModel.getReport()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Routes.CreateLostReport) }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Report")
            }
        }
    ) {
        if (state.loading) {
            LAFLoadingCircle()
            return@Scaffold
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            LAFHeader(title = "My Reports")
            if (state.reportList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "You have not created any reports yet. Tap the + button to create one.",
                        color = Color.Gray,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(24.dp)
                    )
                }
            }
            LazyColumn {
                items(state.reportList, key = { it._id }) { report ->
                    ReportCard(report = report, onClick = {
                        navController.navigate(Routes.getLostReportView(report._id))
                    })
                }
            }
        }
    }
}