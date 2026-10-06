package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsBoat
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.VolumeMute
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import android.app.Activity
import com.example.ads.AdManager
import com.example.ads.AdMobBanner
import com.example.R
import com.example.model.GameItem
import com.example.model.LevelTheme
import com.example.model.PuzzleScenario
import com.example.model.PuzzleScenarios
import com.example.ui.components.ComicVignetteDialog
import com.example.ui.components.HighScoresDialog
import com.example.ui.theme.VibrantBackground
import com.example.ui.theme.VibrantPrimary
import com.example.ui.theme.VibrantPrimaryContainer
import com.example.ui.theme.VibrantSurface
import com.example.ui.theme.VibrantSurfaceBorder
import com.example.ui.theme.VibrantSurfaceVariant
import com.example.ui.theme.VibrantTextPrimary
import com.example.ui.theme.VibrantTextSecondary
import com.example.ui.theme.GoldenBankGlow
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
import com.example.ui.theme.MenuQuickActionBgTop
import com.example.ui.theme.MenuQuickActionBgBottom
import com.example.ui.theme.MenuQuickActionBorder
import com.example.ui.theme.WoodButtonBorder
import com.example.ui.theme.WoodButtonBottom
import com.example.ui.theme.WoodButtonTop
import com.example.ui.theme.WoodGoldenText
import com.example.ui.theme.WoodInsetPanel
import com.example.ui.theme.WoodCardBg
import com.example.ui.theme.WoodScreenBg
import com.example.ui.theme.WoodScreenBgTop
import com.example.ui.theme.WoodSignboardBorder
import com.example.ui.theme.WoodSignboardDark
import com.example.ui.theme.WoodSignboardLight
import com.example.ui.theme.WoodSurfaceCard
import com.example.ui.theme.WoodSurfaceCardBorder
import com.example.ui.theme.WoodTextMuted
import com.example.ui.components.WoodCard
import com.example.ui.components.WoodFilterPill
import com.example.ui.components.WoodInsetBox
import com.example.ui.components.WoodScreenContainer
import com.example.ui.components.WoodTopAppBar
import com.example.viewmodel.RiverGameViewModel

enum class LevelFilter(
    val title: String,
    val minLvl: Int,
    val maxLvl: Int,
    val gradient: List<Color>,
    val border: Color
) {
    ALL("All (90)", 1, 90, listOf(MenuBtnAmberTop, MenuBtnAmberMid, MenuBtnAmberBottom), MenuBtnAmberBorder),
    NOVICE("Novice (1-15)", 1, 15, listOf(MenuBtnGreenTop, MenuBtnGreenMid, MenuBtnGreenBottom), MenuBtnGreenBorder),
    SKILLED("Skilled (16-35)", 16, 35, listOf(MenuBtnBlueTop, MenuBtnBlueMid, MenuBtnBlueBottom), MenuBtnBlueBorder),
    EXPERT("Expert (36-55)", 36, 55, listOf(MenuBtnAmberTop, MenuBtnAmberMid, MenuBtnAmberBottom), MenuBtnAmberBorder),
    CHAMPION("Champion (56-75)", 56, 75, listOf(MenuBtnPurpleTop, MenuBtnPurpleMid, MenuBtnPurpleBottom), MenuBtnPurpleBorder),
    GRANDMASTER("Grandmaster (76-90)", 76, 90, listOf(MenuBtnRedTop, MenuBtnRedMid, MenuBtnRedBottom), MenuBtnRedBorder)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelSelectScreen(
    viewModel: RiverGameViewModel,
    onSelectScenario: (PuzzleScenario) -> Unit,
    onNavigateToMainMenu: () -> Unit,
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val isMuted by viewModel.isMuted.collectAsState()
    val highScores by viewModel.highScores.collectAsState()
    val unlockedLevelIds by viewModel.unlockedLevelIds.collectAsState()
    var showLeaderboard by remember { mutableStateOf(false) }
    var showQuickGrid by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf(LevelFilter.ALL) }
    var scenarioToUnlock by remember { mutableStateOf<PuzzleScenario?>(null) }
    var storyScenarioToPreview by remember { mutableStateOf<PuzzleScenario?>(null) }

    // Map best records per scenario by least moves
    val bestRecordsByScenario = remember(highScores) {
        highScores.groupBy { it.levelId }.mapValues { (_, list) ->
            list.minByOrNull { it.movesCount }
        }
    }

    val totalStarsEarned = remember(bestRecordsByScenario) {
        bestRecordsByScenario.values.filterNotNull().sumOf { it.stars }
    }

    val filteredScenarios = remember(selectedFilter) {
        PuzzleScenarios.ALL.filter { it.levelNumber in selectedFilter.minLvl..selectedFilter.maxLvl }
    }

    WoodScreenContainer(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            WoodTopAppBar(
                title = "Levels Menu",
                subtitle = "${PuzzleScenarios.ALL.size} Handcrafted River Puzzles",
                onBack = onNavigateToMainMenu,
                actions = {
                    Surface(
                        shape = CircleShape,
                        color = MenuQuickActionBgTop,
                        border = BorderStroke(1.5.dp, GoldenBankGlow),
                        shadowElevation = 3.dp,
                        modifier = Modifier
                            .size(36.dp)
                            .clickable { showQuickGrid = true }
                            .testTag("level_select_grid_button")
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Apps,
                                contentDescription = "Quick Level Grid",
                                tint = GoldenBankGlow,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    Surface(
                        shape = CircleShape,
                        color = MenuQuickActionBgTop,
                        border = BorderStroke(1.5.dp, GoldenBankGlow),
                        shadowElevation = 3.dp,
                        modifier = Modifier
                            .size(36.dp)
                            .clickable { showLeaderboard = true }
                            .testTag("level_select_leaderboard_button")
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = "Leaderboard",
                                tint = GoldenBankGlow,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    Surface(
                        shape = CircleShape,
                        color = MenuQuickActionBgTop,
                        border = BorderStroke(1.5.dp, GoldenBankGlow),
                        shadowElevation = 3.dp,
                        modifier = Modifier
                            .size(36.dp)
                            .clickable(onClick = onNavigateToSettings)
                            .testTag("level_select_settings_button")
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings",
                                tint = Color(0xFFFEF3C7),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    Surface(
                        shape = CircleShape,
                        color = MenuQuickActionBgTop,
                        border = BorderStroke(1.5.dp, if (isMuted) Color(0xFFEF4444) else GoldenBankGlow),
                        shadowElevation = 3.dp,
                        modifier = Modifier
                            .size(36.dp)
                            .clickable { viewModel.toggleMute() }
                            .testTag("level_select_mute_button")
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = if (isMuted) Icons.Default.VolumeMute else Icons.Default.VolumeUp,
                                contentDescription = "Toggle Mute",
                                tint = if (isMuted) Color(0xFFEF4444) else GoldenBankGlow,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // AdMob Banner Ad at the top of the level selection screen
            AdMobBanner(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(2.dp))
                    LevelSelectHeroCard(
                        totalStars = totalStarsEarned,
                        completedLevels = bestRecordsByScenario.size,
                        totalLevels = PuzzleScenarios.ALL.size,
                        onOpenGrid = { showQuickGrid = true }
                    )
                }

                // Chapter Filter Chips
                item {
                    Column(modifier = Modifier.padding(vertical = 2.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "CHOOSE A CHALLENGE",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = GoldenBankGlow,
                                letterSpacing = 1.sp,
                                modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                            )
                            Text(
                                text = "Showing ${filteredScenarios.size} of ${PuzzleScenarios.ALL.size}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFFBAE6FD)
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(LevelFilter.values()) { filter ->
                                val isSelected = selectedFilter == filter
                                WoodFilterPill(
                                    selected = isSelected,
                                    onClick = { selectedFilter = filter },
                                    text = filter.title,
                                    activeGradient = filter.gradient,
                                    activeBorder = filter.border
                                )
                            }
                        }
                    }
                }

                items(filteredScenarios) { scenario ->
                    val bestRecord = bestRecordsByScenario[scenario.id]
                    val isUnlocked = unlockedLevelIds.contains(scenario.id)

                    LevelScenarioCard(
                        scenario = scenario,
                        bestRecordTime = bestRecord?.timeSeconds,
                        bestRecordMoves = bestRecord?.movesCount,
                        starsEarned = bestRecord?.stars ?: 0,
                        isUnlocked = isUnlocked,
                        onPlay = {
                            if (isUnlocked) {
                                onSelectScenario(scenario)
                            } else {
                                scenarioToUnlock = scenario
                            }
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }

    // Rewarded Ad Unlock Dialog
    scenarioToUnlock?.let { targetScenario ->
        AlertDialog(
            onDismissRequest = { scenarioToUnlock = null },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Locked Level",
                        tint = GoldenBankGlow,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Unlock Level ${targetScenario.levelNumber}?",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Level ${targetScenario.levelNumber} (${targetScenario.title}) is currently locked.",
                        fontSize = 13.sp,
                        color = Color.White
                    )
                    Text(
                        text = "Unlock this level now to set sail immediately and test your crossing skills!",
                        fontSize = 12.sp,
                        color = Color(0xFFBAE6FD)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val levelId = targetScenario.id
                        scenarioToUnlock = null
                        viewModel.unlockLevel(levelId)
                        onSelectScenario(targetScenario)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LockOpen,
                            contentDescription = "Unlock",
                            tint = GoldenBankGlow,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "UNLOCK & PLAY",
                            fontWeight = FontWeight.Bold,
                            color = GoldenBankGlow
                        )
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { scenarioToUnlock = null }) {
                    Text("Cancel", color = Color(0xFFBAE6FD))
                }
            },
            shape = RoundedCornerShape(20.dp),
            containerColor = Color(0xF2072449),
            tonalElevation = 6.dp
        )
    }

    if (showQuickGrid) {
        QuickLevelGridDialog(
            scenarios = PuzzleScenarios.ALL,
            bestRecordsByScenario = bestRecordsByScenario,
            unlockedLevelIds = unlockedLevelIds,
            onDismiss = { showQuickGrid = false },
            onSelectLevel = { scenario ->
                showQuickGrid = false
                if (unlockedLevelIds.contains(scenario.id)) {
                    onSelectScenario(scenario)
                } else {
                    scenarioToUnlock = scenario
                }
            }
        )
    }

    if (showLeaderboard) {
        HighScoresDialog(
            highScores = highScores,
            onDismiss = { showLeaderboard = false },
            onClearScores = { viewModel.clearHighScoreHistory() }
        )
    }

    storyScenarioToPreview?.let { previewScenario ->
        ComicVignetteDialog(
            scenario = previewScenario,
            onDismiss = { storyScenarioToPreview = null }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun QuickLevelGridDialog(
    scenarios: List<PuzzleScenario>,
    bestRecordsByScenario: Map<String, com.example.data.db.HighScoreEntity?>,
    unlockedLevelIds: Set<String>,
    onDismiss: () -> Unit,
    onSelectLevel: (PuzzleScenario) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xF03B1C08)),
            border = BorderStroke(2.dp, GoldenBankGlow),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xF04A260E), Color(0xF02B1405))
                        )
                    )
                    .padding(18.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Level Matrix",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp,
                            color = GoldenBankGlow
                        )
                        Text(
                            text = "Tap any level (1 - ${PuzzleScenarios.ALL.size}) to set sail",
                            fontSize = 11.5.sp,
                            color = Color(0xFFFEF3C7)
                        )
                    }
                    Surface(
                        shape = CircleShape,
                        color = Color(0xDD220F05),
                        border = BorderStroke(1.2.dp, GoldenBankGlow),
                        modifier = Modifier
                            .size(28.dp)
                            .clickable { onDismiss() }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Color(0xFFFEF3C7),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 420.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        maxItemsInEachRow = 5
                    ) {
                        scenarios.forEach { scenario ->
                            val best = bestRecordsByScenario[scenario.id]
                            val stars = best?.stars ?: 0
                            val isCleared = best != null
                            val isUnlocked = unlockedLevelIds.contains(scenario.id)
                            val levelTheme = remember(scenario.levelNumber) { LevelTheme.forScenario(scenario) }

                            val tileGradient = when {
                                isCleared -> levelTheme.sailButtonColors
                                !isUnlocked -> listOf(Color(0xDD241106), Color(0xDD180A03))
                                else -> levelTheme.headerBackgroundColors
                            }

                            val tileBorder = when {
                                isCleared -> levelTheme.sailButtonBorderColors.first()
                                !isUnlocked -> Color(0x44D97706)
                                else -> levelTheme.bankAccentColor
                            }

                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = tileGradient.first(),
                                border = BorderStroke(1.4.dp, tileBorder),
                                modifier = Modifier
                                    .size(54.dp)
                                    .clickable { onSelectLevel(scenario) }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Brush.verticalGradient(tileGradient)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        if (!isUnlocked) {
                                            Icon(
                                                imageVector = Icons.Default.Lock,
                                                contentDescription = "Locked",
                                                tint = Color(0xFFF87171),
                                                modifier = Modifier.size(14.dp)
                                            )
                                            Text(
                                                text = "${scenario.levelNumber}",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 11.sp,
                                                color = Color(0xFFFECACA)
                                            )
                                        } else {
                                            Text(
                                                text = "${scenario.levelNumber}",
                                                fontWeight = FontWeight.ExtraBold,
                                                fontSize = 14.sp,
                                                color = if (isCleared) Color.White else Color(0xFFFEF3C7)
                                            )
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                if (stars > 0) {
                                                    Text(
                                                        text = "★".repeat(stars),
                                                        fontSize = 9.sp,
                                                        color = GoldenBankGlow
                                                    )
                                                } else {
                                                    Text(
                                                        text = scenario.difficulty.iconEmoji,
                                                        fontSize = 9.sp
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
        }
    }
}

@Composable
private fun LevelSelectHeroCard(
    totalStars: Int,
    completedLevels: Int,
    totalLevels: Int,
    onOpenGrid: () -> Unit,
    modifier: Modifier = Modifier
) {
    WoodCard(
        shape = RoundedCornerShape(14.dp),
        gradientColors = listOf(MenuStatBarBgTop, MenuStatBarBgBottom),
        borderColor = MenuStatBarBorder,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xDD072449),
                    border = BorderStroke(1.2.dp, GoldenBankGlow.copy(alpha = 0.8f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Stars",
                            tint = GoldenBankGlow,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$totalStars / ${totalLevels * 3}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = GoldenBankGlow
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xDD072449),
                    border = BorderStroke(1.2.dp, Color(0xFF22C55E).copy(alpha = 0.8f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.EmojiEvents,
                            contentDescription = "Solved",
                            tint = Color(0xFF4ADE80),
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$completedLevels / $totalLevels Solved",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4ADE80)
                        )
                    }
                }
            }

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MenuBtnAmberTop,
                border = BorderStroke(1.2.dp, MenuBtnAmberBorder),
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onOpenGrid() }
                    .testTag("open_quick_grid")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Apps,
                        contentDescription = "Grid",
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Grid",
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun LevelScenarioCard(
    scenario: PuzzleScenario,
    bestRecordTime: Long?,
    bestRecordMoves: Int?,
    starsEarned: Int,
    isUnlocked: Boolean,
    onPlay: () -> Unit,
    modifier: Modifier = Modifier
) {
    val theme = remember(scenario.levelNumber) { LevelTheme.forScenario(scenario) }
    val cardGradient = listOf(
        theme.headerBackgroundColors.first().copy(alpha = 0.95f),
        theme.headerBackgroundColors.last().copy(alpha = 0.98f)
    )
    val cardBorder = if (isUnlocked) theme.headerBorderColors.first() else Color(0x44D97706)

    WoodCard(
        shape = RoundedCornerShape(14.dp),
        gradientColors = cardGradient,
        borderColor = cardBorder,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onPlay)
            .alpha(if (isUnlocked) 1f else 0.85f)
            .testTag("scenario_card_${scenario.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left: Level Number Badge & Stars
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.width(44.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (isUnlocked) theme.dockWoodTop else Color(0x88220F05),
                    border = BorderStroke(1.2.dp, if (isUnlocked) GoldenBankGlow else Color(0x55F87171)),
                    shadowElevation = 2.dp,
                    modifier = Modifier.size(38.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        if (!isUnlocked) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Locked",
                                tint = Color(0xFFFCA5A5),
                                modifier = Modifier.size(16.dp)
                            )
                        } else {
                            Text(
                                text = "${scenario.levelNumber}",
                                color = GoldenBankGlow,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                // 3 Stars Indicator
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(3) { index ->
                        Text(
                            text = "★",
                            fontSize = 10.sp,
                            color = if (index < starsEarned) Color(0xFFFBBF24) else Color(0x44FFFFFF)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            // Center: Title, Difficulty Badge, Items preview, Optimal moves
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = scenario.title,
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(scenario.difficulty.badgeColorHex)
                    ) {
                        Text(
                            text = scenario.difficulty.title,
                            color = Color(scenario.difficulty.badgeTextColorHex),
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Compact items avatar stack
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        scenario.items.take(4).forEach { item ->
                            Surface(
                                shape = CircleShape,
                                color = Color(0x88000000),
                                border = BorderStroke(0.8.dp, GoldenBankGlow.copy(alpha = 0.5f)),
                                modifier = Modifier.size(20.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = item.drawableRes),
                                    contentDescription = item.displayName,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                        if (scenario.items.size > 4) {
                            Text(
                                text = "+${scenario.items.size - 4}",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFEF3C7)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "🎯 ${scenario.optimalMoves} moves",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = GoldenBankGlow.copy(alpha = 0.9f)
                    )

                    if (bestRecordMoves != null) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "• Best: ${bestRecordMoves}m",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF86EFAC)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            // Right: Play Action Button / Lock Indicator
            if (isUnlocked) {
                Surface(
                    shape = CircleShape,
                    color = theme.sailButtonColors.first(),
                    border = BorderStroke(1.5.dp, theme.sailButtonBorderColors.first()),
                    shadowElevation = 3.dp,
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .clickable(onClick = onPlay)
                        .testTag("play_level_${scenario.id}")
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Brush.verticalGradient(theme.sailButtonColors)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            } else {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xDD220F05),
                    border = BorderStroke(1.2.dp, Color(0xFFEF4444).copy(alpha = 0.8f)),
                    shadowElevation = 2.dp,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable(onClick = onPlay)
                        .testTag("unlock_level_${scenario.id}")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Locked",
                            tint = Color(0xFFFCA5A5),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "Unlock",
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFCA5A5)
                        )
                    }
                }
            }
        }
    }
}
