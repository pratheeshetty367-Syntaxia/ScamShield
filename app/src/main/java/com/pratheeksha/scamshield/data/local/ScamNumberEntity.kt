package com.pratheeksha.scamshield.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scam_numbers")
data class ScamNumberEntity(
    @PrimaryKey val phoneNumber: String,
    val reportCount: Int = 1,
    val category: String = "Unknown",
    val lastReportedAt: Long = System.currentTimeMillis(),
    val isSynced: Boolean = false
)