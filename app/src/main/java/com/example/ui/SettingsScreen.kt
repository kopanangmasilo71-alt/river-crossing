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
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.DirectionsBoat
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
import androidx.compose.runtime.DisposableEffect
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
import com.example.model.BoatSpeed
import com.example.model.GameItem
import com.example.model.PuzzleScenarios
import com.example.model.WeatherEffectType
import com.example.ui.components.spritesheet.SpriteSheetAnimation
import com.example.ui.components.spritesheet.SpriteSheetSpec
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
                gradientColors = listOf(Color(0xF00D3560), Color(0xF007203A)),
                borderColor = MenuBtnBlueBorder
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xDD072449),
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
                            color = Color(0xFFBAE6FD)
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
                                unfocusedBorderColor = MenuBtnBlueBorder,
                                focusedTextColor = GoldenBankGlow,
                                unfocusedTextColor = Color.White,
                                focusedContainerColor = Color(0xDD072449),
                                unfocusedContainerColor = Color(0xDD072449)
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("settings_player_name_input")
                        )
                    }
                }
            }

            // 2. BOAT ROWING SPEED & ANIMATION (Nautical Golden Amber)
            val currentBoatSpeed by viewModel.boatSpeed.collectAsState()

            VibrantSectionHeader(title = "Boat Speed & Animation", accentColor = GoldenBankGlow)
            WoodCard(
                shape = RoundedCornerShape(18.dp),
                gradientColors = listOf(Color(0xF04A2A08), Color(0xF02C1704)),
                borderColor = MenuBtnAmberBorder
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xDD2A1506),
                            border = BorderStroke(1.2.dp, GoldenBankGlow),
                            modifier = Modifier.size(38.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.DirectionsBoat,
                                    contentDescription = null,
                                    tint = GoldenBankGlow,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Crossing Speed",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color.White
                            )
                            Text(
                                text = currentBoatSpeed.description,
                                fontSize = 11.5.sp,
                                color = GoldenBankGlow,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 4-choice segmented speed selector
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        BoatSpeed.values().forEach { speed ->
                            val isSelected = currentBoatSpeed == speed
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (isSelected) MenuBtnAmberTop else Color(0xDD2A1506),
                                border = BorderStroke(
                                    if (isSelected) 1.8.dp else 1.dp,
                                    if (isSelected) GoldenBankGlow else Color(0x55D97706)
                                ),
                                shadowElevation = if (isSelected) 4.dp else 1.dp,
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable { viewModel.setBoatSpeed(speed) }
                                    .testTag("settings_boat_speed_${speed.id}")
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 2.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = speed.iconEmoji,
                                        fontSize = 16.sp
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = speed.title,
                                        fontSize = 11.sp,
                                        fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                                        color = if (isSelected) Color.White else Color(0xFFFEF3C7)
                                    )
                                    Text(
                                        text = "${speed.durationMs / 1000.0}s",
                                        fontSize = 9.5.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = if (isSelected) GoldenBankGlow else Color(0x99D4A373)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 3. AUDIO & HAPTICS SETTINGS (Vibrant Cyan / Teal)
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

                    Spacer(modifier = Modifier.height(14.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color(0x335EEAD4))
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    // Procedural Biome Audio Engine Showcase
                    var auditionBiome by remember { mutableStateOf(WeatherEffectType.SPRING_PETALS) }
                    var isPreviewingAmbience by remember { mutableStateOf(false) }

                    DisposableEffect(Unit) {
                        onDispose {
                            if (isPreviewingAmbience) {
                                viewModel.stopBiomeAmbience()
                            }
                        }
                    }

                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Procedural Biome Audio Engine",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.5.sp,
                                color = MenuBtnCyanTop
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Synthesizes real-time continuous river currents, authentic bird calls, and terrain footsteps natively tailored to the active biome.",
                            fontSize = 11.sp,
                            color = Color(0xFFCCFBF1).copy(alpha = 0.85f),
                            lineHeight = 15.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Biome selection chips
                        val biomeList = listOf(
                            WeatherEffectType.SPRING_PETALS to "🌸 Spring",
                            WeatherEffectType.AUTUMN_LEAVES to "🍂 Autumn",
                            WeatherEffectType.ALPINE_MIST to "⛰️ Alpine",
                            WeatherEffectType.SAVANNAH_DUST to "🌾 Savanna",
                            WeatherEffectType.MIDNIGHT_FIREFLIES to "✨ Night",
                            WeatherEffectType.TWILIGHT_MOTES to "🌌 Twilight",
                            WeatherEffectType.OASIS_MIRAGE to "🌴 Oasis",
                            WeatherEffectType.AURORA_SHIMMER to "❄️ Aurora"
                        )

                        val chipScrollState = rememberScrollState()
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(chipScrollState),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            biomeList.forEach { (biome, label) ->
                                val isSelected = auditionBiome == biome
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isSelected) MenuBtnCyanMid else Color(0xDD072624),
                                    border = BorderStroke(
                                        if (isSelected) 1.5.dp else 1.dp,
                                        if (isSelected) MenuBtnCyanTop else Color(0x445EEAD4)
                                    ),
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable {
                                            auditionBiome = biome
                                            if (isPreviewingAmbience) {
                                                viewModel.startBiomeAmbience(biome)
                                            }
                                        }
                                ) {
                                    Text(
                                        text = label,
                                        fontSize = 10.5.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) Color.White else Color(0xFFCCFBF1),
                                        modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Interactive audition triggers (River Flow, Bird Call, Footsteps, Continuous Ambience)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            // River Flow Button
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xDD072624),
                                border = BorderStroke(1.dp, MenuBtnCyanBorder),
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        viewModel.playRiverFlowSound(auditionBiome)
                                    }
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 7.dp, horizontal = 4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "🌊", fontSize = 14.sp)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "River Flow",
                                        fontSize = 9.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MenuBtnCyanTop
                                    )
                                }
                            }

                            // Bird Call Button
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xDD072624),
                                border = BorderStroke(1.dp, MenuBtnCyanBorder),
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        viewModel.playBiomeBirdCall(auditionBiome)
                                    }
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 7.dp, horizontal = 4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "🐦", fontSize = 14.sp)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "Bird Call",
                                        fontSize = 9.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MenuBtnCyanTop
                                    )
                                }
                            }

                            // Footstep Button
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xDD072624),
                                border = BorderStroke(1.dp, MenuBtnCyanBorder),
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        viewModel.playFootstep(auditionBiome, isBoardingRaft = false)
                                    }
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 7.dp, horizontal = 4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "👟", fontSize = 14.sp)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "Footstep",
                                        fontSize = 9.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MenuBtnCyanTop
                                    )
                                }
                            }

                            // Continuous Ambience Loop Preview Button
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isPreviewingAmbience) MenuBtnCyanMid else Color(0xDD072624),
                                border = BorderStroke(1.dp, if (isPreviewingAmbience) Color.White else MenuBtnCyanBorder),
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        val newState = !isPreviewingAmbience
                                        isPreviewingAmbience = newState
                                        if (newState) {
                                            viewModel.startBiomeAmbience(auditionBiome)
                                        } else {
                                            viewModel.stopBiomeAmbience()
                                        }
                                    }
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 7.dp, horizontal = 4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = if (isPreviewingAmbience) "⏹️" else "▶️", fontSize = 14.sp)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = if (isPreviewingAmbience) "Stop Loop" else "Ambient Loop",
                                        fontSize = 9.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isPreviewingAmbience) Color.White else MenuBtnCyanTop
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "Jump Animation Spritesheets",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = MenuBtnCyanTop
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "6-frame procedural jump animation when arriving on the bank:",
                            fontSize = 10.5.sp,
                            color = Color(0xFFCCFBF1).copy(alpha = 0.85f)
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            var isRabbitJumping by remember { mutableStateOf(false) }
                            var isDogJumping by remember { mutableStateOf(false) }

                            // Rabbit Jump Preview Card
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xDD072624),
                                border = BorderStroke(1.dp, MenuBtnCyanBorder),
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable {
                                        isRabbitJumping = true
                                        viewModel.triggerPreviewJump(GameItem.RABBIT)
                                    }
                            ) {
                                Column(
                                    modifier = Modifier.padding(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box(
                                        modifier = Modifier.size(56.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        SpriteSheetAnimation(
                                            spec = SpriteSheetSpec.RABBIT_JUMP,
                                            isPlaying = isRabbitJumping,
                                            onAnimationEnd = { isRabbitJumping = false },
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "🐰 Rabbit Jump",
                                        fontSize = 10.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "6 frames · Tap to leap",
                                        fontSize = 8.5.sp,
                                        color = Color(0xFFCCFBF1)
                                    )
                                }
                            }

                            // Dog Jump Preview Card
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xDD072624),
                                border = BorderStroke(1.dp, MenuBtnCyanBorder),
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable {
                                        isDogJumping = true
                                        viewModel.triggerPreviewJump(GameItem.DOG)
                                    }
                            ) {
                                Column(
                                    modifier = Modifier.padding(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box(
                                        modifier = Modifier.size(56.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        SpriteSheetAnimation(
                                            spec = SpriteSheetSpec.DOG_JUMP,
                                            isPlaying = isDogJumping,
                                            onAnimationEnd = { isDogJumping = false },
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "🐶 Dog Jump",
                                        fontSize = 10.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "6 frames · Tap to leap",
                                        fontSize = 8.5.sp,
                                        color = Color(0xFFCCFBF1)
                                    )
                                }
                            }
                        }
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
                gradientColors = listOf(Color(0xF00D3560), Color(0xF007203A)),
                borderColor = MenuBtnBlueBorder
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
                        color = Color(0xFFBAE6FD)
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
