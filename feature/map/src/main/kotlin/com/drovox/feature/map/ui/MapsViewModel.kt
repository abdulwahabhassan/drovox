package com.drovox.feature.map.ui

import androidx.lifecycle.viewModelScope
import com.drovox.core.domain.usecase.devicelocation.GetDeviceLocationUseCase
import com.drovox.core.domain.usecase.locationmarker.AddLocationMarkerUseCase
import com.drovox.core.domain.usecase.locationmarker.GetLocationMarkersUseCase
import com.drovox.core.domain.usecase.locationmarker.RemoveLocationMarkerUseCase
import com.drovox.core.domain.usecase.places.GetPlaceDetailsUseCase
import com.drovox.core.model.entity.DeviceLocationEntity
import com.drovox.core.model.entity.LocationMarkerEntity
import com.drovox.core.model.sealed.Result
import com.drovox.core.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MapsViewModel @Inject constructor(
    private val getLocationMarkersUseCase: GetLocationMarkersUseCase,
    private val addLocationMarkerUseCase: AddLocationMarkerUseCase,
    private val removeLocationMarkerUseCase: RemoveLocationMarkerUseCase,
    private val getDeviceLocationUseCase: GetDeviceLocationUseCase,
    private val getPlaceDetailsUseCase: GetPlaceDetailsUseCase,
) : BaseViewModel<MapScreenUiEvent, MapScreenUiState, MapScreenUiOneShotState>(
    MapScreenUiState()
) {

    init {
        viewModelScope.launch {
            getDeviceLocationUseCase().collectLatest { deviceLocation: DeviceLocationEntity? ->
                setUiState {
                    copy(
                        deviceLocation = deviceLocation,
                        isLoading = deviceLocation == null
                    )
                }
            }
        }
    }

    override suspend fun handleUiEvents(event: MapScreenUiEvent) {
        when (event) {
            MapScreenUiEvent.LoadUiData -> {
                loadUiData()
            }

            is MapScreenUiEvent.OnInputSearchQuery -> {
                setUiState { copy(searchQuery = event.searchQuery) }
            }

            is MapScreenUiEvent.OnLocationClicked -> {
                setUiState {
                    copy(
                        showSelectedLocationInfoDialog = true,
                        selectedLocation = LocationMarkerEntity(
                            id = null,
                            placeId = null,
                            name = "",
                            latitude = event.latitude,
                            longitude = event.longitude,
                            marked = false
                        )
                    )
                }
            }

            MapScreenUiEvent.OnDismissLocationInfo -> {
                setUiState { copy(showSelectedLocationInfoDialog = false, selectedLocation = null) }
            }

            is MapScreenUiEvent.OnPointOfInterestClick -> {
                setUiState {
                    copy(
                        showSelectedLocationInfoDialog = true,
                        selectedLocation = LocationMarkerEntity(
                            id = null,
                            placeId = event.poi.placeId,
                            event.poi.name,
                            latitude = event.poi.latLng.latitude,
                            longitude = event.poi.latLng.longitude,
                            marked = false,

                            )
                    )
                }
                loadPlaceDetails(event.poi.placeId)
            }

            is MapScreenUiEvent.OnMarkerSelected -> {
                setUiState {
                    copy(
                        showSelectedLocationInfoDialog = true,
                        selectedLocation = LocationMarkerEntity(
                            id = event.marker.id,
                            placeId = null,
                            name = event.marker.name,
                            latitude = event.marker.latitude,
                            longitude = event.marker.longitude,
                            marked = true
                        )
                    )
                }
            }

            is MapScreenUiEvent.OnDeviceLocationSelected -> {
                setUiState {
                    copy(
                        showSelectedLocationInfoDialog = true,
                        selectedLocation = LocationMarkerEntity(
                            id = null,
                            placeId = null,
                            name = "",
                            latitude = event.location.latitude,
                            longitude = event.location.longitude,
                            marked = true
                        )
                    )
                }
            }

            is MapScreenUiEvent.OnAddMarker -> {
                addLocationMarkerUseCase(event.location)
                setUiState { copy(showSelectedLocationInfoDialog = false) }
            }

            is MapScreenUiEvent.OnRemoveMarker -> {
                removeLocationMarkerUseCase(event.location)
                setUiState { copy(showSelectedLocationInfoDialog = false) }
            }

        }
    }

    private suspend fun loadPlaceDetails(placeId: String) {
        setUiState { copy(isLoadingSelectedLocationPlaceDetails = true) }
        when (val result = getPlaceDetailsUseCase(
            fields = listOf(
                "id",
                "displayName",
                "formattedAddress",
                "rating",
                "userRatingCount",
                "primaryTypeDisplayName",
                "shortFormattedAddress",
                "reviews",
                "photos"
            ),
            placeId = placeId
        )) {
            is Result.Error -> {
                setUiState { copy(showAlertDialog = true, alertDialogMessage = result.message) }
            }

            is Result.Success -> {
                setUiState { copy(selectedLocationPlaceDetails = result.data) }
            }
        }
        setUiState { copy(isLoadingSelectedLocationPlaceDetails = false) }
    }

    private fun loadUiData() {
        getLocationMarkersUseCase().onEach { locationMarkers: List<LocationMarkerEntity> ->
            setUiState { copy(_locationMarkers = locationMarkers) }
        }.launchIn(viewModelScope)
    }

}


