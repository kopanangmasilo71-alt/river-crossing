package com.example.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.VolumeMute
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.app.Activity
import com.example.R
import com.example.ads.AdManager
import com.example.ads.AdMobBanner
import com.example.model.GameItem
import com.example.model.PuzzleScenarios
import com.example.ui.components.WoodCard
import com.example.ui.components.WoodInsetBox
import com.example.ui.components.WoodScreenContainer
import com.example.ui.components.WoodTopAppBar
import com.example.ui.theme.GoldenBankGlow
import com.example.ui.theme.WoodButtonBottom
import com.example.ui.theme.WoodButtonTop
import com.example.ui.theme.WoodGoldenText
import com.example.ui.theme.WoodInsetPanel
import com.example.ui.theme.WoodSignboardBorder
import com.example.ui.theme.WoodSignboardDark
import com.example.ui.theme.WoodSignboardLight
import com.example.ui.theme.WoodTextMuted
import com.example.viewmodel.RiverGameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: RiverGameViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val isMuted by viewModel.isMuted.collectAsState()
    val isHapticsEnabled by viewModel.isHapticsEnabled.collectAsState()
    val playerName by viewModel.playerName.collectAsState()
    val modifiers by viewModel.difficultyModifiers.collectAsState()
    val completedCount by viewModel.completedLevelsCount.collectAsState()
    val totalRunsCount by viewModel.totalRunsCount.collectAsState()
    val unlockedLevelIds by viewModel.unlockedLevelIds.collectAsState()

    var nameInput by remember(playerName) { mutableStateOf(playerName) }
    var showClearDialog by remember { mutableStateOf(false) }
    var showUnlockAllDialog by remember { mutableStateOf(false) }

    WoodScreenContainer(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            WoodTopAppBar(
                title = "Captain's Log & Settings",
                subtitle = "Preferences & Sound",
                onBack = onBack
            )

            // Banner ad at the top of Settings
            AdMobBanner(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
            // 1. PLAYER PROFILE CARD
            SettingsSectionHeader(title = "CAPTAIN PROFILE")
            WoodCard(shape = RoundedCornerShape(18.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = WoodInsetPanel,
                        border = BorderStroke(2.dp, GoldenBankGlow),
                        modifier = Modifier.size(54.dp),
                        shadowElevation = 4.dp
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_farmer),
                            contentDescription = "Player Avatar",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Captain Name",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = WoodTextMuted
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = nameInput,
                            onValueChange = {
                                nameInput = it
                                viewModel.setPlayerName(it)
                            },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = GoldenBankGlow,
                                unfocusedBorderColor = WoodSignboardBorder,
                                focusedTextColor = GoldenBankGlow,
                                unfocusedTextColor = WoodGoldenText,
                                focusedContainerColor = WoodInsetPanel,
                                unfocusedContainerColor = WoodInsetPanel
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("settings_player_name_input")
                        )
                    }
                }
            }

            // 2. AUDIO & HAPTICS SETTINGS
            SettingsSectionHeader(title = "AUDIO & FEEDBACK")
            WoodCard(shape = RoundedCornerShape(18.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Sound Effects switch
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = WoodInsetPanel,
                                border = BorderStroke(1.dp, GoldenBankGlow),
                                modifier = Modifier.size(38.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = if (isMuted) Icons.Default.VolumeMute else Icons.Default.VolumeUp,
                                        contentDescription = null,
                                        tint = GoldenBankGlow,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Synthesized Sound Effects",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = WoodGoldenText
                                )
                                Text(
                                    text = "Animal calls, water splash & oar strokes",
                                    fontSize = 11.sp,
                                    color = WoodTextMuted
                                )
                            }
                        }
                        Switch(
                            checked = !isMuted,
                            onCheckedChange = { viewModel.toggleMute() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = GoldenBankGlow,
                                checkedTrackColor = WoodButtonTop,
                                uncheckedThumbColor = WoodTextMuted,
                                uncheckedTrackColor = WoodInsetPanel
                            ),
                            modifier = Modifier.testTag("settings_sound_switch")
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Haptics switch
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = WoodInsetPanel,
                                border = BorderStroke(1.dp, GoldenBankGlow),
                                modifier = Modifier.size(38.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Vibration,
                                        contentDescription = null,
                                        tint = GoldenBankGlow,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Haptic Feedback",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = WoodGoldenText
                                )
                                Text(
                                    text = "Tactile vibration when boarding or docking",
                                    fontSize = 11.sp,
                                    color = WoodTextMuted
                                )
                            }
                        }
                        Switch(
                            checked = isHapticsEnabled,
                            onCheckedChange = { viewModel.toggleHaptics() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = GoldenBankGlow,
                                checkedTrackColor = WoodButtonTop,
                                uncheckedThumbColor = WoodTextMuted,
                                uncheckedTrackColor = WoodInsetPanel
                            ),
                            modifier = Modifier.testTag("settings_haptics_switch")
                        )
                    }
                }
            }

            // 3. CHALLENGE MODIFIERS
            SettingsSectionHeader(title = "GAMEPLAY CHALLENGES & MODIFIERS")
            WoodCard(shape = RoundedCornerShape(18.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Mystery Shore Fog of War
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = WoodInsetPanel,
                                border = BorderStroke(1.dp, GoldenBankGlow),
                                modifier = Modifier.size(38.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.VisibilityOff,
                                        contentDescription = null,
                                        tint = GoldenBankGlow,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Mystery Shore (Fog of War)",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = WoodGoldenText
                                )
                                Text(
                                    text = "Hides opposite shore items until boat docks",
                                    fontSize = 11.sp,
                                    color = WoodTextMuted
                                )
                            }
                        }
                        Switch(
                            checked = modifiers.hideOppositeBankItems,
                            onCheckedChange = { viewModel.toggleHiddenItems() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = GoldenBankGlow,
                                checkedTrackColor = WoodButtonTop,
                                uncheckedThumbColor = WoodTextMuted,
                                uncheckedTrackColor = WoodInsetPanel
                            ),
                            modifier = Modifier.testTag("settings_fog_switch")
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Restricted Boat Combinations
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = WoodInsetPanel,
                                border = BorderStroke(1.dp, GoldenBankGlow),
                                modifier = Modifier.size(38.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Security,
                                        contentDescription = null,
                                        tint = GoldenBankGlow,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Restricted Boat Combinations",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = WoodGoldenText
                                )
                                Text(
                                    text = "Conflicting items cannot share boat cargo",
                                    fontSize = 11.sp,
                                    color = WoodTextMuted
                                )
                            }
                        }
                        Switch(
                            checked = modifiers.restrictBoatCombinations,
                            onCheckedChange = { viewModel.toggleRestrictedCombos() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = GoldenBankGlow,
                                checkedTrackColor = WoodButtonTop,
                                uncheckedThumbColor = WoodTextMuted,
                                uncheckedTrackColor = WoodInsetPanel
                            ),
                            modifier = Modifier.testTag("settings_restricted_switch")
                        )
                    }
                }
            }

            // 4. DATA & LOCAL ROOM DATABASE MANAGEMENT
            SettingsSectionHeader(title = "LOGBOOK & DATABASE")
            WoodCard(shape = RoundedCornerShape(18.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    WoodInsetBox(
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "$completedCount / ${PuzzleScenarios.ALL.size}",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 16.sp,
                                    color = GoldenBankGlow
                                )
                                Text(
                                    text = "Levels Solved",
                                    fontSize = 11.sp,
                                    color = WoodTextMuted
                                )
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "$totalRunsCount",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 16.sp,
                                    color = WoodGoldenText
                                )
                                Text(
                                    text = "Total Runs Logged",
                                    fontSize = 11.sp,
                                    color = WoodTextMuted
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Unlock All Levels with Rewarded Ad
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = WoodButtonTop,
                        border = BorderStroke(1.dp, GoldenBankGlow),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showUnlockAllDialog = true }
                            .testTag("settings_unlock_all_levels_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.LockOpen,
                                contentDescription = null,
                                tint = GoldenBankGlow,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Unlock All ${PuzzleScenarios.ALL.size} Levels (${unlockedLevelIds.size}/${PuzzleScenarios.ALL.size} Unlocked)",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldenBankGlow
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFF4A1A1A),
                        border = BorderStroke(1.dp, Color(0xFFDC2626)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showClearDialog = true }
                            .testTag("settings_reset_database_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.DeleteOutline,
                                contentDescription = null,
                                tint = Color(0xFFFF7A7A),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Reset High Scores Database",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFF7A7A)
                            )
                        }
                    }
                }
            }

            // 5. ABOUT
            SettingsSectionHeader(title = "ABOUT")
            WoodCard(shape = RoundedCornerShape(18.dp)) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "River Crossing Puzzle Game",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp,
                        color = WoodGoldenText
                    )
                    Text(
                        text = "${PuzzleScenarios.ALL.size} Handcrafted Logic Levels • Room Database High Scores • State-Space AI Solver • Procedural Audio Engine",
                        fontSize = 11.sp,
                        color = WoodTextMuted
                    )
                    Text(
                        text = "Version 1.0.0 • River Academy Edition",
                        fontSize = 10.sp,
                        color = GoldenBankGlow
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

    if (showUnlockAllDialog) {
        AlertDialog(
            onDismissRequest = { showUnlockAllDialog = false },
            containerColor = Color(0xF2072449),
            shape = RoundedCornerShape(20.dp),
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LockOpen,
                        contentDescription = "Unlock All Levels",
                        tint = GoldenBankGlow,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Unlock All ${PuzzleScenarios.ALL.size} Levels?",
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Unlock all ${PuzzleScenarios.ALL.size} handcrafted levels at once for complete access to the expedition!",
                        color = Color(0xFFBAE6FD),
                        fontSize = 13.sp
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showUnlockAllDialog = false
                        viewModel.unlockAllLevels()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LockOpen,
                            contentDescription = null,
                            tint = GoldenBankGlow,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Unlock All Levels", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { showUnlockAllDialog = false }) {
                    Text("Cancel", color = Color(0xFFBAE6FD))
                }
            }
        )
    }

    if (showClearDialog) {
        AlertDialog(
            onDismissRequest = { showClearDialog = false },
            containerColor = Color(0xF2072449),
            shape = RoundedCornerShape(20.dp),
            title = {
                Text(
                    "Reset Local High Scores?",
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            },
            text = {
                Text(
                    "Are you sure you want to delete all saved scores and least moves from the local Room database? This cannot be undone.",
                    color = Color(0xFFBAE6FD)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.clearHighScoreHistory(null)
                        showClearDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626))
                ) {
                    Text("Delete All", fontWeight = FontWeight.Bold, color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearDialog = false }) {
                    Text("Cancel", color = Color(0xFFBAE6FD))
                }
            }
        )
    }
}

@Composable
private fun SettingsSectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 11.sp,
        fontWeight = FontWeight.ExtraBold,
        color = GoldenBankGlow,
        letterSpacing = 1.sp,
        modifier = Modifier.padding(start = 4.dp, top = 2.dp)
    )
}
