package com.drovox.core.data.repository.intf

import com.drovox.core.model.entity.PlaceDetailsEntity
import com.drovox.core.model.sealed.Result

interface GooglePlacesRepository {
    suspend fun getPlacesDetails(fields: List<String>, placeId: String): Result<PlaceDetailsEntity?>
}