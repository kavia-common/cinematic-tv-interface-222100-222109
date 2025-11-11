@file:OptIn(androidx.tv.material3.ExperimentalTvMaterial3Api::class)
package org.example.app.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme as TvMaterialTheme
import androidx.tv.material3.darkColorScheme as tvDarkColorScheme
import androidx.tv.material3.lightColorScheme as tvLightColorScheme



/**
 * Core brand colors per style guide:
 * - Primary: #3B82F6 (blue)
 * - Accents (tertiary/success): #06B6D4 (cyan)
 * - Secondary neutral: #64748B (slate)
 */
private val PrimaryBlue = androidx.compose.ui.graphics.Color(0xFF3B82F6)
private val SecondarySlate = androidx.compose.ui.graphics.Color(0xFF64748B)
private val SuccessCyan = androidx.compose.ui.graphics.Color(0xFF06B6D4)
private val ErrorRed = androidx.compose.ui.graphics.Color(0xFFEF4444)

// Light scheme surfaces
private val LightBackground = androidx.compose.ui.graphics.Color(0xFFF9FAFB) // #f9fafb
private val LightSurface = androidx.compose.ui.graphics.Color(0xFFFFFFFF)     // #ffffff
private val LightOnSurface = androidx.compose.ui.graphics.Color(0xFF111827)   // #111827

// Dark scheme surfaces
private val DarkBackground = androidx.compose.ui.graphics.Color(0xFF0B1020)
private val DarkSurface = androidx.compose.ui.graphics.Color(0xFF0F172A)
private val DarkOnSurface = androidx.compose.ui.graphics.Color(0xFFE5E7EB)

// Compose Material3 color schemes
private val LightColors: ColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    primaryContainer = PrimaryBlue.copy(alpha = 0.15f),
    onPrimaryContainer = PrimaryBlue,

    secondary = SecondarySlate,
    onSecondary = androidx.compose.ui.graphics.Color.White,
    secondaryContainer = SecondarySlate.copy(alpha = 0.15f),
    onSecondaryContainer = SecondarySlate,

    tertiary = SuccessCyan,
    onTertiary = androidx.compose.ui.graphics.Color.Black,
    tertiaryContainer = SuccessCyan.copy(alpha = 0.15f),
    onTertiaryContainer = SuccessCyan,

    error = ErrorRed,
    onError = androidx.compose.ui.graphics.Color.White,
    errorContainer = ErrorRed.copy(alpha = 0.15f),
    onErrorContainer = ErrorRed,

    background = LightBackground,
    onBackground = LightOnSurface,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = SecondarySlate.copy(alpha = 0.10f),
    onSurfaceVariant = SecondarySlate,

    outline = SecondarySlate.copy(alpha = 0.5f),
    outlineVariant = SecondarySlate.copy(alpha = 0.25f),
    scrim = androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.5f),
)

private val DarkColors: ColorScheme = darkColorScheme(
    primary = PrimaryBlue,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    primaryContainer = PrimaryBlue.copy(alpha = 0.25f),
    onPrimaryContainer = PrimaryBlue,

    secondary = SecondarySlate,
    onSecondary = androidx.compose.ui.graphics.Color.White,
    secondaryContainer = SecondarySlate.copy(alpha = 0.25f),
    onSecondaryContainer = SecondarySlate,

    tertiary = SuccessCyan,
    onTertiary = androidx.compose.ui.graphics.Color.Black,
    tertiaryContainer = SuccessCyan.copy(alpha = 0.25f),
    onTertiaryContainer = SuccessCyan,

    error = ErrorRed,
    onError = androidx.compose.ui.graphics.Color.White,
    errorContainer = ErrorRed.copy(alpha = 0.25f),
    onErrorContainer = ErrorRed,

    background = DarkBackground,
    onBackground = androidx.compose.ui.graphics.Color(0xFFE2E8F0),
    surface = DarkSurface,
    onSurface = androidx.compose.ui.graphics.Color(0xFFE5E7EB),
    surfaceVariant = SecondarySlate.copy(alpha = 0.25f),
    onSurfaceVariant = SecondarySlate.copy(alpha = 0.9f),

    outline = SecondarySlate.copy(alpha = 0.6f),
    outlineVariant = SecondarySlate.copy(alpha = 0.35f),
    scrim = androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.7f),
)

// TV Typography: larger sizes for readability on 10-foot UI
@Immutable
data class TvTypography(
    val displayLarge: TextStyle,
    val displayMedium: TextStyle,
    val displaySmall: TextStyle,
    val headlineLarge: TextStyle,
    val headlineMedium: TextStyle,
    val headlineSmall: TextStyle,
    val titleLarge: TextStyle,
    val titleMedium: TextStyle,
    val titleSmall: TextStyle,
    val bodyLarge: TextStyle,
    val bodyMedium: TextStyle,
    val bodySmall: TextStyle,
    val labelLarge: TextStyle,
    val labelMedium: TextStyle,
    val labelSmall: TextStyle,
)

// Defaults tuned for TV (source-independent so usable for both Material3 and TV Material3)
private val DefaultTvTypography = TvTypography(
    displayLarge = TextStyle(fontSize = 64.sp, fontWeight = FontWeight.SemiBold, lineHeight = 72.sp),
    displayMedium = TextStyle(fontSize = 56.sp, fontWeight = FontWeight.SemiBold, lineHeight = 64.sp),
    displaySmall = TextStyle(fontSize = 48.sp, fontWeight = FontWeight.SemiBold, lineHeight = 56.sp),

    headlineLarge = TextStyle(fontSize = 40.sp, fontWeight = FontWeight.SemiBold, lineHeight = 48.sp),
    headlineMedium = TextStyle(fontSize = 34.sp, fontWeight = FontWeight.Medium, lineHeight = 40.sp),
    headlineSmall = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Medium, lineHeight = 34.sp),

    titleLarge = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Medium, lineHeight = 30.sp),
    titleMedium = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Medium, lineHeight = 28.sp),
    titleSmall = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium, lineHeight = 26.sp),

    bodyLarge = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Normal, lineHeight = 28.sp),
    bodyMedium = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Normal, lineHeight = 26.sp),
    bodySmall = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal, lineHeight = 22.sp),

    labelLarge = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold, lineHeight = 22.sp),
    labelMedium = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold, lineHeight = 20.sp),
    labelSmall = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.SemiBold, lineHeight = 18.sp),
)

val LocalTvTypography = staticCompositionLocalOf { DefaultTvTypography }

// PUBLIC_INTERFACE
@Composable
fun tvTypography(): TvTypography {
    /** Returns the current TV typography set for 10-foot UIs. */
    return LocalTvTypography.current
}

// PUBLIC_INTERFACE
@Composable
fun CinematicTVTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    // Expose Material3 color scheme for consumers that need it
    content: @Composable () -> Unit
) {
    /**
     * Compose theme wrapper for the Cinematic TV interface.
     *
     * - Provides Material3 color scheme based on the style guide (#3B82F6 primary, #06B6D4 accent).
     * - Supplies TV-optimized typography via LocalTvTypography.
     * - Wraps content in androidx.tv.material3 theme as well, so TV components inherit colors.
     *
     * Parameters:
     * - useDarkTheme: whether to use the dark color scheme.
     * - dynamicColor: if true (and supported), will use dynamic color on Android 12+.
     * - content: Composable content to render.
     */
    val context = LocalContext.current
    val colorScheme: ColorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (useDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        useDarkTheme -> DarkColors
        else -> LightColors
    }

    // Map Material3 colors into TV Material colors for androidx.tv.material3
    @OptIn(ExperimentalTvMaterial3Api::class)
    val tvColorsLight = tvLightColorScheme(
        primary = colorScheme.primary,
        onPrimary = colorScheme.onPrimary,
        primaryContainer = colorScheme.primaryContainer,
        onPrimaryContainer = colorScheme.onPrimaryContainer,
        secondary = colorScheme.secondary,
        onSecondary = colorScheme.onSecondary,
        secondaryContainer = colorScheme.secondaryContainer,
        onSecondaryContainer = colorScheme.onSecondaryContainer,
        tertiary = colorScheme.tertiary,
        onTertiary = colorScheme.onTertiary,
        background = colorScheme.background,
        onBackground = colorScheme.onBackground,
        surface = colorScheme.surface,
        onSurface = colorScheme.onSurface,
        surfaceVariant = colorScheme.surfaceVariant,
        onSurfaceVariant = colorScheme.onSurfaceVariant,
        error = colorScheme.error,
        onError = colorScheme.onError,
    )

    @OptIn(ExperimentalTvMaterial3Api::class)
    val tvColorsDark = tvDarkColorScheme(
        primary = colorScheme.primary,
        onPrimary = colorScheme.onPrimary,
        primaryContainer = colorScheme.primaryContainer,
        onPrimaryContainer = colorScheme.onPrimaryContainer,
        secondary = colorScheme.secondary,
        onSecondary = colorScheme.onSecondary,
        secondaryContainer = colorScheme.secondaryContainer,
        onSecondaryContainer = colorScheme.onSecondaryContainer,
        tertiary = colorScheme.tertiary,
        onTertiary = colorScheme.onTertiary,
        background = colorScheme.background,
        onBackground = colorScheme.onBackground,
        surface = colorScheme.surface,
        onSurface = colorScheme.onSurface,
        surfaceVariant = colorScheme.surfaceVariant,
        onSurfaceVariant = colorScheme.onSurfaceVariant,
        error = colorScheme.error,
        onError = colorScheme.onError,
    )

    @OptIn(ExperimentalTvMaterial3Api::class)
    TvMaterialTheme(
        colorScheme = if (useDarkTheme) tvColorsDark else tvColorsLight
    ) {
        CompositionLocalProvider(LocalTvTypography provides DefaultTvTypography) {
            MaterialTheme(
                colorScheme = colorScheme,
                typography = androidx.compose.material3.Typography(
                    displayLarge = DefaultTvTypography.displayLarge,
                    displayMedium = DefaultTvTypography.displayMedium,
                    displaySmall = DefaultTvTypography.displaySmall,
                    headlineLarge = DefaultTvTypography.headlineLarge,
                    headlineMedium = DefaultTvTypography.headlineMedium,
                    headlineSmall = DefaultTvTypography.headlineSmall,
                    titleLarge = DefaultTvTypography.titleLarge,
                    titleMedium = DefaultTvTypography.titleMedium,
                    titleSmall = DefaultTvTypography.titleSmall,
                    bodyLarge = DefaultTvTypography.bodyLarge,
                    bodyMedium = DefaultTvTypography.bodyMedium,
                    bodySmall = DefaultTvTypography.bodySmall,
                    labelLarge = DefaultTvTypography.labelLarge,
                    labelMedium = DefaultTvTypography.labelMedium,
                    labelSmall = DefaultTvTypography.labelSmall,
                ),
                content = content
            )
        }
    }
}
