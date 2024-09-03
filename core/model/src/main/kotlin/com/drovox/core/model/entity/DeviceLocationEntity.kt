package com.drovox.core.model.entity

import kotlinx.serialization.Serializable

@Serializable
data class DeviceLocationEntity(
    val longitude: Double,
    val latitude: Double
)