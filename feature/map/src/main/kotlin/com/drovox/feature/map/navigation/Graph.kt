package com.drovox.feature.map.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

fun NavGraphBuilder.mapNavGraph(
    onBackIconClick: () -> Unit,
) {
    navigation(
        route = mapNavGraphRoute,
        startDestination = mapNavRoute,
    ) {
        composable(mapNavRoute) {
            val administrationState by rememberMapState()
            MapNavHost(
                onBackIconClick = onBackIconClick,
                navHostController = administrationState.navHostController,
            )
        }
    }
}

@Composable
internal fun MapNavHost(
    navHostController: NavHostController,
    onBackIconClick: () -> Unit
) {
    NavHost(
        modifier = Modifier,
        navController = navHostController,
        startDestination = mapRoute,
    ) {
        mapRoute(
            onBackIconClick = onBackIconClick
        )
    }
}