package com.drovox.feature.map.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.drovox.feature.map.ui.MapRoute

const val mapNavGraphRoute = "map_graph"
internal const val mapNavRoute =
    mapNavGraphRoute.plus("/map_route")
internal const val mapRoute = mapNavRoute.plus("/map_screen")


internal fun NavGraphBuilder.mapRoute(
    onBackIconClick: () -> Unit
) {
    composable(route = mapRoute) {
        MapRoute(
            onBackIconClick = onBackIconClick
        )
    }
}
