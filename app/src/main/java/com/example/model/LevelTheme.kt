package com.example.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.example.R

enum class WeatherEffectType {
    SPRING_PETALS,
    AUTUMN_LEAVES,
    ALPINE_MIST,
    SAVANNAH_DUST,
    MIDNIGHT_FIREFLIES,
    TWILIGHT_MOTES,
    OASIS_MIRAGE,
    AURORA_SHIMMER
}

enum class FlotsamType {
    LILY_PADS,
    AUTUMN_LEAVES,
    PINE_SPRIGS,
    SAVANNAH_REEDS,
    BIOLUMINESCENT_SPARKS,
    TWILIGHT_PETALS,
    OASIS_BLOOMS,
    AURORA_CRYSTALS
}

/**
 * Defines the visual identity, biome, weather, colors, and graphics for each puzzle level.
 */
data class LevelTheme(
    val id: String,
    val name: String,
    val tagline: String,
    val iconEmoji: String,
    @DrawableRes val backgroundDrawableRes: Int,
    val weatherEffect: WeatherEffectType,
    val flotsamType: FlotsamType,
    // River water coloring
    val waterGradientTop: Color,
    val waterGradientBottom: Color,
    val waveColor: Color,
    val flotsamColor: Color,
    // Shore & Dock theming
    val dockWoodTop: Color,
    val dockWoodBottom: Color,
    val dockBorder: Color,
    val dockLanternGlow: Color?,
    val bankAccentColor: Color,
    // Raft theming
    val raftWoodTone: Color,
    val raftRopeColor: Color,
    // UI badge theming
    val badgeBgColor: Color,
    val badgeTextColor: Color,
    val atmosphericOverlayColor: Color = Color.Transparent,
    // Boat & Set Sail Button Theming (dynamically adapts to the level theme)
    val boatHullColors: List<Color> = listOf(raftWoodTone, dockWoodBottom, raftWoodTone.copy(alpha = 0.85f)),
    val boatTrimColor: Color = dockBorder,
    val boatAccentGlow: Color = bankAccentColor,
    val boatOarColor: Color = dockWoodBottom,
    val sailButtonColors: List<Color> = listOf(Color(0xFF4ADE80), Color(0xFF22C55E), Color(0xFF16A34A), Color(0xFF15803D)),
    val sailButtonBorderColors: List<Color> = listOf(Color(0xFFFEF08A), Color(0xFFFACC15), dockBorder, dockWoodBottom),
    val sailButtonGlow: Color = bankAccentColor,
    // Top Bar & UI Header Theming (dynamically adapts to the level theme)
    val headerBackgroundColors: List<Color> = listOf(dockWoodBottom.copy(alpha = 0.95f), raftWoodTone.copy(alpha = 0.90f), dockWoodBottom.copy(alpha = 0.98f)),
    val headerBorderColors: List<Color> = listOf(dockBorder, bankAccentColor, dockBorder),
    val headerAccentColor: Color = bankAccentColor,
    val headerSurfaceColor: Color = badgeBgColor.copy(alpha = 0.85f),
    val headerTextColor: Color = badgeTextColor,
    val headerTimerColor: Color = bankAccentColor,
    // Bottom Action Buttons Theming
    val actionButtonColors: List<Color> = listOf(dockWoodBottom.copy(alpha = 0.92f), dockWoodTop.copy(alpha = 0.85f)),
    val actionButtonBorder: Color = bankAccentColor,
    val actionButtonTextColor: Color = badgeTextColor,
    // Game Object & Character Theming (Farmer & Passengers tokens, plaques, badges, and names)
    val objectTokenBackgroundColors: List<Color> = listOf(dockWoodBottom.copy(alpha = 0.90f), dockWoodTop.copy(alpha = 0.85f)),
    val objectTokenBorderColor: Color = bankAccentColor,
    val objectPlaqueColor: Color = badgeBgColor.copy(alpha = 0.88f),
    val objectPlaqueBorderColor: Color = dockBorder,
    val objectNameColor: Color = badgeTextColor,
    val objectInteractGlow: Color = bankAccentColor,
    val objectAddBadgeColor: Color = bankAccentColor,
    val farmerTokenBackgroundColors: List<Color> = listOf(dockWoodTop.copy(alpha = 0.95f), dockWoodBottom.copy(alpha = 0.92f)),
    val farmerTokenBorderColor: Color = bankAccentColor,
    val farmerNameColor: Color = badgeTextColor,
    val farmerStatusColor: Color = bankAccentColor,
    // Seated boat cargo passenger berth theming
    val passengerBerthBackgroundColors: List<Color> = listOf(dockWoodBottom.copy(alpha = 0.92f), raftWoodTone.copy(alpha = 0.88f)),
    val passengerBerthBorderColor: Color = boatTrimColor,
    // Particle-based celebration confetti palette matching this level's visual identity
    val confettiColors: List<Color> = emptyList()
) {
    /**
     * Resolves the complete particle celebration palette matching this level's theme.
     * If explicit confettiColors are provided, returns them; otherwise dynamically extracts
     * a harmonious palette from the level's waters, banks, boat trims, and accents.
     */
    fun getEffectiveConfettiColors(): List<Color> {
        if (confettiColors.isNotEmpty()) return confettiColors
        return listOfNotNull(
            bankAccentColor,
            waterGradientTop,
            boatTrimColor,
            dockBorder,
            flotsamColor,
            headerAccentColor,
            sailButtonGlow,
            Color(0xFFFFD700), // Pure Gold
            Color(0xFFFFFFFF)  // Sparkling White
        ).distinct()
    }

    companion object {
        val SPRING_VALLEY = LevelTheme(
            id = "spring_valley",
            name = "Spring Valley",
            tagline = "Gentle morning breeze over clover banks",
            iconEmoji = "🌸",
            backgroundDrawableRes = R.drawable.img_river_valley_full,
            weatherEffect = WeatherEffectType.SPRING_PETALS,
            flotsamType = FlotsamType.LILY_PADS,
            waterGradientTop = Color(0xFF10B981),
            waterGradientBottom = Color(0xFF0284C7),
            waveColor = Color(0xFFE0F2FE),
            flotsamColor = Color(0xFF22C55E),
            dockWoodTop = Color(0xFF8D5B34),
            dockWoodBottom = Color(0xFF5A3519),
            dockBorder = Color(0xFFE5C158),
            dockLanternGlow = null,
            bankAccentColor = Color(0xFF22C55E),
            raftWoodTone = Color(0xFF9E653A),
            raftRopeColor = Color(0xFFD97706),
            badgeBgColor = Color(0xFF0F1E19),
            badgeTextColor = Color(0xFF6EE7B7),
            atmosphericOverlayColor = Color(0x1034D399),
            boatHullColors = listOf(Color(0xFF8B5A2B), Color(0xFF5A3519), Color(0xFF784421)),
            boatTrimColor = Color(0xFFFACC15),
            boatAccentGlow = Color(0xFF22C55E),
            boatOarColor = Color(0xFF783E19),
            sailButtonColors = listOf(Color(0xFF4ADE80), Color(0xFF22C55E), Color(0xFF16A34A), Color(0xFF15803D)),
            sailButtonBorderColors = listOf(Color(0xFFFEF08A), Color(0xFF86EFAC), Color(0xFF15803D), Color(0xFF14532D)),
            sailButtonGlow = Color(0x9922C55E),
            headerBackgroundColors = listOf(Color(0xF20D281E), Color(0xF8071B13)),
            headerBorderColors = listOf(Color(0xFF22C55E), Color(0xFFFACC15), Color(0xFF15803D)),
            headerAccentColor = Color(0xFF4ADE80),
            headerSurfaceColor = Color(0xCC0B2519),
            headerTextColor = Color(0xFFD1FAE5),
            headerTimerColor = Color(0xFF86EFAC),
            actionButtonColors = listOf(Color(0xF00F3827), Color(0xF0082016)),
            actionButtonBorder = Color(0xFF22C55E),
            actionButtonTextColor = Color(0xFFD1FAE5),
            objectTokenBackgroundColors = listOf(Color(0xF00D281E), Color(0xF0082016)),
            objectTokenBorderColor = Color(0xFF22C55E),
            objectPlaqueColor = Color(0xF00D281E),
            objectPlaqueBorderColor = Color(0xFF22C55E),
            objectNameColor = Color(0xFFD1FAE5),
            objectInteractGlow = Color(0xFF4ADE80),
            objectAddBadgeColor = Color(0xFF16A34A),
            farmerTokenBackgroundColors = listOf(Color(0xF018442E), Color(0xF00B2619)),
            farmerTokenBorderColor = Color(0xFF4ADE80),
            farmerNameColor = Color(0xFFD1FAE5),
            farmerStatusColor = Color(0xFF86EFAC),
            passengerBerthBackgroundColors = listOf(Color(0xFF5A3519), Color(0xFF3E220D)),
            passengerBerthBorderColor = Color(0xFFFACC15),
            confettiColors = listOf(
                Color(0xFF10B981), // Emerald Green
                Color(0xFF22C55E), // Spring Leaf
                Color(0xFF34D399), // Mint Green
                Color(0xFF4ADE80), // Bright Lime
                Color(0xFFFFD700), // Sunlit Gold
                Color(0xFFF59E0B), // Warm Amber
                Color(0xFFF472B6), // Blossom Pink
                Color(0xFF38BDF8), // Clear Stream Blue
                Color(0xFFFFFFFF)  // Sparkling White
            )
        )

        val AUTUMN_HARVEST = LevelTheme(
            id = "autumn_harvest",
            name = "Autumn Harvest",
            tagline = "Golden leaves drifting upon amber ripples",
            iconEmoji = "🍁",
            backgroundDrawableRes = R.drawable.bg_autumn_river,
            weatherEffect = WeatherEffectType.AUTUMN_LEAVES,
            flotsamType = FlotsamType.AUTUMN_LEAVES,
            waterGradientTop = Color(0xFF0D9488),
            waterGradientBottom = Color(0xFFB45309),
            waveColor = Color(0xFFFEF3C7),
            flotsamColor = Color(0xFFEA580C),
            dockWoodTop = Color(0xFF78350F),
            dockWoodBottom = Color(0xFF451A03),
            dockBorder = Color(0xFFE5C158),
            dockLanternGlow = Color(0xFFFFB703),
            bankAccentColor = Color(0xFFF59E0B),
            raftWoodTone = Color(0xFF92400E),
            raftRopeColor = Color(0xFFB45309),
            badgeBgColor = Color(0xFF24150A),
            badgeTextColor = Color(0xFFFDE68A),
            atmosphericOverlayColor = Color(0x1AF59E0B),
            boatHullColors = listOf(Color(0xFF92400E), Color(0xFF451A03), Color(0xFF78350F)),
            boatTrimColor = Color(0xFFFBBF24),
            boatAccentGlow = Color(0xFFF59E0B),
            boatOarColor = Color(0xFF7C2D12),
            sailButtonColors = listOf(Color(0xFFFBBF24), Color(0xFFF59E0B), Color(0xFFD97706), Color(0xFFB45309)),
            sailButtonBorderColors = listOf(Color(0xFFFEF3C7), Color(0xFFFDE68A), Color(0xFFB45309), Color(0xFF78350F)),
            sailButtonGlow = Color(0x99F59E0B),
            headerBackgroundColors = listOf(Color(0xF2301407), Color(0xF81C0B03)),
            headerBorderColors = listOf(Color(0xFFF59E0B), Color(0xFFFBBF24), Color(0xFFB45309)),
            headerAccentColor = Color(0xFFFBBF24),
            headerSurfaceColor = Color(0xCC3D1A08),
            headerTextColor = Color(0xFFFEF3C7),
            headerTimerColor = Color(0xFFFDE68A),
            actionButtonColors = listOf(Color(0xF03B1A07), Color(0xF0220F03)),
            actionButtonBorder = Color(0xFFF59E0B),
            actionButtonTextColor = Color(0xFFFEF3C7),
            objectTokenBackgroundColors = listOf(Color(0xF03B1A07), Color(0xF0220F03)),
            objectTokenBorderColor = Color(0xFFF59E0B),
            objectPlaqueColor = Color(0xF0301407),
            objectPlaqueBorderColor = Color(0xFFF59E0B),
            objectNameColor = Color(0xFFFEF3C7),
            objectInteractGlow = Color(0xFFFBBF24),
            objectAddBadgeColor = Color(0xFFD97706),
            farmerTokenBackgroundColors = listOf(Color(0xF05C2B0C), Color(0xF0381604)),
            farmerTokenBorderColor = Color(0xFFFBBF24),
            farmerNameColor = Color(0xFFFEF3C7),
            farmerStatusColor = Color(0xFFFDE68A),
            passengerBerthBackgroundColors = listOf(Color(0xFF451A03), Color(0xFF2E0F02)),
            passengerBerthBorderColor = Color(0xFFF59E0B),
            confettiColors = listOf(
                Color(0xFFEA580C), // Maple Orange
                Color(0xFFF59E0B), // Golden Amber
                Color(0xFFDC2626), // Autumn Crimson
                Color(0xFFD97706), // Russet Orange
                Color(0xFFFBBF24), // Harvest Gold
                Color(0xFFFFD700), // Pure Gold
                Color(0xFFB45309), // Copper Brown
                Color(0xFFFEF3C7), // Warm Champagne
                Color(0xFFFFFFFF)  // Sparkle White
            )
        )

        val ALPINE_PEAKS = LevelTheme(
            id = "alpine_peaks",
            name = "Alpine Peaks",
            tagline = "Crisp glacial breeze across snow-capped heights",
            iconEmoji = "🏔️",
            backgroundDrawableRes = R.drawable.bg_alpine_river,
            weatherEffect = WeatherEffectType.ALPINE_MIST,
            flotsamType = FlotsamType.PINE_SPRIGS,
            waterGradientTop = Color(0xFF06B6D4),
            waterGradientBottom = Color(0xFF1E3A8A),
            waveColor = Color(0xFFF0FDF4),
            flotsamColor = Color(0xFF0F766E),
            dockWoodTop = Color(0xFF475569),
            dockWoodBottom = Color(0xFF1E293B),
            dockBorder = Color(0xFFCBD5E1),
            dockLanternGlow = Color(0xFF67E8F9),
            bankAccentColor = Color(0xFF38BDF8),
            raftWoodTone = Color(0xFF64748B),
            raftRopeColor = Color(0xFFCBD5E1),
            badgeBgColor = Color(0xFF0C1929),
            badgeTextColor = Color(0xFF7DD3FC),
            atmosphericOverlayColor = Color(0x1238BDF8),
            boatHullColors = listOf(Color(0xFF475569), Color(0xFF1E293B), Color(0xFF334155)),
            boatTrimColor = Color(0xFF7DD3FC),
            boatAccentGlow = Color(0xFF38BDF8),
            boatOarColor = Color(0xFF334155),
            sailButtonColors = listOf(Color(0xFF38BDF8), Color(0xFF0EA5E9), Color(0xFF0284C7), Color(0xFF0369A1)),
            sailButtonBorderColors = listOf(Color(0xFFE0F2FE), Color(0xFF7DD3FC), Color(0xFF0284C7), Color(0xFF0C4A6E)),
            sailButtonGlow = Color(0x990284C7),
            headerBackgroundColors = listOf(Color(0xF20B2338), Color(0xF8061625)),
            headerBorderColors = listOf(Color(0xFF38BDF8), Color(0xFF7DD3FC), Color(0xFF0369A1)),
            headerAccentColor = Color(0xFF38BDF8),
            headerSurfaceColor = Color(0xCC0F2E4A),
            headerTextColor = Color(0xFFE0F2FE),
            headerTimerColor = Color(0xFF7DD3FC),
            actionButtonColors = listOf(Color(0xF00D3352), Color(0xF0071F33)),
            actionButtonBorder = Color(0xFF38BDF8),
            actionButtonTextColor = Color(0xFFE0F2FE),
            objectTokenBackgroundColors = listOf(Color(0xF00D3352), Color(0xF0071F33)),
            objectTokenBorderColor = Color(0xFF38BDF8),
            objectPlaqueColor = Color(0xF00F2E4A),
            objectPlaqueBorderColor = Color(0xFF38BDF8),
            objectNameColor = Color(0xFFE0F2FE),
            objectInteractGlow = Color(0xFF38BDF8),
            objectAddBadgeColor = Color(0xFF0284C7),
            farmerTokenBackgroundColors = listOf(Color(0xF01A4B75), Color(0xF00D2D49)),
            farmerTokenBorderColor = Color(0xFF7DD3FC),
            farmerNameColor = Color(0xFFE0F2FE),
            farmerStatusColor = Color(0xFF7DD3FC),
            passengerBerthBackgroundColors = listOf(Color(0xFF1E293B), Color(0xFF0F172A)),
            passengerBerthBorderColor = Color(0xFF38BDF8),
            confettiColors = listOf(
                Color(0xFF06B6D4), // Glacial Cyan
                Color(0xFF38BDF8), // Ice Blue
                Color(0xFF0EA5E9), // Bright Cerulean
                Color(0xFF67E8F9), // Pale Frost
                Color(0xFF0284C7), // Alpine Sapphire
                Color(0xFF7DD3FC), // Sky Frost
                Color(0xFFA7F3D0), // Glacial Mint
                Color(0xFFFFFFFF), // Snowflake White
                Color(0xFFFFD700)  // Golden Sun Star
            )
        )

        val SAVANNAH_SUN = LevelTheme(
            id = "savannah_sun",
            name = "Savannah Sun",
            tagline = "Terracotta heat haze over golden waters",
            iconEmoji = "🦁",
            backgroundDrawableRes = R.drawable.bg_savannah_river,
            weatherEffect = WeatherEffectType.SAVANNAH_DUST,
            flotsamType = FlotsamType.SAVANNAH_REEDS,
            waterGradientTop = Color(0xFF0284C7),
            waterGradientBottom = Color(0xFFD97706),
            waveColor = Color(0xFFFFFBEB),
            flotsamColor = Color(0xFFB45309),
            dockWoodTop = Color(0xFF9A3412),
            dockWoodBottom = Color(0xFF5E1B07),
            dockBorder = Color(0xFFE5C158),
            dockLanternGlow = Color(0xFFF59E0B),
            bankAccentColor = Color(0xFFEA580C),
            raftWoodTone = Color(0xFFC2410C),
            raftRopeColor = Color(0xFF78350F),
            badgeBgColor = Color(0xFF26140B),
            badgeTextColor = Color(0xFFFED7AA),
            atmosphericOverlayColor = Color(0x18EA580C),
            boatHullColors = listOf(Color(0xFFC2410C), Color(0xFF5E1B07), Color(0xFF9A3412)),
            boatTrimColor = Color(0xFFFDE047),
            boatAccentGlow = Color(0xFFEA580C),
            boatOarColor = Color(0xFF7C2D12),
            sailButtonColors = listOf(Color(0xFFFB923C), Color(0xFFEA580C), Color(0xFFC2410C), Color(0xFF9A3412)),
            sailButtonBorderColors = listOf(Color(0xFFFFEDD5), Color(0xFFFDBA74), Color(0xFF9A3412), Color(0xFF5E1B07)),
            sailButtonGlow = Color(0x99EA580C),
            headerBackgroundColors = listOf(Color(0xF2331407), Color(0xF81E0A03)),
            headerBorderColors = listOf(Color(0xFFEA580C), Color(0xFFFDE047), Color(0xFF9A3412)),
            headerAccentColor = Color(0xFFFB923C),
            headerSurfaceColor = Color(0xCC3E1809),
            headerTextColor = Color(0xFFFFEDD5),
            headerTimerColor = Color(0xFFFED7AA),
            actionButtonColors = listOf(Color(0xF03E1909), Color(0xF0240E04)),
            actionButtonBorder = Color(0xFFEA580C),
            actionButtonTextColor = Color(0xFFFFEDD5),
            objectTokenBackgroundColors = listOf(Color(0xF03E1909), Color(0xF0240E04)),
            objectTokenBorderColor = Color(0xFFEA580C),
            objectPlaqueColor = Color(0xF03E1809),
            objectPlaqueBorderColor = Color(0xFFEA580C),
            objectNameColor = Color(0xFFFFEDD5),
            objectInteractGlow = Color(0xFFFB923C),
            objectAddBadgeColor = Color(0xFFC2410C),
            farmerTokenBackgroundColors = listOf(Color(0xF0662208), Color(0xF03D1204)),
            farmerTokenBorderColor = Color(0xFFFDE047),
            farmerNameColor = Color(0xFFFFEDD5),
            farmerStatusColor = Color(0xFFFED7AA),
            passengerBerthBackgroundColors = listOf(Color(0xFF5E1B07), Color(0xFF3D1104)),
            passengerBerthBorderColor = Color(0xFFF97316),
            confettiColors = listOf(
                Color(0xFFF97316), // Savannah Orange
                Color(0xFFEA580C), // Terracotta Red
                Color(0xFFF59E0B), // Warm Sun Amber
                Color(0xFFFDE047), // Golden Yellow
                Color(0xFFFB923C), // Sunlit Coral
                Color(0xFFFFD700), // Pure Gold
                Color(0xFFC2410C), // Deep Ochre
                Color(0xFFFFFBEB), // Sahara Ivory
                Color(0xFFFFFFFF)  // Sparkle White
            )
        )

        val MIDNIGHT_STARLIGHT = LevelTheme(
            id = "midnight_starlight",
            name = "Midnight Starlight",
            tagline = "Bioluminescent glow under starry celestial dome",
            iconEmoji = "🌙",
            backgroundDrawableRes = R.drawable.bg_midnight_river,
            weatherEffect = WeatherEffectType.MIDNIGHT_FIREFLIES,
            flotsamType = FlotsamType.BIOLUMINESCENT_SPARKS,
            waterGradientTop = Color(0xFF1E1B4B),
            waterGradientBottom = Color(0xFF020617),
            waveColor = Color(0xFF67E8F9),
            flotsamColor = Color(0xFF38BDF8),
            dockWoodTop = Color(0xFF1E293B),
            dockWoodBottom = Color(0xFF020617),
            dockBorder = Color(0xFFE5C158),
            dockLanternGlow = Color(0xFFFDE047),
            bankAccentColor = Color(0xFF6366F1),
            raftWoodTone = Color(0xFF0F172A),
            raftRopeColor = Color(0xFF475569),
            badgeBgColor = Color(0xFF0F1226),
            badgeTextColor = Color(0xFFA5B4FC),
            atmosphericOverlayColor = Color(0x221E1B4B),
            boatHullColors = listOf(Color(0xFF1E1B4B), Color(0xFF0F172A), Color(0xFF172554)),
            boatTrimColor = Color(0xFF67E8F9),
            boatAccentGlow = Color(0xFF6366F1),
            boatOarColor = Color(0xFF1E293B),
            sailButtonColors = listOf(Color(0xFF818CF8), Color(0xFF6366F1), Color(0xFF4F46E5), Color(0xFF3730A3)),
            sailButtonBorderColors = listOf(Color(0xFFE0E7FF), Color(0xFFA5B4FC), Color(0xFF4338CA), Color(0xFF1E1B4B)),
            sailButtonGlow = Color(0x996366F1),
            headerBackgroundColors = listOf(Color(0xF20F142D), Color(0xF8080B1C)),
            headerBorderColors = listOf(Color(0xFF6366F1), Color(0xFF67E8F9), Color(0xFF3730A3)),
            headerAccentColor = Color(0xFF818CF8),
            headerSurfaceColor = Color(0xCC182046),
            headerTextColor = Color(0xFFE0E7FF),
            headerTimerColor = Color(0xFFA5B4FC),
            actionButtonColors = listOf(Color(0xF01A224D), Color(0xF0101533)),
            actionButtonBorder = Color(0xFF6366F1),
            actionButtonTextColor = Color(0xFFE0E7FF),
            objectTokenBackgroundColors = listOf(Color(0xF01A224D), Color(0xF0101533)),
            objectTokenBorderColor = Color(0xFF6366F1),
            objectPlaqueColor = Color(0xF0182046),
            objectPlaqueBorderColor = Color(0xFF818CF8),
            objectNameColor = Color(0xFFE0E7FF),
            objectInteractGlow = Color(0xFF818CF8),
            objectAddBadgeColor = Color(0xFF6366F1),
            farmerTokenBackgroundColors = listOf(Color(0xF02A367B), Color(0xF0161C45)),
            farmerTokenBorderColor = Color(0xFF67E8F9),
            farmerNameColor = Color(0xFFE0E7FF),
            farmerStatusColor = Color(0xFFA5B4FC),
            passengerBerthBackgroundColors = listOf(Color(0xFF0F172A), Color(0xFF090D18)),
            passengerBerthBorderColor = Color(0xFF818CF8),
            confettiColors = listOf(
                Color(0xFF8B5CF6), // Royal Purple
                Color(0xFF6366F1), // Electric Indigo
                Color(0xFF67E8F9), // Bioluminescent Cyan
                Color(0xFF38BDF8), // Starlight Blue
                Color(0xFFD946EF), // Neon Magenta
                Color(0xFFA5B4FC), // Lavender Spark
                Color(0xFFFFD700), // Golden Star
                Color(0xFFFDE047), // Celestial Yellow
                Color(0xFFFFFFFF)  // Brilliant White
            )
        )

        val TWILIGHT_RAPIDS = LevelTheme(
            id = "twilight_rapids",
            name = "Twilight Rapids",
            tagline = "Mystical violet dusk cascading along jagged cliffs",
            iconEmoji = "🌋",
            backgroundDrawableRes = R.drawable.bg_twilight_river,
            weatherEffect = WeatherEffectType.TWILIGHT_MOTES,
            flotsamType = FlotsamType.TWILIGHT_PETALS,
            waterGradientTop = Color(0xFF701A75),
            waterGradientBottom = Color(0xFF1E1B4B),
            waveColor = Color(0xFFF472B6),
            flotsamColor = Color(0xFFE879F9),
            dockWoodTop = Color(0xFF581C87),
            dockWoodBottom = Color(0xFF2E1065),
            dockBorder = Color(0xFFE5C158),
            dockLanternGlow = Color(0xFFF43F5E),
            bankAccentColor = Color(0xFFA855F7),
            raftWoodTone = Color(0xFF6B21A8),
            raftRopeColor = Color(0xFFD946EF),
            badgeBgColor = Color(0xFF220E28),
            badgeTextColor = Color(0xFFF0ABFC),
            atmosphericOverlayColor = Color(0x1C701A75),
            boatHullColors = listOf(Color(0xFF6B21A8), Color(0xFF2E1065), Color(0xFF581C87)),
            boatTrimColor = Color(0xFFF472B6),
            boatAccentGlow = Color(0xFFA855F7),
            boatOarColor = Color(0xFF4C1D95),
            sailButtonColors = listOf(Color(0xFFC084FC), Color(0xFFA855F7), Color(0xFF9333EA), Color(0xFF7E22CE)),
            sailButtonBorderColors = listOf(Color(0xFFF3E8FF), Color(0xFFD8B4FE), Color(0xFF7E22CE), Color(0xFF581C87)),
            sailButtonGlow = Color(0x99A855F7),
            headerBackgroundColors = listOf(Color(0xF2270C30), Color(0xF816051E)),
            headerBorderColors = listOf(Color(0xFFA855F7), Color(0xFFF472B6), Color(0xFF7E22CE)),
            headerAccentColor = Color(0xFFC084FC),
            headerSurfaceColor = Color(0xCC350F42),
            headerTextColor = Color(0xFFF5D0FE),
            headerTimerColor = Color(0xFFF0ABFC),
            actionButtonColors = listOf(Color(0xF0381047), Color(0xF021082D)),
            actionButtonBorder = Color(0xFFA855F7),
            actionButtonTextColor = Color(0xFFF5D0FE),
            objectTokenBackgroundColors = listOf(Color(0xF0381047), Color(0xF021082D)),
            objectTokenBorderColor = Color(0xFFA855F7),
            objectPlaqueColor = Color(0xF0350F42),
            objectPlaqueBorderColor = Color(0xFFA855F7),
            objectNameColor = Color(0xFFF5D0FE),
            objectInteractGlow = Color(0xFFC084FC),
            objectAddBadgeColor = Color(0xFF9333EA),
            farmerTokenBackgroundColors = listOf(Color(0xF054166E), Color(0xF0310B42)),
            farmerTokenBorderColor = Color(0xFFF472B6),
            farmerNameColor = Color(0xFFF5D0FE),
            farmerStatusColor = Color(0xFFF0ABFC),
            passengerBerthBackgroundColors = listOf(Color(0xFF2E1065), Color(0xFF1B073D)),
            passengerBerthBorderColor = Color(0xFFA855F7),
            confettiColors = listOf(
                Color(0xFFA855F7), // Mystic Purple
                Color(0xFFD946EF), // Twilight Magenta
                Color(0xFFC084FC), // Orchid Glow
                Color(0xFFF472B6), // Twilight Rose
                Color(0xFFF59E0B), // Sunset Amber
                Color(0xFF9333EA), // Royal Violet
                Color(0xFFFB7185), // Sunset Coral
                Color(0xFFFFFFFF), // Sparkling White
                Color(0xFFFFD700)  // Golden Ember
            )
        )

        val DESERT_OASIS = LevelTheme(
            id = "desert_oasis",
            name = "Desert Oasis",
            tagline = "Mirage-kissed turquoise water fringed with date palms",
            iconEmoji = "🌴",
            backgroundDrawableRes = R.drawable.bg_oasis_river,
            weatherEffect = WeatherEffectType.OASIS_MIRAGE,
            flotsamType = FlotsamType.OASIS_BLOOMS,
            waterGradientTop = Color(0xFF0284C7),
            waterGradientBottom = Color(0xFF0F766E),
            waveColor = Color(0xFFFEF3C7),
            flotsamColor = Color(0xFFF43F5E),
            dockWoodTop = Color(0xFF78350F),
            dockWoodBottom = Color(0xFF451A03),
            dockBorder = Color(0xFFE5C158),
            dockLanternGlow = Color(0xFFF59E0B),
            bankAccentColor = Color(0xFF14B8A6),
            raftWoodTone = Color(0xFF92400E),
            raftRopeColor = Color(0xFFD97706),
            badgeBgColor = Color(0xFF0B1F22),
            badgeTextColor = Color(0xFF5EEAD4),
            atmosphericOverlayColor = Color(0x140D9488),
            boatHullColors = listOf(Color(0xFF92400E), Color(0xFF451A03), Color(0xFF78350F)),
            boatTrimColor = Color(0xFF2DD4BF),
            boatAccentGlow = Color(0xFF14B8A6),
            boatOarColor = Color(0xFF713F12),
            sailButtonColors = listOf(Color(0xFF2DD4BF), Color(0xFF14B8A6), Color(0xFF0D9488), Color(0xFF0F766E)),
            sailButtonBorderColors = listOf(Color(0xFFCCFBF1), Color(0xFF5EEAD4), Color(0xFF0F766E), Color(0xFF115E59)),
            sailButtonGlow = Color(0x9914B8A6),
            headerBackgroundColors = listOf(Color(0xF20D2826), Color(0xF8071A19)),
            headerBorderColors = listOf(Color(0xFF2DD4BF), Color(0xFFFACC15), Color(0xFF0F766E)),
            headerAccentColor = Color(0xFF2DD4BF),
            headerSurfaceColor = Color(0xCC123835),
            headerTextColor = Color(0xFFCCFBF1),
            headerTimerColor = Color(0xFF5EEAD4),
            actionButtonColors = listOf(Color(0xF00F3633), Color(0xF0082220)),
            actionButtonBorder = Color(0xFF2DD4BF),
            actionButtonTextColor = Color(0xFFCCFBF1),
            objectTokenBackgroundColors = listOf(Color(0xF00F3633), Color(0xF0082220)),
            objectTokenBorderColor = Color(0xFF2DD4BF),
            objectPlaqueColor = Color(0xF0123835),
            objectPlaqueBorderColor = Color(0xFF2DD4BF),
            objectNameColor = Color(0xFFCCFBF1),
            objectInteractGlow = Color(0xFF2DD4BF),
            objectAddBadgeColor = Color(0xFF0D9488),
            farmerTokenBackgroundColors = listOf(Color(0xF018504B), Color(0xF00D332F)),
            farmerTokenBorderColor = Color(0xFF5EEAD4),
            farmerNameColor = Color(0xFFCCFBF1),
            farmerStatusColor = Color(0xFF5EEAD4),
            passengerBerthBackgroundColors = listOf(Color(0xFF133633), Color(0xFF0A201E)),
            passengerBerthBorderColor = Color(0xFF2DD4BF),
            confettiColors = listOf(
                Color(0xFF14B8A6), // Lagoon Turquoise
                Color(0xFF2DD4BF), // Seafoam Mint
                Color(0xFF0D9488), // Deep Teal
                Color(0xFFF43F5E), // Oasis Rose
                Color(0xFFFACC15), // Palm Gold
                Color(0xFFFB7185), // Tropical Coral
                Color(0xFF5EEAD4), // Pale Turquoise
                Color(0xFFFFFFFF), // Sparkling Sand White
                Color(0xFFFFD700)  // Golden Sun
            )
        )

        val AURORA_BOREALIS = LevelTheme(
            id = "aurora_borealis",
            name = "Aurora Borealis",
            tagline = "Prismatic polar ribbons reflecting upon glacial mirror",
            iconEmoji = "🌌",
            backgroundDrawableRes = R.drawable.bg_aurora_river,
            weatherEffect = WeatherEffectType.AURORA_SHIMMER,
            flotsamType = FlotsamType.AURORA_CRYSTALS,
            waterGradientTop = Color(0xFF047857),
            waterGradientBottom = Color(0xFF312E81),
            waveColor = Color(0xFF6EE7B7),
            flotsamColor = Color(0xFFA7F3D0),
            dockWoodTop = Color(0xFF1E293B),
            dockWoodBottom = Color(0xFF0F172A),
            dockBorder = Color(0xFFE5C158),
            dockLanternGlow = Color(0xFF6EE7B7),
            bankAccentColor = Color(0xFF10B981),
            raftWoodTone = Color(0xFF1E293B),
            raftRopeColor = Color(0xFF67E8F9),
            badgeBgColor = Color(0xFF081C1D),
            badgeTextColor = Color(0xFFA7F3D0),
            atmosphericOverlayColor = Color(0x1A059669),
            boatHullColors = listOf(Color(0xFF1E293B), Color(0xFF0F172A), Color(0xFF172554)),
            boatTrimColor = Color(0xFF67E8F9),
            boatAccentGlow = Color(0xFF34D399),
            boatOarColor = Color(0xFF1E293B),
            sailButtonColors = listOf(Color(0xFF34D399), Color(0xFF10B981), Color(0xFF059669), Color(0xFF047857)),
            sailButtonBorderColors = listOf(Color(0xFFD1FAE5), Color(0xFF6EE7B7), Color(0xFF047857), Color(0xFF064E3B)),
            sailButtonGlow = Color(0x9910B981),
            headerBackgroundColors = listOf(Color(0xF2092620), Color(0xF8051713)),
            headerBorderColors = listOf(Color(0xFF34D399), Color(0xFFA78BFA), Color(0xFF059669)),
            headerAccentColor = Color(0xFF34D399),
            headerSurfaceColor = Color(0xCC0E382E),
            headerTextColor = Color(0xFFD1FAE5),
            headerTimerColor = Color(0xFFA7F3D0),
            actionButtonColors = listOf(Color(0xF00D362C), Color(0xF007221C)),
            actionButtonBorder = Color(0xFF34D399),
            actionButtonTextColor = Color(0xFFD1FAE5),
            objectTokenBackgroundColors = listOf(Color(0xF00D362C), Color(0xF007221C)),
            objectTokenBorderColor = Color(0xFF34D399),
            objectPlaqueColor = Color(0xF00E382E),
            objectPlaqueBorderColor = Color(0xFF34D399),
            objectNameColor = Color(0xFFD1FAE5),
            objectInteractGlow = Color(0xFF34D399),
            objectAddBadgeColor = Color(0xFF059669),
            farmerTokenBackgroundColors = listOf(Color(0xF0125444), Color(0xF009362A)),
            farmerTokenBorderColor = Color(0xFFA7F3D0),
            farmerNameColor = Color(0xFFD1FAE5),
            farmerStatusColor = Color(0xFFA7F3D0),
            passengerBerthBackgroundColors = listOf(Color(0xFF0B192C), Color(0xFF07111E)),
            passengerBerthBorderColor = Color(0xFF67E8F9),
            confettiColors = listOf(
                Color(0xFF10B981), // Aurora Emerald
                Color(0xFF34D399), // Radiant Mint
                Color(0xFF8B5CF6), // Prismatic Violet
                Color(0xFF06B6D4), // Polar Cyan
                Color(0xFF67E8F9), // Ice Sparkle
                Color(0xFFEC4899), // Aurora Magenta
                Color(0xFFA78BFA), // Lavender Shimmer
                Color(0xFF6EE7B7), // Seafoam Aurora
                Color(0xFFFFFFFF)  // Glacial White
            )
        )

        @DrawableRes
        fun getLevelBackgroundDrawable(levelNumber: Int): Int {
            return when (levelNumber) {
                1 -> R.drawable.img_level_1_bg
                2 -> R.drawable.img_level_2_bg
                3 -> R.drawable.img_level_3_bg
                4 -> R.drawable.img_level_4_bg
                5 -> R.drawable.img_level_5_bg
                6 -> R.drawable.img_level_6_bg
                7 -> R.drawable.img_level_7_bg
                8 -> R.drawable.img_level_8_bg
                9 -> R.drawable.img_level_9_bg
                10 -> R.drawable.img_level_10_bg
                11 -> R.drawable.img_level_11_bg
                12 -> R.drawable.img_level_12_bg
                13 -> R.drawable.img_level_13_bg
                14 -> R.drawable.img_level_14_bg
                15 -> R.drawable.img_level_15_bg
                16 -> R.drawable.img_level_16_bg
                17 -> R.drawable.img_level_17_bg
                18 -> R.drawable.img_level_18_bg
                19 -> R.drawable.img_level_19_bg
                20 -> R.drawable.img_level_20_bg
                21 -> R.drawable.img_level_21_bg
                22 -> R.drawable.img_level_22_bg
                23 -> R.drawable.img_level_23_bg
                24 -> R.drawable.img_level_24_bg
                25 -> R.drawable.img_level_25_bg
                26 -> R.drawable.img_level_26_bg
                27 -> R.drawable.img_level_27_bg
                28 -> R.drawable.img_level_28_bg
                29 -> R.drawable.img_level_29_bg
                30 -> R.drawable.img_level_30_bg
                31 -> R.drawable.img_level_31_bg
                32 -> R.drawable.img_level_32_bg
                33 -> R.drawable.img_level_33_bg
                34 -> R.drawable.img_level_34_bg
                35 -> R.drawable.img_level_35_bg
                36 -> R.drawable.img_level_36_bg
                37 -> R.drawable.img_level_37_bg
                38 -> R.drawable.img_level_38_bg
                39 -> R.drawable.img_level_39_bg
                40 -> R.drawable.img_level_40_bg
                41 -> R.drawable.img_level_41_bg
                42 -> R.drawable.img_level_42_bg
                43 -> R.drawable.img_level_43_bg
                44 -> R.drawable.img_level_44_bg
                45 -> R.drawable.img_level_45_bg
                46 -> R.drawable.img_level_46_bg
                47 -> R.drawable.img_level_47_bg
                48 -> R.drawable.img_level_48_bg
                49 -> R.drawable.img_level_49_bg
                50 -> R.drawable.img_level_50_bg
                51 -> R.drawable.img_level_51_bg
                52 -> R.drawable.img_level_52_bg
                53 -> R.drawable.img_level_53_bg
                54 -> R.drawable.img_level_54_bg
                55 -> R.drawable.img_level_55_bg
                56 -> R.drawable.img_level_56_bg
                57 -> R.drawable.img_level_57_bg
                58 -> R.drawable.img_level_58_bg
                59 -> R.drawable.img_level_59_bg
                60 -> R.drawable.img_level_60_bg
                61 -> R.drawable.img_level_61_bg
                62 -> R.drawable.img_level_62_bg
                63 -> R.drawable.img_level_63_bg
                64 -> R.drawable.img_level_64_bg
                65 -> R.drawable.img_level_65_bg
                66 -> R.drawable.img_level_66_bg
                67 -> R.drawable.img_level_67_bg
                68 -> R.drawable.img_level_68_bg
                69 -> R.drawable.img_level_69_bg
                70 -> R.drawable.img_level_70_bg
                71 -> R.drawable.img_level_71_bg
                72 -> R.drawable.img_level_72_bg
                73 -> R.drawable.img_level_73_bg
                74 -> R.drawable.img_level_74_bg
                75 -> R.drawable.img_level_75_bg
                76 -> R.drawable.img_level_76_bg
                77 -> R.drawable.img_level_77_bg
                78 -> R.drawable.img_level_78_bg
                79 -> R.drawable.img_level_79_bg
                80 -> R.drawable.img_level_80_bg
                81 -> R.drawable.img_level_81_bg
                82 -> R.drawable.img_level_82_bg
                83 -> R.drawable.img_level_83_bg
                84 -> R.drawable.img_level_84_bg
                85 -> R.drawable.img_level_85_bg
                86 -> R.drawable.img_level_86_bg
                87 -> R.drawable.img_level_87_bg
                88 -> R.drawable.img_level_88_bg
                89 -> R.drawable.img_level_89_bg
                90 -> R.drawable.img_level_90_bg
                else -> R.drawable.img_level_1_bg
            }
        }

        fun forScenario(scenario: PuzzleScenario): LevelTheme {
            return forLevel(scenario.levelNumber)
        }

        fun forLevel(levelNumber: Int): LevelTheme {
            val bg = getLevelBackgroundDrawable(levelNumber)
            return when (levelNumber) {
                1 -> SPRING_VALLEY.copy(
                    id = "lvl_1_spring_valley",
                    name = "Spring Valley",
                    tagline = "Gentle morning breeze over clover banks",
                    iconEmoji = "🌸",
                    backgroundDrawableRes = bg
                )
                2 -> AUTUMN_HARVEST.copy(
                    id = "lvl_2_autumn_harvest",
                    name = "Autumn Harvest",
                    tagline = "Golden leaves drifting upon amber ripples",
                    iconEmoji = "🍁",
                    backgroundDrawableRes = bg
                )
                3 -> ALPINE_PEAKS.copy(
                    id = "lvl_3_alpine_glaciers",
                    name = "Alpine Glaciers",
                    tagline = "Crisp glacial breeze across snow-capped heights",
                    iconEmoji = "🏔️",
                    backgroundDrawableRes = bg
                )
                4 -> SAVANNAH_SUN.copy(
                    id = "lvl_4_savannah_sun",
                    name = "Savannah Sun",
                    tagline = "Terracotta heat haze over golden waters",
                    iconEmoji = "🦁",
                    backgroundDrawableRes = bg
                )
                5 -> MIDNIGHT_STARLIGHT.copy(
                    id = "lvl_5_midnight_starlight",
                    name = "Midnight Starlight",
                    tagline = "Bioluminescent glow under starry celestial dome",
                    iconEmoji = "🌙",
                    backgroundDrawableRes = bg
                )
                6 -> TWILIGHT_RAPIDS.copy(
                    id = "lvl_6_twilight_rapids",
                    name = "Twilight Rapids",
                    tagline = "Mystical violet dusk cascading along jagged cliffs",
                    iconEmoji = "🌋",
                    backgroundDrawableRes = bg
                )
                7 -> DESERT_OASIS.copy(
                    id = "lvl_7_desert_oasis",
                    name = "Desert Oasis",
                    tagline = "Mirage-kissed turquoise water fringed with date palms",
                    iconEmoji = "🌴",
                    backgroundDrawableRes = bg
                )
                8 -> AURORA_BOREALIS.copy(
                    id = "lvl_8_aurora_borealis",
                    name = "Aurora Borealis",
                    tagline = "Prismatic polar ribbons reflecting upon glacial mirror",
                    iconEmoji = "🌌",
                    backgroundDrawableRes = bg
                )
                9 -> SPRING_VALLEY.copy(
                    id = "lvl_9_bamboo_forest",
                    name = "Bamboo Forest",
                    tagline = "Tranquil mist and jade waters in a quiet bamboo grove",
                    iconEmoji = "🎋",
                    backgroundDrawableRes = bg
                )
                10 -> SAVANNAH_SUN.copy(
                    id = "lvl_10_red_rock_canyon",
                    name = "Red Rock Canyon",
                    tagline = "Turquoise river carving through ancient crimson bluffs",
                    iconEmoji = "🏜️",
                    backgroundDrawableRes = bg
                )
                11 -> SPRING_VALLEY.copy(
                    id = "lvl_11_sunflower_fields",
                    name = "Sunflower Fields",
                    tagline = "Sun-drenched golden blooms stretching along azure shores",
                    iconEmoji = "🌻",
                    backgroundDrawableRes = bg
                )
                12 -> DESERT_OASIS.copy(
                    id = "lvl_12_tropical_lagoon",
                    name = "Tropical Lagoon",
                    tagline = "Cascading waterfalls plunging into crystal emerald pools",
                    iconEmoji = "🌺",
                    backgroundDrawableRes = bg
                )
                13 -> TWILIGHT_RAPIDS.copy(
                    id = "lvl_13_ancient_ruins",
                    name = "Ancient Ruins",
                    tagline = "Overgrown stone arches whispering forgotten river legends",
                    iconEmoji = "🏛️",
                    backgroundDrawableRes = bg
                )
                14 -> SPRING_VALLEY.copy(
                    id = "lvl_14_cherry_blossom",
                    name = "Cherry Blossom River",
                    tagline = "Pink sakura petals dancing upon soft gentle currents",
                    iconEmoji = "🌸",
                    backgroundDrawableRes = bg
                )
                15 -> TWILIGHT_RAPIDS.copy(
                    id = "lvl_15_hot_springs",
                    name = "Volcanic Hot Springs",
                    tagline = "Geothermal steam and glowing mineral pools in obsidian stone",
                    iconEmoji = "♨️",
                    backgroundDrawableRes = bg
                )
                16 -> MIDNIGHT_STARLIGHT.copy(
                    id = "lvl_16_luminescent_woods",
                    name = "Luminescent Woods",
                    tagline = "Glowing giant mushrooms casting violet light across the water",
                    iconEmoji = "🍄",
                    backgroundDrawableRes = bg
                )
                17 -> SPRING_VALLEY.copy(
                    id = "lvl_17_countryside_mill",
                    name = "Countryside Mill",
                    tagline = "Old wooden watermill turning lazily in pastoral meadows",
                    iconEmoji = "🌾",
                    backgroundDrawableRes = bg
                )
                18 -> ALPINE_PEAKS.copy(
                    id = "lvl_18_nordic_fjord",
                    name = "Nordic Fjord",
                    tagline = "Majestic sea cliffs rising steeply over deep glacial blue",
                    iconEmoji = "🌊",
                    backgroundDrawableRes = bg
                )
                19 -> SAVANNAH_SUN.copy(
                    id = "lvl_19_mangrove_estuary",
                    name = "Mangrove Estuary",
                    tagline = "Golden evening rays threading through tangled root waterways",
                    iconEmoji = "🌅",
                    backgroundDrawableRes = bg
                )
                20 -> ALPINE_PEAKS.copy(
                    id = "lvl_20_winter_frost",
                    name = "Winter Frost",
                    tagline = "Glittering icicles and snow-draped evergreens along frosty banks",
                    iconEmoji = "❄️",
                    backgroundDrawableRes = bg
                )
                21 -> ALPINE_PEAKS.copy(
                    id = "lvl_21_highland_glen",
                    name = "Highland Glen",
                    tagline = "Purple heather slopes rolling down to dark peaty mountain streams",
                    iconEmoji = "🏔️",
                    backgroundDrawableRes = bg
                )
                22 -> AUTUMN_HARVEST.copy(
                    id = "lvl_22_birch_grove",
                    name = "Golden Birch Grove",
                    tagline = "Slender silver trunks glowing with brilliant yellow canopy",
                    iconEmoji = "🍂",
                    backgroundDrawableRes = bg
                )
                23 -> SAVANNAH_SUN.copy(
                    id = "lvl_23_prairie_crossing",
                    name = "Wild Prairie",
                    tagline = "Endless sea of golden wheat whispering under big open sky",
                    iconEmoji = "🌾",
                    backgroundDrawableRes = bg
                )
                24 -> MIDNIGHT_STARLIGHT.copy(
                    id = "lvl_24_crystal_cavern",
                    name = "Crystal Cavern",
                    tagline = "Underground sapphire river illuminated by glowing stalactites",
                    iconEmoji = "💎",
                    backgroundDrawableRes = bg
                )
                25 -> DESERT_OASIS.copy(
                    id = "lvl_25_mediterranean_shore",
                    name = "Mediterranean Shore",
                    tagline = "Terraced olive orchards overlooking sparkling sunlit bays",
                    iconEmoji = "🫒",
                    backgroundDrawableRes = bg
                )
                26 -> AURORA_BOREALIS.copy(
                    id = "lvl_26_sky_isles",
                    name = "Floating Sky Isles",
                    tagline = "Ethereal crystal streams plunging from clouds into infinity",
                    iconEmoji = "☁️",
                    backgroundDrawableRes = bg
                )
                27 -> SPRING_VALLEY.copy(
                    id = "lvl_27_redwoods",
                    name = "Ancient Redwoods",
                    tagline = "Cathedral of colossal trees with sunbeams piercing mist",
                    iconEmoji = "🌲",
                    backgroundDrawableRes = bg
                )
                28 -> DESERT_OASIS.copy(
                    id = "lvl_28_sunset_dunes",
                    name = "Sunset Dunes",
                    tagline = "Rolling desert sands glowing rose-gold along cooling ripples",
                    iconEmoji = "🐪",
                    backgroundDrawableRes = bg
                )
                29 -> ALPINE_PEAKS.copy(
                    id = "lvl_29_arctic_icebergs",
                    name = "Arctic Icebergs",
                    tagline = "Carved sapphire ice drifting through silent northern waters",
                    iconEmoji = "🧊",
                    backgroundDrawableRes = bg
                )
                30 -> TWILIGHT_RAPIDS.copy(
                    id = "lvl_30_provence_lavender",
                    name = "Provence Lavender",
                    tagline = "Fragrant purple blossoms swaying beside sun-warmed canals",
                    iconEmoji = "🪻",
                    backgroundDrawableRes = bg
                )
                31 -> AUTUMN_HARVEST.copy(
                    id = "lvl_31_steampunk_canal",
                    name = "Steampunk Canal",
                    tagline = "Hissing brass valves and lantern-lit stone locks at twilight",
                    iconEmoji = "⚙️",
                    backgroundDrawableRes = bg
                )
                32 -> DESERT_OASIS.copy(
                    id = "lvl_32_coral_atoll",
                    name = "Coral Atoll",
                    tagline = "Turquoise waves lapping powdery white shores and coral reefs",
                    iconEmoji = "🐠",
                    backgroundDrawableRes = bg
                )
                33 -> MIDNIGHT_STARLIGHT.copy(
                    id = "lvl_33_cypress_bayou",
                    name = "Cypress Bayou",
                    tagline = "Spanish moss hanging low over mysterious mirror-dark waters",
                    iconEmoji = "🐊",
                    backgroundDrawableRes = bg
                )
                34 -> TWILIGHT_RAPIDS.copy(
                    id = "lvl_34_basalt_shoreline",
                    name = "Basalt Shoreline",
                    tagline = "Black volcanic sands steaming beside dark oceanic surges",
                    iconEmoji = "🌋",
                    backgroundDrawableRes = bg
                )
                35 -> MIDNIGHT_STARLIGHT.copy(
                    id = "lvl_35_fairy_brook",
                    name = "Fairy Brook",
                    tagline = "Magical shimmering dust and glowing blossoms along a fairy stream",
                    iconEmoji = "✨",
                    backgroundDrawableRes = bg
                )
                36 -> AUTUMN_HARVEST.copy(
                    id = "lvl_36_tuscan_vineyards",
                    name = "Tuscan Vineyards",
                    tagline = "Sun-drenched hillsides heavy with ripe purple wine grapes",
                    iconEmoji = "🍇",
                    backgroundDrawableRes = bg
                )
                37 -> TWILIGHT_RAPIDS.copy(
                    id = "lvl_37_thunderstorm_valley",
                    name = "Thunderstorm Valley",
                    tagline = "Electric lightning crackling across storm-darkened river waters",
                    iconEmoji = "⚡",
                    backgroundDrawableRes = bg
                )
                38 -> SAVANNAH_SUN.copy(
                    id = "lvl_38_nile_sunset",
                    name = "Nile Sunset",
                    tagline = "Towering sandstone temples silhouetted against amber skies",
                    iconEmoji = "🏺",
                    backgroundDrawableRes = bg
                )
                39 -> AURORA_BOREALIS.copy(
                    id = "lvl_39_crystal_palace",
                    name = "Crystal Ice Palace",
                    tagline = "Frosted crystalline spires glistening beneath polar twilights",
                    iconEmoji = "🏰",
                    backgroundDrawableRes = bg
                )
                40 -> MIDNIGHT_STARLIGHT.copy(
                    id = "lvl_40_astral_nebula",
                    name = "Astral Nebula",
                    tagline = "Cosmic stellar river flowing between glowing planetary bodies",
                    iconEmoji = "🪐",
                    backgroundDrawableRes = bg
                )
                41 -> SPRING_VALLEY.copy(
                    id = "lvl_41_lotus_lagoon",
                    name = "Lotus Lagoon",
                    tagline = "Sacred floating lotus blossoms and vibrant koi pond streams",
                    iconEmoji = "🪷",
                    backgroundDrawableRes = bg
                )
                42 -> SAVANNAH_SUN.copy(
                    id = "lvl_42_african_veldt",
                    name = "African Veldt",
                    tagline = "Ancient baobabs casting long sunset shadows across watering holes",
                    iconEmoji = "🦏",
                    backgroundDrawableRes = bg
                )
                43 -> TWILIGHT_RAPIDS.copy(
                    id = "lvl_43_amethyst_geode",
                    name = "Amethyst Geode",
                    tagline = "Radiant purple crystal chambers reflected in subterranean pools",
                    iconEmoji = "🔮",
                    backgroundDrawableRes = bg
                )
                44 -> SPRING_VALLEY.copy(
                    id = "lvl_44_mayan_rapids",
                    name = "Mayan Rapids",
                    tagline = "Vibrant jungle river rushing past moss-covered ancient pyramids",
                    iconEmoji = "🗿",
                    backgroundDrawableRes = bg
                )
                45 -> AUTUMN_HARVEST.copy(
                    id = "lvl_45_weeping_willow",
                    name = "Weeping Willow Lake",
                    tagline = "Golden autumn leaves floating lazily under drooping willow boughs",
                    iconEmoji = "🍃",
                    backgroundDrawableRes = bg
                )
                46 -> AURORA_BOREALIS.copy(
                    id = "lvl_46_mount_olympus",
                    name = "Mount Olympus",
                    tagline = "Golden marble colonnades bathed in celestial sunlight",
                    iconEmoji = "⚡",
                    backgroundDrawableRes = bg
                )
                47 -> AURORA_BOREALIS.copy(
                    id = "lvl_47_rainbow_glaciers",
                    name = "Rainbow Glaciers",
                    tagline = "Prismatic light refracting into spectral hues through frozen cliffs",
                    iconEmoji = "🌈",
                    backgroundDrawableRes = bg
                )
                48 -> SAVANNAH_SUN.copy(
                    id = "lvl_48_crimson_rapids",
                    name = "Crimson Rapids",
                    tagline = "Fiery canyon walls framing raging white-water rapids at dusk",
                    iconEmoji = "🦅",
                    backgroundDrawableRes = bg
                )
                49 -> MIDNIGHT_STARLIGHT.copy(
                    id = "lvl_49_celestial_river",
                    name = "Celestial River",
                    tagline = "Dreamlike starry river woven from glowing astral constellations",
                    iconEmoji = "✨",
                    backgroundDrawableRes = bg
                )
                50 -> AURORA_BOREALIS.copy(
                    id = "lvl_50_masters_pantheon",
                    name = "The Master's Pantheon",
                    tagline = "Triumphal golden palace gates spanning an epic legendary river",
                    iconEmoji = "👑",
                    backgroundDrawableRes = bg
                )
                51 -> SPRING_VALLEY.copy(
                    id = "lvl_51_bamboo_grove",
                    name = "Bamboo Grove",
                    tagline = "Towering jade stalks and misty koi streams",
                    iconEmoji = "🎋",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.SPRING_PETALS,
                    flotsamType = FlotsamType.LILY_PADS,
                    waterGradientTop = Color(0xFF047857),
                    waterGradientBottom = Color(0xFF064E3B),
                    waveColor = Color(0xFFA7F3D0),
                    flotsamColor = Color(0xFF34D399),
                    dockWoodTop = Color(0xFF3F6212),
                    dockWoodBottom = Color(0xFF14532D),
                    dockBorder = Color(0xFF86EFAC),
                    bankAccentColor = Color(0xFF10B981),
                    boatHullColors = listOf(Color(0xFF365314), Color(0xFF14532D), Color(0xFF166534)),
                    boatTrimColor = Color(0xFF86EFAC),
                    sailButtonColors = listOf(Color(0xFF34D399), Color(0xFF10B981), Color(0xFF059669), Color(0xFF047857))
                )
                52 -> DESERT_OASIS.copy(
                    id = "lvl_52_savannah_dunes",
                    name = "Golden Savannah Dunes",
                    tagline = "Amber rolling sands and acacia mirages at noon",
                    iconEmoji = "🏜️",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.SAVANNAH_DUST,
                    flotsamType = FlotsamType.SAVANNAH_REEDS,
                    waterGradientTop = Color(0xFF0284C7),
                    waterGradientBottom = Color(0xFFB45309),
                    waveColor = Color(0xFFFEF3C7),
                    flotsamColor = Color(0xFFD97706),
                    dockWoodTop = Color(0xFFB45309),
                    dockWoodBottom = Color(0xFF78350F),
                    dockBorder = Color(0xFFFDE68A),
                    bankAccentColor = Color(0xFFF59E0B),
                    boatHullColors = listOf(Color(0xFF92400E), Color(0xFF78350F), Color(0xFF451A03)),
                    boatTrimColor = Color(0xFFFDE68A),
                    sailButtonColors = listOf(Color(0xFFFBBF24), Color(0xFFF59E0B), Color(0xFFD97706), Color(0xFFB45309))
                )
                53 -> ALPINE_PEAKS.copy(
                    id = "lvl_53_coral_archipelago",
                    name = "Coral Archipelago",
                    tagline = "Turquoise waves lapping powdered coral reefs",
                    iconEmoji = "🏝️",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.OASIS_MIRAGE,
                    flotsamType = FlotsamType.OASIS_BLOOMS,
                    waterGradientTop = Color(0xFF06B6D4),
                    waterGradientBottom = Color(0xFF0F766E),
                    waveColor = Color(0xFFCCFBF1),
                    flotsamColor = Color(0xFFF43F5E),
                    dockWoodTop = Color(0xFFE2E8F0),
                    dockWoodBottom = Color(0xFF94A3B8),
                    dockBorder = Color(0xFF38BDF8),
                    bankAccentColor = Color(0xFF06B6D4),
                    boatHullColors = listOf(Color(0xFFF1F5F9), Color(0xFFCBD5E1), Color(0xFF0891B2)),
                    boatTrimColor = Color(0xFF38BDF8),
                    sailButtonColors = listOf(Color(0xFF38BDF8), Color(0xFF0EA5E9), Color(0xFF0284C7), Color(0xFF0369A1))
                )
                54 -> SAVANNAH_SUN.copy(
                    id = "lvl_54_volcanic_caldera",
                    name = "Volcanic Caldera",
                    tagline = "Obsidian basalt cliffs and glowing magma streams",
                    iconEmoji = "🌋",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.SAVANNAH_DUST,
                    flotsamType = FlotsamType.SAVANNAH_REEDS,
                    waterGradientTop = Color(0xFFDC2626),
                    waterGradientBottom = Color(0xFF450A0A),
                    waveColor = Color(0xFFFDBA74),
                    flotsamColor = Color(0xFFEF4444),
                    dockWoodTop = Color(0xFF262626),
                    dockWoodBottom = Color(0xFF0A0A0A),
                    dockBorder = Color(0xFFEF4444),
                    dockLanternGlow = Color(0xFFF97316),
                    bankAccentColor = Color(0xFFF97316),
                    boatHullColors = listOf(Color(0xFF450A0A), Color(0xFF171717), Color(0xFF7F1D1D)),
                    boatTrimColor = Color(0xFFF97316),
                    sailButtonColors = listOf(Color(0xFFF97316), Color(0xFFEA580C), Color(0xFFC2410C), Color(0xFF9A3412))
                )
                55 -> SPRING_VALLEY.copy(
                    id = "lvl_55_cherry_blossom",
                    name = "Cherry Blossom Pagoda",
                    tagline = "Sakura petals drifting past ancient pagoda bridges",
                    iconEmoji = "🌸",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.SPRING_PETALS,
                    flotsamType = FlotsamType.TWILIGHT_PETALS,
                    waterGradientTop = Color(0xFFEC4899),
                    waterGradientBottom = Color(0xFF831843),
                    waveColor = Color(0xFFFCE7F3),
                    flotsamColor = Color(0xFFF472B6),
                    dockWoodTop = Color(0xFF991B1B),
                    dockWoodBottom = Color(0xFF450A0A),
                    dockBorder = Color(0xFFFBCFE8),
                    bankAccentColor = Color(0xFFF472B6),
                    boatHullColors = listOf(Color(0xFF881337), Color(0xFF4C0519), Color(0xFFBE123C)),
                    boatTrimColor = Color(0xFFFBCFE8),
                    sailButtonColors = listOf(Color(0xFFF472B6), Color(0xFFEC4899), Color(0xFFDB2777), Color(0xFFBE185D))
                )
                56 -> SPRING_VALLEY.copy(
                    id = "lvl_56_deep_rainforest",
                    name = "Deep Rainforest Sanctuary",
                    tagline = "Lush emerald canopy and roaring jungle cascades",
                    iconEmoji = "🦜",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.SPRING_PETALS,
                    flotsamType = FlotsamType.LILY_PADS,
                    waterGradientTop = Color(0xFF059669),
                    waterGradientBottom = Color(0xFF064E3B),
                    waveColor = Color(0xFFD1FAE5),
                    flotsamColor = Color(0xFF10B981),
                    dockWoodTop = Color(0xFF14532D),
                    dockWoodBottom = Color(0xFF052E16),
                    dockBorder = Color(0xFF6EE7B7),
                    bankAccentColor = Color(0xFF34D399),
                    boatHullColors = listOf(Color(0xFF166534), Color(0xFF14532D), Color(0xFF052E16)),
                    boatTrimColor = Color(0xFF6EE7B7),
                    sailButtonColors = listOf(Color(0xFF34D399), Color(0xFF10B981), Color(0xFF059669), Color(0xFF047857))
                )
                57 -> ALPINE_PEAKS.copy(
                    id = "lvl_57_glacial_fjord",
                    name = "Glacial Fjord",
                    tagline = "Towering blue icebergs mirrored in crystal waters",
                    iconEmoji = "🧊",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.ALPINE_MIST,
                    flotsamType = FlotsamType.AURORA_CRYSTALS,
                    waterGradientTop = Color(0xFF0284C7),
                    waterGradientBottom = Color(0xFF0C4A6E),
                    waveColor = Color(0xFFE0F2FE),
                    flotsamColor = Color(0xFF38BDF8),
                    dockWoodTop = Color(0xFF334155),
                    dockWoodBottom = Color(0xFF0F172A),
                    dockBorder = Color(0xFFBAE6FD),
                    bankAccentColor = Color(0xFF38BDF8),
                    boatHullColors = listOf(Color(0xFF1E293B), Color(0xFF0F172A), Color(0xFF334155)),
                    boatTrimColor = Color(0xFF7DD3FC),
                    sailButtonColors = listOf(Color(0xFF38BDF8), Color(0xFF0EA5E9), Color(0xFF0284C7), Color(0xFF0369A1))
                )
                58 -> AUTUMN_HARVEST.copy(
                    id = "lvl_58_redwood_valley",
                    name = "Twilight Redwood Valley",
                    tagline = "Ancient timber giants glowing in golden twilight",
                    iconEmoji = "🌲",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.AUTUMN_LEAVES,
                    flotsamType = FlotsamType.PINE_SPRIGS,
                    waterGradientTop = Color(0xFFB45309),
                    waterGradientBottom = Color(0xFF451A03),
                    waveColor = Color(0xFFFEF3C7),
                    flotsamColor = Color(0xFFD97706),
                    dockWoodTop = Color(0xFF78350F),
                    dockWoodBottom = Color(0xFF451A03),
                    dockBorder = Color(0xFFFDE68A),
                    bankAccentColor = Color(0xFFF59E0B),
                    boatHullColors = listOf(Color(0xFF92400E), Color(0xFF78350F), Color(0xFF451A03)),
                    boatTrimColor = Color(0xFFFDE68A),
                    sailButtonColors = listOf(Color(0xFFFBBF24), Color(0xFFF59E0B), Color(0xFFD97706), Color(0xFFB45309))
                )
                59 -> MIDNIGHT_STARLIGHT.copy(
                    id = "lvl_59_bioluminescent_cavern",
                    name = "Bioluminescent Cavern",
                    tagline = "Subterranean azure glow reflecting off crystal pools",
                    iconEmoji = "🌌",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.MIDNIGHT_FIREFLIES,
                    flotsamType = FlotsamType.BIOLUMINESCENT_SPARKS,
                    waterGradientTop = Color(0xFF0E7490),
                    waterGradientBottom = Color(0xFF082F49),
                    waveColor = Color(0xFF67E8F9),
                    flotsamColor = Color(0xFF22D3EE),
                    dockWoodTop = Color(0xFF164E63),
                    dockWoodBottom = Color(0xFF082F49),
                    dockBorder = Color(0xFF67E8F9),
                    bankAccentColor = Color(0xFF06B6D4),
                    boatHullColors = listOf(Color(0xFF0E7490), Color(0xFF155E75), Color(0xFF082F49)),
                    boatTrimColor = Color(0xFF67E8F9),
                    sailButtonColors = listOf(Color(0xFF22D3EE), Color(0xFF06B6D4), Color(0xFF0891B2), Color(0xFF0E7490))
                )
                60 -> DESERT_OASIS.copy(
                    id = "lvl_60_atlantis_ruins",
                    name = "Sunken Atlantis Ruins",
                    tagline = "Submerged golden aqueducts and marble archways",
                    iconEmoji = "🏛️",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.OASIS_MIRAGE,
                    flotsamType = FlotsamType.OASIS_BLOOMS,
                    waterGradientTop = Color(0xFF0D9488),
                    waterGradientBottom = Color(0xFF115E59),
                    waveColor = Color(0xFFCCFBF1),
                    flotsamColor = Color(0xFF2DD4BF),
                    dockWoodTop = Color(0xFFF1F5F9),
                    dockWoodBottom = Color(0xFF64748B),
                    dockBorder = Color(0xFFFDE047),
                    bankAccentColor = Color(0xFF14B8A6),
                    boatHullColors = listOf(Color(0xFFE2E8F0), Color(0xFF94A3B8), Color(0xFF0F766E)),
                    boatTrimColor = Color(0xFFFDE047),
                    sailButtonColors = listOf(Color(0xFF2DD4BF), Color(0xFF14B8A6), Color(0xFF0D9488), Color(0xFF0F766E))
                )
                61 -> TWILIGHT_RAPIDS.copy(
                    id = "lvl_61_heather_moors",
                    name = "Highland Heather Moors",
                    tagline = "Purple heather valleys and mysterious highland mist",
                    iconEmoji = "🪻",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.TWILIGHT_MOTES,
                    flotsamType = FlotsamType.TWILIGHT_PETALS,
                    waterGradientTop = Color(0xFF7E22CE),
                    waterGradientBottom = Color(0xFF3B0764),
                    waveColor = Color(0xFFF3E8FF),
                    flotsamColor = Color(0xFFC084FC),
                    dockWoodTop = Color(0xFF581C87),
                    dockWoodBottom = Color(0xFF3B0764),
                    dockBorder = Color(0xFFE9D5FF),
                    bankAccentColor = Color(0xFFA855F7),
                    boatHullColors = listOf(Color(0xFF6B21A8), Color(0xFF3B0764), Color(0xFF581C87)),
                    boatTrimColor = Color(0xFFE9D5FF),
                    sailButtonColors = listOf(Color(0xFFC084FC), Color(0xFFA855F7), Color(0xFF9333EA), Color(0xFF7E22CE))
                )
                62 -> AUTUMN_HARVEST.copy(
                    id = "lvl_62_maple_gorge",
                    name = "Autumn Maple Gorge",
                    tagline = "Scarlet maple leaves rushing over foaming rapids",
                    iconEmoji = "🍁",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.AUTUMN_LEAVES,
                    flotsamType = FlotsamType.AUTUMN_LEAVES,
                    waterGradientTop = Color(0xFFDC2626),
                    waterGradientBottom = Color(0xFF7C2D12),
                    waveColor = Color(0xFFFEE2E2),
                    flotsamColor = Color(0xFFEF4444),
                    dockWoodTop = Color(0xFF78350F),
                    dockWoodBottom = Color(0xFF451A03),
                    dockBorder = Color(0xFFFCA5A5),
                    bankAccentColor = Color(0xFFEF4444),
                    boatHullColors = listOf(Color(0xFF991B1B), Color(0xFF451A03), Color(0xFF78350F)),
                    boatTrimColor = Color(0xFFFCA5A5),
                    sailButtonColors = listOf(Color(0xFFF87171), Color(0xFFEF4444), Color(0xFFDC2626), Color(0xFFB91C1C))
                )
                63 -> SPRING_VALLEY.copy(
                    id = "lvl_63_rice_terraces",
                    name = "Emerald Rice Terraces",
                    tagline = "Mirrored sky waters cascading down emerald stairways",
                    iconEmoji = "🌾",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.SPRING_PETALS,
                    flotsamType = FlotsamType.LILY_PADS,
                    waterGradientTop = Color(0xFF10B981),
                    waterGradientBottom = Color(0xFF047857),
                    waveColor = Color(0xFFD1FAE5),
                    flotsamColor = Color(0xFF34D399),
                    dockWoodTop = Color(0xFF4D7C0F),
                    dockWoodBottom = Color(0xFF1A2E05),
                    dockBorder = Color(0xFFA3E635),
                    bankAccentColor = Color(0xFF84CC16),
                    boatHullColors = listOf(Color(0xFF3F6212), Color(0xFF1A2E05), Color(0xFF4D7C0F)),
                    boatTrimColor = Color(0xFFA3E635),
                    sailButtonColors = listOf(Color(0xFFA3E635), Color(0xFF84CC16), Color(0xFF65A30D), Color(0xFF4D7C0F))
                )
                64 -> TWILIGHT_RAPIDS.copy(
                    id = "lvl_64_dragon_peak",
                    name = "Dragon's Tooth Peak",
                    tagline = "Jagged alpine spires piercing lightning-lit clouds",
                    iconEmoji = "🐉",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.TWILIGHT_MOTES,
                    flotsamType = FlotsamType.BIOLUMINESCENT_SPARKS,
                    waterGradientTop = Color(0xFF4338CA),
                    waterGradientBottom = Color(0xFF1E1B4B),
                    waveColor = Color(0xFFC7D2FE),
                    flotsamColor = Color(0xFF818CF8),
                    dockWoodTop = Color(0xFF312E81),
                    dockWoodBottom = Color(0xFF1E1B4B),
                    dockBorder = Color(0xFFA5B4FC),
                    bankAccentColor = Color(0xFF6366F1),
                    boatHullColors = listOf(Color(0xFF3730A3), Color(0xFF1E1B4B), Color(0xFF312E81)),
                    boatTrimColor = Color(0xFFA5B4FC),
                    sailButtonColors = listOf(Color(0xFF818CF8), Color(0xFF6366F1), Color(0xFF4F46E5), Color(0xFF3730A3))
                )
                65 -> SAVANNAH_SUN.copy(
                    id = "lvl_65_mangrove_swamp",
                    name = "Whispering Mangrove Swamp",
                    tagline = "Winding swamp waterways under dense mangrove arches",
                    iconEmoji = "🐊",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.SAVANNAH_DUST,
                    flotsamType = FlotsamType.SAVANNAH_REEDS,
                    waterGradientTop = Color(0xFF0F766E),
                    waterGradientBottom = Color(0xFF134E4A),
                    waveColor = Color(0xFFCCFBF1),
                    flotsamColor = Color(0xFF2DD4BF),
                    dockWoodTop = Color(0xFF451A03),
                    dockWoodBottom = Color(0xFF1C0B03),
                    dockBorder = Color(0xFFFDE68A),
                    bankAccentColor = Color(0xFF14B8A6),
                    boatHullColors = listOf(Color(0xFF78350F), Color(0xFF1C0B03), Color(0xFF451A03)),
                    boatTrimColor = Color(0xFF2DD4BF),
                    sailButtonColors = listOf(Color(0xFF2DD4BF), Color(0xFF14B8A6), Color(0xFF0D9488), Color(0xFF0F766E))
                )
                66 -> MIDNIGHT_STARLIGHT.copy(
                    id = "lvl_66_moonlit_willow",
                    name = "Moonlit Willow Haven",
                    tagline = "Silvery lunar light dancing upon calm willow shores",
                    iconEmoji = "🌕",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.MIDNIGHT_FIREFLIES,
                    flotsamType = FlotsamType.BIOLUMINESCENT_SPARKS,
                    waterGradientTop = Color(0xFF1E293B),
                    waterGradientBottom = Color(0xFF020617),
                    waveColor = Color(0xFFE2E8F0),
                    flotsamColor = Color(0xFF94A3B8),
                    dockWoodTop = Color(0xFF334155),
                    dockWoodBottom = Color(0xFF0F172A),
                    dockBorder = Color(0xFFCBD5E1),
                    bankAccentColor = Color(0xFF94A3B8),
                    boatHullColors = listOf(Color(0xFF1E293B), Color(0xFF0F172A), Color(0xFF334155)),
                    boatTrimColor = Color(0xFFE2E8F0),
                    sailButtonColors = listOf(Color(0xFF94A3B8), Color(0xFF64748B), Color(0xFF475569), Color(0xFF334155))
                )
                67 -> SAVANNAH_SUN.copy(
                    id = "lvl_67_sun_temple",
                    name = "Golden Sun Temple",
                    tagline = "Sun-baked sandstone monuments reflecting in holy river",
                    iconEmoji = "☀️",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.SAVANNAH_DUST,
                    flotsamType = FlotsamType.SAVANNAH_REEDS,
                    waterGradientTop = Color(0xFFD97706),
                    waterGradientBottom = Color(0xFF92400E),
                    waveColor = Color(0xFFFEF3C7),
                    flotsamColor = Color(0xFFFBBF24),
                    dockWoodTop = Color(0xFFB45309),
                    dockWoodBottom = Color(0xFF451A03),
                    dockBorder = Color(0xFFFEF08A),
                    bankAccentColor = Color(0xFFF59E0B),
                    boatHullColors = listOf(Color(0xFF92400E), Color(0xFF451A03), Color(0xFF78350F)),
                    boatTrimColor = Color(0xFFFEF08A),
                    sailButtonColors = listOf(Color(0xFFFDE047), Color(0xFFEAB308), Color(0xFFCA8A04), Color(0xFFA16207))
                )
                68 -> AURORA_BOREALIS.copy(
                    id = "lvl_68_geode_grotto",
                    name = "Crystal Geode Grotto",
                    tagline = "Prismatic amethyst chambers refracting radiant colors",
                    iconEmoji = "💎",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.AURORA_SHIMMER,
                    flotsamType = FlotsamType.AURORA_CRYSTALS,
                    waterGradientTop = Color(0xFF9333EA),
                    waterGradientBottom = Color(0xFF3B0764),
                    waveColor = Color(0xFFF3E8FF),
                    flotsamColor = Color(0xFFE9D5FF),
                    dockWoodTop = Color(0xFF581C87),
                    dockWoodBottom = Color(0xFF2E1065),
                    dockBorder = Color(0xFFE9D5FF),
                    bankAccentColor = Color(0xFFA855F7),
                    boatHullColors = listOf(Color(0xFF6B21A8), Color(0xFF2E1065), Color(0xFF581C87)),
                    boatTrimColor = Color(0xFFE9D5FF),
                    sailButtonColors = listOf(Color(0xFFC084FC), Color(0xFFA855F7), Color(0xFF9333EA), Color(0xFF7E22CE))
                )
                69 -> MIDNIGHT_STARLIGHT.copy(
                    id = "lvl_69_starfall_canyon",
                    name = "Starfall Astral Canyon",
                    tagline = "Shooting stars streaking across a deep indigo sky",
                    iconEmoji = "🌠",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.MIDNIGHT_FIREFLIES,
                    flotsamType = FlotsamType.BIOLUMINESCENT_SPARKS,
                    waterGradientTop = Color(0xFF312E81),
                    waterGradientBottom = Color(0xFF1E1B4B),
                    waveColor = Color(0xFFC7D2FE),
                    flotsamColor = Color(0xFF818CF8),
                    dockWoodTop = Color(0xFF1E1B4B),
                    dockWoodBottom = Color(0xFF0F172A),
                    dockBorder = Color(0xFFC7D2FE),
                    bankAccentColor = Color(0xFF6366F1),
                    boatHullColors = listOf(Color(0xFF312E81), Color(0xFF1E1B4B), Color(0xFF1E293B)),
                    boatTrimColor = Color(0xFFC7D2FE),
                    sailButtonColors = listOf(Color(0xFF818CF8), Color(0xFF6366F1), Color(0xFF4F46E5), Color(0xFF3730A3))
                )
                70 -> AURORA_BOREALIS.copy(
                    id = "lvl_70_emperors_elysium",
                    name = "The Grand Emperor's Elysium",
                    tagline = "Supreme monumental river gateway of legendary grandmasters",
                    iconEmoji = "👑",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.AURORA_SHIMMER,
                    flotsamType = FlotsamType.AURORA_CRYSTALS,
                    waterGradientTop = Color(0xFFEAB308),
                    waterGradientBottom = Color(0xFF451A03),
                    waveColor = Color(0xFFFEF08A),
                    flotsamColor = Color(0xFFFDE047),
                    dockWoodTop = Color(0xFF78350F),
                    dockWoodBottom = Color(0xFF451A03),
                    dockBorder = Color(0xFFFEF08A),
                    bankAccentColor = Color(0xFFFACC15),
                    boatHullColors = listOf(Color(0xFFB45309), Color(0xFF451A03), Color(0xFF92400E)),
                    boatTrimColor = Color(0xFFFEF08A),
                    sailButtonColors = listOf(Color(0xFFFEF08A), Color(0xFFFACC15), Color(0xFFEAB308), Color(0xFFCA8A04))
                )
                71 -> SPRING_VALLEY.copy(
                    id = "lvl_71_monkey_canopy",
                    name = "Monkey River Canopy",
                    tagline = "Vibrant emerald rainforest canopy with hanging vines",
                    iconEmoji = "🐒",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.SPRING_PETALS,
                    flotsamType = FlotsamType.LILY_PADS,
                    waterGradientTop = Color(0xFF15803D),
                    waterGradientBottom = Color(0xFF14532D),
                    waveColor = Color(0xFFDCFCE7),
                    flotsamColor = Color(0xFF4ADE80),
                    dockWoodTop = Color(0xFF451A03),
                    dockWoodBottom = Color(0xFF1C0B03),
                    dockBorder = Color(0xFF86EFAC),
                    bankAccentColor = Color(0xFF22C55E),
                    boatHullColors = listOf(Color(0xFF78350F), Color(0xFF451A03), Color(0xFF92400E)),
                    boatTrimColor = Color(0xFF86EFAC),
                    sailButtonColors = listOf(Color(0xFF4ADE80), Color(0xFF22C55E), Color(0xFF16A34A), Color(0xFF15803D))
                )
                72 -> SPRING_VALLEY.copy(
                    id = "lvl_72_panda_sanctuary",
                    name = "Misty Bamboo Sanctuary",
                    tagline = "Misty mountain bamboo grove with cascading stone falls",
                    iconEmoji = "🐼",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.ALPINE_MIST,
                    flotsamType = FlotsamType.PINE_SPRIGS,
                    waterGradientTop = Color(0xFF0F766E),
                    waterGradientBottom = Color(0xFF115E59),
                    waveColor = Color(0xFFCCFBF1),
                    flotsamColor = Color(0xFF2DD4BF),
                    dockWoodTop = Color(0xFF3F6212),
                    dockWoodBottom = Color(0xFF14532D),
                    dockBorder = Color(0xFF5EEAD4),
                    bankAccentColor = Color(0xFF14B8A6),
                    boatHullColors = listOf(Color(0xFF365314), Color(0xFF14532D), Color(0xFF166534)),
                    boatTrimColor = Color(0xFF5EEAD4),
                    sailButtonColors = listOf(Color(0xFF2DD4BF), Color(0xFF14B8A6), Color(0xFF0D9488), Color(0xFF0F766E))
                )
                73 -> TWILIGHT_RAPIDS.copy(
                    id = "lvl_73_highland_steed",
                    name = "Highland Steed Pastures",
                    tagline = "Purple heather winds drifting across mountain lochs",
                    iconEmoji = "🐎",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.TWILIGHT_MOTES,
                    flotsamType = FlotsamType.TWILIGHT_PETALS,
                    waterGradientTop = Color(0xFF6B21A8),
                    waterGradientBottom = Color(0xFF3B0764),
                    waveColor = Color(0xFFF3E8FF),
                    flotsamColor = Color(0xFFD8B4FE),
                    dockWoodTop = Color(0xFF475569),
                    dockWoodBottom = Color(0xFF1E293B),
                    dockBorder = Color(0xFFE9D5FF),
                    bankAccentColor = Color(0xFFA855F7),
                    boatHullColors = listOf(Color(0xFF581C87), Color(0xFF2E1065), Color(0xFF4C1D95)),
                    boatTrimColor = Color(0xFFD8B4FE),
                    sailButtonColors = listOf(Color(0xFFC084FC), Color(0xFFA855F7), Color(0xFF9333EA), Color(0xFF7E22CE))
                )
                74 -> AUTUMN_HARVEST.copy(
                    id = "lvl_74_honeycomb_glade",
                    name = "Honeycomb Forest Glade",
                    tagline = "Sun-dappled ancient woodland with golden wildflower breezes",
                    iconEmoji = "🍯",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.AUTUMN_LEAVES,
                    flotsamType = FlotsamType.AUTUMN_LEAVES,
                    waterGradientTop = Color(0xFFD97706),
                    waterGradientBottom = Color(0xFF78350F),
                    waveColor = Color(0xFFFEF3C7),
                    flotsamColor = Color(0xFFFBBF24),
                    dockWoodTop = Color(0xFF92400E),
                    dockWoodBottom = Color(0xFF451A03),
                    dockBorder = Color(0xFFFEF08A),
                    bankAccentColor = Color(0xFFF59E0B),
                    boatHullColors = listOf(Color(0xFFB45309), Color(0xFF451A03), Color(0xFF92400E)),
                    boatTrimColor = Color(0xFFFEF08A),
                    sailButtonColors = listOf(Color(0xFFFDE047), Color(0xFFEAB308), Color(0xFFCA8A04), Color(0xFFA16207))
                )
                75 -> MIDNIGHT_STARLIGHT.copy(
                    id = "lvl_75_emerald_marsh",
                    name = "Bioluminescent Marsh",
                    tagline = "Glowing cypress swamps with floating water lilies",
                    iconEmoji = "🐸",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.MIDNIGHT_FIREFLIES,
                    flotsamType = FlotsamType.BIOLUMINESCENT_SPARKS,
                    waterGradientTop = Color(0xFF047857),
                    waterGradientBottom = Color(0xFF022C22),
                    waveColor = Color(0xFF6EE7B7),
                    flotsamColor = Color(0xFF34D399),
                    dockWoodTop = Color(0xFF14532D),
                    dockWoodBottom = Color(0xFF052E16),
                    dockBorder = Color(0xFF6EE7B7),
                    bankAccentColor = Color(0xFF10B981),
                    boatHullColors = listOf(Color(0xFF065F46), Color(0xFF022C22), Color(0xFF047857)),
                    boatTrimColor = Color(0xFF6EE7B7),
                    sailButtonColors = listOf(Color(0xFF34D399), Color(0xFF10B981), Color(0xFF059669), Color(0xFF047857))
                )
                76 -> SAVANNAH_SUN.copy(
                    id = "lvl_76_raptors_crag",
                    name = "Raptor's Sunset Crag",
                    tagline = "Towering alpine spires ablaze in golden evening light",
                    iconEmoji = "🦅",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.SAVANNAH_DUST,
                    flotsamType = FlotsamType.SAVANNAH_REEDS,
                    waterGradientTop = Color(0xFFEA580C),
                    waterGradientBottom = Color(0xFF7C2D12),
                    waveColor = Color(0xFFFFEDD5),
                    flotsamColor = Color(0xFFFB923C),
                    dockWoodTop = Color(0xFF9A3412),
                    dockWoodBottom = Color(0xFF431407),
                    dockBorder = Color(0xFFFDBA74),
                    bankAccentColor = Color(0xFFEA580C),
                    boatHullColors = listOf(Color(0xFFC2410C), Color(0xFF431407), Color(0xFF7C2D12)),
                    boatTrimColor = Color(0xFFFDBA74),
                    sailButtonColors = listOf(Color(0xFFFB923C), Color(0xFFEA580C), Color(0xFFC2410C), Color(0xFF9A3412))
                )
                77 -> ALPINE_PEAKS.copy(
                    id = "lvl_77_polar_drift",
                    name = "Polar Glacial Drift",
                    tagline = "Deep azure fjord waters with floating sea ice",
                    iconEmoji = "🐧",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.ALPINE_MIST,
                    flotsamType = FlotsamType.AURORA_CRYSTALS,
                    waterGradientTop = Color(0xFF0369A1),
                    waterGradientBottom = Color(0xFF082F49),
                    waveColor = Color(0xFFE0F2FE),
                    flotsamColor = Color(0xFF7DD3FC),
                    dockWoodTop = Color(0xFF334155),
                    dockWoodBottom = Color(0xFF0F172A),
                    dockBorder = Color(0xFFBAE6FD),
                    bankAccentColor = Color(0xFF38BDF8),
                    boatHullColors = listOf(Color(0xFF1E293B), Color(0xFF082F49), Color(0xFF0C4A6E)),
                    boatTrimColor = Color(0xFFBAE6FD),
                    sailButtonColors = listOf(Color(0xFF38BDF8), Color(0xFF0EA5E9), Color(0xFF0284C7), Color(0xFF0369A1))
                )
                78 -> DESERT_OASIS.copy(
                    id = "lvl_78_oasis_bazaar",
                    name = "Golden Oasis Bazaar",
                    tagline = "Silken sand dunes and tranquil moonlit reflection pools",
                    iconEmoji = "🏝️",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.OASIS_MIRAGE,
                    flotsamType = FlotsamType.OASIS_BLOOMS,
                    waterGradientTop = Color(0xFF0D9488),
                    waterGradientBottom = Color(0xFF134E4A),
                    waveColor = Color(0xFFCCFBF1),
                    flotsamColor = Color(0xFF2DD4BF),
                    dockWoodTop = Color(0xFF78350F),
                    dockWoodBottom = Color(0xFF451A03),
                    dockBorder = Color(0xFFFDE68A),
                    bankAccentColor = Color(0xFF14B8A6),
                    boatHullColors = listOf(Color(0xFF92400E), Color(0xFF451A03), Color(0xFF78350F)),
                    boatTrimColor = Color(0xFF2DD4BF),
                    sailButtonColors = listOf(Color(0xFF2DD4BF), Color(0xFF14B8A6), Color(0xFF0D9488), Color(0xFF0F766E))
                )
                79 -> SPRING_VALLEY.copy(
                    id = "lvl_79_serpent_temple",
                    name = "Forbidden Serpent Temple",
                    tagline = "Ancient stepped pyramid reflecting on holy emerald river",
                    iconEmoji = "🐍",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.SPRING_PETALS,
                    flotsamType = FlotsamType.LILY_PADS,
                    waterGradientTop = Color(0xFF059669),
                    waterGradientBottom = Color(0xFF064E3B),
                    waveColor = Color(0xFFD1FAE5),
                    flotsamColor = Color(0xFF34D399),
                    dockWoodTop = Color(0xFF1F2937),
                    dockWoodBottom = Color(0xFF111827),
                    dockBorder = Color(0xFF6EE7B7),
                    bankAccentColor = Color(0xFF10B981),
                    boatHullColors = listOf(Color(0xFF374151), Color(0xFF111827), Color(0xFF1F2937)),
                    boatTrimColor = Color(0xFF6EE7B7),
                    sailButtonColors = listOf(Color(0xFF34D399), Color(0xFF10B981), Color(0xFF059669), Color(0xFF047857))
                )
                80 -> MIDNIGHT_STARLIGHT.copy(
                    id = "lvl_80_moonlit_waterfall",
                    name = "Moonlit Twin Waterfall",
                    tagline = "Silvery lunar currents surging beneath mist-crowned falls",
                    iconEmoji = "🌕",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.MIDNIGHT_FIREFLIES,
                    flotsamType = FlotsamType.BIOLUMINESCENT_SPARKS,
                    waterGradientTop = Color(0xFF1E293B),
                    waterGradientBottom = Color(0xFF090D16),
                    waveColor = Color(0xFFE2E8F0),
                    flotsamColor = Color(0xFF38BDF8),
                    dockWoodTop = Color(0xFF334155),
                    dockWoodBottom = Color(0xFF0F172A),
                    dockBorder = Color(0xFFE2E8F0),
                    bankAccentColor = Color(0xFF67E8F9),
                    boatHullColors = listOf(Color(0xFF1E293B), Color(0xFF0F172A), Color(0xFF334155)),
                    boatTrimColor = Color(0xFFE2E8F0),
                    sailButtonColors = listOf(Color(0xFF67E8F9), Color(0xFF38BDF8), Color(0xFF0284C7), Color(0xFF0369A1))
                )
                81 -> AUTUMN_HARVEST.copy(
                    id = "lvl_81_autumn_orchard",
                    name = "Whispering Apple Orchard",
                    tagline = "Scarlet and amber leaves drifting beside a peaceful millstream",
                    iconEmoji = "🍎",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.AUTUMN_LEAVES,
                    flotsamType = FlotsamType.AUTUMN_LEAVES,
                    waterGradientTop = Color(0xFFB45309),
                    waterGradientBottom = Color(0xFF78350F),
                    waveColor = Color(0xFFFEF3C7),
                    flotsamColor = Color(0xFFEF4444),
                    dockWoodTop = Color(0xFF78350F),
                    dockWoodBottom = Color(0xFF451A03),
                    dockBorder = Color(0xFFFCA5A5),
                    bankAccentColor = Color(0xFFEA580C),
                    boatHullColors = listOf(Color(0xFF92400E), Color(0xFF451A03), Color(0xFF78350F)),
                    boatTrimColor = Color(0xFFFDE68A),
                    sailButtonColors = listOf(Color(0xFFF87171), Color(0xFFEF4444), Color(0xFFDC2626), Color(0xFFB91C1C))
                )
                82 -> TWILIGHT_RAPIDS.copy(
                    id = "lvl_82_geyser_basin",
                    name = "Prismatic Geyser Basin",
                    tagline = "Rainbow geothermal mineral waters and volcanic steam",
                    iconEmoji = "♨️",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.TWILIGHT_MOTES,
                    flotsamType = FlotsamType.TWILIGHT_PETALS,
                    waterGradientTop = Color(0xFF0891B2),
                    waterGradientBottom = Color(0xFF4338CA),
                    waveColor = Color(0xFFFEF08A),
                    flotsamColor = Color(0xFFF472B6),
                    dockWoodTop = Color(0xFF475569),
                    dockWoodBottom = Color(0xFF1E293B),
                    dockBorder = Color(0xFFFDE047),
                    bankAccentColor = Color(0xFF06B6D4),
                    boatHullColors = listOf(Color(0xFF1E293B), Color(0xFF0F172A), Color(0xFF334155)),
                    boatTrimColor = Color(0xFFFDE047),
                    sailButtonColors = listOf(Color(0xFF22D3EE), Color(0xFF06B6D4), Color(0xFF0891B2), Color(0xFF0E7490))
                )
                83 -> SAVANNAH_SUN.copy(
                    id = "lvl_83_savannah_twilight",
                    name = "Savannah Twilight Haven",
                    tagline = "Crimson African horizon with acacia silhouettes over the river",
                    iconEmoji = "🌅",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.SAVANNAH_DUST,
                    flotsamType = FlotsamType.SAVANNAH_REEDS,
                    waterGradientTop = Color(0xFFC2410C),
                    waterGradientBottom = Color(0xFF581C87),
                    waveColor = Color(0xFFFFEDD5),
                    flotsamColor = Color(0xFFFB923C),
                    dockWoodTop = Color(0xFF78350F),
                    dockWoodBottom = Color(0xFF3B0764),
                    dockBorder = Color(0xFFFED7AA),
                    bankAccentColor = Color(0xFFEA580C),
                    boatHullColors = listOf(Color(0xFF9A3412), Color(0xFF3B0764), Color(0xFF7C2D12)),
                    boatTrimColor = Color(0xFFFED7AA),
                    sailButtonColors = listOf(Color(0xFFFB923C), Color(0xFFEA580C), Color(0xFFC2410C), Color(0xFF9A3412))
                )
                84 -> AURORA_BOREALIS.copy(
                    id = "lvl_84_polar_aurora",
                    name = "Polar Aurora Sanctuary",
                    tagline = "Luminescent emerald ribbons dancing over frosty iceberg waters",
                    iconEmoji = "🌌",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.AURORA_SHIMMER,
                    flotsamType = FlotsamType.AURORA_CRYSTALS,
                    waterGradientTop = Color(0xFF047857),
                    waterGradientBottom = Color(0xFF1E1B4B),
                    waveColor = Color(0xFF6EE7B7),
                    flotsamColor = Color(0xFFA7F3D0),
                    dockWoodTop = Color(0xFF1E293B),
                    dockWoodBottom = Color(0xFF0F172A),
                    dockBorder = Color(0xFF6EE7B7),
                    bankAccentColor = Color(0xFF10B981),
                    boatHullColors = listOf(Color(0xFF1E293B), Color(0xFF0F172A), Color(0xFF172554)),
                    boatTrimColor = Color(0xFF6EE7B7),
                    sailButtonColors = listOf(Color(0xFF34D399), Color(0xFF10B981), Color(0xFF059669), Color(0xFF047857))
                )
                85 -> TWILIGHT_RAPIDS.copy(
                    id = "lvl_85_lotus_lagoon",
                    name = "Enchanted Lotus Lagoon",
                    tagline = "Giant glowing pink lotus flowers and turquoise twilight waters",
                    iconEmoji = "🪷",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.TWILIGHT_MOTES,
                    flotsamType = FlotsamType.TWILIGHT_PETALS,
                    waterGradientTop = Color(0xFF0F766E),
                    waterGradientBottom = Color(0xFF701A75),
                    waveColor = Color(0xFFFCE7F3),
                    flotsamColor = Color(0xFFF472B6),
                    dockWoodTop = Color(0xFF4C1D95),
                    dockWoodBottom = Color(0xFF1E1B4B),
                    dockBorder = Color(0xFFFBCFE8),
                    bankAccentColor = Color(0xFFEC4899),
                    boatHullColors = listOf(Color(0xFF581C87), Color(0xFF1E1B4B), Color(0xFF701A75)),
                    boatTrimColor = Color(0xFFFBCFE8),
                    sailButtonColors = listOf(Color(0xFFF472B6), Color(0xFFEC4899), Color(0xFFDB2777), Color(0xFFBE185D))
                )
                86 -> SPRING_VALLEY.copy(
                    id = "lvl_86_redwood_rapids",
                    name = "Redwood Canyon Rapids",
                    tagline = "Colossal mossy timber giants rising through morning mountain fog",
                    iconEmoji = "🌲",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.ALPINE_MIST,
                    flotsamType = FlotsamType.PINE_SPRIGS,
                    waterGradientTop = Color(0xFF065F46),
                    waterGradientBottom = Color(0xFF451A03),
                    waveColor = Color(0xFFD1FAE5),
                    flotsamColor = Color(0xFF34D399),
                    dockWoodTop = Color(0xFF78350F),
                    dockWoodBottom = Color(0xFF451A03),
                    dockBorder = Color(0xFFFDE68A),
                    bankAccentColor = Color(0xFF10B981),
                    boatHullColors = listOf(Color(0xFF92400E), Color(0xFF451A03), Color(0xFF78350F)),
                    boatTrimColor = Color(0xFF86EFAC),
                    sailButtonColors = listOf(Color(0xFF4ADE80), Color(0xFF22C55E), Color(0xFF16A34A), Color(0xFF15803D))
                )
                87 -> MIDNIGHT_STARLIGHT.copy(
                    id = "lvl_87_starfall_fjord",
                    name = "Celestial Starfall Fjord",
                    tagline = "Showers of shooting stars illuminating mirror-smooth fjord currents",
                    iconEmoji = "🌠",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.MIDNIGHT_FIREFLIES,
                    flotsamType = FlotsamType.BIOLUMINESCENT_SPARKS,
                    waterGradientTop = Color(0xFF1E1B4B),
                    waterGradientBottom = Color(0xFF030712),
                    waveColor = Color(0xFFC7D2FE),
                    flotsamColor = Color(0xFF818CF8),
                    dockWoodTop = Color(0xFF1E293B),
                    dockWoodBottom = Color(0xFF030712),
                    dockBorder = Color(0xFFA5B4FC),
                    bankAccentColor = Color(0xFF6366F1),
                    boatHullColors = listOf(Color(0xFF312E81), Color(0xFF030712), Color(0xFF1E1B4B)),
                    boatTrimColor = Color(0xFFA5B4FC),
                    sailButtonColors = listOf(Color(0xFF818CF8), Color(0xFF6366F1), Color(0xFF4F46E5), Color(0xFF3730A3))
                )
                88 -> DESERT_OASIS.copy(
                    id = "lvl_88_atlantis_aqueduct",
                    name = "Sunken Atlantis Aqueduct",
                    tagline = "Submerged marble archways and turquoise tides among lost ruins",
                    iconEmoji = "🏛️",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.OASIS_MIRAGE,
                    flotsamType = FlotsamType.OASIS_BLOOMS,
                    waterGradientTop = Color(0xFF0891B2),
                    waterGradientBottom = Color(0xFF0E7490),
                    waveColor = Color(0xFFE0F2FE),
                    flotsamColor = Color(0xFF22D3EE),
                    dockWoodTop = Color(0xFFF1F5F9),
                    dockWoodBottom = Color(0xFF64748B),
                    dockBorder = Color(0xFFFDE047),
                    bankAccentColor = Color(0xFF06B6D4),
                    boatHullColors = listOf(Color(0xFFE2E8F0), Color(0xFF64748B), Color(0xFF0E7490)),
                    boatTrimColor = Color(0xFFFDE047),
                    sailButtonColors = listOf(Color(0xFF22D3EE), Color(0xFF06B6D4), Color(0xFF0891B2), Color(0xFF0E7490))
                )
                89 -> SAVANNAH_SUN.copy(
                    id = "lvl_89_volcanic_dragon",
                    name = "Volcanic Dragon Fjord",
                    tagline = "Fiery obsidian basalt cliffs and glowing magma river streams",
                    iconEmoji = "🌋",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.SAVANNAH_DUST,
                    flotsamType = FlotsamType.SAVANNAH_REEDS,
                    waterGradientTop = Color(0xFFB91C1C),
                    waterGradientBottom = Color(0xFF450A0A),
                    waveColor = Color(0xFFFDBA74),
                    flotsamColor = Color(0xFFEF4444),
                    dockWoodTop = Color(0xFF171717),
                    dockWoodBottom = Color(0xFF0A0A0A),
                    dockBorder = Color(0xFFEF4444),
                    bankAccentColor = Color(0xFFF97316),
                    boatHullColors = listOf(Color(0xFF450A0A), Color(0xFF0A0A0A), Color(0xFF7F1D1D)),
                    boatTrimColor = Color(0xFFF97316),
                    sailButtonColors = listOf(Color(0xFFF97316), Color(0xFFEA580C), Color(0xFFC2410C), Color(0xFF9A3412))
                )
                90 -> AURORA_BOREALIS.copy(
                    id = "lvl_90_celestial_sovereign",
                    name = "The Grand Celestial Sovereign",
                    tagline = "The supreme pinnacle! Celestial golden palaces spanning legendary waters",
                    iconEmoji = "👑",
                    backgroundDrawableRes = bg,
                    weatherEffect = WeatherEffectType.AURORA_SHIMMER,
                    flotsamType = FlotsamType.AURORA_CRYSTALS,
                    waterGradientTop = Color(0xFFF59E0B),
                    waterGradientBottom = Color(0xFF312E81),
                    waveColor = Color(0xFFFEF3C7),
                    flotsamColor = Color(0xFFFDE047),
                    dockWoodTop = Color(0xFF78350F),
                    dockWoodBottom = Color(0xFF1E1B4B),
                    dockBorder = Color(0xFFFDE047),
                    bankAccentColor = Color(0xFFFACC15),
                    boatHullColors = listOf(Color(0xFFB45309), Color(0xFF1E1B4B), Color(0xFF92400E)),
                    boatTrimColor = Color(0xFFFDE047),
                    sailButtonColors = listOf(Color(0xFFFDE047), Color(0xFFFACC15), Color(0xFFEAB308), Color(0xFFCA8A04))
                )
                else -> SPRING_VALLEY.copy(
                    id = "lvl_${levelNumber}_custom",
                    name = "Level $levelNumber",
                    backgroundDrawableRes = bg
                )
            }
        }
    }
}
