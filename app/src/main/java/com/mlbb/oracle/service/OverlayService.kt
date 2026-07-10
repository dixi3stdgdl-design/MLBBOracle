package com.mlbb.oracle.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import com.mlbb.oracle.MainActivity
import com.mlbb.oracle.OracleApp
import com.mlbb.oracle.R
import com.mlbb.oracle.data.HeroDatabase
import com.mlbb.oracle.data.EquipmentDatabase
import kotlinx.coroutines.*

class OverlayService : Service() {

    companion object {
        private const val TAG = "OverlayService"
        private const val NOTIFICATION_ID = 1001
        private var instance: OverlayService? = null
        fun getInstance(): OverlayService? = instance
    }

    private var windowManager: WindowManager? = null
    private var overlayView: View? = null
    private var isShowing = false
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    // State
    private var isInNexus = false
    private var gameTime = "00:00"
    private var myHeroName = ""
    private var myGold = 0
    private var myKills = 0
    private var myDeaths = 0
    private var myAssists = 0
    private var myDamage = 0
    private var myCS = 0
    private var enemyHeroes = mutableListOf<String>()
    private var enemyGold = mutableListOf<Int>()

    // Team fight prediction state
    private var teamFightPrediction = ""
    private var nextObjective = ""
    private var equipmentRecommendation = ""

    // Data receiver - handles ALL oracle data
    private val frameReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                "com.mlbb.oracle.FRAME_PROCESSED" -> {
                    // Main game data
                    val gold = intent.getIntExtra("gold", 0)
                    val kills = intent.getIntExtra("kills", 0)
                    val deaths = intent.getIntExtra("deaths", 0)
                    val assists = intent.getIntExtra("assists", 0)
                    val damage = intent.getIntExtra("damage_dealt", 0)
                    val cs = intent.getIntExtra("minions_killed", 0)
                    val heroName = intent.getStringExtra("hero_name") ?: "Unknown"
                    val items = intent.getStringArrayExtra("items")?.joinToString(" ") ?: ""
                    val time = intent.getStringExtra("game_time") ?: "00:00"

                    myHeroName = heroName
                    myGold = gold
                    myKills = kills
                    myDeaths = deaths
                    myAssists = assists
                    myDamage = damage
                    myCS = cs
                    gameTime = time

                    updateMyHero(heroName, kills, deaths, assists, gold, damage, cs, items, time)
                    analyzeGameState()
                }
                "com.mlbb.oracle.ENEMY_DETECTED" -> {
                    // Enemy heroes data
                    val enemies = intent.getStringArrayExtra("enemy_heroes") ?: emptyArray()
                    val enemyKills = intent.getStringArrayExtra("enemy_kills") ?: emptyArray()
                    val enemyDeaths = intent.getStringArrayExtra("enemy_deaths") ?: emptyArray()
                    val enemyGolds = intent.getStringArrayExtra("enemy_gold") ?: emptyArray()
                    val enemyItems = intent.getStringArrayExtra("enemy_items") ?: emptyArray()

                    enemyHeroes = enemies.toMutableList()
                    enemyGold = enemyGolds.mapNotNull { it.replace("K", "").replace(",", "").toDoubleOrNull()?.toInt() }.toMutableList()

                    updateEnemies(enemies, enemyKills, enemyDeaths, enemyGolds, enemyItems)
                    analyzeGameState()
                }
                "com.mlbb.oracle.SUGGESTION" -> {
                    // Team suggestions
                    val suggestion = intent.getStringExtra("suggestion") ?: ""
                    updateSuggestion(suggestion)
                }
                "com.mlbb.oracle.NEXUS_STATUS" -> {
                    // Nexus detection
                    isInNexus = intent.getBooleanExtra("in_nexus", false)
                    updateNexusIndicator()
                }
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        // Register receiver for ALL oracle intents
        val filter = IntentFilter().apply {
            addAction("com.mlbb.oracle.FRAME_PROCESSED")
            addAction("com.mlbb.oracle.ENEMY_DETECTED")
            addAction("com.mlbb.oracle.SUGGESTION")
            addAction("com.mlbb.oracle.NEXUS_STATUS")
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(frameReceiver, filter, RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(frameReceiver, filter)
        }

        startForeground(NOTIFICATION_ID, createNotification())
        showOverlay()
    }

    override fun onDestroy() {
        super.onDestroy()
        hideOverlay()
        unregisterReceiver(frameReceiver)
        scope.cancel()
        instance = null
    }

    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return Notification.Builder(this, OracleApp.OVERLAY_CHANNEL_ID)
            .setContentTitle("MLBB Oracle")
            .setContentText("Active — analyzing game state")
            .setSmallIcon(R.drawable.ic_overlay)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }

    private fun showOverlay() {
        if (isShowing) return

        try {
            overlayView = LayoutInflater.from(this).inflate(R.layout.overlay_layout, null)

            // Configure window params — LEFT SIDE
            val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT
            ).apply {
                gravity = Gravity.TOP or Gravity.START
                x = 16
                y = 100
            }

            setupDragBehavior(overlayView!!, params)
            windowManager?.addView(overlayView, params)
            isShowing = true
            Log.d(TAG, "Overlay shown at left side")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to show overlay", e)
        }
    }

    private fun hideOverlay() {
        if (!isShowing) return
        try {
            overlayView?.let { windowManager?.removeView(it) }
            overlayView = null
            isShowing = false
        } catch (e: Exception) {
            Log.e(TAG, "Failed to hide overlay", e)
        }
    }

    private fun setupDragBehavior(view: View, params: WindowManager.LayoutParams) {
        var initialX = 0
        var initialY = 0
        var initialTouchX = 0f
        var initialTouchY = 0f
        var isDragging = false
        var lastTapTime = 0L
        val doubleTapThreshold = 300L // ms

        view.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = params.x
                    initialY = params.y
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                    isDragging = false
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    val dx = event.rawX - initialTouchX
                    val dy = event.rawY - initialTouchY
                    if (Math.abs(dx) > 5 || Math.abs(dy) > 5) {
                        isDragging = true
                        params.x = initialX + dx.toInt()
                        params.y = initialY + dy.toInt()
                        windowManager?.updateViewLayout(view, params)
                    }
                    true
                }
                MotionEvent.ACTION_UP -> {
                    if (!isDragging) {
                        // Double-tap detection
                        val currentTime = System.currentTimeMillis()
                        if (currentTime - lastTapTime < doubleTapThreshold) {
                            // Double-tap detected — toggle overlay visibility
                            toggleOverlayVisibility()
                            lastTapTime = 0L
                        } else {
                            lastTapTime = currentTime
                        }
                    }
                    true
                }
                else -> false
            }
        }
    }

    private var isOverlayMinimized = false

    private fun toggleOverlayVisibility() {
        scope.launch {
            isOverlayMinimized = !isOverlayMinimized

            // Find all content rows except header
            val header = overlayView?.findViewById<LinearLayout>(R.id.overlay_header)
            val myHero = overlayView?.findViewById<LinearLayout>(R.id.my_hero_section)
            val enemyHeader = overlayView?.findViewById<TextView>(R.id.enemies_label)
            val enemy1 = overlayView?.findViewById<LinearLayout>(R.id.enemy1_row)
            val enemy2 = overlayView?.findViewById<LinearLayout>(R.id.enemy2_row)
            val enemy3 = overlayView?.findViewById<LinearLayout>(R.id.enemy3_row)
            val enemy4 = overlayView?.findViewById<LinearLayout>(R.id.enemy4_row)
            val enemy5 = overlayView?.findViewById<LinearLayout>(R.id.enemy5_row)
            val suggestion = overlayView?.findViewById<LinearLayout>(R.id.suggestion_section)

            val visibility = if (isOverlayMinimized) View.GONE else View.VISIBLE

            myHero?.visibility = visibility
            enemyHeader?.visibility = visibility
            enemy1?.visibility = visibility
            enemy2?.visibility = visibility
            enemy3?.visibility = visibility
            enemy4?.visibility = visibility
            enemy5?.visibility = visibility
            suggestion?.visibility = visibility

            Log.d(TAG, "Overlay ${if (isOverlayMinimized) "minimized" else "expanded"} (double-tap)")
        }
    }

    private fun analyzeGameState() {
        scope.launch {
            // Team fight prediction
            val teamFightResult = predictTeamFight()
            updateTeamFightPrediction(teamFightResult)

            // Next objective recommendation
            val objective = calculateNextObjective()
            updateNextObjective(objective)

            // Equipment recommendation based on enemies
            val recommendation = getEquipmentRecommendation()
            updateEquipmentRecommendation(recommendation)

            // Update suggestion with all insights
            val insights = mutableListOf<String>()
            if (teamFightResult.isNotEmpty()) {
                insights.add(teamFightResult)
            }
            if (objective.isNotEmpty()) {
                insights.add("Next: $objective")
            }
            if (recommendation.isNotEmpty()) {
                insights.add(recommendation)
            }
            if (insights.isNotEmpty()) {
                updateSuggestion(insights.joinToString("\n"))
            }
        }
    }

    private fun predictTeamFight(): String {
        if (enemyHeroes.isEmpty() || myHeroName.isEmpty()) return ""

        val myTeamGold = myGold
        val enemyTeamGold = enemyGold.sum()

        val goldAdvantage = myTeamGold - (enemyTeamGold / 5)

        val myHero = HeroDatabase.getHeroByName(myHeroName)
        val myPowerSpike = myHero?.powerSpike

        // Parse game time to determine game phase
        val timeParts = gameTime.split(":")
        val minutes = timeParts.getOrNull(0)?.toIntOrNull() ?: 0

        val gamePhase = when {
            minutes < 5 -> "early"
            minutes < 15 -> "mid"
            else -> "late"
        }

        val myPower = when (gamePhase) {
            "early" -> myPowerSpike?.early ?: 5
            "mid" -> myPowerSpike?.mid ?: 5
            "late" -> myPowerSpike?.late ?: 5
            else -> 5
        }

        return when {
            goldAdvantage > 2000 && myPower >= 7 -> "🟢 TEAM FIGHT: Strong - Engage!"
            goldAdvantage > 1000 && myPower >= 5 -> "🟡 TEAM FIGHT: Favorable - Be cautious"
            goldAdvantage > 0 -> "🟡 TEAM FIGHT: Even - Wait for advantage"
            goldAdvantage > -1000 -> "🟠 TEAM FIGHT: Disadvantage - Avoid"
            else -> "🔴 TEAM FIGHT: Weak - Farm safely"
        }
    }

    private fun calculateNextObjective(): String {
        val timeParts = gameTime.split(":")
        val minutes = timeParts.getOrNull(0)?.toIntOrNull() ?: 0

        return when {
            minutes < 2 -> "Secure first buff"
            minutes < 5 -> "Gank gold lane"
            minutes == 8 || minutes == 16 -> "🔴 TURTLE spawning!"
            minutes == 18 || minutes == 24 -> "🔴 LORD spawning!"
            minutes > 18 -> "Group for Lord"
            minutes > 8 -> "Pressure towers"
            else -> "Farm and rotate"
        }
    }

    private fun getEquipmentRecommendation(): String {
        if (enemyHeroes.isEmpty()) return ""

        val counterBuild = EquipmentDatabase.getCounterBuild(enemyHeroes)
        val recommendations = mutableListOf<String>()

        counterBuild.forEach { (category, items) ->
            val topItem = items.firstOrNull()
            if (topItem != null) {
                when (category) {
                    com.mlbb.oracle.data.EquipmentCategory.DEFENSE -> {
                        if (enemyHeroes.any { it in topItem.counterFor }) {
                            recommendations.add("Buy ${topItem.shortName} vs ${enemyHeroes.first()}")
                        }
                    }
                    com.mlbb.oracle.data.EquipmentCategory.MAGIC -> {
                        if (topItem.name == "Necklace of Durance") {
                            recommendations.add("Anti-heal: ${topItem.shortName}")
                        }
                    }
                    else -> {}
                }
            }
        }

        return recommendations.firstOrNull() ?: ""
    }

    private fun updateMyHero(
        name: String, kills: Int, deaths: Int, assists: Int,
        gold: Int, damage: Int, cs: Int, items: String, gameTime: String
    ) {
        scope.launch {
            overlayView?.findViewById<TextView>(R.id.my_hero_name)?.text = "⚔️ $name (Tú)"
            overlayView?.findViewById<TextView>(R.id.my_kda)?.text = "KDA: $kills/$deaths/$assists"
            overlayView?.findViewById<TextView>(R.id.my_gold)?.text = "💰${formatNumber(gold)}"
            overlayView?.findViewById<TextView>(R.id.my_damage)?.text = "⚔️${formatNumber(damage)} DMG"
            overlayView?.findViewById<TextView>(R.id.my_cs)?.text = "🌾$cs CS"
            overlayView?.findViewById<TextView>(R.id.my_items)?.text = items
            overlayView?.findViewById<TextView>(R.id.timer_text)?.text = gameTime
        }
    }

    private fun updateEnemies(
        names: Array<String>, kills: Array<String>, deaths: Array<String>,
        gold: Array<String>, items: Array<String>
    ) {
        scope.launch {
            val rows = listOf(R.id.enemy1_row, R.id.enemy2_row, R.id.enemy3_row, R.id.enemy4_row, R.id.enemy5_row)
            val nameIds = listOf(R.id.enemy1_name, R.id.enemy2_name, R.id.enemy3_name, R.id.enemy4_name, R.id.enemy5_name)
            val kdaIds = listOf(R.id.enemy1_kda, R.id.enemy2_kda, R.id.enemy3_kda, R.id.enemy4_kda, R.id.enemy5_kda)
            val goldIds = listOf(R.id.enemy1_gold, R.id.enemy2_gold, R.id.enemy3_gold, R.id.enemy4_gold, R.id.enemy5_gold)
            val itemIds = listOf(R.id.enemy1_items, R.id.enemy2_items, R.id.enemy3_items, R.id.enemy4_items, R.id.enemy5_items)

            for (i in 0 until minOf(5, names.size)) {
                overlayView?.findViewById<TextView>(nameIds[i])?.text = names[i]
                overlayView?.findViewById<TextView>(kdaIds[i])?.text = "${kills.getOrElse(i) { "0" }}/${deaths.getOrElse(i) { "0" }}"
                overlayView?.findViewById<TextView>(goldIds[i])?.text = gold.getOrElse(i) { "0" }
                overlayView?.findViewById<TextView>(itemIds[i])?.text = items.getOrElse(i) { "" }
            }
        }
    }

    private fun updateSuggestion(suggestion: String) {
        scope.launch {
            overlayView?.findViewById<TextView>(R.id.suggestion_text)?.text = suggestion
        }
    }

    private fun updateTeamFightPrediction(prediction: String) {
        teamFightPrediction = prediction
    }

    private fun updateNextObjective(objective: String) {
        nextObjective = objective
    }

    private fun updateEquipmentRecommendation(recommendation: String) {
        equipmentRecommendation = recommendation
    }

    private fun updateNexusIndicator() {
        scope.launch {
            overlayView?.findViewById<TextView>(R.id.nexus_indicator)?.text =
                if (isInNexus) " 🟢" else " 🔴"
        }
    }

    private fun formatNumber(num: Int): String {
        return when {
            num >= 1000 -> String.format("%.1fK", num / 1000.0)
            else -> num.toString()
        }
    }
}