package com.drovox.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.drovox.app.ui.DrovoxAppState
import com.drovox.feature.map.navigation.mapNavGraph
import com.drovox.feature.map.navigation.mapNavGraphRoute


@Composable
internal fun AppNavHost(
    appState: DrovoxAppState,
    modifier: Modifier = Modifier,
    startDestination: String = mapNavGraphRoute,
    onExitApp: () -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = appState.navHostController,
        startDestination = startDestination,
    ) {
        mapNavGraph(
            onBackIconClick = {
                if (appState.navHostController.popBackStack().not()) onExitApp()
            },
        )
    }
}