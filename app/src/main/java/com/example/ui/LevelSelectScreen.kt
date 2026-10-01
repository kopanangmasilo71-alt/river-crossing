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

enum class LevelFilter(val title: String, val minLvl: Int, val maxLvl: Int) {
    ALL("All (50)", 1, 50),
    NOVICE("Novice (1-10)", 1, 10),
    SKILLED("Skilled (11-20)", 11, 20),
    EXPERT("Expert (21-35)", 21, 35),
    CHAMPION("Champion (36-45)", 36, 45),
    GRANDMASTER("Grandmaster (46-50)", 46, 50)
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
                subtitle = "50 Handcrafted River Puzzles",
                onBack = onNavigateToMainMenu,
                actions = {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = WoodInsetPanel,
                        border = BorderStroke(1.dp, Color(0xFF38BDF8).copy(alpha = 0.5f)),
                        modifier = Modifier
                            .clickable { showQuickGrid = true }
                            .testTag("level_select_grid_button")
                    ) {
                        Box(modifier = Modifier.padding(6.dp), contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Apps,
                                contentDescription = "Quick Level Grid",
                                tint = Color(0xFFBAE6FD),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = WoodInsetPanel,
                        border = BorderStroke(1.dp, Color(0xFF38BDF8).copy(alpha = 0.5f)),
                        modifier = Modifier
                            .clickable { showLeaderboard = true }
                            .testTag("level_select_leaderboard_button")
                    ) {
                        Box(modifier = Modifier.padding(6.dp), contentAlignment = Alignment.Center) {
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
                        shape = RoundedCornerShape(8.dp),
                        color = WoodInsetPanel,
                        border = BorderStroke(1.dp, Color(0xFF38BDF8).copy(alpha = 0.5f)),
                        modifier = Modifier
                            .clickable(onClick = onNavigateToSettings)
                            .testTag("level_select_settings_button")
                    ) {
                        Box(modifier = Modifier.padding(6.dp), contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings",
                                tint = Color(0xFFBAE6FD),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = WoodInsetPanel,
                        border = BorderStroke(1.dp, Color(0xFF38BDF8).copy(alpha = 0.5f)),
                        modifier = Modifier
                            .clickable { viewModel.toggleMute() }
                            .testTag("level_select_mute_button")
                    ) {
                        Box(modifier = Modifier.padding(6.dp), contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = if (isMuted) Icons.Default.VolumeMute else Icons.Default.VolumeUp,
                                contentDescription = "Toggle Mute",
                                tint = if (isMuted) Color(0xFFEF4444) else Color(0xFFBAE6FD),
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
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    // Hero Banner Card
                    LevelSelectHeroCard(
                        totalStars = totalStarsEarned,
                        completedLevels = bestRecordsByScenario.size,
                        totalLevels = PuzzleScenarios.ALL.size,
                        onOpenGrid = { showQuickGrid = true }
                    )
                }

                // Chapter Filter Chips
                item {
                    Column {
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

                        Spacer(modifier = Modifier.height(8.dp))

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(LevelFilter.values()) { filter ->
                                val isSelected = selectedFilter == filter
                                WoodFilterPill(
                                    selected = isSelected,
                                    onClick = { selectedFilter = filter },
                                    text = filter.title
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
                        },
                        onUnlockWithAd = {
                            scenarioToUnlock = scenario
                        },
                        onPreviewStory = {
                            storyScenarioToPreview = scenario
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
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
            colors = CardDefaults.cardColors(containerColor = WoodCardBg),
            border = BorderStroke(1.5.dp, Color(0xFF38BDF8).copy(alpha = 0.7f)),
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
                            colors = listOf(Color(0xF00D3B73), Color(0xF0072449))
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
                            color = Color.White
                        )
                        Text(
                            text = "Tap any level (1 - ${PuzzleScenarios.ALL.size}) to set sail",
                            fontSize = 11.sp,
                            color = Color(0xFFBAE6FD)
                        )
                    }
                    Surface(
                        shape = CircleShape,
                        color = WoodInsetPanel,
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

                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = when {
                                    isCleared -> Color(0xFF0F4D8F)
                                    isUnlocked -> Color(0xFF0C3565)
                                    else -> Color(0x66081E3B)
                                },
                                border = BorderStroke(
                                    1.2.dp,
                                    when {
                                        isCleared -> GoldenBankGlow
                                        isUnlocked -> Color(0xFF38BDF8).copy(alpha = 0.7f)
                                        else -> Color(0x3338BDF8)
                                    }
                                ),
                                modifier = Modifier
                                    .size(54.dp)
                                    .clickable { onSelectLevel(scenario) }
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
                                            tint = Color(0xFF64748B),
                                            modifier = Modifier.size(15.dp)
                                        )
                                        Text(
                                            text = "${scenario.levelNumber}",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp,
                                            color = Color(0xFF64748B)
                                        )
                                    } else {
                                        Text(
                                            text = "${scenario.levelNumber}",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = if (isCleared) GoldenBankGlow else Color.White
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

@Composable
private fun LevelSelectHeroCard(
    totalStars: Int,
    completedLevels: Int,
    totalLevels: Int,
    onOpenGrid: () -> Unit,
    modifier: Modifier = Modifier
) {
    WoodCard(
        shape = RoundedCornerShape(20.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Ferry Master Academy",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 17.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "Transport passengers across the river without leaving predator and prey alone together.",
                    fontSize = 12.sp,
                    color = Color(0xFFBAE6FD),
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    WoodInsetBox(shape = RoundedCornerShape(10.dp)) {
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
                                text = "$totalStars / ${totalLevels * 3} Stars",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldenBankGlow
                            )
                        }
                    }

                    WoodInsetBox(shape = RoundedCornerShape(10.dp)) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "🏆 $completedLevels/$totalLevels Solved",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Surface(
                shape = CircleShape,
                color = WoodInsetPanel,
                border = BorderStroke(2.dp, Color(0xFF38BDF8).copy(alpha = 0.8f)),
                modifier = Modifier
                    .size(62.dp)
                    .clickable { onOpenGrid() },
                shadowElevation = 4.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.img_farmer),
                        contentDescription = "Farmer",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
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
    onUnlockWithAd: () -> Unit,
    onPreviewStory: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isRulesExpanded by remember { mutableStateOf(false) }

    WoodCard(
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .fillMaxWidth()
            .alpha(if (isUnlocked) 1f else 0.88f)
            .testTag("scenario_card_${scenario.id}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header Row: Level Number + Difficulty Badge + Lock / Capacity Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (isUnlocked) WoodInsetPanel else Color(0x66081E3B),
                        border = BorderStroke(1.dp, if (isUnlocked) GoldenBankGlow else Color(0x3338BDF8)),
                        modifier = Modifier.size(26.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            if (!isUnlocked) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "Locked",
                                    tint = Color(0xFF64748B),
                                    modifier = Modifier.size(14.dp)
                                )
                            } else {
                                Text(
                                    text = "${scenario.levelNumber}",
                                    color = GoldenBankGlow,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(scenario.difficulty.badgeColorHex)
                    ) {
                        Text(
                            text = "${scenario.difficulty.iconEmoji} ${scenario.difficulty.title}",
                            color = Color(scenario.difficulty.badgeTextColorHex),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                // Boat capacity pill or Locked status tag
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isUnlocked) WoodInsetPanel else Color(0x66081E3B),
                    border = BorderStroke(1.dp, if (isUnlocked) Color(0xFF38BDF8).copy(alpha = 0.5f) else Color(0x3338BDF8))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (!isUnlocked) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Locked",
                                tint = Color(0xFFEF4444),
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "LOCKED",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFEF4444)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.DirectionsBoat,
                                contentDescription = "Boat",
                                tint = Color(0xFFBAE6FD),
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Boat Cap: ${scenario.boatCapacity + 1}",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFFBAE6FD)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Thematic Scenic Biome Preview Banner
            val theme = remember(scenario.levelNumber) { LevelTheme.forScenario(scenario) }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(84.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .border(1.2.dp, Color(0xFF38BDF8).copy(alpha = 0.5f), RoundedCornerShape(14.dp))
            ) {
                Image(
                    painter = painterResource(id = theme.backgroundDrawableRes),
                    contentDescription = theme.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0x15000000),
                                    Color(0x95000000)
                                )
                            )
                        )
                )
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = theme.badgeBgColor.copy(alpha = 0.92f),
                    border = BorderStroke(1.dp, theme.bankAccentColor.copy(alpha = 0.65f)),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${theme.iconEmoji} ${theme.name}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = theme.badgeTextColor
                        )
                        Text(
                            text = " • ${theme.tagline}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = theme.badgeTextColor.copy(alpha = 0.85f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Title and Subtitle
            Text(
                text = scenario.title,
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
            Text(
                text = scenario.subtitle,
                fontSize = 12.sp,
                color = Color(0xFFBAE6FD)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Items avatar chip row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Items:",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    scenario.items.forEach { item ->
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = WoodInsetPanel,
                            border = BorderStroke(1.dp, Color(0xFF38BDF8).copy(alpha = 0.4f))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = item.drawableRes),
                                    contentDescription = item.displayName,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(18.dp)
                                        .clip(CircleShape)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = item.displayName,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Target moves & Best score banner
            WoodInsetBox(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🎯 Optimal: ${scenario.optimalMoves} moves",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = GoldenBankGlow
                    )

                    if (bestRecordTime != null && bestRecordMoves != null) {
                        Text(
                            text = "⭐ $starsEarned  ⏱️ ${bestRecordTime}s",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldenBankGlow
                        )
                    } else {
                        Text(
                            text = "Not Cleared",
                            fontSize = 11.sp,
                            color = Color(0xFF94A3B8),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Expandable rules section toggle & Comic Story button
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { isRulesExpanded = !isRulesExpanded }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Rules",
                            tint = Color(0xFFBAE6FD),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isRulesExpanded) "Hide Rules" else "Rules & Danger",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFBAE6FD)
                        )
                    }
                    Icon(
                        imageVector = if (isRulesExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = Color(0xFFBAE6FD),
                        modifier = Modifier.size(16.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = WoodInsetPanel,
                    border = BorderStroke(1.dp, GoldenBankGlow.copy(alpha = 0.6f)),
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable(onClick = onPreviewStory)
                        .testTag("preview_story_${scenario.id}")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "📖 Story Comic",
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldenBankGlow
                        )
                    }
                }
            }

            AnimatedVisibility(visible = isRulesExpanded) {
                WoodInsetBox(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "RULES:",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldenBankGlow
                        )
                        scenario.rules.forEach { rule ->
                            Text(
                                text = "• $rule",
                                fontSize = 11.sp,
                                color = Color.White,
                                lineHeight = 14.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "DANGER PAIRS:",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFF7A7A)
                        )
                        scenario.dangerRules.forEach { danger ->
                            Text(
                                text = "${danger.emoji} ${danger.predator.displayName} + ${danger.prey.displayName}",
                                fontSize = 11.sp,
                                color = Color(0xFFFDE8E8)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (isUnlocked) {
                // Play Button
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0xFF0284C7),
                    border = BorderStroke(1.5.dp, GoldenBankGlow),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .clickable(onClick = onPlay)
                        .testTag("play_level_${scenario.id}")
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFF0284C7),
                                        Color(0xFF0369A1)
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play",
                                tint = GoldenBankGlow,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "SET SAIL • LEVEL ${scenario.levelNumber}",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 13.sp,
                                color = Color.White,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }
                }
            } else {
                // Locked State: Tap to Unlock with Rewarded Ad Button
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0xFF78350F),
                    border = BorderStroke(1.5.dp, GoldenBankGlow),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .clickable(onClick = onUnlockWithAd)
                        .testTag("unlock_level_${scenario.id}")
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFF92400E),
                                        Color(0xFF78350F)
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.OndemandVideo,
                                contentDescription = "Unlock with Video Ad",
                                tint = GoldenBankGlow,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "WATCH AD TO UNLOCK • LEVEL ${scenario.levelNumber}",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 12.sp,
                                color = GoldenBankGlow,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
