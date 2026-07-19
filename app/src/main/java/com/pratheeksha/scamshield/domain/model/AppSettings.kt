package com.pratheeksha.scamshield.domain.model

data class AppSettings(
    val isDarkMode: Boolean = false,
    val detectionSensitivity: Float = 0.5f,
    val notificationsEnabled: Boolean = true,
    val offlineModeEnabled: Boolean = false,
    val shareAnonymousReports: Boolean = true
)