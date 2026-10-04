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
    val passengerBerthBorderColor: Color = boatTrimColor
) {
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
            passengerBerthBorderColor = Color(0xFFFACC15)
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
            passengerBerthBorderColor = Color(0xFFF59E0B)
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
            passengerBerthBorderColor = Color(0xFF38BDF8)
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
            passengerBerthBorderColor = Color(0xFFF97316)
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
            passengerBerthBorderColor = Color(0xFF818CF8)
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
            passengerBerthBorderColor = Color(0xFFA855F7)
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
            passengerBerthBorderColor = Color(0xFF2DD4BF)
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
            passengerBerthBorderColor = Color(0xFF67E8F9)
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
                else -> SPRING_VALLEY.copy(
                    id = "lvl_${levelNumber}_custom",
                    name = "Level $levelNumber",
                    backgroundDrawableRes = bg
                )
            }
        }
    }
}
