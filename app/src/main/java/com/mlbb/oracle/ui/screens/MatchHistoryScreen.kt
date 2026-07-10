package com.mlbb.oracle.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mlbb.oracle.ui.theme.OracleGold
import com.mlbb.oracle.ui.theme.OracleDark
import com.mlbb.oracle.ui.theme.OracleSuccess
import com.mlbb.oracle.ui.theme.OracleError

data class DetailedMatch(
    val id: String,
    val hero: String,
    val heroRole: String,
    val result: String,
    val kills: Int,
    val deaths: Int,
    val assists: Int,
    val duration: String,
    val gold: Int,
    val damageDealt: Int,
    val damageTaken: Int,
    val healing: Int,
    val cs: Int,
    val teamfight: Int,
    val items: List<String>,
    val goldTimeline: List<Pair<String, Int>>,
    val damageSources: Map<String, Int>,
    val date: String,
    val mode: String,
    val isMVP: Boolean
)

object MatchHistoryData {

    val matchHistory = listOf(
        DetailedMatch(
            id = "m1", hero = "Granger", heroRole = "Marksman", result = "WIN",
            kills = 12, deaths = 2, assists = 8, duration = "14:32",
            gold = 14250, damageDealt = 98500, damageTaken = 15200, healing = 0,
            cs = 186, teamfight = 75, items = listOf("BoD", "EB", "WB", "MR", "BF", "Imm"),
            goldTimeline = listOf("2m" to 1200, "4m" to 3400, "6m" to 5800, "8m" to 8200, "10m" to 10500, "12m" to 12800, "14m" to 14250),
            damageSources = mapOf("Basic" to 42000, "S1" to 28000, "S2" to 12000, "Ult" to 16500),
            date = "2026-07-08", mode = "Ranked", isMVP = true
        ),
        DetailedMatch(
            id = "m2", hero = "Fanny", heroRole = "Assassin", result = "LOSE",
            kills = 5, deaths = 8, assists = 3, duration = "18:45",
            gold = 11200, damageDealt = 52000, damageTaken = 28000, healing = 0,
            cs = 142, teamfight = 45, items = listOf("BoD", "EB", "WB", "MR", "Imm", "RG"),
            goldTimeline = listOf("2m" to 900, "4m" to 2400, "6m" to 4200, "8m" to 5800, "10m" to 7200, "12m" to 8500, "14m" to 9800, "16m" to 10500, "18m" to 11200),
            damageSources = mapOf("Basic" to 18000, "S1" to 12000, "S2" to 8000, "Ult" to 14000),
            date = "2026-07-07", mode = "Ranked", isMVP = false
        ),
        DetailedMatch(
            id = "m3", hero = "Chou", heroRole = "Fighter", result = "WIN",
            kills = 8, deaths = 1, assists = 12, duration = "12:18",
            gold = 12800, damageDealt = 68000, damageTaken = 22000, healing = 0,
            cs = 168, teamfight = 85, items = listOf("BoD", "EB", "DI", "WB", "MR", "Imm"),
            goldTimeline = listOf("2m" to 1400, "4m" to 3600, "6m" to 5800, "8m" to 8000, "10m" to 10200, "12m" to 12800),
            damageSources = mapOf("Basic" to 32000, "S1" to 18000, "S2" to 8000, "Ult" to 10000),
            date = "2026-07-07", mode = "Ranked", isMVP = true
        ),
        DetailedMatch(
            id = "m4", hero = "Kagura", heroRole = "Mage", result = "WIN",
            kills = 15, deaths = 0, assists = 10, duration = "16:52",
            gold = 16200, damageDealt = 125000, damageTaken = 8500, healing = 0,
            cs = 198, teamfight = 90, items = listOf("LT", "CE", "HC", "DG", "WT", "Imm"),
            goldTimeline = listOf("2m" to 1300, "4m" to 3500, "6m" to 6000, "8m" to 8500, "10m" to 11000, "12m" to 13200, "14m" to 14800, "16m" to 16200),
            damageSources = mapOf("Basic" to 15000, "S1" to 42000, "S2" to 28000, "Ult" to 40000),
            date = "2026-07-06", mode = "Ranked", isMVP = true
        ),
        DetailedMatch(
            id = "m5", hero = "Miya", heroRole = "Marksman", result = "LOSE",
            kills = 4, deaths = 7, assists = 5, duration = "20:15",
            gold = 10500, damageDealt = 45000, damageTaken = 32000, healing = 0,
            cs = 156, teamfight = 35, items = listOf("WT", "BF", "DHS", "MR", "RG", "Imm"),
            goldTimeline = listOf("2m" to 800, "4m" to 2200, "6m" to 3800, "8m" to 5200, "10m" to 6800, "12m" to 7800, "14m" to 8500, "16m" to 9200, "18m" to 10000, "20m" to 10500),
            damageSources = mapOf("Basic" to 35000, "S1" to 5000, "S2" to 3000, "Ult" to 2000),
            date = "2026-07-06", mode = "Ranked", isMVP = false
        ),
        DetailedMatch(
            id = "m6", hero = "Atlas", heroRole = "Tank", result = "WIN",
            kills = 2, deaths = 3, assists = 18, duration = "15:40",
            gold = 9800, damageDealt = 35000, damageTaken = 48000, healing = 0,
            cs = 85, teamfight = 95, items = listOf("TB", "DI", "AS", "AC", "Imm", "BA"),
            goldTimeline = listOf("2m" to 1000, "4m" to 2400, "6m" to 3800, "8m" to 5200, "10m" to 6500, "12m" to 7800, "14m" to 9000, "15m" to 9800),
            damageSources = mapOf("Basic" to 8000, "S1" to 12000, "S2" to 5000, "Ult" to 10000),
            date = "2026-07-05", mode = "Ranked", isMVP = false
        ),
        DetailedMatch(
            id = "m7", hero = "Gusion", heroRole = "Assassin", result = "WIN",
            kills = 10, deaths = 3, assists = 7, duration = "13:25",
            gold = 13500, damageDealt = 82000, damageTaken = 18000, healing = 0,
            cs = 158, teamfight = 70, items = listOf("CE", "LT", "HC", "DG", "WT", "Imm"),
            goldTimeline = listOf("2m" to 1300, "4m" to 3500, "6m" to 5800, "8m" to 8200, "10m" to 10500, "12m" to 12200, "13m" to 13500),
            damageSources = mapOf("Basic" to 22000, "S1" to 18000, "S2" to 25000, "Ult" to 17000),
            date = "2026-07-05", mode = "Ranked", isMVP = true
        ),
        DetailedMatch(
            id = "m8", hero = "Angela", heroRole = "Support", result = "WIN",
            kills = 3, deaths = 2, assists = 22, duration = "16:10",
            gold = 8200, damageDealt = 18000, damageTaken = 25000, healing = 45000,
            cs = 45, teamfight = 88, items = listOf("TB", "CR", "FT", "AS", "Imm", "DI"),
            goldTimeline = listOf("2m" to 800, "4m" to 2000, "6m" to 3200, "8m" to 4400, "10m" to 5500, "12m" to 6500, "14m" to 7500, "16m" to 8200),
            damageSources = mapOf("Basic" to 5000, "S1" to 8000, "S2" to 3000, "Ult" to 2000),
            date = "2026-07-04", mode = "Ranked", isMVP = false
        ),
        DetailedMatch(
            id = "m9", hero = "Wanwan", heroRole = "Marksman", result = "LOSE",
            kills = 6, deaths = 5, assists = 4, duration = "19:30",
            gold = 11800, damageDealt = 62000, damageTaken = 28000, healing = 0,
            cs = 172, teamfight = 55, items = listOf("DHS", "BF", "WT", "MR", "Imm", "RG"),
            goldTimeline = listOf("2m" to 1100, "4m" to 2800, "6m" to 4500, "8m" to 6200, "10m" to 7800, "12m" to 9200, "14m" to 10200, "16m" to 11000, "18m" to 11500, "19m" to 11800),
            damageSources = mapOf("Basic" to 42000, "S1" to 8000, "S2" to 5000, "Ult" to 7000),
            date = "2026-07-04", mode = "Ranked", isMVP = false
        ),
        DetailedMatch(
            id = "m10", hero = "Ling", heroRole = "Assassin", result = "WIN",
            kills = 9, deaths = 2, assists = 6, duration = "14:05",
            gold = 14800, damageDealt = 88000, damageTaken = 12000, healing = 0,
            cs = 192, teamfight = 72, items = listOf("BoD", "EB", "WB", "BH", "MR", "Imm"),
            goldTimeline = listOf("2m" to 1400, "4m" to 3800, "6m" to 6200, "8m" to 8600, "10m" to 11000, "12m" to 13200, "14m" to 14800),
            damageSources = mapOf("Basic" to 38000, "S1" to 22000, "S2" to 18000, "Ult" to 10000),
            date = "2026-07-03", mode = "Ranked", isMVP = true
        ),
        DetailedMatch(
            id = "m11", hero = "Tigreal", heroRole = "Tank", result = "WIN",
            kills = 1, deaths = 4, assists = 20, duration = "17:20",
            gold = 8500, damageDealt = 22000, damageTaken = 52000, healing = 0,
            cs = 65, teamfight = 92, items = listOf("TB", "DI", "AS", "AC", "Imm", "BA"),
            goldTimeline = listOf("2m" to 800, "4m" to 2000, "6m" to 3200, "8m" to 4400, "10m" to 5500, "12m" to 6500, "14m" to 7500, "16m" to 8200, "17m" to 8500),
            damageSources = mapOf("Basic" to 6000, "S1" to 8000, "S2" to 4000, "Ult" to 4000),
            date = "2026-07-03", mode = "Ranked", isMVP = false
        ),
        DetailedMatch(
            id = "m12", hero = "Eudora", heroRole = "Mage", result = "WIN",
            kills = 11, deaths = 1, assists = 9, duration = "12:45",
            gold = 13200, damageDealt = 95000, damageTaken = 8000, healing = 0,
            cs = 162, teamfight = 80, items = listOf("LT", "HC", "DG", "FT", "WT", "Imm"),
            goldTimeline = listOf("2m" to 1200, "4m" to 3200, "6m" to 5400, "8m" to 7600, "10m" to 9800, "12m" to 12000, "12m" to 13200),
            damageSources = mapOf("Basic" to 12000, "S1" to 28000, "S2" to 22000, "Ult" to 33000),
            date = "2026-07-02", mode = "Ranked", isMVP = true
        ),
        DetailedMatch(
            id = "m13", hero = "Yu Zhong", heroRole = "Fighter", result = "LOSE",
            kills = 3, deaths = 6, assists = 8, duration = "22:10",
            gold = 10800, damageDealt = 48000, damageTaken = 55000, healing = 12000,
            cs = 145, teamfight = 50, items = listOf("WB", "DI", "QW", "Imm", "AS", "RA"),
            goldTimeline = listOf("2m" to 900, "4m" to 2200, "6m" to 3600, "8m" to 4800, "10m" to 6000, "12m" to 7200, "14m" to 8200, "16m" to 9000, "18m" to 9800, "20m" to 10500, "22m" to 10800),
            damageSources = mapOf("Basic" to 22000, "S1" to 12000, "S2" to 8000, "Ult" to 6000),
            date = "2026-07-02", mode = "Ranked", isMVP = false
        ),
        DetailedMatch(
            id = "m14", hero = "Pharsa", heroRole = "Mage", result = "WIN",
            kills = 8, deaths = 2, assists = 14, duration = "15:55",
            gold = 12500, damageDealt = 88000, damageTaken = 10000, healing = 0,
            cs = 178, teamfight = 78, items = listOf("AB", "LT", "HC", "DG", "FT", "WT"),
            goldTimeline = listOf("2m" to 1100, "4m" to 3000, "6m" to 5000, "8m" to 7000, "10m" to 8800, "12m" to 10500, "14m" to 11800, "15m" to 12500),
            damageSources = mapOf("Basic" to 8000, "S1" to 22000, "S2" to 18000, "Ult" to 40000),
            date = "2026-07-01", mode = "Ranked", isMVP = false
        ),
        DetailedMatch(
            id = "m15", hero = "Claude", heroRole = "Marksman", result = "WIN",
            kills = 7, deaths = 3, assists = 10, duration = "16:30",
            gold = 13800, damageDealt = 72000, damageTaken = 20000, healing = 0,
            cs = 195, teamfight = 72, items = listOf("SB", "DHS", "GS", "CS", "MR", "Imm"),
            goldTimeline = listOf("2m" to 1200, "4m" to 3200, "6m" to 5400, "8m" to 7600, "10m" to 9600, "12m" to 11200, "14m" to 12600, "16m" to 13800),
            damageSources = mapOf("Basic" to 32000, "S1" to 12000, "S2" to 8000, "Ult" to 20000),
            date = "2026-07-01", mode = "Ranked", isMVP = false
        ),
        DetailedMatch(
            id = "m16", hero = "Lunox", heroRole = "Mage", result = "LOSE",
            kills = 4, deaths = 5, assists = 6, duration = "21:45",
            gold = 10200, damageDealt = 58000, damageTaken = 15000, healing = 8000,
            cs = 148, teamfight = 52, items = listOf("AB", "CE", "HC", "DG", "WT", "Imm"),
            goldTimeline = listOf("2m" to 900, "4m" to 2200, "6m" to 3600, "8m" to 5000, "10m" to 6200, "12m" to 7200, "14m" to 8000, "16m" to 8800, "18m" to 9400, "20m" to 10000, "21m" to 10200),
            damageSources = mapOf("Basic" to 12000, "S1" to 18000, "S2" to 15000, "Ult" to 13000),
            date = "2026-06-30", mode = "Ranked", isMVP = false
        ),
        DetailedMatch(
            id = "m17", hero = "Esmeralda", heroRole = "Fighter", result = "WIN",
            kills = 6, deaths = 2, assists = 14, duration = "18:20",
            gold = 11500, damageDealt = 55000, damageTaken = 38000, healing = 18000,
            cs = 165, teamfight = 78, items = listOf("AB", "DI", "QW", "HC", "Imm", "AS"),
            goldTimeline = listOf("2m" to 1000, "4m" to 2600, "6m" to 4200, "8m" to 5800, "10m" to 7200, "12m" to 8500, "14m" to 9600, "16m" to 10600, "18m" to 11500),
            damageSources = mapOf("Basic" to 20000, "S1" to 15000, "S2" to 12000, "Ult" to 8000),
            date = "2026-06-30", mode = "Ranked", isMVP = false
        ),
        DetailedMatch(
            id = "m18", hero = "Khufra", heroRole = "Tank", result = "WIN",
            kills = 0, deaths = 2, assists = 24, duration = "19:15",
            gold = 9200, damageDealt = 28000, damageTaken = 58000, healing = 0,
            cs = 55, teamfight = 95, items = listOf("TB", "DI", "AS", "AC", "Imm", "BA"),
            goldTimeline = listOf("2m" to 800, "4m" to 2000, "6m" to 3200, "8m" to 4400, "10m" to 5500, "12m" to 6500, "14m" to 7400, "16m" to 8200, "18m" to 8800, "19m" to 9200),
            damageSources = mapOf("Basic" to 6000, "S1" to 10000, "S2" to 5000, "Ult" to 7000),
            date = "2026-06-29", mode = "Ranked", isMVP = true
        ),
        DetailedMatch(
            id = "m19", hero = "Moskov", heroRole = "Marksman", result = "LOSE",
            kills = 5, deaths = 6, assists = 3, duration = "17:50",
            gold = 10800, damageDealt = 52000, damageTaken = 28000, healing = 0,
            cs = 162, teamfight = 42, items = listOf("SB", "BF", "DHS", "MR", "RG", "Imm"),
            goldTimeline = listOf("2m" to 1000, "4m" to 2600, "6m" to 4200, "8m" to 5600, "10m" to 7000, "12m" to 8200, "14m" to 9200, "16m" to 10200, "17m" to 10800),
            damageSources = mapOf("Basic" to 38000, "S1" to 6000, "S2" to 4000, "Ult" to 4000),
            date = "2026-06-29", mode = "Ranked", isMVP = false
        ),
        DetailedMatch(
            id = "m20", hero = "Estes", heroRole = "Support", result = "WIN",
            kills = 2, deaths = 1, assists = 28, duration = "20:05",
            gold = 7800, damageDealt = 15000, damageTaken = 22000, healing = 68000,
            cs = 38, teamfight = 92, items = listOf("TB", "CR", "ET", "AS", "Imm", "DI"),
            goldTimeline = listOf("2m" to 700, "4m" to 1800, "6m" to 2800, "8m" to 3800, "10m" to 4800, "12m" to 5600, "14m" to 6400, "16m" to 7000, "18m" to 7500, "20m" to 7800),
            damageSources = mapOf("Basic" to 3000, "S1" to 6000, "S2" to 4000, "Ult" to 2000),
            date = "2026-06-28", mode = "Ranked", isMVP = false
        )
    )

    fun getWinRate(): Float {
        val wins = matchHistory.count { it.result == "WIN" }
        return (wins.toFloat() / matchHistory.size) * 100
    }

    fun getWinStreak(): Int {
        var streak = 0
        for (match in matchHistory.reversed()) {
            if (match.result == "WIN") streak++ else break
        }
        return streak
    }

    fun getLoseStreak(): Int {
        var streak = 0
        for (match in matchHistory.reversed()) {
            if (match.result == "LOSE") streak++ else break
        }
        return streak
    }

    fun getMVPCount(): Int = matchHistory.count { it.isMVP }

    fun getAverageKDA(): Triple<Float, Float, Float> {
        val avgK = matchHistory.map { it.kills }.average()
        val avgD = matchHistory.map { it.deaths }.average()
        val avgA = matchHistory.map { it.assists }.average()
        return Triple(avgK.toFloat(), avgD.toFloat(), avgA.toFloat())
    }

    fun getHeroPerformance(): Map<String, Triple<Float, Int, Int>> {
        return matchHistory.groupBy { it.hero }.map { (hero, matches) ->
            val wr = (matches.count { it.result == "WIN" }.toFloat() / matches.size) * 100
            val avgKDA = "${String.format("%.1f", matches.map { it.kills }.average())}/${String.format("%.1f", matches.map { it.deaths }.average())}/${String.format("%.1f", matches.map { it.assists }.average())}"
            hero to Triple(wr, matches.size, matches.count { it.result == "WIN" })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchHistoryScreen() {
    val matches = remember { MatchHistoryData.matchHistory }
    var expandedMatch by remember { mutableStateOf<String?>(null) }
    var selectedTab by remember { mutableIntStateOf(0) }

    val winRate = remember { MatchHistoryData.getWinRate() }
    val winStreak = remember { MatchHistoryData.getWinStreak() }
    val loseStreak = remember { MatchHistoryData.getLoseStreak() }
    val mvpCount = remember { MatchHistoryData.getMVPCount() }
    val avgKDA = remember { MatchHistoryData.getAverageKDA() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Header
        item {
            Text(
                "Match History",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }

        // Stats Summary Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = OracleDark),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Performance Overview", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        StatItem("Win Rate", "${String.format("%.1f", winRate)}%", if (winRate >= 50) OracleSuccess else OracleError)
                        StatItem("Win Streak", "$winStreak", OracleSuccess)
                        StatItem("Lose Streak", "$loseStreak", OracleError)
                        StatItem("MVP", "$mvpCount", OracleGold)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        StatItem("Avg K", String.format("%.1f", avgKDA.first), OracleGold)
                        StatItem("Avg D", String.format("%.1f", avgKDA.second), OracleError)
                        StatItem("Avg A", String.format("%.1f", avgKDA.third), OracleSuccess)
                        StatItem("Games", "${matches.size}", MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
        }

        // Tabs
        item {
            TabRow(selectedTabIndex = selectedTab) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, text = { Text("Recent") })
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }, text = { Text("Heroes") })
                Tab(selected = selectedTab == 2, onClick = { selectedTab = 2 }, text = { Text("Streaks") })
            }
        }

        // Match list
        if (selectedTab == 0) {
            items(matches) { match ->
                MatchDetailCard(
                    match = match,
                    isExpanded = expandedMatch == match.id,
                    onToggle = {
                        expandedMatch = if (expandedMatch == match.id) null else match.id
                    }
                )
            }
        } else if (selectedTab == 1) {
            item {
                HeroPerformanceSection()
            }
        } else {
            item {
                StreaksSection()
            }
        }
    }
}

@Composable
fun StatItem(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = color)
        Text(label, style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.7f))
    }
}

@Composable
fun MatchDetailCard(match: DetailedMatch, isExpanded: Boolean, onToggle: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        onClick = onToggle
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Main row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Result badge
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (match.result == "WIN") OracleSuccess else OracleError),
                    contentAlignment = Alignment.Center
                ) {
                    Text(match.result, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Hero info
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(match.hero, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        if (match.isMVP) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .background(OracleGold, RoundedCornerShape(4.dp))
                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                            ) {
                                Text("MVP", fontSize = 8.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    Text("${match.kills}/${match.deaths}/${match.assists} • ${match.duration} • ${match.mode}",
                        style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.7f))
                }

                // Stats
                Column(horizontalAlignment = Alignment.End) {
                    Text("💰 ${formatNumber(match.gold)}", style = MaterialTheme.typography.bodyMedium, color = OracleGold)
                    Text("⚔️ ${formatNumber(match.damageDealt)}", style = MaterialTheme.typography.bodyMedium, color = Color(0xFFFF5722))
                }

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.7f)
                )
            }

            // Expanded content
            if (isExpanded) {
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                Spacer(modifier = Modifier.height(12.dp))

                // Items
                Text("Build", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    match.items.forEach { item ->
                        Box(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(item, fontSize = 9.sp, color = OracleGold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Damage stats
                Text("Damage Breakdown", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text("Dealt: ${formatNumber(match.damageDealt)}", style = MaterialTheme.typography.bodySmall, color = Color(0xFFFF5722))
                        Text("Taken: ${formatNumber(match.damageTaken)}", style = MaterialTheme.typography.bodySmall, color = Color(0xFF2196F3))
                        Text("Healing: ${formatNumber(match.healing)}", style = MaterialTheme.typography.bodySmall, color = Color(0xFF4CAF50))
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("CS: ${match.cs}", style = MaterialTheme.typography.bodySmall)
                        Text("Teamfight: ${match.teamfight}%", style = MaterialTheme.typography.bodySmall)
                        Text("${match.date}", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.5f))
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Gold timeline
                Text("Gold Timeline", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    match.goldTimeline.takeLast(6).forEach { (time, gold) ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(formatNumber(gold), fontSize = 9.sp, color = OracleGold)
                            Text(time, fontSize = 7.sp, color = Color.White.copy(alpha = 0.5f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HeroPerformanceSection() {
    val performance = remember { MatchHistoryData.getHeroPerformance() }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Hero Performance", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            performance.forEach { (hero, data) ->
                val (wr, games, wins) = data
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(hero, modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
                    Text("${wins}/${games}", style = MaterialTheme.typography.bodySmall, modifier = Modifier.width(40.dp))
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.White.copy(alpha = 0.1f))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(wr / 100f)
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (wr >= 50) OracleSuccess else OracleError)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("${String.format("%.0f", wr)}%", style = MaterialTheme.typography.bodySmall,
                        color = if (wr >= 50) OracleSuccess else OracleError, modifier = Modifier.width(40.dp))
                }
            }
        }
    }
}

@Composable
fun StreaksSection() {
    val matches = remember { MatchHistoryData.matchHistory }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Recent Results", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                matches.takeLast(10).forEach { match ->
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(if (match.result == "WIN") OracleSuccess else OracleError),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            match.hero.first().toString(),
                            fontSize = 10.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("${MatchHistoryData.getWinStreak()}", style = MaterialTheme.typography.headlineSmall, color = OracleSuccess, fontWeight = FontWeight.Bold)
                    Text("Win Streak", style = MaterialTheme.typography.labelSmall)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("${MatchHistoryData.getLoseStreak()}", style = MaterialTheme.typography.headlineSmall, color = OracleError, fontWeight = FontWeight.Bold)
                    Text("Lose Streak", style = MaterialTheme.typography.labelSmall)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("${MatchHistoryData.getMVPCount()}", style = MaterialTheme.typography.headlineSmall, color = OracleGold, fontWeight = FontWeight.Bold)
                    Text("MVP Games", style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}

private fun formatNumber(num: Int): String {
    return when {
        num >= 1000 -> String.format("%.1fK", num / 1000.0)
        else -> num.toString()
    }
}
