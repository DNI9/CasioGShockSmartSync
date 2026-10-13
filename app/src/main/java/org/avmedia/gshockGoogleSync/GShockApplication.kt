package org.avmedia.gshockGoogleSync

import android.annotation.SuppressLint
import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.avmedia.gshockGoogleSync.data.repository.GShockRepository
import org.avmedia.gshockGoogleSync.pairing.CompanionDevicePresenceMonitor
import org.avmedia.gshockGoogleSync.pairing.DeviceAssociationManager
import org.avmedia.gshockGoogleSync.services.DeviceManager
import org.avmedia.gshockGoogleSync.utils.ActivityProvider
import org.avmedia.gshockGoogleSync.utils.CrashReportHelper
import org.avmedia.gshockGoogleSync.ui.actions.ActionRunner
import timber.log.Timber
import javax.inject.Inject

enum class AppScreen {
    INITIAL,
    PRE_CONNECTION,
    CONTENT_SELECTOR,
    MAIN_NAVIGATION,
    RUN_ACTIONS,
    ERROR
}

@HiltAndroidApp
class GShockApplication : Application() {

    private lateinit var eventHandler: MainEventHandler

    @Inject
    lateinit var deviceManager: DeviceManager

    @Inject
    lateinit var repository: GShockRepository

    @Inject
    lateinit var companionDevicePresenceMonitor: CompanionDevicePresenceMonitor

    @Inject
    lateinit var deviceAssociationManager: DeviceAssociationManager

    @Inject
    lateinit var actionRunner: ActionRunner

    @Inject
    lateinit var screenStateManager: ScreenStateManager

    fun init() {
        Timber.i("Initializing GShockApplication")
        CoroutineScope(Dispatchers.IO).launch {
            deviceAssociationManager.init()
        }
    }

    @SuppressLint("NewApi")
    override fun onCreate() {
        super.onCreate()

        // Plant first: Timber calls made before a tree is planted are silently discarded,
        // which previously swallowed all logging from the rest of onCreate().
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.d("Timber planted for Debug") // If you see this, Timber is working
        } else {
            // Optional: Plant a tree that only logs Errors to a crash reporter like Firebase
            // Timber.plant(ReleaseTree())
        }

        CrashReportHelper.installGlobalHandler(this)

        ActivityProvider.initialize(this)
        eventHandler =
            MainEventHandler(context = this, repository = repository, screenManager = screenStateManager)
        eventHandler.setupEventSubscription()

        // Registered here, not from a composable, so watch button presses still run when the
        // process was woken in the background by GShockCompanionDeviceService and no UI exists.
        actionRunner.setupActionSubscriptions()
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}
