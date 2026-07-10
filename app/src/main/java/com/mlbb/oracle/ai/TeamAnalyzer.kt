package com.mlbb.oracle.ai

import com.mlbb.oracle.data.HeroDatabase
import com.mlbb.oracle.data.HeroData
import com.mlbb.oracle.data.HeroRole
import com.mlbb.oracle.data.PowerSpike

/**
 * Analyzes team composition, predicts outcomes, and suggests actions.
 * Used by OverlayService to generate real-time tips.
 */
class TeamAnalyzer {

    data class TeamState(
        val myHero: String = "",
        val myGold: Int = 0,
        val myKills: Int = 0,
        val myDeaths: Int = 0,
        val myAssists: Int = 0,
        val myDamage: Int = 0,
        val myCS: Int = 0,
        val enemyHeroes: List<String> = emptyList(),
        val enemyGold: List<Int> = emptyList(),
        val gameTime: String = "00:00"
    )

    data class TeamComposition(
        val roleCount: Map<HeroRole, Int>,
        val missingRoles: List<HeroRole>,
        val balanceScore: Double,
        val earlyGamePower: Double,
        val midGamePower: Double,
        val lateGamePower: Double,
        val summary: String
    )

    data class WinPrediction(
        val chance: Double,
        val factors: List<String>,
        val recommendation: String
    )

    data class ActionSuggestion(
        val objective: String,
        val teamFight: String,
        val equipment: String
    )

    data class CounterSuggestion(
        val hero: String,
        val counterFor: String,
        val reason: String
    )

    /**
     * Analyze team composition from a list of hero names.
     * Returns role distribution, balance score, and power spike analysis.
     */
    fun analyzeTeamComposition(picks: List<String>): TeamComposition {
        val heroes = picks.mapNotNull { HeroDatabase.getHeroByName(it) }

        val roleCount = mutableMapOf<HeroRole, Int>()
        HeroRole.entries.forEach { role -> roleCount[role] = 0 }
        heroes.forEach { hero ->
            roleCount[hero.role] = (roleCount[hero.role] ?: 0) + 1
        }

        val missingRoles = HeroRole.entries.filter { (roleCount[it] ?: 0) == 0 }

        // Power spike averages
        val earlyAvg = heroes.map { it.powerSpike.early }.average().let { if (it.isNaN()) 5.0 else it }
        val midAvg = heroes.map { it.powerSpike.mid }.average().let { if (it.isNaN()) 5.0 else it }
        val lateAvg = heroes.map { it.powerSpike.late }.average().let { if (it.isNaN()) 5.0 else it }

        // Balance score: penalize missing roles, reward diversity
        var balance = 100.0
        missingRoles.forEach { role ->
            when (role) {
                HeroRole.TANK -> balance -= 20
                HeroRole.SUPPORT -> balance -= 15
                HeroRole.MARKSMAN -> balance -= 10
                HeroRole.MAGE -> balance -= 10
                HeroRole.ASSASSIN -> balance -= 5
                HeroRole.FIGHTER -> balance -= 5
            }
        }
        // Bonus for having 3+ distinct roles
        val distinctRoles = roleCount.count { it.value > 0 }
        balance += (distinctRoles - 3) * 5.0

        balance = balance.coerceIn(0.0, 100.0)

        val summary = buildString {
            if (missingRoles.isEmpty()) {
                append("Balanced comp")
            } else {
                append("Missing: ${missingRoles.joinToString { it.displayName }}")
            }
            when {
                earlyAvg > 7 -> append(" | Strong early")
                lateAvg > 7 -> append(" | Scale late")
                midAvg > 7 -> append(" | Peak mid")
            }
        }

        return TeamComposition(
            roleCount = roleCount,
            missingRoles = missingRoles,
            balanceScore = balance,
            earlyGamePower = earlyAvg,
            midGamePower = midAvg,
            lateGamePower = lateAvg,
            summary = summary
        )
    }

    /**
     * Predict win chance based on gold lead, hero matchups, and game time.
     */
    fun predictWinChance(state: TeamState): WinPrediction {
        if (state.enemyHeroes.isEmpty() || state.myHero.isEmpty()) {
            return WinPrediction(50.0, listOf("Insufficient data"), "Gather info")
        }

        val factors = mutableListOf<String>()
        var chance = 50.0

        // Gold advantage factor
        val avgEnemyGold = if (state.enemyGold.isNotEmpty()) state.enemyGold.average() else 0.0
        val goldDiff = state.myGold - avgEnemyGold

        when {
            goldDiff > 3000 -> { chance += 20; factors.add("Huge gold lead") }
            goldDiff > 1500 -> { chance += 12; factors.add("Gold lead") }
            goldDiff > 500 -> { chance += 5; factors.add("Slight gold lead") }
            goldDiff < -3000 -> { chance -= 20; factors.add("Gold deficit") }
            goldDiff < -1500 -> { chance -= 12; factors.add("Falling behind") }
            goldDiff < -500 -> { chance -= 5; factors.add("Slight deficit") }
        }

        // KDA factor
        val kda = if (state.myDeaths > 0) {
            (state.myKills + state.myAssists).toDouble() / state.myDeaths
        } else {
            (state.myKills + state.myAssists).toDouble()
        }
        when {
            kda > 5 -> { chance += 15; factors.add("Dominating KDA") }
            kda > 3 -> { chance += 8; factors.add("Good KDA") }
            kda < 1 -> { chance -= 12; factors.add("Poor KDA") }
        }

        // Hero matchup factor
        val myHeroData = HeroDatabase.getHeroByName(state.myHero)
        if (myHeroData != null) {
            val counters = myHeroData.counters
            val enemyCounters = state.enemyHeroes.filter { it in counters }
            if (enemyCounters.isNotEmpty()) {
                chance -= enemyCounters.size * 5.0
                factors.add("Countered by ${enemyCounters.first()}")
            }

            // Power spike vs game time
            val timeParts = state.gameTime.split(":")
            val minutes = timeParts.getOrNull(0)?.toIntOrNull() ?: 0
            val phase = when {
                minutes < 5 -> "early"
                minutes < 15 -> "mid"
                else -> "late"
            }
            val myPower = when (phase) {
                "early" -> myHeroData.powerSpike.early
                "mid" -> myHeroData.powerSpike.mid
                else -> myHeroData.powerSpike.late
            }
            when {
                myPower >= 8 -> { chance += 10; factors.add("At power spike") }
                myPower <= 3 -> { chance -= 8; factors.add("Before power spike") }
            }
        }

        chance = chance.coerceIn(5.0, 95.0)

        val recommendation = when {
            chance > 70 -> "Press the advantage"
            chance > 55 -> "Play safe, look for picks"
            chance > 45 -> "Farm and wait for mistakes"
            chance > 30 -> "Avoid team fights"
            else -> "Split push and stall"
        }

        return WinPrediction(chance, factors, recommendation)
    }

    /**
     * Suggest the next objective based on game state.
     */
    fun suggestNextAction(state: TeamState): ActionSuggestion {
        val timeParts = state.gameTime.split(":")
        val minutes = timeParts.getOrNull(0)?.toIntOrNull() ?: 0
        val seconds = timeParts.getOrNull(1)?.toIntOrNull() ?: 0
        val totalSeconds = minutes * 60 + seconds

        val avgEnemyGold = if (state.enemyGold.isNotEmpty()) state.enemyGold.average() else 0.0
        val goldDiff = state.myGold - avgEnemyGold

        // Objective timing
        val objective = when {
            totalSeconds < 120 -> "Secure first buff"
            totalSeconds < 300 -> "Gank gold lane"
            totalSeconds in 475..505 -> "Turtle spawning!"
            totalSeconds in 1075..1105 -> "Lord spawning!"
            totalSeconds in 1435..1465 -> "Lord spawning!"
            totalSeconds > 1080 && goldDiff > 0 -> "Group for Lord"
            totalSeconds > 480 -> "Pressure towers"
            else -> "Farm and rotate"
        }

        // Team fight recommendation
        val teamFight = when {
            goldDiff > 2000 -> "Engage fights"
            goldDiff > 1000 -> "Look for picks"
            goldDiff > 0 -> "Play cautiously"
            goldDiff > -1000 -> "Avoid 5v5"
            else -> "Farm safely, split push"
        }

        // Equipment recommendation based on enemies
        val equipment = if (state.enemyHeroes.isNotEmpty()) {
            val recommendations = mutableListOf<String>()

            val hasHealer = state.enemyHeroes.any { hero ->
                val data = HeroDatabase.getHeroByName(hero)
                data?.role == HeroRole.SUPPORT
            }
            if (hasHealer) recommendations.add("Buy anti-heal")

            val hasMage = state.enemyHeroes.any { hero ->
                val data = HeroDatabase.getHeroByName(hero)
                data?.role == HeroRole.MAGE
            }
            if (hasMage && goldDiff < 500) recommendations.add("Magic defense")

            val hasAssassin = state.enemyHeroes.any { hero ->
                val data = HeroDatabase.getHeroByName(hero)
                data?.role == HeroRole.ASSASSIN
            }
            if (hasAssassin) recommendations.add("Immortality")

            recommendations.firstOrNull() ?: "Follow standard build"
        } else {
            "Build core items"
        }

        return ActionSuggestion(objective, teamFight, equipment)
    }

    /**
     * Suggest counter picks for enemy heroes.
     */
    fun getCounterPicks(enemyHeroes: List<String>): List<CounterSuggestion> {
        val suggestions = mutableListOf<CounterSuggestion>()

        enemyHeroes.forEach { enemyName ->
            val enemyHero = HeroDatabase.getHeroByName(enemyName) ?: return@forEach

            // Find heroes that counter this enemy
            val counters = HeroDatabase.getAllHeroes().filter { hero ->
                hero.counters.any { counterName ->
                    counterName.equals(enemyName, ignoreCase = true)
                }
            }

            // Pick the best counter (highest tier, highest ban priority)
            val bestCounter = counters.maxByOrNull {
                val tierBonus = when (it.tier) { "S" -> 3; "A" -> 2; "B" -> 1; else -> 0 }
                it.banPriority + tierBonus * 10
            }

            if (bestCounter != null) {
                suggestions.add(
                    CounterSuggestion(
                        hero = bestCounter.name,
                        counterFor = enemyName,
                        reason = "${bestCounter.tier}-tier ${bestCounter.role.displayName} counters ${enemyHero.role.displayName}"
                    )
                )
            }
        }

        return suggestions
    }

    /**
     * Get a compact text summary for the overlay suggestion panel.
     */
    fun getSuggestionText(state: TeamState): String {
        val lines = mutableListOf<String>()

        val action = suggestNextAction(state)
        lines.add(action.objective)
        lines.add(action.teamFight)
        if (action.equipment.isNotEmpty() && action.equipment != "Follow standard build") {
            lines.add(action.equipment)
        }

        // Add matchup warning if countered
        if (state.enemyHeroes.isNotEmpty() && state.myHero.isNotEmpty()) {
            val myHero = HeroDatabase.getHeroByName(state.myHero)
            val enemyCounters = state.enemyHeroes.filter {
                it in (myHero?.counters ?: emptyList())
            }
            if (enemyCounters.isNotEmpty()) {
                lines.add("Watch: ${enemyCounters.joinToString()}")
            }
        }

        return lines.joinToString("\n")
    }
}
