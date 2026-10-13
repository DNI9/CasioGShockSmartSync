package org.avmedia.gshockGoogleSync.ui.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.avmedia.gshockGoogleSync.theme.MintEmerald
import org.avmedia.gshockGoogleSync.theme.Periwinkle
import org.avmedia.gshockGoogleSync.theme.chronoLcdTypography

@Composable
fun MetricArcGauge(
    progress: Float,
    currentValueText: String,
    targetText: String,
    modifier: Modifier = Modifier,
    subText: String? = null,
    size: Dp = 150.dp,
    strokeWidth: Dp = 10.dp,
    accessibilityDescription: String? = null
) {
    val clampedProgress = progress.coerceIn(0f, 1f)
    val animatedProgress by animateFloatAsState(
        targetValue = clampedProgress,
        animationSpec = tween(durationMillis = 800),
        label = "MetricArcGaugeProgress"
    )

    val trackColor = MaterialTheme.colorScheme.surfaceContainerHighest
    val gradientBrush = Brush.linearGradient(
        colors = listOf(MintEmerald, Periwinkle)
    )

    val percentInt = (clampedProgress * 100).toInt()
    val fullA11yDescription = accessibilityDescription
        ?: "$currentValueText of $targetText. $percentInt percent completed."

    Box(
        modifier = modifier
            .size(size)
            .semantics(mergeDescendants = true) {
                progressBarRangeInfo = ProgressBarRangeInfo(
                    current = clampedProgress,
                    range = 0f..1f
                )
                stateDescription = "$percentInt%"
                contentDescription = fullA11yDescription
            },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val strokePx = strokeWidth.toPx()
            val diameter = this.size.minDimension - strokePx
            val topLeft = Offset(
                (this.size.width - diameter) / 2f,
                (this.size.height - diameter) / 2f
            )
            val arcSize = Size(diameter, diameter)

            // Background inactive sweep arc (220 degrees, starting at 160 deg)
            drawArc(
                color = trackColor,
                startAngle = 160f,
                sweepAngle = 220f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokePx, cap = StrokeCap.Round)
            )

            // Active progress gradient arc
            if (animatedProgress > 0f) {
                drawArc(
                    brush = gradientBrush,
                    startAngle = 160f,
                    sweepAngle = 220f * animatedProgress,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokePx, cap = StrokeCap.Round)
                )
            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = currentValueText,
                style = MaterialTheme.chronoLcdTypography.lcdTelemetryValue,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = targetText,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (subText != null) {
                Text(
                    text = subText,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
