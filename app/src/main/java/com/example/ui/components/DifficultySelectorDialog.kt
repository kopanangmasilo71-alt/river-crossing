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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DifficultyMode
import com.example.model.DifficultyModifiers
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
fun DifficultySelectorDialog(
    currentModifiers: DifficultyModifiers,
    levelTheme: LevelTheme = LevelTheme.SPRING_VALLEY,
    onApplyModifiers: (DifficultyModifiers) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedMode by remember { mutableStateOf(currentModifiers.mode) }
    var hideOppositeBank by remember { mutableStateOf(currentModifiers.hideOppositeBankItems) }
    var restrictCombos by remember { mutableStateOf(currentModifiers.restrictBoatCombinations) }

    val scrollState = rememberScrollState()

    fun selectPreset(mode: DifficultyMode) {
        selectedMode = mode
        val preset = DifficultyModifiers.fromMode(mode)
        hideOppositeBank = preset.hideOppositeBankItems
        restrictCombos = preset.restrictBoatCombinations
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = levelTheme.sailButtonColors.first(),
                border = BorderStroke(1.8.dp, levelTheme.sailButtonBorderColors.first()),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val finalModifiers = if (selectedMode != DifficultyMode.CUSTOM) {
                            DifficultyModifiers(
                                mode = selectedMode,
                                hideOppositeBankItems = hideOppositeBank,
                                restrictBoatCombinations = restrictCombos
                            )
                        } else {
                            DifficultyModifiers(
                                mode = DifficultyMode.CUSTOM,
                                hideOppositeBankItems = hideOppositeBank,
                                restrictBoatCombinations = restrictCombos
                            )
                        }
                        onApplyModifiers(finalModifiers)
                        onDismiss()
                    }
                    .testTag("confirm_difficulty_button")
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                levelTheme.sailButtonColors
                            )
                        )
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Apply Settings & Play",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp,
                        color = Color.White
                    )
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
                        imageVector = Icons.Default.Tune,
                        contentDescription = null,
                        tint = GoldenBankGlow,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Difficulty Variations",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .size(28.dp)
                        .testTag("close_difficulty_dialog_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = GoldenBankGlow
                    )
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
                Text(
                    text = "Choose a challenge preset or toggle custom river rules:",
                    fontSize = 12.sp,
                    color = Color(0xFFFDE68A)
                )

                // Presets with vibrant difficulty accents
                val presetModes = listOf(
                    Triple(DifficultyMode.STANDARD, MenuBtnGreenBorder, listOf(Color(0xF012351A), Color(0xF00A200F))),
                    Triple(DifficultyMode.FOG_OF_WAR, MenuBtnCyanBorder, listOf(Color(0xF00A263D), Color(0xF0071828))),
                    Triple(DifficultyMode.RESTRICTED_COMBOS, MenuBtnAmberBorder, listOf(Color(0xF038220A), Color(0xF0241405))),
                    Triple(DifficultyMode.EXTREME, MenuBtnRedBorder, listOf(Color(0xF03B1313), Color(0xF0220909)))
                )

                presetModes.forEach { (mode, accentBorder, gradientBg) ->
                    val isSelected = selectedMode == mode
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Color.Transparent,
                        border = BorderStroke(
                            if (isSelected) 2.dp else 1.dp,
                            if (isSelected) GoldenBankGlow else accentBorder.copy(alpha = 0.5f)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .clickable { selectPreset(mode) }
                            .testTag("difficulty_mode_${mode.id}")
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    Brush.horizontalGradient(
                                        if (isSelected) {
                                            listOf(gradientBg[0], Color(0xF03A1C08))
                                        } else {
                                            listOf(Color(0xDD231006), Color(0xDD180B04))
                                        }
                                    )
                                )
                                .padding(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier.weight(1f),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        shape = CircleShape,
                                        color = accentBorder.copy(alpha = 0.2f),
                                        border = BorderStroke(1.2.dp, if (isSelected) GoldenBankGlow else accentBorder),
                                        modifier = Modifier.size(36.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(text = mode.iconEmoji, fontSize = 18.sp)
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = mode.title,
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 13.5.sp,
                                            color = if (isSelected) GoldenBankGlow else Color.White
                                        )
                                        Text(
                                            text = mode.subtitle,
                                            fontSize = 11.sp,
                                            color = Color(0xFFFDE68A),
                                            lineHeight = 14.sp
                                        )
                                    }
                                }

                                if (isSelected) {
                                    Surface(
                                        shape = CircleShape,
                                        color = GoldenBankGlow,
                                        modifier = Modifier.size(22.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Selected",
                                                tint = Color(0xFF2B1307),
                                                modifier = Modifier.size(14.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Custom Variation Switches
                Text(
                    text = "⚙️ Custom Rule Toggles",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = GoldenBankGlow
                )

                // Toggle 1: Hidden Items / Fog of War
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xCC2A1308),
                    border = BorderStroke(1.dp, MenuBtnCyanBorder.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "🌫️", fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Hidden Shore (River Mist)",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = Color.White
                                )
                            }
                            Text(
                                text = "Opposite bank is veiled in fog until farmer arrives.",
                                fontSize = 10.sp,
                                color = Color(0xFFFDE68A)
                            )
                        }
                        Switch(
                            checked = hideOppositeBank,
                            onCheckedChange = {
                                hideOppositeBank = it
                                selectedMode = DifficultyMode.CUSTOM
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = GoldenBankGlow,
                                checkedTrackColor = MenuBtnGreenMid,
                                uncheckedThumbColor = Color(0xFF94A3B8),
                                uncheckedTrackColor = Color(0x664A260E)
                            ),
                            modifier = Modifier.testTag("toggle_hidden_items_switch")
                        )
                    }
                }

                // Toggle 2: Restricted Combinations
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xCC2A1308),
                    border = BorderStroke(1.dp, MenuBtnAmberBorder.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "⛔", fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Restricted Boat Cargo",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = Color.White
                                )
                            }
                            Text(
                                text = "Forbids conflicting predator or crop pairs in the boat.",
                                fontSize = 10.sp,
                                color = Color(0xFFFDE68A)
                            )
                        }
                        Switch(
                            checked = restrictCombos,
                            onCheckedChange = {
                                restrictCombos = it
                                selectedMode = DifficultyMode.CUSTOM
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = GoldenBankGlow,
                                checkedTrackColor = MenuBtnAmberMid,
                                uncheckedThumbColor = Color(0xFF94A3B8),
                                uncheckedTrackColor = Color(0x664A260E)
                            ),
                            modifier = Modifier.testTag("toggle_restricted_combos_switch")
                        )
                    }
                }
            }
        },
        shape = RoundedCornerShape(24.dp),
        containerColor = levelTheme.headerBackgroundColors.last().copy(alpha = 0.96f),
        modifier = modifier.testTag("difficulty_dialog")
    )
}
