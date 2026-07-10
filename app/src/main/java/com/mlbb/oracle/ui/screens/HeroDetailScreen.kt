package com.mlbb.oracle.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.mlbb.oracle.data.HeroData
import com.mlbb.oracle.data.HeroDatabase
import com.mlbb.oracle.data.EquipmentDataStore
import com.mlbb.oracle.ui.theme.OracleGold
import com.mlbb.oracle.ui.theme.OracleDark
import com.mlbb.oracle.ui.theme.OracleSuccess
import com.mlbb.oracle.ui.theme.OracleError

@Composable
fun HeroDetailScreen(heroId: String) {
    val hero = remember { HeroDatabase.getHeroById(heroId) }
    var selectedTab by remember { mutableIntStateOf(0) }

    if (hero == null) {
        Box(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Text("Hero not found", color = Color.White.copy(alpha = 0.7f))
        }
        return
    }

    val counters = remember { HeroDatabase.getCounterHeroes(heroId) }
    val synergies = remember { HeroDatabase.getSynergyHeroes(heroId) }
    val recommendedBuild = remember { EquipmentDataStore.getRecommendedBuild(heroId) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Hero Header
        item {
            HeroHeader(hero)
        }

        // Tabs
        item {
            TabRow(selectedTabIndex = selectedTab) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, text = { Text("Stats") })
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }, text = { Text("Builds") })
                Tab(selected = selectedTab == 2, onClick = { selectedTab = 2 }, text = { Text("Matchups") })
                Tab(selected = selectedTab == 3, onClick = { selectedTab = 3 }, text = { Text("Combo") })
            }
        }

        when (selectedTab) {
            0 -> {
                // Stats Tab
                item { StatsSection(hero) }
                item { SkillsSection(hero) }
                item { InfoSection(hero) }
            }
            1 -> {
                // Builds Tab
                item { BuildsSection(hero) }
                item { RecommendedBuildSection(recommendedBuild) }
                item { EmblemSection(hero) }
            }
            2 -> {
                // Matchups Tab
                item { CounterSection(counters) }
                item { SynergySection(synergies) }
            }
            3 -> {
                // Combo Tab
                item { ComboSection(hero) }
            }
        }
    }
}

@Composable
fun HeroHeader(hero: HeroData) {
    val roleColor = HeroDatabase.roleColors[hero.role] ?: Color.Gray

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = OracleDark),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Hero Avatar
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(roleColor.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    hero.name.first().toString(),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = roleColor
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Name
            Text(
                hero.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                hero.nameCn,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Role & Tier
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .background(roleColor, RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(hero.role, fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(hero.specialty, style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.7f))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Tier Badge
            Box(
                modifier = Modifier
                    .background(
                        when (hero.tier) {
                            "S" -> OracleGold
                            "A" -> Color(0xFF2196F3)
                            "B" -> Color(0xFF9E9E9E)
                            else -> Color(0xFF795548)
                        },
                        RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Text("Tier ${hero.tier}", fontSize = 14.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Win/Pick/Ban rates
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                RateItem("Win Rate", "${String.format("%.1f", hero.winRate)}%", hero.winRate >= 50)
                RateItem("Pick Rate", "${String.format("%.1f", hero.pickRate)}%", true)
                RateItem("Ban Rate", "${String.format("%.1f", hero.banRate)}%", true)
            }
        }
    }
}

@Composable
fun RateItem(label: String, value: String, isPositive: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = if (isPositive) OracleSuccess else OracleError)
        Text(label, style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.7f))
    }
}

@Composable
fun StatsSection(hero: HeroData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Base Stats", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            StatBar("HP", hero.stats.hp.toFloat(), 4500f, OracleSuccess)
            StatBar("MP", hero.stats.mp.toFloat(), 600f, Color(0xFF2196F3))
            StatBar("ATK", hero.stats.attack.toFloat(), 200f, OracleError)
            StatBar("DEF", hero.stats.defense.toFloat(), 250f, OracleGold)
            StatBar("Speed", hero.stats.speed, 4.5f, Color(0xFF9C27B0))
        }
    }
}

@Composable
fun StatBar(label: String, value: Float, maxValue: Float, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, modifier = Modifier.width(50.dp), style = MaterialTheme.typography.bodySmall)
        Box(
            modifier = Modifier
                .weight(1f)
                .height(12.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(Color.White.copy(alpha = 0.1f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(value / maxValue)
                    .clip(RoundedCornerShape(6.dp))
                    .background(color)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            if (label == "Speed") String.format("%.1f", value) else value.toInt().toString(),
            modifier = Modifier.width(50.dp),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun SkillsSection(hero: HeroData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Skills", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            SkillRow("Passive", hero.skills.passive, Color(0xFF9E9E9E))
            SkillRow("Skill 1", hero.skills.skill1, Color(0xFF2196F3))
            SkillRow("Skill 2", hero.skills.skill2, Color(0xFF4CAF50))
            SkillRow("Ultimate", hero.skills.ultimate, OracleGold)
        }
    }
}

@Composable
fun SkillRow(name: String, description: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(color.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(name.first().toString(), fontSize = 10.sp, color = color, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(name, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = color)
            Text(description, style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.8f))
        }
    }
}

@Composable
fun InfoSection(hero: HeroData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Recommended Setup", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Emblem", style = MaterialTheme.typography.labelMedium, color = Color.White.copy(alpha = 0.7f))
                    Text(hero.emblem, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text("Battle Spell", style = MaterialTheme.typography.labelMedium, color = Color.White.copy(alpha = 0.7f))
                    Text(hero.battleSpell, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun BuildsSection(hero: HeroData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Recommended Builds", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            hero.builds.forEach { build ->
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .background(OracleGold.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(build.name, fontSize = 12.sp, color = OracleGold, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(build.description, style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.7f))
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        build.items.forEach { item ->
                            Box(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.background, RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 3.dp)
                            ) {
                                Text(item, fontSize = 9.sp, color = Color.White.copy(alpha = 0.9f))
                            }
                        }
                    }
                }

                if (build != hero.builds.last()) {
                    HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                }
            }
        }
    }
}

@Composable
fun RecommendedBuildSection(build: List<com.mlbb.oracle.data.EquipmentData>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Optimal Build Path", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                build.forEach { item ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.background),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(item.name.first().toString(), fontSize = 20.sp, color = OracleGold, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            item.name.split(" ").first(),
                            fontSize = 8.sp,
                            textAlign = TextAlign.Center,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                        Text(
                            "${item.price}g",
                            fontSize = 8.sp,
                            color = OracleGold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Total: ${build.sumOf { it.price }}g",
                style = MaterialTheme.typography.bodyMedium,
                color = OracleGold,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun EmblemSection(hero: HeroData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Emblem & Spell", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = OracleGold.copy(alpha = 0.1f)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🎯", fontSize = 24.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(hero.emblem, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = OracleGold)
                    }
                }

                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF2196F3).copy(alpha = 0.1f)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("⚡", fontSize = 24.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(hero.battleSpell, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Color(0xFF2196F3))
                    }
                }
            }
        }
    }
}

@Composable
fun CounterSection(counters: List<HeroData>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Counters (Heroes that counter this hero)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            if (counters.isEmpty()) {
                Text("No specific counters", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.7f))
            } else {
                counters.forEach { counter ->
                    CounterRow(counter, true)
                }
            }
        }
    }
}

@Composable
fun SynergySection(synergies: List<HeroData>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Synergies (Heroes that work well with this hero)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            if (synergies.isEmpty()) {
                Text("No specific synergies", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.7f))
            } else {
                synergies.forEach { synergy ->
                    CounterRow(synergy, false)
                }
            }
        }
    }
}

@Composable
fun CounterRow(hero: HeroData, isCounter: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(
                    (HeroDatabase.roleColors[hero.role] ?: Color.Gray).copy(alpha = 0.2f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(hero.name.first().toString(), fontSize = 16.sp, color = HeroDatabase.roleColors[hero.role] ?: Color.Gray, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(hero.name, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
            Text(hero.role, style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.7f))
        }

        Box(
            modifier = Modifier
                .background(
                    if (isCounter) OracleError.copy(alpha = 0.2f) else OracleSuccess.copy(alpha = 0.2f),
                    RoundedCornerShape(4.dp)
                )
                .padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
            Text(
                if (isCounter) "Counter" else "Synergy",
                fontSize = 10.sp,
                color = if (isCounter) OracleError else OracleSuccess,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ComboSection(hero: HeroData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Optimal Combo Sequence", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            val combos = getCombosForHero(hero.id)

            combos.forEachIndexed { index, combo ->
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .background(OracleGold, RoundedCornerShape(4.dp))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text("Combo ${index + 1}", fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(combo.name, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        combo.steps.forEach { step ->
                            Box(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.background, RoundedCornerShape(4.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(step, fontSize = 11.sp, color = Color.White)
                            }

                            if (step != combo.steps.last()) {
                                Text("→", color = OracleGold, modifier = Modifier.padding(horizontal = 2.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(combo.description, style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.7f))
                }

                if (index < combos.lastIndex) {
                    HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                }
            }
        }
    }
}

data class Combo(val name: String, val steps: List<String>, val description: String)

fun getCombosForHero(heroId: String): List<Combo> {
    return when (heroId.lowercase()) {
        "granger" -> listOf(
            Combo("Full Burst", listOf("S1", "AA", "S2", "AA", "Ult"), "Max damage combo with passive resets"),
            Combo("Quick Poke", listOf("S1", "S2"), "Fast poke and disengage"),
            Combo("Execute", listOf("Ult", "S1", "AA"), "Finish low HP targets")
        )
        "fanny" -> listOf(
            Combo("Cable Burst", listOf("S2", "S2", "S1", "S2", "Ult"), "Standard cable combo"),
            Combo("Wall Spam", listOf("S2", "S1", "S2", "S1"), "Wall-hitting combo"),
            Combo("Escape", listOf("S2", "S2", "S2"), "Triple cable escape")
        )
        "chou" -> listOf(
            Combo("Kick Combo", listOf("S1", "S1", "S1", "Ult", "S2"), "Classic kick into wall"),
            Combo("Burst", listOf("AA", "S1", "S2", "Ult"), "Quick burst with CC"),
            Combo("Chase", listOf("S2", "S1", "Ult", "S1"), "Chasing combo")
        )
        "kagura" -> listOf(
            Combo("Full Burst", listOf("S1", "S2", "Ult", "S2", "S1"), "Umbrella throw + teleport + ult"),
            Combo("Poke", listOf("S1", "S1"), "Safe umbrella poke"),
            Combo("Dive", listOf("S2", "Ult", "S1", "S2"), "Teleport + burst + escape")
        )
        "miya" -> listOf(
            Combo("Full DPS", listOf("AA", "S1", "AA", "S2", "AA", "Ult"), "Maximum damage output"),
            Combo("Engage", listOf("S2", "S1", "AA"), "Root + damage"),
            Combo("Escape", listOf("Ult", "S2"), "Purify + slow + run")
        )
        "tigreal" -> listOf(
            Combo("Teamfight", listOf("S2", "S1", "Ult"), "Knockup + AoE stun"),
            Combo("Initiate", listOf("Flicker", "Ult"), "Flicker + ult surprise"),
            Combo("Push", listOf("S2", "Ult"), "Push enemies into tower")
        )
        "gusion" -> listOf(
            Combo("Full Burst", listOf("S1", "S2", "S2", "Ult", "S1", "S2"), "Maximum damage combo"),
            Combo("Quick Kill", listOf("S1", "S2", "Ult", "S2"), "Fast single target burst"),
            Combo("Poke", listOf("S2", "S2"), "Safe daggers poke")
        )
        "ling" -> listOf(
            Combo("Wall Jump", listOf("S1", "S2", "S2", "Ult"), "Wall engage + burst"),
            Combo("Full Combo", listOf("Ult", "S2", "S2", "S2", "S2"), "Ultimate + multiple dashes"),
            Combo("Escape", listOf("S1", "S1"), "Double wall jump escape")
        )
        "atlas" -> listOf(
            Combo("5-Man Ult", listOf("S2", "S1", "Ult"), "Jump + stun + pull all"),
            Combo("Initiate", listOf("S2", "Ult"), "Jump + pull"),
            Combo("Peel", listOf("Ult", "S1"), "Pull enemies off carry")
        )
        "eudora" -> listOf(
            Combo("One-Shot", listOf("S2", "S1", "Ult"), "Stun + damage + execute"),
            Combo("Burst", listOf("Ult", "S2", "S1"), "Ult first for max damage"),
            Combo("Safe Poke", listOf("S1", "S1"), "Long range poke")
        )
        else -> listOf(
            Combo("Basic Combo", listOf("S1", "S2", "Ult"), "Standard skill rotation"),
            Combo("Poke", listOf("S1", "S1"), "Safe poke combo"),
            Combo("Burst", listOf("Ult", "S1", "S2"), "Full damage combo")
        )
    }
}
