package org.avmedia.gshockGoogleSync

import androidx.compose.ui.graphics.Color
import org.avmedia.gshockGoogleSync.theme.ChronoCustomColors
import org.avmedia.gshockGoogleSync.theme.ChronoSurface
import org.avmedia.gshockGoogleSync.theme.MintEmerald
import org.avmedia.gshockGoogleSync.theme.OnMintEmerald
import org.avmedia.gshockGoogleSync.theme.Periwinkle
import org.avmedia.gshockGoogleSync.theme.WarmAmber
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class ChronoThemeTokensTest {

    @Test
    fun `chrono expressive palette colors are valid and defined`() {
        assertNotNull(MintEmerald)
        assertNotNull(OnMintEmerald)
        assertNotNull(Periwinkle)
        assertNotNull(WarmAmber)
        assertNotNull(ChronoSurface)

        // Verify Mint Emerald hex
        assertEquals(Color(0xFF69DB9B), MintEmerald)
        assertEquals(Color(0xFF00391F), OnMintEmerald)
        assertEquals(Color(0xFFA7C8FF), Periwinkle)
        assertEquals(Color(0xFFFFB77C), WarmAmber)
        assertEquals(Color(0xFF121416), ChronoSurface)

        // Verify OnMintEmerald provides dark contrast vs MintEmerald
        assertNotEquals(MintEmerald, OnMintEmerald)
    }

    @Test
    fun `chrono custom colors tokens instantiate with default brush and values`() {
        val customColors = ChronoCustomColors()
        assertNotNull(customColors.gaugeGradient)
        assertNotNull(customColors.activeGlow)
        assertNotNull(customColors.lcdBackground)
        assertEquals(MintEmerald, customColors.lcdTextPrimary)
    }
}
