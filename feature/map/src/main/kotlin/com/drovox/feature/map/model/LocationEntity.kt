package com.drovox.feature.map.model

data class LocationEntity(
    val id: Int,
    val name: String,
    val longitude: Double,
    val latitude: Double,
    val marked: Boolean
)
