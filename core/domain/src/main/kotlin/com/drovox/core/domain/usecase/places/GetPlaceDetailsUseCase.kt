package com.drovox.core.domain.usecase.places

import com.drovox.core.data.repository.intf.GooglePlacesRepository
import com.drovox.core.model.entity.PlaceDetailsEntity
import com.drovox.core.model.sealed.Result
import javax.inject.Inject

class GetPlaceDetailsUseCase @Inject constructor(
    private val googlePlacesRepository: GooglePlacesRepository,
) {
    suspend operator fun invoke(fields: List<String>, placeId: String): Result<PlaceDetailsEntity?> =
        googlePlacesRepository.getPlacesDetails(fields, placeId)
}