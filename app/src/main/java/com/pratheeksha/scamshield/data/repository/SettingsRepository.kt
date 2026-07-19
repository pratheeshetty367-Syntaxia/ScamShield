package com.pratheeksha.scamshield.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.pratheeksha.scamshield.domain.model.AppSettings
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "scamshield_settings")

@Singleton
class SettingsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object Keys {
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val SENSITIVITY = floatPreferencesKey("sensitivity")
        val NOTIFICATIONS = booleanPreferencesKey("notifications")
        val OFFLINE_MODE = booleanPreferencesKey("offline_mode")
        val ANONYMOUS_REPORTS = booleanPreferencesKey("anonymous_reports")
    }

    val settingsFlow = context.dataStore.data.map { prefs ->
        AppSettings(
            isDarkMode = prefs[Keys.DARK_MODE] ?: false,
            detectionSensitivity = prefs[Keys.SENSITIVITY] ?: 0.5f,
            notificationsEnabled = prefs[Keys.NOTIFICATIONS] ?: true,
            offlineModeEnabled = prefs[Keys.OFFLINE_MODE] ?: false,
            shareAnonymousReports = prefs[Keys.ANONYMOUS_REPORTS] ?: true
        )
    }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { it[Keys.DARK_MODE] = enabled }
    }

    suspend fun setSensitivity(value: Float) {
        context.dataStore.edit { it[Keys.SENSITIVITY] = value }
    }

    suspend fun setNotifications(enabled: Boolean) {
        context.dataStore.edit { it[Keys.NOTIFICATIONS] = enabled }
    }

    suspend fun setOfflineMode(enabled: Boolean) {
        context.dataStore.edit { it[Keys.OFFLINE_MODE] = enabled }
    }

    suspend fun setAnonymousReports(enabled: Boolean) {
        context.dataStore.edit { it[Keys.ANONYMOUS_REPORTS] = enabled }
    }
}