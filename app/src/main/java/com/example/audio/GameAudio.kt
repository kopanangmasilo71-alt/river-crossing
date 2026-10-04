package com.example.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import com.example.model.GameItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.sin
import kotlin.random.Random

/**
 * AAA Procedural Sound Synthesis Engine for River Crossing.
 * Generates rich, artifact-free 16-bit PCM audio natively without external heavy asset files.
 */
class GameAudio {
    private val scope = CoroutineScope(Dispatchers.Default)

    private val _isMutedState = MutableStateFlow(false)
    val isMutedState: StateFlow<Boolean> = _isMutedState.asStateFlow()

    var isMuted: Boolean
        get() = _isMutedState.value
        set(value) {
            _isMutedState.value = value
        }

    fun toggleMute(): Boolean {
        val newState = !_isMutedState.value
        _isMutedState.value = newState
        if (!newState) {
            playClickSound()
        }
        return newState
    }

    /**
     * Synthesizes and plays a custom audio wave with harmonics, envelopes, and optional noise/sweeps.
     */
    private fun playCustomSound(
        durationMs: Int,
        sampleRate: Int = 22050,
        volume: Float = 0.5f,
        generator: (timeSec: Float, progress: Float) -> Float
    ) {
        if (isMuted) return
        scope.launch {
            try {
                val numSamples = (sampleRate * (durationMs / 1000f)).toInt().coerceAtLeast(1)
                val buffer = ShortArray(numSamples)

                for (i in 0 until numSamples) {
                    val t = i.toFloat() / sampleRate
                    val progress = i.toFloat() / numSamples

                    // Smooth attack and decay envelope to eliminate audio clicks
                    val attack = (i.toFloat() / (sampleRate * 0.008f)).coerceIn(0f, 1f)
                    val decay = ((numSamples - i).toFloat() / (sampleRate * 0.015f)).coerceIn(0f, 1f)
                    val envelope = attack * decay

                    val rawSample = generator(t, progress)
                    val sample = (rawSample * Short.MAX_VALUE * volume * envelope).coerceIn(
                        Short.MIN_VALUE.toFloat(),
                        Short.MAX_VALUE.toFloat()
                    )
                    buffer[i] = sample.toInt().toShort()
                }

                val audioTrack = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_GAME)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(sampleRate)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(buffer.size * 2)
                    .setTransferMode(AudioTrack.MODE_STATIC)
                    .build()

                audioTrack.write(buffer, 0, buffer.size)
                audioTrack.play()

                scope.launch {
                    kotlinx.coroutines.delay(durationMs.toLong() + 80)
                    audioTrack.release()
                }
            } catch (_: Exception) {
                // Graceful fallback on restricted audio environments
            }
        }
    }

    // -------------------------------------------------------------
    // CHARACTER SPECIFIC SOUND EFFECTS
    // -------------------------------------------------------------

    /** Dog Bark: Dual harmonic acoustic bark burst with body resonance */
    fun playDogBark() {
        playCustomSound(durationMs = 180, volume = 0.42f) { t, p ->
            val freq = 340f - (p * 140f)
            val fundamental = sin(2.0 * PI * freq * t).toFloat()
            val harmonic2 = sin(2.0 * PI * (freq * 1.5f) * t).toFloat() * 0.35f
            val growlNoise = (Random.nextFloat() * 2f - 1f) * (1f - p) * 0.15f
            (fundamental + harmonic2 + growlNoise)
        }
    }

    /** Rabbit Hop: Cute springy upward FM chirp */
    fun playRabbitHop() {
        playCustomSound(durationMs = 140, volume = 0.35f) { t, p ->
            val freq = 620f + (p * 580f)
            val mod = sin(2.0 * PI * 35.0 * t).toFloat() * 0.2f
            sin(2.0 * PI * (freq + mod * 120f) * t).toFloat()
        }
    }

    /** Cabbage Rustle: Soft crisp percussive plant crunch */
    fun playCabbageRustle() {
        playCustomSound(durationMs = 130, volume = 0.38f) { t, p ->
            val tone = sin(2.0 * PI * (480f - p * 220f) * t).toFloat() * 0.4f
            val rustle = (Random.nextFloat() * 2f - 1f) * (1f - p * 0.8f) * 0.6f
            (tone + rustle)
        }
    }

    /** Farmer Greeting Whistle: Cheerful melodious whistle */
    fun playFarmerWhistle() {
        playCustomSound(durationMs = 280, volume = 0.38f) { t, p ->
            val freq = when {
                p < 0.33f -> 659.25f // E5
                p < 0.66f -> 783.99f // G5
                else -> 987.77f      // B5
            }
            val tremolo = 1f + 0.08f * sin(2.0 * PI * 18.0 * t).toFloat()
            sin(2.0 * PI * freq * t).toFloat() * tremolo
        }
    }

    /** Fox Yip: Sly melodic sweep with subtle rasp */
    fun playFoxSound() {
        playCustomSound(durationMs = 190, volume = 0.40f) { t, p ->
            val freq = 520f + sin(2.0 * PI * 8.0 * t).toFloat() * 80f - (p * 180f)
            val base = sin(2.0 * PI * freq * t).toFloat()
            val rasp = (Random.nextFloat() * 2f - 1f) * 0.15f * (1f - p)
            (base * 0.85f + rasp)
        }
    }

    /** Corn Crunch: Crisp organic harvest rustle with high snap */
    fun playCornSound() {
        playCustomSound(durationMs = 150, volume = 0.36f) { t, p ->
            val popFreq = 720f - p * 300f
            val snap = sin(2.0 * PI * popFreq * t).toFloat() * 0.45f
            val crunch = (Random.nextFloat() * 2f - 1f) * (1f - p) * 0.55f
            (snap + crunch)
        }
    }

    /** Wolf Howl: Atmospheric deep howl sweep with harmonic overtone */
    fun playWolfSound() {
        playCustomSound(durationMs = 320, volume = 0.44f) { t, p ->
            val freq = 280f + sin(p * PI.toFloat()) * 180f + sin(2.0 * PI * 6.0 * t).toFloat() * 12f
            val base = sin(2.0 * PI * freq * t).toFloat() * 0.7f
            val overtone = sin(2.0 * PI * (freq * 2f) * t).toFloat() * 0.25f
            val breath = (Random.nextFloat() * 2f - 1f) * 0.1f * (1f - p)
            (base + overtone + breath)
        }
    }

    /** Sheep Baa: Gentle warbling vocal bleat */
    fun playSheepSound() {
        playCustomSound(durationMs = 240, volume = 0.40f) { t, p ->
            val vibrato = sin(2.0 * PI * 14.0 * t).toFloat() * 28f
            val freq = 380f + vibrato - (p * 40f)
            val form1 = sin(2.0 * PI * freq * t).toFloat() * 0.6f
            val form2 = sin(2.0 * PI * (freq * 2.3f) * t).toFloat() * 0.3f
            (form1 + form2)
        }
    }

    /** Hay Rustle: Dry organic golden straw crinkling */
    fun playHaySound() {
        playCustomSound(durationMs = 160, volume = 0.36f) { t, p ->
            val grain = sin(2.0 * PI * (360f - p * 120f) * t).toFloat() * 0.3f
            val strawNoise = (Random.nextFloat() * 2f - 1f) * (1f - p * 0.7f) * 0.7f
            (grain + strawNoise)
        }
    }

    /** Lion Roar: Mighty low frequency growl with textured sub-bass harmonics */
    fun playLionSound() {
        playCustomSound(durationMs = 360, volume = 0.48f) { t, p ->
            val freq = 130f + sin(p * PI.toFloat()) * 70f
            val growl = sin(2.0 * PI * freq * t).toFloat() * 0.65f
            val sub = sin(2.0 * PI * (freq * 0.5f) * t).toFloat() * 0.35f
            val roarNoise = (Random.nextFloat() * 2f - 1f) * (1f - p * 0.5f) * 0.25f
            (growl + sub + roarNoise)
        }
    }

    /** Bear Growl: Deep resonant rumble with woody forest presence */
    fun playBearSound() {
        playCustomSound(durationMs = 320, volume = 0.46f) { t, p ->
            val freq = 95f + sin(p * PI.toFloat() * 1.5f) * 45f
            val rumble = sin(2.0 * PI * freq * t).toFloat() * 0.70f
            val grit = (Random.nextFloat() * 2f - 1f) * (1f - p) * 0.30f
            (rumble + grit)
        }
    }

    /** Berries Rustle: Crisp, gentle pluck sound of wild ripe fruit */
    fun playBerriesSound() {
        playCustomSound(durationMs = 140, volume = 0.38f) { t, p ->
            val popFreq = 620f - p * 200f
            val tone = sin(2.0 * PI * popFreq * t).toFloat() * 0.6f
            val twig = (Random.nextFloat() * 2f - 1f) * (1f - p * 0.9f) * 0.4f
            (tone + twig)
        }
    }

    /** Cat Meow: Playful, melodic feline purr and meow */
    fun playCatSound() {
        playCustomSound(durationMs = 280, volume = 0.45f) { t, p ->
            val freq = 480f + sin(p * PI.toFloat() * 1.8f) * 160f
            val tone = sin(2.0 * PI * freq * t).toFloat() * 0.65f
            val harmonic = sin(2.0 * PI * (freq * 2f) * t).toFloat() * 0.25f
            val breath = (Random.nextFloat() * 2f - 1f) * (1f - p) * 0.1f
            (tone + harmonic + breath)
        }
    }

    /** Fish Splash: Gentle aquatic ripple and bubbly flutter */
    fun playFishSound() {
        playCustomSound(durationMs = 200, volume = 0.42f) { t, p ->
            val splashFreq = 420f + sin(p * 28f) * 150f
            val water = sin(2.0 * PI * splashFreq * t).toFloat() * 0.6f
            val droplets = (Random.nextFloat() * 2f - 1f) * (1f - p * 0.8f) * 0.4f
            (water + droplets)
        }
    }

    /** Mouse Squeak: Fast, lively high-pitched rodent squeaks */
    fun playMouseSound() {
        playCustomSound(durationMs = 150, volume = 0.38f) { t, p ->
            val freq = 1400f + sin(p * PI.toFloat() * 2f) * 400f
            val squeak = sin(2.0 * PI * freq * t).toFloat() * 0.75f
            val overtone = sin(2.0 * PI * (freq * 1.5f) * t).toFloat() * 0.25f
            (squeak + overtone) * (1f - p * 0.5f)
        }
    }

    /** Cheese Plop: Soft, creamy dairy bounce tone */
    fun playCheeseSound() {
        playCustomSound(durationMs = 120, volume = 0.35f) { t, p ->
            val freq = 340f - p * 80f
            val tone = sin(2.0 * PI * freq * t).toFloat() * 0.7f
            val squish = (Random.nextFloat() * 2f - 1f) * (1f - p) * 0.3f
            (tone + squish)
        }
    }

    /** Crocodile Hiss: Deep low reptilian bellow and snap */
    fun playCrocodileSound() {
        playCustomSound(durationMs = 340, volume = 0.50f) { t, p ->
            val freq = 80f + sin(p * PI.toFloat()) * 40f
            val bellow = sin(2.0 * PI * freq * t).toFloat() * 0.65f
            val hiss = (Random.nextFloat() * 2f - 1f) * (1f - p * 0.6f) * 0.35f
            (bellow + hiss)
        }
    }

    /** Goat Bleat: Vibrato mountain goat bleat */
    fun playGoatSound() {
        playCustomSound(durationMs = 300, volume = 0.45f) { t, p ->
            val vibrato = sin(p * 32f) * 45f
            val freq = 360f + vibrato
            val bleat = sin(2.0 * PI * freq * t).toFloat() * 0.7f
            val buzz = sin(2.0 * PI * (freq * 2.2f) * t).toFloat() * 0.3f
            (bleat + buzz) * (1f - p * 0.4f)
        }
    }

    /** Carrot Crunch: Crisp satisfying garden snap */
    fun playCarrotSound() {
        playCustomSound(durationMs = 110, volume = 0.42f) { t, p ->
            val pop = sin(2.0 * PI * (540f - p * 220f) * t).toFloat() * 0.45f
            val crunch = (Random.nextFloat() * 2f - 1f) * (1f - p * 0.9f) * 0.55f
            (pop + crunch)
        }
    }

    /** Chicken Cluck: Rhythmic farm hen cluck */
    fun playChickenSound() {
        playCustomSound(durationMs = 220, volume = 0.44f) { t, p ->
            val cluckFreq = if (p < 0.5f) 520f else 410f
            val tone = sin(2.0 * PI * cluckFreq * t).toFloat() * 0.75f
            val chirp = sin(2.0 * PI * (cluckFreq * 2.5f) * t).toFloat() * 0.25f
            (tone + chirp) * (1f - p * 0.3f)
        }
    }

    /** Grain Rustle: Dry golden seed pouring and sifting */
    fun playGrainSound() {
        playCustomSound(durationMs = 150, volume = 0.36f) { t, p ->
            val hiss = (Random.nextFloat() * 2f - 1f) * (1f - p * 0.7f) * 0.7f
            val seedTone = sin(2.0 * PI * (620f - p * 180f) * t).toFloat() * 0.3f
            (hiss + seedTone)
        }
    }

    /** Tiger Roar: Powerful predatory roar with savage harmonics */
    fun playTigerSound() {
        playCustomSound(durationMs = 380, volume = 0.52f) { t, p ->
            val freq = 120f + sin(p * PI.toFloat() * 1.6f) * 85f
            val roar = sin(2.0 * PI * freq * t).toFloat() * 0.65f
            val sub = sin(2.0 * PI * (freq * 0.5f) * t).toFloat() * 0.25f
            val snarl = (Random.nextFloat() * 2f - 1f) * (1f - p * 0.4f) * 0.3f
            (roar + sub + snarl)
        }
    }

    /** Context-aware character sound */
    fun playCharacterSound(item: GameItem) {
        when (item) {
            GameItem.DOG -> playDogBark()
            GameItem.FOX -> playFoxSound()
            GameItem.RABBIT -> playRabbitHop()
            GameItem.CABBAGE -> playCabbageRustle()
            GameItem.CORN -> playCornSound()
            GameItem.WOLF -> playWolfSound()
            GameItem.SHEEP -> playSheepSound()
            GameItem.HAY -> playHaySound()
            GameItem.LION -> playLionSound()
            GameItem.BEAR -> playBearSound()
            GameItem.BERRIES -> playBerriesSound()
            GameItem.CAT -> playCatSound()
            GameItem.FISH -> playFishSound()
            GameItem.MOUSE -> playMouseSound()
            GameItem.CHEESE -> playCheeseSound()
            GameItem.CROCODILE -> playCrocodileSound()
            GameItem.GOAT -> playGoatSound()
            GameItem.CARROT -> playCarrotSound()
            GameItem.CHICKEN -> playChickenSound()
            GameItem.GRAIN -> playGrainSound()
            GameItem.TIGER -> playTigerSound()
            else -> playWaterRippleSound()
        }
    }

    // -------------------------------------------------------------
    // GAMEPLAY & INTERACTION SOUND EFFECTS
    // -------------------------------------------------------------

    /** Danger Warning Alert / Danger Line Trigger: Pulsing dramatic alarm */
    fun playDangerAlertSound() {
        playCustomSound(durationMs = 340, volume = 0.52f) { t, p ->
            val pulse = if (((t * 16f).toInt() % 2) == 0) 1f else 0.4f
            val f1 = 580f
            val f2 = 580f * 1.414f // Tritone
            val s1 = sin(2.0 * PI * f1 * t).toFloat() * 0.5f
            val s2 = sin(2.0 * PI * f2 * t).toFloat() * 0.4f
            (s1 + s2) * pulse
        }
    }

    /** Water Ripple Droplet effect */
    fun playWaterRippleSound() {
        playCustomSound(durationMs = 120, volume = 0.35f) { t, p ->
            val freq = 900f + (p * 700f)
            sin(2.0 * PI * freq * t).toFloat() * (1f - p)
        }
    }

    /** Level Select / Unlock Chime */
    fun playLevelSelectSound() {
        playCustomSound(durationMs = 280, volume = 0.42f) { t, p ->
            val f = when {
                p < 0.33f -> 523.25f // C5
                p < 0.66f -> 659.25f // E5
                else -> 783.99f      // G5
            }
            sin(2.0 * PI * f * t).toFloat() * 0.7f + sin(2.0 * PI * (f * 2f) * t).toFloat() * 0.3f
        }
    }

    /** Pop / Selection Feedback */
    fun playButtonPopSound() {
        playCustomSound(durationMs = 60, volume = 0.32f) { t, p ->
            val freq = 420f + (p * 680f)
            sin(2.0 * PI * freq * t).toFloat()
        }
    }

    // -------------------------------------------------------------
    // GAMEPLAY & INTERACTION SOUND EFFECTS
    // -------------------------------------------------------------

    /** Boarding Boat: Wooden plank step + character cue */
    fun playBoardSound(item: GameItem? = null) {
        playCustomSound(durationMs = 150, volume = 0.4f) { t, p ->
            val woodKnock = sin(2.0 * PI * (220f - p * 80f) * t).toFloat() * (1f - p)
            val ding = sin(2.0 * PI * (660f + p * 220f) * t).toFloat() * 0.5f
            woodKnock + ding
        }
        item?.let {
            scope.launch {
                kotlinx.coroutines.delay(100)
                playCharacterSound(it)
            }
        }
    }

    /** Disembarking Boat: Soft grass landing tone */
    fun playUnboardSound(item: GameItem? = null) {
        playCustomSound(durationMs = 130, volume = 0.35f) { t, p ->
            val grassStep = sin(2.0 * PI * (380f - p * 120f) * t).toFloat() * 0.6f
            val rustle = (Random.nextFloat() * 2f - 1f) * 0.2f
            grassStep + rustle
        }
    }

    /** Rowing Boat: Rich atmospheric oar paddle stroke in water */
    fun playRowingSound() {
        playCustomSound(durationMs = 500, volume = 0.42f) { t, p ->
            // Dual oar sweep with water turbulence
            val sweepFreq = 160f + sin(p * PI.toFloat()) * 180f
            val waterTone = sin(2.0 * PI * sweepFreq * t).toFloat() * 0.5f
            val waterFoam = (Random.nextFloat() * 2f - 1f) * sin(p * PI.toFloat()) * 0.4f
            (waterTone + waterFoam)
        }
    }

    /** Splash Effect: AAA water impact with droplets and bubbling spray */
    fun playSplashSound() {
        playCustomSound(durationMs = 380, volume = 0.5f) { t, p ->
            val boom = sin(2.0 * PI * (180f - p * 110f) * t).toFloat() * (1f - p) * 0.5f
            val spray = (Random.nextFloat() * 2f - 1f) * (1f - p * 0.7f) * 0.45f
            val bubble = sin(2.0 * PI * (850f + sin(p * 24f) * 300f) * t).toFloat() * 0.3f * (1f - p)
            (boom + spray + bubble)
        }
    }

    /** Celebratory Victory Fanfare: Glorious multi-stage triumphant fanfare with rich brass harmonics, sparkling glockenspiel chimes, and resounding bass when all items successfully transfer across the river */
    fun playVictoryCelebrationSound() {
        playCustomSound(durationMs = 1800, volume = 0.58f) { t, p ->
            // Multi-stage celebratory fanfare:
            // 0.00 - 0.14: C5 (523.25 Hz) - Bold introductory trumpet call
            // 0.14 - 0.28: E5 (659.25 Hz) - Ascending major third
            // 0.28 - 0.42: G5 (783.99 Hz) - Major fifth lift
            // 0.42 - 0.58: C6 (1046.50 Hz) - High octave burst
            // 0.58 - 1.00: High E6 (1318.51 Hz) + Grand Triumphant C Major Chord Climax with sparkling bells & bass
            if (p < 0.58f) {
                val baseFreq = when {
                    p < 0.14f -> 523.25f
                    p < 0.28f -> 659.25f
                    p < 0.42f -> 783.99f
                    else -> 1046.50f
                }
                val brass1 = sin(2.0 * PI * baseFreq * t).toFloat() * 0.50f
                val brass2 = sin(2.0 * PI * (baseFreq * 2.0) * t).toFloat() * 0.24f
                val brass3 = sin(2.0 * PI * (baseFreq * 3.0) * t).toFloat() * 0.12f
                val bell = sin(2.0 * PI * (baseFreq * 2.5) * t).toFloat() * 0.14f
                val bass = sin(2.0 * PI * 130.81 * t).toFloat() * 0.18f
                (brass1 + brass2 + brass3 + bell + bass) * 0.75f
            } else {
                val chordProgress = ((p - 0.58f) / 0.42f).coerceIn(0f, 1f)
                val c5 = sin(2.0 * PI * 523.25 * t).toFloat() * 0.22f
                val g5 = sin(2.0 * PI * 783.99 * t).toFloat() * 0.24f
                val c6 = sin(2.0 * PI * 1046.50 * t).toFloat() * 0.28f
                val e6 = sin(2.0 * PI * 1318.51 * t).toFloat() * 0.32f
                val shimmerBell = sin(2.0 * PI * (2637.0 + sin(24.0 * t) * 60.0) * t).toFloat() * 0.14f * (1f - chordProgress * 0.4f)
                val warmBass = sin(2.0 * PI * 130.81 * t).toFloat() * 0.18f * (1f - chordProgress * 0.6f)
                (c5 + g5 + c6 + e6 + shimmerBell + warmBass) * 0.72f
            }
        }
    }

    /** Victory Fanfare: Legacy alias redirecting to full celebratory victory fanfare */
    fun playVictorySound() {
        playVictoryCelebrationSound()
    }

    /** Game Over: Dramatic warning dissonance with descending slide */
    fun playGameOverSound() {
        playCustomSound(durationMs = 550, volume = 0.55f) { t, p ->
            val freq1 = 440f - (p * 260f)
            val freq2 = freq1 * 1.414f // Tritone dissonance
            val wave1 = sin(2.0 * PI * freq1 * t).toFloat() * 0.5f
            val wave2 = sin(2.0 * PI * freq2 * t).toFloat() * 0.4f
            val impact = (Random.nextFloat() * 2f - 1f) * (1f - p * 2f).coerceAtLeast(0f) * 0.3f
            (wave1 + wave2 + impact)
        }
    }

    /** Magical Sparkle / AI Hint Sound: Celestial glockenspiel chime */
    fun playHintSound() {
        playCustomSound(durationMs = 320, volume = 0.4f) { t, p ->
            val f1 = 1046.50f + (p * 400f) // C6
            val f2 = 1318.51f              // E6
            val f3 = 1567.98f              // G6
            val bell = (sin(2.0 * PI * f1 * t) * 0.4 + sin(2.0 * PI * f2 * t) * 0.3 + sin(2.0 * PI * f3 * t) * 0.3).toFloat()
            val sparkle = sin(2.0 * PI * (f1 * 3f) * t).toFloat() * 0.15f
            (bell + sparkle)
        }
    }

    /** Rewind / Undo: Reverse air whoosh sweep */
    fun playUndoSound() {
        playCustomSound(durationMs = 200, volume = 0.35f) { t, p ->
            val freq = 750f - (p * 450f)
            val tone = sin(2.0 * PI * freq * t).toFloat() * 0.5f
            val whoosh = (Random.nextFloat() * 2f - 1f) * sin(p * PI.toFloat()) * 0.35f
            (tone + whoosh)
        }
    }

    /** Game Reset: Cascade reset sweep */
    fun playResetSound() {
        playCustomSound(durationMs = 260, volume = 0.38f) { t, p ->
            val freq = 320f + (p * 500f)
            sin(2.0 * PI * freq * t).toFloat() * 0.7f + sin(2.0 * PI * (freq * 2f) * t).toFloat() * 0.3f
        }
    }

    /** Tactile UI Button Click */
    fun playClickSound() {
        playCustomSound(durationMs = 45, volume = 0.28f) { t, p ->
            sin(2.0 * PI * (920f - p * 300f) * t).toFloat()
        }
    }

    /** Alert / Conflict Warning: Double buzzer pulse */
    fun playConflictSound() {
        playCustomSound(durationMs = 240, volume = 0.45f) { t, p ->
            val freq = if (p < 0.5f) 330f else 280f
            sin(2.0 * PI * freq * t).toFloat() * 0.7f + sin(2.0 * PI * (freq * 1.5f) * t).toFloat() * 0.3f
        }
    }

    /** Star Reveal Pop: Rising crystal bell chime per star (index 0, 1, 2) */
    fun playStarPopSound(starIndex: Int) {
        val baseFreq = when (starIndex) {
            0 -> 523.25f // C5
            1 -> 659.25f // E5
            else -> 783.99f // G5
        }
        playCustomSound(durationMs = 280, volume = 0.48f) { t, p ->
            val decay = (1f - p).coerceIn(0f, 1f)
            val tone = sin(2.0 * PI * baseFreq * t).toFloat() * 0.7f
            val overtone = sin(2.0 * PI * (baseFreq * 2f) * t).toFloat() * 0.3f
            (tone + overtone) * decay
        }
    }
}
