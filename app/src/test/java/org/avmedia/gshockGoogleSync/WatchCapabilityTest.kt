package org.avmedia.gshockGoogleSync

import org.avmedia.gshockGoogleSync.domain.model.WatchCapability
import org.avmedia.gshockGoogleSync.ui.common.IWatchFeatureManager
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class WatchCapabilityTest {

    @Test
    fun `all watch capabilities have unique instances`() {
        val capabilities = listOf(
            WatchCapability.StepCounter,
            WatchCapability.Reminders,
            WatchCapability.WorldCities,
            WatchCapability.SolarBattery,
            WatchCapability.Temperature,
            WatchCapability.AutoLight,
            WatchCapability.PowerSaving,
            WatchCapability.AlwaysConnectedBle,
            WatchCapability.MultipleFonts,
            WatchCapability.OperationSound,
            WatchCapability.OperationVibration,
            WatchCapability.HourlyChime,
            WatchCapability.FindPhone
        )

        val uniqueSet = capabilities.toSet()
        assertEquals(13, uniqueSet.size)
        assertTrue("All capabilities must be distinct", uniqueSet.size == capabilities.size)
    }

    @Test
    fun `watch capability mapping can be queried through IWatchFeatureManager`() {
        val manager = mock(IWatchFeatureManager::class.java)
        `when`(manager.isCapabilitySupported(WatchCapability.StepCounter)).thenReturn(true)
        `when`(manager.isCapabilitySupported(WatchCapability.Reminders)).thenReturn(false)

        assertTrue(manager.isCapabilitySupported(WatchCapability.StepCounter))
        assertFalse(manager.isCapabilitySupported(WatchCapability.Reminders))
    }
}
