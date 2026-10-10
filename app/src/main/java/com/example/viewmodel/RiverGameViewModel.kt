package com.example.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.algorithm.DiscreteState
import com.example.algorithm.GenericDiscreteState
import com.example.algorithm.RiverCrossingSolver
import com.example.audio.GameAudio
import com.example.data.db.AppDatabase
import com.example.data.db.HighScoreEntity
import com.example.data.repository.HighScoreRepository
import com.example.model.Bank
import com.example.model.BoatSpeed
import com.example.model.DifficultyMode
import com.example.model.DifficultyModifiers
import com.example.model.DisembarkJumpEvent
import com.example.model.GameItem
import com.example.model.GameStatus
import com.example.model.ItemLocation
import com.example.model.GameState
import com.example.model.LevelTheme
import com.example.model.MoveRecord
import com.example.model.PuzzleScenario
import com.example.model.PuzzleScenarios
import com.example.model.RiverState
import com.example.model.SplashEvent
import com.example.model.ViolationType
import com.example.model.WeatherEffectType
import com.example.navigation.ScreenDestination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RiverGameViewModel(application: Application) : AndroidViewModel(application) {

    private val audio = GameAudio()
    private val repository = HighScoreRepository(AppDatabase.getInstance(application).highScoreDao())

    val highScores: StateFlow<List<HighScoreEntity>> = repository.highScores
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val recentHistory: StateFlow<List<HighScoreEntity>> = repository.recentHistory
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val completedLevelsCount: StateFlow<Int> = repository.completedLevelCount
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    val totalRunsCount: StateFlow<Int> = repository.totalRunsCount
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    val bestTimeSeconds: StateFlow<Long?> = repository.bestTime
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    private val prefs = application.getSharedPreferences("river_crossing_prefs", Context.MODE_PRIVATE)
    private val isFirstLaunch = !prefs.getBoolean("has_completed_first_launch", false)

    // Unlocked levels state: Level 1 unlocked by default. Further levels can ONLY be unlocked by completing levels below them or via Rewarded Ad.
    private val defaultUnlockedLevels = setOf("level_1_basics")
    private val _unlockedLevelIds = MutableStateFlow<Set<String>>(
        prefs.getStringSet("unlocked_levels", null) ?: defaultUnlockedLevels
    )
    val unlockedLevelIds: StateFlow<Set<String>> = _unlockedLevelIds.asStateFlow()

    // Navigation state: On first-time activation, take the player directly to the game panel
    private val _screenDestination = MutableStateFlow<ScreenDestination>(
        if (isFirstLaunch) ScreenDestination.GamePlay(PuzzleScenarios.CLASSIC) else ScreenDestination.MainMenu
    )
    val screenDestination: StateFlow<ScreenDestination> = _screenDestination.asStateFlow()

    private var previousDestination: ScreenDestination = ScreenDestination.MainMenu

    // Track level retry count to dynamically offer hints when struggling
    private val _levelAttemptCount = MutableStateFlow(0)
    val levelAttemptCount: StateFlow<Int> = _levelAttemptCount.asStateFlow()

    private val _currentScenario = MutableStateFlow(PuzzleScenarios.CLASSIC)
    val currentScenario: StateFlow<PuzzleScenario> = _currentScenario.asStateFlow()

    // Player Profile & Settings
    private val _playerName = MutableStateFlow("Captain River")
    val playerName: StateFlow<String> = _playerName.asStateFlow()

    private val _isHapticsEnabled = MutableStateFlow(true)
    val isHapticsEnabled: StateFlow<Boolean> = _isHapticsEnabled.asStateFlow()

    // Boat crossing speed preference (controlled via Settings)
    private val _boatSpeed = MutableStateFlow(
        BoatSpeed.fromId(prefs.getString("boat_speed", BoatSpeed.FAST.id))
    )
    val boatSpeed: StateFlow<BoatSpeed> = _boatSpeed.asStateFlow()

    fun setBoatSpeed(speed: BoatSpeed) {
        _boatSpeed.value = speed
        prefs.edit().putString("boat_speed", speed.id).apply()
        audio.playButtonPopSound()
    }

    // Difficulty settings and modifiers
    private val _difficultyModifiers = MutableStateFlow(DifficultyModifiers())
    val difficultyModifiers: StateFlow<DifficultyModifiers> = _difficultyModifiers.asStateFlow()

    private val _riverState = MutableStateFlow(RiverState(scenario = PuzzleScenarios.CLASSIC, difficultyModifiers = _difficultyModifiers.value))
    val riverState: StateFlow<RiverState> = _riverState.asStateFlow()

    val gameState: StateFlow<GameState> = _riverState
        .map { it.toGameState() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = _riverState.value.toGameState()
        )

    private val _gameStatus = MutableStateFlow(GameStatus.IDLE)
    val gameStatus: StateFlow<GameStatus> = _gameStatus.asStateFlow()

    private val _moveCount = MutableStateFlow(0)
    val moveCount: StateFlow<Int> = _moveCount.asStateFlow()

    private val _moveHistory = MutableStateFlow<List<MoveRecord>>(emptyList())
    val moveHistory: StateFlow<List<MoveRecord>> = _moveHistory.asStateFlow()

    private val _activeViolation = MutableStateFlow<ViolationType?>(null)
    val activeViolation: StateFlow<ViolationType?> = _activeViolation.asStateFlow()

    private val _isMuted = MutableStateFlow(false)
    val isMuted: StateFlow<Boolean> = _isMuted.asStateFlow()

    private val _bestMoves = MutableStateFlow<Int?>(null)
    val bestMoves: StateFlow<Int?> = _bestMoves.asStateFlow()

    private val _hintMessage = MutableStateFlow<String?>(null)
    val hintMessage: StateFlow<String?> = _hintMessage.asStateFlow()

    // Alert message when attempting invalid actions (e.g. clicking objects when boat is full)
    private val _boatFullAlert = MutableStateFlow<String?>(null)
    val boatFullAlert: StateFlow<String?> = _boatFullAlert.asStateFlow()
    private var boatAlertJob: Job? = null

    // Interactive on-map visual hint states
    private val _highlightedHintItem = MutableStateFlow<GameItem?>(null)
    val highlightedHintItem: StateFlow<GameItem?> = _highlightedHintItem.asStateFlow()

    private val _isHintHighlightingBoat = MutableStateFlow(false)
    val isHintHighlightingBoat: StateFlow<Boolean> = _isHintHighlightingBoat.asStateFlow()

    private var hintJob: Job? = null

    private val _isAutoSolving = MutableStateFlow(false)
    val isAutoSolving: StateFlow<Boolean> = _isAutoSolving.asStateFlow()

    // Boat position progress: 0f = Left, 1f = Right
    private val _boatPosition = MutableStateFlow(0f)
    val boatPosition: StateFlow<Float> = _boatPosition.asStateFlow()

    // Particle splash event triggered when boat lands successfully
    private val _splashEvent = MutableStateFlow<SplashEvent?>(null)
    val splashEvent: StateFlow<SplashEvent?> = _splashEvent.asStateFlow()

    // Disembark jump animation event when boat lands on the bank with jumping animals (Rabbit, Dog)
    private val _disembarkJumpEvent = MutableStateFlow<DisembarkJumpEvent?>(null)
    val disembarkJumpEvent: StateFlow<DisembarkJumpEvent?> = _disembarkJumpEvent.asStateFlow()

    // Triggered when a river crossing safely lands without rules violations
    private val _crossingSuccessEvent = MutableStateFlow<Long?>(null)
    val crossingSuccessEvent: StateFlow<Long?> = _crossingSuccessEvent.asStateFlow()

    // Live Timer state
    private val _elapsedSeconds = MutableStateFlow(0L)
    val elapsedSeconds: StateFlow<Long> = _elapsedSeconds.asStateFlow()

    private val _isTimerRunning = MutableStateFlow(false)
    val isTimerRunning: StateFlow<Boolean> = _isTimerRunning.asStateFlow()

    private val _isNewBestTime = MutableStateFlow(false)
    val isNewBestTime: StateFlow<Boolean> = _isNewBestTime.asStateFlow()

    private val _isNewLeastMoves = MutableStateFlow(false)
    val isNewLeastMoves: StateFlow<Boolean> = _isNewLeastMoves.asStateFlow()

    private var autoSolveJob: Job? = null
    private var timerJob: Job? = null

    init {
        _boatPosition.value = 0f
        if (isFirstLaunch) {
            prefs.edit().putBoolean("has_completed_first_launch", true).apply()
        }
    }

    // -------------------------------------------------------------------
    // NAVIGATION
    // -------------------------------------------------------------------
    fun navigateToMainMenu() {
        stopAutoSolver()
        pauseTimer()
        _screenDestination.value = ScreenDestination.MainMenu
    }

    fun navigateToLevelSelect() {
        stopAutoSolver()
        pauseTimer()
        _screenDestination.value = ScreenDestination.LevelSelect
    }

    fun navigateToSettings() {
        previousDestination = _screenDestination.value
        _screenDestination.value = ScreenDestination.Settings
    }

    fun navigateBackFromSettings() {
        _screenDestination.value = previousDestination
    }

    fun navigateToLeaderboard() {
        _screenDestination.value = ScreenDestination.Leaderboard
    }

    fun navigateToTutorial() {
        _screenDestination.value = ScreenDestination.Tutorial
    }

    /**
     * Determines whether to display the comic story vignette dialog.
     * If the user has used the app for long (e.g. 5+ runs or 3+ levels cleared), auto-vignettes are disabled.
     * Otherwise, each scenario's vignette is shown at most once.
     */
    fun shouldAutoShowStoryVignette(scenarioId: String): Boolean {
        val totalRuns = totalRunsCount.value
        val completedCount = completedLevelsCount.value
        // If the user has used the app for long, disable it
        if (totalRuns >= 5 || completedCount >= 3) {
            return false
        }
        val key = "seen_vignette_$scenarioId"
        val hasSeen = prefs.getBoolean(key, false)
        if (!hasSeen) {
            prefs.edit().putBoolean(key, true).apply()
            return true
        }
        return false
    }

    /**
     * Determines whether hint tools should be shown.
     * Keeps the screen simple and hides hint buttons for experienced flow;
     * introduces them when the player is struggling with a puzzle.
     */
    fun isPlayerStruggling(): Boolean {
        val scenario = _currentScenario.value
        // Early levels (1 & 2) show hint tool to help learn the ropes
        if (scenario.levelNumber <= 2) return true
        // If moves exceeded optimal moves, or player retried after failure, or spent > 35 seconds
        val optimal = scenario.optimalMoves
        return _moveCount.value > optimal + 1 || _levelAttemptCount.value > 0 || _elapsedSeconds.value > 35L
    }

    fun selectScenarioAndPlay(scenario: PuzzleScenario, modifiers: DifficultyModifiers? = null) {
        // Enforce anti-bypass security: player can ONLY play unlocked levels (or Level 1)
        if (!isLevelUnlocked(scenario.id) && scenario.id != PuzzleScenarios.CLASSIC.id) {
            // Level is locked and has not been legitimately unlocked via completed prior level or rewarded ad
            _screenDestination.value = ScreenDestination.LevelSelect
            return
        }

        stopAutoSolver()
        _levelAttemptCount.value = 0
        _currentScenario.value = scenario
        if (modifiers != null) {
            _difficultyModifiers.value = modifiers
        }
        _screenDestination.value = ScreenDestination.GamePlay(scenario)
        restartGame(scenario, _difficultyModifiers.value)
    }

    fun selectScenario(scenario: PuzzleScenario) {
        selectScenarioAndPlay(scenario)
    }

    fun playNextLevel() {
        val currentIdx = PuzzleScenarios.ALL.indexOfFirst { it.id == _currentScenario.value.id }
        val nextScenario = if (currentIdx >= 0 && currentIdx + 1 < PuzzleScenarios.ALL.size) {
            PuzzleScenarios.ALL[currentIdx + 1]
        } else {
            PuzzleScenarios.ALL.first()
        }
        selectScenarioAndPlay(nextScenario)
    }

    fun setPlayerName(name: String) {
        _playerName.value = name.trim().ifEmpty { "Player" }
    }

    fun toggleHaptics() {
        _isHapticsEnabled.value = !_isHapticsEnabled.value
    }

    // -------------------------------------------------------------------
    // DIFFICULTY & CHALLENGE VARIATIONS
    // -------------------------------------------------------------------
    fun setDifficultyMode(mode: DifficultyMode) {
        val newModifiers = DifficultyModifiers.fromMode(mode)
        _difficultyModifiers.value = newModifiers
        _riverState.value = _riverState.value.copy(difficultyModifiers = newModifiers)
    }

    fun updateModifiers(modifiers: DifficultyModifiers) {
        _difficultyModifiers.value = modifiers
        _riverState.value = _riverState.value.copy(difficultyModifiers = modifiers)
    }

    fun toggleHiddenItems() {
        val current = _difficultyModifiers.value
        val updated = current.copy(
            mode = DifficultyMode.CUSTOM,
            hideOppositeBankItems = !current.hideOppositeBankItems
        )
        updateModifiers(updated)
    }

    fun toggleRestrictedCombos() {
        val current = _difficultyModifiers.value
        val updated = current.copy(
            mode = DifficultyMode.CUSTOM,
            restrictBoatCombinations = !current.restrictBoatCombinations
        )
        updateModifiers(updated)
    }

    // -------------------------------------------------------------------
    // TIMER & SCORES
    // -------------------------------------------------------------------
    private fun startTimerIfNeeded() {
        if (_isTimerRunning.value || _gameStatus.value == GameStatus.VICTORY || _isAutoSolving.value) return
        _isTimerRunning.value = true
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_isTimerRunning.value) {
                delay(1000L)
                if (_isTimerRunning.value && _gameStatus.value != GameStatus.VICTORY) {
                    _elapsedSeconds.value += 1L
                }
            }
        }
    }

    fun pauseTimer() {
        _isTimerRunning.value = false
        timerJob?.cancel()
        timerJob = null
    }

    fun resetTimer() {
        pauseTimer()
        _elapsedSeconds.value = 0L
        _isNewBestTime.value = false
    }

    fun clearHighScoreHistory(levelId: String? = null) {
        viewModelScope.launch {
            repository.clearHistory(levelId)
            if (levelId == null) {
                resetUnlockedLevelsToDefault()
            }
        }
    }

    fun resetUnlockedLevelsToDefault() {
        _unlockedLevelIds.value = defaultUnlockedLevels
        prefs.edit().putStringSet("unlocked_levels", defaultUnlockedLevels).apply()
    }

    fun toggleMute() {
        _isMuted.value = !_isMuted.value
        audio.isMuted = _isMuted.value
        if (!_isMuted.value) {
            audio.playClickSound()
        }
    }

    fun playCharacterVoice(item: GameItem) {
        audio.playCharacterSound(item)
    }

    fun playFarmerVoice() {
        audio.playFarmerWhistle()
    }

    fun playStarPopSound(starIndex: Int) {
        audio.playStarPopSound(starIndex)
    }

    /** Starts procedural continuous ambient sound generator for the specified biome */
    fun startBiomeAmbience(biome: WeatherEffectType) {
        audio.startBiomeAmbience(biome)
    }

    /** Stops procedural continuous ambient sound generator */
    fun stopBiomeAmbience() {
        audio.stopBiomeAmbience()
    }

    /** Synthesizes procedural bird call or nocturnal call for the current biome */
    fun playBiomeBirdCall(biome: WeatherEffectType) {
        audio.playBiomeBirdCall(biome)
    }

    /** Synthesizes procedural river flow sound effect for the biome */
    fun playRiverFlowSound(biome: WeatherEffectType) {
        audio.playRiverFlow(biome)
    }

    /** Plays tactile water droplet / ripple sound effect */
    fun playWaterRippleSound() {
        audio.playWaterRippleSound()
    }

    /** Plays terrain-specific procedural footstep */
    fun playFootstep(biome: WeatherEffectType, isBoardingRaft: Boolean) {
        audio.playFootstep(biome, isBoardingRaft)
    }

    /** Triggers preview jump animation and sound for the Rabbit or Dog */
    fun triggerPreviewJump(item: GameItem, targetBank: Bank = Bank.RIGHT) {
        viewModelScope.launch {
            val jumpEvent = DisembarkJumpEvent(
                id = System.currentTimeMillis(),
                jumpingItems = listOf(item),
                targetBank = targetBank,
                durationMs = 600L
            )
            _disembarkJumpEvent.value = jumpEvent
            if (item == GameItem.RABBIT) {
                audio.playRabbitHop()
            } else if (item == GameItem.DOG) {
                audio.playDogBark()
            }
            delay(jumpEvent.durationMs - 120L)
            val currentBiome = LevelTheme.forScenario(_riverState.value.scenario).weatherEffect
            audio.playFootstep(currentBiome, isBoardingRaft = false)
            delay(120L)
            _disembarkJumpEvent.value = null
        }
    }

    fun showBoatFullAlert(message: String = "⚠️ The raft is full! Tap cargo on the raft to unload before adding more.") {
        _boatFullAlert.value = message
        audio.playConflictSound()
        boatAlertJob?.cancel()
        boatAlertJob = viewModelScope.launch {
            delay(3200)
            if (_boatFullAlert.value == message) {
                _boatFullAlert.value = null
            }
        }
    }

    fun dismissBoatFullAlert() {
        boatAlertJob?.cancel()
        _boatFullAlert.value = null
    }

    // -------------------------------------------------------------------
    // GAMEPLAY ACTIONS
    // -------------------------------------------------------------------
    fun toggleItem(item: GameItem) {
        if (_gameStatus.value == GameStatus.ROWING || _isAutoSolving.value) return
        startTimerIfNeeded()
        val state = _riverState.value
        val itemLoc = state.getItemLocation(item)
        val farmerBank = state.farmerBank

        if (itemLoc == ItemLocation.IN_BOAT) {
            // Unload item to current bank
            unloadItemFromBoat(item)
        } else {
            // Try to load item into boat from bank
            val itemBank = if (itemLoc == ItemLocation.LEFT_BANK) Bank.LEFT else Bank.RIGHT
            if (itemBank != farmerBank) {
                showBoatFullAlert("The Captain must be on this bank to board cargo!")
                return
            }
            val capacity = state.scenario.boatCapacity
            if (state.boatPassengers.size >= capacity) {
                showBoatFullAlert("⚠️ The raft is full! Tap cargo on the raft to unload before boarding.")
                return
            }
            loadItemToBoat(item)
        }
    }

    fun loadItemToBoat(item: GameItem) {
        if (_gameStatus.value == GameStatus.ROWING || _isAutoSolving.value) return
        startTimerIfNeeded()
        val state = _riverState.value
        val capacity = state.scenario.boatCapacity
        val currentPassengers = state.boatPassengers.toMutableList()

        if (item in currentPassengers) return

        if (currentPassengers.size >= capacity) {
            showBoatFullAlert("⚠️ The raft is full! Tap cargo on the raft to unload before boarding.")
            return
        }

        // Check boat combination restrictions if active
        val potentialCombo = currentPassengers + item
        val violation = _difficultyModifiers.value.getBoatCombinationViolation(potentialCombo)
        if (violation != null) {
            _hintMessage.value = violation
            audio.playCharacterSound(item)
            return
        }

        currentPassengers.add(item)

        _riverState.value = state.withItemLocation(item, ItemLocation.IN_BOAT).copy(
            boatPassengers = currentPassengers
        )
        val currentBiome = LevelTheme.forScenario(_riverState.value.scenario).weatherEffect
        audio.playBoardSound(item, currentBiome)
        _hintMessage.value = null
        _boatFullAlert.value = null
    }

    fun unloadItemFromBoat(item: GameItem? = null) {
        if (_gameStatus.value == GameStatus.ROWING || _isAutoSolving.value) return
        val state = _riverState.value
        val passengers = state.boatPassengers
        if (passengers.isEmpty()) return

        val currentBank = state.farmerBank
        val targetLocation = if (currentBank == Bank.LEFT) ItemLocation.LEFT_BANK else ItemLocation.RIGHT_BANK
        val currentBiome = LevelTheme.forScenario(state.scenario).weatherEffect

        if (item != null) {
            if (item !in passengers) return
            val remaining = passengers.filter { it != item }
            _riverState.value = state.withItemLocation(item, targetLocation).copy(
                boatPassengers = remaining
            )
            audio.playUnboardSound(item, currentBiome)
        } else {
            // Unload all
            var newState = state
            for (p in passengers) {
                newState = newState.withItemLocation(p, targetLocation)
                audio.playUnboardSound(p, currentBiome)
            }
            _riverState.value = newState.copy(boatPassengers = emptyList())
        }
        _hintMessage.value = null
        _boatFullAlert.value = null
    }

    fun crossRiver() {
        if (_gameStatus.value == GameStatus.ROWING || _gameStatus.value == GameStatus.GAME_OVER) return
        startTimerIfNeeded()
        dismissBoatFullAlert()

        viewModelScope.launch {
            _gameStatus.value = GameStatus.ROWING
            val currentBiome = LevelTheme.forScenario(_riverState.value.scenario).weatherEffect
            audio.playRowingSound()
            audio.playRiverFlow(currentBiome, durationMs = 1200, volume = 0.25f)
            _hintMessage.value = null

            val previousState = _riverState.value
            val fromBank = previousState.farmerBank
            val toBank = fromBank.opposite()
            val passengers = previousState.boatPassengers

            // Animate boat crossing progress with smooth 60fps cinematic rowing kinematics
            val targetProgress = if (toBank == Bank.RIGHT) 1f else 0f
            val startProgress = _boatPosition.value
            val totalDurationMs = _boatSpeed.value.durationMs
            val frameDelay = 16L
            val totalFrames = (totalDurationMs / frameDelay).toInt().coerceAtLeast(6)
            val strokeCycles = if (totalDurationMs <= 350L) 1.5 else if (totalDurationMs <= 600L) 2.0 else 2.5

            for (i in 1..totalFrames) {
                val t = i.toDouble() / totalFrames // 0.0 to 1.0
                // S-Curve (Cubic Ease-In-Out) base motion
                val sCurve = (1.0 - kotlin.math.cos(t * kotlin.math.PI)) / 2.0
                // Rowing stroke surge dynamics: real oars have rhythmic forward acceleration impulses
                val surge = kotlin.math.sin(t * strokeCycles * 2.0 * kotlin.math.PI) * 0.024 * kotlin.math.sin(t * kotlin.math.PI)
                val physicsProgress = (sCurve + surge).coerceIn(0.0, 1.0).toFloat()

                _boatPosition.value = startProgress + (targetProgress - startProgress) * physicsProgress
                delay(frameDelay)
            }
            _boatPosition.value = targetProgress

            // Check if jumping animals (Rabbit, Dog) are disembarking
            val jumpingAnimals = passengers.filter { it == GameItem.RABBIT || it == GameItem.DOG }
            if (jumpingAnimals.isNotEmpty()) {
                val jumpEvent = DisembarkJumpEvent(
                    id = System.currentTimeMillis(),
                    jumpingItems = jumpingAnimals,
                    targetBank = toBank,
                    durationMs = 580L
                )
                _disembarkJumpEvent.value = jumpEvent
                if (GameItem.RABBIT in jumpingAnimals) {
                    audio.playRabbitHop()
                }
                if (GameItem.DOG in jumpingAnimals) {
                    audio.playDogBark()
                }
                delay(jumpEvent.durationMs - 120L)
                audio.playFootstep(currentBiome, isBoardingRaft = false)
                delay(120L)
                _disembarkJumpEvent.value = null
            }

            // Update item locations upon landing
            val newLocation = if (toBank == Bank.LEFT) ItemLocation.LEFT_BANK else ItemLocation.RIGHT_BANK
            var landingState = previousState.copy(
                farmerBank = toBank,
                boatPassengers = emptyList() // automatically disembarks at destination
            )
            for (p in passengers) {
                landingState = landingState.withItemLocation(p, newLocation)
            }

            _riverState.value = landingState
            val newMoveCount = _moveCount.value + 1
            _moveCount.value = newMoveCount

            // Trigger AAA water splash particle effect & landing audio upon successful crossing
            _splashEvent.value = SplashEvent(id = System.currentTimeMillis(), targetBank = toBank)
            audio.playSplashSound()

            val desc = if (passengers.isNotEmpty()) {
                val names = passengers.joinToString(", ") { it.displayName }
                "Farmer transported $names from ${fromBank.shortName} to ${toBank.shortName}"
            } else {
                "Farmer crossed alone from ${fromBank.shortName} to ${toBank.shortName}"
            }

            _moveHistory.value = _moveHistory.value + MoveRecord(
                moveNumber = newMoveCount,
                fromBank = fromBank,
                toBank = toBank,
                passengers = passengers,
                previousState = previousState,
                description = desc
            )

            // Check game rules and violations
            val violation = landingState.checkViolation()
            if (violation != null) {
                _activeViolation.value = violation
                _gameStatus.value = GameStatus.GAME_OVER
                _levelAttemptCount.value += 1
                pauseTimer()
                audio.playGameOverSound()
            } else {
                _crossingSuccessEvent.value = System.currentTimeMillis()
                if (landingState.isGoal()) {
                _gameStatus.value = GameStatus.VICTORY
                pauseTimer()
                val currentBest = _bestMoves.value
                if (currentBest == null || newMoveCount < currentBest) {
                    _bestMoves.value = newMoveCount
                }

                // Save run to Room high score database (only for human play, not auto solve)
                if (!_isAutoSolving.value) {
                    val finalTime = _elapsedSeconds.value
                    val scenarioId = landingState.scenario.id
                    val currentBestRecord = repository.getBestScore(scenarioId)
                    val isNewBestTime = currentBestRecord == null || finalTime < currentBestRecord.timeSeconds
                    val isNewLeastMoves = currentBestRecord == null || newMoveCount < currentBestRecord.movesCount
                    _isNewBestTime.value = isNewBestTime
                    _isNewLeastMoves.value = isNewLeastMoves
                    val optimalTarget = landingState.scenario.optimalMoves
                    val starCount = if (newMoveCount <= optimalTarget) 3 else if (newMoveCount <= optimalTarget + 2) 2 else 1
                    val player = _playerName.value.ifBlank { "Player" }
                    repository.saveScore(
                        levelId = scenarioId,
                        playerName = player,
                        timeSeconds = finalTime,
                        movesCount = newMoveCount,
                        isOptimal = newMoveCount == optimalTarget,
                        stars = starCount
                    )

                    // Auto-unlock next level upon completing current level
                    val nextLvlNum = landingState.scenario.levelNumber + 1
                    val nextScenario = PuzzleScenarios.ALL.firstOrNull { it.levelNumber == nextLvlNum }
                    if (nextScenario != null) {
                        unlockLevel(nextScenario.id)
                    }
                }

                audio.playVictorySound()
            } else {
                _gameStatus.value = GameStatus.IDLE
            }
        }
    }
}

    fun undoMove() {
        if (_gameStatus.value == GameStatus.ROWING || _isAutoSolving.value) return
        val history = _moveHistory.value
        if (history.isEmpty()) return

        val lastRecord = history.last()
        _riverState.value = lastRecord.previousState
        _boatPosition.value = if (lastRecord.previousState.farmerBank == Bank.LEFT) 0f else 1f
        _moveHistory.value = history.dropLast(1)
        val updatedCount = (_moveCount.value - 1).coerceAtLeast(0)
        _moveCount.value = updatedCount
        _gameStatus.value = GameStatus.IDLE
        _activeViolation.value = null
        _hintMessage.value = null
        _disembarkJumpEvent.value = null
        dismissBoatFullAlert()
        if (updatedCount == 0) {
            resetTimer()
        }
        audio.playUndoSound()
    }

    fun restartGame(
        scenario: PuzzleScenario = _currentScenario.value,
        modifiers: DifficultyModifiers = _difficultyModifiers.value
    ) {
        stopAutoSolver()
        resetTimer()
        _currentScenario.value = scenario
        _difficultyModifiers.value = modifiers
        _riverState.value = RiverState(scenario = scenario, difficultyModifiers = modifiers)
        _boatPosition.value = 0f
        _moveCount.value = 0
        _moveHistory.value = emptyList()
        _gameStatus.value = GameStatus.IDLE
        _activeViolation.value = null
        _hintMessage.value = null
        _disembarkJumpEvent.value = null
        dismissBoatFullAlert()
        audio.playResetSound()
    }

    // -------------------------------------------------------------------
    // AI HINTS & AUTO-SOLVER
    // -------------------------------------------------------------------
    fun provideHint() {
        if (_gameStatus.value == GameStatus.GAME_OVER || _gameStatus.value == GameStatus.VICTORY) return

        hintJob?.cancel()
        hintJob = viewModelScope.launch {
            val state = _riverState.value
            val scenario = state.scenario
            val genericState = toGenericDiscreteState(state)
            val modifiers = _difficultyModifiers.value

            val nextOptimalItems = withContext(Dispatchers.Default) {
                RiverCrossingSolver.getNextOptimalMoveForScenario(
                    scenario = scenario,
                    startState = genericState,
                    difficultyModifiers = modifiers
                )
            }
            if (nextOptimalItems == null) {
                _hintMessage.value = "💡 No valid winning path found from this state. Try using Undo to return to a safe configuration!"
                audio.playHintSound()
                return@launch
            }

            val optimalItem = nextOptimalItems.firstOrNull()
            if (optimalItem != null) {
                val isAlreadyInBoat = optimalItem in state.boatPassengers
                if (isAlreadyInBoat) {
                    _isHintHighlightingBoat.value = true
                    _highlightedHintItem.value = null
                } else {
                    _highlightedHintItem.value = optimalItem
                    _isHintHighlightingBoat.value = false
                }
            } else {
                _isHintHighlightingBoat.value = true
                _highlightedHintItem.value = null
            }

            _hintMessage.value = if (nextOptimalItems.isNotEmpty()) {
                val names = nextOptimalItems.joinToString(" and ") { it.displayName }
                val explanation = RiverCrossingSolver.getMoveExplanation(
                    stepIndex = _moveCount.value + 1,
                    item = nextOptimalItems.firstOrNull(),
                    fromBank = state.farmerBank
                )
                "💡 AI Hint: Load the $names into the boat and row across!\n$explanation"
            } else {
                val explanation = RiverCrossingSolver.getMoveExplanation(
                    stepIndex = _moveCount.value + 1,
                    item = null,
                    fromBank = state.farmerBank
                )
                "💡 AI Hint: Farmer should cross alone to the opposite bank.\n$explanation"
            }
            audio.playHintSound()

            delay(8000L)
            clearHint()
        }
    }

    fun clearHint() {
        _hintMessage.value = null
        _highlightedHintItem.value = null
        _isHintHighlightingBoat.value = false
        hintJob?.cancel()
    }

    fun startAutoSolver() {
        if (_isAutoSolving.value) return
        stopAutoSolver()

        autoSolveJob = viewModelScope.launch {
            _isAutoSolving.value = true
            restartGame(_currentScenario.value, _difficultyModifiers.value)
            delay(400)

            var currentState = _riverState.value
            val scenario = currentState.scenario

            while (!currentState.isGoal() && _isAutoSolving.value) {
                val generic = toGenericDiscreteState(currentState)
                val modifiers = _difficultyModifiers.value
                val nextMoves = withContext(Dispatchers.Default) {
                    RiverCrossingSolver.getNextOptimalMoveForScenario(
                        scenario = scenario,
                        startState = generic,
                        difficultyModifiers = modifiers
                    )
                } ?: break

                // 1. Board passenger(s)
                for (item in nextMoves) {
                    loadItemToBoat(item)
                    delay(400)
                }

                // 2. Cross river
                crossRiver()
                // Wait for boat animation & landing
                delay(_boatSpeed.value.durationMs + 60L)

                currentState = _riverState.value
                if (_gameStatus.value == GameStatus.GAME_OVER) {
                    break
                }
            }
            _isAutoSolving.value = false
        }
    }

    fun stopAutoSolver() {
        autoSolveJob?.cancel()
        autoSolveJob = null
        _isAutoSolving.value = false
    }

    fun isLevelUnlocked(levelId: String): Boolean {
        return _unlockedLevelIds.value.contains(levelId)
    }

    fun unlockLevel(levelId: String) {
        val current = _unlockedLevelIds.value.toMutableSet()
        if (current.add(levelId)) {
            _unlockedLevelIds.value = current
            prefs.edit().putStringSet("unlocked_levels", current).apply()
        }
    }

    fun unlockAllLevels() {
        val allIds = PuzzleScenarios.ALL.map { it.id }.toSet()
        _unlockedLevelIds.value = allIds
        prefs.edit().putStringSet("unlocked_levels", allIds).apply()
    }

    private fun toGenericDiscreteState(state: RiverState): GenericDiscreteState {
        fun resolveBank(loc: ItemLocation): Bank {
            return when (loc) {
                ItemLocation.LEFT_BANK -> Bank.LEFT
                ItemLocation.RIGHT_BANK -> Bank.RIGHT
                ItemLocation.IN_BOAT -> state.farmerBank
            }
        }

        val map = state.scenario.items.associateWith { resolveBank(state.getItemLocation(it)) }
        return GenericDiscreteState(
            farmer = state.farmerBank,
            itemBanks = map
        )
    }

    override fun onCleared() {
        super.onCleared()
        stopAutoSolver()
        audio.stopBiomeAmbience()
    }
}
