package com.pratheeksha.scamshield.ui.home

import androidx.lifecycle.ViewModel
import com.pratheeksha.scamshield.data.local.ScamAlertNotifier
import com.pratheeksha.scamshield.domain.model.ProtectionStatus
import com.pratheeksha.scamshield.domain.model.RiskLevel
import com.pratheeksha.scamshield.domain.model.ScanRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class HomeUiState(
    val protectionStatus: ProtectionStatus = ProtectionStatus(),
    val recentScans: List<ScanRecord> = emptyList()
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val notifier: ScamAlertNotifier
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadPlaceholderData()
    }

    private fun loadPlaceholderData() {
        _uiState.value = HomeUiState(
            protectionStatus = ProtectionStatus(
                isActive = true,
                totalScansToday = 12,
                scamsBlockedToday = 2
            ),
            recentScans = listOf(
                ScanRecord(
                    id = "1",
                    phoneNumber = "+91 98765 43210",
                    callerName = "Unknown Caller",
                    riskLevel = RiskLevel.HIGH,
                    reason = "Detected banking scam keywords"
                ),
                ScanRecord(
                    id = "2",
                    phoneNumber = "+91 91234 56789",
                    callerName = "Mom",
                    riskLevel = RiskLevel.LOW,
                    reason = "No threats detected"
                ),
                ScanRecord(
                    id = "3",
                    phoneNumber = "+91 99887 76655",
                    callerName = "Unknown Caller",
                    riskLevel = RiskLevel.CRITICAL,
                    reason = "Voice cloning artifacts detected"
                )
            )
        )
    }

    fun toggleProtection() {
        _uiState.value = _uiState.value.copy(
            protectionStatus = _uiState.value.protectionStatus.copy(
                isActive = !_uiState.value.protectionStatus.isActive
            )
        )
    }

    fun testScamAlert() {
        notifier.showScamAlert(
            phoneNumber = "+91 98765 43210",
            riskLevel = RiskLevel.CRITICAL,
            reason = "Test alert - voice cloning detected"
        )
    }
}