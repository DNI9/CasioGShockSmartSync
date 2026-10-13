package org.avmedia.gshockGoogleSync

import org.avmedia.gshockGoogleSync.domain.model.WatchCapability
import org.avmedia.gshockGoogleSync.ui.common.WatchFeatureManager
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

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
        assertTrue("All capabilities must be distinct", uniqueSet.size == capabilities.size)
    }

    @Test
    fun `watch capability mapping functions cleanly on WatchFeatureManager`() {
        val manager = WatchFeatureManager()
        // By default with no watch connected, lookup defaults to true or false without exceptions
        for (cap in listOf(
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
        )) {
            val supported = manager.isCapabilitySupported(cap)
            assertNotNull(supported)
        }
    }
}
