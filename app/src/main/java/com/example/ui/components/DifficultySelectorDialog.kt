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
import com.example.ui.theme.GoldenBankGlow
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
                color = Color(0xFF0284C7),
                border = BorderStroke(1.5.dp, GoldenBankGlow),
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
                                listOf(Color(0xFF0284C7), Color(0xFF0369A1))
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
                        tint = Color(0xFFBAE6FD)
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
                    color = Color(0xFFBAE6FD)
                )

                // Presets
                val presetModes = listOf(
                    DifficultyMode.STANDARD,
                    DifficultyMode.FOG_OF_WAR,
                    DifficultyMode.RESTRICTED_COMBOS,
                    DifficultyMode.EXTREME
                )

                presetModes.forEach { mode ->
                    val isSelected = selectedMode == mode
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = if (isSelected) Color(0xFF0D3B73) else WoodInsetPanel,
                        border = BorderStroke(
                            if (isSelected) 1.5.dp else 1.dp,
                            if (isSelected) GoldenBankGlow else Color(0xFF38BDF8).copy(alpha = 0.4f)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .clickable { selectPreset(mode) }
                            .testTag("difficulty_mode_${mode.id}")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = Color(0xFF072449),
                                    border = BorderStroke(1.dp, if (isSelected) GoldenBankGlow else Color(0xFF38BDF8).copy(alpha = 0.4f)),
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
                                        fontSize = 13.sp,
                                        color = if (isSelected) GoldenBankGlow else Color.White
                                    )
                                    Text(
                                        text = mode.subtitle,
                                        fontSize = 11.sp,
                                        color = Color(0xFFBAE6FD),
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
                                            tint = Color(0xFF072449),
                                            modifier = Modifier.size(14.dp)
                                        )
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
                    color = WoodInsetPanel,
                    border = BorderStroke(1.dp, Color(0xFF38BDF8).copy(alpha = 0.4f)),
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
                                color = Color(0xFFBAE6FD)
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
                                checkedTrackColor = Color(0xFF0284C7),
                                uncheckedThumbColor = Color(0xFF94A3B8),
                                uncheckedTrackColor = Color(0xFF072449)
                            ),
                            modifier = Modifier.testTag("toggle_hidden_items_switch")
                        )
                    }
                }

                // Toggle 2: Restricted Combinations
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = WoodInsetPanel,
                    border = BorderStroke(1.dp, Color(0xFF38BDF8).copy(alpha = 0.4f)),
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
                                color = Color(0xFFBAE6FD)
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
                                checkedTrackColor = Color(0xFF0284C7),
                                uncheckedThumbColor = Color(0xFF94A3B8),
                                uncheckedTrackColor = Color(0xFF072449)
                            ),
                            modifier = Modifier.testTag("toggle_restricted_combos_switch")
                        )
                    }
                }
            }
        },
        shape = RoundedCornerShape(24.dp),
        containerColor = Color(0xF2072449),
        modifier = modifier.testTag("difficulty_dialog")
    )
}
