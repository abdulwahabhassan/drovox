package com.drovox.core.domain.usecase.locationmarker

import com.drovox.core.data.repository.intf.LocationMarkerRepository
import com.drovox.core.model.entity.LocationMarkerEntity
import javax.inject.Inject


class AddLocationMarkerUseCase @Inject constructor(
    private val locationMarkerRepository: LocationMarkerRepository,
) {
    suspend operator fun invoke(locationMarker: LocationMarkerEntity) =
        locationMarkerRepository.addLocationMarker(locationMarker)
}