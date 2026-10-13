package org.avmedia.gshockGoogleSync.ui.settings

import AppSwitch
import AppText
import AppTextLarge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import org.avmedia.gshockGoogleSync.R
import org.avmedia.gshockGoogleSync.ui.common.AppCard
import org.avmedia.gshockGoogleSync.ui.common.LocalWatchFeatureManager
import org.avmedia.gshockGoogleSync.ui.common.WatchFeature
import org.avmedia.gshockGoogleSync.utils.Utils

@Composable
fun Light(
    onUpdate: (SettingsViewModel.Light) -> Unit,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val state by settingsViewModel.state.collectAsState()
    val watchFeatureManager = LocalWatchFeatureManager.current

    val lightSetting =
        state.settingsMap[SettingsViewModel.Light::class.java] as SettingsViewModel.Light

    var autoLight by remember { mutableStateOf(lightSetting.autoLight) }
    var lightDuration by remember { mutableStateOf(lightSetting.duration) }

    LaunchedEffect(state.settings) {
        autoLight = lightSetting.autoLight
        lightDuration = lightSetting.duration
    }

    AppCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 4.dp, end = 12.dp, bottom = 4.dp)
        ) {
            WatchFeature(id = "light.auto_light") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        AppTextLarge(
                            text = stringResource(id = R.string.auto_light),
                            modifier = Modifier.padding(end = 6.dp)
                        )
                    }
                    AppSwitch(
                        checked = autoLight,
                        onCheckedChange = {
                            autoLight = it
                            onUpdate(lightSetting.copy(autoLight = it))
                        }
                    )
                }
            }

            WatchFeature(id = "light.duration") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AppTextLarge(
                        text = Utils.shortenString(
                            stringResource(id = R.string.illumination_period),
                            20
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    val shortText = (watchFeatureManager.getString("light.short_duration") ?: "2s") as String
                    val longText = (watchFeatureManager.getString("light.long_duration") ?: "4s") as String
                    val durationOptions = listOf(
                        SettingsViewModel.Light.LightDuration.TWO_SECONDS to shortText,
                        SettingsViewModel.Light.LightDuration.FOUR_SECONDS to longText
                    )
                    val selectedIdx = if (lightDuration == SettingsViewModel.Light.LightDuration.TWO_SECONDS) 0 else 1

                    org.avmedia.gshockGoogleSync.ui.common.SegmentedPillControl(
                        items = durationOptions,
                        selectedIndex = selectedIdx,
                        onSelected = { idx ->
                            val selected = durationOptions[idx].first
                            lightDuration = selected
                            onUpdate(lightSetting.copy(duration = selected))
                        },
                        itemLabel = { it.second },
                        modifier = Modifier.width(160.dp)
                    )
                }
            }
        }
    }
}
