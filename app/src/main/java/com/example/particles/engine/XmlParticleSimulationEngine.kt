package com.example.particles.engine

import androidx.compose.ui.graphics.Color
import com.example.particles.model.ActiveParticle
import com.example.particles.model.EmitterType
import com.example.particles.model.ParticleLayer
import com.example.particles.model.ParticleShape
import com.example.particles.model.ParticleTriggerState
import com.example.particles.model.XmlParticleEmitter
import com.example.particles.model.XmlParticleSystem
import com.example.particles.model.XmlStateTrigger
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/**
 * High-performance particle physics simulation engine.
 * Observes game state changes and applies dynamic XML-defined triggers.
 */
class XmlParticleSimulationEngine(
    val system: XmlParticleSystem
) {
    private val random = Random(system.level.toLong() * 31L + 17L)

    // Pre-allocated active particle instances per emitter to avoid allocations during tick
    private val emitterParticlePools: Map<String, Array<ActiveParticle>> = system.emitters.associate { emitter ->
        val pool = Array(emitter.maxParticles) {
            ActiveParticle()
        }
        // Initialize pool particles with expired life so they spawn organically
        for (p in pool) {
            p.lifeRemainingMs = 0f
            p.totalLifetimeMs = 1f
        }
        emitter.id to pool
    }

    // Spawn accumulator per emitter for fractional spawn rates
    private val spawnAccumulators = mutableMapOf<String, Float>().apply {
        system.emitters.forEach { put(it.id, 0f) }
    }

    private var previousState: ParticleTriggerState = ParticleTriggerState.IDLE

    /**
     * Updates physics simulation for a given delta time (dtMs in milliseconds).
     */
    fun update(
        dtMs: Float,
        canvasWidth: Float,
        canvasHeight: Float,
        currentState: ParticleTriggerState,
        boatProgress: Float,
        isBoatMovingRight: Boolean
    ) {
        if (canvasWidth <= 0f || canvasHeight <= 0f) return

        // Detect state transition to trigger instantaneous bursts
        if (currentState != previousState) {
            handleStateTransition(previousState, currentState, canvasWidth, canvasHeight)
            previousState = currentState
        }

        val dtSec = dtMs / 1000f

        for (emitter in system.emitters) {
            val pool = emitterParticlePools[emitter.id] ?: continue
            val trigger = emitter.findTrigger(currentState)

            val speedMultiplier = trigger?.speedMultiplier ?: 1.0f
            val spawnRateMultiplier = trigger?.spawnRateMultiplier ?: 1.0f
            val turbulenceMultiplier = trigger?.turbulenceMultiplier ?: 1.0f
            val sizeMultiplier = trigger?.sizeMultiplier ?: 1.0f
            val tintColor = trigger?.tintColor
            val windForceX = (trigger?.windForceX ?: emitter.windForceX) + if (currentState == ParticleTriggerState.ROWING) {
                if (isBoatMovingRight) 25f else -25f
            } else 0f
            val windForceY = trigger?.windForceY ?: emitter.windForceY

            // 1. Advance active particles
            for (p in pool) {
                if (p.lifeRemainingMs > 0f) {
                    p.lifeRemainingMs -= dtMs
                    if (p.lifeRemainingMs <= 0f) continue

                    // Harmonic sway
                    p.swayPhase += dtSec * p.swayFrequency * (2f * PI.toFloat())
                    val swayOffset = sin(p.swayPhase) * p.swayAmplitude * turbulenceMultiplier

                    // Turbulence jitter
                    val turbulenceJitterX = (random.nextFloat() - 0.5f) * 8f * p.turbulence * turbulenceMultiplier
                    val turbulenceJitterY = (random.nextFloat() - 0.5f) * 8f * p.turbulence * turbulenceMultiplier

                    // Velocity integration with wind force and multiplier
                    p.x += (p.vx * speedMultiplier + windForceX + swayOffset + turbulenceJitterX) * dtSec
                    p.y += (p.vy * speedMultiplier + windForceY + turbulenceJitterY) * dtSec
                    p.rotationDeg += p.rotationSpeedDeg * dtSec

                    // Boundary recycling if particle moves way outside visible bounds
                    if (p.x < -canvasWidth * 0.2f || p.x > canvasWidth * 1.2f ||
                        p.y < -canvasHeight * 0.2f || p.y > canvasHeight * 1.2f) {
                        p.lifeRemainingMs = 0f
                    }
                }
            }

            // 2. Spawn new particles based on effective spawn rate
            val effectiveSpawnRate = emitter.spawnRate * spawnRateMultiplier
            var accumulator = (spawnAccumulators[emitter.id] ?: 0f) + (effectiveSpawnRate * dtSec)

            while (accumulator >= 1.0f) {
                accumulator -= 1.0f
                val deadParticle = pool.firstOrNull { it.lifeRemainingMs <= 0f }
                if (deadParticle != null) {
                    spawnParticle(
                        particle = deadParticle,
                        emitter = emitter,
                        canvasWidth = canvasWidth,
                        canvasHeight = canvasHeight,
                        sizeMultiplier = sizeMultiplier,
                        tintColor = tintColor,
                        boatProgress = boatProgress,
                        isBoatMovingRight = isBoatMovingRight
                    )
                }
            }
            spawnAccumulators[emitter.id] = accumulator
        }
    }

    /**
     * Handles instant bursts when transitioning into a new game state (e.g. VICTORY, ROWING).
     */
    private fun handleStateTransition(
        fromState: ParticleTriggerState,
        toState: ParticleTriggerState,
        canvasWidth: Float,
        canvasHeight: Float
    ) {
        for (emitter in system.emitters) {
            val trigger = emitter.findTrigger(toState) ?: continue
            val burstCount = trigger.burstCount
            if (burstCount <= 0) continue

            val pool = emitterParticlePools[emitter.id] ?: continue
            var spawned = 0
            for (p in pool) {
                if (spawned >= burstCount) break
                if (p.lifeRemainingMs <= 0f) {
                    spawnParticle(
                        particle = p,
                        emitter = emitter,
                        canvasWidth = canvasWidth,
                        canvasHeight = canvasHeight,
                        sizeMultiplier = trigger.sizeMultiplier * 1.2f,
                        tintColor = trigger.tintColor,
                        boatProgress = 0.5f,
                        isBoatMovingRight = true
                    )
                    spawned++
                }
            }
        }
    }

    private fun spawnParticle(
        particle: ActiveParticle,
        emitter: XmlParticleEmitter,
        canvasWidth: Float,
        canvasHeight: Float,
        sizeMultiplier: Float,
        tintColor: Color?,
        boatProgress: Float,
        isBoatMovingRight: Boolean
    ) {
        val lifetime = (emitter.lifetimeMinMs + random.nextFloat() * (emitter.lifetimeMaxMs - emitter.lifetimeMinMs)).toFloat()
        particle.totalLifetimeMs = lifetime
        particle.lifeRemainingMs = lifetime

        // Spatial spawn position
        val normX = emitter.xMin + random.nextFloat() * (emitter.xMax - emitter.xMin)
        val normY = emitter.yMin + random.nextFloat() * (emitter.yMax - emitter.yMin)
        particle.x = normX * canvasWidth
        particle.y = normY * canvasHeight

        // Velocity from angle & speed
        val angleDeg = emitter.angleMin + random.nextFloat() * (emitter.angleMax - emitter.angleMin)
        val speed = emitter.speedMin + random.nextFloat() * (emitter.speedMax - emitter.speedMin)
        val angleRad = Math.toRadians(angleDeg.toDouble())
        particle.vx = (cos(angleRad) * speed).toFloat()
        particle.vy = (sin(angleRad) * speed).toFloat()

        // Size & visual dynamics
        particle.sizeStart = emitter.sizeStart * sizeMultiplier
        particle.sizeEnd = emitter.sizeEnd * sizeMultiplier

        val baseColorStart = emitter.colorStart
        particle.colorStart = if (tintColor != null) blendColors(baseColorStart, tintColor, 0.45f) else baseColorStart
        particle.colorEnd = emitter.colorEnd
        particle.alphaStart = emitter.alphaStart
        particle.alphaEnd = emitter.alphaEnd

        particle.shape = emitter.shape
        particle.layer = emitter.layer
        particle.emitterType = emitter.type

        particle.swayPhase = random.nextFloat() * (2f * PI.toFloat())
        particle.swayAmplitude = emitter.swayAmplitude * (0.8f + random.nextFloat() * 0.4f)
        particle.swayFrequency = emitter.swayFrequency * (0.8f + random.nextFloat() * 0.4f)
        particle.turbulence = emitter.turbulence

        particle.rotationDeg = random.nextFloat() * 360f
        particle.rotationSpeedDeg = (random.nextFloat() - 0.5f) * 90f
    }

    fun getActiveParticles(layerFilter: ParticleLayer? = null): List<ActiveParticle> {
        val result = mutableListOf<ActiveParticle>()
        for (emitter in system.emitters) {
            if (layerFilter != null && emitter.layer != layerFilter) continue
            val pool = emitterParticlePools[emitter.id] ?: continue
            for (p in pool) {
                if (p.lifeRemainingMs > 0f) {
                    result.add(p)
                }
            }
        }
        return result
    }

    private fun blendColors(c1: Color, c2: Color, ratio: Float): Color {
        val r = c1.red + (c2.red - c1.red) * ratio
        val g = c1.green + (c2.green - c1.green) * ratio
        val b = c1.blue + (c2.blue - c1.blue) * ratio
        val a = c1.alpha + (c2.alpha - c1.alpha) * ratio
        return Color(r, g, b, a)
    }
}
