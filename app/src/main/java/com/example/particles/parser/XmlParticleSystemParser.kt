package com.example.particles.parser

import android.content.Context
import androidx.compose.ui.graphics.Color
import com.example.R
import com.example.particles.model.EmitterType
import com.example.particles.model.ParticleLayer
import com.example.particles.model.ParticleShape
import com.example.particles.model.ParticleTriggerState
import com.example.particles.model.XmlParticleEmitter
import com.example.particles.model.XmlParticleSystem
import com.example.particles.model.XmlStateTrigger
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream
import java.io.StringReader

/**
 * Parser for the XML-based particle system definitions.
 * Compatible with Android's XmlResourceParser (from res/xml/) as well as standard XmlPullParser.
 */
object XmlParticleSystemParser {

    fun parse(parser: XmlPullParser): XmlParticleSystem {
        var systemId = ""
        var systemLevel = 0
        var systemName = ""
        var systemDescription = ""
        val emitters = mutableListOf<XmlParticleEmitter>()

        var currentEmitter: XmlParticleEmitterBuilder? = null

        var eventType = parser.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {
            val tagName = parser.name
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    when (tagName) {
                        "particle_system" -> {
                            systemId = parser.getAttribute("id") ?: "unknown"
                            systemLevel = parser.getAttribute("level")?.toIntOrNull() ?: 0
                            systemName = parser.getAttribute("name") ?: ""
                            systemDescription = parser.getAttribute("description") ?: ""
                        }
                        "emitter" -> {
                            currentEmitter = XmlParticleEmitterBuilder(
                                id = parser.getAttribute("id") ?: "emitter_${emitters.size}",
                                type = EmitterType.fromString(parser.getAttribute("type")),
                                layer = ParticleLayer.fromString(parser.getAttribute("layer")),
                                spawnRate = parser.getAttribute("spawnRate")?.toFloatOrNull() ?: 20.0f,
                                maxParticles = parser.getAttribute("maxParticles")?.toIntOrNull() ?: 60,
                                xMin = parser.getAttribute("xMin")?.toFloatOrNull() ?: 0.0f,
                                xMax = parser.getAttribute("xMax")?.toFloatOrNull() ?: 1.0f,
                                yMin = parser.getAttribute("yMin")?.toFloatOrNull() ?: 0.0f,
                                yMax = parser.getAttribute("yMax")?.toFloatOrNull() ?: 1.0f,
                                speedMin = parser.getAttribute("speedMin")?.toFloatOrNull() ?: 20.0f,
                                speedMax = parser.getAttribute("speedMax")?.toFloatOrNull() ?: 60.0f,
                                angleMin = parser.getAttribute("angleMin")?.toFloatOrNull() ?: 0.0f,
                                angleMax = parser.getAttribute("angleMax")?.toFloatOrNull() ?: 360.0f,
                                colorStart = parseHexColor(parser.getAttribute("colorStart")),
                                colorEnd = parseHexColor(parser.getAttribute("colorEnd")),
                                alphaStart = parser.getAttribute("alphaStart")?.toFloatOrNull() ?: 1.0f,
                                alphaEnd = parser.getAttribute("alphaEnd")?.toFloatOrNull() ?: 0.0f,
                                sizeStart = parser.getAttribute("sizeStart")?.toFloatOrNull() ?: 4.0f,
                                sizeEnd = parser.getAttribute("sizeEnd")?.toFloatOrNull() ?: 2.0f,
                                shape = ParticleShape.fromString(parser.getAttribute("shape")),
                                lifetimeMinMs = parser.getAttribute("lifetimeMinMs")?.toLongOrNull() ?: 2000L,
                                lifetimeMaxMs = parser.getAttribute("lifetimeMaxMs")?.toLongOrNull() ?: 4000L,
                                turbulence = parser.getAttribute("turbulence")?.toFloatOrNull() ?: 1.0f,
                                swayAmplitude = parser.getAttribute("swayAmplitude")?.toFloatOrNull() ?: 10.0f,
                                swayFrequency = parser.getAttribute("swayFrequency")?.toFloatOrNull() ?: 1.0f,
                                windForceX = parser.getAttribute("windForceX")?.toFloatOrNull() ?: 0.0f,
                                windForceY = parser.getAttribute("windForceY")?.toFloatOrNull() ?: 0.0f
                            )
                        }
                        "trigger" -> {
                            val trigger = XmlStateTrigger(
                                gameState = ParticleTriggerState.fromString(parser.getAttribute("gameState")),
                                speedMultiplier = parser.getAttribute("speedMultiplier")?.toFloatOrNull() ?: 1.0f,
                                spawnRateMultiplier = parser.getAttribute("spawnRateMultiplier")?.toFloatOrNull() ?: 1.0f,
                                turbulenceMultiplier = parser.getAttribute("turbulenceMultiplier")?.toFloatOrNull() ?: 1.0f,
                                sizeMultiplier = parser.getAttribute("sizeMultiplier")?.toFloatOrNull() ?: 1.0f,
                                tintColor = parser.getAttribute("tintColor")?.let { parseHexColor(it) },
                                windForceX = parser.getAttribute("windForceX")?.toFloatOrNull(),
                                windForceY = parser.getAttribute("windForceY")?.toFloatOrNull(),
                                burstCount = parser.getAttribute("burstCount")?.toIntOrNull() ?: 0
                            )
                            currentEmitter?.triggers?.add(trigger)
                        }
                    }
                }
                XmlPullParser.END_TAG -> {
                    if (tagName == "emitter" && currentEmitter != null) {
                        emitters.add(currentEmitter.build())
                        currentEmitter = null
                    }
                }
            }
            eventType = parser.next()
        }

        return XmlParticleSystem(
            id = systemId,
            level = systemLevel,
            name = systemName,
            description = systemDescription,
            emitters = emitters
        )
    }

    fun parseXmlString(xmlContent: String): XmlParticleSystem {
        val factory = XmlPullParserFactory.newInstance()
        val parser = factory.newPullParser()
        parser.setInput(StringReader(xmlContent))
        return parse(parser)
    }

    fun parseInputStream(inputStream: InputStream): XmlParticleSystem {
        val factory = XmlPullParserFactory.newInstance()
        val parser = factory.newPullParser()
        parser.setInput(inputStream, "UTF-8")
        return parse(parser)
    }

    private fun XmlPullParser.getAttribute(name: String): String? {
        return getAttributeValue(null, name)
    }

    fun parseHexColor(colorStr: String?): Color {
        if (colorStr.isNullOrBlank()) return Color.White
        val clean = colorStr.trim().removePrefix("#")
        val colorLong = clean.toLongOrNull(16) ?: return Color.White
        return if (clean.length <= 6) {
            Color(0xFF000000L or colorLong)
        } else {
            Color(colorLong)
        }
    }

    private class XmlParticleEmitterBuilder(
        val id: String,
        val type: EmitterType,
        val layer: ParticleLayer,
        val spawnRate: Float,
        val maxParticles: Int,
        val xMin: Float,
        val xMax: Float,
        val yMin: Float,
        val yMax: Float,
        val speedMin: Float,
        val speedMax: Float,
        val angleMin: Float,
        val angleMax: Float,
        val colorStart: Color,
        val colorEnd: Color,
        val alphaStart: Float,
        val alphaEnd: Float,
        val sizeStart: Float,
        val sizeEnd: Float,
        val shape: ParticleShape,
        val lifetimeMinMs: Long,
        val lifetimeMaxMs: Long,
        val turbulence: Float,
        val swayAmplitude: Float,
        val swayFrequency: Float,
        val windForceX: Float,
        val windForceY: Float,
        val triggers: MutableList<XmlStateTrigger> = mutableListOf()
    ) {
        fun build(): XmlParticleEmitter = XmlParticleEmitter(
            id = id,
            type = type,
            layer = layer,
            spawnRate = spawnRate,
            maxParticles = maxParticles,
            xMin = xMin,
            xMax = xMax,
            yMin = yMin,
            yMax = yMax,
            speedMin = speedMin,
            speedMax = speedMax,
            angleMin = angleMin,
            angleMax = angleMax,
            colorStart = colorStart,
            colorEnd = colorEnd,
            alphaStart = alphaStart,
            alphaEnd = alphaEnd,
            sizeStart = sizeStart,
            sizeEnd = sizeEnd,
            shape = shape,
            lifetimeMinMs = lifetimeMinMs,
            lifetimeMaxMs = lifetimeMaxMs,
            turbulence = turbulence,
            swayAmplitude = swayAmplitude,
            swayFrequency = swayFrequency,
            windForceX = windForceX,
            windForceY = windForceY,
            triggers = triggers
        )
    }
}

/**
 * Repository to load and cache XML-defined particle systems for the final 10 levels (91 to 100).
 */
object XmlParticleSystemRepository {

    private val cache = mutableMapOf<Int, XmlParticleSystem>()

    private val levelXmlResourceMap: Map<Int, Int> = mapOf(
        91 to R.xml.particles_level_91,
        92 to R.xml.particles_level_92,
        93 to R.xml.particles_level_93,
        94 to R.xml.particles_level_94,
        95 to R.xml.particles_level_95,
        96 to R.xml.particles_level_96,
        97 to R.xml.particles_level_97,
        98 to R.xml.particles_level_98,
        99 to R.xml.particles_level_99,
        100 to R.xml.particles_level_100
    )

    fun isLevelSupported(levelNumber: Int): Boolean {
        return levelXmlResourceMap.containsKey(levelNumber)
    }

    fun getParticleSystem(context: Context, levelNumber: Int): XmlParticleSystem? {
        if (!isLevelSupported(levelNumber)) return null
        cache[levelNumber]?.let { return it }

        val resId = levelXmlResourceMap[levelNumber] ?: return null
        return try {
            val parser = context.resources.getXml(resId)
            val system = XmlParticleSystemParser.parse(parser)
            cache[levelNumber] = system
            system
        } catch (e: Exception) {
            null
        }
    }

    fun clearCache() {
        cache.clear()
    }
}
