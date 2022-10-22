package com.example.lostandfound.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = GreenPrimary,
    primaryVariant = GreenLight,
    secondary = GreenDark
)

private val LightColorPalette = lightColors(
    primary = GreenPrimary,
    primaryVariant = GreenLight,
    secondary = GreenDark
)

@Composable
fun LostAndFoundTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}