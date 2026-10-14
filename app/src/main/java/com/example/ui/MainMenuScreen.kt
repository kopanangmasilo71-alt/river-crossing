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
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
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
import com.example.model.PuzzleScenario
import com.example.model.PuzzleScenarios
import com.example.ui.theme.GoldenBankGlow
import com.example.ui.theme.MenuBtnAmberBorder
import com.example.ui.theme.MenuBtnAmberBottom
import com.example.ui.theme.MenuBtnAmberMid
import com.example.ui.theme.MenuBtnAmberTop
import com.example.ui.theme.MenuBtnRedBorder
import com.example.ui.theme.MenuBtnRedBottom
import com.example.ui.theme.MenuBtnRedMid
import com.example.ui.components.BannerAdView
import com.example.ui.components.LockedLevelUnlockDialog
import com.example.ui.components.WoodAssets
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
    val unlockedLevelIds by viewModel.unlockedLevelIds.collectAsState()
    var showRulesDialog by remember { mutableStateOf(false) }
    var scenarioToUnlock by remember { mutableStateOf<PuzzleScenario?>(null) }

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

        // Main Layout: Scrollable menu items with fixed bottom BannerAdView
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Scrollable Menu Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
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
                        .padding(top = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Sleek compact game title
                    Box(
                        modifier = Modifier
                            .weight(1.12f)
                            .offset(y = gentleFloat.dp)
                    ) {
                        RiverCrossingTitleText()
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Tagline & Quick Action Buttons (Sound, Rules, Close)
                    Column(
                        modifier = Modifier.weight(0.88f),
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        // Quick Action Buttons Row
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

                        // Slogan Tagline: "Think • Plan • Move"
                        Column(
                            horizontalAlignment = Alignment.End,
                            modifier = Modifier.padding(end = 4.dp, top = 1.dp)
                        ) {
                            Text(
                                text = "Think • Plan • Move",
                                color = Color(0xFFFEF3C7),
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontStyle = FontStyle.Italic,
                                letterSpacing = 0.5.sp,
                                style = androidx.compose.ui.text.TextStyle(
                                    shadow = androidx.compose.ui.graphics.Shadow(
                                        color = Color(0xCC000000),
                                        offset = androidx.compose.ui.geometry.Offset(1f, 2f),
                                        blurRadius = 3f
                                    )
                                )
                            )
                            Text(
                                text = "Get everyone safely across!",
                                color = Color(0xFFFFFBEB),
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Italic,
                                textAlign = TextAlign.End,
                                style = androidx.compose.ui.text.TextStyle(
                                    shadow = androidx.compose.ui.graphics.Shadow(
                                        color = Color(0xCC000000),
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
                // Solid carved wood badge with crisp high-contrast cream/white text
                // =================================================================
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF2E1508),
                    border = BorderStroke(1.4.dp, GoldenBankGlow),
                    shadowElevation = 4.dp,
                    modifier = Modifier.padding(start = 2.dp, top = 4.dp, bottom = 2.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF4A250E),
                            border = BorderStroke(1.dp, GoldenBankGlow),
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
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "EXPEDITION MENU",
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.2.sp,
                            color = Color(0xFFFFFBEB),
                            style = androidx.compose.ui.text.TextStyle(
                                shadow = androidx.compose.ui.graphics.Shadow(
                                    color = Color(0xCC000000),
                                    offset = androidx.compose.ui.geometry.Offset(1f, 1.5f),
                                    blurRadius = 3f
                                )
                            )
                        )
                    }
                }

                // =================================================================
                // 6 COHESIVE SOLID & BRIGHT CARVED WOOD MENU ACTION BUTTONS
                // =================================================================

                // 1. CONTINUE / PLAY GAME (VIBRANT SUNLIT GOLDEN TIMBER)
                MainMenuPillButton(
                    title = if (clearedCount == 0) "Play Game" else "Continue",
                    subtitle = "Level ${nextLevel.levelNumber}: ${nextLevel.title} (${nextLevel.difficulty.title})",
                    iconVector = Icons.Default.PlayArrow,
                    badgeText = if (clearedCount == 0) "START" else "LVL ${nextLevel.levelNumber}",
                    highlightColor = Color(0xFF10B981),
                    borderColor = Color(0xFF34D399),
                    medallionBg = Color(0xFF065F46),
                    testTag = "main_menu_play_button",
                    onClick = {
                        if (unlockedLevelIds.contains(nextLevel.id)) {
                            viewModel.selectScenarioAndPlay(nextLevel)
                        } else {
                            scenarioToUnlock = nextLevel
                        }
                    }
                )

                // 2. LEVELS MENU (WARM RICH CEDAR TIMBER)
                MainMenuPillButton(
                    title = "Levels Menu",
                    subtitle = "Browse all ${PuzzleScenarios.ALL.size} handcrafted river puzzles",
                    drawableIconRes = R.drawable.ic_map_treasure,
                    badgeText = "$clearedCount/${PuzzleScenarios.ALL.size}",
                    highlightColor = Color(0xFFF59E0B),
                    borderColor = Color(0xFFFBBF24),
                    medallionBg = Color(0xFF92400E),
                    testTag = "main_menu_levels_button",
                    onClick = onNavigateToLevels
                )

                // 3. RIVER ACADEMY (BRIGHT SAPPHIRE RIVER TIMBER)
                MainMenuPillButton(
                    title = "River Academy",
                    subtitle = "Step-by-step guided practice, rules & bestiary",
                    drawableIconRes = R.drawable.ic_group_people,
                    badgeText = "GUIDE",
                    highlightColor = Color(0xFF38BDF8),
                    borderColor = Color(0xFF7DD3FC),
                    medallionBg = Color(0xFF0369A1),
                    testTag = "main_menu_tutorial_button",
                    onClick = onNavigateToTutorial
                )

                // 4. HIGH SCORE LEADERBOARD (ROYAL RADIANT GOLD TIMBER)
                MainMenuPillButton(
                    title = "High Score Leaderboard",
                    subtitle = "Personal best moves, stars & optimal clears",
                    drawableIconRes = R.drawable.ic_crown,
                    badgeText = "$totalStarsEarned ★",
                    highlightColor = Color(0xFFFACC15),
                    borderColor = Color(0xFFFEF08A),
                    medallionBg = Color(0xFFA16207),
                    testTag = "main_menu_leaderboard_button",
                    onClick = onNavigateToLeaderboard
                )

                // 5. SETTINGS MENU (VIBRANT AMETHYST OAK TIMBER)
                MainMenuPillButton(
                    title = "Settings Menu",
                    subtitle = "Audio SFX, captain profile & modifiers",
                    iconVector = Icons.Default.Settings,
                    highlightColor = Color(0xFFA78BFA),
                    borderColor = Color(0xFFC4B5FD),
                    medallionBg = Color(0xFF6D28D9),
                    testTag = "main_menu_settings_button",
                    onClick = onNavigateToSettings
                )

                // 6. QUIT GAME (WARM COPPER RUST TIMBER)
                MainMenuPillButton(
                    title = "Quit Game",
                    subtitle = "Exit back to home screen",
                    iconVector = Icons.AutoMirrored.Filled.ExitToApp,
                    highlightColor = Color(0xFFF87171),
                    borderColor = Color(0xFFFCA5A5),
                    medallionBg = Color(0xFF991B1B),
                    testTag = "main_menu_quit_button",
                    onClick = { activity?.finish() }
                )

                Spacer(modifier = Modifier.height(4.dp))
            }

            // =================================================================
            // FIXED BOTTOM BANNER AD VIEW (Does not scroll with menu buttons)
            // =================================================================
            Surface(
                color = Color(0xF21C0E05),
                border = BorderStroke(1.2.dp, Color(0xFF6B3510)),
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    BannerAdView()
                }
            }
        }

        // Rules Dialog overlay
        if (showRulesDialog) {
            HowToPlayDialog(onDismiss = { showRulesDialog = false })
        }

        // Rewarded Ad Unlock Dialog for locked next level
        scenarioToUnlock?.let { targetScenario ->
            LockedLevelUnlockDialog(
                scenario = targetScenario,
                onDismiss = { scenarioToUnlock = null },
                onUnlockSuccess = {
                    val levelId = targetScenario.id
                    scenarioToUnlock = null
                    viewModel.unlockLevel(levelId)
                    viewModel.selectScenarioAndPlay(targetScenario)
                }
            )
        }
    }
}

/**
 * Game title with game launcher icon positioned directly above the name of the game
 */
@Composable
private fun RiverCrossingTitleText(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .testTag("main_menu_game_title")
            .padding(end = 4.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Game launcher icon placed directly above the name of the game with wooden frame
        Surface(
            shape = RoundedCornerShape(12.dp),
            shadowElevation = 6.dp,
            border = BorderStroke(1.8.dp, GoldenBankGlow),
            color = Color(0xFF2C1607),
            modifier = Modifier
                .padding(bottom = 6.dp)
                .size(42.dp)
                .testTag("main_menu_launch_icon")
        ) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = WoodAssets.btnSmall),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize().alpha(1.0f)
                )
                Image(
                    painter = painterResource(id = R.drawable.img_app_icon),
                    contentDescription = "Game Launch Icon",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                )
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "RIVER ",
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp,
                color = GoldenBankGlow,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color(0xCC000000),
                        offset = Offset(1.5f, 2f),
                        blurRadius = 3f
                    )
                )
            )
            Text(
                text = "CROSSING",
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp,
                color = Color.White,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color(0xCC000000),
                        offset = Offset(1.5f, 2f),
                        blurRadius = 3f
                    )
                )
            )
        }
        Text(
            text = "LOGIC PUZZLE ADVENTURE",
            fontSize = 9.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.2.sp,
            color = Color(0xFFFDE68A),
            style = TextStyle(
                shadow = Shadow(
                    color = Color(0x99000000),
                    offset = Offset(1f, 1f),
                    blurRadius = 2f
                )
            )
        )
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
        color = Color(0xFF1E3A5F),
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
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = WoodAssets.btnSmall),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(1.0f)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                MenuQuickActionBgTop.copy(alpha = 0.55f),
                                MenuQuickActionBgBottom.copy(alpha = 0.70f)
                            )
                        )
                    )
            )
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
        color = Color(0xFF2C1607),
        border = BorderStroke(1.5.dp, MenuStatBarBorder),
        shadowElevation = 6.dp,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("main_menu_stat_capsule_bar")
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF5A2C0F), Color(0xFF381A07))
                        )
                    )
            )
            Image(
                painter = painterResource(id = WoodAssets.pillMenu),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .alpha(1.0f)
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                MenuStatBarBgTop.copy(alpha = 0.40f),
                                MenuStatBarBgBottom.copy(alpha = 0.55f)
                            )
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .align(Alignment.TopCenter)
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color.Transparent, Color(0x88FFE082), Color.Transparent)
                        )
                    )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 9.dp),
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
                            color = Color(0xFFFDE68A)
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
                            color = Color(0xFFFDE68A)
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
                            color = Color(0xFFFDE68A)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Authentic carved wood menu button with solid opaque base, rich warm highlights,
 * and crisp typography.
 */
@Composable
private fun MainMenuPillButton(
    title: String,
    subtitle: String,
    iconVector: ImageVector? = null,
    drawableIconRes: Int? = null,
    badgeText: String? = null,
    highlightColor: Color = Color(0xFFD97706),
    borderColor: Color = GoldenBankGlow,
    medallionBg: Color = Color(0xFF4A250E),
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
        color = Color(0xFF2C1607), // SOLID OPAQUE COLOR - zero transparency
        border = BorderStroke(2.dp, borderColor),
        shadowElevation = 6.dp,
        modifier = Modifier
            .fillMaxWidth()
            .scale(buttonScale)
            .shadow(5.dp, RoundedCornerShape(18.dp))
            .clip(RoundedCornerShape(18.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .testTag(testTag)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            // 1. Solid warm timber base layer
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF5A2C0F), Color(0xFF381A07))
                        )
                    )
            )

            // 2. Solid carved wooden button artwork (100% opaque, no transparency)
            Image(
                painter = painterResource(id = WoodAssets.pillMenu),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .alpha(1.0f)
            )

            // 3. Warm luminous glow that brightens the button art
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                highlightColor.copy(alpha = 0.35f),
                                highlightColor.copy(alpha = 0.12f),
                                Color.Transparent
                            )
                        )
                    )
            )

            // 4. Subtle top bevel highlight for 3D embossed look
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.5.dp)
                    .align(Alignment.TopCenter)
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                Color.Transparent,
                                Color(0x88FFE082),
                                Color(0xCCFFD54F),
                                Color(0x88FFE082),
                                Color.Transparent
                            )
                        )
                    )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left Circular Inset Medallion
                Surface(
                    shape = CircleShape,
                    color = medallionBg,
                    border = BorderStroke(1.5.dp, Color.White.copy(alpha = 0.85f)),
                    shadowElevation = 3.dp,
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
                            fontWeight = FontWeight.Black,
                            color = Color(0xFFFFFBEB),
                            letterSpacing = 0.3.sp,
                            style = androidx.compose.ui.text.TextStyle(
                                shadow = androidx.compose.ui.graphics.Shadow(
                                    color = Color(0xDD000000),
                                    offset = androidx.compose.ui.geometry.Offset(1f, 1.5f),
                                    blurRadius = 3f
                                )
                            )
                        )
                        if (badgeText != null) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0xFF431D06),
                                border = BorderStroke(1.dp, GoldenBankGlow)
                            ) {
                                Text(
                                    text = badgeText,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFFFFFBEB),
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                                )
                            }
                        }
                    }

                    Text(
                        text = subtitle,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFFEF3C7),
                        maxLines = 1,
                        style = androidx.compose.ui.text.TextStyle(
                            shadow = androidx.compose.ui.graphics.Shadow(
                                color = Color(0x99000000),
                                offset = androidx.compose.ui.geometry.Offset(0.5f, 1f),
                                blurRadius = 2f
                            )
                        )
                    )
                }

                // Right Chevron Arrow in subtle inset circle
                Surface(
                    shape = CircleShape,
                    color = Color(0x40000000),
                    border = BorderStroke(0.8.dp, Color(0x66FFFFFF)),
                    modifier = Modifier.size(26.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null,
                            tint = Color(0xFFFFFBEB),
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
 * Dialog displaying River Crossing Rules & Lore with carved wooden theme
 */
@Composable
private fun HowToPlayDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color(0xF22B1406),
            border = BorderStroke(2.dp, GoldenBankGlow),
            shadowElevation = 10.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = WoodAssets.woodPanel),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .matchParentSize()
                        .alpha(0.35f)
                )
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
                            color = Color(0x40000000),
                            border = BorderStroke(1.2.dp, GoldenBankGlow.copy(alpha = 0.7f)),
                            modifier = Modifier
                                .size(28.dp)
                                .clickable { onDismiss() }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "Close",
                                    tint = Color(0xFFFFFBEB),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0x80180B04),
                        border = BorderStroke(1.2.dp, Color(0xFF8B4513))
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "The Goal",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = GoldenBankGlow
                            )
                            Text(
                                text = "Transport all characters and cargo safely from the Left Bank across to the Right Bank.",
                                fontSize = 12.sp,
                                color = Color(0xFFFEF3C7)
                            )

                            Text(
                                text = "River Crossing Laws",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = GoldenBankGlow
                            )
                            Text(
                                text = "• The Farmer must always pilot the wooden rowboat.\n• The boat can only carry 1 or 2 passengers at a time.\n• If the Farmer leaves a bank, predator & prey left together will clash (Wolf eats Sheep, Sheep eats Cabbage, Fox eats Rabbit, etc.)!\n• Complete the crossings in the fewest moves to earn 3 Stars and climb the Leaderboards!",
                                fontSize = 12.sp,
                                color = Color(0xFFFEF3C7),
                                lineHeight = 16.sp
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Color.Transparent,
                        border = BorderStroke(1.5.dp, GoldenBankGlow),
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
                                        colors = listOf(Color(0xFF8B4513), Color(0xFF5A2603))
                                    )
                                )
                                .padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Got It, Let's Play!",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 14.sp,
                                color = Color(0xFFFFFBEB)
                            )
                        }
                    }
                }
            }
        }
    }
}
