package com.example.data.repository

import com.example.data.db.HighScoreDao
import com.example.data.db.HighScoreEntity
import kotlinx.coroutines.flow.Flow

class HighScoreRepository(private val dao: HighScoreDao) {
    val highScores: Flow<List<HighScoreEntity>> = dao.getHighScores()
    val recentHistory: Flow<List<HighScoreEntity>> = dao.getRecentHistory()
    val bestTime: Flow<Long?> = dao.getBestTimeFlow()
    val completedLevelCount: Flow<Int> = dao.getCompletedLevelCountFlow()
    val totalRunsCount: Flow<Int> = dao.getTotalRunsCountFlow()

    fun getScoresForLevel(levelId: String): Flow<List<HighScoreEntity>> = dao.getHighScoresForLevel(levelId)
    fun getBestTimeForLevel(levelId: String): Flow<Long?> = dao.getBestTimeForLevelFlow(levelId)
    fun getLeastMovesForLevel(levelId: String): Flow<Int?> = dao.getLeastMovesForLevelFlow(levelId)

    suspend fun saveScore(
        levelId: String = "classic",
        timeSeconds: Long,
        movesCount: Int,
        isOptimal: Boolean,
        stars: Int,
        playerName: String = "Player"
    ): Long {
        val entry = HighScoreEntity(
            levelId = levelId,
            playerName = playerName,
            timeSeconds = timeSeconds,
            movesCount = movesCount,
            isOptimal = isOptimal,
            stars = stars,
            timestamp = System.currentTimeMillis()
        )
        return dao.insertHighScore(entry)
    }

    suspend fun getBestScore(levelId: String = "classic"): HighScoreEntity? {
        return dao.getBestScoreForLevel(levelId)
    }

    suspend fun clearHistory(levelId: String? = null) {
        if (levelId != null) {
            dao.clearLevelScores(levelId)
        } else {
            dao.clearAllScores()
        }
    }
}
