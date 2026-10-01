package com.example.ui.components.spritesheet

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.example.R
import kotlinx.coroutines.delay
import kotlin.math.min
import kotlin.math.roundToInt

/**
 * AAA Precision Spritesheet Animation Engine.
 * Slices 2D texture atlases into seamless frame animations with sub-pixel sampling,
 * bleeding prevention insets, variable FPS controls, and playback states.
 */
data class SpriteSheetSpec(
    @DrawableRes val drawableRes: Int,
    val columns: Int,
    val rows: Int = 1,
    val totalFrames: Int = columns * rows,
    val fps: Int = 12,
    val loop: Boolean = true,
    val cellBleedInsetPercent: Float = 0.035f,
    val flipHorizontal: Boolean = false
) {
    companion object {
        val FARMER_ROWING = SpriteSheetSpec(
            drawableRes = R.drawable.spr_farmer_rowing,
            columns = 6,
            rows = 1,
            totalFrames = 6,
            fps = 12,
            loop = true
        )

        val FARMER_CHEER = SpriteSheetSpec(
            drawableRes = R.drawable.spr_farmer_cheer,
            columns = 6,
            rows = 1,
            totalFrames = 6,
            fps = 10,
            loop = true
        )

        val FARMER_IDLE = SpriteSheetSpec(
            drawableRes = R.drawable.spr_farmer_idle,
            columns = 6,
            rows = 1,
            totalFrames = 6,
            fps = 8,
            loop = true
        )

        val WATER_SPLASH = SpriteSheetSpec(
            drawableRes = R.drawable.spr_water_splash,
            columns = 4,
            rows = 2,
            totalFrames = 8,
            fps = 16,
            loop = false
        )

        val RABBIT_IDLE = SpriteSheetSpec(
            drawableRes = R.drawable.spr_rabbit_anim,
            columns = 6,
            rows = 1,
            totalFrames = 6,
            fps = 9,
            loop = true
        )

        val DOG_IDLE = SpriteSheetSpec(
            drawableRes = R.drawable.spr_dog_anim,
            columns = 6,
            rows = 1,
            totalFrames = 6,
            fps = 10,
            loop = true
        )
    }
}

/**
 * High-performance frame-accurate SpriteSheet Player.
 */
@Composable
fun SpriteSheetAnimation(
    spec: SpriteSheetSpec,
    modifier: Modifier = Modifier,
    isPlaying: Boolean = true,
    customFps: Int? = null,
    flipX: Boolean = false,
    onAnimationEnd: (() -> Unit)? = null
) {
    val bitmap = ImageBitmap.imageResource(id = spec.drawableRes)
    val effectiveFps = (customFps ?: spec.fps).coerceAtLeast(1)
    val frameDurationMs = (1000L / effectiveFps)

    var currentFrame by remember(spec.drawableRes, isPlaying) { mutableIntStateOf(0) }

    LaunchedEffect(spec.drawableRes, isPlaying, effectiveFps) {
        if (!isPlaying) return@LaunchedEffect
        currentFrame = 0
        while (true) {
            delay(frameDurationMs)
            if (currentFrame + 1 < spec.totalFrames) {
                currentFrame++
            } else {
                if (spec.loop) {
                    currentFrame = 0
                } else {
                    onAnimationEnd?.invoke()
                    break
                }
            }
        }
    }

    val cellWidth = bitmap.width / spec.columns
    val cellHeight = bitmap.height / spec.rows

    val col = currentFrame % spec.columns
    val row = (currentFrame / spec.columns).coerceAtMost(spec.rows - 1)

    // Inner crop insets to eliminate neighbor pixel bleeding
    val insetX = (cellWidth * spec.cellBleedInsetPercent).roundToInt()
    val insetY = (cellHeight * spec.cellBleedInsetPercent).roundToInt()

    val srcLeft = (col * cellWidth) + insetX
    val srcTop = (row * cellHeight) + insetY
    val srcWidth = (cellWidth - insetX * 2).coerceAtLeast(1)
    val srcHeight = (cellHeight - insetY * 2).coerceAtLeast(1)

    Canvas(modifier = modifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        // Maintain sprite aspect ratio centered inside canvas
        val srcAspect = srcWidth.toFloat() / srcHeight.toFloat()
        val dstAspect = canvasWidth / canvasHeight

        val drawWidth: Float
        val drawHeight: Float

        if (srcAspect > dstAspect) {
            drawWidth = canvasWidth
            drawHeight = canvasWidth / srcAspect
        } else {
            drawHeight = canvasHeight
            drawWidth = canvasHeight * srcAspect
        }

        val dstLeft = (canvasWidth - drawWidth) / 2f
        val dstTop = (canvasHeight - drawHeight) / 2f

        val shouldFlip = spec.flipHorizontal xor flipX

        if (shouldFlip) {
            scale(scaleX = -1f, scaleY = 1f, pivot = center) {
                drawImage(
                    image = bitmap,
                    srcOffset = IntOffset(srcLeft, srcTop),
                    srcSize = IntSize(srcWidth, srcHeight),
                    dstOffset = IntOffset(dstLeft.roundToInt(), dstTop.roundToInt()),
                    dstSize = IntSize(drawWidth.roundToInt(), drawHeight.roundToInt()),
                    filterQuality = FilterQuality.Medium
                )
            }
        } else {
            drawImage(
                image = bitmap,
                srcOffset = IntOffset(srcLeft, srcTop),
                srcSize = IntSize(srcWidth, srcHeight),
                dstOffset = IntOffset(dstLeft.roundToInt(), dstTop.roundToInt()),
                dstSize = IntSize(drawWidth.roundToInt(), drawHeight.roundToInt()),
                filterQuality = FilterQuality.Medium
            )
        }
    }
}
