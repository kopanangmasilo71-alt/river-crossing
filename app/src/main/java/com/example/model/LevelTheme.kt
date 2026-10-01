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
    val atmosphericOverlayColor: Color = Color.Transparent
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
            atmosphericOverlayColor = Color(0x1034D399)
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
            atmosphericOverlayColor = Color(0x1AF59E0B)
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
            atmosphericOverlayColor = Color(0x1238BDF8)
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
            atmosphericOverlayColor = Color(0x18EA580C)
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
            atmosphericOverlayColor = Color(0x221E1B4B)
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
            atmosphericOverlayColor = Color(0x1C701A75)
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
            atmosphericOverlayColor = Color(0x140D9488)
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
            raftWoodTone = Color(0xFF064E3B),
            raftRopeColor = Color(0xFF34D399),
            badgeBgColor = Color(0xFF081C1D),
            badgeTextColor = Color(0xFFA7F3D0),
            atmosphericOverlayColor = Color(0x1A059669)
        )

        fun forScenario(scenario: PuzzleScenario): LevelTheme {
            return forLevel(scenario.levelNumber)
        }

        fun forLevel(levelNumber: Int): LevelTheme {
            return when (levelNumber % 8) {
                1 -> SPRING_VALLEY
                2 -> AUTUMN_HARVEST
                3 -> ALPINE_PEAKS
                4 -> SAVANNAH_SUN
                5 -> MIDNIGHT_STARLIGHT
                6 -> TWILIGHT_RAPIDS
                7 -> DESERT_OASIS
                else -> AURORA_BOREALIS
            }
        }
    }
}
