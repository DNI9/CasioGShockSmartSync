package org.avmedia.gshockGoogleSync

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.avmedia.gshockGoogleSync.ui.common.AppSnackbar
import org.avmedia.gshockGoogleSync.ui.common.CrashLogDialog
import org.avmedia.gshockGoogleSync.ui.others.CoverScreen
import org.avmedia.gshockGoogleSync.ui.others.PreConnectionScreen
import org.avmedia.gshockGoogleSync.ui.others.RunActionsScreen
import org.avmedia.gshockGoogleSync.ui.others.RunFindPhoneScreen

@Composable
fun RootScreen(
    viewModel: RootViewModel,
    contentPadding: PaddingValues
) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()

    viewModel.pendingCrashLog?.let { log ->
        CrashLogDialog(
            crashLog = log,
            onDismiss = { viewModel.dismissCrashLog() }
        )
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let { message ->
            AppSnackbar(message)
            viewModel.clearError()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        when (currentScreen) {
            AppScreen.INITIAL -> {
                PreConnectionScreen()
                LaunchedEffect(Unit) {
                    viewModel.checkPairedDevices()
                }
            }

            AppScreen.PRE_CONNECTION -> {
                PreConnectionScreen()
            }

            AppScreen.CONTENT_SELECTOR -> {
                RootContentSelector(
                    viewModel = viewModel,
                    onUnlocked = { viewModel.onUnlockCover() }
                )
            }

            AppScreen.MAIN_NAVIGATION -> {
                BottomNavigationBarWithPermissions(repository = viewModel.repository)
            }

            AppScreen.RUN_ACTIONS -> {
                RunActionsScreen()
            }

            AppScreen.ERROR -> {
                PreConnectionScreen()
            }
        }
    }
}

@Composable
private fun RootContentSelector(
    viewModel: RootViewModel,
    onUnlocked: () -> Unit
) {
    val repository = viewModel.repository
    when {
        repository.isAlwaysConnectedConnectionPressed() -> {
            CoverScreen(
                onUnlock = onUnlocked,
                isConnected = repository.isConnected()
            )
        }

        repository.isActionButtonPressed() || repository.isAutoTimeStarted() -> {
            RunActionsScreen()
        }

        repository.isFindPhoneButtonPressed() -> {
            RunFindPhoneScreen()
        }

        else -> {
            BottomNavigationBarWithPermissions(repository = repository)
        }
    }
}
