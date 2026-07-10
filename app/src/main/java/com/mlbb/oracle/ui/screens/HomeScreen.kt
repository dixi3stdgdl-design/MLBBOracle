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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mlbb.oracle.service.OracleAccessibilityService
import com.mlbb.oracle.service.GameBoosterService
import com.mlbb.oracle.service.OverlayService
import com.mlbb.oracle.ui.theme.OracleGold

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    var isServiceRunning by remember { mutableStateOf(false) }
    var hasOverlayPermission by remember { mutableStateOf(Settings.canDrawOverlays(context)) }
    var isBoosterEnabled by remember { mutableStateOf(GameBoosterService.isBoosterEnabled) }
    var isGameActive by remember { mutableStateOf(GameBoosterService.isGameActive) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Hero Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            OracleGold.copy(alpha = 0.3f),
                            Color.Transparent
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("🔮", fontSize = 64.sp)
                Text(
                    "MLBB Oracle",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = OracleGold
                )
                Text(
                    "Your Game Intelligence",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Game Booster Toggle Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = if (isBoosterEnabled) OracleGold.copy(alpha = 0.15f) 
                                 else MaterialTheme.colorScheme.surface
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("⚡", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Game Booster",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        if (isGameActive) "MLBB detected - Overlay active"
                        else if (isBoosterEnabled) "Waiting for MLBB..."
                        else "Booster disabled",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
                
                Switch(
                    checked = isBoosterEnabled,
                    onCheckedChange = { 
                        isBoosterEnabled = it
                        // Send toggle to GameBoosterService
                        val intent = Intent(context, GameBoosterService::class.java).apply {
                            action = "com.mlbb.oracle.BOOSTER_TOGGLE"
                            putExtra("enabled", it)
                        }
                        context.startService(intent)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = OracleGold,
                        checkedTrackColor = OracleGold.copy(alpha = 0.5f)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Service Status Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Service Status",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                StatusRow(
                    label = "Accessibility Service",
                    isActive = isServiceRunning,
                    onClick = {
                        context.startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
                    }
                )

                StatusRow(
                    label = "Overlay Permission",
                    isActive = hasOverlayPermission,
                    onClick = {
                        val intent = Intent(
                            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            android.net.Uri.parse("package:${context.packageName}")
                        )
                        context.startActivity(intent)
                    }
                )

                StatusRow(
                    label = "Game Booster",
                    isActive = isBoosterEnabled,
                    onClick = { }
                )

                StatusRow(
                    label = "MLBB Detected",
                    isActive = isGameActive,
                    onClick = { }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Quick Actions
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Quick Actions",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ActionButton(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.PlayArrow,
                        label = "Start",
                        enabled = isServiceRunning && hasOverlayPermission && isBoosterEnabled,
                        onClick = {
                            context.startForegroundService(
                                Intent(context, OverlayService::class.java)
                            )
                        }
                    )
                    ActionButton(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Close,
                        label = "Stop",
                        enabled = isServiceRunning,
                        onClick = {
                            context.stopService(
                                Intent(context, OverlayService::class.java)
                            )
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // How it works
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "💡 How it works",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "1. Enable Game Booster toggle above\n" +
                    "2. Enable Accessibility Service\n" +
                    "3. Grant Overlay Permission\n" +
                    "4. Open MLBB from Game Booster\n" +
                    "5. Oracle activates automatically!",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
fun StatusRow(
    label: String,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium)
        if (onClick != {}) {
            TextButton(onClick = onClick) {
                Icon(
                    if (isActive) Icons.Default.CheckCircle else Icons.Default.Warning,
                    contentDescription = null,
                    tint = if (isActive) MaterialTheme.colorScheme.tertiary
                           else MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    if (isActive) "Active" else "Inactive",
                    color = if (isActive) MaterialTheme.colorScheme.tertiary
                            else MaterialTheme.colorScheme.error
                )
            }
        } else {
            Icon(
                if (isActive) Icons.Default.CheckCircle else Icons.Default.Close,
                contentDescription = null,
                tint = if (isActive) MaterialTheme.colorScheme.tertiary
                       else MaterialTheme.colorScheme.error,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Icon(icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(label)
    }
}
