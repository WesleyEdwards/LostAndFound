package com.example.lostandfound.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LAFTitleBar(
    title: String,
    onBack: (() -> Unit?)? = null,
    onMenu: (() -> Unit)? = null
) {
    Text(
        text = title,
        fontSize = 24.sp,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f)
            .padding(16.dp)
    )
}