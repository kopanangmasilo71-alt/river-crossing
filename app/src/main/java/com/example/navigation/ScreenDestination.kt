package com.example.navigation

import com.example.model.PuzzleScenario
import com.example.model.PuzzleScenarios

/**
 * State-based navigation destinations for River Crossing.
 */
sealed interface ScreenDestination {
    data object MainMenu : ScreenDestination
    data object LevelSelect : ScreenDestination
    data object Settings : ScreenDestination
    data object Leaderboard : ScreenDestination
    data object Tutorial : ScreenDestination
    data class GamePlay(val scenario: PuzzleScenario = PuzzleScenarios.CLASSIC) : ScreenDestination
}
