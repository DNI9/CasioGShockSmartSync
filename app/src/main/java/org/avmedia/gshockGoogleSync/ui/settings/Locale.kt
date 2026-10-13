package org.avmedia.gshockGoogleSync.ui.settings

import AppText
import AppTextLarge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import org.avmedia.gshockGoogleSync.R
import org.avmedia.gshockGoogleSync.ui.common.AppCard
import org.avmedia.gshockGoogleSync.ui.common.WatchFeature

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Locale(
    onUpdate: (SettingsViewModel.Locale) -> Unit,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val state by settingsViewModel.state.collectAsState()
    val localeSetting =
        state.settingsMap[SettingsViewModel.Locale::class.java] as SettingsViewModel.Locale

    var timeFormat by remember { mutableStateOf(localeSetting.timeFormat) }
    var dateFormat by remember { mutableStateOf(localeSetting.dateFormat) }

    LaunchedEffect(state.settings) {
        timeFormat = localeSetting.timeFormat
        dateFormat = localeSetting.dateFormat
    }

    AppCard(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, top = 2.dp, bottom = 2.dp)
        ) {
            // Time Format Section
            WatchFeature(id = "locale.time_format") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    AppTextLarge(
                        text = stringResource(
                            id = R.string.time_format
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 12.dp)
                    )

                    val timeOptions = listOf(
                        SettingsViewModel.Locale.TimeFormat.TWELVE_HOURS to stringResource(id = R.string._12h),
                        SettingsViewModel.Locale.TimeFormat.TWENTY_FOUR_HOURS to stringResource(id = R.string._24h)
                    )
                    val selectedTimeIndex = if (timeFormat == SettingsViewModel.Locale.TimeFormat.TWELVE_HOURS) 0 else 1

                    org.avmedia.gshockGoogleSync.ui.common.SegmentedPillControl(
                        items = timeOptions,
                        selectedIndex = selectedTimeIndex,
                        onSelected = { idx ->
                            val selected = timeOptions[idx].first
                            timeFormat = selected
                            onUpdate(localeSetting.copy(timeFormat = selected))
                        },
                        itemLabel = { it.second },
                        modifier = Modifier.width(160.dp)
                    )
                }
            }

            WatchFeature(id = "locale.date_format") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    AppTextLarge(
                        text = stringResource(
                            id = R.string.date_format
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 6.dp)
                    )

                    val dateOptions = listOf(
                        SettingsViewModel.Locale.DateFormat.MONTH_DAY to stringResource(id = R.string.mm_dd),
                        SettingsViewModel.Locale.DateFormat.DAY_MONTH to stringResource(id = R.string.dd_mm)
                    )
                    val selectedDateIndex = if (dateFormat == SettingsViewModel.Locale.DateFormat.MONTH_DAY) 0 else 1

                    org.avmedia.gshockGoogleSync.ui.common.SegmentedPillControl(
                        items = dateOptions,
                        selectedIndex = selectedDateIndex,
                        onSelected = { idx ->
                            val selected = dateOptions[idx].first
                            dateFormat = selected
                            onUpdate(localeSetting.copy(dateFormat = selected))
                        },
                        itemLabel = { it.second },
                        modifier = Modifier.width(160.dp)
                    )
                }
            }

            WatchFeature(id = "locale.week_language") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    AppTextLarge(
                        text = stringResource(
                            id = R.string.language
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 0.dp)
                    )

                    LanguageDropdownMenu(
                        modifier = Modifier
                            .weight(1.5f)
                            .padding(bottom = 2.dp),
                        onUpdate = onUpdate,
                        localeSetting = localeSetting,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageDropdownMenu(
    onUpdate: (SettingsViewModel.Locale) -> Unit,
    localeSetting: SettingsViewModel.Locale,
    modifier: Modifier,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val state by settingsViewModel.state.collectAsState()

    val languages = SettingsViewModel.Locale.DayOfWeekLanguage.entries.map { it }
    var selectedLanguage by remember { mutableStateOf(localeSetting.dayOfWeekLanguage) }
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(state.settings) {
        selectedLanguage = localeSetting.dayOfWeekLanguage
    }

    // ExposedDropdownMenuBox wraps around the TextField and DropdownMenu
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedLanguage.nativeName,
            onValueChange = {},
            readOnly = true,  // To prevent user from typing in the field
            label = {
                AppText(
                    text = stringResource(
                        R.string.select_language
                    )
                )
            },

            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(
                    ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                    true
                ),  // Attach menu to the OutlinedTextField
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            languages.forEach { language ->
                DropdownMenuItem(
                    text = { AppText(language.nativeName) },
                    onClick = {
                        selectedLanguage = language
                        expanded = false
                        onUpdate(localeSetting.copy(dayOfWeekLanguage = language))
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLocale() {
    Locale(onUpdate = {})
}

