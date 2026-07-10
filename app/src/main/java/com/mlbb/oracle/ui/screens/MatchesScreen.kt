package com.mlbb.oracle.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mlbb.oracle.ui.theme.OracleGold

data class Match(
    val id: String,
    val hero: String,
    val result: String,
    val kda: String,
    val duration: String,
    val gold: Int,
    val damage: Int
)

@Composable
fun MatchesScreen() {
    val matches = remember {
        listOf(
            Match("1", "Granger", "WIN", "8/2/5", "12:34", 12450, 75000),
            Match("2", "Fanny", "LOSE", "3/5/2", "15:20", 9800, 45000),
            Match("3", "Chou", "WIN", "5/1/8", "10:45", 11200, 52000),
            Match("4", "Kagura", "WIN", "12/0/6", "18:30", 15600, 98000),
            Match("5", "Miya", "LOSE", "2/4/3", "14:10", 8900, 38000),
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                "Match History",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(matches) { match ->
            MatchCard(match)
        }
    }
}

@Composable
fun MatchCard(match: Match) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Result Badge
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = if (match.result == "WIN") Color(0xFF4CAF50)
                               else Color(0xFFF44336),
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    match.result,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Match Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    match.hero,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "KDA: ${match.kda} • ${match.duration}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }

            // Stats
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    "💰 ${formatNumber(match.gold)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = OracleGold
                )
                Text(
                    "⚔️ ${formatNumber(match.damage)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFFF5722)
                )
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
