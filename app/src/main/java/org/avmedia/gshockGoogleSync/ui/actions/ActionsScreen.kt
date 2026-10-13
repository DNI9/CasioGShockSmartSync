package org.avmedia.gshockGoogleSync.ui.actions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.avmedia.gshockGoogleSync.R
import org.avmedia.gshockGoogleSync.theme.GShockSmartSyncTheme
import org.avmedia.gshockGoogleSync.ui.common.AppSnackbar
import org.avmedia.gshockGoogleSync.ui.common.LocalWatchFeatureManager
import org.avmedia.gshockGoogleSync.ui.common.ScreenTitle
import timber.log.Timber

@Composable
fun ActionsScreen(
    modifier: Modifier = Modifier,
    actionsViewModel: ActionsViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        actionsViewModel.uiEvents.collect { event ->
            when (event) {
                is ActionContainer.UiEvent.ShowSnackbar -> {
                    AppSnackbar(event.message)
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            Timber.i("Screen is now out of view. Saving state...")
            actionsViewModel.save()
        }
    }

    val watchFeatureManager = LocalWatchFeatureManager.current

    GShockSmartSyncTheme {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                ScreenTitle(
                    text = stringResource(id = R.string.actions),
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (watchFeatureManager.isFeatureSupported("actions.find_phone")) {
                        PhoneFinderView(
                            onUpdate = { actionsViewModel.updateAction(it) },
                            actionsViewModel = actionsViewModel
                        )
                    }

                    SetTimeView(
                        onUpdate = { actionsViewModel.updateAction(it) },
                        actionsViewModel = actionsViewModel
                    )

                    if (watchFeatureManager.isFeatureSupported("actions.reminders")) {
                        RemindersView(
                            onUpdate = { actionsViewModel.updateAction(it) },
                            actionsViewModel = actionsViewModel
                        )
                    }

                    PhotoView(
                        onUpdate = { actionsViewModel.updateAction(it) },
                        actionsViewModel = actionsViewModel
                    )

                    FlashlightView(
                        onUpdate = { actionsViewModel.updateAction(it) },
                        actionsViewModel = actionsViewModel
                    )

                    VoiceAssistView(
                        onUpdate = { actionsViewModel.updateAction(it) },
                        actionsViewModel = actionsViewModel
                    )

                    SkipToNextTrackView(
                        onUpdate = { actionsViewModel.updateAction(it) },
                        actionsViewModel = actionsViewModel
                    )

                    PlayPauseView(
                        onUpdate = { actionsViewModel.updateAction(it) },
                        actionsViewModel = actionsViewModel
                    )

                    PrayerAlarmsView(
                        onUpdate = { actionsViewModel.updateAction(it) },
                        actionsViewModel = actionsViewModel
                    )

                    SeparatorView()

                    PhoneView(
                        onUpdate = { actionsViewModel.updateAction(it) },
                        actionsViewModel = actionsViewModel
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ActionsScreenPreview() {
    ActionsScreen()
}
