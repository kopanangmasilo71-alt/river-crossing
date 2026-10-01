package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.db.HighScoreEntity
import com.example.model.PuzzleScenarios
import com.example.ui.theme.GoldenBankGlow
import com.example.ui.theme.WoodButtonBottom
import com.example.ui.theme.WoodButtonTop
import com.example.ui.theme.WoodGoldenText
import com.example.ui.theme.WoodInsetPanel
import com.example.ui.theme.WoodSignboardBorder
import com.example.ui.theme.WoodSignboardDark
import com.example.ui.theme.WoodTextMuted
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HighScoresDialog(
    highScores: List<HighScoreEntity>,
    bestTimeSeconds: Long? = null,
    initialLevelId: String? = null,
    onDismiss: () -> Unit,
    onClearScores: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showConfirmClear by remember { mutableStateOf(false) }
    var selectedLevelId by remember { mutableStateOf<String?>(initialLevelId) }

    val filteredScores = remember(highScores, selectedLevelId) {
        if (selectedLevelId == null) {
            highScores
        } else {
            highScores.filter { it.levelId == selectedLevelId }
        }
    }

    val fastestInSelection = remember(filteredScores) {
        filteredScores.minByOrNull { it.timeSeconds }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color(0xFF0284C7),
                border = BorderStroke(1.dp, GoldenBankGlow),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onDismiss)
                    .testTag("high_scores_close_button")
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color(0xFF0284C7), Color(0xFF0369A1))
                            )
                        )
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Close Logbook", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = GoldenBankGlow)
                }
            }
        },
        dismissButton = {
            if (highScores.isNotEmpty()) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = WoodInsetPanel,
                    border = BorderStroke(1.dp, Color(0xFFDC2626)),
                    modifier = Modifier
                        .clickable { showConfirmClear = true }
                        .testTag("high_scores_clear_button")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Clear Records",
                            tint = Color(0xFFFF7A7A),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Clear", fontSize = 12.sp, color = Color(0xFFFF7A7A), fontWeight = FontWeight.Bold)
                    }
                }
            }
        },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = WoodInsetPanel,
                        border = BorderStroke(1.5.dp, GoldenBankGlow),
                        modifier = Modifier.size(38.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = "Trophy",
                                tint = GoldenBankGlow,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                    Column {
                        Text(
                            text = "Hall of Fame",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                        Text(
                            text = "Room Database Records",
                            fontSize = 11.sp,
                            color = Color(0xFFBAE6FD)
                        )
                    }
                }
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color(0xFFBAE6FD)
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Level filter tabs
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    item {
                        val isAllSelected = selectedLevelId == null
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isAllSelected) WoodButtonBottom else WoodInsetPanel,
                            border = BorderStroke(1.dp, if (isAllSelected) GoldenBankGlow else WoodSignboardBorder),
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { selectedLevelId = null }
                        ) {
                            Text(
                                text = "All Levels",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isAllSelected) GoldenBankGlow else WoodTextMuted,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }

                    items(PuzzleScenarios.ALL) { scenario ->
                        val isSelected = selectedLevelId == scenario.id
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) WoodButtonBottom else WoodInsetPanel,
                            border = BorderStroke(1.dp, if (isSelected) GoldenBankGlow else WoodSignboardBorder),
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { selectedLevelId = scenario.id }
                        ) {
                            Text(
                                text = "Lvl ${scenario.levelNumber}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) GoldenBankGlow else WoodTextMuted,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                // Summary Highlight Card
                if (fastestInSelection != null) {
                    WoodInsetBox(
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text("⚡", fontSize = 22.sp)
                                Column {
                                    Text(
                                        text = if (selectedLevelId != null) "Level Best Time" else "All-Time Fastest",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = WoodTextMuted
                                    )
                                    Text(
                                        text = formatTime(fastestInSelection.timeSeconds),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = GoldenBankGlow
                                    )
                                }
                            }
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = WoodSignboardDark,
                                border = BorderStroke(1.dp, GoldenBankGlow)
                            ) {
                                Text(
                                    text = "${fastestInSelection.movesCount} moves",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GoldenBankGlow,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }

                if (filteredScores.isEmpty()) {
                    // Empty State
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_farmer),
                            contentDescription = "Farmer",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(54.dp)
                                .clip(CircleShape)
                                .border(1.5.dp, GoldenBankGlow, CircleShape)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "No records yet!",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                        Text(
                            text = "Safely guide everyone across the river to record your first time in the logbook.",
                            fontSize = 11.5.sp,
                            color = Color(0xFFBAE6FD),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                        )
                    }
                } else {
                    // High scores list
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 280.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        itemsIndexed(filteredScores) { index, item ->
                            HighScoreRow(rank = index + 1, score = item)
                        }
                    }
                }
            }
        },
        shape = RoundedCornerShape(24.dp),
        containerColor = Color(0xF2072449),
        modifier = modifier
    )

    if (showConfirmClear) {
        AlertDialog(
            onDismissRequest = { showConfirmClear = false },
            containerColor = Color(0xF2072449),
            title = { Text("Reset Leaderboard?", fontWeight = FontWeight.Bold, color = Color.White) },
            text = { Text("Are you sure you want to delete all saved record times? This action cannot be undone.", color = Color(0xFFBAE6FD)) },
            confirmButton = {
                Button(
                    onClick = {
                        onClearScores()
                        showConfirmClear = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626))
                ) {
                    Text("Delete All", color = Color.White)
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showConfirmClear = false }) {
                    Text("Cancel", color = Color(0xFFBAE6FD))
                }
            }
        )
    }
}

@Composable
private fun HighScoreRow(
    rank: Int,
    score: HighScoreEntity
) {
    val medal = when (rank) {
        1 -> "🥇"
        2 -> "🥈"
        3 -> "🥉"
        else -> "#$rank"
    }

    val scenario = PuzzleScenarios.getById(score.levelId)

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = WoodInsetPanel,
        border = BorderStroke(
            width = if (rank == 1) 1.5.dp else 1.dp,
            color = if (rank == 1) GoldenBankGlow else WoodSignboardBorder
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = WoodSignboardDark,
                    border = BorderStroke(1.dp, if (rank <= 3) GoldenBankGlow else WoodSignboardBorder),
                    modifier = Modifier.size(34.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = medal,
                            fontWeight = FontWeight.Bold,
                            fontSize = if (rank <= 3) 15.sp else 11.sp,
                            color = GoldenBankGlow
                        )
                    }
                }

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Timer,
                            contentDescription = null,
                            tint = GoldenBankGlow,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = formatTime(score.timeSeconds),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = GoldenBankGlow
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = WoodSignboardDark
                        ) {
                            Text(
                                text = "Lvl ${scenario?.levelNumber ?: 1}",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = WoodGoldenText,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                            )
                        }
                        if (score.isOptimal) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0xFF2E4E28)
                            ) {
                                Text(
                                    text = "OPTIMAL",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GoldenBankGlow,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "${score.movesCount} moves",
                            fontSize = 11.sp,
                            color = WoodGoldenText
                        )
                        Text("•", fontSize = 10.sp, color = WoodTextMuted)
                        Text(
                            text = formatDate(score.timestamp),
                            fontSize = 10.sp,
                            color = WoodTextMuted
                        )
                    }
                }
            }

            // Star Rating
            Row(verticalAlignment = Alignment.CenterVertically) {
                for (i in 1..3) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = if (i <= score.stars) GoldenBankGlow else WoodTextMuted.copy(alpha = 0.4f),
                        modifier = Modifier.size(15.dp)
                    )
                }
            }
        }
    }
}

fun formatTime(totalSeconds: Long): String {
    val mins = totalSeconds / 60
    val secs = totalSeconds % 60
    return String.format(Locale.getDefault(), "%02d:%02d", mins, secs)
}

private fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("MMM d, HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}
