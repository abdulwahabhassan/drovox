package com.drovox.core.data.repository.impl

import com.drovox.core.data.BuildConfig
import com.drovox.core.data.datastore.UserPreferencesDataStore
import com.drovox.core.data.di.IODispatcher
import com.drovox.core.data.handler.handleNetworkRequest
import com.drovox.core.data.mapper.toEntity
import com.drovox.core.data.repository.intf.GooglePlacesRepository
import com.drovox.core.database.dao.LocationMarkerDao
import com.drovox.core.model.entity.PlaceDetailsEntity
import com.drovox.core.model.sealed.Result
import com.drovox.core.network.connectivity.NetworkMonitor
import com.drovox.core.network.model.response.PlaceDetailsRemoteData
import com.drovox.core.network.service.GooglePlacesApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json
import javax.inject.Inject

class DefaultGooglePlacesRepository @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val networkMonitor: NetworkMonitor,
    private val json: Json,
    private val googlePlacesApiService: GooglePlacesApiService,
) : GooglePlacesRepository {
    override suspend fun getPlacesDetails(
        fields: List<String>,
        placeId: String,
    ): Result<PlaceDetailsEntity?> = handleNetworkRequest(
        dispatcher = ioDispatcher,
        networkMonitor = networkMonitor,
        json = json
    ) {
        googlePlacesApiService.getPlaceDetails(
            fields = fields,
            placeId = placeId,
            apiKey = BuildConfig.GOOGLE_CLOUD_API_KEY
        )?.toEntity()
    }
}