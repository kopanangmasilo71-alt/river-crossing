package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.db.HighScoreEntity
import com.example.model.PuzzleScenario
import com.example.model.PuzzleScenarios
import com.example.ui.components.WoodCard
import com.example.ui.components.WoodFilterPill
import com.example.ui.components.WoodInsetBox
import com.example.ui.components.WoodScreenContainer
import com.example.ui.components.WoodTopAppBar
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
import com.example.ui.theme.MenuBtnRedTop
import com.example.ui.theme.MenuBtnRedMid
import com.example.ui.theme.MenuBtnRedBottom
import com.example.ui.theme.MenuBtnRedBorder
import com.example.ui.theme.WoodButtonBottom
import com.example.ui.theme.WoodButtonTop
import com.example.ui.theme.WoodGoldenText
import com.example.ui.theme.WoodInsetPanel
import com.example.ui.theme.WoodSignboardBorder
import com.example.ui.theme.WoodSignboardDark
import com.example.ui.theme.WoodSignboardLight
import com.example.ui.theme.WoodTextMuted
import com.example.viewmodel.RiverGameViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(
    viewModel: RiverGameViewModel,
    onBack: () -> Unit,
    onPlayScenario: (PuzzleScenario) -> Unit,
    modifier: Modifier = Modifier
) {
    val highScores by viewModel.highScores.collectAsState()
    val recentHistory by viewModel.recentHistory.collectAsState()
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var selectedFilter by remember { mutableStateOf(LevelFilter.ALL) }
    var showClearConfirmDialog by remember { mutableStateOf(false) }

    // Map best score per scenario by least moves
    val bestScoresByScenario = remember(highScores) {
        PuzzleScenarios.ALL.associate { scenario ->
            scenario.id to highScores.filter { it.levelId == scenario.id }.minByOrNull { it.movesCount }
        }
    }

    val totalStars = remember(bestScoresByScenario) {
        bestScoresByScenario.values.filterNotNull().sumOf { it.stars }
    }
    val clearedCount = remember(bestScoresByScenario) {
        bestScoresByScenario.values.count { it != null }
    }
    val perfectCount = remember(bestScoresByScenario) {
        bestScoresByScenario.count { (scenarioId, score) ->
            val scenario = PuzzleScenarios.getById(scenarioId)
            score != null && scenario != null && score.movesCount <= scenario.optimalMoves
        }
    }

    val filteredScenarios = remember(selectedFilter) {
        PuzzleScenarios.ALL.filter { it.levelNumber in selectedFilter.minLvl..selectedFilter.maxLvl }
    }

    WoodScreenContainer(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            WoodTopAppBar(
                title = "High Scores",
                subtitle = "Hall of Fame • Least Moves",
                onBack = onBack,
                actions = {
                    if (highScores.isNotEmpty()) {
                        IconButton(
                            onClick = { showClearConfirmDialog = true },
                            modifier = Modifier.testTag("leaderboard_clear_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.DeleteOutline,
                                contentDescription = "Clear Records",
                                tint = GoldenBankGlow
                            )
                        }
                    }
                }
            )

        // OVERALL STATS HEADER CARD (Glossy multi-color stat capsules matching Main Menu)
        WoodCard(
            shape = RoundedCornerShape(20.dp),
            gradientColors = listOf(MenuStatBarBgTop, MenuStatBarBgBottom),
            borderColor = MenuStatBarBorder,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .testTag("leaderboard_stats_card")
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Capsule 1: Total Stars (Golden Amber)
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xDD2A1506),
                    border = BorderStroke(1.2.dp, MenuBtnAmberBorder),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = GoldenBankGlow,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "$totalStars",
                                fontWeight = FontWeight.Black,
                                fontSize = 15.sp,
                                color = Color.White
                            )
                        }
                        Text(
                            text = "TOTAL STARS",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = GoldenBankGlow,
                            letterSpacing = 0.5.sp
                        )
                    }
                }

                // Capsule 2: Levels Cleared (Lush Emerald)
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xDD0D2612),
                    border = BorderStroke(1.2.dp, MenuBtnGreenBorder),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "🏆",
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "$clearedCount/${PuzzleScenarios.ALL.size}",
                                fontWeight = FontWeight.Black,
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        }
                        Text(
                            text = "CLEARED",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF4ADE80),
                            letterSpacing = 0.5.sp
                        )
                    }
                }

                // Capsule 3: Optimal Solves (Royal Amethyst)
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xDD230B33),
                    border = BorderStroke(1.2.dp, MenuBtnPurpleBorder),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "👑",
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "$perfectCount",
                                fontWeight = FontWeight.Black,
                                fontSize = 15.sp,
                                color = Color.White
                            )
                        }
                        Text(
                            text = "OPTIMAL",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFFC084FC),
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }
        }

        // TABS: 50 Levels Matrix vs Match History
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            WoodFilterPill(
                text = "${PuzzleScenarios.ALL.size} Levels Matrix",
                selected = selectedTabIndex == 0,
                onClick = { selectedTabIndex = 0 },
                activeGradient = listOf(MenuBtnPurpleTop, MenuBtnPurpleMid, MenuBtnPurpleBottom),
                activeBorder = MenuBtnPurpleBorder,
                modifier = Modifier.weight(1f)
            )
            WoodFilterPill(
                text = "Match History (${recentHistory.size})",
                selected = selectedTabIndex == 1,
                onClick = { selectedTabIndex = 1 },
                activeGradient = listOf(MenuBtnAmberTop, MenuBtnAmberMid, MenuBtnAmberBottom),
                activeBorder = MenuBtnAmberBorder,
                modifier = Modifier.weight(1f)
            )
        }

        if (selectedTabIndex == 0) {
            // FILTER CHIPS FOR 50 LEVELS
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                items(LevelFilter.entries) { filter ->
                    WoodFilterPill(
                        text = filter.title,
                        selected = selectedFilter == filter,
                        onClick = { selectedFilter = filter },
                        activeGradient = filter.gradient,
                        activeBorder = filter.border
                    )
                }
            }

            // 30 LEVELS LEAST MOVES LIST
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredScenarios) { scenario ->
                    val bestScore = bestScoresByScenario[scenario.id]
                    LevelLeaderboardCard(
                        scenario = scenario,
                        bestScore = bestScore,
                        onPlay = { onPlayScenario(scenario) }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        } else {
            // RECENT HISTORY LIST
            if (recentHistory.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = null,
                            tint = WoodGoldenText,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No Match History Yet",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 16.sp,
                            color = WoodGoldenText
                        )
                        Text(
                            text = "Play any of the ${PuzzleScenarios.ALL.size} levels to record your least moves in the logbook!",
                            fontSize = 12.sp,
                            color = WoodTextMuted,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(recentHistory) { record ->
                        val scenario = PuzzleScenarios.getById(record.levelId) ?: PuzzleScenarios.CLASSIC
                        HistoryItemCard(record = record, scenario = scenario)
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
    }

    if (showClearConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showClearConfirmDialog = false },
            containerColor = Color(0xF4281206),
            title = {
                Text(
                    "Reset Leaderboard Records?",
                    fontWeight = FontWeight.ExtraBold,
                    color = GoldenBankGlow
                )
            },
            text = {
                Text(
                    "This will remove all saved runs and high score records from the local Room database. Are you sure?",
                    color = Color(0xFFFEF3C7)
                )
            },
            confirmButton = {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MenuBtnRedTop,
                    border = BorderStroke(1.2.dp, MenuBtnRedBorder),
                    modifier = Modifier.clickable {
                        viewModel.clearHighScoreHistory(null)
                        showClearConfirmDialog = false
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .background(Brush.verticalGradient(listOf(MenuBtnRedTop, MenuBtnRedMid, MenuBtnRedBottom)))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text("Clear All", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearConfirmDialog = false }) {
                    Text("Cancel", color = Color(0xFFFEF3C7))
                }
            }
        )
    }
}

@Composable
private fun LevelLeaderboardCard(
    scenario: PuzzleScenario,
    bestScore: HighScoreEntity?,
    onPlay: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isCleared = bestScore != null
    val isOptimal = bestScore != null && bestScore.movesCount <= scenario.optimalMoves

    val (cardGradient, cardBorder) = when (scenario.difficulty) {
        com.example.model.PuzzleDifficulty.NORMAL -> listOf(Color(0xF012351A), Color(0xF00A200F)) to MenuBtnGreenBorder
        com.example.model.PuzzleDifficulty.MEDIUM -> listOf(Color(0xF00D3560), Color(0xF007203A)) to MenuBtnBlueBorder
        com.example.model.PuzzleDifficulty.HARD -> listOf(Color(0xF04A2A08), Color(0xF02C1704)) to MenuBtnAmberBorder
        com.example.model.PuzzleDifficulty.EXPERT -> listOf(Color(0xF032124A), Color(0xF01D092B)) to MenuBtnPurpleBorder
    }

    WoodCard(
        shape = RoundedCornerShape(16.dp),
        gradientColors = cardGradient,
        borderColor = cardBorder,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onPlay() }
            .testTag("leaderboard_level_${scenario.levelNumber}_card")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Level Number badge
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xDD220F05),
                border = BorderStroke(1.2.dp, if (isOptimal) GoldenBankGlow else cardBorder),
                modifier = Modifier.size(46.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${scenario.levelNumber}",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 14.sp,
                            color = if (isOptimal) GoldenBankGlow else Color.White
                        )
                        Text(
                            text = scenario.difficulty.iconEmoji,
                            fontSize = 9.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Level Info & Stats
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = scenario.title,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                    if (isOptimal) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xDD220F05),
                            border = BorderStroke(1.dp, GoldenBankGlow)
                        ) {
                            Text(
                                text = "★ OPTIMAL",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldenBankGlow,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(3.dp))

                if (bestScore != null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "🏆 Least: ${bestScore.movesCount} (Goal: ${scenario.optimalMoves})",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = GoldenBankGlow
                        )
                        Text(
                            text = "⏱️ ${bestScore.timeSeconds}s",
                            fontSize = 11.sp,
                            color = Color(0xFFFEF3C7)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Text(
                            text = "⭐".repeat(bestScore.stars) + "☆".repeat(3 - bestScore.stars),
                            fontSize = 11.sp,
                            color = GoldenBankGlow
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "By ${bestScore.playerName}",
                            fontSize = 10.sp,
                            color = Color(0xFFFEF3C7)
                        )
                    }
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Optimal Target: ${scenario.optimalMoves} moves",
                            fontSize = 11.sp,
                            color = Color(0xFFD4A373)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "• Unplayed",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFD4A373)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Surface(
                shape = CircleShape,
                color = MenuBtnGreenTop,
                border = BorderStroke(1.2.dp, MenuBtnGreenBorder),
                modifier = Modifier.size(36.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.verticalGradient(listOf(MenuBtnGreenTop, MenuBtnGreenMid, MenuBtnGreenBottom))),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun HistoryItemCard(
    record: HighScoreEntity,
    scenario: PuzzleScenario,
    modifier: Modifier = Modifier
) {
    val dateStr = remember(record.timestamp) {
        val sdf = SimpleDateFormat("MMM d, HH:mm", Locale.getDefault())
        sdf.format(Date(record.timestamp))
    }

    WoodCard(
        shape = RoundedCornerShape(14.dp),
        gradientColors = listOf(Color(0xF03B1C08), Color(0xF0241004)),
        borderColor = if (record.isOptimal) GoldenBankGlow else Color(0xFFD97706),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xDD220F05),
                border = BorderStroke(1.2.dp, if (record.isOptimal) GoldenBankGlow else Color(0xFFD97706)),
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "L${scenario.levelNumber}",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 12.sp,
                        color = GoldenBankGlow
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = scenario.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = Color.White
                    )
                    Text(
                        text = dateStr,
                        fontSize = 10.sp,
                        color = Color(0xFFFEF3C7)
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Moves: ${record.movesCount} (${if (record.isOptimal) "★ Optimal" else "Target: ${scenario.optimalMoves}"})",
                        fontSize = 11.sp,
                        fontWeight = if (record.isOptimal) FontWeight.Bold else FontWeight.Normal,
                        color = if (record.isOptimal) GoldenBankGlow else Color.White
                    )
                    Text(
                        text = "⏱️ ${record.timeSeconds}s",
                        fontSize = 11.sp,
                        color = Color(0xFFFEF3C7)
                    )
                    Text(
                        text = "★".repeat(record.stars),
                        fontSize = 11.sp,
                        color = GoldenBankGlow
                    )
                }
            }
        }
    }
}
