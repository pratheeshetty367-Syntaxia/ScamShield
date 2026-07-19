package com.pratheeksha.scamshield.data.local

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.pratheeksha.scamshield.domain.model.RiskLevel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScamAlertNotifier @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        const val CHANNEL_ID = "scam_alerts"
        private const val NOTIFICATION_ID = 1001
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Scam Alerts",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Real-time scam call warnings"
            enableVibration(true)
            setSound(
                android.provider.Settings.System.DEFAULT_NOTIFICATION_URI,
                android.media.AudioAttributes.Builder()
                    .setUsage(android.media.AudioAttributes.USAGE_ALARM)
                    .build()
            )
        }
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    fun showScamAlert(phoneNumber: String, riskLevel: RiskLevel, reason: String) {
        val title = when (riskLevel) {
            RiskLevel.CRITICAL -> "⚠️ CRITICAL: Likely Scam Call"
            RiskLevel.HIGH -> "⚠️ High Risk Call Detected"
            RiskLevel.MEDIUM -> "Suspicious Call"
            RiskLevel.LOW -> "Call Scanned"
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle(title)
            .setContentText("$phoneNumber — $reason")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_CALL)
            .setAutoCancel(true)
            .build()

        if (riskLevel == RiskLevel.HIGH || riskLevel == RiskLevel.CRITICAL) {
            vibrate()
        }

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
    }

    private fun vibrate() {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(
                VibrationEffect.createWaveform(longArrayOf(0, 400, 200, 400), -1)
            )
        }
    }
}