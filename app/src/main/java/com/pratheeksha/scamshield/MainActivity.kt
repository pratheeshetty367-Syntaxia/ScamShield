package com.pratheeksha.scamshield

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pratheeksha.scamshield.ui.navigation.ScamShieldBottomNavBar
import com.pratheeksha.scamshield.ui.navigation.ScamShieldDestinations
import com.pratheeksha.scamshield.ui.navigation.ScamShieldNavGraph
import com.pratheeksha.scamshield.ui.settings.SettingsViewModel
import com.pratheeksha.scamshield.ui.theme.ScamShieldTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            val settings by settingsViewModel.settings.collectAsState()

            ScamShieldTheme(darkTheme = settings.isDarkMode) {
                val navController = rememberNavController()
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = backStackEntry?.destination?.route

                Scaffold(
                    bottomBar = {
                        if (currentRoute != ScamShieldDestinations.Auth.route) {
                            ScamShieldBottomNavBar(navController)
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        ScamShieldNavGraph(navController = navController)
                    }
                }
            }
        }
    }
}