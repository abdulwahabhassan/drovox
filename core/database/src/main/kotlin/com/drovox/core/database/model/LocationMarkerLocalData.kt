package com.drovox.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "location_marker"
)
data class LocationMarkerLocalData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val placeId: String?,
    val name: String,
    val longitude: Double,
    val latitude: Double,
    val marked: Boolean,
)