package org.avmedia.gshockGoogleSync

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.CALL_PHONE
import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_CALENDAR
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.EntryPointAccessors
import org.avmedia.gshockGoogleSync.data.repository.GShockRepository
import org.avmedia.gshockGoogleSync.di.ApplicationContextEntryPoint
import org.avmedia.gshockGoogleSync.services.InactivityHandler
import org.avmedia.gshockGoogleSync.ui.actions.ActionsScreen
import org.avmedia.gshockGoogleSync.ui.alarms.AlarmsScreen
import org.avmedia.gshockGoogleSync.ui.common.AppSnackbar
import org.avmedia.gshockGoogleSync.ui.events.EventsScreen
import org.avmedia.gshockGoogleSync.ui.settings.SettingsScreen
import org.avmedia.gshockGoogleSync.ui.time.TimeScreen
import org.avmedia.gshockapi.ProgressEvents
import org.avmedia.gshockapi.EventAction
import org.avmedia.gshockGoogleSync.utils.subscribeToProgressEvents
import org.avmedia.gshockGoogleSync.voice.VoiceNavigation
import kotlin.time.Duration.Companion.seconds

@Composable
fun BottomNavigationBarWithPermissions(
    repository: GShockRepository,
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val localContext = LocalContext.current.applicationContext
    val appContext = remember {
        EntryPointAccessors.fromApplication(
            localContext,
            ApplicationContextEntryPoint::class.java
        ).getApplicationContext()
    }

    val inactivityHandler = remember {
        InactivityHandler(timeout = (3 * 60).seconds) {
            repository.disconnect()
            AppSnackbar(appContext.getString(R.string.disconnected_due_to_inactivity))
        }
    }

    // ProgressEvents silently drops a second subscription registered under a
    // name it has already seen - which "BottomNavigationBar-Voice" is, the
    // moment this composable is ever recreated (e.g. a config change). A
    // unique name per instance guarantees this instance's subscription
    // actually registers. See subscribeToProgressEvents() for the full
    // explanation - this is the reason voice-triggered navigation (e.g. to
    // Settings) can silently stop working partway through a session.
    val voiceNavSubscriptionName = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        voiceNavSubscriptionName.value = subscribeToProgressEvents("BottomNavigationBar-Voice", arrayOf(
            EventAction("NavigateTo") {
                val nav = ProgressEvents.getPayload("NavigateTo") as? VoiceNavigation ?: return@EventAction
                navController.navigate(nav.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        ))
    }

    DisposableEffect(Unit) {
        inactivityHandler.startMonitoring()
        onDispose {
            inactivityHandler.stopMonitoring()
            voiceNavSubscriptionName.value?.let { ProgressEvents.subscriber.stop(it) }
        }
    }

    fun Modifier.detectInactivity(handler: InactivityHandler): Modifier {
        return this.pointerInput(Unit) {
            while (true) {
                awaitPointerEventScope {
                    awaitPointerEvent()
                    handler.registerInteraction()
                }
            }
        }
    }

    val navigateToHome: () -> Unit = {
        navController.navigate(Screens.Time.route) {
            popUpTo(Screens.Time.route) { inclusive = true }
        }
    }

    val navItems = BottomNavigationItem().bottomNavigationItems()

    val navSuiteItemColors = androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults.itemColors(
        navigationBarItemColors = androidx.compose.material3.NavigationBarItemDefaults.colors(
            selectedIconColor = org.avmedia.gshockGoogleSync.theme.OnWarmAmber,
            selectedTextColor = org.avmedia.gshockGoogleSync.theme.WarmAmber,
            indicatorColor = org.avmedia.gshockGoogleSync.theme.WarmAmber,
            unselectedIconColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant,
            unselectedTextColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
        )
    )

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            navItems.forEach { navigationItem ->
                item(
                    selected = currentDestination?.hierarchy?.any { it.route == navigationItem.route } == true,
                    onClick = {
                        navController.navigate(navigationItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            navigationItem.icon,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(navigationItem.label)
                    },
                    alwaysShowLabel = false,
                    colors = navSuiteItemColors
                )
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .detectInactivity(inactivityHandler)
    ) {
        NavHost(
            navController = navController,
            startDestination = Screens.Time.route,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(Screens.Time.route) {
                TimeScreen()
            }
            composable(Screens.Alarms.route) {
                AlarmsScreen()
            }
            composable(Screens.Events.route) {
                NavigateWithPermissions(
                    requiredPermissions = listOf(READ_CALENDAR),
                    onPermissionDenied = navigateToHome,
                    destinationScreen = { EventsScreen() },
                    errorMessage = stringResource(
                        R.string.calendar_permission_denied_cannot_access_events,
                    )
                )
            }
            composable(Screens.Actions.route) {
                val permissions = mutableListOf(CAMERA, CALL_PHONE).also {
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
                        it += WRITE_EXTERNAL_STORAGE
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        it += ACCESS_COARSE_LOCATION
                    }
                }

                NavigateWithPermissions(
                    requiredPermissions = permissions,
                    onPermissionDenied = navigateToHome,
                    destinationScreen = { ActionsScreen() },
                    errorMessage = stringResource(
                        R.string.required_permissions_denied_cannot_access_actions
                    ),
                )
            }
            composable(Screens.Settings.route) {
                SettingsScreen()
            }
        }
    }
}

@Composable
fun NavigateWithPermissions(
    requiredPermissions: List<String>,
    onPermissionDenied: () -> Unit,
    destinationScreen: @Composable () -> Unit,
    errorMessage: String = stringResource(R.string.required_permissions_denied_cannot_access_screen),
) {
    var showSnackbar by remember { mutableStateOf(false) }

    // Resolve the string safely here in the Composable scope
    val additionalInfo = stringResource(R.string.clear_app_storage_from_android_setting_and_restart_the_app_to_add_permissions)
    val fullErrorMessage = remember(errorMessage, additionalInfo) { errorMessage + additionalInfo }

    if (requiredPermissions.isEmpty()) return

    // Render the Snackbar in the UI tree, not in the effect
    if (showSnackbar) {
        AppSnackbar(fullErrorMessage)
    }

    PermissionRequiredScreen(
        requiredPermissions = requiredPermissions,
        onPermissionGranted = { destinationScreen() },
        onPermissionDenied = {
            showSnackbar = true
            onPermissionDenied()
        }
    )
}

@Composable
fun PermissionRequiredScreen(
    requiredPermissions: List<String>,
    onPermissionGranted: @Composable () -> Unit,
    onPermissionDenied: () -> Unit
) {
    var permissionGranted by remember { mutableStateOf(false) }
    var permissionChecked by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            permissionGranted = permissions.values.all { it }
            permissionChecked = true
        }
    )

    LaunchedEffect(Unit) {
        launcher.launch(requiredPermissions.toTypedArray())
    }

    LaunchedEffect(permissionChecked, permissionGranted) {
        if (permissionChecked && !permissionGranted) {
            onPermissionDenied()
        }
    }

    if (permissionChecked && permissionGranted) {
        onPermissionGranted()
    }
}
