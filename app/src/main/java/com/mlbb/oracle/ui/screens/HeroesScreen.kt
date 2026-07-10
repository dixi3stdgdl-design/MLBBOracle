package com.mlbb.oracle.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mlbb.oracle.data.HeroDatabase
import com.mlbb.oracle.data.HeroData
import com.mlbb.oracle.data.HeroRole
import com.mlbb.oracle.ui.theme.OracleGold

@Composable
fun HeroesScreen(
    onHeroClick: (String) -> Unit = {}
) {
    var selectedRole by remember { mutableStateOf<HeroRole?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    val heroes = remember(selectedRole, searchQuery) {
        var filtered = HeroDatabase.getAllHeroes()
        if (selectedRole != null) {
            filtered = filtered.filter { it.role == selectedRole }
        }
        if (searchQuery.isNotEmpty()) {
            filtered = filtered.filter { it.name.contains(searchQuery, ignoreCase = true) }
        }
        filtered.sortedByDescending { it.banPriority }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            "Heroes",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search heroes...") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Role Filter
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = selectedRole == null,
                onClick = { selectedRole = null },
                label = { Text("All") }
            )
            HeroRole.entries.forEach { role ->
                FilterChip(
                    selected = selectedRole == role,
                    onClick = {
                        selectedRole = if (selectedRole == role) null else role
                    },
                    label = { Text(role.displayName) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(android.graphics.Color.parseColor(role.colorHex)).copy(alpha = 0.2f)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Hero Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(heroes) { hero ->
                HeroGridCard(hero) { onHeroClick(hero.name) }
            }
        }
    }
}

@Composable
fun HeroGridCard(hero: HeroData, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Hero Avatar
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        color = Color(android.graphics.Color.parseColor(hero.role.colorHex)).copy(alpha = 0.2f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(hero.emoji, fontSize = 32.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                hero.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                hero.role.displayName,
                style = MaterialTheme.typography.labelSmall,
                color = Color(android.graphics.Color.parseColor(hero.role.colorHex))
            )

            // Tier Badge
            Box(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .background(
                        color = when (hero.tier) {
                            "S" -> OracleGold
                            "A" -> Color(0xFF2196F3)
                            "B" -> Color(0xFF9E9E9E)
                            else -> Color(0xFF795548)
                        },
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    hero.tier,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}