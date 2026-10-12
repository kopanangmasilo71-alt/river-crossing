package com.example.particles

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.test.core.app.ApplicationProvider
import com.example.particles.engine.XmlParticleSimulationEngine
import com.example.particles.model.EmitterType
import com.example.particles.model.ParticleLayer
import com.example.particles.model.ParticleShape
import com.example.particles.model.ParticleTriggerState
import com.example.particles.parser.XmlParticleSystemParser
import com.example.particles.parser.XmlParticleSystemRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class XmlParticleSystemTest {

    @Test
    fun testFinalTenLevelsSupported() {
        for (lvl in 91..100) {
            assertTrue("Level $lvl should be supported in XML particle repository",
                XmlParticleSystemRepository.isLevelSupported(lvl))
        }
        assertFalse(XmlParticleSystemRepository.isLevelSupported(1))
        assertFalse(XmlParticleSystemRepository.isLevelSupported(90))
    }

    @Test
    fun testLoadAllFinalTenLevelsXml() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        for (lvl in 91..100) {
            val system = XmlParticleSystemRepository.getParticleSystem(context, lvl)
            assertNotNull("Particle system for level $lvl should load", system)
            system!!

            assertEquals(lvl, system.level)
            assertTrue("System name should not be blank for level $lvl", system.name.isNotBlank())
            assertTrue("System should contain at least 2 emitters for level $lvl", system.emitters.size >= 2)

            // Verify water and wind emitters exist
            val waterEmitter = system.emitters.firstOrNull { it.type == EmitterType.FLOWING_WATER }
            assertNotNull("Level $lvl should have a FLOWING_WATER emitter", waterEmitter)
            assertEquals(ParticleLayer.WATER_SURFACE, waterEmitter!!.layer)

            val windEmitter = system.emitters.firstOrNull { it.type == EmitterType.WIND }
            assertNotNull("Level $lvl should have a WIND emitter", windEmitter)
            assertEquals(ParticleLayer.ATMOSPHERE, windEmitter!!.layer)

            // Verify triggers exist for game states
            val rowingTrigger = waterEmitter.findTrigger(ParticleTriggerState.ROWING)
            assertNotNull("Flowing water should have a ROWING trigger", rowingTrigger)
            assertTrue(rowingTrigger!!.speedMultiplier > 1.0f)

            val victoryTrigger = windEmitter.findTrigger(ParticleTriggerState.VICTORY)
            assertNotNull("Wind emitter should have a VICTORY trigger", victoryTrigger)
            assertTrue(victoryTrigger!!.burstCount > 0)
        }
    }

    @Test
    fun testXmlStringParserDirect() {
        val xmlSample = """
            <?xml version="1.0" encoding="utf-8"?>
            <particle_system
                id="test_particles"
                level="99"
                name="Test Cosmic Void"
                description="Testing XML Schema parser">
                <emitter
                    id="test_water"
                    type="FLOWING_WATER"
                    layer="WATER_SURFACE"
                    spawnRate="30.0"
                    maxParticles="40"
                    xMin="0.2"
                    xMax="0.8"
                    yMin="0.0"
                    yMax="1.0"
                    speedMin="50.0"
                    speedMax="100.0"
                    colorStart="#FF00E5FF"
                    colorEnd="#FF1A1041"
                    alphaStart="0.9"
                    alphaEnd="0.1"
                    sizeStart="5.0"
                    sizeEnd="2.5"
                    shape="WAVE_RIPPLE"
                    lifetimeMinMs="1500"
                    lifetimeMaxMs="3000"
                    turbulence="2.0">
                    <trigger
                        gameState="ROWING"
                        speedMultiplier="2.5"
                        spawnRateMultiplier="2.0"
                        tintColor="#FFFFD700" />
                    <trigger
                        gameState="VICTORY"
                        burstCount="50"
                        sizeMultiplier="1.5" />
                </emitter>
            </particle_system>
        """.trimIndent()

        val parsed = XmlParticleSystemParser.parseXmlString(xmlSample)
        assertEquals("test_particles", parsed.id)
        assertEquals(99, parsed.level)
        assertEquals("Test Cosmic Void", parsed.name)
        assertEquals(1, parsed.emitters.size)

        val emitter = parsed.emitters[0]
        assertEquals("test_water", emitter.id)
        assertEquals(EmitterType.FLOWING_WATER, emitter.type)
        assertEquals(ParticleShape.WAVE_RIPPLE, emitter.shape)
        assertEquals(40, emitter.maxParticles)
        assertEquals(2, emitter.triggers.size)

        val rowing = emitter.findTrigger(ParticleTriggerState.ROWING)
        assertNotNull(rowing)
        assertEquals(2.5f, rowing!!.speedMultiplier)

        val victory = emitter.findTrigger(ParticleTriggerState.VICTORY)
        assertNotNull(victory)
        assertEquals(50, victory!!.burstCount)
    }

    @Test
    fun testSimulationEngineUpdatesParticles() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val system = XmlParticleSystemRepository.getParticleSystem(context, 100)
        assertNotNull(system)

        val engine = XmlParticleSimulationEngine(system!!)

        // Initial update with dtMs = 100ms in IDLE state
        engine.update(
            dtMs = 100f,
            canvasWidth = 800f,
            canvasHeight = 600f,
            currentState = ParticleTriggerState.IDLE,
            boatProgress = 0.0f,
            isBoatMovingRight = true
        )

        val idleParticles = engine.getActiveParticles()
        assertTrue("Engine should spawn particles after ticks", idleParticles.isNotEmpty())

        // Transition to ROWING state
        engine.update(
            dtMs = 100f,
            canvasWidth = 800f,
            canvasHeight = 600f,
            currentState = ParticleTriggerState.ROWING,
            boatProgress = 0.5f,
            isBoatMovingRight = true
        )

        val rowingParticles = engine.getActiveParticles()
        assertTrue("Engine should simulate active particles during ROWING", rowingParticles.isNotEmpty())

        // Transition to VICTORY state: burst particles should spawn
        val countBeforeVictory = engine.getActiveParticles().size
        engine.update(
            dtMs = 50f,
            canvasWidth = 800f,
            canvasHeight = 600f,
            currentState = ParticleTriggerState.VICTORY,
            boatProgress = 1.0f,
            isBoatMovingRight = false
        )
        val countAfterVictory = engine.getActiveParticles().size
        assertTrue("Victory transition should spawn celebratory burst particles",
            countAfterVictory >= countBeforeVictory)
    }

    @Test
    fun testColorHexParsing() {
        assertEquals(Color(0xFF00E5FF), XmlParticleSystemParser.parseHexColor("#FF00E5FF"))
        assertEquals(Color(0xFFFFD700), XmlParticleSystemParser.parseHexColor("#FFD700"))
        assertEquals(Color.White, XmlParticleSystemParser.parseHexColor(null))
        assertEquals(Color.White, XmlParticleSystemParser.parseHexColor(""))
    }
}
