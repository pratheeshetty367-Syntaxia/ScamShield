package com.pratheeksha.scamshield.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val settings by viewModel.settings.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        SettingsSectionTitle("Appearance")
        SettingsSwitchRow(
            label = "Dark Mode",
            checked = settings.isDarkMode,
            onCheckedChange = { viewModel.toggleDarkMode() }
        )

        Spacer(modifier = Modifier.height(24.dp))

        SettingsSectionTitle("Detection")
        Text(
            text = "Sensitivity: ${(settings.detectionSensitivity * 100).toInt()}%",
            style = MaterialTheme.typography.bodyMedium
        )
        Slider(
            value = settings.detectionSensitivity,
            onValueChange = { viewModel.updateSensitivity(it) },
            valueRange = 0f..1f
        )
        Text(
            text = "Higher sensitivity flags more calls as suspicious, but may increase false positives.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        SettingsSectionTitle("Privacy & Data")
        SettingsSwitchRow(
            label = "Offline Mode",
            checked = settings.offlineModeEnabled,
            onCheckedChange = { viewModel.toggleOfflineMode() }
        )
        SettingsSwitchRow(
            label = "Share Anonymous Scam Reports",
            checked = settings.shareAnonymousReports,
            onCheckedChange = { viewModel.toggleAnonymousReports() }
        )

        Spacer(modifier = Modifier.height(24.dp))

        SettingsSectionTitle("Notifications")
        SettingsSwitchRow(
            label = "Enable Notifications",
            checked = settings.notificationsEnabled,
            onCheckedChange = { viewModel.toggleNotifications() }
        )
    }
}

@Composable
private fun SettingsSectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary
    )
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun SettingsSwitchRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}