package org.avmedia.gshockGoogleSync

import org.avmedia.gshockGoogleSync.data.repository.GShockRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class ScreenStateManagerTest {

    private lateinit var screenStateManager: ScreenStateManager
    private val mockRepository = mock(GShockRepository::class.java)

    @Before
    fun setUp() {
        screenStateManager = ScreenStateManager()
    }

    @Test
    fun `initial state is INITIAL and error is null`() {
        assertEquals(AppScreen.INITIAL, screenStateManager.currentScreen.value)
        assertNull(screenStateManager.errorMessage.value)
    }

    @Test
    fun `showPreConnectionScreen transitions to PRE_CONNECTION`() {
        screenStateManager.showPreConnectionScreen()
        assertEquals(AppScreen.PRE_CONNECTION, screenStateManager.currentScreen.value)
    }

    @Test
    fun `showContentSelector transitions to CONTENT_SELECTOR`() {
        screenStateManager.showContentSelector(mockRepository)
        assertEquals(AppScreen.CONTENT_SELECTOR, screenStateManager.currentScreen.value)
    }

    @Test
    fun `showRunActionsScreen transitions to RUN_ACTIONS`() {
        screenStateManager.showRunActionsScreen()
        assertEquals(AppScreen.RUN_ACTIONS, screenStateManager.currentScreen.value)
    }

    @Test
    fun `goToNavigationScreen transitions to MAIN_NAVIGATION`() {
        screenStateManager.goToNavigationScreen()
        assertEquals(AppScreen.MAIN_NAVIGATION, screenStateManager.currentScreen.value)
    }

    @Test
    fun `showError sets error message and transitions to ERROR`() {
        val error = "Connection failed"
        screenStateManager.showError(error)
        assertEquals(AppScreen.ERROR, screenStateManager.currentScreen.value)
        assertEquals(error, screenStateManager.errorMessage.value)
    }

    @Test
    fun `clearError clears error message`() {
        screenStateManager.showError("Some error")
        screenStateManager.clearError()
        assertNull(screenStateManager.errorMessage.value)
    }
}
