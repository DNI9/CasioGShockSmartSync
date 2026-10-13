package org.avmedia.gshockGoogleSync.domain.model

sealed interface WatchCapability {
    data object StepCounter : WatchCapability
    data object Reminders : WatchCapability
    data object WorldCities : WatchCapability
    data object SolarBattery : WatchCapability
    data object Temperature : WatchCapability
    data object AutoLight : WatchCapability
    data object PowerSaving : WatchCapability
    data object AlwaysConnectedBle : WatchCapability
    data object MultipleFonts : WatchCapability
    data object OperationSound : WatchCapability
    data object OperationVibration : WatchCapability
    data object HourlyChime : WatchCapability
    data object FindPhone : WatchCapability
}
