package com.drovox.core.model.entity

import kotlinx.serialization.Serializable

@Serializable
data class LocationMarkerEntity(
    val id: Int?,
    val placeId: String?,
    val name: String,
    val longitude: Double,
    val latitude: Double,
    val marked: Boolean
)