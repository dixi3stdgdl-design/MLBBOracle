package com.mlbb.oracle.ui.screens

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mlbb.oracle.ui.theme.OracleGold

@Composable
fun SettingsScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Settings",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        // Permissions Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Permissions",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))

                SettingsItem(
                    icon = Icons.Default.Settings,
                    title = "Accessibility Service",
                    subtitle = "Required for screen capture",
                    onClick = {
                        context.startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
                    }
                )

                SettingsItem(
                    icon = Icons.Default.List,
                    title = "Overlay Permission",
                    subtitle = "Required for floating window",
                    onClick = {
                        val intent = Intent(
                            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            android.net.Uri.parse("package:${context.packageName}")
                        )
                        context.startActivity(intent)
                    }
                )
            }
        }

        // Capture Settings
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Capture Settings",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))

                var captureInterval by remember { mutableStateOf(500f) }
                var showOverlay by remember { mutableStateOf(true) }

                Text("Capture Interval: ${captureInterval.toInt()}ms")
                Slider(
                    value = captureInterval,
                    onValueChange = { captureInterval = it },
                    valueRange = 100f..2000f,
                    steps = 9,
                    colors = SliderDefaults.colors(
                        thumbColor = OracleGold,
                        activeTrackColor = OracleGold
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Show Overlay")
                    Switch(
                        checked = showOverlay,
                        onCheckedChange = { showOverlay = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = OracleGold,
                            checkedTrackColor = OracleGold.copy(alpha = 0.5f)
                        )
                    )
                }
            }
        }

        // AI Settings
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "AI Settings",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))

                var confidenceThreshold by remember { mutableStateOf(0.7f) }

                Text("Confidence Threshold: ${String.format("%.0f", confidenceThreshold * 100)}%")
                Slider(
                    value = confidenceThreshold,
                    onValueChange = { confidenceThreshold = it },
                    valueRange = 0.5f..0.95f,
                    steps = 4,
                    colors = SliderDefaults.colors(
                        thumbColor = OracleGold,
                        activeTrackColor = OracleGold
                    )
                )
            }
        }

        // About
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "About",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "MLBB Oracle v1.0.0\n" +
                    "Your personal game intelligence assistant.\n" +
                    "Non-intrusive screen capture + AI analysis.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = OracleGold
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyMedium)
            Text(
                subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
        IconButton(onClick = onClick) {
            Icon(
                Icons.Default.ArrowForward,
                contentDescription = "Open",
                tint = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}
