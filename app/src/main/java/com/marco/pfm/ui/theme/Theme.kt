package com.marco.pfm.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = PFMPrimary,
    onPrimary = PFMOnPrimary,
    primaryContainer = PFMPrimaryContainer,
    onPrimaryContainer = PFMOnPrimaryContainer,
    surface = PFMSurface,
    surfaceVariant = PFMSurfaceVariant,
)

@Composable
fun PfmTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography,
        content = content,
    )
}
