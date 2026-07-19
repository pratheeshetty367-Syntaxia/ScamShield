package com.pratheeksha.scamshield.ui.navigation

sealed class ScamShieldDestinations(val route: String) {
    object Auth : ScamShieldDestinations("auth")
    object Home : ScamShieldDestinations("home")
    object Settings : ScamShieldDestinations("settings")
}
