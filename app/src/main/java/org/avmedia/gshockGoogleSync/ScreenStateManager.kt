package org.avmedia.gshockGoogleSync

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.avmedia.gshockGoogleSync.data.repository.GShockRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScreenStateManager @Inject constructor() : IScreenManager {

    private val _currentScreen = MutableStateFlow(AppScreen.INITIAL)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    override fun showContentSelector(repository: GShockRepository) {
        _currentScreen.value = AppScreen.CONTENT_SELECTOR
    }

    override fun showRunActionsScreen() {
        _currentScreen.value = AppScreen.RUN_ACTIONS
    }

    override fun showPreConnectionScreen() {
        _currentScreen.value = AppScreen.PRE_CONNECTION
    }

    override fun showInitialScreen() {
        _currentScreen.value = AppScreen.INITIAL
    }

    override fun showError(message: String) {
        _errorMessage.value = message
        _currentScreen.value = AppScreen.ERROR
    }

    fun goToNavigationScreen() {
        _currentScreen.value = AppScreen.MAIN_NAVIGATION
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
