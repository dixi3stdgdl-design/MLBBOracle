package com.mlbb.oracle

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build



class OracleApp : Application() {

    companion object {
        const val OVERLAY_CHANNEL_ID = "mlbb_oracle_overlay"
        const val OVERLAY_CHANNEL_NAME = "MLBB Oracle Overlay"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val overlayChannel = NotificationChannel(
                OVERLAY_CHANNEL_ID,
                OVERLAY_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "MLBB Oracle overlay service"
                setShowBadge(false)
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(overlayChannel)
        }
    }
}
