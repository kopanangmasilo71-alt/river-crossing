package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "high_scores")
data class HighScoreEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val levelId: String = "classic",
    val playerName: String = "Player",
    val timeSeconds: Long,
    val movesCount: Int,
    val isOptimal: Boolean,
    val stars: Int,
    val timestamp: Long = System.currentTimeMillis()
)
