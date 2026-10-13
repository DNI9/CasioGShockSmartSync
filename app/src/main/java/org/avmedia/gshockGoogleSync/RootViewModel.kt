package org.avmedia.gshockGoogleSync

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import org.avmedia.gshockGoogleSync.data.repository.GShockRepository
import org.avmedia.gshockGoogleSync.pairing.DeviceAssociationManager
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    private val screenStateManager: ScreenStateManager,
    val repository: GShockRepository,
    private val deviceAssociationManager: DeviceAssociationManager
) : ViewModel() {

    val currentScreen: StateFlow<AppScreen> = screenStateManager.currentScreen
    val errorMessage: StateFlow<String?> = screenStateManager.errorMessage

    val pendingCrashLog: String?
        get() = deviceAssociationManager.pendingCrashLog

    fun dismissCrashLog() {
        deviceAssociationManager.pendingCrashLog = null
    }

    fun onUnlockCover() {
        screenStateManager.goToNavigationScreen()
    }

    fun checkPairedDevices() {
        deviceAssociationManager.checkPairedDevicesOrNotify()
    }

    fun clearError() {
        screenStateManager.clearError()
    }
}
