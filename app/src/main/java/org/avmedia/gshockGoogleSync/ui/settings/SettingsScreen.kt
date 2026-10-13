package org.avmedia.gshockGoogleSync.ui.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.avmedia.gshockGoogleSync.R
import org.avmedia.gshockGoogleSync.theme.GShockSmartSyncTheme
import org.avmedia.gshockGoogleSync.ui.common.AppSnackbar
import org.avmedia.gshockGoogleSync.ui.common.ButtonData
import org.avmedia.gshockGoogleSync.ui.common.ButtonsRow
import org.avmedia.gshockGoogleSync.ui.common.InfoButton
import org.avmedia.gshockGoogleSync.ui.common.ScreenTitle
import org.avmedia.gshockGoogleSync.ui.common.WatchFeature
import org.avmedia.gshockGoogleSync.utils.Utils

@SuppressLint("MutableCollectionMutableState")
@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    GShockSmartSyncTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                ScreenTitle(
                    stringResource(id = R.string.settings),
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SettingsList(settingsViewModel = settingsViewModel)
                }

                BottomRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    settingsViewModel = settingsViewModel
                )
            }
        }
    }
}

@Composable
fun SettingsList(
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        settingsViewModel.uiEvents.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    AppSnackbar(event.message)
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        WatchFeature(id = "locale_card") {
            Locale(settingsViewModel::onSettingUpdated)
        }
        WatchFeature(id = "operation_tone_card") {
            OperationalTone(settingsViewModel::onSettingUpdated)
        }
        WatchFeature(id = "light_card") {
            Light(settingsViewModel::onSettingUpdated)
        }
        WatchFeature(id = "power_saving_card") {
            PowerSavings(settingsViewModel::onSettingUpdated)
        }
        WatchFeature(id = "font_card") {
            Font(settingsViewModel::onSettingUpdated)
        }
        WatchFeature(id = "time_adjustment_card") {
            TimeAdjustment(settingsViewModel::onSettingUpdated)
        }
    }
}

@Composable
fun BottomRow(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                InfoButton(infoText = stringResource(id = R.string.auto_fill_help))
            }

            val buttons =
                arrayListOf(
                    ButtonData(
                        text =
                        Utils.shortenStringNewLine(
                            stringResource(
                                id = R.string.auto_configure_settings
                            ),
                            15,
                            "AUTO"
                        ),
                        onClick = { settingsViewModel.setSmartDefaults() }
                    ),
                    ButtonData(
                        text = stringResource(id = R.string.send_to_watch),
                        onClick = { settingsViewModel.sendToWatch() }
                    )
                )

            ButtonsRow(buttons = buttons, modifier = Modifier.weight(2.5f))

            Spacer(modifier = Modifier.weight(.5f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsScreen() {
    SettingsScreen()
}
