package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.model.Bank
import com.example.model.DisembarkJumpEvent
import com.example.model.GameItem
import com.example.ui.components.spritesheet.SpriteSheetAnimation
import com.example.ui.components.spritesheet.SpriteSheetSpec
import kotlin.math.PI
import kotlin.math.sin

/**
 * Realistic Physics-Based Disembark Jump Animation Overlay.
 *
 * When the riverboat docks at the bank, passengers (such as the Rabbit and Dog)
 * leap realistically from the raft onto the bank dock.
 *
 * Employs a 6-frame procedural spritesheet cycle:
 * 1. Crouch / squash anticipation
 * 2. Takeoff push-off
 * 3. Apex flight arc
 * 4. Descent reach
 * 5. Touchdown impact squash
 * 6. Settle upright stance
 *
 * Paired with dynamic parabolic arc kinetics, landing ground shadow tracking,
 * and procedural sound effects.
 */
@Composable
fun AnimalDisembarkJumpOverlay(
    jumpEvent: DisembarkJumpEvent?,
    totalWidth: Dp,
    totalHeight: Dp,
    bankWidth: Dp,
    boatWidth: Dp,
    modifier: Modifier = Modifier
) {
    if (jumpEvent == null || jumpEvent.jumpingItems.isEmpty()) return

    val density = LocalDensity.current
    val progressAnim = remember(jumpEvent.id) { Animatable(0f) }

    LaunchedEffect(jumpEvent.id) {
        progressAnim.snapTo(0f)
        progressAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = jumpEvent.durationMs.toInt(),
                easing = FastOutSlowInEasing
            )
        )
    }

    val progress = progressAnim.value
    val isRightBank = jumpEvent.targetBank == Bank.RIGHT

    // Determine horizontal start (inside boat) and end (on bank dock) positions in Dp
    val startXBase = if (isRightBank) {
        totalWidth - bankWidth - (boatWidth * 0.45f)
    } else {
        bankWidth + (boatWidth * 0.45f)
    }

    val endXBase = if (isRightBank) {
        totalWidth - bankWidth + 36.dp
    } else {
        bankWidth - 36.dp
    }

    val baseY = totalHeight * 0.44f + 4.dp
    val jumpArcHeight = 52.dp

    Box(modifier = modifier.fillMaxSize()) {
        jumpEvent.jumpingItems.forEachIndexed { index, item ->
            val slotOffset = (index * 22).dp
            val startX = if (isRightBank) startXBase + slotOffset else startXBase - slotOffset
            val endX = if (isRightBank) endXBase + slotOffset else endXBase - slotOffset

            val currentX = startX + (endX - startX) * progress
            val arcOffset = jumpArcHeight * sin(progress * PI.toFloat())
            val currentY = baseY - arcOffset

            // Sprite sheet spec
            val spec = when (item) {
                GameItem.RABBIT -> SpriteSheetSpec.RABBIT_JUMP
                GameItem.DOG -> SpriteSheetSpec.DOG_JUMP
                else -> null
            }

            if (spec != null) {
                // Ground drop shadow following the jump arc
                Canvas(
                    modifier = Modifier
                        .offset(x = currentX - 18.dp, y = baseY + 36.dp)
                        .size(width = 36.dp, height = 12.dp)
                ) {
                    val shadowScale = (1f - 0.45f * sin(progress * PI.toFloat())).coerceIn(0.5f, 1f)
                    val shadowAlpha = (0.35f - 0.20f * sin(progress * PI.toFloat())).coerceIn(0.1f, 0.4f)
                    drawOval(
                        color = Color.Black.copy(alpha = shadowAlpha),
                        topLeft = Offset(
                            size.width * (1f - shadowScale) / 2f,
                            size.height * (1f - shadowScale) / 2f
                        ),
                        size = Size(size.width * shadowScale, size.height * shadowScale)
                    )
                }

                // Animated jumping animal token with synced frame playback
                Box(
                    modifier = Modifier
                        .offset(x = currentX - 24.dp, y = currentY)
                        .size(48.dp)
                        .graphicsLayer {
                            // Slight forward tilt in mid-air
                            val tilt = if (isRightBank) {
                                (sin(progress * PI.toFloat()) * 8f)
                            } else {
                                -(sin(progress * PI.toFloat()) * 8f)
                            }
                            rotationZ = tilt
                        },
                    contentAlignment = Alignment.Center
                ) {
                    SpriteSheetAnimation(
                        spec = spec,
                        progress = progress,
                        flipX = !isRightBank,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}
