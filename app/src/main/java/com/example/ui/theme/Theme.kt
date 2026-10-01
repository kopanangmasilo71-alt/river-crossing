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

    // 5. Semantic Surfaces & Containers (No plain white or generic grey cards)
    val screenBackground: Color = Color(0xFF07192F),
    val cardBackground: Color = Color(0xF2092341),
    val cardWoodBorder: Color = Color(0xFFA06634),
    val cardWaterBorder: Color = Color(0xFF38BDF8),
    val insetDark: Color = Color(0xCC05172A),
    val insetBorder: Color = Color(0x6638BDF8),

    // 6. High-Contrast Typography
    val textPrimary: Color = Color(0xFFFFFFFF),
    val textSecondary: Color = Color(0xFFE0F2FE),
    val textMuted: Color = Color(0xFFBAE6FD),
    val textGold: Color = Color(0xFFFEF08A),
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
        listOf(Color(0xFF0F325E), Color(0xFF07192F))
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
    primary = Color(0xFF22724A),             // Forest green
    onPrimary = Color.White,
    primaryContainer = Color(0xFF143B28),
    onPrimaryContainer = Color(0xFF86EFAC),
    secondary = Color(0xFF0284C7),           // Deep river blue
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF0A2B4E),
    onSecondaryContainer = Color(0xFFBAE6FD),
    tertiary = Color(0xFF8B5A2B),            // Earthy timber brown
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFF2E190E),
    onTertiaryContainer = Color(0xFFFDE68A),
    background = Color(0xFF07192F),          // Deep river twilight backdrop
    onBackground = Color.White,
    surface = Color(0xF2092341),             // Themed sapphire/timber surface
    onSurface = Color.White,
    surfaceVariant = Color(0xFF0A2B4E),
    onSurfaceVariant = Color(0xFFBAE6FD),
    outline = Color(0xFF38BDF8),
    outlineVariant = Color(0xFFA06634)
)

val RiverDarkColorScheme: ColorScheme = darkColorScheme(
    primary = Color(0xFF2E8B57),             // Forest green highlight
    onPrimary = Color.White,
    primaryContainer = Color(0xFF143B28),
    onPrimaryContainer = Color(0xFF86EFAC),
    secondary = Color(0xFF38BDF8),           // River cyan highlight
    onSecondary = Color(0xFF04182E),
    secondaryContainer = Color(0xFF071E38),
    onSecondaryContainer = Color(0xFFBAE6FD),
    tertiary = Color(0xFFA06634),            // Warm timber
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFF2E190E),
    onTertiaryContainer = Color(0xFFFDE68A),
    background = Color(0xFF05182F),
    onBackground = Color.White,
    surface = Color(0xF2071E38),
    onSurface = Color.White,
    surfaceVariant = Color(0xFF0A2440),
    onSurfaceVariant = Color(0xFFBAE6FD),
    outline = Color(0xFF38BDF8),
    outlineVariant = Color(0xFF5A361D)
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
