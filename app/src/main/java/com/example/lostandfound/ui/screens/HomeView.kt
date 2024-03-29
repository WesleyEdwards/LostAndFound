package com.example.lostandfound.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.primarySurface
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
import com.example.lostandfound.ui.models.ReportStatus
import com.example.lostandfound.ui.navigation.Routes
import com.example.lostandfound.ui.viewmodels.HomeViewModel

@Composable
fun HomeView(navController: NavController) {

    val viewModel: HomeViewModel = viewModel()
    val state = viewModel.state

    LaunchedEffect(true) {
        viewModel.getAllReports()
    }

    if (state.loading) {
        LAFLoadingCircle()
        return
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
//            .background(Color.LightGray)
    ) {

        LAFHeader(title = "Still Lost")

        if (state.reportList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "There are no reports.",
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
                if (report.reportStats.status == ReportStatus.LOST) {
                    ReportCard(report = report, onClick = {
                        navController.navigate(Routes.getLostReportView(report._id))
                    })
                }
            }
        }
    }
}
