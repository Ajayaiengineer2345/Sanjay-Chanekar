package com.example.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

fun mapIconNameToVector(iconName: String): ImageVector {
    return when (iconName) {
        "factory" -> Icons.Default.Build
        "computer" -> Icons.Default.Computer
        "build" -> Icons.Default.Construction
        "chair" -> Icons.Default.Weekend
        "work" -> Icons.Default.Work
        "medical_services" -> Icons.Default.LocalHospital
        "school" -> Icons.Default.School
        "home" -> Icons.Default.Home
        "directions_car" -> Icons.Default.DirectionsCar
        "celebration" -> Icons.Default.Celebration
        "shopping_cart" -> Icons.Default.ShoppingCart
        "restaurant" -> Icons.Default.Restaurant
        
        "share" -> Icons.Default.Share
        "campaign" -> Icons.Default.Campaign
        "travel_explore" -> Icons.Default.LocationOn
        "bar_chart" -> Icons.Default.BarChart
        "workspace_premium" -> Icons.Default.Star
        "verified_user" -> Icons.Default.CheckCircle
        else -> Icons.Default.Info
    }
}

fun parseColor(hex: String): Color {
    return try {
        Color(android.graphics.Color.parseColor(hex))
    } catch (e: Exception) {
        Color(0xFF00D2FF)
    }
}
