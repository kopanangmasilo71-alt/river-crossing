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
import com.example.model.LevelTheme
import com.example.ui.theme.GoldenBankGlow
import com.example.ui.theme.MenuBtnAmberBorder
import com.example.ui.theme.MenuBtnAmberBottom
import com.example.ui.theme.MenuBtnAmberMid
import com.example.ui.theme.MenuBtnAmberTop
import com.example.ui.theme.MenuBtnCyanBorder
import com.example.ui.theme.MenuBtnCyanBottom
import com.example.ui.theme.MenuBtnCyanMid
import com.example.ui.theme.MenuBtnCyanTop
import com.example.ui.theme.MenuBtnGreenBorder
import com.example.ui.theme.MenuBtnGreenBottom
import com.example.ui.theme.MenuBtnGreenMid
import com.example.ui.theme.MenuBtnGreenTop
import com.example.ui.theme.MenuBtnRedBorder
import com.example.ui.theme.MenuBtnRedBottom
import com.example.ui.theme.MenuBtnRedMid
import com.example.ui.theme.MenuBtnRedTop
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
    levelTheme: LevelTheme? = null,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    val currentScenario = scenario ?: com.example.model.PuzzleScenarios.CLASSIC
    val activeTheme = levelTheme ?: LevelTheme.forScenario(currentScenario)

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = activeTheme.sailButtonColors.last(),
                border = BorderStroke(1.8.dp, activeTheme.sailButtonBorderColors.first()),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onDismiss)
                    .testTag("rules_dismiss_button")
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(activeTheme.sailButtonColors)
                        )
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Got It! Let's Play", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = Color.White)
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
                        tint = activeTheme.headerAccentColor,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Level ${currentScenario.levelNumber}: Rules & Lore",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 17.sp,
                        color = activeTheme.headerTextColor
                    )
                }
                IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = activeTheme.headerAccentColor)
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
                    borderColor = MenuBtnAmberBorder,
                    bgGradient = listOf(Color(0xF038220A), Color(0xF0241405))
                ) {
                    Text(
                        text = currentScenario.description,
                        fontSize = 12.5.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "The log raft can carry the Farmer plus at most ${currentScenario.boatCapacity} passenger(s) at a time (or the Farmer alone).",
                        fontSize = 12.sp,
                        color = Color(0xFFFDE68A)
                    )
                }

                // Forbidden Situations
                RuleSection(
                    title = "🚫 Forbidden Situations",
                    borderColor = MenuBtnRedBorder,
                    bgGradient = listOf(Color(0xF03B1313), Color(0xF0220909))
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
                    borderColor = MenuBtnGreenBorder,
                    bgGradient = listOf(Color(0xF012351A), Color(0xF00A200F))
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
                    borderColor = MenuBtnCyanBorder,
                    bgGradient = listOf(Color(0xF00A263D), Color(0xF0071828))
                ) {
                    Text(
                        text = "1. First move a passenger that prevents immediate conflict.\n2. When returning, bring a passenger back if leaving them would trigger a conflict.\n3. Keep calm and alternate your ferrying moves!",
                        fontSize = 12.sp,
                        color = Color(0xFFFDE68A),
                        lineHeight = 16.sp
                    )
                }
            }
        },
        shape = RoundedCornerShape(24.dp),
        containerColor = activeTheme.headerBackgroundColors.last().copy(alpha = 0.97f),
        modifier = modifier
    )
}

@Composable
private fun RuleSection(
    title: String,
    borderColor: Color,
    bgGradient: List<Color> = listOf(Color(0xCC2A1308), Color(0xCC1A0B05)),
    content: @Composable () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Color.Transparent,
        border = BorderStroke(1.2.dp, borderColor.copy(alpha = 0.7f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .background(Brush.horizontalGradient(bgGradient))
                .padding(12.dp)
        ) {
            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 13.sp,
                    color = borderColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                content()
            }
        }
    }
}
