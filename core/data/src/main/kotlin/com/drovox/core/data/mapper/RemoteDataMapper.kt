package com.drovox.core.data.mapper

import com.drovox.core.model.entity.AuthorAttributionEntity
import com.drovox.core.model.entity.DisplayNameEntity
import com.drovox.core.model.entity.PhotoEntity
import com.drovox.core.model.entity.PlaceDetailsEntity
import com.drovox.core.model.entity.ReviewEntity
import com.drovox.core.network.model.response.AuthorAttributionRemoteData
import com.drovox.core.network.model.response.DisplayNameRemoteData
import com.drovox.core.network.model.response.PhotoRemoteData
import com.drovox.core.network.model.response.PlaceDetailsRemoteData
import com.drovox.core.network.model.response.ReviewRemoteData

fun DisplayNameRemoteData.toEntity(): DisplayNameEntity = DisplayNameEntity(
    text = text,
    languageCode = languageCode
)

fun AuthorAttributionRemoteData.toEntity(): AuthorAttributionEntity = AuthorAttributionEntity(
    displayName = displayName,
    uri = uri,
    photoURI = photoURI
)

fun PhotoRemoteData.toEntity(): PhotoEntity = PhotoEntity(
    name = name,
    widthPx = widthPx,
    heightPx = heightPx,
    authorAttributions = authorAttributions?.map { it.toEntity() }
)

fun ReviewRemoteData.toEntity(): ReviewEntity = ReviewEntity(
    name = name,
    relativePublishTimeDescription = relativePublishTimeDescription,
    rating = rating,
    text = text?.toEntity(),
    originalText = originalText?.toEntity(),
    authorAttribution = authorAttribution?.toEntity(),
    publishTime = publishTime
)

fun PlaceDetailsRemoteData.toEntity(): PlaceDetailsEntity = PlaceDetailsEntity(
    id = id,
    formattedAddress = formattedAddress,
    rating = rating,
    userRatingCount = userRatingCount,
    displayName = displayName?.toEntity(),
    primaryTypeDisplayName = primaryTypeDisplayName?.toEntity(),
    shortFormattedAddress = shortFormattedAddress,
    reviews = reviews?.map { it.toEntity() },
    photos = photos?.map { it.toEntity() }
)