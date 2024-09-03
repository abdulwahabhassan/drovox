package com.drovox.core.domain.usecase.devicelocation

import com.drovox.core.data.datastore.UserPreferencesDataStore
import com.drovox.core.model.entity.DeviceLocationEntity
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetDeviceLocationUseCase @Inject constructor(
    private val datastore: UserPreferencesDataStore,
) {
    suspend operator fun invoke() =
        datastore.flow().map { it.deviceLocation }
}