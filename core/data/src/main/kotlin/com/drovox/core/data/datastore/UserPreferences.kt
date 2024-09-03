package com.drovox.core.data.datastore

import com.drovox.core.model.entity.DeviceLocationEntity
import kotlinx.serialization.Serializable


@Serializable
data class UserPreferences(
    val deviceLocation: DeviceLocationEntity? = null
)
