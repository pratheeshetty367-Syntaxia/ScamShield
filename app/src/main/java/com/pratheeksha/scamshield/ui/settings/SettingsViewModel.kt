package com.pratheeksha.scamshield.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pratheeksha.scamshield.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.pratheeksha.scamshield.domain.model.AppSettings
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {

    val settings: StateFlow<AppSettings> = repository.settingsFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AppSettings()
    )

    fun toggleDarkMode() {
        viewModelScope.launch {
            repository.setDarkMode(!settings.value.isDarkMode)
        }
    }

    fun updateSensitivity(value: Float) {
        viewModelScope.launch { repository.setSensitivity(value) }
    }

    fun toggleNotifications() {
        viewModelScope.launch {
            repository.setNotifications(!settings.value.notificationsEnabled)
        }
    }

    fun toggleOfflineMode() {
        viewModelScope.launch {
            repository.setOfflineMode(!settings.value.offlineModeEnabled)
        }
    }

    fun toggleAnonymousReports() {
        viewModelScope.launch {
            repository.setAnonymousReports(!settings.value.shareAnonymousReports)
        }
    }
}