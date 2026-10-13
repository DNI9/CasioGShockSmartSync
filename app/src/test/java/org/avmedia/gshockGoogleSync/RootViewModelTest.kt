package org.avmedia.gshockGoogleSync

import org.avmedia.gshockGoogleSync.data.repository.GShockRepository
import org.avmedia.gshockGoogleSync.pairing.DeviceAssociationManager
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class RootViewModelTest {

    private lateinit var screenStateManager: ScreenStateManager
    private val mockRepository = mock(GShockRepository::class.java)
    private val mockDeviceAssociationManager = mock(DeviceAssociationManager::class.java)
    private lateinit var rootViewModel: RootViewModel

    @Before
    fun setUp() {
        screenStateManager = ScreenStateManager()
        rootViewModel = RootViewModel(
            screenStateManager = screenStateManager,
            repository = mockRepository,
            deviceAssociationManager = mockDeviceAssociationManager
        )
    }

    @Test
    fun `initial screen reflects ScreenStateManager initial state`() {
        assertEquals(AppScreen.INITIAL, rootViewModel.currentScreen.value)
        assertNull(rootViewModel.errorMessage.value)
    }

    @Test
    fun `onUnlockCover transitions ScreenStateManager to MAIN_NAVIGATION`() {
        rootViewModel.onUnlockCover()
        assertEquals(AppScreen.MAIN_NAVIGATION, rootViewModel.currentScreen.value)
    }

    @Test
    fun `checkPairedDevices delegates to DeviceAssociationManager`() = kotlinx.coroutines.runBlocking {
        rootViewModel.checkPairedDevices()
        verify(mockDeviceAssociationManager).checkPairedDevicesOrNotify()
    }

    @Test
    fun `clearError clears error in ScreenStateManager`() {
        screenStateManager.showError("Network error")
        rootViewModel.clearError()
        assertNull(rootViewModel.errorMessage.value)
    }
}
