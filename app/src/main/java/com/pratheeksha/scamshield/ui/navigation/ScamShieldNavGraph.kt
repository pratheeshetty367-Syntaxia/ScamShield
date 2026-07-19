package com.pratheeksha.scamshield.ui.navigation

import com.pratheeksha.scamshield.ui.settings.SettingsScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pratheeksha.scamshield.ui.auth.AuthScreen
import com.pratheeksha.scamshield.ui.home.HomeScreen

@Composable
fun ScamShieldNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = ScamShieldDestinations.Auth.route
    ) {
        composable(ScamShieldDestinations.Auth.route) {
            AuthScreen(
                onAuthSuccess = {
                    navController.navigate(ScamShieldDestinations.Home.route) {
                        popUpTo(ScamShieldDestinations.Auth.route) { inclusive = true }
                    }
                }
            )
        }
        composable(ScamShieldDestinations.Home.route) {
            HomeScreen()
        }
        composable(ScamShieldDestinations.Settings.route) {
            SettingsScreen()
        }
    }
}