package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.R
import com.example.model.GameItem
import com.example.model.PuzzleScenario

/**
 * Interactive Comic-Style Story Vignette Dialog for level rules & lore.
 * Displays vibrant comic panels explaining the mission, character conflicts, and boat limits.
 */
@Composable
fun ComicVignetteDialog(
    scenario: PuzzleScenario,
    onDismiss: () -> Unit
) {
    val scrollState = rememberScrollState()
    val infiniteTransition = rememberInfiniteTransition(label = "comic_bounce")
    val comicWiggle by infiniteTransition.animateFloat(
        initialValue = -2f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "comic_wiggle"
    )

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xF5072449)),
            border = BorderStroke(2.dp, Color(0xFF38BDF8)),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .testTag("comic_vignette_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xF00D3B73),
                                Color(0xF00A2D58),
                                Color(0xF0072449)
                            )
                        )
                    )
                    .padding(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Comic Book Banner Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .rotate(comicWiggle)
                            .background(
                                color = Color(0xFF0284C7),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "STORY VIGNETTE",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp
                        )
                    }

                    Text(
                        text = scenario.title,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color(0xFFBAE6FD),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Scrollable 3-Panel Comic Strip
                Column(
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Panel 1: The Mission
                    ComicPanel(
                        panelNumber = "1",
                        badgeText = "THE MISSION",
                        badgeColor = Color(0xFF2563EB),
                        title = "Cross The Rapid River!",
                        speechText = "The Farmer must safely ferry all companions across to the other shore without any disasters!",
                        visualContent = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.img_farmer),
                                    contentDescription = "Farmer",
                                    modifier = Modifier
                                        .size(54.dp)
                                        .clip(CircleShape)
                                        .borderCircle(Color(0xFFD97706))
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(text = "🚣‍♂️ ➔ 🏞️", fontSize = 24.sp)
                                Spacer(modifier = Modifier.width(10.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    scenario.items.take(3).forEach { item ->
                                        Image(
                                            painter = painterResource(id = item.drawableRes),
                                            contentDescription = item.displayName,
                                            modifier = Modifier
                                                .size(36.dp)
                                                .clip(CircleShape)
                                                .borderCircle(Color(0xFFD97706))
                                        )
                                    }
                                }
                            }
                        }
                    )

                    // Panel 2: The Conflict & Danger Rules
                    ComicPanel(
                        panelNumber = "2",
                        badgeText = "BEWARE!",
                        badgeColor = Color(0xFFDC2626),
                        title = "Rivalries & Hunger!",
                        speechText = if (scenario.dangerRules.isNotEmpty()) {
                            scenario.dangerRules.joinToString("\n") {
                                "• ${it.predator.displayName} will attack ${it.prey.displayName} if left alone on either bank!"
                            }
                        } else {
                            "Keep your companions calm and balance the crossing carefully!"
                        },
                        visualContent = {
                            if (scenario.dangerRules.isNotEmpty()) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    scenario.dangerRules.take(2).forEach { rule ->
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painter = painterResource(id = rule.predator.drawableRes),
                                                contentDescription = rule.predator.displayName,
                                                modifier = Modifier
                                                    .size(42.dp)
                                                    .clip(CircleShape)
                                                    .borderCircle(Color(0xFFDC2626))
                                            )
                                            Text(
                                                text = " ⚔️ ",
                                                fontSize = 18.sp,
                                                modifier = Modifier.padding(horizontal = 4.dp)
                                            )
                                            Image(
                                                painter = painterResource(id = rule.prey.drawableRes),
                                                contentDescription = rule.prey.displayName,
                                                modifier = Modifier
                                                    .size(42.dp)
                                                    .clip(CircleShape)
                                                    .borderCircle(Color(0xFFDC2626))
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    )

                    // Panel 3: The Raft Limit & Secret
                    ComicPanel(
                        panelNumber = "3",
                        badgeText = "STRATEGY",
                        badgeColor = Color(0xFF059669),
                        title = "Raft Holds Farmer + ${scenario.boatCapacity} Cargo",
                        speechText = "Space is limited! You can bring passengers BACK to the start if needed. Think 2 steps ahead!",
                        visualContent = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(Color(0xFF5A3E26), RoundedCornerShape(10.dp))
                                        .padding(horizontal = 14.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = "🪵 Log Raft Slots: ${scenario.boatCapacity} + Farmer",
                                        color = Color(0xFFFDE68A),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Play / Dismiss Button
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0xFFD97706),
                    border = BorderStroke(2.dp, Color(0xFFFDE68A)),
                    shadowElevation = 6.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .clickable(onClick = onDismiss)
                        .testTag("comic_start_button")
                ) {
                    Row(
                        modifier = Modifier
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color(0xFFF59E0B), Color(0xFFB45309))
                                )
                            )
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Start",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "LET'S SOLVE IT!",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ComicPanel(
    panelNumber: String,
    badgeText: String,
    badgeColor: Color,
    title: String,
    speechText: String,
    visualContent: @Composable () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xF00A2D58)),
        border = BorderStroke(2.dp, Color(0xFF38BDF8).copy(alpha = 0.6f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(Color(0xFF0284C7), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = panelNumber,
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .background(badgeColor, RoundedCornerShape(6.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = badgeText,
                            color = Color.White,
                            fontSize = 9.5.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }

                Text(
                    text = title,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 12.5.sp
                )
            }

            visualContent()

            // Comic Speech Bubble
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0x3338BDF8), RoundedCornerShape(8.dp))
                    .border(1.dp, Color(0x6638BDF8), RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    text = speechText,
                    color = Color(0xFFE0F2FE),
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 15.sp
                )
            }
        }
    }
}

private fun Modifier.borderCircle(color: Color): Modifier = this.then(
    Modifier.background(color, CircleShape).padding(2.dp)
)
