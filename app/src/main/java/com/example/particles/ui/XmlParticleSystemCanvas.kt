package com.example.particles.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalContext
import com.example.model.GameStatus
import com.example.particles.engine.XmlParticleSimulationEngine
import com.example.particles.model.ActiveParticle
import com.example.particles.model.ParticleLayer
import com.example.particles.model.ParticleShape
import com.example.particles.model.ParticleTriggerState
import com.example.particles.model.XmlParticleSystem
import com.example.particles.parser.XmlParticleSystemRepository
import kotlin.math.sqrt

/**
 * Composable Canvas that renders environmental effects driven by the XML particle schema.
 * Reusable across different layers (water surface, atmospheric wind, foreground).
 */
@Composable
fun XmlParticleSystemCanvas(
    levelNumber: Int,
    gameStatus: GameStatus,
    isRowing: Boolean,
    boatProgress: Float,
    isVictory: Boolean,
    isDangerAlertActive: Boolean = false,
    isBoardingActive: Boolean = false,
    layerFilter: ParticleLayer? = null,
    modifier: Modifier = Modifier
) {
    if (!XmlParticleSystemRepository.isLevelSupported(levelNumber)) return

    val context = LocalContext.current
    val particleSystem = remember(levelNumber) {
        XmlParticleSystemRepository.getParticleSystem(context, levelNumber)
    } ?: return

    val engine = remember(particleSystem) {
        XmlParticleSimulationEngine(particleSystem)
    }

    // Resolve current trigger state
    val triggerState = remember(gameStatus, isRowing, isVictory, isDangerAlertActive, isBoardingActive) {
        when {
            isVictory -> ParticleTriggerState.VICTORY
            gameStatus == GameStatus.GAME_OVER -> ParticleTriggerState.GAME_OVER
            isDangerAlertActive -> ParticleTriggerState.DANGER
            isBoardingActive -> ParticleTriggerState.BOARDING
            isRowing || gameStatus == GameStatus.ROWING -> ParticleTriggerState.ROWING
            else -> ParticleTriggerState.IDLE
        }
    }

    // High performance frame loop
    var frameTimeNanos by remember { mutableLongStateOf(0L) }
    var lastFrameNanos by remember { mutableLongStateOf(0L) }

    LaunchedEffect(engine) {
        while (true) {
            withFrameNanos { frameNanos ->
                if (lastFrameNanos == 0L) {
                    lastFrameNanos = frameNanos
                }
                val dtMs = ((frameNanos - lastFrameNanos) / 1_000_000f).coerceIn(1f, 50f)
                lastFrameNanos = frameNanos

                frameTimeNanos = frameNanos
                engine.update(
                    dtMs = dtMs,
                    canvasWidth = 1000f, // Normalized scale or dynamic
                    canvasHeight = 1000f,
                    currentState = triggerState,
                    boatProgress = boatProgress,
                    isBoatMovingRight = boatProgress < 0.5f || isRowing
                )
            }
        }
    }

    // Reusable drawing objects to eliminate GC churn
    val reusablePath = remember { Path() }

    Canvas(modifier = modifier.fillMaxSize()) {
        // Read frameTimeNanos to trigger recomposition per frame
        @Suppress("UNUSED_VARIABLE")
        val tick = frameTimeNanos
        val w = size.width
        val h = size.height

        val activeParticles = engine.getActiveParticles(layerFilter)
        for (p in activeParticles) {
            // Map 0..1000 virtual space to canvas actual dimensions
            val drawX = (p.x / 1000f) * w
            val drawY = (p.y / 1000f) * h

            drawSingleParticle(
                p = p,
                drawX = drawX,
                drawY = drawY,
                path = reusablePath
            )
        }
    }
}

private fun DrawScope.drawSingleParticle(
    p: ActiveParticle,
    drawX: Float,
    drawY: Float,
    path: Path
) {
    val alpha = p.currentAlpha.coerceIn(0f, 1f)
    if (alpha <= 0.01f) return

    val progress = p.progress
    val r = p.colorStart.red + (p.colorEnd.red - p.colorStart.red) * progress
    val g = p.colorStart.green + (p.colorEnd.green - p.colorStart.green) * progress
    val b = p.colorStart.blue + (p.colorEnd.blue - p.colorStart.blue) * progress
    val particleColor = Color(r, g, b, alpha)

    val currentSize = p.currentSize.coerceAtLeast(1.5f)

    when (p.shape) {
        ParticleShape.WAVE_RIPPLE -> {
            // Soft horizontal wave ripple with oval aspect ratio
            val rippleWidth = currentSize * 3.8f
            val rippleHeight = currentSize * 1.2f
            drawOval(
                color = particleColor,
                topLeft = Offset(drawX - rippleWidth / 2f, drawY - rippleHeight / 2f),
                size = Size(rippleWidth, rippleHeight),
                style = Stroke(width = 1.6f)
            )
        }

        ParticleShape.WATER_DROPLET -> {
            // Stretched droplet in velocity direction
            val speed = sqrt(p.vx * p.vx + p.vy * p.vy).coerceAtLeast(1f)
            val dropLength = (currentSize * (1.5f + (speed / 50f))).coerceIn(currentSize, currentSize * 4f)
            rotate(degrees = Math.toDegrees(kotlin.math.atan2(p.vy.toDouble(), p.vx.toDouble())).toFloat(), pivot = Offset(drawX, drawY)) {
                drawOval(
                    color = particleColor,
                    topLeft = Offset(drawX - dropLength / 2f, drawY - currentSize / 2f),
                    size = Size(dropLength, currentSize)
                )
            }
        }

        ParticleShape.LINE_STREAK -> {
            // Fast streak line in velocity vector direction
            val speed = sqrt(p.vx * p.vx + p.vy * p.vy).coerceAtLeast(10f)
            val streakLen = (currentSize * 4.5f * (speed / 60f)).coerceIn(8f, 40f)
            rotate(degrees = Math.toDegrees(kotlin.math.atan2(p.vy.toDouble(), p.vx.toDouble())).toFloat(), pivot = Offset(drawX, drawY)) {
                drawLine(
                    color = particleColor,
                    start = Offset(drawX - streakLen / 2f, drawY),
                    end = Offset(drawX + streakLen / 2f, drawY),
                    strokeWidth = currentSize * 0.75f
                )
            }
        }

        ParticleShape.EMBER_SPARK -> {
            // Glowing 4-point diamond ember
            rotate(degrees = p.rotationDeg, pivot = Offset(drawX, drawY)) {
                path.reset()
                path.moveTo(drawX, drawY - currentSize * 1.5f)
                path.lineTo(drawX + currentSize * 0.6f, drawY)
                path.lineTo(drawX, drawY + currentSize * 1.5f)
                path.lineTo(drawX - currentSize * 0.6f, drawY)
                path.close()
                drawPath(path = path, color = particleColor)

                // Bright inner hot core
                drawCircle(
                    color = Color.White.copy(alpha = alpha * 0.85f),
                    radius = currentSize * 0.45f,
                    center = Offset(drawX, drawY)
                )
            }
        }

        ParticleShape.LEAF_WISP -> {
            // Fluttering organic leaf/petal
            rotate(degrees = p.rotationDeg, pivot = Offset(drawX, drawY)) {
                path.reset()
                path.moveTo(drawX - currentSize * 1.4f, drawY)
                path.quadraticTo(drawX, drawY - currentSize * 0.8f, drawX + currentSize * 1.4f, drawY)
                path.quadraticTo(drawX, drawY + currentSize * 0.8f, drawX - currentSize * 1.4f, drawY)
                path.close()
                drawPath(path = path, color = particleColor)
            }
        }

        ParticleShape.STAR_ORB -> {
            // Celestial 4-ray star orb
            rotate(degrees = p.rotationDeg, pivot = Offset(drawX, drawY)) {
                val rayLen = currentSize * 1.8f
                val rayWidth = currentSize * 0.45f
                drawLine(
                    color = particleColor,
                    start = Offset(drawX - rayLen, drawY),
                    end = Offset(drawX + rayLen, drawY),
                    strokeWidth = rayWidth
                )
                drawLine(
                    color = particleColor,
                    start = Offset(drawX, drawY - rayLen),
                    end = Offset(drawX, drawY + rayLen),
                    strokeWidth = rayWidth
                )
                drawCircle(
                    color = Color.White.copy(alpha = alpha * 0.9f),
                    radius = currentSize * 0.5f,
                    center = Offset(drawX, drawY)
                )
            }
        }

        ParticleShape.AURORA_SHIMMER -> {
            // Soft horizontal prismatic shimmer wave
            val shimmerW = currentSize * 5.0f
            val shimmerH = currentSize * 1.5f
            drawOval(
                color = particleColor,
                topLeft = Offset(drawX - shimmerW / 2f, drawY - shimmerH / 2f),
                size = Size(shimmerW, shimmerH)
            )
        }

        ParticleShape.BUBBLE -> {
            // Translucent bubble with specular highlight
            drawCircle(
                color = particleColor.copy(alpha = alpha * 0.4f),
                radius = currentSize,
                center = Offset(drawX, drawY)
            )
            drawCircle(
                color = particleColor,
                radius = currentSize,
                center = Offset(drawX, drawY),
                style = Stroke(width = 1.2f)
            )
            // Specular reflection glint
            drawCircle(
                color = Color.White.copy(alpha = alpha * 0.8f),
                radius = currentSize * 0.3f,
                center = Offset(drawX - currentSize * 0.35f, drawY - currentSize * 0.35f)
            )
        }

        ParticleShape.CRESCENT_RIPPLE -> {
            val rippleR = currentSize * 2.5f
            drawArc(
                color = particleColor,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = Offset(drawX - rippleR, drawY - rippleR * 0.5f),
                size = Size(rippleR * 2f, rippleR),
                style = Stroke(width = 1.5f)
            )
        }

        ParticleShape.CIRCLE -> {
            drawCircle(
                color = particleColor,
                radius = currentSize,
                center = Offset(drawX, drawY)
            )
        }
    }
}
