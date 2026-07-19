package com.pratheeksha.scamshield.domain.model

enum class RiskLevel {
    LOW, MEDIUM, HIGH, CRITICAL
}

data class ScanRecord(
    val id: String = "",
    val phoneNumber: String = "",
    val callerName: String = "Unknown",
    val riskLevel: RiskLevel = RiskLevel.LOW,
    val timestamp: Long = System.currentTimeMillis(),
    val reason: String = ""
)

data class ProtectionStatus(
    val isActive: Boolean = true,
    val totalScansToday: Int = 0,
    val scamsBlockedToday: Int = 0
)