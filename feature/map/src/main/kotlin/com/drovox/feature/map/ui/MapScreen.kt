package com.drovox.feature.map.ui

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.drovox.core.design.component.DrovoxCenterDialog
import com.drovox.core.design.component.DrovoxClickableIcon
import com.drovox.core.design.component.DrovoxFilledButton
import com.drovox.core.design.component.DrovoxOutlinedButton
import com.drovox.core.design.component.DrovoxSearchBar
import com.drovox.core.design.icon.DrovoxIcons
import com.drovox.core.design.theme.DecorativeColor
import com.drovox.core.design.theme.DrovoxTheme
import com.drovox.core.model.entity.DeviceLocationEntity
import com.drovox.core.model.entity.DisplayNameEntity
import com.drovox.core.model.entity.LocationMarkerEntity
import com.drovox.core.model.entity.PlaceDetailsEntity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.PointOfInterest
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

private const val ZOOM = 15f

@Composable
internal fun MapRoute(
    onBackIconClick: () -> Unit,
    viewModel: MapsViewModel = hiltViewModel(),
) {
    BackHandler {
        onBackIconClick()
    }

    val screenState by viewModel.uiState.collectAsStateWithLifecycle()

    MapScreen(
        uiState = screenState,
        onUiEvent = { uiEvent ->
            viewModel.sendEvent(uiEvent)
        }
    )

    LaunchedEffect(key1 = Unit, block = {
        viewModel.sendEvent(MapScreenUiEvent.LoadUiData)
    })
}

@SuppressLint("MissingPermission")
@Composable
internal fun MapScreen(
    uiState: MapScreenUiState,
    onUiEvent: (MapScreenUiEvent) -> Unit,
) {

    val coroutineScope = rememberCoroutineScope()
    val ctx = LocalContext.current
    val deviceLocationDrawable = AppCompatResources.getDrawable(ctx, DrovoxIcons.DeviceLocation)
    val markerLocationDrawable = AppCompatResources.getDrawable(ctx, DrovoxIcons.MarkerLocation)
    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                compassEnabled = false,
                zoomControlsEnabled = false
            )
        )
    }
    val properties by remember {
        mutableStateOf(
            MapProperties(
                mapType = MapType.NORMAL,
                isIndoorEnabled = true,
                isBuildingEnabled = true
            )
        )
    }
    val cameraPositionState = rememberCameraPositionState() {
        this.position =
            CameraPosition(position.target, ZOOM, position.tilt, position.bearing)
    }

    Scaffold(
        floatingActionButton = {
            DrovoxClickableIcon(
                modifier = Modifier
                    .size(60.dp),
                icon = DrovoxIcons.MyLocation,
                color = MaterialTheme.colorScheme.primary,
                onClick = {
                    uiState.deviceLocation?.let { deviceLocation: DeviceLocationEntity ->
                        coroutineScope.launch {
                            cameraPositionState.animate(
                                CameraUpdateFactory.newLatLng(
                                    LatLng(deviceLocation.latitude, deviceLocation.longitude)
                                )
                            )
                        }
                    }
                },
                backgroundColor = MaterialTheme.colorScheme.inversePrimary,
                backgroundShape = CircleShape,
                iconPaddingValues = PaddingValues(16.dp),
                isEnabled = uiState.isUserInputEnabled

            )
        },
    )
    { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {

            if (uiState.isLoading) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 4.dp,
                        strokeCap = StrokeCap.Round,
                        trackColor = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Initializing map... May take a minute, hold on!",
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.tertiary),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                GoogleMap(
                    modifier = Modifier
                        .fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    properties = properties,
                    uiSettings = uiSettings,
                    onMapClick = { latLng: LatLng ->
                        coroutineScope.launch {
                            cameraPositionState.animate(
                                CameraUpdateFactory.newLatLng(
                                    LatLng(latLng.latitude, latLng.longitude)
                                )
                            )
                        }
                        onUiEvent(
                            MapScreenUiEvent.OnLocationClicked(
                                latLng.latitude,
                                latLng.longitude
                            )
                        )
                    },
                    onPOIClick = { poi: PointOfInterest ->
                        coroutineScope.launch {
                            cameraPositionState.animate(
                                CameraUpdateFactory.newLatLng(
                                    LatLng(poi.latLng.latitude, poi.latLng.longitude)
                                )
                            )
                        }
                        onUiEvent(
                            MapScreenUiEvent.OnPointOfInterestClick(poi)
                        )
                    },
                    onMyLocationButtonClick = { false },
                    onMyLocationClick = { }
                ) {
                    uiState.deviceLocation?.let {
                        Marker(
                            state = MarkerState(
                                position = LatLng(it.latitude, it.longitude)
                            ),
                            icon = deviceLocationDrawable?.intrinsicWidth?.let {
                                deviceLocationDrawable.intrinsicHeight.let { it1 ->
                                    deviceLocationDrawable.toBitmap(it, it1)
                                        .let { BitmapDescriptorFactory.fromBitmap(it) }
                                }
                            }
                                ?: BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN),
                            onClick = { marker: Marker ->
                                coroutineScope.launch {
                                    cameraPositionState.animate(
                                        CameraUpdateFactory.newLatLng(
                                            LatLng(
                                                marker.position.latitude,
                                                marker.position.longitude
                                            )
                                        )
                                    )
                                }
                                onUiEvent(MapScreenUiEvent.OnDeviceLocationSelected(it))
                                true
                            }
                        )
                    }

                    uiState.locationMarkers
                        .forEach { locationMarker ->
                            Marker(
                                state = MarkerState(
                                    position = LatLng(
                                        locationMarker.latitude,
                                        locationMarker.longitude
                                    )
                                ),
                                icon = markerLocationDrawable?.intrinsicWidth?.let {
                                    markerLocationDrawable.intrinsicHeight.let { it1 ->
                                        markerLocationDrawable.toBitmap(it, it1)
                                            .let { BitmapDescriptorFactory.fromBitmap(it) }
                                    }
                                }
                                    ?: BitmapDescriptorFactory.defaultMarker(),
                                title = locationMarker.name,
                                onClick = { marker: Marker ->
                                    coroutineScope.launch {
                                        cameraPositionState.animate(
                                            CameraUpdateFactory.newLatLng(
                                                LatLng(
                                                    marker.position.latitude,
                                                    marker.position.longitude
                                                )
                                            )
                                        )
                                    }
                                    onUiEvent(MapScreenUiEvent.OnMarkerSelected(locationMarker))
                                    true
                                }
                            )
                        }
                }
            }

            DrovoxSearchBar(
                modifier = Modifier,
                query = uiState.searchQuery,
                onQueryChange = { newSearchQuery ->
                    onUiEvent(MapScreenUiEvent.OnInputSearchQuery(newSearchQuery))
                },
                searchPlaceholder = "Search",
                backgroundColor = MaterialTheme.colorScheme.inversePrimary,
                paddingValues = PaddingValues(24.dp),
                outlined = true,
                isEnabled = uiState.isUserInputEnabled
            )

            LaunchedEffect(
                uiState.searchQuery,
                uiState.deviceLocation
            ) {
                if (uiState.locationMarkers.isNotEmpty() && uiState.searchQuery.isNotEmpty()) {
                    cameraPositionState.animate(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                uiState.locationMarkers[0].latitude,
                                uiState.locationMarkers[0].longitude
                            ),
                            ZOOM
                        )
                    )
                } else {
                    uiState.deviceLocation?.let { deviceLocation: DeviceLocationEntity ->
                        cameraPositionState.animate(
                            CameraUpdateFactory.newLatLng(
                                LatLng(deviceLocation.latitude, deviceLocation.longitude)
                            )
                        )
                    }
                }
            }
        }
    }

    DrovoxCenterDialog(
        title = "Alert",
        showDialog = uiState.showAlertDialog,
        subtitle = uiState.alertDialogMessage,
        icon = {
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = DrovoxIcons.Warning,
                contentDescription = "Alert icon",
                tint = DecorativeColor.yellow,
            )
        },
        positiveActionText = "Okay",
    )

    DrovoxCenterDialog(
        title = "Location Info",
        showDialog = uiState.showSelectedLocationInfoDialog,
        onDismissIconClick = {
            onUiEvent(MapScreenUiEvent.OnDismissLocationInfoDialog)
        },
        icon = {
            Icon(
                imageVector = DrovoxIcons.Location,
                contentDescription = null,
                tint = DecorativeColor.red
            )
        },
        extraContent = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.selectedLocation?.name?.ifBlank { "Lat: ${uiState.selectedLocation.latitude}\nLong: ${uiState.selectedLocation.longitude}" }
                            ?: "",
                        style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.primary),
                        textAlign = TextAlign.Center
                    )
                }

                if (uiState.isLoadingSelectedLocationPlaceDetails) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 2.dp,
                            strokeCap = StrokeCap.Round,
                            trackColor = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Loading place details... ",
                            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.tertiary),
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    uiState.selectedLocationPlaceDetails?.let { placeDetails: PlaceDetailsEntity ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    shape = MaterialTheme.shapes.medium
                                )
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            placeDetails.primaryTypeDisplayName?.text?.let {
                                Text(
                                    text = placeDetails.primaryTypeDisplayName?.text.toString(),
                                    style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.tertiary),
                                    textAlign = TextAlign.Center
                                )
                            }

                            placeDetails.rating?.let { rating: Double ->
                                placeDetails.userRatingCount?.let { userRatingCount: Long ->
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            modifier = Modifier.size(16.dp),
                                            imageVector = DrovoxIcons.Star,
                                            contentDescription = null,
                                            tint = DecorativeColor.yellow
                                        )
                                        Text(
                                            text = "$rating (${userRatingCount})",
                                            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.tertiary),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }

                            placeDetails.formattedAddress?.let { address: String ->
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = address,
                                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.tertiary),
                                    textAlign = TextAlign.Center
                                )
                            }


                        }
                    }
                }
                uiState.selectedLocation?.let {
                    if (it.marked.not()) {
                        DrovoxOutlinedButton(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            text = "Add Marker",
                            onClick = {
                                onUiEvent(MapScreenUiEvent.OnAddMarker(uiState.selectedLocation))
                            }
                        )
                    } else {
                        DrovoxFilledButton(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            text = "Remove Marker",
                            onClick = {
                                onUiEvent(MapScreenUiEvent.OnRemoveMarker(uiState.selectedLocation))
                            }
                        )
                    }
                }
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
private fun MapScreenPreview() {
    DrovoxTheme {
        MapScreen(
            uiState = MapScreenUiState(
                isLoading = true,
                showSelectedLocationInfoDialog = true,
                isLoadingSelectedLocationPlaceDetails = false,
                selectedLocation = LocationMarkerEntity(
                    12,
                    "Jnsnsnwu723kmn2D",
                    "Tasty Eatery",
                    121.3992,
                    89.002,
                    false,
                ),
                selectedLocationPlaceDetails = PlaceDetailsEntity(
                    id = "ChIJs5ydyTiuEmsR0fRSlU0C7k0",
                    formattedAddress = "29 King St, Sydney NSW 2000, Australia",
                    rating = 4.00,
                    userRatingCount = 23,
                    displayName = DisplayNameEntity(text = "Tasty Restaurant", languageCode = "en"),
                    DisplayNameEntity(text = "Restaurant", languageCode = "en"),
                    shortFormattedAddress = "29 King St, Sydney",
                    reviews = emptyList(),
                    photos = emptyList()
                )
            ),
            onUiEvent = {}
        )
    }
}