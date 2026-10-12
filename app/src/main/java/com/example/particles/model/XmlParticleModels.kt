package com.example.particles.model

import androidx.compose.ui.graphics.Color

/**
 * High-level types of particle emitters in the environmental simulation.
 */
enum class EmitterType {
    FLOWING_WATER,
    WIND,
    WATER_RIPPLE,
    MIST_CURTAIN,
    MYTHIC_EMBER,
    COSMIC_BREEZE;

    companion object {
        fun fromString(value: String?): EmitterType = when (value?.trim()?.uppercase()) {
            "FLOWING_WATER" -> FLOWING_WATER
            "WIND" -> WIND
            "WATER_RIPPLE" -> WATER_RIPPLE
            "MIST_CURTAIN" -> MIST_CURTAIN
            "MYTHIC_EMBER" -> MYTHIC_EMBER
            "COSMIC_BREEZE" -> COSMIC_BREEZE
            else -> FLOWING_WATER
        }
    }
}

/**
 * Visual layering position relative to river water and on-world actors.
 */
enum class ParticleLayer {
    BELOW_WATER,
    WATER_SURFACE,
    ATMOSPHERE,
    FOREGROUND;

    companion object {
        fun fromString(value: String?): ParticleLayer = when (value?.trim()?.uppercase()) {
            "BELOW_WATER" -> BELOW_WATER
            "WATER_SURFACE" -> WATER_SURFACE
            "ATMOSPHERE" -> ATMOSPHERE
            "FOREGROUND" -> FOREGROUND
            else -> WATER_SURFACE
        }
    }
}

/**
 * Geometric render shape used for individual particles.
 */
enum class ParticleShape {
    CIRCLE,
    WAVE_RIPPLE,
    WATER_DROPLET,
    LINE_STREAK,
    EMBER_SPARK,
    LEAF_WISP,
    STAR_ORB,
    AURORA_SHIMMER,
    CRESCENT_RIPPLE,
    BUBBLE;

    companion object {
        fun fromString(value: String?): ParticleShape = when (value?.trim()?.uppercase()) {
            "CIRCLE" -> CIRCLE
            "WAVE_RIPPLE" -> WAVE_RIPPLE
            "WATER_DROPLET" -> WATER_DROPLET
            "LINE_STREAK" -> LINE_STREAK
            "EMBER_SPARK" -> EMBER_SPARK
            "LEAF_WISP" -> LEAF_WISP
            "STAR_ORB" -> STAR_ORB
            "AURORA_SHIMMER" -> AURORA_SHIMMER
            "CRESCENT_RIPPLE" -> CRESCENT_RIPPLE
            "BUBBLE" -> BUBBLE
            else -> CIRCLE
        }
    }
}

/**
 * Target game state that triggers kinetic or visual modifications on an emitter.
 */
enum class ParticleTriggerState {
    IDLE,
    ROWING,
    VICTORY,
    GAME_OVER,
    BOARDING,
    DANGER;

    companion object {
        fun fromString(value: String?): ParticleTriggerState = when (value?.trim()?.uppercase()) {
            "IDLE" -> IDLE
            "ROWING" -> ROWING
            "VICTORY" -> VICTORY
            "GAME_OVER" -> GAME_OVER
            "BOARDING" -> BOARDING
            "DANGER" -> DANGER
            else -> IDLE
        }
    }
}

/**
 * Dynamic parameter modifiers applied when the specified game state is active.
 */
data class XmlStateTrigger(
    val gameState: ParticleTriggerState,
    val speedMultiplier: Float = 1.0f,
    val spawnRateMultiplier: Float = 1.0f,
    val turbulenceMultiplier: Float = 1.0f,
    val sizeMultiplier: Float = 1.0f,
    val tintColor: Color? = null,
    val windForceX: Float? = null,
    val windForceY: Float? = null,
    val burstCount: Int = 0
)

/**
 * XML-defined particle emitter blueprint.
 */
data class XmlParticleEmitter(
    val id: String,
    val type: EmitterType,
    val layer: ParticleLayer = ParticleLayer.WATER_SURFACE,
    val spawnRate: Float = 20.0f,
    val maxParticles: Int = 60,
    val xMin: Float = 0.0f,
    val xMax: Float = 1.0f,
    val yMin: Float = 0.0f,
    val yMax: Float = 1.0f,
    val speedMin: Float = 20.0f,
    val speedMax: Float = 60.0f,
    val angleMin: Float = 0.0f,
    val angleMax: Float = 360.0f,
    val colorStart: Color = Color.White,
    val colorEnd: Color = Color.Transparent,
    val alphaStart: Float = 1.0f,
    val alphaEnd: Float = 0.0f,
    val sizeStart: Float = 4.0f,
    val sizeEnd: Float = 2.0f,
    val shape: ParticleShape = ParticleShape.CIRCLE,
    val lifetimeMinMs: Long = 2000L,
    val lifetimeMaxMs: Long = 4000L,
    val turbulence: Float = 1.0f,
    val swayAmplitude: Float = 10.0f,
    val swayFrequency: Float = 1.0f,
    val windForceX: Float = 0.0f,
    val windForceY: Float = 0.0f,
    val triggers: List<XmlStateTrigger> = emptyList()
) {
    fun findTrigger(state: ParticleTriggerState): XmlStateTrigger? =
        triggers.firstOrNull { it.gameState == state }
}

/**
 * Complete XML-defined particle system definition for a level.
 */
data class XmlParticleSystem(
    val id: String,
    val level: Int,
    val name: String,
    val description: String = "",
    val emitters: List<XmlParticleEmitter> = emptyList()
)

/**
 * Live active particle instance in the physics simulation.
 */
data class ActiveParticle(
    var x: Float = 0f,
    var y: Float = 0f,
    var vx: Float = 0f,
    var vy: Float = 0f,
    var lifeRemainingMs: Float = 0f,
    var totalLifetimeMs: Float = 0f,
    var sizeStart: Float = 4f,
    var sizeEnd: Float = 2f,
    var colorStart: Color = Color.White,
    var colorEnd: Color = Color.Transparent,
    var alphaStart: Float = 1f,
    var alphaEnd: Float = 0f,
    var shape: ParticleShape = ParticleShape.CIRCLE,
    var swayPhase: Float = 0f,
    var swayAmplitude: Float = 0f,
    var swayFrequency: Float = 1f,
    var turbulence: Float = 1f,
    var layer: ParticleLayer = ParticleLayer.WATER_SURFACE,
    var rotationDeg: Float = 0f,
    var rotationSpeedDeg: Float = 0f,
    var emitterType: EmitterType = EmitterType.FLOWING_WATER
) {
    val progress: Float
        get() = if (totalLifetimeMs <= 0f) 1f else (1f - (lifeRemainingMs / totalLifetimeMs)).coerceIn(0f, 1f)

    val currentSize: Float
        get() = sizeStart + (sizeEnd - sizeStart) * progress

    val currentAlpha: Float
        get() = alphaStart + (alphaEnd - alphaStart) * progress
}
