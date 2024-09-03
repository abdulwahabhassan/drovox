package com.drovox.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.drovox.app.navigation.AppTopLevelDestination
import com.drovox.app.navigation.navigateToMapNavGraph
import kotlinx.coroutines.CoroutineScope

@Composable
internal fun rememberDrovoxAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navHostController: NavHostController = rememberNavController(),
) = DrovoxAppState(
    navHostController,
    coroutineScope,
)

@Stable
internal data class DrovoxAppState(
    val navHostController: NavHostController,
    private val coroutineScope: CoroutineScope,
) {

    fun navigateTo(destination: AppTopLevelDestination) {
        val navOptions = navOptions {
            launchSingleTop = true
        }
        when (destination) {
            AppTopLevelDestination.MAP -> {
                navHostController.navigateToMapNavGraph(navOptions = navOptions)
            }
        }
    }
}