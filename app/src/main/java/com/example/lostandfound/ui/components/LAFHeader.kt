package com.example.lostandfound.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@Composable
fun LAFHeader(
    title: String,
    onBack: (() -> Unit)? = null,
    onEdit: (() -> Unit)? = null,
    reportId: String? = null,
    onDelete: (() -> Unit)? = null
) {
    val menuExpanded = remember { mutableStateOf(false) }
    val deleteDialogue = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    if (onDelete != null) {
        LAFDeleteDialogue(
            open = deleteDialogue.value,
            onDismiss = { deleteDialogue.value = false },
            onConfirm = { scope.launch { onDelete() } }
        )
    }
    Row(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        onBack?.let {
            IconButton(onClick = { it() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
        if (onBack == null) {
            Spacer(modifier = Modifier.width(28.dp))
        }

        Text(
            text = title,
            style = if (title.length < 15) {
                MaterialTheme.typography.h4
            } else {
                MaterialTheme.typography.h6
            }
        )

        reportId?.let {
            Box(
                modifier = Modifier.size(28.dp)
            ) {
                IconButton(onClick = { menuExpanded.value = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More options")
                }
                DropdownMenu(
                    expanded = menuExpanded.value,
                    onDismissRequest = { menuExpanded.value = false }
                ) {
                    onEdit?.let {
                        DropdownMenuItem(onClick = {
                            it()
                            menuExpanded.value = false
                        }) {
                            Text("Edit")
                        }
                    }
                    DropdownMenuItem(onClick = {
                        deleteDialogue.value = true
                        menuExpanded.value = false
                    }) {
                        Text("Delete")
                    }
                }
            }
        }

        if (reportId == null) {
            Spacer(modifier = Modifier.width(28.dp))
        }
    }
}