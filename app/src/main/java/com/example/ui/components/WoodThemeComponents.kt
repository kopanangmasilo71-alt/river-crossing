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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
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
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = RiverDeepBlueDark),
        border = BorderStroke(
            1.8.dp,
            Brush.horizontalGradient(
                listOf(RiverTimberBorder, RiverWaterCyan, RiverTimberBorder)
            )
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(18.dp))
            .testTag("wood_signboard_header")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(RiverDeepBlueMid, RiverDeepBlueDark)
                    )
                )
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
                    color = WoodInsetPanel,
                    border = BorderStroke(1.2.dp, Color(0xFF38BDF8).copy(alpha = 0.6f)),
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
                                tint = Color(0xFFE0F2FE),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "LEVELS",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE0F2FE)
                            )
                        }
                    }
                }

                // Main Title & Biome Theme Badge
                val displayTitle = if (scenario.title.startsWith("Level ")) scenario.title else "Level ${scenario.levelNumber}: ${scenario.title}"
                val theme = LevelTheme.forScenario(scenario)
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
                        color = Color(0x35000000),
                        border = BorderStroke(0.8.dp, Color(0xFF38BDF8).copy(alpha = 0.7f))
                    ) {
                        Text(
                            text = "${theme.iconEmoji} ${theme.name}",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFBAE6FD),
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
                            tint = if (isMuted) Color(0xFFEF4444) else Color(0xFFE0F2FE),
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
                                tint = Color(0xFFE0F2FE),
                                modifier = Modifier.size(19.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = isMenuExpanded,
                            onDismissRequest = { isMenuExpanded = false },
                            modifier = Modifier
                                .background(Color(0xFF072449))
                                .border(1.2.dp, Color(0xFF38BDF8).copy(alpha = 0.7f), RoundedCornerShape(8.dp))
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
            val progressRatio = if (optimalMoves > 0) {
                (moveCount.toFloat() / optimalMoves).coerceIn(0f, 1f)
            } else {
                0f
            }

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
                            fontSize = 10.sp
                        )
                    } else if (moveCount > 0 && moveCount <= optimalMoves) {
                        Text(
                            text = "⭐",
                            fontSize = 10.sp
                        )
                    }
                }

                // Subtle Strategic Progress Indicator Pill
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(46.dp)
                            .height(5.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(Color(0x88080C14))
                            .border(0.6.dp, ClassyGoldPrimary.copy(alpha = 0.35f), RoundedCornerShape(3.dp))
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
                        color = if (moveCount > optimalMoves) Color(0xFFFCA5A5) else Color(0xFFE5D5C5)
                    )
                }

                Text(
                    text = formatTime(elapsedSeconds),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFBAE6FD)
                )
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
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = WoodPillBackground,
        border = BorderStroke(1.5.dp, WoodButtonBorder),
        shadowElevation = 4.dp,
        modifier = modifier.testTag("wood_cargo_badge")
    ) {
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
                color = WoodGoldenText,
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
        initialValue = if (isRowing) -1.8f else 0f,
        targetValue = if (isRowing) 1.8f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(450, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sailing_rock"
    )

    // Shimmer gleam traveling across the button when ready to cross
    val shimmerPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "sail_shimmer"
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
        !enabled -> listOf(Color(0xE60A2B4E), Color(0xCC061A30))
        isPressed -> listOf(Color(0xFF15803D), Color(0xFF14532D))
        else -> listOf(
            Color(0xFF4ADE80), // Forest Meadow Green top
            Color(0xFF22C55E), // Lush Emerald core
            Color(0xFF16A34A), // Rich Forest Green
            Color(0xFF15803D)  // Deep Pine Green bevel
        )
    }
    val borderBrush = when {
        isConflict -> Brush.verticalGradient(listOf(Color(0xFFEF4444), Color(0xFF991B1B)))
        !enabled -> Brush.verticalGradient(listOf(Color(0xFF1E4976), Color(0xFF0F2B48)))
        isPressed -> Brush.verticalGradient(listOf(Color(0xFF86EFAC), Color(0xFF22C55E)))
        else -> Brush.verticalGradient(
            listOf(
                Color(0xFFFEF08A), // Sunlit Gold highlight
                Color(0xFFFACC15), // 24k Gold
                RiverTimberBorder, // Earthy timber bevel
                RiverEarthBrownMid // Deep carved wood
            )
        )
    }

    Surface(
        shape = RoundedCornerShape(18.dp),
        color = if (isConflict) Color(0xFF7F1D1D) else if (enabled) RiverForestGreenMid else Color(0xFF0A2B4E),
        border = BorderStroke(if (isPressed || isConflict) 3.2.dp else 2.6.dp, borderBrush),
        shadowElevation = shadowElevation,
        modifier = modifier
            .graphicsLayer {
                scaleX = buttonScale
                scaleY = buttonScale
                rotationZ = rowingRockAngle
            }
            .shadow(
                elevation = shadowElevation,
                shape = RoundedCornerShape(18.dp),
                spotColor = if (enabled && !isConflict) Color(0x9922C55E) else Color(0x33000000)
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
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(colors = buttonBg))
        ) {
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
                    if (isRowing) {
                        Text(
                            text = "⛵ ",
                            fontSize = 20.sp
                        )
                    }
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
                            fontSize = 18.sp
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
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = Color(0xDD0A2A54),
        border = BorderStroke(1.5.dp, Color(0xFF38BDF8).copy(alpha = 0.7f)),
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
                        .border(1.5.dp, GoldenBankGlow, CircleShape)
                )
                Text(
                    text = "FARMER",
                    fontSize = 8.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Present",
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4ADE80)
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
                                    if (isLoaded) GoldenBankGlow else Color(0x4038BDF8),
                                    CircleShape
                                )
                        )
                    }
                    Text(
                        text = item.displayName.uppercase(),
                        fontSize = 8.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = if (isLoaded) "Loaded" else "Waiting",
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isLoaded) GoldenBankGlow else Color(0xFF94A3B8)
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
        color = if (isDocked) Color(0xF00E3D79) else Color(0x990A2342),
        border = BorderStroke(1.2.dp, if (isDocked) Color(0xFF38BDF8) else Color(0x4038BDF8)),
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
                color = if (isDocked) Color.White else Color(0xFF94A3B8),
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
        targetValue = if (isReset && isPressed) 180f else 0f,
        animationSpec = spring(dampingRatio = 0.5f, stiffness = 450f),
        label = "icon_rotation"
    )

    val undoNudge by animateFloatAsState(
        targetValue = if (isUndo && isPressed) -3f else 0f,
        animationSpec = spring(dampingRatio = 0.45f, stiffness = 500f),
        label = "undo_nudge"
    )

    val hintGlowScale = if (isHint && enabled) {
        val infiniteTransition = rememberInfiniteTransition(label = "btn_pulse_$text")
        val anim by infiniteTransition.animateFloat(
            initialValue = 1.0f,
            targetValue = 1.14f,
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

    Surface(
        shape = RoundedCornerShape(11.dp),
        color = if (isPressed) Color(0xFF0F3666) else if (enabled) Color(0xEA09244B) else Color(0x6609244B),
        border = BorderStroke(
            1.4.dp,
            if (isPressed) GoldenBankGlow else if (isHint) Color(0xFFFBBF24) else if (enabled) Color(0xFF38BDF8).copy(alpha = 0.8f) else Color(0x3338BDF8)
        ),
        shadowElevation = if (isPressed) 1.dp else if (enabled) 3.dp else 1.dp,
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
        Row(
            modifier = Modifier
                .background(
                    if (enabled) {
                        Brush.verticalGradient(
                            if (isPressed) {
                                listOf(Color(0xFF0D3768), Color(0xFF082040))
                            } else if (isHint) {
                                listOf(Color(0xFF78350F), Color(0xFF451A03))
                            } else {
                                listOf(Color(0xF00D3C77), Color(0xF007234A))
                            }
                        )
                    } else {
                        Brush.verticalGradient(
                            colors = listOf(Color(0x66082246), Color(0x4405162E))
                        )
                    }
                )
                .padding(horizontal = 6.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = text,
                    tint = if (isHint) Color(0xFFFDE047) else if (enabled) Color(0xFFBAE6FD) else Color(0xFF64748B),
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
                color = if (isHint) Color(0xFFFEF08A) else if (enabled) Color.White else Color(0xFF64748B)
            )
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
        if (backgroundImageRes != null) {
            Image(
                painter = painterResource(id = backgroundImageRes),
                contentDescription = "Screen Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // Vibrant scrim overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0x2A000000),
                                Color(0x40000000),
                                Color(0x70000000)
                            )
                        )
                    )
            )
        } else {
            // Default to rich valley sky background drawable matching Main Menu
            Image(
                painter = painterResource(id = R.drawable.img_valley_bg_1790060103557),
                contentDescription = "Screen Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0x33000000),
                                Color(0x4D072147),
                                Color(0x8004142B)
                            )
                        )
                    )
            )
        }
        content()
    }
}

/**
 * Top App Bar with vibrant sapphire and cyan border.
 */
@Composable
fun WoodTopAppBar(
    title: String,
    subtitle: String? = null,
    onBack: (() -> Unit)? = null,
    actions: @Composable () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
        colors = CardDefaults.cardColors(containerColor = RiverDeepBlueDark),
        border = BorderStroke(
            1.8.dp,
            Brush.horizontalGradient(
                listOf(RiverTimberBorder, RiverWaterCyan, RiverTimberBorder)
            )
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(RiverDeepBlueMid, RiverDeepBlueDark)
                    )
                )
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                if (onBack != null) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = WoodInsetPanel,
                        border = BorderStroke(1.2.dp, Color(0xFF38BDF8).copy(alpha = 0.6f)),
                        modifier = Modifier
                            .clickable(onClick = onBack)
                            .testTag("wood_topbar_back_button")
                    ) {
                        Box(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back",
                                    tint = Color(0xFFE0F2FE),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }

                Column {
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    if (subtitle != null) {
                        Text(
                            text = subtitle,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFBAE6FD)
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                actions()
            }
        }
    }
}

/**
 * Vibrant card with sapphire blue backdrop and electric cyan border.
 */
@Composable
fun WoodCard(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
    content: @Composable () -> Unit
) {
    Card(
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = RiverDeepBlueDark),
        border = BorderStroke(1.5.dp, Brush.horizontalGradient(listOf(RiverTimberBorder, RiverWaterCyan, RiverTimberBorder))),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            RiverDeepBlueMid,
                            RiverDeepBlueDark
                        )
                    )
                )
        ) {
            content()
        }
    }
}

/**
 * Vibrant inset panel for content groupings, stats, or text descriptions.
 */
@Composable
fun WoodInsetBox(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(10.dp),
    content: @Composable () -> Unit
) {
    Surface(
        shape = shape,
        color = Color(0xCC061A35),
        border = BorderStroke(1.dp, Color(0xFF38BDF8).copy(alpha = 0.45f)),
        modifier = modifier
    ) {
        content()
    }
}

/**
 * Vibrant button for menus and large actions.
 */
@Composable
fun WoodMenuButton(
    title: String,
    subtitle: String,
    icon: ImageVector,
    testTag: String,
    onClick: () -> Unit,
    accentGlow: Boolean = false,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = if (accentGlow) Color(0xFF0F3E7A) else Color(0xEE09264E),
        border = BorderStroke(
            1.6.dp,
            if (accentGlow) GoldenBankGlow else Color(0xFF38BDF8).copy(alpha = 0.7f)
        ),
        shadowElevation = if (accentGlow) 6.dp else 3.dp,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .testTag(testTag)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = if (accentGlow) {
                            listOf(Color(0xFF134E96), Color(0xFF0B2D58))
                        } else {
                            listOf(Color(0xFF0F3B72), Color(0xFF082245))
                        }
                    )
                )
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (accentGlow) Color(0x33F59E0B) else Color(0x3338BDF8),
                border = BorderStroke(1.dp, if (accentGlow) Color(0x66F59E0B) else Color(0x4038BDF8)),
                modifier = Modifier.size(44.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (accentGlow) GoldenBankGlow else Color(0xFFE0F2FE),
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
                    color = if (accentGlow) GoldenBankGlow else Color.White
                )
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (accentGlow) Color(0xFFFDE68A) else Color(0xFFBAE6FD),
                    maxLines = 1
                )
            }
        }
    }
}

/**
 * Filter pill / chip for level selections and category tabs.
 */
@Composable
fun WoodFilterPill(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (selected) RiverForestGreenMid else Color(0xCC09264E),
        border = BorderStroke(
            1.3.dp,
            if (selected) RiverMeadowGrass else RiverTimberBorder.copy(alpha = 0.6f)
        ),
        shadowElevation = if (selected) 4.dp else 1.dp,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (selected) {
                        Brush.verticalGradient(
                            listOf(RiverForestGreenMid, RiverForestGreenDark)
                        )
                    } else {
                        Brush.verticalGradient(
                            listOf(Color(0xE60A2B4E), Color(0xE6071F3B))
                        )
                    }
                )
                .padding(horizontal = 12.dp, vertical = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Bold,
                color = if (selected) Color.White else Color(0xFFBAE6FD)
            )
        }
    }
}

