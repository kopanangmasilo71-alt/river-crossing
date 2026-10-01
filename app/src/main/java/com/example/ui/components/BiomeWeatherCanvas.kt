package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import com.example.model.WeatherEffectType
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private data class WeatherParticle(
    val xRatio: Float,
    val yRatio: Float,
    val speed: Float,
    val swayAmp: Float,
    val swayFreq: Float,
    val size: Float,
    val color: Color,
    val phaseOffset: Float
)

/**
 * Procedural ambient atmospheric weather and particle canvas tailored to the level's theme.
 * Highly performant, single-Canvas draw with hardware acceleration.
 */
@Composable
fun BiomeWeatherCanvas(
    weatherEffect: WeatherEffectType,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "biome_weather")

    val weatherPhaseState = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "weather_phase"
    )

    val auroraWavePhaseState = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(7000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "aurora_wave"
    )

    // Reusable path objects to prevent GC churn on every frame
    val reusableCurtainPaths = remember { listOf(Path(), Path()) }

    // Pre-generate stable pseudo-random particles per weather effect type
    val particles = remember(weatherEffect) {
        val count = when (weatherEffect) {
            WeatherEffectType.MIDNIGHT_FIREFLIES -> 24
            WeatherEffectType.AURORA_SHIMMER -> 28
            WeatherEffectType.SAVANNAH_DUST -> 22
            WeatherEffectType.ALPINE_MIST -> 16
            else -> 26
        }
        List(count) { i ->
            val seed = (i * 97 + 13) % 1000 / 1000f
            val seed2 = (i * 131 + 47) % 1000 / 1000f
            val seed3 = (i * 179 + 89) % 1000 / 1000f

            val color = when (weatherEffect) {
                WeatherEffectType.SPRING_PETALS -> {
                    if (i % 3 == 0) Color(0xFFFFB7B2) else if (i % 3 == 1) Color(0xFFFFC0CB) else Color(0xFFFFFFFF)
                }
                WeatherEffectType.AUTUMN_LEAVES -> {
                    if (i % 3 == 0) Color(0xFFF59E0B) else if (i % 3 == 1) Color(0xFFEA580C) else Color(0xFFDC2626)
                }
                WeatherEffectType.ALPINE_MIST -> {
                    Color(0xFFE0F2FE).copy(alpha = 0.35f)
                }
                WeatherEffectType.SAVANNAH_DUST -> {
                    if (i % 2 == 0) Color(0xFFFDE68A) else Color(0xFFFBBF24)
                }
                WeatherEffectType.MIDNIGHT_FIREFLIES -> {
                    if (i % 3 == 0) Color(0xFF67E8F9) else Color(0xFFFDE047)
                }
                WeatherEffectType.TWILIGHT_MOTES -> {
                    if (i % 2 == 0) Color(0xFFF472B6) else Color(0xFFFBBF24)
                }
                WeatherEffectType.OASIS_MIRAGE -> {
                    if (i % 2 == 0) Color(0xFF5EEAD4) else Color(0xFFFEF08A)
                }
                WeatherEffectType.AURORA_SHIMMER -> {
                    if (i % 2 == 0) Color(0xFFA7F3D0) else Color(0xFFDDD6FE)
                }
            }

            WeatherParticle(
                xRatio = seed,
                yRatio = seed2,
                speed = 0.5f + seed3 * 0.9f,
                swayAmp = 12f + seed * 24f,
                swayFreq = 1.5f + seed2 * 2f,
                size = when (weatherEffect) {
                    WeatherEffectType.SPRING_PETALS -> 5f + seed3 * 4f
                    WeatherEffectType.AUTUMN_LEAVES -> 7f + seed3 * 5f
                    WeatherEffectType.MIDNIGHT_FIREFLIES -> 3.5f + seed3 * 2.5f
                    WeatherEffectType.ALPINE_MIST -> 40f + seed3 * 45f
                    else -> 4f + seed3 * 3f
                },
                color = color,
                phaseOffset = seed3 * 2f * PI.toFloat()
            )
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val weatherPhase = weatherPhaseState.value
        val auroraWavePhase = auroraWavePhaseState.value
        val w = size.width
        val h = size.height

        when (weatherEffect) {
            WeatherEffectType.SPRING_PETALS -> {
                // Gentle drifting flower petals falling diagonally right
                particles.forEach { p ->
                    val totalProgress = (weatherPhase * p.speed + p.yRatio) % 1f
                    val y = totalProgress * (h + 30f) - 15f
                    val sway = sin(totalProgress * p.swayFreq * 2 * PI + p.phaseOffset).toFloat() * p.swayAmp
                    val x = (p.xRatio * w + totalProgress * 60f + sway) % w

                    drawPetal(
                        center = Offset(x, y),
                        size = p.size,
                        angle = (totalProgress * 360f + p.phaseOffset * 50f) % 360f,
                        color = p.color.copy(alpha = 0.85f)
                    )
                }
            }

            WeatherEffectType.AUTUMN_LEAVES -> {
                // Tumbling colorful autumn leaves
                particles.forEach { p ->
                    val totalProgress = (weatherPhase * p.speed + p.yRatio) % 1f
                    val y = totalProgress * (h + 40f) - 20f
                    val sway = sin(totalProgress * p.swayFreq * 2 * PI + p.phaseOffset).toFloat() * (p.swayAmp * 1.5f)
                    val x = (p.xRatio * w + totalProgress * 40f + sway) % w
                    val tumble = sin(totalProgress * 6f * PI + p.phaseOffset).toFloat()

                    drawAutumnLeaf(
                        center = Offset(x, y),
                        size = p.size,
                        tumble = tumble,
                        angle = (totalProgress * 280f + p.phaseOffset * 60f) % 360f,
                        color = p.color.copy(alpha = 0.90f)
                    )
                }
            }

            WeatherEffectType.ALPINE_MIST -> {
                // Drifting misty cloud bands across the mountain river
                for (band in 0..2) {
                    val bandY = h * (0.20f + band * 0.28f)
                    val shift = (weatherPhase * 0.4f + band * 0.33f) % 1f
                    val startX = (shift * w * 1.5f) - (w * 0.25f)

                    drawOval(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.18f),
                                Color(0xFFBAE6FD).copy(alpha = 0.10f),
                                Color.Transparent
                            ),
                            center = Offset(startX % (w + 100f), bandY),
                            radius = w * 0.45f
                        ),
                        topLeft = Offset((startX % (w + 100f)) - w * 0.45f, bandY - 35f),
                        size = androidx.compose.ui.geometry.Size(w * 0.9f, 70f)
                    )
                }

                // Crisp crystalline mountain sparkles
                particles.forEach { p ->
                    val alpha = (sin(weatherPhase * 8f * PI + p.phaseOffset).toFloat() * 0.4f + 0.5f).coerceIn(0f, 1f)
                    val x = p.xRatio * w
                    val y = p.yRatio * h
                    drawCircle(
                        color = Color.White.copy(alpha = alpha * 0.6f),
                        radius = p.size * 0.25f,
                        center = Offset(x, y)
                    )
                }
            }

            WeatherEffectType.SAVANNAH_DUST -> {
                // Rising warm golden sun dust motes
                particles.forEach { p ->
                    val totalProgress = (1f - (weatherPhase * p.speed + p.yRatio) % 1f)
                    val y = totalProgress * (h + 20f) - 10f
                    val sway = cos(totalProgress * p.swayFreq * 2 * PI + p.phaseOffset).toFloat() * p.swayAmp
                    val x = (p.xRatio * w + sway) % w
                    val pulse = (sin(totalProgress * 10f + p.phaseOffset).toFloat() * 0.3f + 0.7f).coerceIn(0.2f, 1f)

                    drawCircle(
                        color = p.color.copy(alpha = 0.75f * pulse),
                        radius = p.size * 0.55f,
                        center = Offset(x, y)
                    )
                    // Outer halo
                    drawCircle(
                        color = p.color.copy(alpha = 0.25f * pulse),
                        radius = p.size * 1.4f,
                        center = Offset(x, y)
                    )
                }
            }

            WeatherEffectType.MIDNIGHT_FIREFLIES -> {
                // Gently glowing, bobbing fireflies with pulsing aura
                particles.forEach { p ->
                    val t = (weatherPhase * p.speed + p.yRatio)
                    val x = (p.xRatio * w + sin(t * 2 * PI + p.phaseOffset).toFloat() * p.swayAmp) % w
                    val y = (p.yRatio * h + cos(t * 1.6 * PI + p.phaseOffset).toFloat() * (p.swayAmp * 0.8f)) % h
                    val glowPulse = (sin(t * 12f + p.phaseOffset).toFloat() * 0.5f + 0.5f).coerceIn(0.15f, 1f)

                    // Outer soft glow
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                p.color.copy(alpha = 0.60f * glowPulse),
                                p.color.copy(alpha = 0.15f * glowPulse),
                                Color.Transparent
                            ),
                            center = Offset(x, y),
                            radius = p.size * 3.5f
                        ),
                        radius = p.size * 3.5f,
                        center = Offset(x, y)
                    )
                    // Bright core
                    drawCircle(
                        color = Color.White.copy(alpha = 0.95f * glowPulse),
                        radius = p.size * 0.5f,
                        center = Offset(x, y)
                    )
                }
            }

            WeatherEffectType.TWILIGHT_MOTES -> {
                // Rising evening twilight embers
                particles.forEach { p ->
                    val totalProgress = (1f - (weatherPhase * p.speed + p.yRatio) % 1f)
                    val y = totalProgress * (h + 30f) - 15f
                    val sway = sin(totalProgress * 4 * PI + p.phaseOffset).toFloat() * (p.swayAmp * 0.7f)
                    val x = (p.xRatio * w + sway) % w
                    val alpha = (sin(totalProgress * 8f + p.phaseOffset).toFloat() * 0.35f + 0.65f).coerceIn(0f, 1f)

                    drawCircle(
                        color = p.color.copy(alpha = 0.75f * alpha),
                        radius = p.size * 0.6f,
                        center = Offset(x, y)
                    )
                }
            }

            WeatherEffectType.OASIS_MIRAGE -> {
                // Glistening oasis light specks & warm air
                particles.forEach { p ->
                    val t = (weatherPhase * p.speed + p.yRatio)
                    val x = (p.xRatio * w + sin(t * 3 * PI).toFloat() * 8f) % w
                    val y = (p.yRatio * h)
                    val sparkle = (sin(t * 14f + p.phaseOffset).toFloat() * 0.5f + 0.5f).coerceIn(0f, 1f)

                    drawCircle(
                        color = p.color.copy(alpha = 0.65f * sparkle),
                        radius = p.size * 0.5f * sparkle,
                        center = Offset(x, y)
                    )
                }
            }

            WeatherEffectType.AURORA_SHIMMER -> {
                // Grandmaster Aurora: Dancing shimmering emerald/violet northern lights ribbons across the sky
                val waveOffset = auroraWavePhase
                for (curtain in 0..1) {
                    val curtainPath = reusableCurtainPaths[curtain]
                    curtainPath.reset()
                    val baseY = h * (0.06f + curtain * 0.08f)
                    curtainPath.moveTo(0f, baseY)
                    var currX = 0f
                    while (currX <= w) {
                        val currY = baseY + sin((currX / (w * 0.35f)) + waveOffset + (curtain * 1.5)).toFloat() * 18f
                        curtainPath.lineTo(currX, currY)
                        currX += 16f
                    }
                    curtainPath.lineTo(w, 0f)
                    curtainPath.lineTo(0f, 0f)
                    curtainPath.close()

                    drawPath(
                        path = curtainPath,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                if (curtain == 0) Color(0x6034D399) else Color(0x50A78BFA),
                                if (curtain == 0) Color(0x3010B981) else Color(0x20818CF8),
                                Color.Transparent
                            ),
                            startY = 0f,
                            endY = baseY + 45f
                        )
                    )
                }

                // Celestial starlight sparkles
                particles.forEach { p ->
                    val twinkle = (sin(weatherPhase * 16f + p.phaseOffset).toFloat() * 0.45f + 0.55f).coerceIn(0f, 1f)
                    val x = p.xRatio * w
                    val y = (p.yRatio * h * 0.45f) // Upper starry sky
                    drawCircle(
                        color = Color.White.copy(alpha = twinkle * 0.85f),
                        radius = p.size * 0.35f,
                        center = Offset(x, y)
                    )
                }
            }
        }
    }
}

private fun DrawScope.drawPetal(center: Offset, size: Float, angle: Float, color: Color) {
    rotate(degrees = angle, pivot = center) {
        drawOval(
            color = color,
            topLeft = Offset(center.x - size * 0.45f, center.y - size),
            size = Size(size * 0.9f, size * 2f)
        )
        // Gentle highlight streak
        drawOval(
            color = Color.White.copy(alpha = 0.35f * color.alpha),
            topLeft = Offset(center.x - size * 0.15f, center.y - size * 0.6f),
            size = Size(size * 0.3f, size * 1.2f)
        )
    }
}

private fun DrawScope.drawAutumnLeaf(center: Offset, size: Float, tumble: Float, angle: Float, color: Color) {
    rotate(degrees = angle, pivot = center) {
        val effectiveH = (size * 2f * kotlin.math.abs(tumble)).coerceAtLeast(size * 0.4f)
        drawOval(
            color = color,
            topLeft = Offset(center.x - size * 0.5f, center.y - effectiveH * 0.5f),
            size = Size(size, effectiveH)
        )
    }
}
