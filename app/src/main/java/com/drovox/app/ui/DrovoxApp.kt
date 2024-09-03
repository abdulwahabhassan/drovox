package com.drovox.app.ui

import androidx.compose.runtime.Composable
import com.drovox.app.navigation.AppNavHost

@Composable
internal fun DrovoxApp(
    appState: DrovoxAppState,
    onExitApp: () -> Unit,
) {
    AppNavHost(
        appState,
        onExitApp = onExitApp,
    )
}