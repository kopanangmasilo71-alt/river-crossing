package com.example.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.Bank
import com.example.model.GameItem
import com.example.model.LevelTheme
import com.example.model.PuzzleScenario
import com.example.ui.theme.GoldenBankGlow
import com.example.ui.theme.RiverDeepBlueDark
import com.example.ui.theme.RiverDeepBlueMid
import com.example.ui.theme.RiverDeepBlueLight
import com.example.ui.theme.RiverWaterCyan
import com.example.ui.theme.RiverEarthBrownDark
import com.example.ui.theme.RiverEarthBrownMid
import com.example.ui.theme.RiverEarthBrownLight
import com.example.ui.theme.RiverTimberBorder
import com.example.ui.theme.RiverForestGreenDark
import com.example.ui.theme.RiverForestGreenMid
import com.example.ui.theme.RiverMeadowGrass
import com.example.ui.theme.RiverGoldGlow
import com.example.ui.theme.RiverTheme
import com.example.ui.theme.WoodButtonBorder
import com.example.ui.theme.WoodButtonBottom
import com.example.ui.theme.WoodButtonTop
import com.example.ui.theme.WoodGoldenText
import com.example.ui.theme.WoodInsetPanel
import com.example.ui.theme.WoodPillBackground
import com.example.ui.theme.WoodSignboardBorder
import com.example.ui.theme.WoodSignboardDark
import com.example.ui.theme.WoodSignboardLight
import com.example.ui.theme.WoodSignboardBg
import com.example.ui.theme.WoodCardBg
import com.example.ui.theme.ClassyGoldBurnished
import com.example.ui.theme.ClassyGoldChampagne
import com.example.ui.theme.ClassyGoldPrimary
import com.example.ui.theme.ClassyGoldRadiant
import com.example.ui.theme.ClassyObsidianDeep
import com.example.ui.theme.ClassyObsidianSurface
import com.example.ui.theme.GlassAccentGold
import com.example.ui.theme.GlassTextPrimary
import com.example.ui.theme.GlassTextMuted
import com.example.ui.theme.MenuQuickActionBgTop
import com.example.ui.theme.MenuQuickActionBgBottom
import com.example.ui.theme.MenuQuickActionBorder
import com.example.ui.theme.MenuStatBarBgTop
import com.example.ui.theme.MenuStatBarBgBottom
import com.example.ui.theme.MenuStatBarBorder
import com.example.ui.theme.MenuBtnGreenTop
import com.example.ui.theme.MenuBtnGreenMid
import com.example.ui.theme.MenuBtnGreenBottom
import com.example.ui.theme.MenuBtnGreenBorder
import com.example.ui.theme.MenuBtnBlueTop
import com.example.ui.theme.MenuBtnBlueMid
import com.example.ui.theme.MenuBtnBlueBottom
import com.example.ui.theme.MenuBtnBlueBorder
import com.example.ui.theme.MenuBtnAmberTop
import com.example.ui.theme.MenuBtnAmberMid
import com.example.ui.theme.MenuBtnAmberBottom
import com.example.ui.theme.MenuBtnAmberBorder
import com.example.ui.theme.MenuBtnPurpleTop
import com.example.ui.theme.MenuBtnPurpleMid
import com.example.ui.theme.MenuBtnPurpleBottom
import com.example.ui.theme.MenuBtnPurpleBorder
import com.example.ui.theme.MenuBtnCyanTop
import com.example.ui.theme.MenuBtnCyanMid
import com.example.ui.theme.MenuBtnCyanBottom
import com.example.ui.theme.MenuBtnCyanBorder
import com.example.ui.theme.MenuBtnRedTop
import com.example.ui.theme.MenuBtnRedMid
import com.example.ui.theme.MenuBtnRedBottom
import com.example.ui.theme.MenuBtnRedBorder

/**
 * High-resolution wooden image assets generated for UI components across all menus.
 */
object WoodAssets {
    val signHeader = R.drawable.img_wood_sign_header_1791812764001
    val btnSail = R.drawable.img_wood_btn_sail_1791812782354
    val btnSmall = R.drawable.img_wood_btn_small_1791812800194
    val pillMenu = R.drawable.img_wood_pill_menu_1791812817980
    val cardSquare = R.drawable.img_wood_card_square_1791812840654
    val woodPanel = cardSquare
}

/**
 * Authentic carved wooden signboard header matching the provided reference image.
 * Features beveled wooden plank texture, level title, dark carved inset panel for items,
 * and moves/timer stats.
 */
@Composable
fun WoodSignboardHeader(
    scenario: PuzzleScenario,
    moveCount: Int,
    elapsedSeconds: Long,
    isMuted: Boolean,
    onBack: () -> Unit,
    onOpenTutorial: () -> Unit,
    onOpenRules: () -> Unit,
    onToggleMute: () -> Unit,
    onOpenModifiers: () -> Unit,
    levelTheme: LevelTheme = LevelTheme.forScenario(scenario),
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "header_ambient_anim")
    val headerSheenPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "header_sheen"
    )
    val clockPulse by infiniteTransition.animateFloat(
        initialValue = 0.92f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "clock_pulse"
    )
    val starGlintAngle by infiniteTransition.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "star_glint"
    )
    val starScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "star_scale"
    )
    val warningWobble by infiniteTransition.animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "warning_wobble"
    )

    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(
            1.8.dp,
            Brush.horizontalGradient(levelTheme.headerBorderColors.map { it.copy(alpha = 0.85f) })
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = modifier
            .fillMaxWidth()
            .testTag("wood_signboard_header")
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Authentic carved wooden signboard texture (solid realistic wood, no semitransparent color overlay)
            Image(
                painter = painterResource(id = WoodAssets.signHeader),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            // Ambient light gleam traveling across the carved wooden signboard
            Canvas(modifier = Modifier.matchParentSize()) {
                val sheenW = size.width * 0.32f
                val currX = -sheenW + (size.width + sheenW * 2f) * headerSheenPhase
                drawRect(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.White.copy(alpha = 0.08f),
                            Color(0xFFFFE082).copy(alpha = 0.12f),
                            Color.Transparent
                        ),
                        start = Offset(currX, 0f),
                        end = Offset(currX + sheenW, size.height)
                    )
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
            // Top Bar: Back Button, Main Title, and Utility Icons
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Back / Level Select Button
                val backInteraction = remember { MutableInteractionSource() }
                val isBackPressed by backInteraction.collectIsPressedAsState()
                val backScale by animateFloatAsState(
                    targetValue = if (isBackPressed) 0.91f else 1.0f,
                    animationSpec = spring(dampingRatio = 0.52f, stiffness = 600f),
                    label = "back_scale"
                )
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = levelTheme.headerSurfaceColor.copy(alpha = 0.50f),
                    border = BorderStroke(1.2.dp, levelTheme.headerAccentColor.copy(alpha = 0.7f)),
                    modifier = Modifier
                        .scale(backScale)
                        .clickable(
                            interactionSource = backInteraction,
                            indication = null,
                            onClick = onBack
                        )
                        .testTag("header_back_button")
                ) {
                    Box(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back to Levels",
                                tint = levelTheme.headerAccentColor,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "LEVELS",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = levelTheme.headerAccentColor
                            )
                        }
                    }
                }

                // Main Title & Biome Theme Badge
                val displayTitle = if (scenario.title.startsWith("Level ")) scenario.title else "Level ${scenario.levelNumber}: ${scenario.title}"
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(1.dp)
                ) {
                    Text(
                        text = displayTitle,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier.testTag("header_level_title")
                    )
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = levelTheme.headerSurfaceColor.copy(alpha = 0.50f),
                        border = BorderStroke(0.8.dp, levelTheme.headerAccentColor.copy(alpha = 0.7f))
                    ) {
                        Text(
                            text = "${levelTheme.iconEmoji} ${levelTheme.name}",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = levelTheme.headerTextColor,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 1.dp)
                        )
                    }
                }

                // Grouped Utility Menu & Quick Audio Toggle with generous spacing
                var isMenuExpanded by remember { mutableStateOf(false) }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val muteInteraction = remember { MutableInteractionSource() }
                    val isMutePressed by muteInteraction.collectIsPressedAsState()
                    val muteScale by animateFloatAsState(
                        targetValue = if (isMutePressed) 0.84f else 1.0f,
                        animationSpec = spring(dampingRatio = 0.5f, stiffness = 650f),
                        label = "mute_scale"
                    )
                    val muteRotation by animateFloatAsState(
                        targetValue = if (isMuted) -15f else 0f,
                        animationSpec = spring(dampingRatio = 0.5f, stiffness = 450f),
                        label = "mute_rotation"
                    )
                    IconButton(
                        onClick = onToggleMute,
                        interactionSource = muteInteraction,
                        modifier = Modifier
                            .size(30.dp)
                            .scale(muteScale)
                            .rotate(muteRotation)
                            .testTag("header_mute_button")
                    ) {
                        Icon(
                            imageVector = if (isMuted) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
                            contentDescription = if (isMuted) "Unmute Sound" else "Mute Sound",
                            tint = if (isMuted) Color(0xFFEF4444) else levelTheme.headerAccentColor,
                            modifier = Modifier.size(19.dp)
                        )
                    }

                    // Options / Utilities Dropdown Menu
                    Box {
                        IconButton(
                            onClick = { isMenuExpanded = true },
                            modifier = Modifier
                                .size(30.dp)
                                .testTag("header_menu_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Game Menu",
                                tint = levelTheme.headerAccentColor,
                                modifier = Modifier.size(19.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = isMenuExpanded,
                            onDismissRequest = { isMenuExpanded = false },
                            modifier = Modifier
                                .background(levelTheme.headerBackgroundColors.first().copy(alpha = 0.90f))
                                .border(1.2.dp, levelTheme.headerAccentColor.copy(alpha = 0.7f), RoundedCornerShape(8.dp))
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = "📖 Tutorial & Guide",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                },
                                onClick = {
                                    isMenuExpanded = false
                                    onOpenTutorial()
                                },
                                modifier = Modifier.testTag("header_tutorial_button")
                            )
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = "📜 Puzzle Rules & Hazards",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                },
                                onClick = {
                                    isMenuExpanded = false
                                    onOpenRules()
                                },
                                modifier = Modifier.testTag("header_rules_button")
                            )
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = "⚙️ Game Modifiers",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                },
                                onClick = {
                                    isMenuExpanded = false
                                    onOpenModifiers()
                                },
                                modifier = Modifier.testTag("header_modifiers_button")
                            )
                        }
                    }
                }
            }

            // Bottom Sub-Row: Moves Counter with Dynamic Tension, Strategic Progress Bar & Live Timer
            val optimalMoves = scenario.optimalMoves
            val moveTensionColor = when {
                moveCount == 0 -> WoodGoldenText
                moveCount < optimalMoves -> Color(0xFF86EFAC) // Emerald: on track for star!
                moveCount == optimalMoves -> Color(0xFFFDE047) // Bright Gold: exact optimal reached!
                moveCount <= optimalMoves + 2 -> Color(0xFFFB923C) // Warm Orange: exceeding target
                else -> Color(0xFFEF4444) // Urgent Crimson: exceeded target
            }
            val targetProgressRatio = if (optimalMoves > 0) {
                (moveCount.toFloat() / optimalMoves).coerceIn(0f, 1f)
            } else {
                0f
            }
            val progressRatio by animateFloatAsState(
                targetValue = targetProgressRatio,
                animationSpec = spring(dampingRatio = 0.65f, stiffness = 420f),
                label = "progress_ratio_anim"
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    AnimatedContent(
                        targetState = moveCount,
                        transitionSpec = {
                            (slideInVertically(spring(dampingRatio = 0.6f, stiffness = 500f)) { -it } + fadeIn())
                                .togetherWith(slideOutVertically { it } + fadeOut())
                        },
                        label = "moves_counter_anim"
                    ) { count ->
                        Text(
                            text = "Moves: $count",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = moveTensionColor
                        )
                    }

                    if (moveCount > optimalMoves) {
                        Text(
                            text = "⚠️",
                            fontSize = 10.sp,
                            modifier = Modifier.rotate(warningWobble)
                        )
                    } else if (moveCount > 0 && moveCount <= optimalMoves) {
                        Text(
                            text = "⭐",
                            fontSize = 10.sp,
                            modifier = Modifier
                                .rotate(starGlintAngle)
                                .scale(starScale)
                        )
                    }
                }

                // Subtle Strategic Progress Indicator Pill with smooth spring progression
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(46.dp)
                            .height(5.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(Color(0x88000000))
                            .border(0.6.dp, levelTheme.headerAccentColor.copy(alpha = 0.45f), RoundedCornerShape(3.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(progressRatio)
                                .clip(RoundedCornerShape(3.dp))
                                .background(moveTensionColor)
                        )
                    }

                    Text(
                        text = "Goal: ${scenario.optimalMoves} moves",
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (moveCount > optimalMoves) Color(0xFFFCA5A5) else levelTheme.headerTextColor.copy(alpha = 0.9f)
                    )
                }

                // Live Pulsing Timer
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Text(
                        text = "⏱️",
                        fontSize = 10.5.sp,
                        modifier = Modifier.scale(clockPulse)
                    )
                    Text(
                        text = formatTime(elapsedSeconds),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = levelTheme.headerTimerColor
                    )
                }
            }
        }
    }
}
}

/**
 * Carved wood pill badge showing currently loaded cargo items.
 */
@Composable
fun WoodCargoBadge(
    passengers: List<GameItem>,
    boatCapacity: Int,
    levelTheme: LevelTheme = LevelTheme.SPRING_VALLEY,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Color.Transparent,
        border = BorderStroke(1.5.dp, levelTheme.headerBorderColors.first().copy(alpha = 0.75f)),
        shadowElevation = 4.dp,
        modifier = modifier.testTag("wood_cargo_badge")
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = WoodAssets.pillMenu),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            Box(
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
            val cargoText = if (passengers.isEmpty()) {
                "Cargo: Empty (Farmer Alone)"
            } else {
                "Cargo: ${passengers.joinToString(", ") { it.displayName }} (${passengers.size}/$boatCapacity)"
            }

            Text(
                text = cargoText,
                fontSize = 13.5.sp,
                fontWeight = FontWeight.Bold,
                color = levelTheme.headerTextColor,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color(0x88000000),
                        offset = Offset(1f, 1f),
                        blurRadius = 2f
                    )
                )
            )
        }
    }
}
}

/**
 * Large embossed wooden "SET SAIL" button matching the reference.
 * Enhanced with:
 * - Tactile spring press feedback & depth drop
 * - Welcoming golden breathing glow when ready to cross
 * - Rowing wave rocking motion during transit
 * - Animated shimmering light sheen
 */
@Composable
fun WoodSetSailButton(
    isRowing: Boolean,
    enabled: Boolean,
    subtext: String? = null,
    isConflict: Boolean = false,
    levelTheme: LevelTheme = LevelTheme.SPRING_VALLEY,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val infiniteTransition = rememberInfiniteTransition(label = "wood_sail_btn_anim")

    // Gentle breathing scale when button is ready to sail
    val breathingPulse by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = if (enabled && !isRowing && !isConflict) 1.025f else 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sail_breathing"
    )

    // Gentle wave rock when rowing
    val rowingRockAngle by infiniteTransition.animateFloat(
        initialValue = if (isRowing) -2.0f else 0f,
        targetValue = if (isRowing) 2.0f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(420, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sailing_rock"
    )

    // Shimmer gleam traveling across the button when ready to cross
    val shimmerPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "sail_shimmer"
    )

    // Concentric nautical water wave pulses behind the button
    val wakePulse by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.09f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "wake_pulse"
    )
    val wakeAlpha by infiniteTransition.animateFloat(
        initialValue = 0.50f,
        targetValue = 0.12f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "wake_alpha"
    )

    // Oar dipping bobbing animation for the sailing emoji
    val oarBob by infiniteTransition.animateFloat(
        initialValue = -3.5f,
        targetValue = 3.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(380, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "oar_bob"
    )

    // Tactile press response: spring dips down, then bounces back
    val buttonScale by animateFloatAsState(
        targetValue = if (isPressed) 0.93f else breathingPulse,
        animationSpec = spring(dampingRatio = 0.52f, stiffness = 550f),
        label = "sail_btn_scale"
    )

    val shadowElevation by animateDpAsState(
        targetValue = if (isPressed) 2.dp else if (enabled) 10.dp else 2.dp,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 500f),
        label = "sail_shadow"
    )

    val buttonBg = when {
        isConflict -> listOf(Color(0xFF7F1D1D), Color(0xFF450A0A))
        !enabled -> listOf(Color(0xDD2A1406), Color(0xDD180A03))
        isPressed -> listOf(
            levelTheme.sailButtonColors.last(),
            levelTheme.sailButtonColors.last()
        )
        else -> levelTheme.sailButtonColors
    }
    val borderBrush = when {
        isConflict -> Brush.verticalGradient(listOf(Color(0xFFEF4444), Color(0xFF991B1B)))
        !enabled -> Brush.verticalGradient(listOf(Color(0xFF5A2C0D), Color(0xFF381A07)))
        isPressed -> Brush.verticalGradient(
            listOf(
                levelTheme.sailButtonBorderColors.getOrElse(1) { Color(0xFF86EFAC) },
                levelTheme.sailButtonColors.first()
            )
        )
        else -> Brush.verticalGradient(levelTheme.sailButtonBorderColors)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        // Subtle concentric aquatic wake ripples radiating behind the button adapting to levelTheme
        if (enabled && !isRowing && !isConflict) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .graphicsLayer {
                        scaleX = wakePulse
                        scaleY = wakePulse
                        alpha = wakeAlpha
                    }
                    .border(1.8.dp, levelTheme.sailButtonGlow.copy(alpha = 0.85f), RoundedCornerShape(20.dp))
            )
        }

        Surface(
            shape = RoundedCornerShape(18.dp),
            color = Color.Transparent,
            border = BorderStroke(if (isPressed || isConflict) 3.2.dp else 2.6.dp, borderBrush),
            shadowElevation = shadowElevation,
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    scaleX = buttonScale
                    scaleY = buttonScale
                    rotationZ = rowingRockAngle
                }
                .shadow(
                    elevation = shadowElevation,
                    shape = RoundedCornerShape(18.dp),
                    spotColor = if (enabled && !isConflict) levelTheme.sailButtonGlow else Color(0x33000000)
                )
                .clip(RoundedCornerShape(18.dp))
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled && !isRowing,
                    onClick = onClick
                )
                .testTag("set_sail_button")
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Embossed wooden button texture (solid realistic wood)
                Image(
                    painter = painterResource(id = WoodAssets.btnSail),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .matchParentSize()
                        .alpha(if (enabled) 1f else 0.45f)
                )
                // Dynamic shimmering sunlight light gleam across button when ready to cross
                if (enabled && !isRowing && !isConflict) {
                    Canvas(modifier = Modifier.matchParentSize()) {
                        val shimmerW = size.width * 0.35f
                        val currX = -shimmerW + (size.width + shimmerW * 2f) * shimmerPhase
                        drawRect(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.White.copy(alpha = 0.28f),
                                    Color(0xFFFFE082).copy(alpha = 0.22f),
                                    Color.Transparent
                                ),
                                start = Offset(currX, 0f),
                                end = Offset(currX + shimmerW, size.height)
                            )
                        )
                    }
                }

                // Dynamic animated river wake and floating bubbles during sailing
                if (isRowing) {
                    Canvas(modifier = Modifier.matchParentSize()) {
                        val w = size.width
                        val h = size.height
                        val streamPhase = (shimmerPhase * 3.5f) % 1.0f
                        for (line in 0..3) {
                            val ly = h * (0.35f + line * 0.16f)
                            val startX = -w * 0.2f + (w * 1.4f) * ((streamPhase + line * 0.25f) % 1.0f)
                            drawLine(
                                color = Color(0x55BAE6FD),
                                start = Offset(startX, ly),
                                end = Offset(startX + 36f, ly),
                                strokeWidth = 2.0f,
                                cap = StrokeCap.Round
                            )
                        }
                        for (b in 0..4) {
                            val bx = w * (0.15f + b * 0.18f)
                            val by = h - (h * ((streamPhase * 1.3f + b * 0.22f) % 1.0f))
                            drawCircle(
                                color = Color(0x44E0F2FE),
                                radius = 2.4f + (b % 2),
                                center = Offset(bx, by)
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "⛵ ",
                            fontSize = 20.sp,
                            modifier = Modifier.offset(y = if (isRowing) oarBob.dp else 0.dp)
                        )
                        Text(
                            text = if (isRowing) "SAILING ACROSS..." else "S E T   S A I L",
                            fontSize = if (isRowing) 17.sp else 19.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = if (isRowing) 1.2.sp else 2.5.sp,
                            color = if (isConflict) Color(0xFFFEE2E2) else if (enabled) Color.White else Color(0xFF7DD3FC).copy(alpha = 0.6f),
                            style = TextStyle(
                                shadow = Shadow(
                                    color = if (enabled && !isConflict) Color(0x66000000) else Color(0xAA000000),
                                    offset = Offset(0.5f, 1f),
                                    blurRadius = 2f
                                )
                            )
                        )
                        if (isRowing) {
                            Text(
                                text = " 🌊",
                                fontSize = 18.sp,
                                modifier = Modifier.offset(y = (-oarBob).dp)
                            )
                        }
                    }
                    if (subtext != null && !isRowing) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = subtext,
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.0.sp,
                            color = if (isConflict) Color(0xFFFECDD3) else if (enabled) Color(0xFFFEF08A) else Color(0xFF7DD3FC).copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Sleek Frosted Glass Cargo Pill Floating over the Valley Sky.
 * Matches the reference mockup:
 * [ 👨‍🌾 FARMER Present ]  [ 🐶 DOG Loaded ]  [ 🥬 CABBAGE Loaded ]
 */
@Composable
fun FloatingSkyCargoBadge(
    farmerBank: Bank,
    passengers: List<GameItem>,
    scenario: PuzzleScenario,
    levelTheme: LevelTheme = LevelTheme.SPRING_VALLEY,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = levelTheme.headerBackgroundColors.first().copy(alpha = 0.80f),
        border = BorderStroke(1.5.dp, levelTheme.headerBorderColors.first().copy(alpha = 0.7f)),
        shadowElevation = 6.dp,
        modifier = modifier.testTag("floating_sky_cargo_badge")
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Farmer Avatar + Status
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_farmer),
                    contentDescription = "Farmer",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, levelTheme.farmerTokenBorderColor, CircleShape)
                )
                Text(
                    text = "FARMER",
                    fontSize = 8.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = levelTheme.farmerNameColor
                )
                Text(
                    text = "Present",
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    color = levelTheme.farmerStatusColor
                )
            }

            // Scenario Items
            scenario.items.forEach { item ->
                val isLoaded = item in passengers
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(id = item.drawableRes),
                            contentDescription = item.displayName,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .border(
                                    1.5.dp,
                                    if (isLoaded) levelTheme.headerAccentColor else levelTheme.objectTokenBorderColor.copy(alpha = 0.40f),
                                    CircleShape
                                )
                        )
                    }
                    Text(
                        text = item.displayName.uppercase(),
                        fontSize = 8.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = levelTheme.objectNameColor
                    )
                    Text(
                        text = if (isLoaded) "Loaded" else "Waiting",
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isLoaded) levelTheme.headerAccentColor else Color(0xFF94A3B8)
                    )
                }
            }
        }
    }
}

/**
 * Floating Bank Indicators in the top corners:
 * ◀ LEFT BANK    and    RIGHT BANK ▶
 */
@Composable
fun FloatingBankPill(
    bank: Bank,
    isDocked: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (isDocked) Color(0xF04A260E) else Color(0x992E1507),
        border = BorderStroke(1.2.dp, if (isDocked) GoldenBankGlow else Color(0x66B45309)),
        shadowElevation = if (isDocked) 4.dp else 1.dp,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (bank == Bank.LEFT) "▶ LEFT BANK" else "▶ RIGHT BANK",
                fontSize = 9.5.sp,
                fontWeight = FontWeight.ExtraBold,
                color = if (isDocked) GoldenBankGlow else Color(0xFFFEF3C7),
                letterSpacing = 0.5.sp
            )
        }
    }
}

/**
 * Action button for secondary actions (RESET, LEVELS, UNDO, HINT, TUTORIAL).
 * Styled in rich vibrant deep sapphire with cyan borders and clear white/gold icons.
 */
@Composable
fun WoodActionButton(
    text: String,
    icon: ImageVector? = null,
    enabled: Boolean = true,
    testTag: String,
    levelTheme: LevelTheme = LevelTheme.SPRING_VALLEY,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val buttonScale by animateFloatAsState(
        targetValue = if (isPressed) 0.90f else 1.0f,
        animationSpec = spring(dampingRatio = 0.52f, stiffness = 600f),
        label = "btn_scale_$text"
    )

    val shadowElevation by animateDpAsState(
        targetValue = if (isPressed) 1.dp else if (enabled) 4.dp else 1.dp,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 500f),
        label = "btn_shadow_$text"
    )

    // Contextual icon animations
    val isReset = text.contains("RESET", ignoreCase = true)
    val isUndo = text.contains("UNDO", ignoreCase = true)
    val isHint = text.contains("HINT", ignoreCase = true)

    val iconRotation by animateFloatAsState(
        targetValue = if (isReset && isPressed) 360f else 0f,
        animationSpec = spring(dampingRatio = 0.45f, stiffness = 520f),
        label = "icon_rotation"
    )

    val undoNudge by animateFloatAsState(
        targetValue = if (isUndo && isPressed) -5f else 0f,
        animationSpec = spring(dampingRatio = 0.42f, stiffness = 550f),
        label = "undo_nudge"
    )

    val hintGlowScale = if (isHint && enabled) {
        val infiniteTransition = rememberInfiniteTransition(label = "btn_pulse_$text")
        val anim by infiniteTransition.animateFloat(
            initialValue = 0.95f,
            targetValue = 1.16f,
            animationSpec = infiniteRepeatable(
                animation = tween(750, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "hint_glow"
        )
        anim
    } else {
        1.0f
    }

    val primaryBg = levelTheme.actionButtonColors.first()
    val secondaryBg = if (levelTheme.actionButtonColors.size > 1) levelTheme.actionButtonColors[1] else primaryBg

    Surface(
        shape = RoundedCornerShape(11.dp),
        color = Color.Transparent,
        border = BorderStroke(
            1.4.dp,
            if (isPressed) GoldenBankGlow else if (isHint) Color(0xFFFBBF24) else if (enabled) levelTheme.actionButtonBorder.copy(alpha = 0.85f) else levelTheme.actionButtonBorder.copy(alpha = 0.25f)
        ),
        shadowElevation = if (isPressed) 1.dp else if (enabled) 2.dp else 1.dp,
        modifier = modifier
            .graphicsLayer {
                scaleX = buttonScale
                scaleY = buttonScale
            }
            .clip(RoundedCornerShape(11.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick
            )
            .testTag(testTag)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = WoodAssets.btnSmall),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .alpha(if (enabled) 1f else 0.45f)
            )
            Row(
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 7.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = text,
                    tint = if (isHint) Color(0xFFFDE047) else if (enabled) levelTheme.actionButtonTextColor else Color(0xFF94A3B8),
                    modifier = Modifier
                        .size(13.dp)
                        .rotate(iconRotation)
                        .offset(x = undoNudge.dp)
                        .scale(if (isHint) hintGlowScale else 1.0f)
                )
                Spacer(modifier = Modifier.width(3.dp))
            }
            Text(
                text = text,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                softWrap = false,
                color = if (isHint) Color(0xFFFEF08A) else if (enabled) levelTheme.actionButtonTextColor else Color(0xFF94A3B8)
            )
        }
    }
}
}

/**
 * Full screen container with a luminous vibrant background.
 */
@Composable
fun WoodScreenContainer(
    modifier: Modifier = Modifier,
    backgroundImageRes: Int? = null,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Full bleed background image matching Main Menu sunny valley
        Image(
            painter = painterResource(id = backgroundImageRes ?: R.drawable.img_valley_bg_1790060103557),
            contentDescription = "Screen Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        // Subtle gradient scrim at bottom to keep text ultra-crisp over landscape while preserving vibrant sunny valley
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0x10000000),
                            Color(0x22000000),
                            Color(0x55000000)
                        )
                    )
                )
        )
        content()
    }
}

/**
 * Circular Quick Action Button matching the warm wooden adventure theme
 * (Carved timber grain, warm brass/gold border, interactive spring press, and crisp warm cream icon)
 */
@Composable
fun WoodQuickActionButton(
    icon: ImageVector? = null,
    drawableRes: Int? = null,
    contentDescription: String,
    testTag: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.88f else 1.0f,
        animationSpec = spring(dampingRatio = 0.52f, stiffness = 600f),
        label = "wood_quick_act_scale"
    )

    Surface(
        shape = CircleShape,
        color = Color.Transparent,
        border = BorderStroke(1.5.dp, GoldenBankGlow),
        shadowElevation = 4.dp,
        modifier = modifier
            .size(36.dp)
            .scale(scale)
            .clip(CircleShape)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .testTag(testTag)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = WoodAssets.btnSmall),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = contentDescription,
                    tint = Color(0xFFFFFBEB),
                    modifier = Modifier.size(19.dp)
                )
            } else if (drawableRes != null) {
                Icon(
                    painter = painterResource(id = drawableRes),
                    contentDescription = contentDescription,
                    tint = Color(0xFFFFFBEB),
                    modifier = Modifier.size(19.dp)
                )
            }
        }
    }
}

/**
 * Top App Bar styled with warm cedar/mahogany signboard and golden amber border trim.
 * Features carved wooden texture, warm cream typography, and brass quick action buttons.
 */
@Composable
fun WoodTopAppBar(
    title: String,
    subtitle: String? = null,
    onBack: (() -> Unit)? = null,
    actions: @Composable () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
        color = Color.Transparent,
        border = BorderStroke(
            1.8.dp,
            Brush.verticalGradient(
                listOf(GoldenBankGlow, Color(0xFFB45309), Color(0xFF4A260E))
            )
        ),
        shadowElevation = 8.dp,
        modifier = modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = WoodAssets.signHeader),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            // Subtle warm golden amber bevel highlight at top edge
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color.Transparent, Color(0x66FDE68A), Color.Transparent)
                        )
                    )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    if (onBack != null) {
                        WoodQuickActionButton(
                            icon = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            testTag = "wood_topbar_back_button",
                            onClick = onBack
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    }

                    Column {
                        Text(
                            text = title,
                            fontSize = 17.5.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFFFFFBEB),
                            style = TextStyle(
                                shadow = Shadow(
                                    color = Color(0xCC000000),
                                    offset = Offset(1f, 1.5f),
                                    blurRadius = 3f
                                )
                            )
                        )
                        if (subtitle != null) {
                            Text(
                                text = subtitle,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFFFEF3C7)
                            )
                        }
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    actions()
                }
            }
        }
    }
}

/**
 * Cohesive rustic wooden card with rich warm cedar/mahogany tones,
 * carved wooden borders, subtle bevel sheen, and authentic wood texture.
 */
@Composable
fun WoodCard(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(18.dp),
    gradientColors: List<Color>? = null,
    borderColor: Color? = null,
    content: @Composable () -> Unit
) {
    val bgColors = gradientColors ?: listOf(
        Color(0xF042220E),
        Color(0xF0281305)
    )
    val borderCol = borderColor ?: Color(0xFFB45309)

    Card(
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(
            1.6.dp,
            Brush.verticalGradient(
                listOf(borderCol.copy(alpha = 0.95f), borderCol.copy(alpha = 0.65f))
            )
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        modifier = modifier.shadow(5.dp, shape)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = WoodAssets.cardSquare),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            // Subtle warm golden amber grain highlight at top
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color.Transparent, Color(0x33FDE68A), Color.Transparent)
                        )
                    )
            )
            content()
        }
    }
}

/**
 * Cohesive wooden section header matching the rustic adventure signpost aesthetic.
 */
@Composable
fun VibrantSectionHeader(
    title: String,
    icon: ImageVector? = null,
    iconDrawableRes: Int? = null,
    accentColor: Color = GoldenBankGlow,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(vertical = 4.dp, horizontal = 2.dp)
    ) {
        Surface(
            shape = CircleShape,
            color = Color(0xDD2C1406),
            border = BorderStroke(1.2.dp, accentColor),
            modifier = Modifier.size(24.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                if (iconDrawableRes != null) {
                    Icon(
                        painter = painterResource(id = iconDrawableRes),
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(13.dp)
                    )
                } else if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(13.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title.uppercase(),
            fontSize = 12.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.2.sp,
            color = accentColor,
            style = TextStyle(
                shadow = Shadow(
                    color = Color(0xCC000000),
                    offset = Offset(1f, 1f),
                    blurRadius = 2f
                )
            )
        )
    }
}

/**
 * Carved mahogany inset panel for grouped stats, text descriptions, or inputs.
 * Features deep warm mahogany inset with subtle carved timber border.
 */
@Composable
fun WoodInsetBox(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    content: @Composable () -> Unit
) {
    Surface(
        shape = shape,
        color = Color.Transparent,
        border = BorderStroke(1.2.dp, Color(0xFFB45309)),
        modifier = modifier
    ) {
        Box {
            Image(
                painter = painterResource(id = WoodAssets.cardSquare),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            content()
        }
    }
}

/**
 * Vibrant button for menus and large actions with carved wooden textures.
 */
@Composable
fun WoodMenuButton(
    title: String,
    subtitle: String,
    icon: ImageVector,
    testTag: String,
    onClick: () -> Unit,
    accentGlow: Boolean = false,
    gradientColors: List<Color>? = null,
    borderColor: Color? = null,
    modifier: Modifier = Modifier
) {
    val bgColors = gradientColors ?: if (accentGlow) {
        listOf(Color(0xF048260E), Color(0xF02C1406))
    } else {
        listOf(Color(0xF03B1E0C), Color(0xF0220F05))
    }
    val borderCol = borderColor ?: if (accentGlow) GoldenBankGlow else Color(0xFFB45309)

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.Transparent,
        border = BorderStroke(1.8.dp, borderCol),
        shadowElevation = if (accentGlow) 5.dp else 3.dp,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .testTag(testTag)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = WoodAssets.pillMenu),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color(0x35000000),
                    border = BorderStroke(1.2.dp, GoldenBankGlow.copy(alpha = 0.7f)),
                    modifier = Modifier.size(44.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = Color(0xFFFFFBEB),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFFFFFBEB),
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color(0x99000000),
                                offset = Offset(1f, 1f),
                                blurRadius = 2f
                            )
                        )
                    )
                    Text(
                        text = subtitle,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFFFEF3C7),
                        maxLines = 1
                    )
                }
            }
        }
    }
}

/**
 * Filter pill / chip for level selections and category tabs with warm wooden styling.
 */
@Composable
fun WoodFilterPill(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    activeGradient: List<Color>? = null,
    activeBorder: Color? = null,
    modifier: Modifier = Modifier
) {
    val bgGradient = if (selected) {
        activeGradient ?: listOf(Color(0xFFB45309), Color(0xFF78350F))
    } else {
        listOf(Color(0xE6321607), Color(0xE61F0C03))
    }
    val borderCol = if (selected) {
        activeBorder ?: Color(0xFFFDE68A)
    } else {
        Color(0x66A06634)
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.Transparent,
        border = BorderStroke(1.4.dp, borderCol),
        shadowElevation = if (selected) 4.dp else 1.dp,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = WoodAssets.btnSmall),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            Box(
                modifier = Modifier.padding(horizontal = 13.dp, vertical = 7.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    fontSize = 11.5.sp,
                    fontWeight = if (selected) FontWeight.ExtraBold else FontWeight.Bold,
                    color = if (selected) Color(0xFFFFFBEB) else Color(0xFFFEF3C7)
                )
            }
        }
    }
}

