package org.avmedia.gshockGoogleSync.ui.alarms

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.avmedia.gshockGoogleSync.R
import org.avmedia.gshockGoogleSync.theme.GShockSmartSyncTheme
import org.avmedia.gshockGoogleSync.ui.common.AppSnackbar
import org.avmedia.gshockGoogleSync.ui.common.ButtonData
import org.avmedia.gshockGoogleSync.ui.common.ButtonsRow
import org.avmedia.gshockGoogleSync.ui.common.ScreenTitle

@Composable
fun AlarmList(alarmViewModel: AlarmViewModel = hiltViewModel()) {
    val alarms by alarmViewModel.alarms.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        alarms.forEachIndexed { index, alarm ->
            key(index) {
                AlarmItem(
                    hours = alarm.hour,
                    minutes = alarm.minute,
                    isAlarmEnabled = alarm.enabled,
                    name = alarm.name,
                    onToggleAlarm = { isEnabled ->
                        alarmViewModel.toggleAlarm(index, isEnabled)
                    },
                    onTimeChanged = { hours, minutes ->
                        alarmViewModel.onTimeChanged(index, hours, minutes)
                    }
                )
            }
        }
    }
}

@Composable
fun AlarmsScreen(alarmViewModel: AlarmViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        alarmViewModel.uiEvents.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    AppSnackbar(event.message)
                }
            }
        }
    }

    GShockSmartSyncTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                ScreenTitle(
                    text = stringResource(id = R.string.watch_alarms)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    AlarmList(alarmViewModel = alarmViewModel)
                    AlarmChimeSwitch(
                        onUpdate = { isChecked -> alarmViewModel.toggleHourlyChime(isChecked) }
                    )
                }

                val buttons =
                    listOf(
                        ButtonData(
                            text = stringResource(id = R.string.send_alarms_to_phone),
                            onClick = { alarmViewModel.sendAlarmsToPhone() }
                        ),
                        ButtonData(
                            text = stringResource(id = R.string.send_alarms_to_watch),
                            onClick = { alarmViewModel.sendAlarmsToWatch() }
                        )
                    )

                ButtonsRow(
                    buttons = buttons,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}
