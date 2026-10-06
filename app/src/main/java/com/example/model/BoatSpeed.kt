package com.example.model

/**
 * Configurable speed settings for the wooden river ferry crossing animation.
 * Governs the duration and physics cadence of the boat traveling across the river.
 */
enum class BoatSpeed(
    val id: String,
    val title: String,
    val iconEmoji: String,
    val durationMs: Long,
    val description: String
) {
    SLOW(
        id = "slow",
        title = "Relaxed",
        iconEmoji = "🐢",
        durationMs = 950L,
        description = "0.95s • Gentle scenic paddle"
    ),
    NORMAL(
        id = "normal",
        title = "Standard",
        iconEmoji = "⛵",
        durationMs = 550L,
        description = "0.55s • Steady balanced rowing"
    ),
    FAST(
        id = "fast",
        title = "Swift",
        iconEmoji = "⚡",
        durationMs = 320L,
        description = "0.32s • Fast & snappy crossing"
    ),
    TURBO(
        id = "turbo",
        title = "Turbo",
        iconEmoji = "🚀",
        durationMs = 180L,
        description = "0.18s • Ultra-fast lightning dash"
    );

    companion object {
        fun fromId(id: String?): BoatSpeed = values().firstOrNull { it.id == id } ?: FAST
    }
}
