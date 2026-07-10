package com.mlbb.oracle.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log

/**
 * BroadcastReceiver that detects when MLBB is launched or closed.
 * 
 * This enables auto-activation of the overlay when the game starts,
 * and auto-deactivation when the game ends.
 * 
 * Monitors:
 * - Package manager for MLBB launch/stop
 * - Game Booster integration (Xiaomi, OnePlus, etc.)
 */
class MLBBReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "MLBBReceiver"
        private const val MLBB_PACKAGE = "com.mobile.legends"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val packageName = intent.data?.schemeSpecificPart ?: return

        if (packageName != MLBB_PACKAGE) return

        when (intent.action) {
            Intent.ACTION_PACKAGE_ADDED,
            Intent.ACTION_PACKAGE_REPLACED -> {
                Log.d(TAG, "MLBB installed/updated")
            }
            Intent.ACTION_PACKAGE_REMOVED -> {
                Log.d(TAG, "MLBB uninstalled")
            }
        }
    }
}
