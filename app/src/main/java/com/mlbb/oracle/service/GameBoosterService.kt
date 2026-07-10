package com.mlbb.oracle.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.mlbb.oracle.MainActivity
import com.mlbb.oracle.OracleApp

/**
 * Game Booster Integration Service
 * 
 * This service allows MLBB Oracle to be controlled from the phone's
 * built-in Game Booster / Game Turbo / Game Space settings.
 * 
 * Features:
 * - Toggle ON/OFF from Game Booster
 * - Auto-activate when MLBB is launched
 * - Auto-deactivate when MLBB is closed
 * - Performance mode optimization
 */
class GameBoosterService : Service() {

    companion object {
        private const val TAG = "GameBoosterService"
        private const val NOTIFICATION_ID = 1002
        private const val ACTION_BOOSTER_TOGGLE = "com.mlbb.oracle.BOOSTER_TOGGLE"
        private const val ACTION_GAME_LAUNCHED = "com.mlbb.oracle.GAME_LAUNCHED"
        private const val ACTION_GAME_CLOSED = "com.mlbb.oracle.GAME_CLOSED"
        
        private var instance: GameBoosterService? = null
        fun getInstance(): GameBoosterService? = instance
        
        // State
        var isBoosterEnabled = true
            private set
        var isGameActive = false
            private set
    }

    private var gameReceiver: BroadcastReceiver? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        registerGameReceiver()
        Log.d(TAG, "Game Booster Service created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_BOOSTER_TOGGLE -> {
                isBoosterEnabled = intent.getBooleanExtra("enabled", true)
                handleBoosterToggle()
            }
            ACTION_GAME_LAUNCHED -> {
                isGameActive = true
                handleGameLaunch()
            }
            ACTION_GAME_CLOSED -> {
                isGameActive = false
                handleGameClose()
            }
        }

        startForeground(NOTIFICATION_ID, createNotification())
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterGameReceiver()
        instance = null
        Log.d(TAG, "Game Booster Service destroyed")
    }

    private fun registerGameReceiver() {
        gameReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (intent.action) {
                    "com.miui.gamebooster.ACTION_GAME_START" -> {
                        // Xiaomi Game Turbo start
                        val packageName = intent.getStringExtra("package_name")
                        if (packageName == "com.mobile.legends") {
                            isGameActive = true
                            handleGameLaunch()
                        }
                    }
                    "com.miui.gamebooster.ACTION_GAME_STOP" -> {
                        // Xiaomi Game Turbo stop
                        val packageName = intent.getStringExtra("package_name")
                        if (packageName == "com.mobile.legends") {
                            isGameActive = false
                            handleGameClose()
                        }
                    }
                    "com.oneplus.gamecenter.GAME_RUNNING" -> {
                        // OnePlus Game Space start
                        val packageName = intent.getStringExtra("packageName")
                        if (packageName == "com.mobile.legends") {
                            isGameActive = true
                            handleGameLaunch()
                        }
                    }
                    "com.oneplus.gamecenter.GAME_EXITED" -> {
                        // OnePlus Game Space stop
                        val packageName = intent.getStringExtra("packageName")
                        if (packageName == "com.mobile.legends") {
                            isGameActive = false
                            handleGameClose()
                        }
                    }
                }
            }
        }

        val filter = IntentFilter().apply {
            addAction("com.miui.gamebooster.ACTION_GAME_START")
            addAction("com.miui.gamebooster.ACTION_GAME_STOP")
            addAction("com.oneplus.gamecenter.GAME_RUNNING")
            addAction("com.oneplus.gamecenter.GAME_EXITED")
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(gameReceiver, filter, RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(gameReceiver, filter)
        }
    }

    private fun unregisterGameReceiver() {
        gameReceiver?.let {
            unregisterReceiver(it)
            gameReceiver = null
        }
    }

    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val statusText = when {
            !isBoosterEnabled -> "Booster disabled"
            isGameActive -> "MLBB detected - Overlay active"
            else -> "Waiting for MLBB..."
        }

        return Notification.Builder(this, OracleApp.OVERLAY_CHANNEL_ID)
            .setContentTitle("MLBB Oracle Booster")
            .setContentText(statusText)
            .setSmallIcon(com.mlbb.oracle.R.drawable.ic_overlay)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }

    private fun handleBoosterToggle() {
        if (isBoosterEnabled) {
            Log.d(TAG, "Game Booster ENABLED")
            // Start overlay service if game is active
            if (isGameActive) {
                startOverlayService()
            }
        } else {
            Log.d(TAG, "Game Booster DISABLED")
            // Stop overlay service
            stopOverlayService()
        }
    }

    private fun handleGameLaunch() {
        Log.d(TAG, "MLBB Game LAUNCHED")
        if (isBoosterEnabled) {
            startOverlayService()
        }
    }

    private fun handleGameClose() {
        Log.d(TAG, "MLBB Game CLOSED")
        stopOverlayService()
    }

    private fun startOverlayService() {
        val intent = Intent(this, OverlayService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }

    private fun stopOverlayService() {
        val intent = Intent(this, OverlayService::class.java)
        stopService(intent)
    }
}
