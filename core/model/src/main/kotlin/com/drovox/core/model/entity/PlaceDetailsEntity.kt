package com.drovox.core.model.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaceDetailsEntity(
    val id: String?,
    val formattedAddress: String?,
    val rating: Double?,
    val userRatingCount: Long?,
    val displayName: DisplayNameEntity?,
    val primaryTypeDisplayName: DisplayNameEntity?,
    val shortFormattedAddress: String?,
    val reviews: List<ReviewEntity>?,
    val photos: List<PhotoEntity>?,
)

@Serializable
data class DisplayNameEntity(
    val text: String?,
    val languageCode: String?,
)

@Serializable
data class PhotoEntity(
    val name: String?,
    val widthPx: Long?,
    val heightPx: Long?,
    val authorAttributions: List<AuthorAttributionEntity>?,
)

@Serializable
data class AuthorAttributionEntity(
    val displayName: String?,
    val uri: String?,

    @SerialName("photoUri")
    val photoURI: String?,
)

@Serializable
data class ReviewEntity(
    val name: String?,
    val relativePublishTimeDescription: String?,
    val rating: Long?,
    val text: DisplayNameEntity?,
    val originalText: DisplayNameEntity?,
    val authorAttribution: AuthorAttributionEntity?,
    val publishTime: String?,
)