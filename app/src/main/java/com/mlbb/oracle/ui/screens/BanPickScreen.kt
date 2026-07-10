package com.mlbb.oracle.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mlbb.oracle.data.*
import com.mlbb.oracle.ui.theme.*

data class PickState(
    val teamPicks: List<String> = emptyList(),
    val enemyPicks: List<String> = emptyList(),
    val bannedHeroes: List<String> = emptyList(),
    val phase: BanPickPhase = BanPickPhase.BANNING,
    val currentTurn: Int = 0
)

enum class BanPickPhase {
    BANNING,
    PICKING,
    COMPLETE
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BanPickScreen(
    onNavigateToHeroDetail: (String) -> Unit = {}
) {
    var pickState by remember { mutableStateOf(PickState()) }
    var showHeroSelector by remember { mutableStateOf(false) }
    var selectorMode by remember { mutableStateOf("ban") }
    var selectedRole by remember { mutableStateOf<HeroRole?>(null) }
    var showTeamAnalysis by remember { mutableStateOf(false) }

    val teamAnalysis = remember(pickState.teamPicks) {
        if (pickState.teamPicks.isNotEmpty()) {
            HeroDatabase.analyzeTeamComposition(pickState.teamPicks)
        } else null
    }

    val enemyAnalysis = remember(pickState.enemyPicks) {
        if (pickState.enemyPicks.isNotEmpty()) {
            HeroDatabase.analyzeTeamComposition(pickState.enemyPicks)
        } else null
    }

    val recommendedBans = remember(pickState.teamPicks, pickState.enemyPicks, pickState.bannedHeroes) {
        HeroDatabase.getRecommendedBan(
            pickState.teamPicks + pickState.bannedHeroes,
            pickState.enemyPicks
        ).take(6)
    }

    val recommendedPicks = remember(pickState.teamPicks, pickState.enemyPicks, pickState.bannedHeroes, selectedRole) {
        HeroDatabase.getRecommendedPick(
            pickState.teamPicks + pickState.bannedHeroes,
            pickState.enemyPicks,
            selectedRole
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("⚔️", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Ban/Pick Assistant", fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                actions = {
                    IconButton(onClick = { showTeamAnalysis = !showTeamAnalysis }) {
                        Icon(Icons.Default.Info, "Team Analysis")
                    }
                    IconButton(onClick = {
                        pickState = PickState()
                    }) {
                        Icon(Icons.Default.Refresh, "Reset")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Phase Indicator
            item {
                PhaseIndicator(pickState.phase)
            }

            // Team Compositions Side by Side
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Our Team
                    TeamCompositionCard(
                        modifier = Modifier.weight(1f),
                        title = "OUR TEAM",
                        picks = pickState.teamPicks,
                        analysis = teamAnalysis,
                        isEnemy = false,
                        onRemove = { hero ->
                            pickState = pickState.copy(teamPicks = pickState.teamPicks - hero)
                        },
                        onHeroClick = onNavigateToHeroDetail
                    )

                    // VS
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(top = 24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "VS",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = OracleGold
                        )
                    }

                    // Enemy Team
                    TeamCompositionCard(
                        modifier = Modifier.weight(1f),
                        title = "ENEMY TEAM",
                        picks = pickState.enemyPicks,
                        analysis = enemyAnalysis,
                        isEnemy = true,
                        onRemove = { hero ->
                            pickState = pickState.copy(enemyPicks = pickState.enemyPicks - hero)
                        },
                        onHeroClick = onNavigateToHeroDetail
                    )
                }
            }

            // Quick Add Buttons
            item {
                QuickAddSection(
                    onAddTeam = { hero ->
                        if (hero !in pickState.teamPicks && hero !in pickState.enemyPicks && hero !in pickState.bannedHeroes) {
                            pickState = pickState.copy(teamPicks = pickState.teamPicks + hero)
                        }
                    },
                    onAddEnemy = { hero ->
                        if (hero !in pickState.teamPicks && hero !in pickState.enemyPicks && hero !in pickState.bannedHeroes) {
                            pickState = pickState.copy(enemyPicks = pickState.enemyPicks + hero)
                        }
                    },
                    onBan = { hero ->
                        if (hero !in pickState.bannedHeroes && hero !in pickState.teamPicks && hero !in pickState.enemyPicks) {
                            pickState = pickState.copy(bannedHeroes = pickState.bannedHeroes + hero)
                        }
                    },
                    currentPicks = pickState.teamPicks + pickState.enemyPicks + pickState.bannedHeroes
                )
            }

            // Recommended Bans
            item {
                RecommendationCard(
                    title = "TOP BANS",
                    icon = Icons.Default.Block,
                    color = OracleError,
                    heroes = recommendedBans,
                    subtitle = "Based on current meta",
                    onHeroClick = { hero ->
                        if (hero !in pickState.bannedHeroes && hero !in pickState.teamPicks && hero !in pickState.enemyPicks) {
                            pickState = pickState.copy(bannedHeroes = pickState.bannedHeroes + hero)
                        }
                    },
                    onDetailClick = onNavigateToHeroDetail
                )
            }

            // Role Filter for Picks
            item {
                RoleFilterRow(
                    selectedRole = selectedRole,
                    onRoleSelected = { role ->
                        selectedRole = if (selectedRole == role) null else role
                    }
                )
            }

            // Recommended Picks
            item {
                RecommendationCard(
                    title = if (selectedRole != null) "BEST ${selectedRole!!.displayName.uppercase()} PICKS" else "RECOMMENDED PICKS",
                    icon = Icons.Default.Star,
                    color = OracleGold,
                    heroes = recommendedPicks,
                    subtitle = "Counter picks for enemy team",
                    onHeroClick = { hero ->
                        if (hero !in pickState.teamPicks && hero !in pickState.enemyPicks && hero !in pickState.bannedHeroes) {
                            pickState = pickState.copy(teamPicks = pickState.teamPicks + hero)
                        }
                    },
                    onDetailClick = onNavigateToHeroDetail
                )
            }

            // Team Analysis Panel
            if (showTeamAnalysis && teamAnalysis != null) {
                item {
                    TeamAnalysisCard(
                        analysis = teamAnalysis,
                        teamName = "Your Team"
                    )
                }
            }

            if (showTeamAnalysis && enemyAnalysis != null) {
                item {
                    TeamAnalysisCard(
                        analysis = enemyAnalysis,
                        teamName = "Enemy Team"
                    )
                }
            }

            // Power Spike Timeline
            if (teamAnalysis != null) {
                item {
                    PowerSpikeTimeline(
                        early = teamAnalysis.earlyGamePower,
                        mid = teamAnalysis.midGamePower,
                        late = teamAnalysis.lateGamePower
                    )
                }
            }

            // Bottom spacer
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun PhaseIndicator(phase: BanPickPhase) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when (phase) {
                BanPickPhase.BANNING -> OracleError.copy(alpha = 0.15f)
                BanPickPhase.PICKING -> OracleGold.copy(alpha = 0.15f)
                BanPickPhase.COMPLETE -> OracleSuccess.copy(alpha = 0.15f)
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PhaseStep(
                label = "BAN",
                isActive = phase == BanPickPhase.BANNING,
                isCompleted = phase != BanPickPhase.BANNING,
                color = OracleError
            )
            Icon(
                Icons.Default.ArrowForward,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.5f)
            )
            PhaseStep(
                label = "PICK",
                isActive = phase == BanPickPhase.PICKING,
                isCompleted = phase == BanPickPhase.COMPLETE,
                color = OracleGold
            )
            Icon(
                Icons.Default.ArrowForward,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.5f)
            )
            PhaseStep(
                label = "GO!",
                isActive = phase == BanPickPhase.COMPLETE,
                isCompleted = false,
                color = OracleSuccess
            )
        }
    }
}

@Composable
fun PhaseStep(label: String, isActive: Boolean, isCompleted: Boolean, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = when {
                        isActive -> color
                        isCompleted -> color.copy(alpha = 0.5f)
                        else -> Color.White.copy(alpha = 0.1f)
                    },
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isCompleted) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Text(
                    label,
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun TeamCompositionCard(
    modifier: Modifier = Modifier,
    title: String,
    picks: List<String>,
    analysis: TeamAnalysis?,
    isEnemy: Boolean,
    onRemove: (String) -> Unit,
    onHeroClick: (String) -> Unit
) {
    val accentColor = if (isEnemy) OracleError else OracleGold

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = accentColor
            )
            Spacer(modifier = Modifier.height(8.dp))

            repeat(5) { index ->
                val heroName = picks.getOrNull(index)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                        .then(
                            if (heroName != null) Modifier.clickable { onHeroClick(heroName) }
                            else Modifier
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (heroName != null) {
                        val hero = HeroDatabase.getHeroByName(heroName)
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .background(
                                    color = Color(android.graphics.Color.parseColor(hero?.role?.colorHex ?: "#666666")).copy(alpha = 0.3f),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                hero?.emoji ?: "?",
                                fontSize = 14.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            heroName,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = { onRemove(heroName) },
                            modifier = Modifier.size(20.dp)
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Remove",
                                modifier = Modifier.size(14.dp),
                                tint = Color.White.copy(alpha = 0.5f)
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color.White.copy(alpha = 0.2f),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "${index + 1}",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.3f)
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            "Empty",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.3f)
                        )
                    }
                }
            }

            // Weaknesses
            if (analysis?.weaknesses?.isNotEmpty() == true) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Weaknesses:",
                    style = MaterialTheme.typography.labelSmall,
                    color = OracleWarning
                )
                analysis.weaknesses.take(2).forEach { weakness ->
                    Text(
                        "• $weakness",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 9.sp
                    )
                }
            }
        }
    }
}

@Composable
fun QuickAddSection(
    onAddTeam: (String) -> Unit,
    onAddEnemy: (String) -> Unit,
    onBan: (String) -> Unit,
    currentPicks: List<String>
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "QUICK ADD",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null
                    )
                }
            }

            if (expanded) {
                HeroRole.entries.forEach { role ->
                    val heroes = HeroDatabase.getHeroesByRole(role)
                        .filter { it.name !in currentPicks }
                        .sortedByDescending { it.banPriority }

                    if (heroes.isNotEmpty()) {
                        Text(
                            role.displayName,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(android.graphics.Color.parseColor(role.colorHex)),
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        LazyRow(
                            modifier = Modifier.padding(top = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            items(heroes.take(10)) { hero ->
                                CompactHeroChip(
                                    hero = hero,
                                    onAddTeam = { onAddTeam(hero.name) },
                                    onAddEnemy = { onAddEnemy(hero.name) },
                                    onBan = { onBan(hero.name) }
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
fun CompactHeroChip(
    hero: HeroData,
    onAddTeam: () -> Unit,
    onAddEnemy: () -> Unit,
    onBan: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    Box {
        Card(
            modifier = Modifier
                .clickable { showMenu = true }
                .width(80.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(android.graphics.Color.parseColor(hero.role.colorHex)).copy(alpha = 0.15f)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(hero.emoji, fontSize = 20.sp)
                Text(
                    hero.name,
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 9.sp,
                    maxLines = 1
                )
                Text(
                    hero.tier,
                    style = MaterialTheme.typography.labelSmall,
                    color = when (hero.tier) {
                        "S" -> OracleGold
                        "A" -> Color(0xFF2196F3)
                        else -> Color(0xFF9E9E9E)
                    },
                    fontSize = 8.sp
                )
            }
        }

        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false }
        ) {
            DropdownMenuItem(
                text = { Text("Add to Our Team") },
                onClick = { onAddTeam(); showMenu = false },
                leadingIcon = { Icon(Icons.Default.Add, null, tint = OracleGold) }
            )
            DropdownMenuItem(
                text = { Text("Add to Enemy Team") },
                onClick = { onAddEnemy(); showMenu = false },
                leadingIcon = { Icon(Icons.Default.Warning, null, tint = OracleError) }
            )
            DropdownMenuItem(
                text = { Text("Ban Hero") },
                onClick = { onBan(); showMenu = false },
                leadingIcon = { Icon(Icons.Default.Block, null, tint = OracleError) }
            )
        }
    }
}

@Composable
fun RoleFilterRow(
    selectedRole: HeroRole?,
    onRoleSelected: (HeroRole) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(HeroRole.entries) { role ->
            FilterChip(
                selected = selectedRole == role,
                onClick = { onRoleSelected(role) },
                label = { Text(role.displayName) },
                leadingIcon = {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                color = Color(android.graphics.Color.parseColor(role.colorHex)),
                                shape = CircleShape
                            )
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(android.graphics.Color.parseColor(role.colorHex)).copy(alpha = 0.2f)
                )
            )
        }
    }
}

@Composable
fun RecommendationCard(
    title: String,
    icon: ImageVector,
    color: Color,
    heroes: List<HeroData>,
    subtitle: String,
    onHeroClick: (String) -> Unit,
    onDetailClick: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.08f)
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = color
                    )
                    Text(
                        subtitle,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(heroes) { hero ->
                    RecommendationHeroCard(
                        hero = hero,
                        color = color,
                        onClick = { onHeroClick(hero.name) },
                        onDetailClick = { onDetailClick(hero.name) }
                    )
                }
            }
        }
    }
}

@Composable
fun RecommendationHeroCard(
    hero: HeroData,
    color: Color,
    onClick: () -> Unit,
    onDetailClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = Color(android.graphics.Color.parseColor(hero.role.colorHex)).copy(alpha = 0.2f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(hero.emoji, fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                hero.name,
                style = MaterialTheme.typography.labelSmall,
                fontSize = 10.sp,
                maxLines = 1
            )
            Text(
                "${hero.role.displayName} • ${hero.tier}",
                style = MaterialTheme.typography.labelSmall,
                fontSize = 8.sp,
                color = Color.White.copy(alpha = 0.5f)
            )
            TextButton(
                onClick = onDetailClick,
                modifier = Modifier.height(20.dp)
            ) {
                Text("Details", fontSize = 8.sp)
            }
        }
    }
}

@Composable
fun TeamAnalysisCard(
    analysis: TeamAnalysis,
    teamName: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                "$teamName Analysis",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Role Distribution
            Text("Role Distribution:", style = MaterialTheme.typography.labelSmall, color = OracleGold)
            Row(
                modifier = Modifier.padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                analysis.roles.forEach { (role, count) ->
                    Text(
                        "${role.displayName}: $count",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(android.graphics.Color.parseColor(role.colorHex))
                    )
                }
            }

            // Missing Roles
            if (analysis.missingRoles.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Missing: ${analysis.missingRoles.joinToString { it.displayName }}",
                    style = MaterialTheme.typography.labelSmall,
                    color = OracleError
                )
            }

            // Overall Rating
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Overall Rating:", style = MaterialTheme.typography.labelSmall)
                Text(
                    "${String.format("%.1f", analysis.overallRating)}/100",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = when {
                        analysis.overallRating >= 70 -> OracleSuccess
                        analysis.overallRating >= 40 -> OracleGold
                        else -> OracleError
                    }
                )
            }

            // Synergy Score
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Synergy Score:", style = MaterialTheme.typography.labelSmall)
                Text(
                    "${String.format("%.1f", analysis.synergyScore)}%",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = OracleAccent
                )
            )
        }
    }
}

@Composable
fun PowerSpikeTimeline(
    early: Double,
    mid: Double,
    late: Double
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                "POWER SPIKE TIMELINE",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                PowerSpikeStat("EARLY", early, Color(0xFFFF9800))
                PowerSpikeStat("MID", mid, OracleGold)
                PowerSpikeStat("LATE", late, OracleSuccess)
            }
        }
    }
}

@Composable
fun PowerSpikeStat(
    label: String,
    value: Double,
    color: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(
                    color = color.copy(alpha = 0.15f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    String.format("%.1f", value),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
                Text(
                    "/10",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.5f)
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = color
        )
    }
}