package org.avmedia.gshockGoogleSync.ui.time

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.avmedia.gshockGoogleSync.ui.common.LocalWatchFeatureManager

@Composable
fun Battery(
    modifier: Modifier = Modifier,
    timeModel: TimeViewModel = hiltViewModel()
) {
    val watchFeatureManager = LocalWatchFeatureManager.current
    if (watchFeatureManager.isFeatureSupported("time.battery") == false) {
        return
    }

    val state by timeModel.state.collectAsStateWithLifecycle()
    val percent = state.batteryLevel.coerceIn(0, 100)

    val outlineColor = MaterialTheme.colorScheme.onSurfaceVariant
    val fillColor = when {
        percent <= 15 -> MaterialTheme.colorScheme.error
        percent <= 30 -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.primary
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Canvas(modifier = Modifier.size(width = 24.dp, height = 13.dp)) {
            val strokeWidth = 1.5.dp.toPx()
            val capWidth = 2.dp.toPx()
            val capHeight = 5.dp.toPx()
            val bodyWidth = size.width - capWidth - 1.dp.toPx()
            val bodyHeight = size.height

            // Outer battery body
            drawRoundRect(
                color = outlineColor,
                topLeft = Offset(0f, 0f),
                size = Size(bodyWidth, bodyHeight),
                cornerRadius = CornerRadius(3.dp.toPx(), 3.dp.toPx()),
                style = Stroke(width = strokeWidth)
            )

            // Positive terminal cap on the right
            drawRoundRect(
                color = outlineColor,
                topLeft = Offset(bodyWidth + 1.dp.toPx(), (bodyHeight - capHeight) / 2f),
                size = Size(capWidth, capHeight),
                cornerRadius = CornerRadius(1.dp.toPx(), 1.dp.toPx())
            )

            // Inner fill based on percentage
            if (percent > 0) {
                val padding = strokeWidth + 1.dp.toPx()
                val maxFillWidth = (bodyWidth - (padding * 2)).coerceAtLeast(0f)
                val fillWidth = maxFillWidth * (percent / 100f)
                val fillHeight = (bodyHeight - (padding * 2)).coerceAtLeast(0f)

                drawRoundRect(
                    color = fillColor,
                    topLeft = Offset(padding, padding),
                    size = Size(fillWidth, fillHeight),
                    cornerRadius = CornerRadius(1.5.dp.toPx(), 1.5.dp.toPx())
                )
            }
        }

        Text(
            text = "$percent%",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
