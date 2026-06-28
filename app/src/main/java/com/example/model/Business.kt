package com.example.model

data class Business(
    val id: String,
    val name: String,
    val category: String,
    val address: String,
    val phone: String,
    val email: String,
    val website: String,
    val rating: Float,
    val reviewsCount: Int,
    val description: String,
    val isFeatured: Boolean = false,
    val accentColorHex: String = "#00D2FF" // default cyan neon
)

data class BusinessCategory(
    val id: String,
    val name: String,
    val iconName: String,
    val count: Int,
    val accentColorHex: String
)
