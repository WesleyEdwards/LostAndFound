package com.example.lostandfound.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LAFLoadingButton(
    onClick: () -> Unit,
    text: String,
    loading: Boolean = false,
    disabled: Boolean = false,
    showIcon: Boolean = false,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onClick,
            enabled = !disabled,
        ) {
            if (loading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text(text = text)
                if (showIcon) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Next",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}