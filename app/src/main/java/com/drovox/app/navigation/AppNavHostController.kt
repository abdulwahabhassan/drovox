package com.drovox.app.navigation

import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.drovox.feature.map.navigation.mapNavGraphRoute


internal fun NavHostController.navigateToMapNavGraph(
    navOptions: NavOptions? = null,
) {
    this.navigate(mapNavGraphRoute, navOptions)
}