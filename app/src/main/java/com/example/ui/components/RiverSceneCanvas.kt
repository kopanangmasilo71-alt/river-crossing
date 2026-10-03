package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex
import kotlin.math.roundToInt
import com.example.model.FlotsamType
import com.example.model.LevelTheme
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.Bank
import com.example.model.GameItem
import com.example.model.RiverState
import com.example.model.SplashEvent
import com.example.ui.theme.GoldenBankGlow
import com.example.ui.theme.RiverDeepBlueDark
import com.example.ui.theme.RiverTimberBorder
import com.example.ui.theme.RiverWaterCyan
import com.example.ui.theme.VibrantRiverCanvasFrame
import com.example.ui.theme.VibrantWaterGradientBottom
import com.example.ui.theme.VibrantWaterGradientTop
import com.example.ui.theme.WoodButtonBorder
import com.example.ui.theme.WoodButtonBottom
import com.example.ui.theme.WoodButtonTop
import com.example.ui.theme.WoodGoldenText
import com.example.ui.theme.WoodSignboardBorder
import com.example.ui.theme.WoodSignboardDark
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Living River World Scene with On-World Character Entities, Wooden Dock Piers,
 * Bound Log Raft with dynamic anchor states, Contextual Conflict Indicators,
 * Smooth Curved Sailing Traversal, and On-Map Interactive Hint Spotlight!
 */
@Composable
fun RiverScene(
    riverState: RiverState,
    boatProgress: Float,
    isRowing: Boolean,
    splashEvent: SplashEvent? = null,
    highlightedHintItem: GameItem? = null,
    isHintHighlightingBoat: Boolean = false,
    gameHaptics: GameHaptics? = null,
    onItemClick: (GameItem) -> Unit,
    isVictory: Boolean = false,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "living_river_anim")

    // Flowing water wave phase
    val wavePhaseState = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "water_flow_phase"
    )

    // Gentle floating leaves / lily pads offset
    val floatingCurrentOffsetState = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "river_current_drift"
    )

    // Raft water bobbing (pitch & roll & vertical wave heave)
    val bobbingOffsetState = infiniteTransition.animateFloat(
        initialValue = -3f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "boat_bobbing"
    )

    val boatTiltState = infiniteTransition.animateFloat(
        initialValue = -2.5f,
        targetValue = 2.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "boat_tilt"
    )

    // Oar rowing stroke sweep
    val oarSweepState = infiniteTransition.animateFloat(
        initialValue = if (isRowing) -24f else -4f,
        targetValue = if (isRowing) 24f else 4f,
        animationSpec = infiniteRepeatable(
            animation = tween(if (isRowing) 460 else 1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "oar_sweep"
    )

    // Golden selection aura pulsation
    val auraGlowScaleState = infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(850, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "token_aura_glow"
    )

    val levelTheme = remember(riverState.scenario.levelNumber) {
        LevelTheme.forScenario(riverState.scenario)
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(RiverDeepBlueDark)
            .border(
                BorderStroke(
                    2.4.dp,
                    Brush.verticalGradient(
                        listOf(
                            RiverWaterCyan.copy(alpha = 0.85f),
                            RiverTimberBorder,
                            GoldenBankGlow.copy(alpha = 0.65f),
                            RiverTimberBorder
                        )
                    )
                ),
                RoundedCornerShape(22.dp)
            )
            .shadow(10.dp, RoundedCornerShape(22.dp))
    ) {
        val totalWidth = maxWidth
        val totalHeight = maxHeight
        val isLandscape = totalWidth > totalHeight
        val bankWidth = if (isLandscape) {
            (totalWidth * 0.35f).coerceIn(240.dp, 360.dp)
        } else {
            totalWidth * 0.32f
        }

        // 1. Scenic River Valley Background (Thematic per level)
        Image(
            painter = painterResource(id = levelTheme.backgroundDrawableRes),
            contentDescription = "${levelTheme.name} Landscape",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp))
        )

        // Thematic atmospheric color grading overlay
        if (levelTheme.atmosphericOverlayColor != Color.Transparent) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(20.dp))
                    .background(levelTheme.atmosphericOverlayColor)
            )
        }

        // 2. Dynamic Procedural Biome Atmospheric Weather Particle Engine
        BiomeWeatherCanvas(
            weatherEffect = levelTheme.weatherEffect,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp))
        )

        // 3. Living River Water Canvas (flowing waves, caustics, wake)
        val reusableWavePath = remember { Path() }
        Canvas(modifier = Modifier.fillMaxSize()) {
            val wavePhase = wavePhaseState.value
            val floatingCurrentOffset = floatingCurrentOffsetState.value
            val w = size.width
            val h = size.height
            val leftBankFraction = if (isLandscape) 0.33f else 0.28f
            val rightBankFraction = if (isLandscape) 0.67f else 0.72f
            val leftBankX = w * leftBankFraction
            val rightBankX = w * rightBankFraction
            val riverSpan = rightBankX - leftBankX

            // Soft river water overlay tinted to level biome
            drawRect(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color.Transparent,
                        levelTheme.waterGradientTop.copy(alpha = 0.30f),
                        levelTheme.waterGradientBottom.copy(alpha = 0.45f),
                        levelTheme.waterGradientTop.copy(alpha = 0.30f),
                        Color.Transparent
                    ),
                    startX = leftBankX - 20f,
                    endX = rightBankX + 20f
                ),
                topLeft = Offset(leftBankX - 20f, 0f),
                size = Size(riverSpan + 40f, h)
            )

            // Traveling River Wave Curves & Sun Caustics
            val rad = Math.toRadians(wavePhase.toDouble())
            for (i in 0..7) {
                val waveYBase = h * (0.10f + i * 0.12f)
                reusableWavePath.reset()
                reusableWavePath.moveTo(leftBankX + 10f, waveYBase)
                var currX = leftBankX + 10f
                while (currX <= rightBankX - 10f) {
                    val waveY = waveYBase + (sin((currX / 26.0) + rad + (i * 0.8)) * 3.8).toFloat()
                    reusableWavePath.lineTo(currX, waveY)
                    currX += 10f
                }
                drawPath(
                    path = reusableWavePath,
                    color = levelTheme.waveColor,
                    style = Stroke(width = 1.8f, cap = StrokeCap.Round)
                )
            }

            // Secondary gentle high-frequency surface wave ripples
            for (j in 0..5) {
                val rippleYBase = h * (0.15f + j * 0.13f)
                reusableWavePath.reset()
                reusableWavePath.moveTo(leftBankX + 18f, rippleYBase)
                var rx = leftBankX + 18f
                while (rx <= rightBankX - 18f) {
                    val ry = rippleYBase + (sin((rx / 18.0) - rad * 1.2 + (j * 1.1)) * 2.2).toFloat()
                    reusableWavePath.lineTo(rx, ry)
                    rx += 14f
                }
                drawPath(
                    path = reusableWavePath,
                    color = levelTheme.waveColor.copy(alpha = 0.40f),
                    style = Stroke(width = 1.0f, cap = StrokeCap.Round)
                )
            }

            // Glistening Water Surface Sun / Starlight Caustics
            for (sparkle in 0..8) {
                val sProgress = (floatingCurrentOffset + sparkle * 0.18f) % 1.0f
                val sx = leftBankX + 22f + ((sparkle * 53f) % (riverSpan - 44f))
                val sy = h * (0.12f + sProgress * 0.76f)
                val sparkleAlpha = ((sin(rad * 2.2 + sparkle * 1.6) + 1.0) * 0.35).toFloat().coerceIn(0f, 0.70f)
                if (sparkleAlpha > 0.06f) {
                    drawCircle(
                        color = Color.White.copy(alpha = sparkleAlpha),
                        radius = 2.2f,
                        center = Offset(sx, sy)
                    )
                    drawLine(
                        color = Color(0xFFFFFBEB).copy(alpha = sparkleAlpha * 0.65f),
                        start = Offset(sx - 3.2f, sy),
                        end = Offset(sx + 3.2f, sy),
                        strokeWidth = 1.0f
                    )
                }
            }

            // Drifting Flotsam tailored to Level Biome
            for (padIndex in 0..3) {
                val driftProgress = (floatingCurrentOffset + padIndex * 0.28f) % 1.0f
                val padY = h * (0.14f + driftProgress * 0.72f)
                val padX = leftBankX + 25f + (sin(driftProgress * PI * 2.0 + padIndex) * 24f).toFloat() + (padIndex * 32f)
                if (padX in (leftBankX + 15f)..(rightBankX - 15f)) {
                    when (levelTheme.flotsamType) {
                        FlotsamType.LILY_PADS -> {
                            drawCircle(
                                color = levelTheme.flotsamColor.copy(alpha = 0.75f),
                                radius = 5.5f,
                                center = Offset(padX, padY)
                            )
                            drawCircle(
                                color = Color(0xFFFFB6C1),
                                radius = 1.8f,
                                center = Offset(padX - 1.2f, padY - 1.2f)
                            )
                        }
                        FlotsamType.AUTUMN_LEAVES -> {
                            drawCircle(
                                color = levelTheme.flotsamColor.copy(alpha = 0.80f),
                                radius = 4.5f,
                                center = Offset(padX, padY)
                            )
                            drawCircle(
                                color = Color(0xFFFDE047).copy(alpha = 0.75f),
                                radius = 2.0f,
                                center = Offset(padX + 1f, padY - 1f)
                            )
                        }
                        FlotsamType.BIOLUMINESCENT_SPARKS -> {
                            drawCircle(
                                color = levelTheme.flotsamColor.copy(alpha = 0.85f),
                                radius = 4.5f,
                                center = Offset(padX, padY)
                            )
                            drawCircle(
                                color = Color.White.copy(alpha = 0.95f),
                                radius = 1.8f,
                                center = Offset(padX, padY)
                            )
                        }
                        FlotsamType.AURORA_CRYSTALS -> {
                            drawCircle(
                                color = levelTheme.flotsamColor.copy(alpha = 0.85f),
                                radius = 4.0f,
                                center = Offset(padX, padY)
                            )
                            drawCircle(
                                color = Color(0xFFE0F2FE),
                                radius = 1.6f,
                                center = Offset(padX, padY)
                            )
                        }
                        else -> {
                            drawCircle(
                                color = levelTheme.flotsamColor.copy(alpha = 0.70f),
                                radius = 5.0f,
                                center = Offset(padX, padY)
                            )
                        }
                    }
                }
            }

            // Boat Wake & Trailing Foam
            val boatCenterX = leftBankX + (riverSpan * boatProgress)
            val curveFactor = sin(boatProgress * PI).toFloat()
            val boatCenterY = (h * 0.48f) + (curveFactor * 26f)

            if (isRowing) {
                val isHeadingRight = riverState.farmerBank == Bank.LEFT
                val wakeSternX = if (isHeadingRight) boatCenterX - 46f else boatCenterX + 46f
                val bowApexX = if (isHeadingRight) boatCenterX + 56f else boatCenterX - 56f
                val bowDir = if (isHeadingRight) 1f else -1f

                // 1. Bow Wave (Foaming V-shaped spray cutting through the water at the prow)
                val bowWaveSweep = (wavePhase * 0.35f) % 14f
                val bowPath = Path()
                bowPath.moveTo(bowApexX + (bowDir * 4f), boatCenterY)
                bowPath.quadraticBezierTo(
                    bowApexX - (bowDir * 14f), boatCenterY - 12f,
                    bowApexX - (bowDir * (34f + bowWaveSweep)), boatCenterY - (20f + bowWaveSweep * 0.45f)
                )
                drawPath(
                    path = bowPath,
                    color = Color.White.copy(alpha = 0.58f),
                    style = Stroke(width = 2.4f, cap = StrokeCap.Round)
                )

                bowPath.reset()
                bowPath.moveTo(bowApexX + (bowDir * 4f), boatCenterY)
                bowPath.quadraticBezierTo(
                    bowApexX - (bowDir * 14f), boatCenterY + 12f,
                    bowApexX - (bowDir * (34f + bowWaveSweep)), boatCenterY + (20f + bowWaveSweep * 0.45f)
                )
                drawPath(
                    path = bowPath,
                    color = Color.White.copy(alpha = 0.58f),
                    style = Stroke(width = 2.4f, cap = StrokeCap.Round)
                )

                // Dynamic water spray droplets kicked up at the bow
                for (drop in 0..3) {
                    val dropOffset = (drop * 7f + (wavePhase * 0.6f) % 14f)
                    val dx = bowApexX + (bowDir * dropOffset * 0.35f)
                    val dy = boatCenterY + (if (drop % 2 == 0) -1f else 1f) * (6f + drop * 5f)
                    drawCircle(
                        color = Color.White.copy(alpha = 0.68f),
                        radius = (2.6f - drop * 0.4f).coerceAtLeast(1.1f),
                        center = Offset(dx, dy)
                    )
                }

                // 2. Oar dip water ripples at power stroke peak
                val oarDipIntensity = (kotlin.math.abs(oarSweepState.value) / 24f).coerceIn(0f, 1f)
                if (oarDipIntensity > 0.42f) {
                    val dipPulse = (oarDipIntensity - 0.42f) / 0.58f
                    val oarPortTipY = boatCenterY - 32f
                    val oarStarboardTipY = boatCenterY + 32f
                    val oarRippleRadius = 6f + (dipPulse * 15f)
                    val oarRippleAlpha = (1f - dipPulse) * 0.60f

                    drawOval(
                        color = Color.White.copy(alpha = oarRippleAlpha),
                        topLeft = Offset(boatCenterX - oarRippleRadius * 1.3f, oarPortTipY - oarRippleRadius * 0.5f),
                        size = Size(oarRippleRadius * 2.6f, oarRippleRadius * 1.0f),
                        style = Stroke(width = 1.6f)
                    )
                    drawOval(
                        color = Color.White.copy(alpha = oarRippleAlpha),
                        topLeft = Offset(boatCenterX - oarRippleRadius * 1.3f, oarStarboardTipY - oarRippleRadius * 0.5f),
                        size = Size(oarRippleRadius * 2.6f, oarRippleRadius * 1.0f),
                        style = Stroke(width = 1.6f)
                    )
                }

                // 3. Concentric expanding foam rings at stern
                for (ring in 1..4) {
                    val ringRadius = 10f * ring + ((wavePhase * 0.40f) % 22f)
                    val ringAlpha = (0.45f - ring * 0.09f).coerceAtLeast(0.06f)
                    drawOval(
                        color = Color.White.copy(alpha = ringAlpha),
                        topLeft = Offset(wakeSternX - ringRadius * 1.6f, boatCenterY - ringRadius * 0.6f),
                        size = Size(ringRadius * 3.2f, ringRadius * 1.2f),
                        style = Stroke(width = 2.0f)
                    )
                }

                // 4. Trailing wake foam froth bubbles
                for (bubble in 0..5) {
                    val bubbleOffset = (bubble * 14f) + ((wavePhase * 0.5f) % 18f)
                    val bx = if (isHeadingRight) wakeSternX - bubbleOffset else wakeSternX + bubbleOffset
                    val by = boatCenterY + (sin(bubble * 1.5 + rad) * 6f).toFloat()
                    drawCircle(
                        color = Color.White.copy(alpha = 0.40f),
                        radius = (3.4f - bubble * 0.42f).coerceAtLeast(1.2f),
                        center = Offset(bx, by)
                    )
                }
            } else {
                // Subtle resting water ripples around raft
                val ripplePulse = (wavePhase % 180f) / 180f
                for (r in 0..1) {
                    val rRadius = 45f + (r * 22f) + (ripplePulse * 16f)
                    val rAlpha = (0.24f - (r * 0.08f) - (ripplePulse * 0.12f)).coerceAtLeast(0.04f)
                    drawOval(
                        color = Color.White.copy(alpha = rAlpha),
                        topLeft = Offset(boatCenterX - rRadius, boatCenterY - (rRadius * 0.28f)),
                        size = Size(rRadius * 2f, rRadius * 0.56f),
                        style = Stroke(width = 1.2f)
                    )
                }
            }
        }

        // Ambient River Valley Fireflies & Atmospheric Floating Nature Motes
        val natureInfiniteTransition = rememberInfiniteTransition(label = "nature_motes_anim")
        val natureMotePhase by natureInfiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(6500, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "nature_mote_phase"
        )
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            for (i in 0..9) {
                val seed = i * 49.37f
                val p = (natureMotePhase + i * 0.10f) % 1.0f
                val mx = (w * (0.06f + (i * 0.091f) % 0.88f) + kotlin.math.sin(p * Math.PI * 2.0 + seed) * 18f).toFloat()
                val my = (h * (0.12f + ((i * 0.14f) % 0.72f)) - p * 32f + kotlin.math.cos(p * Math.PI * 1.5 + seed) * 14f).toFloat()
                val pulse = (kotlin.math.sin(p * Math.PI * 2.0 + seed) + 1.0) * 0.5
                val moteAlpha = (pulse * 0.60f).toFloat().coerceIn(0.08f, 0.70f)
                val moteRadius = (2.0f + pulse * 1.6f).toFloat()

                // Glow aura
                drawCircle(
                    color = Color(0xFFFEF08A).copy(alpha = moteAlpha * 0.35f),
                    radius = moteRadius * 2.5f,
                    center = Offset(mx, my)
                )
                // Core
                drawCircle(
                    color = Color(0xFFFFFBEB).copy(alpha = moteAlpha),
                    radius = moteRadius,
                    center = Offset(mx, my)
                )
            }
        }

        // Track active dragging across banks to illuminate boat DROP slots
        var isAnyItemDragging by remember { mutableStateOf(false) }
        val isDropActive = isAnyItemDragging || (highlightedHintItem != null && riverState.isItemOnBank(highlightedHintItem, riverState.farmerBank))

        // 3. Bank Zones with Organic Wooden Dock Piers & On-World Characters
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // LEFT BANK DOCK & CHARACTERS
            BankZone(
                bank = Bank.LEFT,
                riverState = riverState,
                highlightedHintItem = highlightedHintItem,
                gameHaptics = gameHaptics,
                onItemClick = onItemClick,
                onDragStateChange = { isAnyItemDragging = it },
                auraGlowScale = { auraGlowScaleState.value },
                levelTheme = levelTheme,
                isLandscape = isLandscape,
                modifier = Modifier
                    .width(bankWidth)
                    .fillMaxHeight()
            )

            Spacer(modifier = Modifier.weight(1f))

            // RIGHT BANK DOCK & CHARACTERS
            BankZone(
                bank = Bank.RIGHT,
                riverState = riverState,
                highlightedHintItem = highlightedHintItem,
                gameHaptics = gameHaptics,
                onItemClick = onItemClick,
                onDragStateChange = { isAnyItemDragging = it },
                auraGlowScale = { auraGlowScaleState.value },
                levelTheme = levelTheme,
                isLandscape = isLandscape,
                isVictory = isVictory,
                modifier = Modifier
                    .width(bankWidth)
                    .fillMaxHeight()
            )
        }

        // 4. ANIMATED WOODEN LOG RAFT WITH RECESSED BERTHS
        val capacity = riverState.scenario.boatCapacity
        val boatWidth = if (capacity >= 3) 168.dp else if (capacity >= 2) 146.dp else 122.dp

        Box(
            modifier = Modifier
                .graphicsLayer {
                    val minBoatXPx = (bankWidth - 18.dp).toPx()
                    val maxBoatXPx = (totalWidth - bankWidth - boatWidth + 18.dp).toPx()
                    translationX = minBoatXPx + (maxBoatXPx - minBoatXPx) * boatProgress
                    val curveYPx = 26.dp.toPx() * sin(boatProgress * PI.toFloat())
                    translationY = (totalHeight.toPx() * 0.44f) + bobbingOffsetState.value.dp.toPx() + curveYPx
                    rotationZ = if (isRowing) {
                        (sin(boatProgress * PI.toFloat() * 2f) * 4f) + (boatTiltState.value * 1.6f)
                    } else {
                        boatTiltState.value
                    }
                }
                .testTag("boat_view")
        ) {
            BoundLogRaft(
                passengers = riverState.boatPassengers,
                capacity = capacity,
                isRowing = isRowing,
                oarAngle = { oarSweepState.value },
                headingRight = if (isRowing) (riverState.farmerBank == Bank.LEFT) else (riverState.farmerBank == Bank.RIGHT),
                isHintHighlighted = isHintHighlightingBoat,
                levelTheme = levelTheme,
                isDropActive = isDropActive,
                gameHaptics = gameHaptics,
                onPassengerClick = { item -> onItemClick(item) }
            )
        }

        // 5. Water Splash Particle Overlay
        WaterSplashParticleOverlay(
            splashEvent = splashEvent,
            modifier = Modifier.fillMaxSize()
        )
    }
}

/**
 * On-World Riverbank Pier & Characters.
 * Removes bulky opaque frames; characters stand naturally in the environment on wooden dock planks.
 */
@Composable
private fun BankZone(
    bank: Bank,
    riverState: RiverState,
    highlightedHintItem: GameItem?,
    gameHaptics: GameHaptics? = null,
    onItemClick: (GameItem) -> Unit,
    onDragStateChange: ((Boolean) -> Unit)? = null,
    auraGlowScale: () -> Float = { 1.0f },
    levelTheme: LevelTheme = LevelTheme.SPRING_VALLEY,
    isLandscape: Boolean = false,
    isVictory: Boolean = false,
    modifier: Modifier = Modifier
) {
    val isFarmerHere = riverState.farmerBank == bank
    val isGoal = riverState.isGoal() && bank == Bank.RIGHT
    val isCelebrating = (isGoal || isVictory) && bank == Bank.RIGHT
    val isHidden = riverState.isBankHidden(bank)

    Column(
        modifier = modifier.padding(horizontal = 3.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        // Floating Bank Indicator Pill
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = if (isCelebrating) {
                Color(0xEE059669)
            } else if (isGoal) {
                Color(0xDD059669)
            } else if (isFarmerHere) {
                levelTheme.dockWoodBottom.copy(alpha = 0.85f)
            } else {
                Color(0x882A1C12)
            },
            shadowElevation = if (isFarmerHere || isGoal || isCelebrating) 6.dp else 2.dp,
            border = BorderStroke(
                1.2.dp,
                if (isCelebrating) Color(0xFFFDE047) else if (isGoal) Color(0xFF86EFAC) else if (isFarmerHere) levelTheme.bankAccentColor else Color(0x66D4A373)
            ),
            modifier = Modifier.padding(bottom = 1.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (isCelebrating || isGoal) Icons.Default.Star else if (bank == Bank.LEFT) Icons.Default.PlayArrow else Icons.Default.Flag,
                    contentDescription = null,
                    tint = if (isCelebrating) Color(0xFFFEF08A) else if (isGoal) Color(0xFFFFFBEB) else if (isFarmerHere) Color(0xFFFDE68A) else Color.White,
                    modifier = Modifier.size(10.dp)
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = if (isCelebrating) "ALL TRANSFERRED!" else if (isGoal) "GOAL" else if (bank == Bank.LEFT) "LEFT BANK" else "RIGHT BANK",
                    color = if (isCelebrating) Color(0xFFFEF08A) else if (isGoal) Color(0xFFFFFBEB) else if (isFarmerHere) Color(0xFFFDE68A) else Color.White,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.5.sp
                )
            }
        }

        // Rustic Shoreline Platform with Soft Fading Gradient & Drop Shadow
        val scrollState = rememberScrollState()
        // Soft backdrop gradient that fades out toward the center river
        val bankFadeGradient = if (bank == Bank.LEFT) {
            Brush.horizontalGradient(
                colors = listOf(
                    levelTheme.dockWoodBottom.copy(alpha = 0.36f), // Soft framing at screen edge
                    levelTheme.dockWoodTop.copy(alpha = 0.16f),
                    Color.Transparent                              // Seamlessly fades toward center river
                )
            )
        } else {
            Brush.horizontalGradient(
                colors = listOf(
                    Color.Transparent,                             // Seamlessly fades from center river
                    levelTheme.dockWoodTop.copy(alpha = 0.16f),
                    levelTheme.dockWoodBottom.copy(alpha = 0.36f)  // Soft framing at screen edge
                )
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(16.dp),
                    spotColor = Color(0x35000000),
                    ambientColor = Color(0x20000000)
                )
                .clip(RoundedCornerShape(16.dp))
                .background(bankFadeGradient)
                .border(
                    width = 0.8.dp,
                    color = if (isFarmerHere) levelTheme.dockBorder.copy(alpha = 0.28f) else Color(0x1AD4A373),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 2.dp, vertical = 2.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 3.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Farmer Captain presence
                if (isFarmerHere) {
                    FarmerDockToken(isVictory = isCelebrating)
                }

                // Characters on this bank
                val itemsOnBank = riverState.scenario.items.filter {
                    riverState.isItemOnBank(it, bank)
                }

                if (isLandscape && itemsOnBank.size >= 2) {
                    // Table / Grid 2-column layout to utilize wide horizontal bank space and prevent vertical clipping
                    val chunked = itemsOnBank.chunked(2)
                    chunked.forEachIndexed { rowIndex, rowItems ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            rowItems.forEachIndexed { colIndex, item ->
                                val globalIndex = rowIndex * 2 + colIndex
                                val isHintTarget = highlightedHintItem == item

                                Box(
                                    modifier = Modifier.weight(1f),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isHidden) {
                                        ShroudedBankItemToken()
                                    } else {
                                        GameObjectToken(
                                            item = item,
                                            canInteract = isFarmerHere,
                                            bank = bank,
                                            isHintTarget = isHintTarget,
                                            auraScale = if (isFarmerHere || isHintTarget) auraGlowScale else ({ 1.0f }),
                                            phaseOffset = globalIndex * 0.7f,
                                            gameHaptics = gameHaptics,
                                            onDragStateChange = onDragStateChange,
                                            isVictory = isCelebrating,
                                            onClick = { onItemClick(item) },
                                            onDropInBoat = { onItemClick(item) }
                                        )
                                    }
                                }
                            }
                            if (rowItems.size == 1 && chunked.size > 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                } else {
                    // Single column layout
                    itemsOnBank.forEachIndexed { index, item ->
                        if (isHidden) {
                            ShroudedBankItemToken()
                        } else {
                            val isHintTarget = highlightedHintItem == item

                            GameObjectToken(
                                item = item,
                                canInteract = isFarmerHere,
                                bank = bank,
                                isHintTarget = isHintTarget,
                                auraScale = if (isFarmerHere || isHintTarget) auraGlowScale else ({ 1.0f }),
                                phaseOffset = index * 0.7f,
                                gameHaptics = gameHaptics,
                                onDragStateChange = onDragStateChange,
                                isVictory = isCelebrating,
                                onClick = { onItemClick(item) },
                                onDropInBoat = { onItemClick(item) }
                            )
                        }
                    }
                }
            }

            // Discreet scroll indicator if there are more items below the fold
            if (scrollState.canScrollForward) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xEE2A170E),
                    border = BorderStroke(1.dp, GoldenBankGlow.copy(alpha = 0.75f)),
                    shadowElevation = 4.dp,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 2.dp)
                ) {
                    Text(
                        text = "▼ MORE",
                        fontSize = 7.5.sp,
                        fontWeight = FontWeight.Black,
                        color = GoldenBankGlow,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(1.dp))
    }
}

/**
 * Animated Tactile Game Object Figurine.
 * Features:
 * - Fluid drag-and-drop gesture physics directly into the boat
 * - Instant tap to board/unboard
 * - Red silhouette & panic tremor for threatened prey (e.g. Rabbit)
 * - Comical hungry reaction emote for predator (e.g. Dog)
 * - Golden glowing halo & animated beacon when suggested by HINT
 * - Wooden plank nametag
 */
@Composable
private fun GameObjectToken(
    item: GameItem,
    canInteract: Boolean,
    bank: Bank,
    isHintTarget: Boolean,
    auraScale: () -> Float = { 1.0f },
    phaseOffset: Float,
    gameHaptics: GameHaptics? = null,
    onDragStateChange: ((Boolean) -> Unit)? = null,
    isVictory: Boolean = false,
    onClick: () -> Unit,
    onDropInBoat: () -> Unit = onClick,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "token_anim_${item.id}")

    // Organic idle floating / breathing
    val idleBobState = infiniteTransition.animateFloat(
        initialValue = -2.5f,
        targetValue = 2.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1300 + (item.ordinal * 110), easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "idle_bob"
    )

    // Celebratory victory hop when transferred across river
    val victoryHopState = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = if (isVictory) -12f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(360, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "item_victory_hop"
    )

    // Character specific personality animations:
    val itemIdleRotationState = infiniteTransition.animateFloat(
        initialValue = when (item) {
            GameItem.DOG -> -6.5f
            GameItem.FOX -> -5.0f
            GameItem.RABBIT -> -3.0f
            GameItem.SHEEP -> -2.5f
            GameItem.WOLF -> -2.0f
            else -> 0f
        },
        targetValue = when (item) {
            GameItem.DOG -> 6.5f
            GameItem.FOX -> 5.0f
            GameItem.RABBIT -> 3.0f
            GameItem.SHEEP -> 2.5f
            GameItem.WOLF -> 2.0f
            else -> 0f
        },
        animationSpec = infiniteRepeatable(
            animation = tween(
                when (item) {
                    GameItem.DOG -> 340
                    GameItem.RABBIT -> 280
                    GameItem.FOX -> 440
                    GameItem.SHEEP -> 850
                    GameItem.WOLF -> 650
                    else -> 1000
                },
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "item_tail_wag"
    )

    // Organic breathing & ear twitches:
    val itemOrganicScaleState = infiniteTransition.animateFloat(
        initialValue = when (item) {
            GameItem.RABBIT -> 0.95f
            GameItem.CABBAGE, GameItem.CORN -> 0.96f
            GameItem.DOG -> 0.97f
            else -> 0.98f
        },
        targetValue = when (item) {
            GameItem.RABBIT -> 1.05f
            GameItem.CABBAGE, GameItem.CORN -> 1.04f
            GameItem.DOG -> 1.03f
            else -> 1.02f
        },
        animationSpec = infiniteRepeatable(
            animation = tween(
                when (item) {
                    GameItem.RABBIT -> 320
                    GameItem.DOG -> 400
                    GameItem.CABBAGE, GameItem.CORN -> 1100
                    else -> 900
                },
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "item_organic_scale"
    )

    // Tap-Hop spring jump animation
    var isTapped by remember { mutableStateOf(false) }
    val tapHopOffset by animateFloatAsState(
        targetValue = if (isTapped) -16f else 0f,
        animationSpec = spring(dampingRatio = 0.42f, stiffness = 440f),
        finishedListener = { isTapped = false },
        label = "tap_hop"
    )

    // Drag-and-drop state physics
    var dragOffset by remember { mutableStateOf(Offset.Zero) }
    var isDragging by remember { mutableStateOf(false) }

    val animatedDragOffsetX by animateFloatAsState(
        targetValue = if (isDragging) dragOffset.x else 0f,
        animationSpec = spring(dampingRatio = 0.70f, stiffness = 550f),
        label = "drag_x"
    )
    val animatedDragOffsetY by animateFloatAsState(
        targetValue = if (isDragging) dragOffset.y else 0f,
        animationSpec = spring(dampingRatio = 0.70f, stiffness = 550f),
        label = "drag_y"
    )

    // Landing arrival bounce on bank when unboarded or when level starts
    val arrivalSpring = remember(item.id, bank) { Animatable(0f) }
    LaunchedEffect(item.id, bank) {
        arrivalSpring.snapTo(0f)
        arrivalSpring.animateTo(
            targetValue = 1f,
            animationSpec = spring(dampingRatio = 0.58f, stiffness = 420f)
        )
    }

    // Invalid action shudder shake
    val invalidShakeAnim = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    val gestureModifier = if (canInteract) {
        Modifier.pointerInput(canInteract, bank) {
            awaitEachGesture {
                val down = awaitFirstDown(requireUnconsumed = false)
                var totalDrag = Offset.Zero
                var hasExceededSlop = false
                var isHorizontalDrag = false
                val pointerId = down.id

                while (true) {
                    val event = awaitPointerEvent()
                    val change = event.changes.firstOrNull { it.id == pointerId } ?: break

                    if (!change.pressed) {
                        // Released finger
                        onDragStateChange?.invoke(false)
                        if (hasExceededSlop && isHorizontalDrag) {
                            isDragging = false
                            // For LEFT bank, boat is on the right (+X > 28dp)
                            // For RIGHT bank, boat is on the left (-X < -28dp)
                            val isTowardsBoat = if (bank == Bank.LEFT) totalDrag.x > 28f else totalDrag.x < -28f
                            if (isTowardsBoat) {
                                gameHaptics?.onDropSuccess()
                                onDropInBoat()
                            }
                            dragOffset = Offset.Zero
                        } else if (!hasExceededSlop) {
                            // Instant Tap
                            isTapped = true
                            gameHaptics?.onDropSuccess()
                            onClick()
                        }
                        break
                    }

                    val dragAmount = change.positionChange()
                    totalDrag += dragAmount

                    if (!hasExceededSlop && totalDrag.getDistance() > viewConfiguration.touchSlop) {
                        hasExceededSlop = true
                        // Only engage horizontal drag-to-boat if the movement is predominantly horizontal towards the river
                        val isTowardsRiver = if (bank == Bank.LEFT) totalDrag.x > 0 else totalDrag.x < 0
                        if (kotlin.math.abs(totalDrag.x) > kotlin.math.abs(totalDrag.y) * 0.9f && isTowardsRiver) {
                            isHorizontalDrag = true
                            isDragging = true
                            onDragStateChange?.invoke(true)
                            gameHaptics?.onDragStart()
                        }
                    }

                    if (hasExceededSlop && isHorizontalDrag) {
                        change.consume()
                        dragOffset = totalDrag
                    }
                }
                isDragging = false
                onDragStateChange?.invoke(false)
                dragOffset = Offset.Zero
            }
        }
    } else {
        Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            gameHaptics?.onInvalidMove()
            coroutineScope.launch {
                invalidShakeAnim.snapTo(0f)
                invalidShakeAnim.animateTo(-6f, tween(35))
                invalidShakeAnim.animateTo(6f, tween(45))
                invalidShakeAnim.animateTo(-3f, tween(45))
                invalidShakeAnim.animateTo(3f, tween(45))
                invalidShakeAnim.animateTo(0f, tween(35))
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .zIndex(if (isDragging) 30f else 1f)
            .graphicsLayer {
                val breathing = itemOrganicScaleState.value
                val hopSquashFactor = if (tapHopOffset < 0f) {
                    1f + (-tapHopOffset / 16f) * 0.12f
                } else 1f

                val arrivalP = arrivalSpring.value
                val arrivalHop = (1f - arrivalP) * -22.dp.toPx()
                val arrivalSquashX = 1f + (1f - arrivalP) * 0.14f
                val arrivalSquashY = 0.86f + arrivalP * 0.14f

                val baseScaleX = if (isDragging) 1.18f else (1f / (breathing * 0.96f + 0.04f)) / hopSquashFactor
                val baseScaleY = if (isDragging) 1.18f else (breathing * hopSquashFactor)

                scaleX = baseScaleX * arrivalSquashX
                scaleY = baseScaleY * arrivalSquashY
                rotationZ = itemIdleRotationState.value + invalidShakeAnim.value
                translationX = animatedDragOffsetX
                translationY = (idleBobState.value + tapHopOffset + victoryHopState.value).dp.toPx() + animatedDragOffsetY + arrivalHop
            }
            .then(gestureModifier)
            .testTag("item_badge_${item.id}"),
        contentAlignment = Alignment.Center
    ) {
        // Drag in progress indicator pill
        if (isDragging) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = GoldenBankGlow,
                border = BorderStroke(1.dp, Color.White),
                shadowElevation = 8.dp,
                modifier = Modifier
                    .offset(y = (-36).dp)
                    .zIndex(35f)
            ) {
                Text(
                    text = "⚓ DROP IN BOAT",
                    fontSize = 8.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF1F1501),
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
        // Pulsing Golden Spotlight Halo when selected by HINT
        if (isHintTarget) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .graphicsLayer {
                        val s = auraScale() * 1.15f
                        scaleX = s
                        scaleY = s
                    }
                    .clip(CircleShape)
                    .background(GoldenBankGlow.copy(alpha = 0.45f))
            )
        } else if (canInteract) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .graphicsLayer {
                        val s = auraScale()
                        scaleX = s
                        scaleY = s
                    }
                    .clip(CircleShape)
                    .background(GoldenBankGlow.copy(alpha = 0.20f))
            )
        }

        // Tactile Figurine & Plaque
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 1.dp)
        ) {
            // Hint Beacon Pill
            if (isHintTarget) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color(0xFFF59E0B),
                    border = BorderStroke(1.dp, Color(0xFFFFFBEB)),
                    shadowElevation = 4.dp,
                    modifier = Modifier.offset(y = (-4).dp)
                ) {
                    Text(
                        text = "💡 TAP TO LOAD",
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                    )
                }
            }

            // Character Figurine Stand with Contact Shadow & Tactile Plinth
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .size(width = 52.dp, height = 56.dp)
            ) {
                // Grounding Contact Shadow underneath the figurine
                Box(
                    modifier = Modifier
                        .width(42.dp)
                        .height(8.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    Color(0x990A0604),
                                    Color(0x330A0604),
                                    Color.Transparent
                                )
                            )
                        )
                )

                // Illuminated Plinth Base Ring (when interactive or highlighted)
                if (canInteract || isHintTarget) {
                    Box(
                        modifier = Modifier
                            .width(36.dp)
                            .height(5.dp)
                            .align(Alignment.BottomCenter)
                            .clip(RoundedCornerShape(50))
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        Color(0x44FDE68A),
                                        GoldenBankGlow.copy(alpha = 0.85f),
                                        Color(0x44FDE68A)
                                    )
                                )
                            )
                    )
                }

                // Static image figurine with balanced character scaling
                Image(
                    painter = painterResource(id = item.drawableRes),
                    contentDescription = item.displayName,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 2.dp)
                        .graphicsLayer {
                            scaleX = item.visualScale
                            scaleY = item.visualScale
                        }
                )

                // Top-Right Badge: Victory Star or Interactive Board Icon
                if (isVictory) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFEAB308),
                        border = BorderStroke(1.2.dp, Color(0xFFFEF08A)),
                        shadowElevation = 4.dp,
                        modifier = Modifier
                            .size(17.dp)
                            .align(Alignment.TopEnd)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = "⭐", fontSize = 10.sp)
                        }
                    }
                } else if (canInteract) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF16A34A),
                        border = BorderStroke(1.2.dp, Color(0xFFBBF7D0)),
                        shadowElevation = 3.dp,
                        modifier = Modifier
                            .size(16.dp)
                            .align(Alignment.TopEnd)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Board",
                                tint = Color.White,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            // Sleek Carved Wooden Nametag Plaque (Dog, Rabbit, Cabbage)
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = if (canInteract) WoodSignboardDark else Color(0xEE090D16),
                border = BorderStroke(
                    1.2.dp,
                    if (isHintTarget || canInteract) GoldenBankGlow else WoodSignboardBorder
                ),
                shadowElevation = 3.dp
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.emoji,
                        fontSize = 9.sp,
                        modifier = Modifier.padding(end = 2.dp)
                    )
                    Text(
                        text = item.displayName,
                        fontSize = 9.5.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (isHintTarget || canInteract) GoldenBankGlow else Color(0xFFD4B89B),
                        maxLines = 1
                    )
                }
            }
        }
    }
}

/**
 * Handcrafted Bound Log Raft with Recessed Passenger Berths and Seated Farmer.
 * Tied wooden logs with rope lashing matching user reference.
 */
@Composable
fun BoundLogRaft(
    passengers: List<GameItem>,
    capacity: Int = 1,
    isRowing: Boolean,
    oarAngle: () -> Float = { 0f },
    headingRight: Boolean = true,
    isHintHighlighted: Boolean = false,
    levelTheme: LevelTheme = LevelTheme.SPRING_VALLEY,
    isDropActive: Boolean = false,
    gameHaptics: GameHaptics? = null,
    onPassengerClick: (GameItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val raftWidth = if (capacity >= 3) 168.dp else if (capacity >= 2) 146.dp else 122.dp

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // Glowing Golden Halo if Raft is Highlighted by Hint
        if (isHintHighlighted) {
            Box(
                modifier = Modifier
                    .size(width = raftWidth + 16.dp, height = 76.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(GoldenBankGlow.copy(alpha = 0.35f))
            )
        }

        // Dynamic Wooden Oars with Water Dipping
        // Port Oar
        Surface(
            shape = RoundedCornerShape(2.dp),
            color = levelTheme.dockWoodBottom,
            border = BorderStroke(0.8.dp, Color(0x770F172A)),
            modifier = Modifier
                .offset(x = (-raftWidth / 2) + 14.dp, y = 14.dp)
                .width(38.dp)
                .height(6.dp)
                .graphicsLayer { rotationZ = -32f + oarAngle() }
        ) {
            Box(
                modifier = Modifier
                    .size(width = 11.dp, height = 6.dp)
                    .background(levelTheme.raftWoodTone)
            )
        }

        // Starboard Oar
        Surface(
            shape = RoundedCornerShape(2.dp),
            color = levelTheme.dockWoodBottom,
            border = BorderStroke(0.8.dp, Color(0x770F172A)),
            modifier = Modifier
                .offset(x = (raftWidth / 2) - 14.dp, y = 14.dp)
                .width(38.dp)
                .height(6.dp)
                .graphicsLayer { rotationZ = 32f - oarAngle() }
        ) {
            Box(
                modifier = Modifier
                    .size(width = 11.dp, height = 6.dp)
                    .background(levelTheme.raftWoodTone)
            )
        }

        // Main Bound Wooden Logs Hull with Biome Wood Tone
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = levelTheme.raftWoodTone,
            border = BorderStroke(2.2.dp, if (isHintHighlighted) GoldenBankGlow else levelTheme.dockBorder),
            shadowElevation = 12.dp,
            modifier = Modifier
                .width(raftWidth)
                .height(68.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                levelTheme.raftWoodTone,
                                levelTheme.dockWoodBottom,
                                levelTheme.raftWoodTone.copy(alpha = 0.85f)
                            )
                        )
                    )
            ) {
                // Bound Log Planks Grooves
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    repeat(capacity + 4) {
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .fillMaxHeight()
                                .background(Color(0x551B1008))
                        )
                    }
                }

                // Hemp Rope Lashings across the logs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .width(5.dp)
                            .fillMaxHeight()
                            .background(levelTheme.raftRopeColor.copy(alpha = 0.75f))
                    )
                    Box(
                        modifier = Modifier
                            .width(5.dp)
                            .fillMaxHeight()
                            .background(levelTheme.raftRopeColor.copy(alpha = 0.75f))
                    )
                }

                // Interior Layout: Seated Farmer & Recessed Passenger Cargo Berths
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 6.dp, vertical = 2.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Farmer Figurine on the Raft
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 2.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.BottomCenter,
                            modifier = Modifier
                                .size(30.dp)
                                .graphicsLayer {
                                    if (isRowing) {
                                        val angle = oarAngle()
                                        // Lean forward on power stroke, lean back on return
                                        rotationZ = -angle * 0.40f
                                        translationX = if (headingRight) (-angle * 0.16f) else (angle * 0.16f)
                                    }
                                }
                        ) {
                            // Subtle contact shadow
                            Box(
                                modifier = Modifier
                                    .width(22.dp)
                                    .height(4.dp)
                                    .align(Alignment.BottomCenter)
                                    .background(
                                        Brush.radialGradient(
                                            listOf(Color(0x88000000), Color.Transparent)
                                        )
                                    )
                            )
                            Image(
                                painter = painterResource(id = R.drawable.img_farmer),
                                contentDescription = "Farmer",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .graphicsLayer {
                                        if (!headingRight) {
                                            rotationY = 180f
                                        }
                                    }
                            )
                        }
                        Spacer(modifier = Modifier.height(1.dp))
                        Text(
                            text = if (isRowing) "SAILING" else "FARMER",
                            fontSize = 7.sp,
                            color = GoldenBankGlow,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }

                    // Recessed Cargo Berths
                    for (i in 0 until capacity) {
                        val passenger = passengers.getOrNull(i)
                        if (passenger != null) {
                            RaftPassengerSlot(
                                passenger = passenger,
                                isRowing = isRowing,
                                headingRight = headingRight,
                                oarAngle = oarAngle,
                                gameHaptics = gameHaptics,
                                onPassengerClick = onPassengerClick
                            )
                        } else {
                            // Empty Recessed Cargo Berth - Welcoming Animated Drop Slot with High-Contrast Target Feedback
                            val slotTransition = rememberInfiniteTransition(label = "slot_pulse_$i")
                            val slotScale by slotTransition.animateFloat(
                                initialValue = 0.94f,
                                targetValue = 1.05f,
                                animationSpec = infiniteRepeatable(
                                    animation = tween(850, easing = FastOutSlowInEasing),
                                    repeatMode = RepeatMode.Reverse
                                ),
                                label = "slot_scale"
                            )
                            val activePulseScale by slotTransition.animateFloat(
                                initialValue = 0.96f,
                                targetValue = 1.15f,
                                animationSpec = infiniteRepeatable(
                                    animation = tween(380, easing = FastOutSlowInEasing),
                                    repeatMode = RepeatMode.Reverse
                                ),
                                label = "active_pulse_scale"
                            )
                            val slotIconBounce by slotTransition.animateFloat(
                                initialValue = -1.5f,
                                targetValue = 1.5f,
                                animationSpec = infiniteRepeatable(
                                    animation = tween(if (isDropActive) 350 else 600, easing = FastOutSlowInEasing),
                                    repeatMode = RepeatMode.Reverse
                                ),
                                label = "slot_bounce"
                            )

                            val borderStroke = if (isDropActive) {
                                BorderStroke(2.6.dp, Color(0xFFFDE047))
                            } else {
                                BorderStroke(1.4.dp, GoldenBankGlow.copy(alpha = 0.85f * slotScale))
                            }

                            Box(
                                contentAlignment = Alignment.Center
                            ) {
                                if (isDropActive) {
                                    // High-contrast vibrant pulsing outer aura
                                    Box(
                                        modifier = Modifier
                                            .size(width = 38.dp, height = 42.dp)
                                            .scale(activePulseScale * 1.08f)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(Color(0x55FDE047))
                                    )
                                }

                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (isDropActive) Color(0xDD3D2600) else Color(0x880B101D),
                                    border = borderStroke,
                                    shadowElevation = if (isDropActive) 6.dp else 1.dp,
                                    modifier = Modifier
                                        .size(width = 34.dp, height = 38.dp)
                                        .scale(if (isDropActive) activePulseScale else slotScale)
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "Drop slot",
                                            tint = if (isDropActive) Color(0xFFFFFBEB) else GoldenBankGlow,
                                            modifier = Modifier
                                                .size(if (isDropActive) 15.dp else 13.dp)
                                                .offset(y = slotIconBounce.dp)
                                        )
                                        Text(
                                            text = "DROP",
                                            fontSize = if (isDropActive) 7.5.sp else 7.sp,
                                            color = if (isDropActive) Color(0xFFFFFBEB) else GoldenBankGlow,
                                            fontWeight = FontWeight.Black,
                                            letterSpacing = 0.5.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Passenger seated in the raft with both click-to-unboard and drag-to-shore gestures.
 */
@Composable
private fun RaftPassengerSlot(
    passenger: GameItem,
    isRowing: Boolean,
    headingRight: Boolean,
    oarAngle: () -> Float = { 0f },
    gameHaptics: GameHaptics? = null,
    onPassengerClick: (GameItem) -> Unit
) {
    var dragOffset by remember { mutableStateOf(Offset.Zero) }
    var isDragging by remember { mutableStateOf(false) }

    // Boarding hop and landing spring animation
    val embarkAnim = remember(passenger.id) { Animatable(0f) }
    LaunchedEffect(passenger.id) {
        embarkAnim.snapTo(0f)
        embarkAnim.animateTo(
            targetValue = 1f,
            animationSpec = spring(dampingRatio = 0.52f, stiffness = 420f)
        )
    }

    val animatedDragOffsetX by animateFloatAsState(
        targetValue = if (isDragging) dragOffset.x else 0f,
        animationSpec = spring(dampingRatio = 0.70f, stiffness = 550f),
        label = "passenger_drag_x"
    )
    val animatedDragOffsetY by animateFloatAsState(
        targetValue = if (isDragging) dragOffset.y else 0f,
        animationSpec = spring(dampingRatio = 0.70f, stiffness = 550f),
        label = "passenger_drag_y"
    )

    val dragModifier = if (!isRowing) {
        Modifier.pointerInput(passenger, headingRight) {
            awaitEachGesture {
                val down = awaitFirstDown(requireUnconsumed = false)
                var totalDrag = Offset.Zero
                var hasExceededSlop = false
                val pointerId = down.id

                while (true) {
                    val event = awaitPointerEvent()
                    val change = event.changes.firstOrNull { it.id == pointerId } ?: break

                    if (!change.pressed) {
                        if (hasExceededSlop) {
                            isDragging = false
                            val isTowardsShore = if (headingRight) totalDrag.x > 22f else totalDrag.x < -22f
                            if (isTowardsShore || totalDrag.getDistance() > 32f) {
                                gameHaptics?.onDropSuccess()
                                onPassengerClick(passenger)
                            }
                            dragOffset = Offset.Zero
                        } else {
                            gameHaptics?.onDropSuccess()
                            onPassengerClick(passenger)
                        }
                        break
                    }

                    val dragAmount = change.positionChange()
                    totalDrag += dragAmount

                    if (!hasExceededSlop && totalDrag.getDistance() > viewConfiguration.touchSlop) {
                        hasExceededSlop = true
                        isDragging = true
                        gameHaptics?.onDragStart()
                    }

                    if (hasExceededSlop) {
                        change.consume()
                        dragOffset = totalDrag
                    }
                }
                isDragging = false
                dragOffset = Offset.Zero
            }
        }
    } else {
        Modifier
    }

    val embarkFraction = embarkAnim.value
    val embarkHop = (1f - embarkFraction) * -22f
    val embarkScaleX = 1f + (1f - embarkFraction) * 0.16f
    val embarkScaleY = 0.84f + (embarkFraction * 0.16f)
    val passengerRowSway = if (isRowing) {
        kotlin.math.sin(Math.toRadians(oarAngle().toDouble())).toFloat() * 4.5f
    } else 0f

    Box(
        modifier = Modifier
            .zIndex(if (isDragging) 30f else 1f)
            .offset {
                IntOffset(
                    animatedDragOffsetX.roundToInt(),
                    (animatedDragOffsetY + embarkHop).roundToInt()
                )
            }
            .graphicsLayer {
                val dragScale = if (isDragging) 1.15f else 1.0f
                scaleX = dragScale * embarkScaleX
                scaleY = dragScale * embarkScaleY
                rotationZ = passengerRowSway
            }
            .then(dragModifier)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(WoodButtonTop, WoodButtonBottom)
                    )
                )
                .border(1.4.dp, GoldenBankGlow, RoundedCornerShape(10.dp))
                .padding(horizontal = 4.dp, vertical = 2.dp)
                .testTag("boat_passenger_${passenger.id}")
        ) {
            // Seated/Standing 3D Figurine in Boat Berth with contact shadow
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier.size(width = 34.dp, height = 32.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(4.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.radialGradient(
                                listOf(Color(0x99000000), Color.Transparent)
                            )
                        )
                )
                Image(
                    painter = painterResource(id = passenger.drawableRes),
                    contentDescription = passenger.displayName,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 1.dp)
                        .graphicsLayer {
                            scaleX = passenger.visualScale
                            scaleY = passenger.visualScale
                            if (!headingRight) {
                                rotationY = 180f
                            }
                        }
                )
            }
            Text(
                text = passenger.displayName.uppercase(),
                fontSize = 7.sp,
                color = GoldenBankGlow,
                fontWeight = FontWeight.ExtraBold,
                maxLines = 1
            )
        }
    }
}

/**
 * Animated Farmer Captain Dock Token on the River Bank.
 */
@Composable
private fun FarmerDockToken(isVictory: Boolean = false) {
    val infiniteTransition = rememberInfiniteTransition(label = "farmer_idle")
    val farmerBobState = infiniteTransition.animateFloat(
        initialValue = -1.5f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "farmer_bob"
    )
    val victoryHopState = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = if (isVictory) -12f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(360, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "farmer_victory_hop"
    )

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = WoodButtonBottom,
        border = BorderStroke(1.5.dp, if (isVictory) Color(0xFFFDE047) else GoldenBankGlow),
        shadowElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                translationY = (farmerBobState.value + victoryHopState.value).dp.toPx()
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(WoodButtonTop, WoodButtonBottom)
                    )
                )
                .padding(horizontal = 5.dp, vertical = 3.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier.size(28.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_farmer),
                        contentDescription = "Farmer",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                Column {
                    Text(
                        text = if (isVictory) "HOORAY!" else "FARMER",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (isVictory) Color(0xFFFEF08A) else GoldenBankGlow,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = if (isVictory) "Safe Across" else "Docked",
                        fontSize = 7.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF94A3B8)
                    )
                }
            }
        }
    }
}

/**
 * Shrouded Mysterious Item Token (Misty bank mode).
 */
@Composable
private fun ShroudedBankItemToken() {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xCC334155),
        border = BorderStroke(1.dp, Color(0xFF64748B)),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("shrouded_item_badge")
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text("🌫️", fontSize = 12.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Hidden",
                fontSize = 9.5.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFCBD5E1)
            )
        }
    }
}
