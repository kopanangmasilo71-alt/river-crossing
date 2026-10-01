package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.isActive
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

private enum class ConfettiShape {
    RECTANGLE,
    RIBBON,
    CIRCLE,
    STAR,
    DIAMOND
}

private data class ConfettiParticle(
    val startXFraction: Float,
    val startYFraction: Float,
    val initialVx: Float,
    val initialVy: Float,
    val gravity: Float,
    val drag: Float,
    val swayFreq: Float,
    val swayAmp: Float,
    val rotationSpeed: Float,
    val tumbleSpeed: Float,
    val color: Color,
    val width: Float,
    val height: Float,
    val shape: ConfettiShape,
    val delayFraction: Float
)

private data class FireworkParticle(
    val originXFraction: Float,
    val originYFraction: Float,
    val vx: Float,
    val vy: Float,
    val color: Color,
    val size: Float,
    val delayFraction: Float,
    val durationFraction: Float
)

/**
 * High-performance, celebratory confetti, fireworks, and sparkling victory animation overlay.
 * Uses hardware-accelerated Canvas with realistic physics, 3D tumbling rotation, dual cannon bursts,
 * multi-stage firework explosions, and rotating golden sunburst rays.
 */
@Composable
fun CelebrationConfettiOverlay(
    isVictory: Boolean,
    showBanner: Boolean = true,
    modifier: Modifier = Modifier
) {
    val progress = remember { Animatable(0f) }
    var confettiParticles by remember { mutableStateOf<List<ConfettiParticle>>(emptyList()) }
    var fireworkSparks by remember { mutableStateOf<List<FireworkParticle>>(emptyList()) }
    var isBannerVisible by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "celebration_shine")
    val bannerGlowScaleState = infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "banner_glow"
    )

    val sunRayAngleState = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(22000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "sun_ray_rotation"
    )

    val reusableRayPath = remember { Path() }

    val confettiColors = remember {
        listOf(
            Color(0xFFFFD700), // Pure Gold
            Color(0xFFF59E0B), // Vibrant Amber
            Color(0xFF10B981), // Emerald Green
            Color(0xFF3B82F6), // Electric Blue
            Color(0xFF8B5CF6), // Royal Purple
            Color(0xFFEC4899), // Hot Pink
            Color(0xFFEF4444), // Crimson Red
            Color(0xFF06B6D4), // Cyan
            Color(0xFFF97316), // Bright Orange
            Color(0xFFFFFFFF)  // Sparkling White
        )
    }

    LaunchedEffect(isVictory) {
        if (isVictory) {
            val rng = Random(System.currentTimeMillis())
            val count = 140

            // 1. Generate Confetti from Cannons & Sky
            val newConfetti = List(count) { i ->
                val originType = i % 3
                val (startX, startY, vx, vy) = when (originType) {
                    0 -> {
                        // Left cannon burst (shoots up & right)
                        val angleDeg = rng.nextFloat() * 45f + 30f // 30° to 75° upwards
                        val speed = rng.nextFloat() * 800f + 580f
                        val rad = Math.toRadians(angleDeg.toDouble())
                        val vX = (cos(rad) * speed).toFloat()
                        val vY = (-sin(rad) * speed).toFloat()
                        Quadruple(0.04f, 0.96f, vX, vY)
                    }
                    1 -> {
                        // Right cannon burst (shoots up & left)
                        val angleDeg = rng.nextFloat() * 45f + 105f // 105° to 150° upwards
                        val speed = rng.nextFloat() * 800f + 580f
                        val rad = Math.toRadians(angleDeg.toDouble())
                        val vX = (cos(rad) * speed).toFloat()
                        val vY = (-sin(rad) * speed).toFloat()
                        Quadruple(0.96f, 0.96f, vX, vY)
                    }
                    else -> {
                        // Top shower confetti (rains across the top)
                        val startXF = rng.nextFloat()
                        val vX = (rng.nextFloat() - 0.5f) * 180f
                        val vY = rng.nextFloat() * 140f + 70f
                        Quadruple(startXF, -0.05f, vX, vY)
                    }
                }

                val shape = when (rng.nextInt(5)) {
                    0 -> ConfettiShape.RECTANGLE
                    1 -> ConfettiShape.RIBBON
                    2 -> ConfettiShape.STAR
                    3 -> ConfettiShape.DIAMOND
                    else -> ConfettiShape.CIRCLE
                }

                val w = when (shape) {
                    ConfettiShape.RECTANGLE -> rng.nextFloat() * 11f + 8f
                    ConfettiShape.RIBBON -> rng.nextFloat() * 9f + 6f
                    ConfettiShape.STAR -> rng.nextFloat() * 15f + 11f
                    ConfettiShape.DIAMOND -> rng.nextFloat() * 12f + 8f
                    ConfettiShape.CIRCLE -> rng.nextFloat() * 9f + 6f
                }

                val h = when (shape) {
                    ConfettiShape.RECTANGLE -> w * (rng.nextFloat() * 0.6f + 0.8f)
                    ConfettiShape.RIBBON -> w * (rng.nextFloat() * 1.8f + 1.6f)
                    else -> w
                }

                ConfettiParticle(
                    startXFraction = startX,
                    startYFraction = startY,
                    initialVx = vx,
                    initialVy = vy,
                    gravity = rng.nextFloat() * 320f + 460f,
                    drag = rng.nextFloat() * 0.15f + 0.84f,
                    swayFreq = rng.nextFloat() * 4f + 2f,
                    swayAmp = rng.nextFloat() * 45f + 20f,
                    rotationSpeed = (rng.nextFloat() - 0.5f) * 580f,
                    tumbleSpeed = (rng.nextFloat() - 0.5f) * 760f,
                    color = confettiColors[rng.nextInt(confettiColors.size)],
                    width = w,
                    height = h,
                    shape = shape,
                    delayFraction = if (originType == 2) rng.nextFloat() * 0.22f else rng.nextFloat() * 0.08f
                )
            }

            // 2. Generate Firework Bursts (4 distinct bursts at staggered delays)
            val bursts = listOf(
                Triple(0.25f, 0.28f, 0.08f),
                Triple(0.75f, 0.24f, 0.28f),
                Triple(0.50f, 0.18f, 0.50f),
                Triple(0.35f, 0.35f, 0.72f)
            )

            val fireworkList = mutableListOf<FireworkParticle>()
            bursts.forEach { (burstX, burstY, burstDelay) ->
                val burstThemeColor = confettiColors[rng.nextInt(confettiColors.size)]
                val secondaryTheme = confettiColors[rng.nextInt(confettiColors.size)]
                val sparksPerBurst = 28
                for (s in 0 until sparksPerBurst) {
                    val angle = (s * 2f * PI.toFloat() / sparksPerBurst) + (rng.nextFloat() - 0.5f) * 0.3f
                    val speed = rng.nextFloat() * 260f + 140f
                    val vx = cos(angle) * speed
                    val vy = sin(angle) * speed
                    val sparkColor = if (s % 2 == 0) burstThemeColor else secondaryTheme
                    fireworkList.add(
                        FireworkParticle(
                            originXFraction = burstX,
                            originYFraction = burstY,
                            vx = vx,
                            vy = vy,
                            color = sparkColor,
                            size = rng.nextFloat() * 4f + 4f,
                            delayFraction = burstDelay,
                            durationFraction = 0.32f
                        )
                    )
                }
            }

            confettiParticles = newConfetti
            fireworkSparks = fireworkList
            isBannerVisible = true

            // Run continuous celebration loop while isVictory is true
            while (isActive && isVictory) {
                progress.snapTo(0f)
                progress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = 3600,
                        easing = LinearEasing
                    )
                )
            }
        } else {
            isBannerVisible = false
            progress.snapTo(0f)
            confettiParticles = emptyList()
            fireworkSparks = emptyList()
        }
    }

    if (isVictory && (confettiParticles.isNotEmpty() || fireworkSparks.isNotEmpty())) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .testTag("celebration_confetti_overlay")
        ) {
            // 1. Radiant Golden Victory Aura & Rotating Starburst Rays
            Canvas(modifier = Modifier.fillMaxSize()) {
                val sunRayAngle = sunRayAngleState.value
                val cx = size.width / 2f
                val cy = size.height * 0.35f
                val maxDim = maxOf(size.width, size.height)

                // Golden radial background sheen
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFFFD700).copy(alpha = 0.22f),
                            Color(0xFFF59E0B).copy(alpha = 0.10f),
                            Color.Transparent
                        ),
                        center = Offset(cx, cy),
                        radius = maxDim * 0.70f
                    ),
                    radius = maxDim * 0.70f,
                    center = Offset(cx, cy)
                )

                // 12 rotating victory starburst rays
                val rayCount = 12
                val radOffset = Math.toRadians(sunRayAngle.toDouble())
                val rayLength = maxDim * 0.85f

                for (i in 0 until rayCount) {
                    val angle = (i * 2f * PI.toFloat() / rayCount) + radOffset.toFloat()
                    reusableRayPath.reset()
                    reusableRayPath.moveTo(cx, cy)
                    val angle1 = angle - 0.06f
                    val angle2 = angle + 0.06f
                    reusableRayPath.lineTo(cx + cos(angle1) * rayLength, cy + sin(angle1) * rayLength)
                    reusableRayPath.lineTo(cx + cos(angle2) * rayLength, cy + sin(angle2) * rayLength)
                    reusableRayPath.close()

                    drawPath(
                        path = reusableRayPath,
                        color = Color(0xFFFFE082).copy(alpha = 0.06f)
                    )
                }
            }

            // 2. Hardware-Accelerated Physics Confetti & Fireworks Particle Engine
            val currentP = progress.value
            val totalSeconds = 3.6f

            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height

                // Render Firework Star Bursts
                fireworkSparks.forEach { spark ->
                    if (currentP >= spark.delayFraction) {
                        val sparkP = ((currentP - spark.delayFraction) / spark.durationFraction).coerceIn(0f, 1f)
                        if (sparkP < 1f) {
                            val t = sparkP * (totalSeconds * spark.durationFraction)
                            val originX = spark.originXFraction * w
                            val originY = spark.originYFraction * h

                            // Radial expansion + gravity drop + twinkle decay
                            val drag = Math.pow(0.85, (t * 2.5).toDouble()).toFloat()
                            val posX = originX + (spark.vx * drag * t)
                            val posY = originY + (spark.vy * drag * t) + (0.5f * 240f * t * t)

                            val sparkAlpha = (1f - sparkP).coerceIn(0f, 1f)
                            val twinkle = (sin(sparkP * 25f) * 0.3f + 0.7f).coerceIn(0.2f, 1f)
                            val sparkColor = spark.color.copy(alpha = sparkAlpha * twinkle)

                            drawCircle(
                                color = sparkColor,
                                radius = spark.size * (1f - sparkP * 0.4f),
                                center = Offset(posX, posY)
                            )
                            // Bright white spark core
                            drawCircle(
                                color = Color.White.copy(alpha = sparkAlpha * 0.8f),
                                radius = spark.size * 0.4f,
                                center = Offset(posX, posY)
                            )
                        }
                    }
                }

                // Render Confetti Particles
                confettiParticles.forEach { p ->
                    if (currentP >= p.delayFraction) {
                        val localP = ((currentP - p.delayFraction) / (1f - p.delayFraction)).coerceIn(0f, 1f)
                        val t = localP * (totalSeconds * (1f - p.delayFraction))

                        val dragFactor = Math.pow(p.drag.toDouble(), t.toDouble()).toFloat()
                        val originX = p.startXFraction * w
                        val originY = p.startYFraction * h

                        val vx = p.initialVx * dragFactor
                        val swayX = sin(t * p.swayFreq * PI.toFloat()).toFloat() * p.swayAmp
                        val posX = originX + (vx * t) + swayX
                        val posY = originY + (p.initialVy * dragFactor * t) + (0.5f * p.gravity * t * t)

                        val alpha = (1f - (localP - 0.78f) / 0.22f).coerceIn(0f, 1f)
                        val rotation = (t * p.rotationSpeed) % 360f
                        val tumble = sin(t * p.tumbleSpeed * (PI / 180f).toFloat()).toFloat()

                        if (posY in -50f..(h + 60f) && posX in -50f..(w + 50f) && alpha > 0.05f) {
                            val particleColor = p.color.copy(alpha = alpha * 0.95f)

                            when (p.shape) {
                                ConfettiShape.RECTANGLE, ConfettiShape.RIBBON -> {
                                    drawRotatedRect(
                                        center = Offset(posX, posY),
                                        width = p.width,
                                        height = p.height * tumble.coerceIn(-1f, 1f),
                                        angleDegrees = rotation,
                                        color = particleColor
                                    )
                                }
                                ConfettiShape.CIRCLE -> {
                                    drawCircle(
                                        color = particleColor,
                                        radius = (p.width / 2f) * kotlin.math.abs(tumble).coerceAtLeast(0.3f),
                                        center = Offset(posX, posY)
                                    )
                                }
                                ConfettiShape.STAR -> {
                                    drawSparkleConfettiStar(
                                        center = Offset(posX, posY),
                                        size = p.width * (0.75f + 0.25f * tumble),
                                        angleDegrees = rotation,
                                        color = particleColor
                                    )
                                }
                                ConfettiShape.DIAMOND -> {
                                    drawRotatedDiamond(
                                        center = Offset(posX, posY),
                                        size = p.width * (0.7f + 0.3f * tumble),
                                        angleDegrees = rotation,
                                        color = particleColor
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 3. Celebratory Top Banner with Soft Entry Transition
            AnimatedVisibility(
                visible = showBanner && isBannerVisible,
                enter = fadeIn(tween(400)) + slideInVertically(tween(400)) { -it } + scaleIn(spring(dampingRatio = 0.65f, stiffness = 400f)),
                exit = fadeOut(tween(300)) + slideOutVertically(tween(300)) { -it } + scaleOut(),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color(0xFFFFFBEB),
                    border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFFBBF24)),
                    shadowElevation = 10.dp,
                    modifier = Modifier
                        .graphicsLayer {
                            val s = bannerGlowScaleState.value
                            scaleX = s
                            scaleY = s
                        }
                        .testTag("celebration_banner")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 22.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "🎉", fontSize = 22.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "ALL ITEMS TRANSFERRED SAFELY!",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 14.sp,
                                color = Color(0xFFB45309),
                                letterSpacing = 1.2.sp
                            )
                            Text(
                                text = "All travelers arrived safely on the bank!",
                                fontWeight = FontWeight.Medium,
                                fontSize = 11.5.sp,
                                color = Color(0xFF92400E)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "⭐", fontSize = 22.sp)
                    }
                }
            }
        }
    }
}

/**
 * Draws a 3D-tumbling rotated rectangle for confetti.
 */
private fun DrawScope.drawRotatedRect(
    center: Offset,
    width: Float,
    height: Float,
    angleDegrees: Float,
    color: Color
) {
    val rad = Math.toRadians(angleDegrees.toDouble())
    val cosA = cos(rad).toFloat()
    val sinA = sin(rad).toFloat()

    val hw = width / 2f
    val hh = height / 2f

    fun transform(x: Float, y: Float): Offset {
        return Offset(
            center.x + (x * cosA - y * sinA),
            center.y + (x * sinA + y * cosA)
        )
    }

    val path = Path().apply {
        moveTo(transform(-hw, -hh).x, transform(-hw, -hh).y)
        lineTo(transform(hw, -hh).x, transform(hw, -hh).y)
        lineTo(transform(hw, hh).x, transform(hw, hh).y)
        lineTo(transform(-hw, hh).x, transform(-hw, hh).y)
        close()
    }

    drawPath(path = path, color = color)
}

/**
 * Draws a 4-pointed golden sparkle confetti star.
 */
private fun DrawScope.drawSparkleConfettiStar(
    center: Offset,
    size: Float,
    angleDegrees: Float,
    color: Color
) {
    val rad = Math.toRadians(angleDegrees.toDouble())
    val cosA = cos(rad).toFloat()
    val sinA = sin(rad).toFloat()

    val tip = size
    val waist = size * 0.28f

    fun rotate(x: Float, y: Float): Offset {
        return Offset(
            center.x + (x * cosA - y * sinA),
            center.y + (x * sinA + y * cosA)
        )
    }

    val path = Path().apply {
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

/**
 * Draws a rotated diamond for confetti.
 */
private fun DrawScope.drawRotatedDiamond(
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

    val hs = size / 2f
    val path = Path().apply {
        moveTo(rotate(0f, -hs).x, rotate(0f, -hs).y)
        lineTo(rotate(hs * 0.7f, 0f).x, rotate(hs * 0.7f, 0f).y)
        lineTo(rotate(0f, hs).x, rotate(0f, hs).y)
        lineTo(rotate(-hs * 0.7f, 0f).x, rotate(-hs * 0.7f, 0f).y)
        close()
    }

    drawPath(path = path, color = color)
}

private data class Quadruple<A, B, C, D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
)
