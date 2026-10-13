import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.avmedia.gshockGoogleSync.theme.OnMintEmerald

@Composable
fun AppSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: SwitchColors = SwitchDefaults.colors(
        checkedThumbColor = OnMintEmerald,
        checkedTrackColor = MaterialTheme.colorScheme.primary,
        uncheckedThumbColor = MaterialTheme.colorScheme.outline,
        uncheckedTrackColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        uncheckedBorderColor = MaterialTheme.colorScheme.outlineVariant
    ),
    thumbContent: (@Composable () -> Unit)? = null,
) {
    val handleChange = { newValue: Boolean ->
        if (enabled) {
            onCheckedChange(newValue)
        }
    }

    Switch(
        checked = checked,
        onCheckedChange = handleChange,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        thumbContent = thumbContent
    )
}
