package com.example.model

data class MarketingService(
    val id: String,
    val title: String,
    val price: String,
    val duration: String,
    val description: String,
    val features: List<String>,
    val iconName: String
)

data class GoogleCertification(
    val id: String,
    val title: String,
    val issuer: String,
    val year: String,
    val certUrl: String,
    val iconName: String
)
