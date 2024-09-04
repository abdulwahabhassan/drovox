package com.drovox.core.network.service

import com.drovox.core.network.model.response.PlaceDetailsRemoteData
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GooglePlacesApiService {

    @GET("places/{place_id}")
    suspend fun getPlaceDetails(
        @Header("X-Goog-FieldMask") fields: List<String>,
        @Path("place_id") placeId: String,
        @Query("key") apiKey: String,
    ): PlaceDetailsRemoteData?
}