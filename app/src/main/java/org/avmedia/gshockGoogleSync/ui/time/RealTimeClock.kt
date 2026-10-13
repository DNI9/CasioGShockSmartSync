package org.avmedia.gshockGoogleSync.ui.time

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import org.avmedia.gshockGoogleSync.theme.chronoLcdTypography
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RealTimeClock(modifier: Modifier = Modifier, offsetMs: Long = 0L) {
    var currentTime by remember(offsetMs) { mutableStateOf(getCurrentTime(getSystemTimeFormat(), offsetMs)) }

    LaunchedEffect(offsetMs) {
        while (true) {
            delay(1000L)
            currentTime = getCurrentTime(getSystemTimeFormat(), offsetMs)
        }
    }

    Text(
        text = currentTime,
        style = MaterialTheme.chronoLcdTypography.lcdDisplayHero,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
    )
}

enum class TimeFormat(val pattern: String) {
    TwelveHour("h:mm:ss a"),
    TwentyFourHour("H:mm:ss")
}

fun getCurrentTime(format: TimeFormat, offsetMs: Long = 0L): String {
    val sdf = SimpleDateFormat(format.pattern, Locale.getDefault())
    return sdf.format(Date(System.currentTimeMillis() + offsetMs))
}

fun getSystemTimeFormat(): TimeFormat {
    val pattern = (DateFormat.getTimeInstance(DateFormat.SHORT) as SimpleDateFormat).toPattern()
    return if (pattern.contains("a")) {
        TimeFormat.TwelveHour
    } else {
        TimeFormat.TwentyFourHour
    }
}
