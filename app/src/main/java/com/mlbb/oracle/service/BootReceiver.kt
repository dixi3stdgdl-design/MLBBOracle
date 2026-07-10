package com.mlbb.oracle.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log

/**
 * BroadcastReceiver that starts services on boot completion.
 * Ensures Oracle services restart after device reboot.
 */
class BootReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "BootReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return

        Log.d(TAG, "Boot completed, starting GameBoosterService")

        val serviceIntent = Intent(context, GameBoosterService::class.java).apply {
            action = "com.mlbb.oracle.BOOSTER_TOGGLE"
            putExtra("enabled", true)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent)
        } else {
            context.startService(serviceIntent)
        }
    }
}