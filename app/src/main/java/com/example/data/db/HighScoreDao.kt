package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HighScoreDao {
    @Query("SELECT * FROM high_scores ORDER BY movesCount ASC, timeSeconds ASC LIMIT 100")
    fun getHighScores(): Flow<List<HighScoreEntity>>

    @Query("SELECT * FROM high_scores ORDER BY timestamp DESC LIMIT 100")
    fun getRecentHistory(): Flow<List<HighScoreEntity>>

    @Query("SELECT * FROM high_scores WHERE levelId = :levelId ORDER BY movesCount ASC, timeSeconds ASC LIMIT 50")
    fun getHighScoresForLevel(levelId: String): Flow<List<HighScoreEntity>>

    @Query("SELECT * FROM high_scores WHERE levelId = :levelId ORDER BY movesCount ASC, timeSeconds ASC LIMIT 1")
    suspend fun getBestScoreForLevel(levelId: String): HighScoreEntity?

    @Query("SELECT * FROM high_scores ORDER BY movesCount ASC, timeSeconds ASC LIMIT 1")
    suspend fun getBestScore(): HighScoreEntity?

    @Query("SELECT MIN(movesCount) FROM high_scores WHERE levelId = :levelId")
    fun getLeastMovesForLevelFlow(levelId: String): Flow<Int?>

    @Query("SELECT MIN(timeSeconds) FROM high_scores WHERE levelId = :levelId")
    fun getBestTimeForLevelFlow(levelId: String): Flow<Long?>

    @Query("SELECT MIN(timeSeconds) FROM high_scores")
    fun getBestTimeFlow(): Flow<Long?>

    @Query("SELECT COUNT(DISTINCT levelId) FROM high_scores")
    fun getCompletedLevelCountFlow(): Flow<Int>

    @Query("SELECT COUNT(*) FROM high_scores")
    fun getTotalRunsCountFlow(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHighScore(highScore: HighScoreEntity): Long

    @Query("DELETE FROM high_scores WHERE levelId = :levelId")
    suspend fun clearLevelScores(levelId: String)

    @Query("DELETE FROM high_scores")
    suspend fun clearAllScores()
}
