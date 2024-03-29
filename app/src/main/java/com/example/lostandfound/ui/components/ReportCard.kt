package com.example.lostandfound.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.lostandfound.R
import com.example.lostandfound.ui.models.Report
import com.example.lostandfound.ui.models.ReportStatus

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReportCard(report: Report, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 4.dp),
        onClick = onClick,
        border = BorderStroke(1.dp, Color.LightGray),

        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(8.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_zoom_in_24),
                    contentDescription = "Search"
                )
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = report.reportStats.title)
                    Text(
                        text = report.reportStats.description,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Gray,
                        style = MaterialTheme.typography.caption
                    )
                }
            }
            if (report.reportStats.status == ReportStatus.FOUND) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Found",
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .size(48.dp)
                )
            }
        }
    }
}