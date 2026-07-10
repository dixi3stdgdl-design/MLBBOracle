package com.mlbb.oracle.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mlbb.oracle.data.EquipmentData
import com.mlbb.oracle.data.EquipmentDataStore
import com.mlbb.oracle.ui.theme.OracleGold
import com.mlbb.oracle.ui.theme.OracleDark
import com.mlbb.oracle.ui.theme.OracleSuccess
import com.mlbb.oracle.ui.theme.OracleError

@Composable
fun EquipmentGuideScreen() {
    var selectedCategory by remember { mutableStateOf("All") }
    var expandedItem by remember { mutableStateOf<String?>(null) }

    val categories = listOf("All", "Attack", "Magic", "Defense", "Movement", "Roam", "Component")

    val filteredItems = remember(selectedCategory) {
        if (selectedCategory == "All") {
            EquipmentDataStore.items.filter { it.tier >= 2 }
        } else {
            EquipmentDataStore.items.filter { it.category == selectedCategory && it.tier >= 2 }
        }
    }

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
                "Equipment Guide",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }

        // Counter Build Tip
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = OracleGold.copy(alpha = 0.1f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("💡", fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("Quick Counter Tip", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = OracleGold)
                        Text(
                            "vs Healers: Halberd/Necklace • vs Mages: Athena's/Radiant • vs MM: Dominance Ice/Blade Armor • vs Tanks: Malefic Roar/Divine Glaive",
                            style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }

        // Category Tabs
        item {
            androidx.compose.foundation.lazy.LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        label = { Text(category) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = OracleGold.copy(alpha = 0.2f),
                            selectedLabelColor = OracleGold
                        )
                    )
                }
            }
        }

        // Items count
        item {
            Text(
                "${filteredItems.size} items",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.5f)
            )
        }

        // Items list
        items(filteredItems) { item ->
            EquipmentItemCard(
                item = item,
                isExpanded = expandedItem == item.id,
                onToggle = {
                    expandedItem = if (expandedItem == item.id) null else item.id
                }
            )
        }
    }
}

@Composable
fun EquipmentItemCard(item: EquipmentData, isExpanded: Boolean, onToggle: () -> Unit) {
    val categoryColor = when (item.category) {
        "Attack" -> OracleError
        "Magic" -> Color(0xFF9C27B0)
        "Defense" -> Color(0xFF4CAF50)
        "Movement" -> Color(0xFF2196F3)
        "Roam" -> Color(0xFFFF9800)
        else -> Color.Gray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        onClick = onToggle
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Category icon
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(categoryColor.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        item.name.first().toString(),
                        fontSize = 16.sp,
                        color = categoryColor,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Name & category
                Column(modifier = Modifier.weight(1f)) {
                    Text(item.name, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                    Text("${item.category} • ${item.subcategory}", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.7f))
                }

                // Price
                Text("${item.price}g", style = MaterialTheme.typography.bodyMedium, color = OracleGold, fontWeight = FontWeight.Bold)

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

                // Stats
                Text("Stats", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (item.stats.attack > 0) StatBadge("+${item.stats.attack} ATK", OracleError)
                    if (item.stats.magicPower > 0) StatBadge("+${item.stats.magicPower} MP", Color(0xFF9C27B0))
                    if (item.stats.hp > 0) StatBadge("+${item.stats.hp} HP", OracleSuccess)
                    if (item.stats.mp > 0) StatBadge("+${item.stats.mp} MP", Color(0xFF2196F3))
                    if (item.stats.defense > 0) StatBadge("+${item.stats.defense} DEF", OracleGold)
                    if (item.stats.magicDefense > 0) StatBadge("+${item.stats.magicDefense} MDEF", Color(0xFF2196F3))
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (item.stats.attackSpeed > 0) StatBadge("+${String.format("%.0f", item.stats.attackSpeed * 100)}% AS", Color(0xFFFF9800))
                    if (item.stats.criticalChance > 0) StatBadge("+${String.format("%.0f", item.stats.criticalChance * 100)}% Crit", Color(0xFFE91E63))
                    if (item.stats.cooldownReduction > 0) StatBadge("+${String.format("%.0f", item.stats.cooldownReduction * 100)}% CDR", Color(0xFF9C27B0))
                    if (item.stats.lifesteal > 0) StatBadge("+${String.format("%.0f", item.stats.lifesteal * 100)}% LS", Color(0xFFE91E63))
                    if (item.stats.movementSpeed > 0) StatBadge("+${String.format("%.0f", item.stats.movementSpeed * 100)}% MS", Color(0xFF2196F3))
                    if (item.stats.penetration > 0) StatBadge("+${item.stats.penetration} PEN", OracleError)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Passive
                Text("Passive", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(item.passive, style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.8f))

                // Build path
                if (item.buildFrom.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Builds From", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        item.buildFrom.forEach { component ->
                            val componentItem = EquipmentDataStore.getItemById(component)
                            Box(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.background, RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    componentItem?.name ?: component,
                                    fontSize = 9.sp,
                                    color = Color.White.copy(alpha = 0.8f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatBadge(text: String, color: Color) {
    Box(
        modifier = Modifier
            .background(color.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(text, fontSize = 9.sp, color = color, fontWeight = FontWeight.Bold)
    }
}
