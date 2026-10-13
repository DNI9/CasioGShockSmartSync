package org.avmedia.gshockGoogleSync

import org.avmedia.gshockGoogleSync.theme.MintEmerald
import org.avmedia.gshockGoogleSync.theme.OnMintEmerald
import org.avmedia.gshockGoogleSync.ui.common.AppButtonVariant
import org.avmedia.gshockGoogleSync.ui.common.CardTonalLevel
import org.avmedia.gshockGoogleSync.ui.common.GatePolicy
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ComponentTokensTest {

    @Test
    fun `card tonal levels contain expected tiers`() {
        val levels = CardTonalLevel.entries
        assertEquals(3, levels.size)
        assertTrue(levels.contains(CardTonalLevel.Container))
        assertTrue(levels.contains(CardTonalLevel.High))
        assertTrue(levels.contains(CardTonalLevel.Highest))
    }

    @Test
    fun `button variants contain expected options`() {
        val variants = AppButtonVariant.entries
        assertEquals(3, variants.size)
        assertTrue(variants.contains(AppButtonVariant.Primary))
        assertTrue(variants.contains(AppButtonVariant.Tonal))
        assertTrue(variants.contains(AppButtonVariant.Outlined))
    }

    @Test
    fun `gate policy contains expected omission policies`() {
        val policies = GatePolicy.entries
        assertEquals(3, policies.size)
        assertTrue(policies.contains(GatePolicy.Omit))
        assertTrue(policies.contains(GatePolicy.Disabled))
        assertTrue(policies.contains(GatePolicy.Fallback))
    }

    @Test
    fun `mint emerald and onMintEmerald meet WCAG AAA contrast standard`() {
        // Compute relative luminance
        fun relativeLuminance(r: Float, g: Float, b: Float): Double {
            fun adjust(c: Float): Double =
                if (c <= 0.03928f) (c / 12.92)
                else Math.pow(((c + 0.055) / 1.055), 2.4)
            return 0.2126 * adjust(r) + 0.7152 * adjust(g) + 0.0722 * adjust(b)
        }

        val lumBg = relativeLuminance(MintEmerald.red, MintEmerald.green, MintEmerald.blue)
        val lumFg = relativeLuminance(OnMintEmerald.red, OnMintEmerald.green, OnMintEmerald.blue)
        val contrastRatio = (Math.max(lumBg, lumFg) + 0.05) / (Math.min(lumBg, lumFg) + 0.05)

        // WCAG AAA requires >= 7.0:1 for normal text
        assertTrue("Contrast ratio $contrastRatio must satisfy WCAG AAA (>= 7.0)", contrastRatio >= 7.0)
    }
}
