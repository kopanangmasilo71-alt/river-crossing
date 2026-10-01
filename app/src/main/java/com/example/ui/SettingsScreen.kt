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
import com.example.ui.theme.MenuBtnGreenTop
import com.example.ui.theme.MenuBtnGreenMid
import com.example.ui.theme.MenuBtnGreenBottom
import com.example.ui.theme.MenuBtnGreenBorder
import com.example.ui.theme.MenuBtnBlueTop
import com.example.ui.theme.MenuBtnBlueMid
import com.example.ui.theme.MenuBtnBlueBottom
import com.example.ui.theme.MenuBtnBlueBorder
import com.example.ui.theme.MenuBtnAmberTop
import com.example.ui.theme.MenuBtnAmberMid
import com.example.ui.theme.MenuBtnAmberBottom
import com.example.ui.theme.MenuBtnAmberBorder
import com.example.ui.theme.MenuBtnPurpleTop
import com.example.ui.theme.MenuBtnPurpleMid
import com.example.ui.theme.MenuBtnPurpleBottom
import com.example.ui.theme.MenuBtnPurpleBorder
import com.example.ui.theme.MenuBtnCyanTop
import com.example.ui.theme.MenuBtnCyanMid
import com.example.ui.theme.MenuBtnCyanBottom
import com.example.ui.theme.MenuBtnCyanBorder
import com.example.ui.theme.MenuBtnRedTop
import com.example.ui.theme.MenuBtnRedMid
import com.example.ui.theme.MenuBtnRedBottom
import com.example.ui.theme.MenuBtnRedBorder
import com.example.ui.components.VibrantSectionHeader
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
            // 1. PLAYER PROFILE CARD (Warm Golden Amber)
            VibrantSectionHeader(title = "Captain Profile", accentColor = GoldenBankGlow)
            WoodCard(
                shape = RoundedCornerShape(18.dp),
                gradientColors = listOf(Color(0xF04A260E), Color(0xF02B1405)),
                borderColor = GoldenBankGlow
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xDD220F05),
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
                            color = Color(0xFFFEF3C7)
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
                                unfocusedBorderColor = Color(0xFFD97706),
                                focusedTextColor = GoldenBankGlow,
                                unfocusedTextColor = Color.White,
                                focusedContainerColor = Color(0xDD220F05),
                                unfocusedContainerColor = Color(0xDD220F05)
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("settings_player_name_input")
                        )
                    }
                }
            }

            // 2. AUDIO & HAPTICS SETTINGS (Vibrant Cyan / Teal)
            VibrantSectionHeader(title = "Audio & Feedback", accentColor = MenuBtnCyanTop)
            WoodCard(
                shape = RoundedCornerShape(18.dp),
                gradientColors = listOf(Color(0xF00E3D3A), Color(0xF0072624)),
                borderColor = MenuBtnCyanBorder
            ) {
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
                                color = Color(0xDD072624),
                                border = BorderStroke(1.2.dp, MenuBtnCyanBorder),
                                modifier = Modifier.size(38.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = if (isMuted) Icons.Default.VolumeMute else Icons.Default.VolumeUp,
                                        contentDescription = null,
                                        tint = if (isMuted) Color(0xFFEF4444) else MenuBtnCyanTop,
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
                                    color = Color.White
                                )
                                Text(
                                    text = "Animal calls, water splash & oar strokes",
                                    fontSize = 11.sp,
                                    color = Color(0xFFCCFBF1)
                                )
                            }
                        }
                        Switch(
                            checked = !isMuted,
                            onCheckedChange = { viewModel.toggleMute() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = MenuBtnCyanMid,
                                uncheckedThumbColor = Color(0xFF99F6E4),
                                uncheckedTrackColor = Color(0xDD072624)
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
                                color = Color(0xDD072624),
                                border = BorderStroke(1.2.dp, MenuBtnCyanBorder),
                                modifier = Modifier.size(38.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Vibration,
                                        contentDescription = null,
                                        tint = MenuBtnCyanTop,
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
                                    color = Color.White
                                )
                                Text(
                                    text = "Tactile vibration when boarding or docking",
                                    fontSize = 11.sp,
                                    color = Color(0xFFCCFBF1)
                                )
                            }
                        }
                        Switch(
                            checked = isHapticsEnabled,
                            onCheckedChange = { viewModel.toggleHaptics() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = MenuBtnCyanMid,
                                uncheckedThumbColor = Color(0xFF99F6E4),
                                uncheckedTrackColor = Color(0xDD072624)
                            ),
                            modifier = Modifier.testTag("settings_haptics_switch")
                        )
                    }
                }
            }

            // 3. CHALLENGE MODIFIERS (Royal Amethyst Purple)
            VibrantSectionHeader(title = "Gameplay Modifiers", accentColor = MenuBtnPurpleTop)
            WoodCard(
                shape = RoundedCornerShape(18.dp),
                gradientColors = listOf(Color(0xF0300F48), Color(0xF01B072B)),
                borderColor = MenuBtnPurpleBorder
            ) {
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
                                color = Color(0xDD1B072B),
                                border = BorderStroke(1.2.dp, MenuBtnPurpleBorder),
                                modifier = Modifier.size(38.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.VisibilityOff,
                                        contentDescription = null,
                                        tint = MenuBtnPurpleTop,
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
                                    color = Color.White
                                )
                                Text(
                                    text = "Hides opposite shore items until boat docks",
                                    fontSize = 11.sp,
                                    color = Color(0xFFF3E8FF)
                                )
                            }
                        }
                        Switch(
                            checked = modifiers.hideOppositeBankItems,
                            onCheckedChange = { viewModel.toggleHiddenItems() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = MenuBtnPurpleMid,
                                uncheckedThumbColor = Color(0xFFE9D5FF),
                                uncheckedTrackColor = Color(0xDD1B072B)
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
                                color = Color(0xDD1B072B),
                                border = BorderStroke(1.2.dp, MenuBtnPurpleBorder),
                                modifier = Modifier.size(38.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Security,
                                        contentDescription = null,
                                        tint = MenuBtnPurpleTop,
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
                                    color = Color.White
                                )
                                Text(
                                    text = "Conflicting items cannot share boat cargo",
                                    fontSize = 11.sp,
                                    color = Color(0xFFF3E8FF)
                                )
                            }
                        }
                        Switch(
                            checked = modifiers.restrictBoatCombinations,
                            onCheckedChange = { viewModel.toggleRestrictedCombos() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = MenuBtnPurpleMid,
                                uncheckedThumbColor = Color(0xFFE9D5FF),
                                uncheckedTrackColor = Color(0xDD1B072B)
                            ),
                            modifier = Modifier.testTag("settings_restricted_switch")
                        )
                    }
                }
            }

            // 4. DATA & LOCAL ROOM DATABASE MANAGEMENT (Lush Emerald Green)
            VibrantSectionHeader(title = "Logbook & Progress", accentColor = MenuBtnGreenTop)
            WoodCard(
                shape = RoundedCornerShape(18.dp),
                gradientColors = listOf(Color(0xF012361B), Color(0xF00B2110)),
                borderColor = MenuBtnGreenBorder
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xCC091D0E),
                        border = BorderStroke(1.2.dp, MenuBtnGreenBorder),
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
                                    fontSize = 17.sp,
                                    color = GoldenBankGlow
                                )
                                Text(
                                    text = "Levels Solved",
                                    fontSize = 11.sp,
                                    color = Color(0xFF86EFAC)
                                )
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "$totalRunsCount",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 17.sp,
                                    color = Color.White
                                )
                                Text(
                                    text = "Total Runs Logged",
                                    fontSize = 11.sp,
                                    color = Color(0xFF86EFAC)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Unlock All Levels with Rewarded Ad (Vibrant Golden Amber 3D pill)
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = MenuBtnAmberTop,
                        border = BorderStroke(1.8.dp, MenuBtnAmberBorder),
                        shadowElevation = 4.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showUnlockAllDialog = true }
                            .testTag("settings_unlock_all_levels_button")
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(MenuBtnAmberTop, MenuBtnAmberMid, MenuBtnAmberBottom)
                                    )
                                )
                                .padding(vertical = 11.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LockOpen,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "Unlock All ${PuzzleScenarios.ALL.size} Levels (${unlockedLevelIds.size}/${PuzzleScenarios.ALL.size} Unlocked)",
                                    fontSize = 12.5.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White,
                                    letterSpacing = 0.5.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Reset High Scores Database (Vibrant Ruby Red 3D pill)
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = MenuBtnRedTop,
                        border = BorderStroke(1.8.dp, MenuBtnRedBorder),
                        shadowElevation = 4.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showClearDialog = true }
                            .testTag("settings_reset_database_button")
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(MenuBtnRedTop, MenuBtnRedMid, MenuBtnRedBottom)
                                    )
                                )
                                .padding(vertical = 11.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DeleteOutline,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "Reset High Scores Database",
                                    fontSize = 12.5.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White,
                                    letterSpacing = 0.5.sp
                                )
                            }
                        }
                    }
                }
            }

            // 5. ABOUT
            SettingsSectionHeader(title = "ABOUT")
            WoodCard(
                shape = RoundedCornerShape(18.dp),
                gradientColors = listOf(Color(0xF04A260E), Color(0xF02B1405)),
                borderColor = GoldenBankGlow
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "River Crossing Puzzle Game",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                    Text(
                        text = "${PuzzleScenarios.ALL.size} Handcrafted Logic Levels • Room Database High Scores • State-Space AI Solver • Procedural Audio Engine",
                        fontSize = 11.sp,
                        color = Color(0xFFFEF3C7)
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
            containerColor = Color(0xF4281206),
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
                        color = GoldenBankGlow
                    )
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Unlock all ${PuzzleScenarios.ALL.size} handcrafted levels at once for complete access to the expedition!",
                        color = Color(0xFFFEF3C7),
                        fontSize = 13.sp
                    )
                }
            },
            confirmButton = {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MenuBtnAmberTop,
                    border = BorderStroke(1.2.dp, MenuBtnAmberBorder),
                    modifier = Modifier.clickable {
                        showUnlockAllDialog = false
                        viewModel.unlockAllLevels()
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .background(Brush.verticalGradient(listOf(MenuBtnAmberTop, MenuBtnAmberMid, MenuBtnAmberBottom)))
                            .padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LockOpen,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Unlock All", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { showUnlockAllDialog = false }) {
                    Text("Cancel", color = Color(0xFFFEF3C7))
                }
            }
        )
    }

    if (showClearDialog) {
        AlertDialog(
            onDismissRequest = { showClearDialog = false },
            containerColor = Color(0xF4281206),
            shape = RoundedCornerShape(20.dp),
            title = {
                Text(
                    "Reset Local High Scores?",
                    fontWeight = FontWeight.ExtraBold,
                    color = GoldenBankGlow
                )
            },
            text = {
                Text(
                    "Are you sure you want to delete all saved scores and least moves from the local Room database? This cannot be undone.",
                    color = Color(0xFFFEF3C7)
                )
            },
            confirmButton = {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MenuBtnRedTop,
                    border = BorderStroke(1.2.dp, MenuBtnRedBorder),
                    modifier = Modifier.clickable {
                        viewModel.clearHighScoreHistory(null)
                        showClearDialog = false
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .background(Brush.verticalGradient(listOf(MenuBtnRedTop, MenuBtnRedMid, MenuBtnRedBottom)))
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Text("Delete All", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearDialog = false }) {
                    Text("Cancel", color = Color(0xFFFEF3C7))
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
