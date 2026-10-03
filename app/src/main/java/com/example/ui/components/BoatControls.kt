package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.GameStatus
import com.example.model.LevelTheme
import com.example.model.RiverState
import com.example.ui.theme.RiverForestGreenMid
import com.example.ui.theme.RiverMeadowGrass
import com.example.ui.theme.RiverDeepBlueDark
import com.example.ui.theme.RiverTimberBorder
import com.example.ui.theme.RiverGoldGlow
import com.example.ui.theme.GoldenBankGlow
import com.example.ui.theme.VibrantSurfaceBorder
import com.example.ui.theme.VibrantTextPrimary
import com.example.ui.theme.VibrantTextSecondary

@Composable
fun BoatControls(
    riverState: RiverState,
    gameStatus: GameStatus,
    moveCount: Int,
    canUndo: Boolean,
    hintMessage: String?,
    levelTheme: LevelTheme = LevelTheme.forScenario(riverState.scenario),
    onCrossRiver: () -> Unit,
    onUndo: () -> Unit,
    onRestart: () -> Unit,
    onHintClick: () -> Unit,
    onDismissHint: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isRowing = gameStatus == GameStatus.ROWING
    val isGameOver = gameStatus == GameStatus.GAME_OVER
    val isVictory = gameStatus == GameStatus.VICTORY

    val targetBank = riverState.farmerBank.opposite()
    val passenger = riverState.boatPassenger

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // AI Hint Card if active
        AnimatedVisibility(
            visible = hintMessage != null,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            hintMessage?.let { msg ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = RiverDeepBlueDark),
                    border = BorderStroke(1.2.dp, RiverGoldGlow),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = "💡", fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = msg,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = RiverGoldGlow
                            )
                        }
                        IconButton(
                            onClick = onDismissHint,
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close Hint",
                                tint = Color(0xFFBAE6FD),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }

        // Primary Cross River Button (Theme-adaptive)
        Button(
            onClick = onCrossRiver,
            enabled = !isRowing && !isGameOver && !isVictory,
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = levelTheme.sailButtonColors.getOrElse(1) { RiverForestGreenMid },
                contentColor = Color.White,
                disabledContainerColor = RiverDeepBlueDark
            ),
            border = BorderStroke(1.5.dp, if (!isRowing && !isGameOver && !isVictory) levelTheme.sailButtonGlow else RiverTimberBorder.copy(alpha = 0.5f)),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp, pressedElevation = 2.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .testTag("row_across_button")
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if (isRowing) "ROWING ACROSS..." else "CROSS RIVER (${targetBank.displayName.uppercase()})",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = GoldenBankGlow
                )
            }
        }

        // Secondary Action Controls Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Undo Move
            OutlinedButton(
                onClick = onUndo,
                enabled = canUndo && !isRowing,
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.dp, if (canUndo) RiverTimberBorder else Color(0x3364748B)),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = RiverDeepBlueDark.copy(alpha = 0.7f),
                    contentColor = Color.White,
                    disabledContentColor = Color.Gray
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
                    .testTag("undo_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Undo,
                    contentDescription = "Undo",
                    modifier = Modifier.size(16.dp),
                    tint = if (canUndo) Color.White else Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Undo", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            }

            // AI Hint
            OutlinedButton(
                onClick = onHintClick,
                enabled = !isRowing && !isGameOver && !isVictory,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = RiverDeepBlueDark.copy(alpha = 0.7f),
                    contentColor = RiverGoldGlow
                ),
                border = BorderStroke(1.dp, RiverGoldGlow.copy(alpha = 0.7f)),
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
                    .testTag("hint_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = "Hint",
                    modifier = Modifier.size(16.dp),
                    tint = RiverGoldGlow
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Hint", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = RiverGoldGlow)
            }

            // Restart Game
            OutlinedButton(
                onClick = onRestart,
                enabled = !isRowing,
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.dp, Color(0xFFEF4444).copy(alpha = 0.7f)),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = RiverDeepBlueDark.copy(alpha = 0.7f),
                    contentColor = Color(0xFFFCA5A5)
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
                    .testTag("restart_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Restart",
                    modifier = Modifier.size(16.dp),
                    tint = Color(0xFFFCA5A5)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Reset", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFFFCA5A5))
            }
        }
    }
}

