package com.drovox.feature.map.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
internal fun rememberMapState(
    navHostController: NavHostController = rememberNavController(),
): MutableState<MapState> {
    return remember(
        navHostController,
    ) {
        mutableStateOf(
            MapState(navHostController = navHostController),
        )
    }
}

@Stable
internal data class MapState(
    val navHostController: NavHostController,
)
