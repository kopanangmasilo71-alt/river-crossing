package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.model.LevelTheme
import com.example.model.PuzzleScenario
import com.example.model.PuzzleScenarios
import com.example.ui.theme.GoldenBankGlow
import com.example.ui.theme.MenuBtnGreenBottom
import com.example.ui.theme.MenuBtnGreenMid
import com.example.ui.theme.MenuBtnGreenTop
import com.example.ui.theme.WoodButtonBottom
import com.example.ui.theme.WoodButtonTop
import com.example.ui.theme.WoodGoldenText
import com.example.ui.theme.WoodInsetPanel
import com.example.ui.theme.WoodSignboardBorder
import com.example.ui.theme.WoodSignboardDark
import com.example.ui.theme.WoodTextMuted
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun VictoryDialog(
    moveCount: Int,
    timeSeconds: Long,
    scenario: PuzzleScenario = PuzzleScenarios.CLASSIC,
    levelTheme: LevelTheme? = null,
    isNewBestTime: Boolean = false,
    bestMoves: Int? = null,
    onPlayAgain: () -> Unit,
    onNextLevel: (() -> Unit)? = null,
    onViewLeaderboard: () -> Unit = {},
    onSelectLevel: () -> Unit = {},
    onMainMenu: (() -> Unit)? = null,
    onStarPop: ((Int) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val theme = levelTheme ?: remember(scenario.levelNumber) { LevelTheme.forScenario(scenario) }
    val dialogScale = remember { Animatable(0.4f) }
    val star1Scale = remember { Animatable(0f) }
    val star2Scale = remember { Animatable(0f) }
    val star3Scale = remember { Animatable(0f) }

    val star1Rotation = remember { Animatable(-35f) }
    val star2Rotation = remember { Animatable(-35f) }
    val star3Rotation = remember { Animatable(-35f) }

    val animatedMoves = remember { Animatable(0f) }
    val animatedTime = remember { Animatable(0f) }

    val infiniteTransition = rememberInfiniteTransition(label = "victory_shimmer")
    val trophyHover by infiniteTransition.animateFloat(
        initialValue = -4f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "trophy_hover"
    )
    val trophyAuraRotate by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(18000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "trophy_aura_rotate"
    )
    val ribbonShimmer by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ribbon_shimmer"
    )
    val characterHopPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "character_hop_phase"
    )

    val isOptimal = moveCount <= scenario.optimalMoves
    val starCount = if (moveCount <= scenario.optimalMoves) 3 else if (moveCount <= scenario.optimalMoves + 2) 2 else 1

    LaunchedEffect(Unit) {
        dialogScale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )

        // Rolling number counters
        animatedMoves.animateTo(
            targetValue = moveCount.toFloat(),
            animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing)
        )
        animatedTime.animateTo(
            targetValue = timeSeconds.toFloat(),
            animationSpec = tween(durationMillis = 750, easing = FastOutSlowInEasing)
        )

        // Staggered Star pops with sound
        delay(120)
        onStarPop?.invoke(0)
        star1Rotation.animateTo(0f, spring(dampingRatio = 0.5f, stiffness = 500f))
        star1Scale.animateTo(1f, spring(dampingRatio = 0.55f, stiffness = 420f))

        if (starCount >= 2) {
            delay(150)
            onStarPop?.invoke(1)
            star2Rotation.animateTo(0f, spring(dampingRatio = 0.5f, stiffness = 500f))
            star2Scale.animateTo(1f, spring(dampingRatio = 0.55f, stiffness = 420f))
        }

        if (starCount >= 3) {
            delay(150)
            onStarPop?.invoke(2)
            star3Rotation.animateTo(0f, spring(dampingRatio = 0.5f, stiffness = 500f))
            star3Scale.animateTo(1f, spring(dampingRatio = 0.55f, stiffness = 420f))
        }
    }

    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(theme.headerBackgroundColors.first().copy(alpha = 0.70f))
                .padding(horizontal = 16.dp, vertical = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                shape = RoundedCornerShape(26.dp),
                color = theme.headerBackgroundColors.last().copy(alpha = 0.96f),
                border = BorderStroke(2.dp, Brush.verticalGradient(theme.headerBorderColors)),
                shadowElevation = 16.dp,
                modifier = Modifier
                    .fillMaxWidth(0.94f)
                    .widthIn(max = 410.dp)
                    .scale(dialogScale.value)
                    .testTag("victory_dialog")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 1. Radiant Animated Trophy Header
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(110.dp)
                    ) {
                        // Rotating Starburst Aura
                        Canvas(
                            modifier = Modifier
                                .size(110.dp)
                                .rotate(trophyAuraRotate)
                        ) {
                            val cx = size.width / 2f
                            val cy = size.height / 2f
                            val rayCount = 12
                            val r = size.width * 0.46f

                            for (i in 0 until rayCount) {
                                val angle = (i * 2f * PI.toFloat() / rayCount)
                                val path = Path().apply {
                                    moveTo(cx, cy)
                                    val a1 = angle - 0.12f
                                    val a2 = angle + 0.12f
                                    lineTo(cx + cos(a1) * r, cy + sin(a1) * r)
                                    lineTo(cx + cos(a2) * r, cy + sin(a2) * r)
                                    close()
                                }
                                drawPath(path, GoldenBankGlow.copy(alpha = 0.16f))
                            }
                        }

                        // Floating Golden Medallion
                        Surface(
                            shape = CircleShape,
                            color = WoodInsetPanel,
                            border = BorderStroke(3.dp, GoldenBankGlow),
                            shadowElevation = 10.dp,
                            modifier = Modifier
                                .size(76.dp)
                                .offset(y = trophyHover.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "🏆",
                                    fontSize = 38.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Title
                    Text(
                        text = if (scenario.levelNumber >= 100) "👑 ALL 100 LEVELS CONQUERED! 👑" else "Level ${scenario.levelNumber} Cleared!",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = if (scenario.levelNumber >= 100) 20.sp else 22.sp,
                        color = GoldenBankGlow,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    if (scenario.levelNumber >= 100) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFF78350F).copy(alpha = 0.95f),
                            border = BorderStroke(1.5.dp, Color(0xFFFFD700)),
                            modifier = Modifier.padding(bottom = 6.dp)
                        ) {
                            Text(
                                text = "🏆 SUPREME GRANDMASTER OF RIVER CROSSING! You conquered all 100 handcrafted river logic puzzles!",
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFD700),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }

                    // Unique Biome Theme Tag
                    val theme = remember(scenario.levelNumber) { LevelTheme.forScenario(scenario) }
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = theme.badgeBgColor.copy(alpha = 0.90f),
                        border = BorderStroke(1.dp, theme.bankAccentColor.copy(alpha = 0.60f))
                    ) {
                        Text(
                            text = "${theme.iconEmoji} ${theme.name} • ${theme.tagline}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = theme.badgeTextColor,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
                        )
                    }

                    // Shimmering Record Ribbon (if new best or optimal)
                    if (isNewBestTime || isOptimal) {
                        Spacer(modifier = Modifier.height(6.dp))
                        val shimmerBrush = Brush.linearGradient(
                            colors = listOf(
                                GoldenBankGlow.copy(alpha = 0.85f),
                                Color.White,
                                GoldenBankGlow.copy(alpha = 0.85f)
                            ),
                            start = Offset(ribbonShimmer * 300f - 150f, 0f),
                            end = Offset(ribbonShimmer * 300f + 150f, 0f)
                        )
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = WoodInsetPanel,
                            border = BorderStroke(1.2.dp, shimmerBrush)
                        ) {
                            Text(
                                text = if (isNewBestTime) "⚡ NEW BEST TIME RECORD! ⚡" else "✨ MATHEMATICALLY OPTIMAL! ✨",
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = GoldenBankGlow,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // 2. Animated Staggered 3-Star Podium
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Star 1
                        AnimatedStarPill(
                            isEarned = starCount >= 1,
                            scale = star1Scale.value,
                            rotation = star1Rotation.value,
                            size = 32.dp
                        )
                        // Star 2 (Center Hero Star)
                        AnimatedStarPill(
                            isEarned = starCount >= 2,
                            scale = star2Scale.value,
                            rotation = star2Rotation.value,
                            size = 42.dp
                        )
                        // Star 3
                        AnimatedStarPill(
                            isEarned = starCount >= 3,
                            scale = star3Scale.value,
                            rotation = star3Rotation.value,
                            size = 32.dp
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // 3. Rescued Characters Victory Parade Dock
                    WoodInsetBox(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Synchronized Bouncing Rescued Characters
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(bottom = 6.dp)
                            ) {
                                // Farmer
                                val farmerHop = (-abs(sin(characterHopPhase.toDouble())) * 7f).toFloat()
                                Image(
                                    painter = painterResource(id = R.drawable.img_farmer),
                                    contentDescription = "Farmer",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(32.dp)
                                        .offset(y = farmerHop.dp)
                                        .clip(CircleShape)
                                        .border(1.5.dp, GoldenBankGlow, CircleShape)
                                )

                                // Rescued Cargo
                                scenario.items.forEachIndexed { index, item ->
                                    val itemHop = (-abs(sin(characterHopPhase.toDouble() + (index + 1) * 0.85)) * 7f).toFloat()
                                    Image(
                                        painter = painterResource(id = item.drawableRes),
                                        contentDescription = item.displayName,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(32.dp)
                                            .offset(y = itemHop.dp)
                                            .clip(CircleShape)
                                            .border(1.5.dp, GoldenBankGlow, CircleShape)
                                    )
                                }
                            }

                            val displayMoves = animatedMoves.value.toInt()
                            val displayTime = animatedTime.value.toLong()

                            Text(
                                text = "Time: ${formatTime(displayTime)}  •  $displayMoves Moves",
                                fontSize = 15.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldenBankGlow
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (isOptimal) {
                                    "✨ Perfect solution! You reached the theoretical optimum of ${scenario.optimalMoves} moves!"
                                } else {
                                    "Great crossing! Optimal is ${scenario.optimalMoves} moves. Can you solve it even faster?"
                                },
                                fontSize = 11.sp,
                                color = WoodGoldenText,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // 4. Detailed Stat Pills
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatPill(label = "Time", value = formatTime(animatedTime.value.toLong()))
                        StatPill(label = "Moves", value = "${animatedMoves.value.toInt()}")
                        StatPill(label = "Optimal", value = "${scenario.optimalMoves}")
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 5. Action Controls
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (onNextLevel != null) {
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = theme.sailButtonColors.first(),
                                border = BorderStroke(1.8.dp, theme.sailButtonBorderColors.first()),
                                shadowElevation = 6.dp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(onClick = onNextLevel)
                                    .testTag("victory_next_level_button")
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            Brush.verticalGradient(
                                                theme.sailButtonColors
                                            )
                                        )
                                        .padding(vertical = 12.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            "Next Level",
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = Color.White
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            "➔",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = WoodInsetPanel,
                                border = BorderStroke(1.dp, WoodSignboardBorder),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable(onClick = onPlayAgain)
                                    .testTag("victory_play_again")
                            ) {
                                Row(
                                    modifier = Modifier.padding(vertical = 10.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Refresh,
                                        contentDescription = "Replay",
                                        tint = GoldenBankGlow,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Replay", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GoldenBankGlow)
                                }
                            }

                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = WoodInsetPanel,
                                border = BorderStroke(1.dp, GoldenBankGlow),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable(onClick = onViewLeaderboard)
                                    .testTag("victory_view_leaderboard")
                            ) {
                                Row(
                                    modifier = Modifier.padding(vertical = 10.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.EmojiEvents,
                                        contentDescription = "Leaderboard",
                                        tint = GoldenBankGlow,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Records", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GoldenBankGlow)
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = WoodInsetPanel,
                                border = BorderStroke(1.dp, WoodSignboardBorder),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable(onClick = onSelectLevel)
                                    .testTag("victory_choose_level")
                            ) {
                                Text(
                                    text = "Levels Menu",
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = WoodGoldenText,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }

                            if (onMainMenu != null) {
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = WoodInsetPanel,
                                    border = BorderStroke(1.dp, WoodSignboardBorder),
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable(onClick = onMainMenu)
                                        .testTag("victory_main_menu_button")
                                ) {
                                    Text(
                                        text = "Main Menu",
                                        fontSize = 11.5.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = WoodTextMuted,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // FOREGROUND CELEBRATION OVERLAY
            // Confetti, fireworks, and sparkling starburst cascade directly in FRONT of the dialog card
            CelebrationConfettiOverlay(
                isVictory = true,
                showBanner = false,
                theme = levelTheme,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun AnimatedStarPill(
    isEarned: Boolean,
    scale: Float,
    rotation: Float,
    size: androidx.compose.ui.unit.Dp
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size + 8.dp)
            .scale(if (isEarned) scale else 1f)
            .rotate(if (isEarned) rotation else 0f)
    ) {
        if (isEarned && scale > 0.8f) {
            Surface(
                shape = CircleShape,
                color = GoldenBankGlow.copy(alpha = 0.15f),
                modifier = Modifier.size(size + 6.dp)
            ) {}
        }

        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Star",
            tint = if (isEarned) GoldenBankGlow else WoodTextMuted.copy(alpha = 0.35f),
            modifier = Modifier.size(size)
        )
    }
}

@Composable
private fun StatPill(label: String, value: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = WoodInsetPanel,
        border = BorderStroke(1.dp, WoodSignboardBorder),
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = label, fontSize = 10.sp, color = WoodTextMuted, fontWeight = FontWeight.Medium)
            Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = GoldenBankGlow)
        }
    }
}
