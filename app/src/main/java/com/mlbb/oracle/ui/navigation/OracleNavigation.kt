package com.mlbb.oracle.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mlbb.oracle.ui.screens.*

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object BanPick : Screen("ban_pick", "Ban/Pick", Icons.Default.Gavel)
    object Live : Screen("live", "Live", Icons.Default.PlayArrow)
    object Matches : Screen("matches", "Matches", Icons.Default.List)
    object Heroes : Screen("heroes", "Heroes", Icons.Default.Person)
    object Settings : Screen("settings", "Settings", Icons.Default.Settings)
}

data class BottomNavItem(
    val screen: Screen,
    val icon: ImageVector,
    val label: String
)

val bottomNavItems = listOf(
    BottomNavItem(Screen.Home, Icons.Default.Home, "Home"),
    BottomNavItem(Screen.BanPick, Icons.Default.Gavel, "Ban/Pick"),
    BottomNavItem(Screen.Live, Icons.Default.PlayArrow, "Live"),
    BottomNavItem(Screen.Matches, Icons.Default.List, "Matches"),
    BottomNavItem(Screen.Heroes, Icons.Default.Person, "Heroes"),
    BottomNavItem(Screen.Settings, Icons.Default.Settings, "Settings")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OracleNavigation() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "🔮 MLBB Oracle",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.screen.route } == true,
                        onClick = {
                            navController.navigate(item.screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { HomeScreen() }
            composable(Screen.BanPick.route) {
                BanPickScreen(
                    onNavigateToHeroDetail = { heroName ->
                        navController.navigate("hero_detail/$heroName")
                    }
                )
            }
            composable(Screen.Live.route) { LiveScreen() }
            composable(Screen.Matches.route) { MatchHistoryScreen() }
            composable(Screen.Heroes.route) {
                HeroesScreen(
                    onHeroClick = { heroName ->
                        navController.navigate("hero_detail/$heroName")
                    }
                )
            }
            composable(Screen.Settings.route) { SettingsScreen() }
            composable(
                route = "hero_detail/{heroName}",
                arguments = listOf(navArgument("heroName") { type = NavType.StringType })
            ) { backStackEntry ->
                val heroName = backStackEntry.arguments?.getString("heroName") ?: ""
                HeroDetailScreen(
                    heroName = heroName,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}