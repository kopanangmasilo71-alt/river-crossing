package com.example.ui

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.material.icons.filled.Tune
import kotlinx.coroutines.delay
import com.example.model.DifficultyMode
import com.example.model.GameStatus
import com.example.model.LevelTheme
import com.example.model.PuzzleScenarios
import com.example.ads.AdManager
import com.example.ads.AdMobBanner
import android.app.Activity
import androidx.compose.ui.platform.LocalContext
import com.example.navigation.ScreenDestination
import com.example.ui.components.CelebrationConfettiOverlay
import com.example.ui.components.ComicVignetteDialog
import com.example.ui.components.DifficultySelectorDialog
import com.example.ui.components.GameOverDialog
import com.example.ui.components.HighScoresDialog
import com.example.ui.components.RiverScene
import com.example.ui.components.rememberGameHaptics
import com.example.ui.components.RulesDialog
import com.example.ui.components.StateGraphVisualizerDialog
import com.example.ui.components.VictoryDialog
import com.example.ui.components.WoodActionButton
import com.example.ui.components.WoodCargoBadge
import com.example.ui.components.WoodSetSailButton
import com.example.ui.components.WoodSignboardHeader
import com.example.ui.components.formatTime
import com.example.ui.theme.VibrantBackground
import com.example.ui.theme.VibrantPrimary
import com.example.ui.theme.VibrantPrimaryContainer
import com.example.ui.theme.VibrantRiverCanvasFrame
import com.example.ui.theme.VibrantSurfaceBorder
import com.example.ui.theme.VibrantSurfaceVariant
import com.example.ui.theme.VibrantTextPrimary
import com.example.ui.theme.VibrantTextSecondary
import com.example.ui.theme.WoodButtonBorder
import com.example.ui.theme.WoodGoldenText
import com.example.ui.theme.WoodInsetPanel
import com.example.ui.theme.RiverDeepBlueDark
import com.example.ui.theme.RiverDeepBlueMid
import com.example.ui.theme.RiverTimberBorder
import com.example.ui.theme.GoldenBankGlow
import com.example.ui.theme.WoodSignboardBg
import com.example.ui.theme.WoodSignboardDark
import com.example.viewmodel.RiverGameViewModel
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Warning
import androidx.compose.foundation.BorderStroke

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RiverGameScreen(
    viewModel: RiverGameViewModel,
    modifier: Modifier = Modifier
) {
    val destination by viewModel.screenDestination.collectAsStateWithLifecycle()

    AnimatedContent(
        targetState = destination,
        transitionSpec = {
            when {
                // Entering Gameplay (from MainMenu, LevelSelect, or Tutorial)
                targetState is ScreenDestination.GamePlay -> {
                    (scaleIn(initialScale = 0.90f, animationSpec = tween(380, easing = FastOutSlowInEasing)) +
                        slideInVertically(animationSpec = tween(380, easing = FastOutSlowInEasing)) { it / 6 } +
                        fadeIn(animationSpec = tween(300, easing = LinearEasing)))
                        .togetherWith(
                            scaleOut(targetScale = 1.08f, animationSpec = tween(320, easing = FastOutSlowInEasing)) +
                                fadeOut(animationSpec = tween(260, easing = LinearEasing))
                        )
                }
                // Exiting Gameplay (back to MainMenu, LevelSelect, etc.)
                initialState is ScreenDestination.GamePlay -> {
                    (scaleIn(initialScale = 1.08f, animationSpec = tween(340, easing = FastOutSlowInEasing)) +
                        fadeIn(animationSpec = tween(280, easing = LinearEasing)))
                        .togetherWith(
                            scaleOut(targetScale = 0.90f, animationSpec = tween(300, easing = FastOutSlowInEasing)) +
                                slideOutVertically(animationSpec = tween(300, easing = FastOutSlowInEasing)) { it / 6 } +
                                fadeOut(animationSpec = tween(240, easing = LinearEasing))
                        )
                }
                // Forward navigation from MainMenu to sub-screens (LevelSelect, Settings, Leaderboard, Tutorial)
                initialState is ScreenDestination.MainMenu && targetState !is ScreenDestination.MainMenu -> {
                    (slideInHorizontally(animationSpec = tween(340, easing = FastOutSlowInEasing)) { it } +
                        fadeIn(animationSpec = tween(260, easing = LinearEasing)))
                        .togetherWith(
                            slideOutHorizontally(animationSpec = tween(340, easing = FastOutSlowInEasing)) { -it / 3 } +
                                fadeOut(animationSpec = tween(220, easing = LinearEasing))
                        )
                }
                // Returning back to MainMenu from sub-screens
                initialState !is ScreenDestination.MainMenu && targetState is ScreenDestination.MainMenu -> {
                    (slideInHorizontally(animationSpec = tween(340, easing = FastOutSlowInEasing)) { -it / 3 } +
                        fadeIn(animationSpec = tween(260, easing = LinearEasing)))
                        .togetherWith(
                            slideOutHorizontally(animationSpec = tween(340, easing = FastOutSlowInEasing)) { it } +
                                fadeOut(animationSpec = tween(220, easing = LinearEasing))
                        )
                }
                // Lateral navigation between sub-screens (e.g. LevelSelect <-> Settings)
                else -> {
                    (slideInHorizontally(animationSpec = tween(320, easing = FastOutSlowInEasing)) { it / 2 } +
                        fadeIn(animationSpec = tween(260, easing = LinearEasing)))
                        .togetherWith(
                            slideOutHorizontally(animationSpec = tween(320, easing = FastOutSlowInEasing)) { -it / 2 } +
                                fadeOut(animationSpec = tween(220, easing = LinearEasing))
                        )
                }
            }
        },
        label = "screen_navigation"
    ) { currentDestination ->
        when (currentDestination) {
            is ScreenDestination.MainMenu -> {
                MainMenuScreen(
                    viewModel = viewModel,
                    onNavigateToLevels = { viewModel.navigateToLevelSelect() },
                    onNavigateToSettings = { viewModel.navigateToSettings() },
                    onNavigateToLeaderboard = { viewModel.navigateToLeaderboard() },
                    onNavigateToTutorial = { viewModel.navigateToTutorial() },
                    modifier = modifier
                )
            }
            is ScreenDestination.Tutorial -> {
                TutorialScreen(
                    onBack = { viewModel.navigateToMainMenu() },
                    onStartGame = { viewModel.selectScenarioAndPlay(PuzzleScenarios.CLASSIC) },
                    modifier = modifier
                )
            }
            is ScreenDestination.LevelSelect -> {
                LevelSelectScreen(
                    viewModel = viewModel,
                    onSelectScenario = { scenario ->
                        viewModel.selectScenario(scenario)
                    },
                    onNavigateToMainMenu = { viewModel.navigateToMainMenu() },
                    onNavigateToSettings = { viewModel.navigateToSettings() },
                    modifier = modifier
                )
            }
            is ScreenDestination.Settings -> {
                SettingsScreen(
                    viewModel = viewModel,
                    onBack = { viewModel.navigateBackFromSettings() },
                    modifier = modifier
                )
            }
            is ScreenDestination.Leaderboard -> {
                LeaderboardScreen(
                    viewModel = viewModel,
                    onBack = { viewModel.navigateToMainMenu() },
                    onPlayScenario = { scenario ->
                        viewModel.selectScenario(scenario)
                    },
                    modifier = modifier
                )
            }
            is ScreenDestination.GamePlay -> {
                GameplayScreenContent(
                    viewModel = viewModel,
                    modifier = modifier
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GameplayScreenContent(
    viewModel: RiverGameViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val riverState by viewModel.riverState.collectAsStateWithLifecycle()
    val gameStatus by viewModel.gameStatus.collectAsStateWithLifecycle()
    val moveCount by viewModel.moveCount.collectAsStateWithLifecycle()
    val moveHistory by viewModel.moveHistory.collectAsStateWithLifecycle()
    val activeViolation by viewModel.activeViolation.collectAsStateWithLifecycle()
    val isMuted by viewModel.isMuted.collectAsStateWithLifecycle()
    val bestMoves by viewModel.bestMoves.collectAsStateWithLifecycle()
    val hintMessage by viewModel.hintMessage.collectAsStateWithLifecycle()
    val highlightedHintItem by viewModel.highlightedHintItem.collectAsStateWithLifecycle()
    val isHintHighlightingBoat by viewModel.isHintHighlightingBoat.collectAsStateWithLifecycle()
    val isAutoSolving by viewModel.isAutoSolving.collectAsStateWithLifecycle()
    val boatProgress by viewModel.boatPosition.collectAsStateWithLifecycle()
    val splashEvent by viewModel.splashEvent.collectAsStateWithLifecycle()
    val boatFullAlert by viewModel.boatFullAlert.collectAsStateWithLifecycle()

    val highScores by viewModel.highScores.collectAsStateWithLifecycle()
    val bestTimeSeconds by viewModel.bestTimeSeconds.collectAsStateWithLifecycle()
    val elapsedSeconds by viewModel.elapsedSeconds.collectAsStateWithLifecycle()
    val isTimerRunning by viewModel.isTimerRunning.collectAsStateWithLifecycle()
    val isNewBestTime by viewModel.isNewBestTime.collectAsStateWithLifecycle()
    val difficultyModifiers by viewModel.difficultyModifiers.collectAsStateWithLifecycle()
    val isHapticsEnabled by viewModel.isHapticsEnabled.collectAsStateWithLifecycle()
    val crossingSuccessEvent by viewModel.crossingSuccessEvent.collectAsStateWithLifecycle()

    val gameHaptics = rememberGameHaptics(isEnabled = isHapticsEnabled)

    LaunchedEffect(crossingSuccessEvent) {
        if (crossingSuccessEvent != null) {
            gameHaptics.onRiverCrossingSuccess()
        }
    }

    var showRulesDialog by remember { mutableStateOf(false) }
    var showTutorialDialog by remember { mutableStateOf(false) }
    var showGraphDialog by remember { mutableStateOf(false) }
    var showLeaderboard by remember { mutableStateOf(false) }
    var showDifficultyDialog by remember { mutableStateOf(false) }
    var showComicVignette by remember { mutableStateOf(false) }
    var lastAutoPromptedLevelId by rememberSaveable { mutableStateOf<String?>(null) }
    var showVictoryDialog by remember { mutableStateOf(false) }

    // Sequence celebration: show river transfer joy, fanfare, & cannons first, then show victory dialog
    // Ensuring confetti animation does not run in background behind the dialog
    LaunchedEffect(gameStatus) {
        if (gameStatus == GameStatus.VICTORY) {
            showVictoryDialog = false
            delay(1200L)
            showVictoryDialog = true
        } else {
            showVictoryDialog = false
        }
    }

    val currentScenario = riverState.scenario
    val levelTheme = remember(currentScenario.levelNumber) {
        LevelTheme.forScenario(currentScenario)
    }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    BackHandler {
        viewModel.navigateToLevelSelect()
    }

    // Dynamic story vignettes: only auto-shown on first introduction, disabled for long-term users
    LaunchedEffect(currentScenario.id) {
        if (lastAutoPromptedLevelId != currentScenario.id) {
            if (viewModel.shouldAutoShowStoryVignette(currentScenario.id)) {
                showComicVignette = true
            }
            lastAutoPromptedLevelId = currentScenario.id
        }
    }

    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        levelTheme.waterGradientTop,
                        levelTheme.waterGradientBottom
                    )
                )
            )
    ) {
        // Fullscreen level theme background image matching the game panel
        Image(
            painter = painterResource(id = levelTheme.backgroundDrawableRes),
            contentDescription = "${levelTheme.name} Fullscreen Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Thematic atmospheric color grading overlay matching the level theme
        if (levelTheme.atmosphericOverlayColor != Color.Transparent) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(levelTheme.atmosphericOverlayColor)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = if (isLandscape) 12.dp else 10.dp,
                    vertical = if (isLandscape) 4.dp else 8.dp
                ),
            verticalArrangement = Arrangement.spacedBy(if (isLandscape) 4.dp else 8.dp)
        ) {
            // 1. Top Rustic Wood Signboard Header (Full header in portrait, ultra-compact header in landscape)
            if (!isLandscape) {
                WoodSignboardHeader(
                    scenario = currentScenario,
                    moveCount = moveCount,
                    elapsedSeconds = elapsedSeconds,
                    isMuted = isMuted,
                    onBack = { viewModel.navigateToLevelSelect() },
                    onOpenTutorial = { showComicVignette = true },
                    onOpenRules = { showRulesDialog = true },
                    onToggleMute = { viewModel.toggleMute() },
                    onOpenModifiers = { showDifficultyDialog = true },
                    levelTheme = levelTheme
                )
            } else {
                CompactLandscapeHeader(
                    scenario = currentScenario,
                    moveCount = moveCount,
                    elapsedSeconds = elapsedSeconds,
                    isMuted = isMuted,
                    onBack = { viewModel.navigateToLevelSelect() },
                    onOpenStory = { showComicVignette = true },
                    onOpenRules = { showRulesDialog = true },
                    onToggleMute = { viewModel.toggleMute() },
                    levelTheme = levelTheme
                )
            }

            // 2. Maximized River Scene (Takes all remaining screen height)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                RiverScene(
                    riverState = riverState,
                    boatProgress = boatProgress,
                    isRowing = gameStatus == GameStatus.ROWING,
                    splashEvent = splashEvent,
                    highlightedHintItem = highlightedHintItem,
                    isHintHighlightingBoat = isHintHighlightingBoat,
                    gameHaptics = gameHaptics,
                    onItemClick = { viewModel.toggleItem(it) },
                    isVictory = gameStatus == GameStatus.VICTORY,
                    theme = levelTheme,
                    modifier = Modifier.fillMaxSize()
                )

                // Prominent Alert Banner when boat is full or invalid interaction
                androidx.compose.animation.AnimatedVisibility(
                    visible = boatFullAlert != null,
                    enter = fadeIn(tween(200)) + slideInVertically(tween(220)) { -it },
                    exit = fadeOut(tween(180)) + slideOutVertically(tween(200)) { -it },
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 10.dp, start = 14.dp, end = 14.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = levelTheme.headerSurfaceColor.copy(alpha = 0.75f),
                        border = BorderStroke(1.5.dp, levelTheme.headerAccentColor),
                        shadowElevation = 4.dp,
                        modifier = Modifier.testTag("boat_full_alert_banner")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 9.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = levelTheme.headerAccentColor.copy(alpha = 0.25f),
                                modifier = Modifier.size(26.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Warning,
                                        contentDescription = null,
                                        tint = levelTheme.headerAccentColor,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = boatFullAlert ?: "",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = levelTheme.headerTextColor,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = { viewModel.dismissBoatFullAlert() },
                                modifier = Modifier.size(22.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Dismiss Alert",
                                    tint = levelTheme.headerAccentColor,
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                        }
                    }
                }

                // Floating AI Hint bubble if active
                androidx.compose.animation.AnimatedVisibility(
                    visible = hintMessage != null,
                    enter = fadeIn(tween(220)) + slideInVertically(spring(dampingRatio = 0.6f, stiffness = 480f)) { -it },
                    exit = fadeOut(tween(180)) + slideOutVertically(tween(200)) { -it },
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 8.dp, start = 12.dp, end = 12.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = levelTheme.headerSurfaceColor.copy(alpha = 0.75f),
                        border = BorderStroke(1.5.dp, levelTheme.headerAccentColor),
                        shadowElevation = 4.dp,
                        modifier = Modifier.testTag("floating_hint_bubble")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "💡", fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = hintMessage ?: "",
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = levelTheme.headerTextColor,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = { viewModel.clearHint() },
                                modifier = Modifier.size(20.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Dismiss Hint",
                                    tint = levelTheme.headerAccentColor,
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                        }
                    }
                }
            }

            // 3. Bottom Wooden UI Controls Overlay
            if (!isLandscape) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // SET SAIL Button (Clean, maximizing gameplay space without noisy alert subtext)
                    WoodSetSailButton(
                        isRowing = gameStatus == GameStatus.ROWING,
                        enabled = gameStatus != GameStatus.GAME_OVER && gameStatus != GameStatus.VICTORY,
                        levelTheme = levelTheme,
                        onClick = { viewModel.crossRiver() }
                    )

                    // Adaptive Action Controls: Essential Reset & Undo, with Hint tool introduced when struggling
                    val isStruggling = viewModel.isPlayerStruggling()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        WoodActionButton(
                            text = "RESET",
                            icon = Icons.Default.Refresh,
                            testTag = "wood_reset_button",
                            levelTheme = levelTheme,
                            onClick = { viewModel.restartGame() },
                            modifier = Modifier.weight(1f)
                        )

                        WoodActionButton(
                            text = "UNDO",
                            icon = Icons.Default.Undo,
                            enabled = moveHistory.isNotEmpty(),
                            testTag = "wood_undo_button",
                            levelTheme = levelTheme,
                            onClick = { viewModel.undoMove() },
                            modifier = Modifier.weight(1f)
                        )

                        if (isStruggling) {
                            WoodActionButton(
                                text = "HINT",
                                icon = Icons.Default.Lightbulb,
                                testTag = "wood_hint_button",
                                levelTheme = levelTheme,
                                onClick = { viewModel.provideHint() },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                // AdMob Banner Ad at the bottom of the portrait gameplay controls with generous top padding to avoid misclicks
                AdMobBanner(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 4.dp)
                )
            } else {
                // In landscape: clean horizontal control row with Reset, Set Sail, Undo, and Hint
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 2.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WoodActionButton(
                        text = "RESET",
                        icon = Icons.Default.Refresh,
                        testTag = "wood_reset_button",
                        levelTheme = levelTheme,
                        onClick = { viewModel.restartGame() },
                        modifier = Modifier.widthIn(max = 110.dp)
                    )

                    WoodSetSailButton(
                        isRowing = gameStatus == GameStatus.ROWING,
                        enabled = gameStatus != GameStatus.GAME_OVER && gameStatus != GameStatus.VICTORY,
                        levelTheme = levelTheme,
                        onClick = { viewModel.crossRiver() },
                        modifier = Modifier
                            .widthIn(max = 240.dp)
                            .padding(bottom = 1.dp)
                    )

                    WoodActionButton(
                        text = "UNDO",
                        icon = Icons.Default.Undo,
                        enabled = moveHistory.isNotEmpty(),
                        testTag = "wood_undo_button",
                        levelTheme = levelTheme,
                        onClick = { viewModel.undoMove() },
                        modifier = Modifier.widthIn(max = 110.dp)
                    )

                    if (viewModel.isPlayerStruggling()) {
                        WoodActionButton(
                            text = "HINT",
                            icon = Icons.Default.Lightbulb,
                            testTag = "wood_hint_button",
                            levelTheme = levelTheme,
                            onClick = { viewModel.provideHint() },
                            modifier = Modifier.widthIn(max = 100.dp)
                        )
                    }
                }
            }
        }

        // Full-screen Victory Celebration Confetti & Sparkling Starburst Overlay
        // Active only in Phase 1 before dialog opens, ensuring confetti NEVER runs in the background behind the dialog!
        CelebrationConfettiOverlay(
            isVictory = gameStatus == GameStatus.VICTORY && !showVictoryDialog,
            showBanner = true,
            theme = levelTheme
        )
    }

    // Game Over Dialog
    if (gameStatus == GameStatus.GAME_OVER && activeViolation != null) {
        GameOverDialog(
            violation = activeViolation!!,
            levelTheme = levelTheme,
            onUndo = { viewModel.undoMove() },
            onRestart = { viewModel.restartGame() }
        )
    }

    // Victory Dialog with Time, Stars & Level Selection
    // Renders celebration confetti directly in the foreground of the dialog!
    if (gameStatus == GameStatus.VICTORY && showVictoryDialog) {
        val nextLevelNumber = currentScenario.levelNumber + 1
        val nextScenario = com.example.model.PuzzleScenarios.ALL.firstOrNull { it.levelNumber == nextLevelNumber }

        VictoryDialog(
            moveCount = moveCount,
            timeSeconds = elapsedSeconds,
            scenario = currentScenario,
            levelTheme = levelTheme,
            isNewBestTime = isNewBestTime,
            bestMoves = bestMoves,
            onPlayAgain = {
                showVictoryDialog = false
                viewModel.restartGame()
            },
            onNextLevel = if (nextScenario != null) {
                {
                    showVictoryDialog = false
                    viewModel.selectScenario(nextScenario)
                }
            } else null,
            onViewLeaderboard = { showLeaderboard = true },
            onSelectLevel = {
                if (activity != null) {
                    AdManager.showInterstitialAd(activity) {
                        viewModel.navigateToLevelSelect()
                    }
                } else {
                    viewModel.navigateToLevelSelect()
                }
            },
            onMainMenu = {
                if (activity != null) {
                    AdManager.showInterstitialAd(activity) {
                        viewModel.navigateToMainMenu()
                    }
                } else {
                    viewModel.navigateToMainMenu()
                }
            },
            onStarPop = { starIndex -> viewModel.playStarPopSound(starIndex) }
        )
    }

    // Hall of Fame Leaderboard Dialog
    if (showLeaderboard) {
        HighScoresDialog(
            highScores = highScores,
            bestTimeSeconds = bestTimeSeconds,
            initialLevelId = currentScenario.id,
            levelTheme = levelTheme,
            onDismiss = { showLeaderboard = false },
            onClearScores = { viewModel.clearHighScoreHistory() }
        )
    }

    // Comic-Style Story Vignette Dialog
    if (showComicVignette) {
        ComicVignetteDialog(
            scenario = currentScenario,
            levelTheme = levelTheme,
            onDismiss = { showComicVignette = false }
        )
    }

    // Rules & Story Dialog
    if (showRulesDialog) {
        RulesDialog(
            scenario = currentScenario,
            levelTheme = levelTheme,
            onDismiss = { showRulesDialog = false }
        )
    }

    // Difficulty Variations Dialog
    if (showDifficultyDialog) {
        DifficultySelectorDialog(
            currentModifiers = difficultyModifiers,
            levelTheme = levelTheme,
            onApplyModifiers = { newMods ->
                viewModel.updateModifiers(newMods)
            },
            onDismiss = { showDifficultyDialog = false }
        )
    }

    // State Graph Visualizer Dialog
    if (showGraphDialog) {
        StateGraphVisualizerDialog(
            currentState = riverState,
            isAutoSolving = isAutoSolving,
            levelTheme = levelTheme,
            onStartAutoSolve = { viewModel.startAutoSolver() },
            onStopAutoSolve = { viewModel.stopAutoSolver() },
            onDismiss = { showGraphDialog = false }
        )
    }
}

/**
 * Ultra-compact, single-row wooden header for landscape mode.
 * Preserves access to Back, Level Info, Moves, Timer, Rules, and Audio without eating vertical space.
 */
@Composable
private fun CompactLandscapeHeader(
    scenario: com.example.model.PuzzleScenario,
    moveCount: Int,
    elapsedSeconds: Long,
    isMuted: Boolean,
    onBack: () -> Unit,
    onOpenStory: () -> Unit,
    onOpenRules: () -> Unit,
    onToggleMute: () -> Unit,
    levelTheme: LevelTheme = LevelTheme.forScenario(scenario),
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.Transparent,
        border = BorderStroke(1.5.dp, levelTheme.headerBorderColors.first().copy(alpha = 0.85f)),
        shadowElevation = 0.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(levelTheme.headerBackgroundColors.map { it.copy(alpha = 0.58f) })
                )
                .padding(horizontal = 8.dp, vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left: Back button + Level info
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = levelTheme.headerSurfaceColor.copy(alpha = 0.50f),
                    border = BorderStroke(1.dp, levelTheme.headerAccentColor.copy(alpha = 0.6f)),
                    modifier = Modifier.clickable(onClick = onBack)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = levelTheme.headerAccentColor,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "LEVELS",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = levelTheme.headerAccentColor
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "LVL ${scenario.levelNumber}: ${scenario.title}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }

            // Right: Moves, Goal, Timer, Rules, Mute
            val optimalMoves = scenario.optimalMoves
            val moveTensionColor = when {
                moveCount == 0 -> Color(0xFFFEF3C7)
                moveCount < optimalMoves -> Color(0xFF86EFAC)
                moveCount == optimalMoves -> Color(0xFFFDE047)
                moveCount <= optimalMoves + 2 -> Color(0xFFFB923C)
                else -> Color(0xFFEF4444)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = levelTheme.headerSurfaceColor.copy(alpha = 0.50f),
                    border = BorderStroke(1.dp, levelTheme.headerAccentColor.copy(alpha = 0.4f))
                ) {
                    Text(
                        text = "MOVES: $moveCount / $optimalMoves",
                        fontSize = 9.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = moveTensionColor,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                val mins = elapsedSeconds / 60
                val secs = elapsedSeconds % 60
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = levelTheme.headerSurfaceColor.copy(alpha = 0.50f),
                    border = BorderStroke(1.dp, levelTheme.headerAccentColor.copy(alpha = 0.4f))
                ) {
                    Text(
                        text = String.format("%02d:%02d", mins, secs),
                        fontSize = 9.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = levelTheme.headerTimerColor,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = levelTheme.headerSurfaceColor.copy(alpha = 0.50f),
                    border = BorderStroke(1.dp, levelTheme.headerAccentColor.copy(alpha = 0.4f)),
                    modifier = Modifier.clickable(onClick = onOpenStory)
                ) {
                    Box(
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "📖",
                            fontSize = 11.sp
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = levelTheme.headerSurfaceColor.copy(alpha = 0.50f),
                    border = BorderStroke(1.dp, levelTheme.headerAccentColor.copy(alpha = 0.4f)),
                    modifier = Modifier.clickable(onClick = onOpenRules)
                ) {
                    Box(
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.HelpOutline,
                            contentDescription = "Rules",
                            tint = levelTheme.headerAccentColor,
                            modifier = Modifier.size(13.dp)
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = levelTheme.headerSurfaceColor.copy(alpha = 0.50f),
                    border = BorderStroke(1.dp, levelTheme.headerAccentColor.copy(alpha = 0.4f)),
                    modifier = Modifier.clickable(onClick = onToggleMute)
                ) {
                    Box(
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isMuted) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
                            contentDescription = if (isMuted) "Unmute" else "Mute",
                            tint = if (isMuted) Color(0xFFEF4444) else levelTheme.headerAccentColor,
                            modifier = Modifier.size(13.dp)
                        )
                    }
                }
            }
        }
    }
}


