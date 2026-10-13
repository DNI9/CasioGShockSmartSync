package org.avmedia.gshockGoogleSync.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Tonal Chrono Expressive Color Schemes
private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    primaryContainer = PrimaryContainerLight,
    onPrimaryContainer = OnPrimaryContainerLight,
    secondary = SecondaryLight,
    onSecondary = OnSecondaryLight,
    secondaryContainer = SecondaryContainerLight,
    onSecondaryContainer = OnSecondaryContainerLight,
    tertiary = TertiaryLight,
    onTertiary = OnTertiaryLight,
    tertiaryContainer = TertiaryContainerLight,
    onTertiaryContainer = OnTertiaryContainerLight,
    error = ErrorLight,
    onError = OnErrorLight,
    errorContainer = ErrorContainerLight,
    onErrorContainer = OnErrorContainerLight,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    outline = OutlineLight,
    surfaceContainerLowest = SurfaceContainerLowestLight,
    surfaceContainerLow = SurfaceContainerLowLight,
    surfaceContainer = SurfaceContainerLight,
    surfaceContainerHigh = SurfaceContainerHighLight,
    surfaceContainerHighest = SurfaceContainerHighestLight,
)

private val DarkColorScheme = darkColorScheme(
    primary = MintEmerald,
    onPrimary = OnMintEmerald,
    primaryContainer = MintEmeraldContainer,
    onPrimaryContainer = OnMintEmeraldContainer,
    secondary = Periwinkle,
    onSecondary = OnPeriwinkle,
    secondaryContainer = PeriwinkleContainer,
    onSecondaryContainer = OnPeriwinkleContainer,
    tertiary = WarmAmber,
    onTertiary = OnWarmAmber,
    tertiaryContainer = WarmAmberContainer,
    onTertiaryContainer = OnWarmAmberContainer,
    error = ChronoError,
    onError = ChronoOnError,
    errorContainer = ChronoErrorContainer,
    onErrorContainer = ChronoOnErrorContainer,
    background = ChronoSurface,
    onBackground = ChronoOnSurface,
    surface = ChronoSurface,
    onSurface = ChronoOnSurface,
    surfaceVariant = ChronoSurfaceContainerHighest,
    onSurfaceVariant = ChronoOnSurfaceVariant,
    outline = ChronoOutline,
    outlineVariant = ChronoOutlineVariant,
    surfaceContainerLowest = ChronoSurfaceContainerLowest,
    surfaceContainerLow = ChronoSurfaceContainerLow,
    surfaceContainer = ChronoSurfaceContainer,
    surfaceContainerHigh = ChronoSurfaceContainerHigh,
    surfaceContainerHighest = ChronoSurfaceContainerHighest,
)

@Composable
fun getCurrentColorScheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true
): ColorScheme {
    val context = LocalContext.current
    return when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
}

@Composable
fun GShockSmartSyncTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = getCurrentColorScheme(darkTheme = darkTheme, dynamicColor = dynamicColor)
    val chronoColors = ChronoCustomColors()
    val chronoLcdTypography = ChronoLcdTypography()

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val activity = view.context as? Activity
            val window = activity?.window
            if (window != null) {
                val insetsController = WindowCompat.getInsetsController(window, view)
                insetsController.isAppearanceLightStatusBars = !darkTheme
            }
        }
    }

    CompositionLocalProvider(
        LocalChronoColors provides chronoColors,
        LocalChronoLcdTypography provides chronoLcdTypography
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            shapes = Shapes,
            typography = Typography,
            content = content
        )
    }
}

val MaterialTheme.chronoColors: ChronoCustomColors
    @Composable
    @ReadOnlyComposable
    get() = LocalChronoColors.current

val MaterialTheme.chronoLcdTypography: ChronoLcdTypography
    @Composable
    @ReadOnlyComposable
    get() = LocalChronoLcdTypography.current
