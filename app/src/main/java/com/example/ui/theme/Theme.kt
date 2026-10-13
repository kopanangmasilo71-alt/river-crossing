package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// ============================================================================
// SHARED RIVER-THEMED COLOR PALETTE
// Defines consistent, rich palette:
//   - Deep Blue: River waters, deep channels, rapids, twilight atmosphere
//   - Earthy Brown: Rustic riverbanks, wooden raft/boat, carved timber docks
//   - Forest Green: Lush shoreline vegetation, pine grove, mossy boulders
//   - Sunlit Gold: Sparkling water reflections, stars, victory trophies, compass
// ============================================================================

/**
 * Shared palette data class holding all river-themed color tokens, surfaces, and gradients.
 */
data class RiverThemePalette(
    // 1. Deep River Blues
    val deepBlueDark: Color = Color(0xFF07192F),
    val deepBlueMid: Color = Color(0xFF0A2B4E),
    val deepBlueLight: Color = Color(0xFF0284C7),
    val waterCyan: Color = Color(0xFF38BDF8),
    val waterFoam: Color = Color(0xFFE0F2FE),

    // 2. Earthy Browns
    val earthyBrownDark: Color = Color(0xFF2E190E),
    val earthyBrownMid: Color = Color(0xFF5A361D),
    val earthyBrownLight: Color = Color(0xFF8B5A2B),
    val timberWarm: Color = Color(0xFFA06634),
    val sandTan: Color = Color(0xFFD4A373),

    // 3. Forest Greens
    val forestGreenDark: Color = Color(0xFF143B28),
    val forestGreenMid: Color = Color(0xFF22724A),
    val forestGreenLight: Color = Color(0xFF2E8B57),
    val meadowGrass: Color = Color(0xFF4ADE80),
    val mossGreen: Color = Color(0xFF52B788),

    // 4. River Sunlit Amber / Gold
    val sunlitGold: Color = Color(0xFFF59E0B),
    val goldGlow: Color = Color(0xFFFACC15),
    val goldLight: Color = Color(0xFFFEF08A),
    val goldShadow: Color = Color(0x66D97706),

    // 5. Semantic Surfaces & Containers (Warm carved wood panels)
    val screenBackground: Color = Color(0xFF1B0B04),
    val cardBackground: Color = Color(0xF22B1406),
    val cardWoodBorder: Color = Color(0xFFD97706),
    val cardWaterBorder: Color = GoldenBankGlow,
    val insetDark: Color = Color(0xDD180A03),
    val insetBorder: Color = GoldenBankGlow.copy(alpha = 0.5f),

    // 6. High-Contrast Typography
    val textPrimary: Color = Color(0xFFFFFBEB),
    val textSecondary: Color = Color(0xFFFEF3C7),
    val textMuted: Color = Color(0xFFFDE68A),
    val textGold: Color = GoldenBankGlow,
    val textTimber: Color = Color(0xFFD4A373),

    // 7. Gradients
    val riverWaterGradient: Brush = Brush.verticalGradient(
        listOf(Color(0xFF0284C7), Color(0xFF072B4F))
    ),
    val timberGradient: Brush = Brush.verticalGradient(
        listOf(Color(0xFF8B5A2B), Color(0xFF5A361D))
    ),
    val forestGradient: Brush = Brush.verticalGradient(
        listOf(Color(0xFF2E8B57), Color(0xFF143B28))
    ),
    val sunlitGoldGradient: Brush = Brush.verticalGradient(
        listOf(Color(0xFFFBBF24), Color(0xFFD97706))
    ),
    val screenBackgroundGradient: Brush = Brush.verticalGradient(
        listOf(Color(0xFF3B1E0D), Color(0xFF180A03))
    )
)

val defaultRiverThemePalette = RiverThemePalette()

/**
 * CompositionLocal providing access to [RiverThemePalette].
 */
val LocalRiverTheme = staticCompositionLocalOf { defaultRiverThemePalette }

/**
 * Convenient shared object to access river theme tokens anywhere in Compose:
 * e.g., `RiverTheme.colors.forestGreenMid` or `RiverTheme.colors.timberGradient`.
 */
object RiverTheme {
    val colors: RiverThemePalette
        @Composable
        @ReadOnlyComposable
        get() = LocalRiverTheme.current
}

/**
 * Extension on MaterialTheme for semantic river colors:
 * e.g., `MaterialTheme.riverColors.deepBlueMid`.
 */
val MaterialTheme.riverColors: RiverThemePalette
    @Composable
    @ReadOnlyComposable
    get() = LocalRiverTheme.current

// ============================================================================
// MATERIAL 3 COLOR SCHEMES
// ============================================================================

val RiverLightColorScheme: ColorScheme = lightColorScheme(
    primary = Color(0xFF8B4513),             // Rustic timber brown
    onPrimary = Color(0xFFFFFBEB),
    primaryContainer = Color(0xFF451A03),
    onPrimaryContainer = Color(0xFFFEF3C7),
    secondary = Color(0xFFD97706),           // Warm amber
    onSecondary = Color(0xFFFFFBEB),
    secondaryContainer = Color(0xFF3B1C08),
    onSecondaryContainer = Color(0xFFFDE68A),
    tertiary = Color(0xFFB45309),            // Warm bronze
    onTertiary = Color(0xFFFFFBEB),
    tertiaryContainer = Color(0xFF2E1508),
    onTertiaryContainer = Color(0xFFFDE68A),
    background = Color(0xFF1B0B04),          // Dark timber wood backdrop
    onBackground = Color(0xFFFFFBEB),
    surface = Color(0xF22B1406),             // Carved wood surface
    onSurface = Color(0xFFFFFBEB),
    surfaceVariant = Color(0xFF3B1E0D),
    onSurfaceVariant = Color(0xFFFEF3C7),
    outline = GoldenBankGlow,
    outlineVariant = Color(0xFF8B4513)
)

val RiverDarkColorScheme: ColorScheme = darkColorScheme(
    primary = Color(0xFF8B4513),             // Rustic timber brown
    onPrimary = Color(0xFFFFFBEB),
    primaryContainer = Color(0xFF451A03),
    onPrimaryContainer = Color(0xFFFEF3C7),
    secondary = Color(0xFFD97706),           // Warm amber
    onSecondary = Color(0xFFFFFBEB),
    secondaryContainer = Color(0xFF3B1C08),
    onSecondaryContainer = Color(0xFFFDE68A),
    tertiary = Color(0xFFB45309),            // Warm bronze
    onTertiary = Color(0xFFFFFBEB),
    tertiaryContainer = Color(0xFF2E1508),
    onTertiaryContainer = Color(0xFFFDE68A),
    background = Color(0xFF180A03),
    onBackground = Color(0xFFFFFBEB),
    surface = Color(0xF2231106),
    onSurface = Color(0xFFFFFBEB),
    surfaceVariant = Color(0xFF331707),
    onSurfaceVariant = Color(0xFFFEF3C7),
    outline = GoldenBankGlow,
    outlineVariant = Color(0xFF6B3209)
)

/**
 * Main application theme defining the river-themed palette.
 * Provides both [MaterialTheme] and [LocalRiverTheme].
 */
@Composable
fun RiverGameTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val riverPalette = remember(darkTheme) {
        if (darkTheme) {
            defaultRiverThemePalette.copy(
                screenBackground = Color(0xFF05182F),
                cardBackground = Color(0xF2071E38),
                insetDark = Color(0xCC031222)
            )
        } else {
            defaultRiverThemePalette
        }
    }

    val colorScheme = if (darkTheme) RiverDarkColorScheme else RiverLightColorScheme

    CompositionLocalProvider(LocalRiverTheme provides riverPalette) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

/**
 * Backward-compatible alias for [RiverGameTheme].
 */
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    RiverGameTheme(darkTheme = darkTheme, dynamicColor = dynamicColor, content = content)
}
