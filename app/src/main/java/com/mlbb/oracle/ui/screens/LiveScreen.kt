package com.mlbb.oracle.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mlbb.oracle.ui.theme.OracleGold

@Composable
fun LiveScreen() {
    var isActive by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Live Status Indicator
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(
                    color = if (isActive) OracleGold.copy(alpha = 0.2f)
                            else MaterialTheme.colorScheme.surface,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    if (isActive) Icons.Default.Star else Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = if (isActive) OracleGold else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    if (isActive) "LIVE" else "PAUSED",
                    style = MaterialTheme.typography.labelLarge,
                    color = if (isActive) OracleGold else MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Live Stats Grid
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LiveStatCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Star,
                label = "Gold",
                value = "12.4K",
                subtext = "2,340/min"
            )
            LiveStatCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Favorite,
                label = "Damage",
                value = "75K",
                subtext = "5,200/min"
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LiveStatCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Person,
                label = "KDA",
                value = "8/2/5",
                subtext = "Score: 6.5"
            )
            LiveStatCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.List,
                label = "Farm",
                value = "156",
                subtext = "8.2 CS/min"
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Items Row
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    "Current Build",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    repeat(6) { index ->
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(
                                    MaterialTheme.colorScheme.background,
                                    RoundedCornerShape(8.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                if (index < 3) "🗡️" else "🛡️",
                                fontSize = 24.sp
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Control Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = { isActive = !isActive },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isActive) MaterialTheme.colorScheme.error
                                    else OracleGold
                )
            ) {
                Icon(
                    if (isActive) Icons.Default.Close else Icons.Default.PlayArrow,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (isActive) "Pause" else "Start")
            }

            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = { /* Settings */ }
            ) {
                Icon(Icons.Default.Settings, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Overlay")
            }
        }
    }
}

@Composable
fun LiveStatCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    value: String,
    subtext: String
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    icon,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = OracleGold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    label,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
            Text(
                value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                subtext,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}
