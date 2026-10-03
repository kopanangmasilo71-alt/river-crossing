package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.model.Bank
import com.example.model.SplashEvent
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/**
 * Data structures for AAA particle simulation.
 * Pre-computed per burst to ensure zero memory allocations during 60/120fps Compose draw passes.
 */
private data class SplashDroplet(
    val vx: Float,            // Horizontal velocity in px/sec
    val vy: Float,            // Initial vertical velocity (upwards, negative) in px/sec
    val radius: Float,        // Base radius in px
    val color: Color,         // Particle color with alpha
    val gravity: Float,       // Custom gravity pull
    val delayFraction: Float, // Staggered start delay (0f - 0.2f)
    val lifeFraction: Float   // Relative lifetime multiplier (0.6f - 1f)
)

private data class SparkleStar(
    val vx: Float,
    val vy: Float,
    val size: Float,
    val rotationSpeed: Float,
    val initialAngle: Float,
    val color: Color
)

private data class RippleWave(
    val maxRadiusX: Float,
    val maxRadiusY: Float,
    val delayFraction: Float,
    val color: Color,
    val strokeWidth: Float
)

private data class FoamBubble(
    val offsetX: Float,
    val offsetY: Float,
    val maxRadius: Float,
    val color: Color
)

@Composable
fun WaterSplashParticleOverlay(
    splashEvent: SplashEvent?,
    modifier: Modifier = Modifier
) {
    val progress = remember { Animatable(1f) }
    var currentOrigin by remember { mutableStateOf(Offset.Zero) }
    var droplets by remember { mutableStateOf<List<SplashDroplet>>(emptyList()) }
    var sparkles by remember { mutableStateOf<List<SparkleStar>>(emptyList()) }
    var ripples by remember { mutableStateOf<List<RippleWave>>(emptyList()) }
    var foamBubbles by remember { mutableStateOf<List<FoamBubble>>(emptyList()) }

    val dropletColors = remember {
        listOf(
            Color(0xFFFFFFFF), // Pure glistening white
            Color(0xFFE0F2FE), // Ice crystal highlight
            Color(0xFFBAE6FD), // Sky blue tint
            Color(0xFF7DD3FC), // Aqua cyan
            Color(0xFF38BDF8), // Vibrant river blue
            Color(0xFF60A5FA), // Azure splash
            Color(0xFF93C5FD)  // Light cobalt
        )
    }

    LaunchedEffect(splashEvent) {
        if (splashEvent != null) {
            // Generate randomized AAA particle set for this burst
            val rng = Random(splashEvent.id)
            val isRightBank = splashEvent.targetBank == Bank.RIGHT

            // Bias the horizontal burst spray slightly towards the river center
            val sprayDir = if (isRightBank) -1f else 1f

            val newDroplets = List(38) {
                val speed = rng.nextFloat() * 260f + 140f
                val angleDeg = if (isRightBank) {
                    // Spray arc upwards and leftwards into river (110° to 175°)
                    rng.nextFloat() * 65f + 110f
                } else {
                    // Spray arc upwards and rightwards into river (5° to 70°)
                    rng.nextFloat() * 65f + 5f
                }
                val angleRad = Math.toRadians(angleDeg.toDouble())
                val vx = (cos(angleRad) * speed).toFloat() + (sprayDir * rng.nextFloat() * 40f)
                val vy = (-sin(angleRad) * speed * 1.35f).toFloat() // Strong upward impulse

                SplashDroplet(
                    vx = vx,
                    vy = vy,
                    radius = rng.nextFloat() * 5.5f + 3f,
                    color = dropletColors[rng.nextInt(dropletColors.size)],
                    gravity = rng.nextFloat() * 450f + 750f, // Gravity px/s^2
                    delayFraction = rng.nextFloat() * 0.12f,
                    lifeFraction = rng.nextFloat() * 0.35f + 0.65f
                )
            }

            val newSparkles = List(10) {
                SparkleStar(
                    vx = (rng.nextFloat() - 0.5f) * 160f + (sprayDir * 50f),
                    vy = -(rng.nextFloat() * 180f + 100f),
                    size = rng.nextFloat() * 7f + 5f,
                    rotationSpeed = (rng.nextFloat() - 0.5f) * 720f,
                    initialAngle = rng.nextFloat() * 360f,
                    color = if (rng.nextBoolean()) Color(0xFFFFFFFF) else Color(0xFFE0F7FA)
                )
            }

            val newRipples = List(4) { i ->
                RippleWave(
                    maxRadiusX = (i + 1) * 24f + 35f,
                    maxRadiusY = ((i + 1) * 24f + 35f) * 0.42f, // Perspective oval
                    delayFraction = i * 0.08f,
                    color = if (i % 2 == 0) Color.White.copy(alpha = 0.85f) else Color(0xFF7DD3FC).copy(alpha = 0.7f),
                    strokeWidth = (3.5f - i * 0.5f).coerceAtLeast(1.5f)
                )
            }

            val newFoam = List(14) {
                FoamBubble(
                    offsetX = (rng.nextFloat() - 0.5f) * 70f,
                    offsetY = (rng.nextFloat() - 0.5f) * 20f,
                    maxRadius = rng.nextFloat() * 6f + 3.5f,
                    color = Color.White.copy(alpha = rng.nextFloat() * 0.3f + 0.6f)
                )
            }

            droplets = newDroplets
            sparkles = newSparkles
            ripples = newRipples
            foamBubbles = newFoam

            // Run smooth 60fps/120fps physics animation
            progress.snapTo(0f)
            progress.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 850,
                    easing = FastOutLinearInEasing
                )
            )
        }
    }

    val currentP = progress.value
    if (currentP < 1f && splashEvent != null) {
        val isRight = splashEvent.targetBank == Bank.RIGHT
        Box(modifier = modifier.fillMaxSize()) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("water_splash_overlay")
            ) {
            val w = size.width
            val h = size.height

            // Calculate landing dock origin based on bank
            val isRight = splashEvent.targetBank == Bank.RIGHT
            val originX = if (isRight) w * 0.70f else w * 0.30f
            val originY = h * 0.58f

            val totalSec = 0.85f
            val tSec = currentP * totalSec

            // 1. Draw Surface Expanding Water Ripple Rings
            ripples.forEach { ripple ->
                if (currentP >= ripple.delayFraction) {
                    val rippleP = ((currentP - ripple.delayFraction) / (1f - ripple.delayFraction)).coerceIn(0f, 1f)
                    val alpha = (1f - rippleP).coerceIn(0f, 1f) * 0.75f
                    val rx = ripple.maxRadiusX * rippleP
                    val ry = ripple.maxRadiusY * rippleP

                    drawOval(
                        color = ripple.color.copy(alpha = alpha),
                        topLeft = Offset(originX - rx, originY - ry),
                        size = Size(rx * 2f, ry * 2f),
                        style = Stroke(width = ripple.strokeWidth * (1f - rippleP * 0.5f))
                    )
                }
            }

            // 2. Draw Foam Base Orbs
            foamBubbles.forEach { foam ->
                val bubbleP = (currentP * 1.5f).coerceIn(0f, 1f)
                val alpha = (1f - currentP).coerceIn(0f, 1f) * 0.8f
                val radius = foam.maxRadius * sin(bubbleP * PI.toFloat()).coerceAtLeast(0f)

                if (radius > 0.5f && alpha > 0.05f) {
                    drawCircle(
                        color = foam.color.copy(alpha = alpha),
                        radius = radius,
                        center = Offset(originX + foam.offsetX, originY + foam.offsetY)
                    )
                }
            }

            // 3. Draw Water Droplets with Gravity & Parabolic Trajectories
            droplets.forEach { d ->
                if (currentP >= d.delayFraction) {
                    val localP = ((currentP - d.delayFraction) / d.lifeFraction).coerceIn(0f, 1f)
                    val localT = localP * (totalSec * d.lifeFraction)

                    // Physics position: x = x0 + vx*t, y = y0 + vy*t + 0.5*g*t^2
                    val px = originX + d.vx * localT
                    val py = originY + d.vy * localT + 0.5f * d.gravity * localT * localT

                    // Alpha fades out towards end of life
                    val alpha = ((1f - localP) * 1.2f).coerceIn(0f, 1f)
                    val rad = d.radius * (1f - localP * 0.35f)

                    if (alpha > 0.02f && rad > 0.5f) {
                        // Soft glow under droplet
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    d.color.copy(alpha = alpha * 0.9f),
                                    d.color.copy(alpha = 0f)
                                ),
                                center = Offset(px, py),
                                radius = rad * 1.6f
                            ),
                            radius = rad * 1.6f,
                            center = Offset(px, py)
                        )

                        // Core bright droplet
                        drawCircle(
                            color = d.color.copy(alpha = alpha),
                            radius = rad,
                            center = Offset(px, py)
                        )

                        // Specular highlight spot
                        drawCircle(
                            color = Color.White.copy(alpha = alpha * 0.95f),
                            radius = (rad * 0.4f).coerceAtLeast(1f),
                            center = Offset(px - rad * 0.25f, py - rad * 0.25f)
                        )
                    }
                }
            }

            // 4. Draw Glistening Sparkle Stars
            sparkles.forEach { s ->
                val sparkleP = currentP.coerceIn(0f, 1f)
                val alpha = sin(sparkleP * PI.toFloat()).toFloat().coerceIn(0f, 1f) * 0.9f
                val px = originX + s.vx * tSec
                val py = originY + s.vy * tSec
                val currentAngle = s.initialAngle + s.rotationSpeed * tSec

                if (alpha > 0.05f) {
                    drawSparkleStar(
                        center = Offset(px, py),
                        size = s.size * (1f + 0.3f * sin(sparkleP * PI.toFloat() * 2f).toFloat()),
                        angleDegrees = currentAngle,
                        color = s.color.copy(alpha = alpha)
                    )
                }
            }
        }
    }
}
}

/**
 * Renders a crisp 4-pointed diamond sparkle star for AAA visual sheen.
 */
private fun DrawScope.drawSparkleStar(
    center: Offset,
    size: Float,
    angleDegrees: Float,
    color: Color
) {
    val rad = Math.toRadians(angleDegrees.toDouble())
    val cosA = cos(rad).toFloat()
    val sinA = sin(rad).toFloat()

    fun rotate(x: Float, y: Float): Offset {
        return Offset(
            center.x + (x * cosA - y * sinA),
            center.y + (x * sinA + y * cosA)
        )
    }

    val path = Path().apply {
        val tip = size
        val waist = size * 0.22f

        moveTo(rotate(0f, -tip).x, rotate(0f, -tip).y)
        lineTo(rotate(waist, -waist).x, rotate(waist, -waist).y)
        lineTo(rotate(tip, 0f).x, rotate(tip, 0f).y)
        lineTo(rotate(waist, waist).x, rotate(waist, waist).y)
        lineTo(rotate(0f, tip).x, rotate(0f, tip).y)
        lineTo(rotate(-waist, waist).x, rotate(-waist, waist).y)
        lineTo(rotate(-tip, 0f).x, rotate(-tip, 0f).y)
        lineTo(rotate(-waist, -waist).x, rotate(-waist, -waist).y)
        close()
    }

    drawPath(path = path, color = color)
}
