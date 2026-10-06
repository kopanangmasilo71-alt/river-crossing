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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Undo
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
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
import com.example.model.ViolationType
import com.example.ui.theme.GoldenBankGlow
import com.example.ui.theme.MenuBtnAmberBorder
import com.example.ui.theme.MenuBtnAmberBottom
import com.example.ui.theme.MenuBtnAmberMid
import com.example.ui.theme.MenuBtnAmberTop
import com.example.ui.theme.WoodButtonBottom
import com.example.ui.theme.WoodButtonTop
import com.example.ui.theme.WoodGoldenText
import com.example.ui.theme.WoodSignboardBorder

/**
 * Polished, high-craft Game Over Dialog with dramatic animated clash effects.
 * Elevates the visual atmosphere with rustic carved timber borders,
 * animated character confrontation medallions, expanding shockwaves,
 * and prominent, tactile rescue/restart controls.
 */
@Composable
fun GameOverDialog(
    violation: ViolationType,
    levelTheme: LevelTheme = LevelTheme.SPRING_VALLEY,
    onUndo: () -> Unit,
    onRestart: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scaleAnim = remember { Animatable(0.65f) }

    LaunchedEffect(Unit) {
        scaleAnim.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    }

    val infiniteTransition = rememberInfiniteTransition(label = "game_over_alarm_anim")

    // Pulsing danger warning aura
    val alarmPulse by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(650, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alarm_pulse"
    )

    // Clash emblem shockwave expansion
    val shockwaveScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(850, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shockwave_scale"
    )
    val shockwaveAlpha by infiniteTransition.animateFloat(
        initialValue = 0.75f,
        targetValue = 0.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(850, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shockwave_alpha"
    )

    // Clash emblem rotational wobble
    val clashWobble by infiniteTransition.animateFloat(
        initialValue = -12f,
        targetValue = 12f,
        animationSpec = infiniteRepeatable(
            animation = tween(220, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "clash_wobble"
    )

    // Predator eager lunge forward
    val predatorLunge by infiniteTransition.animateFloat(
        initialValue = -2f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(
            animation = tween(400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "predator_lunge"
    )

    // Prey nervous shudder tremor
    val preyTremor by infiniteTransition.animateFloat(
        initialValue = -3.5f,
        targetValue = 3.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(90, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "prey_tremor"
    )

    // Button shimmer sweep
    val buttonSheen by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "btn_sheen"
    )

    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(26.dp),
            color = levelTheme.headerBackgroundColors.last().copy(alpha = 0.96f),
            border = BorderStroke(
                2.dp,
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFEF4444).copy(alpha = alarmPulse.coerceIn(0.6f, 1f)),
                        levelTheme.headerAccentColor,
                        Color(0xFFB91C1C)
                    )
                )
            ),
            shadowElevation = 18.dp,
            modifier = modifier
                .fillMaxWidth(0.92f)
                .widthIn(max = 420.dp)
                .scale(scaleAnim.value)
                .testTag("game_over_dialog")
        ) {
            Box(
                modifier = Modifier
                    .background(
                        Brush.verticalGradient(
                            levelTheme.headerBackgroundColors.map { it.copy(alpha = 0.95f) }
                        )
                    )
                    .padding(horizontal = 20.dp, vertical = 22.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 1. Top Danger Badge Pill
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0x33EF4444),
                        border = BorderStroke(1.2.dp, Color(0xFFEF4444)),
                        shadowElevation = 2.dp,
                        modifier = Modifier.graphicsLayer { scaleX = alarmPulse; scaleY = alarmPulse }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "⚠️", fontSize = 11.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "CROSSING HAZARD",
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFFFCA5A5),
                                letterSpacing = 1.2.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 2. Character Confrontation Clash Medallions
                    val (leftImg, rightImg, leftName, rightName) = when (violation) {
                        is ViolationType.DogEatsRabbit -> listOf(
                            R.drawable.img_dog,
                            R.drawable.img_rabbit,
                            "Dog",
                            "Rabbit"
                        )
                        is ViolationType.RabbitEatsCabbage -> listOf(
                            R.drawable.img_rabbit,
                            R.drawable.img_cabbage,
                            "Rabbit",
                            "Cabbage"
                        )
                        is ViolationType.Custom -> listOf(
                            violation.predator?.drawableRes ?: R.drawable.img_dog,
                            violation.prey?.drawableRes ?: R.drawable.img_rabbit,
                            violation.predator?.displayName ?: "Predator",
                            violation.prey?.displayName ?: "Prey"
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Left character medallion (Predator - animated leaning forward)
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.graphicsLayer {
                                translationX = predatorLunge
                                scaleX = 1.04f
                                scaleY = 1.04f
                            }
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(62.dp)
                                    .shadow(8.dp, CircleShape)
                                    .clip(CircleShape)
                                    .background(Color(0xFF141C2E))
                                    .border(2.5.dp, Color(0xFFEF4444), CircleShape)
                            ) {
                                Image(
                                    painter = painterResource(id = leftImg as Int),
                                    contentDescription = leftName as String,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.matchParentSize()
                                )
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0xFF090D16),
                                border = BorderStroke(1.dp, Color(0x66EF4444))
                            ) {
                                Text(
                                    text = (leftName as String).uppercase(),
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFFCA5A5),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        // Center Clash Emblem with Expanding Shockwave
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        ) {
                            // Expanding Shockwave Ring
                            Canvas(modifier = Modifier.size(54.dp)) {
                                drawCircle(
                                    color = Color(0xFFEF4444).copy(alpha = shockwaveAlpha),
                                    radius = (size.minDimension / 2f) * shockwaveScale,
                                    style = Stroke(width = 2.5f)
                                )
                            }

                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(42.dp)
                                    .shadow(6.dp, CircleShape)
                                    .clip(CircleShape)
                                    .background(
                                        Brush.radialGradient(
                                            listOf(Color(0xFFB91C1C), Color(0xFF450A0A))
                                        )
                                    )
                                    .border(2.dp, Color(0xFFEF4444), CircleShape)
                                    .graphicsLayer {
                                        rotationZ = clashWobble
                                        scaleX = 1.08f
                                        scaleY = 1.08f
                                    }
                            ) {
                                Text(text = "💥", fontSize = 20.sp)
                            }
                        }

                        // Right character medallion (Prey - animated shivering tremor)
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.graphicsLayer {
                                rotationZ = preyTremor
                                translationX = -predatorLunge * 0.5f
                            }
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(62.dp)
                                    .shadow(8.dp, CircleShape)
                                    .clip(CircleShape)
                                    .background(Color(0xFF141C2E))
                                    .border(2.5.dp, Color(0xFFEF4444), CircleShape)
                            ) {
                                Image(
                                    painter = painterResource(id = rightImg as Int),
                                    contentDescription = rightName as String,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.matchParentSize()
                                )
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0xFF090D16),
                                border = BorderStroke(1.dp, Color(0x66EF4444))
                            ) {
                                Text(
                                    text = (rightName as String).uppercase(),
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFFCA5A5),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 3. Violation Title
                    Text(
                        text = violation.title,
                        fontWeight = FontWeight.Black,
                        fontSize = 19.sp,
                        color = Color(0xFFF87171),
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.3.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // 4. Description Box (Frosted Glass Inset)
                    WoodInsetBox(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = violation.description,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            lineHeight = 18.sp,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // 5. Riverbank Law Reminder Callout
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0x33F59E0B),
                        border = BorderStroke(1.dp, Color(0xFFF59E0B)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "⚖️", fontSize = 12.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Riverbank Law: The Farmer must be present on the bank to keep predator and prey peaceful!",
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.Normal,
                                color = GoldenBankGlow,
                                lineHeight = 14.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // 6. Action Controls: Balanced side-by-side row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Undo Move Button (Primary Recovery - Thematic 3D Pill with Shimmer)
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = levelTheme.sailButtonColors.last(),
                            border = BorderStroke(1.8.dp, levelTheme.sailButtonBorderColors.first()),
                            shadowElevation = 6.dp,
                            modifier = Modifier
                                .weight(1.1f)
                                .height(48.dp)
                                .clickable(onClick = onUndo)
                                .testTag("game_over_undo")
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        Brush.verticalGradient(levelTheme.sailButtonColors)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                // Animated gleam across undo button
                                Canvas(modifier = Modifier.matchParentSize()) {
                                    val sheenWidth = size.width * 0.35f
                                    val startX = -sheenWidth + (size.width + sheenWidth * 2f) * buttonSheen
                                    drawRect(
                                        brush = Brush.linearGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                Color.White.copy(alpha = 0.20f),
                                                Color.Transparent
                                            ),
                                            start = Offset(startX, 0f),
                                            end = Offset(startX + sheenWidth, size.height)
                                        )
                                    )
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Undo,
                                        contentDescription = "Undo",
                                        tint = Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Undo Move",
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color.White,
                                        fontSize = 13.5.sp
                                    )
                                }
                            }
                        }

                        // Restart Button (Reset - Thematic Timber Pill with Crimson border)
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = levelTheme.actionButtonColors.first().copy(alpha = 0.85f),
                            border = BorderStroke(1.2.dp, Color(0xFFEF4444)),
                            shadowElevation = 2.dp,
                            modifier = Modifier
                                .weight(0.9f)
                                .height(48.dp)
                                .clickable(onClick = onRestart)
                                .testTag("game_over_restart")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Restart",
                                    tint = Color(0xFFFCA5A5),
                                    modifier = Modifier.size(17.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Restart",
                                    color = Color(0xFFFCA5A5),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
