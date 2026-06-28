package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.state.AppTheme

@Composable
fun MyApplicationTheme(
    selectedTheme: AppTheme = AppTheme.MIDNIGHT_NAVY,
    isDarkMode: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (isDarkMode) {
        darkColorScheme(
            primary = selectedTheme.primary,
            secondary = selectedTheme.secondary,
            tertiary = selectedTheme.glowColor,
            background = selectedTheme.background,
            surface = selectedTheme.surface,
            onPrimary = Color.White,
            onSecondary = Color.Black,
            onBackground = Color.White,
            onSurface = Color.White
        )
    } else {
        // Light mode variant
        lightColorScheme(
            primary = selectedTheme.primary,
            secondary = selectedTheme.secondary,
            tertiary = selectedTheme.glowColor,
            background = Color(0xFFF8FAFC),
            surface = Color.White,
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color(0xFF0F172A),
            onSurface = Color(0xFF0F172A)
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
