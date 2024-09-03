package com.drovox.feature.map.ui

import com.drovox.core.model.entity.DeviceLocationEntity
import com.drovox.core.model.entity.LocationMarkerEntity
import com.drovox.core.ui.viewmodel.OneShotState
import kotlinx.datetime.LocalDate

internal data class MapScreenUiState(
    private val _locationMarkers: List<LocationMarkerEntity> = emptyList(),
    val showConfirmDeleteMarker: List<LocalDate> = emptyList(),
    val isLoading: Boolean = false,
    val deviceLocation: DeviceLocationEntity? = null,
    val searchQuery: String = "",
    val showLocationInfoDialog: Boolean = false,
    val selectedLocation: LocationMarkerEntity? = null,
) {

    val isUserInputEnabled: Boolean
        get() = isLoading.not()

    val locationMarkers = _locationMarkers.filter { marker: LocationMarkerEntity ->
        (marker.name.contains(searchQuery, true))
    }
}

internal sealed interface MapScreenUiOneShotState : OneShotState