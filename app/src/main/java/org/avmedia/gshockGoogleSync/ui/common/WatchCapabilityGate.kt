package org.avmedia.gshockGoogleSync.ui.common

import androidx.compose.runtime.Composable
import org.avmedia.gshockGoogleSync.domain.model.WatchCapability

enum class GatePolicy {
    Omit,       // Completely removed from composition tree (default: zero dead space)
    Disabled,   // Renders disabled content with fallback
    Fallback    // Renders an alternative composable
}

@Composable
fun WatchCapabilityGate(
    capability: WatchCapability,
    policy: GatePolicy = GatePolicy.Omit,
    fallback: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val manager = LocalWatchFeatureManager.current
    val isSupported = manager.isCapabilitySupported(capability)

    if (isSupported) {
        content()
    } else {
        when (policy) {
            GatePolicy.Omit -> Unit
            GatePolicy.Fallback -> fallback?.invoke()
            GatePolicy.Disabled -> fallback?.invoke() ?: Unit
        }
    }
}
