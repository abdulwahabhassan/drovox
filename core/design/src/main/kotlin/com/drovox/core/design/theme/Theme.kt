package com.drovox.core.design.theme

import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider


@Composable
fun DrovoxTheme(
    content: @Composable () -> Unit,
) {
    val drovoxLightColorScheme = lightColorScheme(
        primary = blue06,
        onPrimary = white,
        primaryContainer = blue02.copy(0.4f),
        onPrimaryContainer = grey04,
        inversePrimary = blue03.copy(0.5f),

        secondary = pink04,
        onSecondary = blue05,

        tertiary = grey05,
        onTertiary = white,
        tertiaryContainer = grey02.copy(alpha = 0.5f),
        onTertiaryContainer = white,

        error = red02,
        onError = white,
        errorContainer = pink02,
        onErrorContainer = red02,

        background = grey00,
        surface = grey00,
        surfaceVariant = white,
        outlineVariant = grey01,
    )

    CompositionLocalProvider(LocalRippleTheme provides DrovoxRippleTheme) {
        MaterialTheme(
            colorScheme = drovoxLightColorScheme,
            typography = drovoxTypography,
            content = content,
        )
    }
}
