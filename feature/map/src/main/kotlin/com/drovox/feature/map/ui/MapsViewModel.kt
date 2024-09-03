package com.drovox.feature.map.ui


import androidx.lifecycle.viewModelScope
import com.drovox.core.domain.usecase.devicelocation.GetDeviceLocationUseCase
import com.drovox.core.domain.usecase.locationmarker.AddLocationMarkerUseCase
import com.drovox.core.domain.usecase.locationmarker.GetLocationMarkersUseCase
import com.drovox.core.domain.usecase.locationmarker.RemoveLocationMarkerUseCase
import com.drovox.core.model.entity.DeviceLocationEntity
import com.drovox.core.model.entity.LocationMarkerEntity
import com.drovox.core.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class MapsViewModel @Inject constructor(
    private val getLocationMarkersUseCase: GetLocationMarkersUseCase,
    private val addLocationMarkerUseCase: AddLocationMarkerUseCase,
    private val removeLocationMarkerUseCase: RemoveLocationMarkerUseCase,
    private val getDeviceLocationUseCase: GetDeviceLocationUseCase,
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
                        showLocationInfoDialog = true,
                        selectedLocation = LocationMarkerEntity(
                            id = null,
                            name = "",
                            latitude = event.latitude,
                            longitude = event.longitude,
                            marked = false
                        )
                    )
                }
            }

            MapScreenUiEvent.OnDismissLocationInfo -> {
                setUiState { copy(showLocationInfoDialog = false, selectedLocation = null) }
            }

            is MapScreenUiEvent.OnPointOfInterestClick -> {
                setUiState {
                    copy(
                        showLocationInfoDialog = true,
                        selectedLocation = LocationMarkerEntity(
                            id = null,
                            event.poi.name,
                            latitude = event.poi.latLng.latitude,
                            longitude = event.poi.latLng.longitude,
                            marked = false
                        )
                    )
                }
            }

            is MapScreenUiEvent.OnMarkerSelected -> {
                setUiState {
                    copy(
                        showLocationInfoDialog = true,
                        selectedLocation = LocationMarkerEntity(
                            id = event.marker.id,
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
                        showLocationInfoDialog = true,
                        selectedLocation = LocationMarkerEntity(
                            id = null,
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
                setUiState { copy(showLocationInfoDialog = false) }
            }

            is MapScreenUiEvent.OnRemoveMarker -> {
                removeLocationMarkerUseCase(event.location)
                setUiState { copy(showLocationInfoDialog = false) }
            }

        }
    }

    private fun loadUiData() {
        getLocationMarkersUseCase().onEach { locationMarkers: List<LocationMarkerEntity> ->
            setUiState { copy(_locationMarkers = locationMarkers) }
        }.launchIn(viewModelScope)
    }

}


