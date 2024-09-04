package com.drovox.core.data.mapper

import com.drovox.core.database.model.LocationMarkerLocalData
import com.drovox.core.model.entity.LocationMarkerEntity

fun LocationMarkerEntity.toLocalData(): LocationMarkerLocalData = LocationMarkerLocalData(
    id = id ?: 0,
    placeId = placeId,
    name = name,
    longitude = longitude,
    latitude = latitude,
    marked = true
)