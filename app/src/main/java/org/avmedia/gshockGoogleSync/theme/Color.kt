package org.avmedia.gshockGoogleSync.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// --- Core Tonal Chrono Expressive Accents ---
val MintEmerald = Color(0xFF69DB9B)          // High-luminance functional primary
val OnMintEmerald = Color(0xFF00391F)        // WCAG AAA dark contrast text/icon (7.62:1)
val MintEmeraldContainer = Color(0xFF005E38)
val OnMintEmeraldContainer = Color(0xFF86F8B5)

val Periwinkle = Color(0xFFA7C8FF)           // Secondary accent & telemetry arcs
val OnPeriwinkle = Color(0xFF04305F)        // WCAG AAA dark contrast (9.46:1)
val PeriwinkleContainer = Color(0xFF274A79)
val OnPeriwinkleContainer = Color(0xFF99BAF0)

val WarmAmber = Color(0xFFFFB77C)            // Tertiary accent, alarms & active nav pills
val OnWarmAmber = Color(0xFF4D2600)          // WCAG AAA dark contrast (8.23:1)
val WarmAmberContainer = Color(0xFF784514)
val OnWarmAmberContainer = Color(0xFFFFDAC0)

// --- Tonal Surface Architecture (Dark Theme Foundation) ---
val ChronoSurface = Color(0xFF121416)
val ChronoSurfaceDim = Color(0xFF0F1012)
val ChronoSurfaceBright = Color(0xFF37393B)

val ChronoSurfaceContainerLowest = Color(0xFF0C0E10)
val ChronoSurfaceContainerLow = Color(0xFF1A1C1E)    // Recessed selector tracks
val ChronoSurfaceContainer = Color(0xFF202327)       // Default container card fill
val ChronoSurfaceContainerHigh = Color(0xFF282A2C)   // Secondary buttons, elevated nav bar
val ChronoSurfaceContainerHighest = Color(0xFF333537)

val ChronoOnSurface = Color(0xFFE2E2E5)
val ChronoOnSurfaceVariant = Color(0xFFBDCABE)
val ChronoOutline = Color(0xFF879489)
val ChronoOutlineVariant = Color(0xFF3E4A41)         // Subtle 1dp container boundary

val ChronoError = Color(0xFFFFB4AB)
val ChronoOnError = Color(0xFF690005)
val ChronoErrorContainer = Color(0xFF93000A)
val ChronoOnErrorContainer = Color(0xFFFFDAD6)

// --- Light Theme Colors ---
val PrimaryLight = Color(0xFF006D41)
val OnPrimaryLight = Color(0xFFFFFFFF)
val PrimaryContainerLight = Color(0xFF86F8B5)
val OnPrimaryContainerLight = Color(0xFF002110)

val SecondaryLight = Color(0xFF3B5E91)
val OnSecondaryLight = Color(0xFFFFFFFF)
val SecondaryContainerLight = Color(0xFFD5E3FF)
val OnSecondaryContainerLight = Color(0xFF001B3B)

val TertiaryLight = Color(0xFF8E4E16)
val OnTertiaryLight = Color(0xFFFFFFFF)
val TertiaryContainerLight = Color(0xFFFFDCC3)
val OnTertiaryContainerLight = Color(0xFF2F1500)

val BackgroundLight = Color(0xFFF9F9FC)
val OnBackgroundLight = Color(0xFF191C1D)
val SurfaceLight = Color(0xFFF9F9FC)
val OnSurfaceLight = Color(0xFF191C1D)
val SurfaceVariantLight = Color(0xFFDCE5DC)
val OnSurfaceVariantLight = Color(0xFF404942)
val OutlineLight = Color(0xFF717972)

val SurfaceContainerLowestLight = Color(0xFFFFFFFF)
val SurfaceContainerLowLight = Color(0xFFF3F3F6)
val SurfaceContainerLight = Color(0xFFEDEEF1)
val SurfaceContainerHighLight = Color(0xFFE8E8EB)
val SurfaceContainerHighestLight = Color(0xFFE2E2E5)

val ErrorLight = Color(0xFFBA1A1A)
val OnErrorLight = Color(0xFFFFFFFF)
val ErrorContainerLight = Color(0xFFFFDAD6)
val OnErrorContainerLight = Color(0xFF410002)

// Backward-compatibility aliases for legacy references
val PrimaryDark = MintEmerald
val OnPrimaryDark = OnMintEmerald
val PrimaryContainerDark = MintEmeraldContainer
val OnPrimaryContainerDark = OnMintEmeraldContainer

val SecondaryDark = Periwinkle
val OnSecondaryDark = OnPeriwinkle
val SecondaryContainerDark = PeriwinkleContainer
val OnSecondaryContainerDark = OnPeriwinkleContainer

val TertiaryDark = WarmAmber
val OnTertiaryDark = OnWarmAmber
val TertiaryContainerDark = WarmAmberContainer
val OnTertiaryContainerDark = OnWarmAmberContainer

val BackgroundDark = ChronoSurface
val OnBackgroundDark = ChronoOnSurface
val SurfaceDark = ChronoSurface
val OnSurfaceDark = ChronoOnSurface
val SurfaceVariantDark = ChronoSurfaceContainerHighest
val OnSurfaceVariantDark = ChronoOnSurfaceVariant
val OutlineDark = ChronoOutline

val SurfaceContainerLowestDark = ChronoSurfaceContainerLowest
val SurfaceContainerLowDark = ChronoSurfaceContainerLow
val SurfaceContainerDark = ChronoSurfaceContainer
val SurfaceContainerHighDark = ChronoSurfaceContainerHigh
val SurfaceContainerHighestDark = ChronoSurfaceContainerHighest

val ErrorDark = ChronoError
val OnErrorDark = ChronoOnError
val ErrorContainerDark = ChronoErrorContainer
val OnErrorContainerDark = ChronoOnErrorContainer

// --- Extended Horological Tokens ---
@Immutable
data class ChronoCustomColors(
    val lcdBackground: Color = Color(0xFF141A16),
    val lcdTextPrimary: Color = MintEmerald,
    val lcdTextSecondary: Color = Color(0xFF3B5E4A),
    val gaugeGradient: Brush = Brush.sweepGradient(listOf(MintEmerald, Periwinkle, MintEmerald)),
    val activeGlow: Color = Color(0x4069DB9B),
    val connectionPillActive: Color = MintEmerald,
    val connectionPillDisconnected: Color = Color(0xFFE57373),
    val cardBorder: Color = ChronoOutlineVariant.copy(alpha = 0.35f)
)

val LocalChronoColors = staticCompositionLocalOf { ChronoCustomColors() }
