package com.example.ui

import android.app.Activity
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.R
import com.example.model.PuzzleScenarios
import com.example.ui.theme.GoldenBankGlow
import com.example.ui.theme.MenuBtnAmberBorder
import com.example.ui.theme.MenuBtnAmberBottom
import com.example.ui.theme.MenuBtnAmberMid
import com.example.ui.theme.MenuBtnAmberTop
import com.example.ui.theme.MenuBtnBlueBorder
import com.example.ui.theme.MenuBtnBlueBottom
import com.example.ui.theme.MenuBtnBlueMid
import com.example.ui.theme.MenuBtnBlueTop
import com.example.ui.theme.MenuBtnCyanBorder
import com.example.ui.theme.MenuBtnCyanBottom
import com.example.ui.theme.MenuBtnCyanMid
import com.example.ui.theme.MenuBtnCyanTop
import com.example.ui.theme.MenuBtnGreenBorder
import com.example.ui.theme.MenuBtnGreenBottom
import com.example.ui.theme.MenuBtnGreenMid
import com.example.ui.theme.MenuBtnGreenTop
import com.example.ui.theme.MenuBtnPurpleBorder
import com.example.ui.theme.MenuBtnPurpleBottom
import com.example.ui.theme.MenuBtnPurpleMid
import com.example.ui.theme.MenuBtnPurpleTop
import com.example.ui.theme.MenuBtnRedBorder
import com.example.ui.theme.MenuBtnRedBottom
import com.example.ui.theme.MenuBtnRedMid
import com.example.ui.theme.MenuBtnRedTop
import com.example.ui.theme.MenuCrownCyan
import com.example.ui.theme.MenuQuickActionBgBottom
import com.example.ui.theme.MenuQuickActionBgTop
import com.example.ui.theme.MenuQuickActionBorder
import com.example.ui.theme.MenuStatBarBgBottom
import com.example.ui.theme.MenuStatBarBgTop
import com.example.ui.theme.MenuStatBarBorder
import com.example.ui.theme.MenuStatDivider
import com.example.viewmodel.RiverGameViewModel

@Composable
fun MainMenuScreen(
    viewModel: RiverGameViewModel,
    onNavigateToLevels: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToLeaderboard: () -> Unit,
    onNavigateToTutorial: () -> Unit,
    modifier: Modifier = Modifier
) {
    val highScores by viewModel.highScores.collectAsState()
    val isMuted by viewModel.isMuted.collectAsState()
    val completedCount by viewModel.completedLevelsCount.collectAsState()
    var showRulesDialog by remember { mutableStateOf(false) }

    // Aggregate stars and least moves
    val bestScoresByLevel = remember(highScores) {
        PuzzleScenarios.ALL.associate { scenario ->
            scenario.id to highScores.filter { it.levelId == scenario.id }.minByOrNull { it.movesCount }
        }
    }
    val totalStarsEarned = remember(bestScoresByLevel) {
        bestScoresByLevel.values.filterNotNull().sumOf { it.stars }
    }
    val clearedCount = remember(bestScoresByLevel) {
        bestScoresByLevel.values.count { it != null }
    }

    val context = LocalContext.current
    val activity = context as? Activity

    // Determine next level to continue
    val nextLevel = remember(bestScoresByLevel) {
        PuzzleScenarios.ALL.firstOrNull { bestScoresByLevel[it.id] == null } ?: PuzzleScenarios.ALL.first()
    }

    // Dynamic player rank title
    val playerRank = remember(clearedCount) {
        when {
            clearedCount >= 45 -> "Grandmaster"
            clearedCount >= 25 -> "Master"
            clearedCount >= 8 -> "Skilled"
            clearedCount >= 3 -> "Apprentice"
            else -> "Explorer"
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "menu_animations")
    val gentleFloat by infiniteTransition.animateFloat(
        initialValue = -2.5f,
        targetValue = 2.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logo_float"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("main_menu_screen")
    ) {
        // 1. FULL-BLEED BACKGROUND IMAGE (Valley, river, hills, boat foreground)
        Image(
            painter = painterResource(id = R.drawable.img_valley_bg_1790060103557),
            contentDescription = "River Valley Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Subtle gradient scrim at bottom to keep menu text ultra-crisp over landscape
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0x15000000),
                            Color(0x40000000)
                        )
                    )
                )
        )

        // Signpost Graphic overlay on the middle right as seen in reference image
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 10.dp)
                .offset(y = (-38).dp)
        ) {
            SignpostDecoration()
        }

        // Main Vertical Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // =================================================================
            // TOP HEADER: LOGO ON LEFT, TAGLINE IN MIDDLE, ACTION BUTTONS RIGHT
            // =================================================================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Game Title Logo Banner (Wooden signboard with 3D embossed lettering)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .offset(y = gentleFloat.dp)
                ) {
                    RiverCrossingTitleLogo()
                }

                Spacer(modifier = Modifier.width(6.dp))

                // Tagline & Quick Action Buttons (Sound, Rules, Close)
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Quick Action Buttons Row (Circular 3D glossy blue pills)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Sound Mute Toggle
                        QuickActionButton(
                            drawableRes = if (isMuted) R.drawable.ic_sound_mute else R.drawable.ic_sound_speaker,
                            contentDescription = if (isMuted) "Unmute Audio" else "Mute Audio",
                            testTag = "main_menu_mute_button",
                            onClick = { viewModel.toggleMute() }
                        )

                        // Help / Rules
                        QuickActionButton(
                            drawableRes = R.drawable.ic_help_question,
                            contentDescription = "How To Play Rules",
                            testTag = "main_menu_rules_button",
                            onClick = { showRulesDialog = true }
                        )

                        // Close App
                        QuickActionButton(
                            icon = Icons.Default.Close,
                            contentDescription = "Close Game",
                            testTag = "main_menu_close_button",
                            onClick = { activity?.finish() }
                        )
                    }

                    // Slogan Tagline: "Think • Plan • Move" + "Get everyone safely to the other side!"
                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier.padding(end = 4.dp, top = 2.dp)
                    ) {
                        Text(
                            text = "Think • Plan • Move",
                            color = Color(0xFFE0F2FE),
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontStyle = FontStyle.Italic,
                            letterSpacing = 0.5.sp,
                            style = androidx.compose.ui.text.TextStyle(
                                shadow = androidx.compose.ui.graphics.Shadow(
                                    color = Color(0x99000000),
                                    offset = androidx.compose.ui.geometry.Offset(1f, 2f),
                                    blurRadius = 3f
                                )
                            )
                        )
                        Text(
                            text = "Get everyone safely\nto the other side!",
                            color = Color.White,
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic,
                            lineHeight = 14.sp,
                            textAlign = TextAlign.End,
                            style = androidx.compose.ui.text.TextStyle(
                                shadow = androidx.compose.ui.graphics.Shadow(
                                    color = Color(0x99000000),
                                    offset = androidx.compose.ui.geometry.Offset(1f, 2f),
                                    blurRadius = 3f
                                )
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            // =================================================================
            // STAT CAPSULE BAR: STARS, LEVELS CLEARED, PLAYER RANK
            // Deep glossy ocean blue container with cyan highlight border
            // =================================================================
            StatCapsuleBar(
                starsEarned = totalStarsEarned,
                totalStars = PuzzleScenarios.ALL.size * 3,
                levelsCleared = clearedCount,
                totalLevels = PuzzleScenarios.ALL.size,
                rankTitle = playerRank
            )

            // =================================================================
            // SECTION HEADER: "🧭 EXPEDITION MENU"
            // =================================================================
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp, bottom = 2.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color(0xCC072449),
                    border = BorderStroke(1.2.dp, GoldenBankGlow),
                    modifier = Modifier.size(22.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_map_treasure),
                            contentDescription = null,
                            tint = GoldenBankGlow,
                            modifier = Modifier.size(13.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "EXPEDITION MENU",
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.2.sp,
                    color = GoldenBankGlow
                )
            }

            // =================================================================
            // 6 VIBRANT RAINBOW MENU ACTION BUTTONS (Original Reference Design)
            // =================================================================

            // 1. CONTINUE / PLAY GAME (LUSH EMERALD / MEADOW GREEN)
            MainMenuPillButton(
                title = if (clearedCount == 0) "Play Game" else "Continue",
                subtitle = "Level ${nextLevel.levelNumber}: ${nextLevel.title} (${nextLevel.difficulty.title})",
                iconVector = Icons.Default.PlayArrow,
                badgeText = if (clearedCount == 0) "START" else "LVL ${nextLevel.levelNumber}",
                gradientColors = listOf(MenuBtnGreenTop, MenuBtnGreenMid, MenuBtnGreenBottom),
                borderColor = MenuBtnGreenBorder,
                testTag = "main_menu_play_button",
                onClick = { viewModel.selectScenarioAndPlay(nextLevel) }
            )

            // 2. LEVELS MENU (BRILLIANT SKY / OCEAN BLUE)
            MainMenuPillButton(
                title = "Levels Menu",
                subtitle = "Browse all 50 handcrafted river puzzles",
                drawableIconRes = R.drawable.ic_map_treasure,
                badgeText = "$clearedCount/50",
                gradientColors = listOf(MenuBtnBlueTop, MenuBtnBlueMid, MenuBtnBlueBottom),
                borderColor = MenuBtnBlueBorder,
                testTag = "main_menu_levels_button",
                onClick = onNavigateToLevels
            )

            // 3. RIVER ACADEMY (VIBRANT GOLDEN AMBER)
            MainMenuPillButton(
                title = "River Academy",
                subtitle = "Step-by-step guided practice, rules & bestiary",
                drawableIconRes = R.drawable.ic_group_people,
                badgeText = "GUIDE",
                gradientColors = listOf(MenuBtnAmberTop, MenuBtnAmberMid, MenuBtnAmberBottom),
                borderColor = MenuBtnAmberBorder,
                testTag = "main_menu_tutorial_button",
                onClick = onNavigateToTutorial
            )

            // 4. HIGH SCORE LEADERBOARD (ROYAL AMETHYST PURPLE)
            MainMenuPillButton(
                title = "High Score Leaderboard",
                subtitle = "Personal best moves, stars & optimal clears",
                drawableIconRes = R.drawable.ic_crown,
                badgeText = "$totalStarsEarned ★",
                gradientColors = listOf(MenuBtnPurpleTop, MenuBtnPurpleMid, MenuBtnPurpleBottom),
                borderColor = MenuBtnPurpleBorder,
                testTag = "main_menu_leaderboard_button",
                onClick = onNavigateToLeaderboard
            )

            // 5. SETTINGS MENU (VIBRANT TEAL / CYAN)
            MainMenuPillButton(
                title = "Settings Menu",
                subtitle = "Audio SFX, captain profile & modifiers",
                iconVector = Icons.Default.Settings,
                gradientColors = listOf(MenuBtnCyanTop, MenuBtnCyanMid, MenuBtnCyanBottom),
                borderColor = MenuBtnCyanBorder,
                testTag = "main_menu_settings_button",
                onClick = onNavigateToSettings
            )

            // 6. QUIT GAME (VIBRANT CRIMSON / RUBY RED)
            MainMenuPillButton(
                title = "Quit Game",
                subtitle = "Exit back to home screen",
                iconVector = Icons.AutoMirrored.Filled.ExitToApp,
                gradientColors = listOf(MenuBtnRedTop, MenuBtnRedMid, MenuBtnRedBottom),
                borderColor = MenuBtnRedBorder,
                testTag = "main_menu_quit_button",
                onClick = { activity?.finish() }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Rules Dialog overlay
        if (showRulesDialog) {
            HowToPlayDialog(onDismiss = { showRulesDialog = false })
        }
    }
}

/**
 * Authentic wooden signboard title logo with 3D embossed lettering and metallic corner rivets
 */
@Composable
private fun RiverCrossingTitleLogo(modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF65330D)),
        border = BorderStroke(
            2.dp,
            Brush.verticalGradient(
                listOf(Color(0xFFD97706), Color(0xFF92400E), Color(0xFF5A2603))
            )
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier.padding(end = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF8B4513),
                            Color(0xFF6B3209),
                            Color(0xFF4D2205)
                        )
                    )
                )
                .padding(horizontal = 14.dp, vertical = 8.dp)
        ) {
            // Metallic screw accents in corners
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFDE68A))
                    .align(Alignment.TopStart)
            )
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFDE68A))
                    .align(Alignment.TopEnd)
            )
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFB45309))
                    .align(Alignment.BottomStart)
            )
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFB45309))
                    .align(Alignment.BottomEnd)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = "⛵",
                        fontSize = 19.sp
                    )
                    Text(
                        text = "RIVER",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        letterSpacing = 1.5.sp,
                        style = androidx.compose.ui.text.TextStyle(
                            shadow = androidx.compose.ui.graphics.Shadow(
                                color = Color(0xFF2E1202),
                                offset = androidx.compose.ui.geometry.Offset(2f, 2f),
                                blurRadius = 2f
                            )
                        )
                    )
                }

                Text(
                    text = "CROSSING",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = GoldenBankGlow,
                    letterSpacing = 1.2.sp,
                    style = androidx.compose.ui.text.TextStyle(
                        shadow = androidx.compose.ui.graphics.Shadow(
                            color = Color(0xFF522103),
                            offset = androidx.compose.ui.geometry.Offset(2f, 2f),
                            blurRadius = 2f
                        )
                    )
                )
            }
        }
    }
}

/**
 * Top Circular Quick Action Button with glossy 3D blue gradient matching the reference
 */
@Composable
private fun QuickActionButton(
    icon: ImageVector? = null,
    drawableRes: Int? = null,
    contentDescription: String,
    testTag: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.88f else 1.0f,
        animationSpec = spring(dampingRatio = 0.52f, stiffness = 600f),
        label = "quick_act_scale"
    )

    Surface(
        shape = CircleShape,
        color = MenuQuickActionBgTop,
        border = BorderStroke(1.5.dp, MenuQuickActionBorder),
        shadowElevation = 4.dp,
        modifier = Modifier
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
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(MenuQuickActionBgTop, MenuQuickActionBgBottom)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = contentDescription,
                    tint = Color.White,
                    modifier = Modifier.size(19.dp)
                )
            } else if (drawableRes != null) {
                Icon(
                    painter = painterResource(id = drawableRes),
                    contentDescription = contentDescription,
                    tint = Color.White,
                    modifier = Modifier.size(19.dp)
                )
            }
        }
    }
}

/**
 * Stat Capsule Bar (Stars, Levels Cleared, Rank) with glossy ocean blue container
 */
@Composable
private fun StatCapsuleBar(
    starsEarned: Int,
    totalStars: Int,
    levelsCleared: Int,
    totalLevels: Int,
    rankTitle: String
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MenuStatBarBgBottom,
        border = BorderStroke(1.5.dp, MenuStatBarBorder),
        shadowElevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("main_menu_stat_capsule_bar")
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(MenuStatBarBgTop, MenuStatBarBgBottom)
                    )
                )
                .padding(horizontal = 14.dp, vertical = 9.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 1. Stars Earned
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0x33000000),
                        border = BorderStroke(1.2.dp, GoldenBankGlow),
                        modifier = Modifier.size(28.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("⭐", fontSize = 13.sp)
                        }
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text(
                            text = "$starsEarned / $totalStars",
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = GoldenBankGlow
                        )
                        Text(
                            text = "Stars Earned",
                            fontSize = 9.5.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFBAE6FD)
                        )
                    }
                }

                // Divider line
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(26.dp)
                        .background(MenuStatDivider)
                )

                // 2. Levels Cleared
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1.1f)
                        .padding(start = 10.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0x33000000),
                        border = BorderStroke(1.2.dp, MenuCrownCyan),
                        modifier = Modifier.size(28.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_crown),
                                contentDescription = null,
                                tint = MenuCrownCyan,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text(
                            text = "$levelsCleared / $totalLevels",
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                        Text(
                            text = "Levels Cleared",
                            fontSize = 9.5.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFBAE6FD)
                        )
                    }
                }

                // Divider line
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(26.dp)
                        .background(MenuStatDivider)
                )

                // 3. Player Rank
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0x33000000),
                        border = BorderStroke(1.2.dp, GoldenBankGlow),
                        modifier = Modifier.size(28.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("🏆", fontSize = 13.sp)
                        }
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Column {
                        Text(
                            text = rankTitle,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = GoldenBankGlow
                        )
                        Text(
                            text = "Player Rank",
                            fontSize = 9.5.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFBAE6FD)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Vibrant 3D pill button with rainbow gradients matching reference design
 */
@Composable
private fun MainMenuPillButton(
    title: String,
    subtitle: String,
    iconVector: ImageVector? = null,
    drawableIconRes: Int? = null,
    badgeText: String? = null,
    gradientColors: List<Color>,
    borderColor: Color,
    testTag: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val buttonScale by animateFloatAsState(
        targetValue = if (isPressed) 0.94f else 1.0f,
        animationSpec = spring(dampingRatio = 0.52f, stiffness = 600f),
        label = "btn_scale_$title"
    )

    Surface(
        shape = RoundedCornerShape(18.dp),
        color = gradientColors.last(),
        border = BorderStroke(2.dp, borderColor),
        shadowElevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .scale(buttonScale)
            .shadow(4.dp, RoundedCornerShape(18.dp))
            .clip(RoundedCornerShape(18.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .testTag(testTag)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(colors = gradientColors))
                .padding(horizontal = 12.dp, vertical = 10.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left Circular Inset Medallion
                Surface(
                    shape = CircleShape,
                    color = Color(0x35000000),
                    border = BorderStroke(1.2.dp, Color.White.copy(alpha = 0.65f)),
                    modifier = Modifier.size(44.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (iconVector != null) {
                            Icon(
                                imageVector = iconVector,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(23.dp)
                            )
                        } else if (drawableIconRes != null) {
                            Icon(
                                painter = painterResource(id = drawableIconRes),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Title and Subtitle Text
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = title,
                            fontSize = 16.5.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            letterSpacing = 0.3.sp,
                            style = androidx.compose.ui.text.TextStyle(
                                shadow = androidx.compose.ui.graphics.Shadow(
                                    color = Color(0x99000000),
                                    offset = androidx.compose.ui.geometry.Offset(1f, 1f),
                                    blurRadius = 2f
                                )
                            )
                        )
                        if (badgeText != null) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0x40000000),
                                border = BorderStroke(0.8.dp, Color.White.copy(alpha = 0.7f))
                            ) {
                                Text(
                                    text = badgeText,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                                )
                            }
                        }
                    }

                    Text(
                        text = subtitle,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xE6FFFFFF),
                        maxLines = 1
                    )
                }

                // Right Chevron Arrow in subtle inset circle
                Surface(
                    shape = CircleShape,
                    color = Color(0x25000000),
                    modifier = Modifier.size(26.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Directional rustic carved wooden signpost pointing to Left and Right banks
 */
@Composable
private fun SignpostDecoration(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        // Upper plank: Left Bank
        Surface(
            shape = RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp, topEnd = 12.dp, bottomEnd = 12.dp),
            color = Color(0xFF6B3209),
            border = BorderStroke(1.5.dp, Color(0xFFD97706)),
            shadowElevation = 5.dp
        ) {
            Row(
                modifier = Modifier
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFF8B4513), Color(0xFF5A2603))
                        )
                    )
                    .padding(horizontal = 9.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "◀ Left Bank",
                    fontSize = 9.5.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFFFFBEB),
                    letterSpacing = 0.4.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Lower plank: Right Bank
        Surface(
            shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp, topEnd = 4.dp, bottomEnd = 4.dp),
            color = Color(0xFF6B3209),
            border = BorderStroke(1.5.dp, GoldenBankGlow),
            shadowElevation = 5.dp
        ) {
            Row(
                modifier = Modifier
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFF5A2603), Color(0xFF8B4513))
                        )
                    )
                    .padding(horizontal = 9.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Right Bank ▶",
                    fontSize = 9.5.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = GoldenBankGlow,
                    letterSpacing = 0.4.sp
                )
            }
        }

        // Wooden signpost pole
        Box(
            modifier = Modifier
                .width(5.dp)
                .height(18.dp)
                .background(Color(0xFF4D2205))
        )
    }
}

/**
 * Dialog displaying River Crossing Rules & Lore
 */
@Composable
private fun HowToPlayDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color(0xF2072449),
            border = BorderStroke(1.8.dp, Color(0xFF38BDF8)),
            shadowElevation = 10.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "📜", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Expedition Rules & Lore",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 16.5.sp,
                            color = GoldenBankGlow
                        )
                    }
                    Surface(
                        shape = CircleShape,
                        color = Color(0x33000000),
                        border = BorderStroke(1.dp, Color(0xFF38BDF8).copy(alpha = 0.5f)),
                        modifier = Modifier
                            .size(28.dp)
                            .clickable { onDismiss() }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Color(0xFFE0F2FE),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0x4D041429),
                    border = BorderStroke(1.dp, Color(0x3338BDF8))
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "The Goal",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = Color(0xFF38BDF8)
                        )
                        Text(
                            text = "Transport all characters and cargo safely from the Left Bank across to the Right Bank.",
                            fontSize = 12.sp,
                            color = Color.White
                        )

                        Text(
                            text = "River Crossing Laws",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = Color(0xFF38BDF8)
                        )
                        Text(
                            text = "• The Farmer must always pilot the wooden rowboat.\n• The boat can only carry 1 or 2 passengers at a time.\n• If the Farmer leaves a bank, predator & prey left together will clash (Wolf eats Sheep, Sheep eats Cabbage, Fox eats Rabbit, etc.)!\n• Complete the crossings in the fewest moves to earn 3 Stars and climb the Leaderboards!",
                            fontSize = 12.sp,
                            color = Color(0xFFE0F2FE),
                            lineHeight = 16.sp
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0xFF0284C7),
                    border = BorderStroke(1.5.dp, Color(0xFF38BDF8)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .clickable(onClick = onDismiss)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color(0xFF0284C7), Color(0xFF0369A1))
                                )
                            )
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Got It, Let's Play!",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
