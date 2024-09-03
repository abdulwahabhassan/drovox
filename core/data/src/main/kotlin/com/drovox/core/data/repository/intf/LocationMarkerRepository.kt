package com.drovox.core.data.repository.intf

import com.drovox.core.model.entity.LocationMarkerEntity
import kotlinx.coroutines.flow.Flow

interface LocationMarkerRepository {
    suspend fun addLocationMarker(locationMarker: LocationMarkerEntity)

    fun getLocationMarkers(): Flow<List<LocationMarkerEntity>>

    suspend fun removeLocationMarker(locationMarker: LocationMarkerEntity)
}