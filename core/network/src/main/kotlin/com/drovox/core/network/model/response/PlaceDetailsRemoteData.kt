package com.drovox.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaceDetailsRemoteData(
    val id: String? = null,
    val formattedAddress: String? = null,
    val rating: Double? = null,
    val userRatingCount: Long? = null,
    val displayName: DisplayNameRemoteData? = null,
    val primaryTypeDisplayName: DisplayNameRemoteData? = null,
    val shortFormattedAddress: String? = null,
    val reviews: List<ReviewRemoteData>? = null,
    val photos: List<PhotoRemoteData>? = null,
)

@Serializable
data class DisplayNameRemoteData(
    val text: String? = null,
    val languageCode: String? = null,
)

@Serializable
data class PhotoRemoteData(
    val name: String? = null,
    val widthPx: Long? = null,
    val heightPx: Long? = null,
    val authorAttributions: List<AuthorAttributionRemoteData>? = null,
)

@Serializable
data class AuthorAttributionRemoteData(
    val displayName: String? = null,
    val uri: String? = null,

    @SerialName("photoUri")
    val photoURI: String? = null,
)

@Serializable
data class ReviewRemoteData(
    val name: String? = null,
    val relativePublishTimeDescription: String? = null,
    val rating: Long? = null,
    val text: DisplayNameRemoteData? = null,
    val originalText: DisplayNameRemoteData? = null,
    val authorAttribution: AuthorAttributionRemoteData? = null,
    val publishTime: String? = null,
)