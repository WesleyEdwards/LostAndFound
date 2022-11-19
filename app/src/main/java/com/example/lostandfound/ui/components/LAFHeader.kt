package com.example.lostandfound.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ColumnScope.LAFHeader(title: String, onBack: () -> Unit) {
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
                .clickable { onBack() }
        )

        Text(
            text = title,
            style = if (title.length < 15) {
                MaterialTheme.typography.h4
            } else {
                MaterialTheme.typography.h6
            }
        )

        Box(modifier = Modifier.size(28.dp))
    }
}