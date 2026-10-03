package com.example

import com.example.model.Bank
import com.example.model.DangerRule
import com.example.model.DifficultyModifiers
import com.example.model.GameItem
import com.example.model.GameState
import com.example.model.ItemLocation
import com.example.model.MoveResult
import com.example.model.MoveValidationResult
import com.example.model.PuzzleDifficulty
import com.example.model.PuzzleScenario
import com.example.model.ViolationType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class GameStateTest {

    private val bearBerriesScenario = PuzzleScenario(
        id = "bear_and_berries_test",
        levelNumber = 99,
        title = "Bear & Berries Test",
        subtitle = "Transport the Bear and Berries across the river",
        difficulty = PuzzleDifficulty.NORMAL,
        items = listOf(GameItem.BEAR, GameItem.BERRIES),
        boatCapacity = 1,
        optimalMoves = 3,
        dangerRules = listOf(
            DangerRule(
                predator = GameItem.BEAR,
                prey = GameItem.BERRIES,
                title = "Bear Ate The Berries!",
                reason = "You left the Bear and Berries alone together on the {bank} without the Farmer.",
                emoji = "🐻💥🫐"
            )
        ),
        description = "Test scenario with Bear and Berries",
        rules = listOf("Bear eats Berries if left alone together on a bank.")
    )

    @Test
    fun testGameStateInitialPositionsIncludingBearAndBerries() {
        val state = GameState.createInitial(scenario = bearBerriesScenario)

        // Farmer is on left bank
        assertEquals(Bank.LEFT, state.farmerBank)

        // Bear and Berries positions are explicitly tracked
        assertEquals(ItemLocation.LEFT_BANK, state.bearPosition)
        assertEquals(ItemLocation.LEFT_BANK, state.berriesPosition)
        assertEquals(ItemLocation.LEFT_BANK, state.getItemPosition(GameItem.BEAR))
        assertEquals(ItemLocation.LEFT_BANK, state.getItemPosition(GameItem.BERRIES))

        // Boat is empty initially
        assertTrue(state.boatPassengers.isEmpty())
        assertNull(state.boatPassenger)
        assertEquals(1, state.boatCapacity)
        assertFalse(state.isGoal())
    }

    @Test
    fun testBoardingMovementRules() {
        var state = GameState.createInitial(scenario = bearBerriesScenario)

        // Can board Bear
        val canBoardBear = state.canBoard(GameItem.BEAR)
        assertTrue(canBoardBear is MoveValidationResult.Valid)

        // Board Bear into the boat
        state = state.boardItem(GameItem.BEAR)
        assertEquals(ItemLocation.IN_BOAT, state.bearPosition)
        assertEquals(listOf(GameItem.BEAR), state.boatPassengers)

        // Cannot board Berries because capacity is 1
        val canBoardBerries = state.canBoard(GameItem.BERRIES)
        assertTrue(canBoardBerries is MoveValidationResult.Invalid)

        // Cannot board Bear again
        val canBoardBearAgain = state.canBoard(GameItem.BEAR)
        assertTrue(canBoardBearAgain is MoveValidationResult.Invalid)
    }

    @Test
    fun testDisembarkMovementRules() {
        var state = GameState.createInitial(scenario = bearBerriesScenario)
        state = state.boardItem(GameItem.BEAR)

        // Can disembark Bear
        val canDisembark = state.canDisembark(GameItem.BEAR)
        assertTrue(canDisembark is MoveValidationResult.Valid)

        // Cannot disembark Berries because it's not in the boat
        val canDisembarkBerries = state.canDisembark(GameItem.BERRIES)
        assertTrue(canDisembarkBerries is MoveValidationResult.Invalid)

        // Disembark Bear
        state = state.disembarkItem(GameItem.BEAR)
        assertEquals(ItemLocation.LEFT_BANK, state.bearPosition)
        assertTrue(state.boatPassengers.isEmpty())
    }

    @Test
    fun testBearEatsBerriesViolationWhenLeftAloneOnBank() {
        // Farmer crosses alone to RIGHT, leaving Bear and Berries unattended on LEFT
        val state = GameState.createInitial(scenario = bearBerriesScenario)

        // Farmer crosses alone
        val moveResult = state.crossRiver()
        assertTrue(moveResult is MoveResult.Violation)

        val violationResult = moveResult as MoveResult.Violation
        val violation = violationResult.violation
        assertTrue(violation is ViolationType.Custom)
        val customViolation = violation as ViolationType.Custom
        assertEquals(GameItem.BEAR, customViolation.predator)
        assertEquals(GameItem.BERRIES, customViolation.prey)
        assertEquals(Bank.LEFT, customViolation.bank)
    }

    @Test
    fun testSafeMovementPathWithBearAndBerries() {
        var state = GameState.createInitial(scenario = bearBerriesScenario)

        // Step 1: Farmer boards Bear and crosses to RIGHT
        state = state.boardItem(GameItem.BEAR)
        assertEquals(ItemLocation.IN_BOAT, state.bearPosition)
        assertEquals(ItemLocation.LEFT_BANK, state.berriesPosition)

        val move1 = state.crossRiver()
        assertTrue(move1 is MoveResult.Success)
        state = (move1 as MoveResult.Success).newState
        assertEquals(Bank.RIGHT, state.farmerBank)
        assertEquals(ItemLocation.RIGHT_BANK, state.bearPosition)
        assertEquals(ItemLocation.LEFT_BANK, state.berriesPosition)
        assertFalse(move1.isGoal)

        // Step 2: Farmer crosses back alone to LEFT
        val move2 = state.crossRiver()
        assertTrue(move2 is MoveResult.Success)
        state = (move2 as MoveResult.Success).newState
        assertEquals(Bank.LEFT, state.farmerBank)
        assertEquals(ItemLocation.RIGHT_BANK, state.bearPosition)
        assertEquals(ItemLocation.LEFT_BANK, state.berriesPosition)

        // Step 3: Farmer boards Berries and crosses to RIGHT
        state = state.boardItem(GameItem.BERRIES)
        assertEquals(ItemLocation.IN_BOAT, state.berriesPosition)

        val move3 = state.crossRiver()
        assertTrue(move3 is MoveResult.Success)
        state = (move3 as MoveResult.Success).newState
        assertEquals(Bank.RIGHT, state.farmerBank)
        assertEquals(ItemLocation.RIGHT_BANK, state.bearPosition)
        assertEquals(ItemLocation.RIGHT_BANK, state.berriesPosition)

        // Goal reached safely!
        assertTrue(move3.isGoal)
        assertTrue(state.isGoal())
        assertNull(state.checkViolations())
    }
}
