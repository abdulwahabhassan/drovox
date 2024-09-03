package com.drovox.app.ui

import com.drovox.core.ui.viewmodel.OneShotState

internal data class MainScreenState(
    val showAlertDialog: Boolean = false,
    val alertMessage: String = "",
    val showPermissionRationale: Boolean = false,
    val permissionRationale: String = ""
)
internal sealed interface MainScreenOneShotState : OneShotState