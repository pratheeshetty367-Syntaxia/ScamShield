package com.pratheeksha.scamshield.domain.model

data class WeeklyDataPoint(val day: String, val scansCount: Int)

data class DashboardStats(
    val totalProtectedCalls: Int = 0,
    val totalScamsBlocked: Int = 0,
    val totalDeepfakesDetected: Int = 0,
    val totalReportsSent: Int = 0,
    val weeklySummary: List<WeeklyDataPoint> = emptyList()
)