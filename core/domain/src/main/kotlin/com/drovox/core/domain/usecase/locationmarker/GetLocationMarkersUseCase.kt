package com.drovox.core.domain.usecase.locationmarker

import com.drovox.core.data.repository.intf.LocationMarkerRepository
import com.drovox.core.model.entity.LocationMarkerEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetLocationMarkersUseCase @Inject constructor(
    private val locationMarkerRepository: LocationMarkerRepository,
) {
    operator fun invoke(): Flow<List<LocationMarkerEntity>> =
        locationMarkerRepository.getLocationMarkers()
}