package com.example.state

import androidx.compose.ui.graphics.Color

enum class AppTheme(
    val displayName: String,
    val primary: Color,
    val secondary: Color,
    val background: Color,
    val surface: Color,
    val onPrimary: Color = Color.White,
    val glowColor: Color
) {
    MIDNIGHT_NAVY(
        displayName = "Midnight Navy",
        primary = Color(0xFF0072FF),
        secondary = Color(0xFF00D2FF),
        background = Color(0xFF070B19),
        surface = Color(0xFF0E172C),
        glowColor = Color(0xFF00F0FF)
    ),
    ELECTRIC_PURPLE(
        displayName = "Electric Purple",
        primary = Color(0xFF9D4EDD),
        secondary = Color(0xFFC77DFF),
        background = Color(0xFF0B0516),
        surface = Color(0xFF190F2D),
        glowColor = Color(0xFFE0AAFF)
    ),
    FOREST_EMERALD(
        displayName = "Forest Emerald",
        primary = Color(0xFF00A896),
        secondary = Color(0xFF02C39A),
        background = Color(0xFF03140F),
        surface = Color(0xFF0A281E),
        glowColor = Color(0xFF02F0C2)
    ),
    SUNSET_GOLD(
        displayName = "Sunset Gold",
        primary = Color(0xFFF39C12),
        secondary = Color(0xFFF1C40F),
        background = Color(0xFF140D02),
        surface = Color(0xFF271B04),
        glowColor = Color(0xFFFFE57F)
    ),
    CRIMSON_FIRE(
        displayName = "Crimson Fire",
        primary = Color(0xFFD01B1B),
        secondary = Color(0xFFFF5252),
        background = Color(0xFF160303),
        surface = Color(0xFF2B0A0A),
        glowColor = Color(0xFFFF8A80)
    );

    companion object {
        fun fromName(name: String): AppTheme {
            return values().find { it.name == name } ?: MIDNIGHT_NAVY
        }
    }
}

enum class FontSize(val displayName: String, val scale: Float) {
    SMALL("Small", 0.85f),
    MEDIUM("Medium", 1.0f),
    LARGE("Large", 1.15f)
}

data class AppSettings(
    val selectedTheme: AppTheme = AppTheme.MIDNIGHT_NAVY,
    val isDarkMode: Boolean = true,
    val fontSize: FontSize = FontSize.MEDIUM,
    val animationSpeedMultiplier: Float = 1.0f,
    
    // Notifications
    val isNotificationEnabled: Boolean = true,
    val isMarketingTipsEnabled: Boolean = true,
    
    // Search Preferences
    val searchRadiusKm: Int = 10,
    val autoSuggestionsEnabled: Boolean = true,
    
    // Privacy Settings
    val shareAnalytics: Boolean = false,
    val personalizationEnabled: Boolean = true
)
