package com.mlbb.oracle.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// MLBB Oracle Colors
val OracleGold = Color(0xFFFFD700)
val OracleDark = Color(0xFF1A1A2E)
val OracleDarker = Color(0xFF0F0F1A)
val OracleAccent = Color(0xFF6C63FF)
val OracleSuccess = Color(0xFF4CAF50)
val OracleWarning = Color(0xFFFF9800)
val OracleError = Color(0xFFF44336)

private val DarkColorScheme = darkColorScheme(
    primary = OracleGold,
    secondary = OracleAccent,
    tertiary = OracleSuccess,
    background = OracleDarker,
    surface = OracleDark,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    error = OracleError,
)

private val LightColorScheme = lightColorScheme(
    primary = OracleGold,
    secondary = OracleAccent,
    tertiary = OracleSuccess,
    background = Color(0xFFF5F5F5),
    surface = Color.White,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    error = OracleError,
)

@Composable
fun MLBBOrcleTheme(
    darkTheme: Boolean = true, // Default to dark theme for gaming
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
