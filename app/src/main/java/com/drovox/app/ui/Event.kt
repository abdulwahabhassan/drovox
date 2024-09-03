package com.drovox.app.ui

internal sealed interface MainScreenEvent {
    data class OnShowAlertDialog(val message: String) : MainScreenEvent
    object OnDismissAlertDialog : MainScreenEvent
    object OnShowPermissionRationaleDialog : MainScreenEvent
    object OnDismissPermissionRationaleDialog : MainScreenEvent
    data class UpdateDeviceLocation(val longitude: Double, val latitude: Double) : MainScreenEvent
}