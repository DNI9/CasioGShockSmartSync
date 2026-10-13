package org.avmedia.gshockGoogleSync.ui.time

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

@Composable
fun TimeScreen(timeViewModel: TimeViewModel = hiltViewModel()) {

    LaunchedEffect(Unit) {
        timeViewModel.uiEvents.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    AppSnackbar(event.message)
                }
            }
        }
    }

    val watchFeatureManager = LocalWatchFeatureManager.current
    val isStepCounterSupported = watchFeatureManager.isCardSupported("step_counter_card")

    GShockSmartSyncTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                ScreenTitle(
                    text = stringResource(id = R.string.time)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    LocalTimeView(
                        modifier = Modifier.fillMaxWidth()
                    )

                    TimerView(
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (isStepCounterSupported) {
                        StepCounterView(
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    WatchNameView(
                        modifier = Modifier.fillMaxWidth()
                    )

                    WatchInfoView(
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTimeScreen() {
    TimeScreen()
}
