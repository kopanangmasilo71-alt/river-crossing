package com.example.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import com.example.model.GameItem
import com.example.model.WeatherEffectType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.sin
import kotlin.random.Random

/**
 * AAA Procedural Sound Synthesis Engine for River Crossing.
 * Generates rich, artifact-free 16-bit PCM audio natively without external heavy asset files.
 * Includes continuous multi-layered biome ambience (river currents, bird calls, crickets, celestial chimes)
 * and terrain-specific procedural footsteps.
 */
class GameAudio {
    private val scope = CoroutineScope(Dispatchers.Default)

    private val _isMutedState = MutableStateFlow(false)
    val isMutedState: StateFlow<Boolean> = _isMutedState.asStateFlow()

    private var ambientLoopJob: Job? = null
    private var currentActiveBiome: WeatherEffectType? = null

    var isMuted: Boolean
        get() = _isMutedState.value
        set(value) {
            _isMutedState.value = value
            if (value) {
                stopBiomeAmbience()
            } else {
                currentActiveBiome?.let { startBiomeAmbience(it) }
            }
        }

    fun toggleMute(): Boolean {
        val newState = !_isMutedState.value
        _isMutedState.value = newState
        if (!newState) {
            playClickSound()
            currentActiveBiome?.let { startBiomeAmbience(it) }
        } else {
            stopBiomeAmbience()
        }
        return newState
    }

    /**
     * Starts the procedural continuous ambient background sound generator for the current biome.
     * Dynamically blends organic water flow currents with intermittent natural soundscapes:
     * - SPRING_VALLEY: Gentle river ripples, cheerful songbirds (warblers/finches)
     * - AUTUMN_LEAVES: Amber river rapids, woodland robins and falling foliage rustle
     * - ALPINE_PEAKS: Glacial torrent rapids, soaring mountain hawk/eagle calls, glacial echoes
     * - SAVANNAH_SUN: Broad lazy river, distant savanna shrike birds, heat cicadas
     * - MIDNIGHT_STARLIGHT: Calm dark water, nocturnal crickets and gentle night owl calls
     * - TWILIGHT_MOTES: Mystical bubbling mineral stream, resonant loon calls & dusk motes
     * - OASIS_MIRAGE: Turquoise lagoon lap, tropical desert parakeet / palm warblers
     * - AURORA_SHIMMER: Prismatic ethereal glacial stream, celestial harmonic crystal chimes
     */
    fun startBiomeAmbience(biome: WeatherEffectType) {
        currentActiveBiome = biome
        if (isMuted) return

        ambientLoopJob?.cancel()
        ambientLoopJob = scope.launch {
            // Initial river rush upon level entry
            playRiverFlow(biome, durationMs = 2800, volume = 0.22f)
            delay(1200L)

            while (isActive && !isMuted) {
                // Play rolling river flow layer with slight organic variations
                val flowDuration = Random.nextInt(3200, 4800)
                playRiverFlow(biome, durationMs = flowDuration, volume = 0.20f)

                // Randomly trigger intermittent nature calls (bird calls, crickets, celestial bells)
                delay(Random.nextLong(1400L, 2600L))
                if (!isActive || isMuted) break

                val eventChoice = Random.nextInt(100)
                when {
                    eventChoice < 65 -> {
                        // Bird call or nocturnal wildlife sound
                        playBiomeBirdCall(biome)
                    }
                    else -> {
                        // Soft surface river surge / droplet
                        playWaterRippleSound()
                    }
                }

                delay(Random.nextLong(2000L, 4200L))
            }
        }
    }

    /**
     * Stops the ambient background loop.
     */
    fun stopBiomeAmbience() {
        ambientLoopJob?.cancel()
        ambientLoopJob = null
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
            GameItem.PHOENIX -> playPhoenixChirp()
            GameItem.DRAGON -> playDragonRoar()
            GameItem.UNICORN -> playUnicornWhinny()
            GameItem.KRAKEN -> playKrakenSurge()
            GameItem.GRIFFIN -> playGriffinScreech()
            GameItem.CERBERUS -> playCerberusBark()
            GameItem.STAR_CRYSTAL -> playStarCrystalChime()
            GameItem.SUN_CHALICE -> playSunChaliceResonance()
            GameItem.CELESTIAL_LOTUS -> playLotusBloomSound()
            GameItem.GOLDEN_ORB -> playOrbHumSound()
            GameItem.ASTRAL_CROWN -> playCrownChime()
            GameItem.MYTHIC_STAG -> playStagBugle()
            else -> playWaterRippleSound()
        }
    }

    // -------------------------------------------------------------
    // PROCEDURAL BIOME SOUND ENGINE (RIVER FLOW, BIRD CALLS, FOOTSTEPS)
    // -------------------------------------------------------------

    /**
     * Synthesizes continuous organic river water flow acoustics dynamically tailored to the biome.
     * Generates rich pink-noise filtered water swirls, tumbling brook ripples, glacial currents,
     * or desert oasis lap waves.
     */
    fun playRiverFlow(
        biome: WeatherEffectType,
        durationMs: Int = 3600,
        volume: Float = 0.20f
    ) {
        playCustomSound(durationMs = durationMs, volume = volume) { t, p ->
            // Base water wave frequencies adapt by biome
            val (baseFreq, modulationRate, depth) = when (biome) {
                WeatherEffectType.SPRING_PETALS -> Triple(280f, 0.45f, 0.65f)      // Gentle crystal stream
                WeatherEffectType.AUTUMN_LEAVES -> Triple(240f, 0.55f, 0.70f)      // Woodland rushing creek
                WeatherEffectType.ALPINE_MIST -> Triple(180f, 0.85f, 0.85f)        // Glacial mountain torrent
                WeatherEffectType.SAVANNAH_DUST -> Triple(150f, 0.30f, 0.50f)      // Wide lazy warm river
                WeatherEffectType.MIDNIGHT_FIREFLIES -> Triple(210f, 0.25f, 0.45f) // Serene tranquil night water
                WeatherEffectType.TWILIGHT_MOTES -> Triple(320f, 0.40f, 0.60f)     // Mystical mineral spring
                WeatherEffectType.OASIS_MIRAGE -> Triple(190f, 0.35f, 0.55f)       // Tropical palm lagoon lap
                WeatherEffectType.AURORA_SHIMMER -> Triple(350f, 0.60f, 0.75f)     // Prismatic glacial crystal melt
            }

            // Organic natural swelling and wave modulation
            val flowSwell = sin(2.0 * PI * modulationRate * t).toFloat() * 0.4f + 0.6f
            val currentRipple = sin(2.0 * PI * (baseFreq + sin(2.0 * PI * 1.8 * t).toFloat() * 45f) * t).toFloat() * 0.35f
            val subCurrent = sin(2.0 * PI * (baseFreq * 0.55f) * t).toFloat() * 0.25f

            // Filtered water surface turbulence
            val waterTurbulence = (Random.nextFloat() * 2f - 1f) * depth * (1f - p * 0.15f) * 0.40f

            (currentRipple + subCurrent + waterTurbulence) * flowSwell
        }
    }

    /**
     * Synthesizes authentic, melodic bird calls and nocturnal nature calls tailored to the game biome.
     * Uses frequency modulation and chirped harmonic envelopes.
     */
    fun playBiomeBirdCall(biome: WeatherEffectType) {
        when (biome) {
            WeatherEffectType.SPRING_PETALS -> {
                // Cheerful Spring Warbler: Rising multi-note trill (C6 -> G6 melodic chirp)
                playCustomSound(durationMs = 380, volume = 0.34f) { t, p ->
                    val noteIndex = (p * 4f).toInt()
                    val baseFreq = when (noteIndex) {
                        0 -> 1046.50f // C6
                        1 -> 1318.51f // E6
                        2 -> 1567.98f // G6
                        else -> 2093.00f // C7
                    }
                    val trill = sin(2.0 * PI * 28.0 * t).toFloat() * 120f
                    val birdTone = sin(2.0 * PI * (baseFreq + trill) * t).toFloat() * 0.75f
                    val airOvertone = sin(2.0 * PI * ((baseFreq + trill) * 2f) * t).toFloat() * 0.25f
                    (birdTone + airOvertone)
                }
            }
            WeatherEffectType.AUTUMN_LEAVES -> {
                // Woodland Thrush / Meadowlark: Melodious descending triple whistle
                playCustomSound(durationMs = 420, volume = 0.32f) { t, p ->
                    val freq = 1760f - (sin(p * PI.toFloat() * 2.5f) * 320f) - p * 200f
                    val whistle = sin(2.0 * PI * freq * t).toFloat() * 0.85f
                    val flutter = (Random.nextFloat() * 2f - 1f) * 0.15f * (1f - p)
                    (whistle + flutter)
                }
            }
            WeatherEffectType.ALPINE_MIST -> {
                // Mountain Raptor / High-Altitude Hawk screech
                playCustomSound(durationMs = 520, volume = 0.36f) { t, p ->
                    val glideFreq = 1950f + sin(p * PI.toFloat()) * 480f - p * 500f
                    val screech = sin(2.0 * PI * glideFreq * t).toFloat() * 0.70f
                    val rasp = (Random.nextFloat() * 2f - 1f) * (1f - p * 0.5f) * 0.30f
                    (screech + rasp)
                }
            }
            WeatherEffectType.SAVANNAH_DUST -> {
                // Sunlit Savanna Lark & Grassland Cicadas
                playCustomSound(durationMs = 460, volume = 0.30f) { t, p ->
                    val cicadaBuzz = (Random.nextFloat() * 2f - 1f) * sin(2.0 * PI * 42.0 * t).toFloat() * 0.35f
                    val larkFreq = 1450f + sin(2.0 * PI * 18.0 * t).toFloat() * 180f
                    val larkChirp = sin(2.0 * PI * larkFreq * t).toFloat() * 0.65f
                    (larkChirp + cicadaBuzz)
                }
            }
            WeatherEffectType.MIDNIGHT_FIREFLIES -> {
                // Nocturnal Crickets & Night Owl Hoot
                playCustomSound(durationMs = 600, volume = 0.32f) { t, p ->
                    val owlPhase = if (p < 0.45f) sin(p / 0.45f * PI.toFloat()) else sin((p - 0.45f) / 0.55f * PI.toFloat())
                    val owlFreq = 340f + owlPhase * 70f
                    val owlTone = sin(2.0 * PI * owlFreq * t).toFloat() * 0.75f
                    val cricketTrill = (Random.nextFloat() * 2f - 1f) * sin(2.0 * PI * 65.0 * t).toFloat() * 0.25f
                    (owlTone + cricketTrill)
                }
            }
            WeatherEffectType.TWILIGHT_MOTES -> {
                // Twilight Canyon Loon: Haunting resonant wail over lake
                playCustomSound(durationMs = 540, volume = 0.34f) { t, p ->
                    val freq = 587.33f + sin(p * PI.toFloat()) * 180f // D5
                    val loon = sin(2.0 * PI * freq * t).toFloat() * 0.70f
                    val overtone = sin(2.0 * PI * (freq * 1.5f) * t).toFloat() * 0.30f
                    (loon + overtone)
                }
            }
            WeatherEffectType.OASIS_MIRAGE -> {
                // Palm Parakeet & Desert Warbler: Sweet rapid warble
                playCustomSound(durationMs = 360, volume = 0.33f) { t, p ->
                    val freq = 1200f + sin(2.0 * PI * 34.0 * t).toFloat() * 280f
                    val chirps = sin(2.0 * PI * freq * t).toFloat() * 0.85f
                    val air = (Random.nextFloat() * 2f - 1f) * 0.15f * (1f - p)
                    (chirps + air)
                }
            }
            WeatherEffectType.AURORA_SHIMMER -> {
                // Polar Snow Bunting & Ethereal Celestial Glacial Chimes
                playCustomSound(durationMs = 680, volume = 0.35f) { t, p ->
                    val f1 = 1567.98f + sin(p * PI.toFloat()) * 300f // G6
                    val f2 = 2093.00f                                // C7
                    val bell = sin(2.0 * PI * f1 * t).toFloat() * 0.5f + sin(2.0 * PI * f2 * t).toFloat() * 0.35f
                    val shimmer = sin(2.0 * PI * (f1 * 2.2f) * t).toFloat() * 0.15f
                    (bell + shimmer) * (1f - p * 0.4f)
                }
            }
        }
    }

    /**
     * Procedural Footsteps: Tailors board & bank step acoustics to the current ground terrain.
     * Grass, pebble/rock, dry timber, and sandy shores sound distinctly tactile.
     */
    fun playFootstep(biome: WeatherEffectType, isBoardingRaft: Boolean) {
        if (isBoardingRaft) {
            // Wooden plank footstep onto the raft dock
            playCustomSound(durationMs = 90, volume = 0.32f) { t, p ->
                val woodThump = sin(2.0 * PI * (190f - p * 80f) * t).toFloat() * 0.75f
                val woodCreak = (Random.nextFloat() * 2f - 1f) * (1f - p * 0.8f) * 0.25f
                (woodThump + woodCreak)
            }
        } else {
            // Shore ground footstep based on the biome terrain
            when (biome) {
                WeatherEffectType.SPRING_PETALS, WeatherEffectType.TWILIGHT_MOTES -> {
                    // Soft lush meadow grass step
                    playCustomSound(durationMs = 95, volume = 0.28f) { t, p ->
                        val grass = sin(2.0 * PI * (320f - p * 120f) * t).toFloat() * 0.6f
                        val rustle = (Random.nextFloat() * 2f - 1f) * (1f - p * 0.85f) * 0.4f
                        (grass + rustle)
                    }
                }
                WeatherEffectType.AUTUMN_LEAVES -> {
                    // Crisp autumn leaf litter crunch
                    playCustomSound(durationMs = 110, volume = 0.32f) { t, p ->
                        val leafCrunch = (Random.nextFloat() * 2f - 1f) * (1f - p * 0.7f) * 0.70f
                        val earthSnap = sin(2.0 * PI * (440f - p * 200f) * t).toFloat() * 0.30f
                        (leafCrunch + earthSnap)
                    }
                }
                WeatherEffectType.ALPINE_MIST, WeatherEffectType.AURORA_SHIMMER -> {
                    // Crisp crunchy snow / glacial pebble gravel
                    playCustomSound(durationMs = 100, volume = 0.34f) { t, p ->
                        val gravelFreq = 540f - p * 180f
                        val grit = sin(2.0 * PI * gravelFreq * t).toFloat() * 0.45f
                        val crunch = (Random.nextFloat() * 2f - 1f) * (1f - p * 0.6f) * 0.55f
                        (grit + crunch)
                    }
                }
                WeatherEffectType.SAVANNAH_DUST, WeatherEffectType.OASIS_MIRAGE -> {
                    // Soft sandy dry bank scuff
                    playCustomSound(durationMs = 105, volume = 0.30f) { t, p ->
                        val sandHiss = (Random.nextFloat() * 2f - 1f) * (1f - p * 0.8f) * 0.65f
                        val sandThump = sin(2.0 * PI * (220f - p * 90f) * t).toFloat() * 0.35f
                        (sandHiss + sandThump)
                    }
                }
                WeatherEffectType.MIDNIGHT_FIREFLIES -> {
                    // Damp riverbank moss step
                    playCustomSound(durationMs = 95, volume = 0.29f) { t, p ->
                        val moss = sin(2.0 * PI * (260f - p * 80f) * t).toFloat() * 0.65f
                        val squish = (Random.nextFloat() * 2f - 1f) * (1f - p * 0.85f) * 0.35f
                        (moss + squish)
                    }
                }
            }
        }
    }

    // -------------------------------------------------------------
    // MYTHIC GAME OBJECT SOUND EFFECTS (LEVELS 91-100)
    // -------------------------------------------------------------

    /** Phoenix: Radiant vermilion solar flame cry & blazing wings */
    fun playPhoenixChirp() {
        playCustomSound(durationMs = 380, volume = 0.48f) { t, p ->
            val solarFreq = 880f + sin(p * PI.toFloat() * 2f) * 440f
            val cry = sin(2.0 * PI * solarFreq * t).toFloat() * 0.70f
            val flame = (Random.nextFloat() * 2f - 1f) * sin(p * PI.toFloat()) * 0.30f
            (cry + flame)
        }
    }

    /** Dragon: Majestic glacial frost wyrm roar */
    fun playDragonRoar() {
        playCustomSound(durationMs = 450, volume = 0.52f) { t, p ->
            val freq = 90f + sin(p * PI.toFloat()) * 75f
            val roar = sin(2.0 * PI * freq * t).toFloat() * 0.60f
            val sub = sin(2.0 * PI * (freq * 0.5f) * t).toFloat() * 0.25f
            val frost = (Random.nextFloat() * 2f - 1f) * 0.25f
            (roar + sub + frost)
        }
    }

    /** Unicorn: Ethereal celestial whinny with starlight resonance */
    fun playUnicornWhinny() {
        playCustomSound(durationMs = 360, volume = 0.42f) { t, p ->
            val freq = 650f + sin(p * 24f) * 120f + (1f - p) * 200f
            val tone = sin(2.0 * PI * freq * t).toFloat() * 0.70f
            val sparkle = sin(2.0 * PI * (freq * 2.5f) * t).toFloat() * 0.30f
            (tone + sparkle)
        }
    }

    /** Kraken: Deep abyssal sea rumble and suction surge */
    fun playKrakenSurge() {
        playCustomSound(durationMs = 420, volume = 0.48f) { t, p ->
            val freq = 75f + sin(p * PI.toFloat()) * 50f
            val abyss = sin(2.0 * PI * freq * t).toFloat() * 0.65f
            val waterSurge = (Random.nextFloat() * 2f - 1f) * sin(p * PI.toFloat()) * 0.35f
            (abyss + waterSurge)
        }
    }

    /** Griffin: Regal raptor-lion sky screech */
    fun playGriffinScreech() {
        playCustomSound(durationMs = 340, volume = 0.46f) { t, p ->
            val freq = 1200f + sin(p * PI.toFloat()) * 600f - p * 300f
            val screech = sin(2.0 * PI * freq * t).toFloat() * 0.75f
            val growl = sin(2.0 * PI * 180f * t).toFloat() * 0.25f
            (screech + growl)
        }
    }

    /** Cerberus: Volcanic three-headed nether hound bark */
    fun playCerberusBark() {
        playCustomSound(durationMs = 300, volume = 0.48f) { t, p ->
            val f1 = 280f - p * 80f
            val f2 = 220f - p * 60f
            val b1 = sin(2.0 * PI * f1 * t).toFloat() * 0.45f
            val b2 = sin(2.0 * PI * f2 * t).toFloat() * 0.40f
            val magma = (Random.nextFloat() * 2f - 1f) * 0.25f * (1f - p)
            (b1 + b2 + magma)
        }
    }

    /** Star Crystal: Multi-faceted cosmic starlight chime */
    fun playStarCrystalChime() {
        playCustomSound(durationMs = 340, volume = 0.42f) { t, p ->
            val f1 = 1760.0f // A6
            val f2 = 2217.46f // C#7
            val c1 = sin(2.0 * PI * f1 * t).toFloat() * 0.55f
            val c2 = sin(2.0 * PI * f2 * t).toFloat() * 0.45f
            (c1 + c2) * (1f - p * 0.5f)
        }
    }

    /** Sun Chalice: Imperial solar ambrosia bell resonance */
    fun playSunChaliceResonance() {
        playCustomSound(durationMs = 400, volume = 0.44f) { t, p ->
            val f = 783.99f // G5
            val bell = sin(2.0 * PI * f * t).toFloat() * 0.65f
            val warmHarmonic = sin(2.0 * PI * (f * 1.5f) * t).toFloat() * 0.35f
            (bell + warmHarmonic) * (1f - p * 0.4f)
        }
    }

    /** Celestial Lotus: Gentle blossom unfold with water droplet */
    fun playLotusBloomSound() {
        playCustomSound(durationMs = 280, volume = 0.38f) { t, p ->
            val freq = 980f + sin(p * PI.toFloat()) * 400f
            val bloom = sin(2.0 * PI * freq * t).toFloat() * 0.70f
            val dew = (Random.nextFloat() * 2f - 1f) * 0.30f * (1f - p)
            (bloom + dew)
        }
    }

    /** Golden Orb: Primordial orbital pearl hum */
    fun playOrbHumSound() {
        playCustomSound(durationMs = 420, volume = 0.42f) { t, p ->
            val orbitalFreq = 440f + sin(2.0 * PI * 12.0 * t).toFloat() * 60f
            val hum = sin(2.0 * PI * orbitalFreq * t).toFloat() * 0.75f
            val pulse = sin(2.0 * PI * 220f * t).toFloat() * 0.25f
            (hum + pulse)
        }
    }

    /** Astral Crown: Imperial cosmic crest chime */
    fun playCrownChime() {
        playCustomSound(durationMs = 360, volume = 0.45f) { t, p ->
            val c6 = sin(2.0 * PI * 1046.50 * t).toFloat() * 0.5f
            val e6 = sin(2.0 * PI * 1318.51 * t).toFloat() * 0.35f
            val g6 = sin(2.0 * PI * 1567.98 * t).toFloat() * 0.25f
            (c6 + e6 + g6) * (1f - p * 0.4f)
        }
    }

    /** Mythic Stag: Ancient forest sovereign bugle */
    fun playStagBugle() {
        playCustomSound(durationMs = 480, volume = 0.46f) { t, p ->
            val bugleFreq = 380f + sin(p * PI.toFloat()) * 260f
            val horn = sin(2.0 * PI * bugleFreq * t).toFloat() * 0.75f
            val echo = sin(2.0 * PI * (bugleFreq * 0.5f) * t).toFloat() * 0.25f
            (horn + echo)
        }
    }

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
    fun playBoardSound(item: GameItem? = null, biome: WeatherEffectType = WeatherEffectType.SPRING_PETALS) {
        playFootstep(biome, isBoardingRaft = true)
        item?.let {
            scope.launch {
                delay(90)
                playCharacterSound(it)
            }
        }
    }

    /** Disembarking Boat: Procedural terrain ground footstep based on the biome */
    fun playUnboardSound(item: GameItem? = null, biome: WeatherEffectType = WeatherEffectType.SPRING_PETALS) {
        playFootstep(biome, isBoardingRaft = false)
        item?.let {
            scope.launch {
                delay(80)
                playCharacterSound(it)
            }
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
