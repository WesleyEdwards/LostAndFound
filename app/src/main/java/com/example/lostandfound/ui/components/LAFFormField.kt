package com.example.lostandfound.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.lostandfound.R

@Composable
fun LAFFormField(
    value: String,
    onValueChange: (value: String) -> Unit,
    error: Boolean = false,
    placeholder: String? = null,
    label: String? = null,
    multiline: Boolean = false,
    numeric: Boolean = false,
    password: Boolean = false,
    trailingIcon: Boolean = true,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp, 8.dp),
        value = value,
        trailingIcon = {
            if (password) {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_remove_red_eye_24),
                        contentDescription = "password",
                        tint = if (passwordVisible) MaterialTheme.colors.primary else Color.Gray
                    )
                }
                return@OutlinedTextField
            }
            if (trailingIcon) {
                IconButton(onClick = { onValueChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear",
                        modifier = Modifier.clickable {
                            onValueChange("")
                        }
                    )
                }
            }
        },
        onValueChange = onValueChange,
        isError = error,
        singleLine = !multiline,
        label = {
            label?.let {
                Text(text = it)
            }
        },
        placeholder = {
            placeholder?.let {
                Text(text = it, fontStyle = FontStyle.Italic, color = Color.LightGray)
            }
        },
        keyboardOptions =
        KeyboardOptions(keyboardType = if (numeric) KeyboardType.Number else KeyboardType.Text),
        visualTransformation = if (password && !passwordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        }
    )
}
