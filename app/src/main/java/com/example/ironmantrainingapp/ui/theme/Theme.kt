package com.example.ironmantrainingapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val IronmanLightColorScheme = lightColorScheme(
    primary = IronmanRed,
    onPrimary = IronmanWhite,
    primaryContainer = IronmanRedDark,
    onPrimaryContainer = IronmanWhite,
    secondary = IronmanBlack,
    onSecondary = IronmanWhite,
    background = IronmanWhite,
    onBackground = IronmanBlack,
    surface = IronmanSurface,
    onSurface = IronmanBlack,
    surfaceVariant = IronmanSurfaceVariant,
    onSurfaceVariant = IronmanGray,
    error = IronmanRed
)

@Composable
fun IronmanTrainingAppTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = IronmanLightColorScheme,
        typography = Typography,
        content = content
    )
}
