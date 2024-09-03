package com.drovox.core.data.repository.impl

import com.drovox.core.data.datastore.UserPreferencesDataStore
import com.drovox.core.data.di.IODispatcher
import com.drovox.core.data.mapper.toEntity
import com.drovox.core.data.mapper.toLocalData
import com.drovox.core.data.repository.intf.LocationMarkerRepository
import com.drovox.core.database.dao.LocationMarkerDao
import com.drovox.core.database.model.LocationMarkerLocalData
import com.drovox.core.model.entity.LocationMarkerEntity
import com.drovox.core.network.connectivity.NetworkMonitor
import com.drovox.core.network.service.DrovoxApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject

class DefaultLocationMarkerRepository @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val networkMonitor: NetworkMonitor,
    private val json: Json,
    private val drovoxApiService: DrovoxApiService,
    private val dataStore: UserPreferencesDataStore,
    private val locationMarkerDao: LocationMarkerDao,
) : LocationMarkerRepository {
    override suspend fun addLocationMarker(locationMarker: LocationMarkerEntity) {
        locationMarkerDao.insertLocationMarker(locationMarker.toLocalData())
    }

    override fun getLocationMarkers(): Flow<List<LocationMarkerEntity>> =
        locationMarkerDao.getLocalMarkers()
            .map { locationMarkers: List<LocationMarkerLocalData> ->
                locationMarkers.map { locationMarker: LocationMarkerLocalData ->
                    locationMarker.toEntity()
                }
            }

    override suspend fun removeLocationMarker(locationMarker: LocationMarkerEntity) {
        locationMarker.id?.let { locationMarkerDao.deleteLocationMarker(it) }
    }

}