package com.drovox.core.domain.usecase.devicelocation

import com.drovox.core.data.datastore.UserPreferencesDataStore
import com.drovox.core.model.entity.DeviceLocationEntity
import javax.inject.Inject

class UpdateDeviceLocationUseCase @Inject constructor(
    private val datastore: UserPreferencesDataStore,
) {
    suspend operator fun invoke(longitude: Double, latitude: Double) {
        datastore.update { copy(DeviceLocationEntity(longitude = longitude, latitude = latitude)) }
    }
}