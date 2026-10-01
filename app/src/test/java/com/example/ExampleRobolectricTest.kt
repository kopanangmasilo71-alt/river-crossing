package com.example

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.db.AppDatabase
import com.example.data.repository.HighScoreRepository
import com.example.model.Bank
import com.example.model.GameItem
import com.example.model.ItemLocation
import com.example.model.PuzzleScenarios
import com.example.model.RiverState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun readStringFromContext() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("River Crossing", appName)
    }

    @Test
    fun testInitialStateIsValid() {
        val state = RiverState()
        assertNull(state.checkViolation())
        assertFalse(state.isGoal())
    }

    @Test
    fun testAllLevelsConfiguredCorrectly() {
        val scenarios = PuzzleScenarios.ALL
        assertEquals(50, scenarios.size)

        // Verify each level from 1 to 50 exists and has valid configuration
        for (i in 1..50) {
            val level = scenarios[i - 1]
            assertEquals(i, level.levelNumber)
            assertTrue(level.items.isNotEmpty())
            assertTrue(level.optimalMoves > 0)
            assertTrue(level.boatCapacity in 1..3)
        }
    }

    @Test
    fun testViolationDogAndRabbitWithoutFarmer() {
        // Farmer crosses to RIGHT alone, leaving Dog and Rabbit on LEFT
        val state = RiverState(
            farmerBank = Bank.RIGHT,
            itemLocations = mapOf(
                GameItem.DOG to ItemLocation.LEFT_BANK,
                GameItem.RABBIT to ItemLocation.LEFT_BANK,
                GameItem.CABBAGE to ItemLocation.LEFT_BANK
            )
        )
        val violation = state.checkViolation()
        assertNotNull(violation)
    }

    @Test
    fun testSafeFirstMoveRabbit() {
        // Farmer crosses to RIGHT with Rabbit, leaving Dog and Cabbage on LEFT
        val state = RiverState(
            farmerBank = Bank.RIGHT,
            itemLocations = mapOf(
                GameItem.DOG to ItemLocation.LEFT_BANK,
                GameItem.RABBIT to ItemLocation.RIGHT_BANK,
                GameItem.CABBAGE to ItemLocation.LEFT_BANK
            )
        )
        val violation = state.checkViolation()
        assertNull(violation) // Dog and Cabbage are safe together!
    }

    @Test
    fun testRoomDatabaseLeastMovesPersistence() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        val repo = HighScoreRepository(db.highScoreDao())

        // Save first run (9 moves)
        repo.saveScore(
            levelId = "classic",
            timeSeconds = 25,
            movesCount = 9,
            isOptimal = false,
            stars = 2,
            playerName = "Alice"
        )

        // Save second run (7 moves - optimal least moves)
        repo.saveScore(
            levelId = "classic",
            timeSeconds = 18,
            movesCount = 7,
            isOptimal = true,
            stars = 3,
            playerName = "Bob"
        )

        val leastMoves = repo.getLeastMovesForLevel("classic").first()
        assertEquals(7, leastMoves)

        val bestRun = repo.getBestScore("classic")
        assertNotNull(bestRun)
        assertEquals(7, bestRun?.movesCount)
        assertEquals("Bob", bestRun?.playerName)

        val completedCount = repo.completedLevelCount.first()
        assertEquals(1, completedCount)

        val totalRuns = repo.totalRunsCount.first()
        assertEquals(2, totalRuns)

        db.close()
    }
}
