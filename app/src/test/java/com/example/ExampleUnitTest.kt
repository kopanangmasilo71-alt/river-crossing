package com.example

import com.example.algorithm.GenericDiscreteState
import com.example.algorithm.RiverCrossingSolver
import com.example.model.Bank
import com.example.model.DifficultyMode
import com.example.model.DifficultyModifiers
import com.example.model.GameItem
import com.example.model.PuzzleScenarios
import com.example.model.RiverState
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testClassicLevel1OptimalPath() {
        val scenario = PuzzleScenarios.ALL[0] // Level 1 (Basics: 2 items)
        val startState = GenericDiscreteState(
            farmer = Bank.LEFT,
            itemBanks = scenario.items.associateWith { Bank.LEFT }
        )
        val path = RiverCrossingSolver.findGenericShortestPath(scenario, startState)
        assertNotNull(path)
        assertEquals(3, path!!.size)

        val scenario2 = PuzzleScenarios.ALL[1] // Level 2 (Classic: 3 items)
        val startState2 = GenericDiscreteState(
            farmer = Bank.LEFT,
            itemBanks = scenario2.items.associateWith { Bank.LEFT }
        )
        val path2 = RiverCrossingSolver.findGenericShortestPath(scenario2, startState2)
        assertNotNull(path2)
        assertEquals(7, path2!!.size)
    }

    @Test
    fun testRestrictedCargoCombinations() {
        val modifiers = DifficultyModifiers(
            mode = DifficultyMode.RESTRICTED_COMBOS,
            restrictBoatCombinations = true
        )
        // Dog + Wolf forbidden in same boat
        val violationDogWolf = modifiers.getBoatCombinationViolation(listOf(GameItem.WOLF, GameItem.DOG))
        assertNotNull(violationDogWolf)

        // Single passenger allowed
        val violationSingle = modifiers.getBoatCombinationViolation(listOf(GameItem.WOLF))
        assertNull(violationSingle)
    }

    @Test
    fun testHiddenBankLogic() {
        val scenario = PuzzleScenarios.ALL[0]
        val modifiers = DifficultyModifiers(
            mode = DifficultyMode.FOG_OF_WAR,
            hideOppositeBankItems = true
        )
        val state = RiverState(scenario = scenario, difficultyModifiers = modifiers)

        // Farmer is on LEFT bank, so RIGHT bank should be hidden
        assertEquals(Bank.LEFT, state.farmerBank)
        assertTrue(state.isBankHidden(Bank.RIGHT))
        assertFalse(state.isBankHidden(Bank.LEFT))
    }
}
