package org.avmedia.gshockGoogleSync.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.avmedia.gshockGoogleSync.theme.chronoColors

enum class CardTonalLevel { Container, High, Highest }

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    padding: Dp = 0.dp,
    elevation: Dp = 0.dp,
    borderWidth: Dp = 1.dp,
    borderColor: Color = MaterialTheme.chronoColors.cardBorder,
    shape: Shape = MaterialTheme.shapes.extraLarge,
    tonalLevel: CardTonalLevel = CardTonalLevel.Container,
    containerColor: Color = when (tonalLevel) {
        CardTonalLevel.Container -> MaterialTheme.colorScheme.surfaceContainer
        CardTonalLevel.High -> MaterialTheme.colorScheme.surfaceContainerHigh
        CardTonalLevel.Highest -> MaterialTheme.colorScheme.surfaceContainerHighest
    },
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier.padding(padding),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(elevation),
        border = if (borderWidth > 0.dp) BorderStroke(borderWidth, borderColor) else null
    ) {
        content()
    }
}
