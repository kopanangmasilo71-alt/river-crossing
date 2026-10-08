package com.example.ui.components

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ads.AdManager
import com.example.model.PuzzleScenario
import com.example.ui.theme.GoldenBankGlow
import com.example.ui.theme.MenuBtnAmberBorder
import com.example.ui.theme.MenuBtnGreenBorder
import com.example.ui.theme.MenuBtnGreenTop
import com.example.ui.theme.MenuBtnRedBorder

/**
 * Dialog presented when a player attempts to access a locked level.
 *
 * Enforces strict anti-bypass security:
 * 1. Players cannot bypass or unlock locked levels without completing the previous level or watching a rewarded ad.
 * 2. If the user chooses to watch a rewarded ad and network is not available or the ad fails/closes early,
 *    the level is NOT unlocked.
 * 3. Level is ONLY unlocked if the user completely watches the rewarded ad.
 */
@Composable
fun LockedLevelUnlockDialog(
    scenario: PuzzleScenario,
    onDismiss: () -> Unit,
    onUnlockSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activity = context as? Activity

    var isLoadingAd by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val previousLevelNum = scenario.levelNumber - 1

    Dialog(
        onDismissRequest = {
            if (!isLoadingAd) {
                onDismiss()
            }
        },
        properties = DialogProperties(usePlatformDefaultWidth = true)
    ) {
        Surface(
            shape = RoundedCornerShape(22.dp),
            color = Color(0xF6180B04),
            border = BorderStroke(1.8.dp, GoldenBankGlow),
            shadowElevation = 8.dp,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .testTag("locked_level_unlock_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xDD3A1608),
                            border = BorderStroke(1.5.dp, Color(0xFFF87171)),
                            modifier = Modifier.size(42.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "Locked Level",
                                    tint = Color(0xFFFCA5A5),
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                        Column {
                            Text(
                                text = "Level ${scenario.levelNumber} is Locked",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 17.sp,
                                color = Color.White
                            )
                            Text(
                                text = scenario.title,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = GoldenBankGlow
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        enabled = !isLoadingAd,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color(0xFFFEF3C7)
                        )
                    }
                }

                // Scenario Items preview
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xCC0E0502),
                    border = BorderStroke(1.dp, Color(0x55D97706)),
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
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            scenario.items.take(4).forEach { item ->
                                Surface(
                                    shape = CircleShape,
                                    color = Color(0x88000000),
                                    border = BorderStroke(0.8.dp, GoldenBankGlow),
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = item.drawableRes),
                                        contentDescription = item.displayName,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }
                        }
                        Text(
                            text = "Goal: ${scenario.optimalMoves} moves",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldenBankGlow
                        )
                    }
                }

                // Requirements Explanation
                Text(
                    text = "To access this river puzzle, choose one of two valid methods:",
                    fontSize = 12.sp,
                    color = Color(0xFFFEF3C7)
                )

                // Standard Approach: Complete Level below
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xDD2A1406),
                    border = BorderStroke(1.dp, MenuBtnAmberBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "🏆", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Standard Approach",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = GoldenBankGlow
                            )
                            Text(
                                text = if (previousLevelNum >= 1) {
                                    "Complete Level $previousLevelNum to unlock automatically."
                                } else {
                                    "Complete previous levels in sequential order."
                                },
                                fontSize = 11.sp,
                                color = Color(0xFFFDE68A)
                            )
                        }
                    }
                }

                // Alternative Approach: Watch Rewarded Ad
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xDD0D2612),
                    border = BorderStroke(1.dp, MenuBtnGreenBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "🎬", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Watch Rewarded Ad",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = Color(0xFF86EFAC)
                            )
                            Text(
                                text = "Watch an ad completely to unlock instantly. Active internet is required.",
                                fontSize = 11.sp,
                                color = Color(0xFFBBF7D0)
                            )
                        }
                    }
                }

                // Error Notice if network or ad fails
                errorMessage?.let { error ->
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xDD3A0A0A),
                        border = BorderStroke(1.dp, MenuBtnRedBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = Color(0xFFFCA5A5),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = error,
                                fontSize = 10.5.sp,
                                color = Color(0xFFFECACA),
                                lineHeight = 14.sp
                            )
                        }
                    }
                }

                if (isLoadingAd) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            color = GoldenBankGlow,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Loading sponsored ad...",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = GoldenBankGlow
                        )
                    }
                }

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = onDismiss,
                        enabled = !isLoadingAd,
                        modifier = Modifier.testTag("unlock_cancel_button")
                    ) {
                        Text(
                            text = "Close",
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFFEF3C7)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MenuBtnGreenTop,
                        border = BorderStroke(1.5.dp, MenuBtnGreenBorder),
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .testTag("unlock_via_rewarded_ad_button")
                    ) {
                        Button(
                            onClick = {
                                if (isLoadingAd) return@Button
                                errorMessage = null

                                if (activity == null) {
                                    errorMessage = "Activity unavailable. Cannot show ads."
                                    return@Button
                                }

                                // Network check prior to requesting
                                if (!AdManager.isNetworkAvailable(activity)) {
                                    errorMessage = "No internet connection detected. Please connect to Wi-Fi or mobile data to watch an ad and unlock this level."
                                    Toast.makeText(
                                        context,
                                        "No internet connection. Level remains locked.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    return@Button
                                }

                                isLoadingAd = true
                                AdManager.loadAndShowRewardedAd(
                                    activity = activity,
                                    onLoading = {
                                        isLoadingAd = true
                                    },
                                    onRewardEarned = {
                                        // ONLY called when user actually finishes watching the full rewarded ad!
                                        isLoadingAd = false
                                        Toast.makeText(
                                            context,
                                            "Level ${scenario.levelNumber} Unlocked!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        onUnlockSuccess()
                                    },
                                    onAdFailed = { failReason ->
                                        // Ad was not completed or failed to load/play: NO UNLOCK!
                                        isLoadingAd = false
                                        errorMessage = failReason
                                        Toast.makeText(
                                            context,
                                            failReason,
                                            Toast.LENGTH_LONG
                                        ).show()
                                    },
                                    onAdDismissed = {
                                        isLoadingAd = false
                                    }
                                )
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent
                            ),
                            enabled = !isLoadingAd
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.OndemandVideo,
                                    contentDescription = "Watch Ad",
                                    tint = Color.White,
                                    modifier = Modifier.size(17.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isLoadingAd) "LOADING..." else "WATCH AD TO UNLOCK",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 11.5.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
