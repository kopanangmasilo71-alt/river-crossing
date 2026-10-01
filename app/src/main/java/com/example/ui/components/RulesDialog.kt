package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GoldenBankGlow
import com.example.ui.theme.WoodButtonBottom
import com.example.ui.theme.WoodButtonTop
import com.example.ui.theme.WoodGoldenText
import com.example.ui.theme.WoodInsetPanel
import com.example.ui.theme.WoodSignboardBorder
import com.example.ui.theme.WoodSignboardDark
import com.example.ui.theme.WoodTextMuted

@Composable
fun RulesDialog(
    scenario: com.example.model.PuzzleScenario? = null,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    val currentScenario = scenario ?: com.example.model.PuzzleScenarios.CLASSIC

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color(0xFF0284C7),
                border = BorderStroke(1.5.dp, GoldenBankGlow),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onDismiss)
                    .testTag("rules_dismiss_button")
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color(0xFF0284C7), Color(0xFF0369A1))
                            )
                        )
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Got It! Let's Play", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = GoldenBankGlow)
                }
            }
        },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = null,
                        tint = GoldenBankGlow,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Level ${currentScenario.levelNumber}: Rules & Lore",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 17.sp,
                        color = Color.White
                    )
                }
                IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Color(0xFFE0F2FE))
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Narrative Section
                RuleSection(
                    title = "📖 The Story",
                    borderColor = Color(0xFF38BDF8).copy(alpha = 0.5f)
                ) {
                    Text(
                        text = currentScenario.description,
                        fontSize = 12.5.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "The log raft can carry the Farmer plus at most ${currentScenario.boatCapacity} passenger(s) at a time (or the Farmer alone).",
                        fontSize = 12.5.sp,
                        color = Color(0xFFBAE6FD)
                    )
                }

                // Forbidden Situations
                RuleSection(
                    title = "🚫 Forbidden Situations",
                    borderColor = Color(0xFFEF4444).copy(alpha = 0.6f)
                ) {
                    if (currentScenario.dangerRules.isNotEmpty()) {
                        currentScenario.dangerRules.forEach { rule ->
                            Text(
                                text = "• ${rule.predator.displayName} + ${rule.prey.displayName} without Farmer ➔ Attack!",
                                fontSize = 12.5.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFFFCA5A5)
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                        }
                    } else {
                        Text(
                            text = "No predator conflicts in this level! Focus on capacity and safe sequencing.",
                            fontSize = 12.sp,
                            color = GoldenBankGlow
                        )
                    }
                }

                // Mathematical State Logic
                RuleSection(
                    title = "⚙️ Level Parameters & Target",
                    borderColor = GoldenBankGlow.copy(alpha = 0.5f)
                ) {
                    Text(
                        text = "Passengers: ${currentScenario.items.joinToString { it.displayName }}",
                        fontSize = 12.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = "Target Optimal Moves: ${currentScenario.optimalMoves} moves | Target Time: ${currentScenario.targetTimeSeconds}s",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldenBankGlow
                    )
                }

                // Human-Friendly Strategy
                RuleSection(
                    title = "💡 The 'Take-Back' Strategy",
                    borderColor = Color(0xFF38BDF8).copy(alpha = 0.5f)
                ) {
                    Text(
                        text = "1. First move a passenger that prevents immediate conflict.\n2. When returning, bring a passenger back if leaving them would trigger a conflict.\n3. Keep calm and alternate your ferrying moves!",
                        fontSize = 12.sp,
                        color = Color(0xFFBAE6FD),
                        lineHeight = 16.sp
                    )
                }
            }
        },
        shape = RoundedCornerShape(24.dp),
        containerColor = Color(0xF2072449),
        modifier = modifier
    )
}

@Composable
private fun RuleSection(
    title: String,
    borderColor: Color,
    content: @Composable () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = WoodInsetPanel,
        border = BorderStroke(1.dp, borderColor),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = GoldenBankGlow
            )
            Spacer(modifier = Modifier.height(4.dp))
            content()
        }
    }
}
