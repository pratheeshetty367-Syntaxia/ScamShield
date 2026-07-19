package com.pratheeksha.scamshield.ui.dashboard

import androidx.lifecycle.ViewModel
import com.pratheeksha.scamshield.domain.model.DashboardStats
import com.pratheeksha.scamshield.domain.model.WeeklyDataPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor() : ViewModel() {

    private val _stats = MutableStateFlow(
        DashboardStats(
            totalProtectedCalls = 87,
            totalScamsBlocked = 14,
            totalDeepfakesDetected = 3,
            totalReportsSent = 9,
            weeklySummary = listOf(
                WeeklyDataPoint("Mon", 8),
                WeeklyDataPoint("Tue", 12),
                WeeklyDataPoint("Wed", 5),
                WeeklyDataPoint("Thu", 15),
                WeeklyDataPoint("Fri", 10),
                WeeklyDataPoint("Sat", 20),
                WeeklyDataPoint("Sun", 17)
            )
        )
    )
    val stats: StateFlow<DashboardStats> = _stats.asStateFlow()
}