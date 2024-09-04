package com.drovox.feature.map.ui

import com.drovox.core.model.entity.DeviceLocationEntity
import com.drovox.core.model.entity.LocationMarkerEntity
import com.google.android.gms.maps.model.PointOfInterest

internal interface MapScreenUiEvent {
    object LoadUiData : MapScreenUiEvent
    data class OnInputSearchQuery(val searchQuery: String) : MapScreenUiEvent
    data class OnMarkerSelected(val marker: LocationMarkerEntity) : MapScreenUiEvent
    data class OnLocationClicked(val latitude: Double, val longitude: Double) : MapScreenUiEvent
    data class OnPointOfInterestClick(val poi: PointOfInterest) : MapScreenUiEvent
    object OnDismissLocationInfoDialog : MapScreenUiEvent
    data class OnRemoveMarker(val location: LocationMarkerEntity) : MapScreenUiEvent
    data class OnAddMarker(val location: LocationMarkerEntity) : MapScreenUiEvent
    data class OnDeviceLocationSelected(val location: DeviceLocationEntity): MapScreenUiEvent
}