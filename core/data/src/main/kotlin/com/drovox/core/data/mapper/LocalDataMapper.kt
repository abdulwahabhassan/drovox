package com.drovox.core.data.mapper

import com.drovox.core.database.model.LocationMarkerLocalData
import com.drovox.core.model.entity.LocationMarkerEntity

fun LocationMarkerLocalData.toEntity(): LocationMarkerEntity = LocationMarkerEntity(
    id = id,
    name = name,
    longitude = longitude,
    latitude = latitude,
    marked = marked
)