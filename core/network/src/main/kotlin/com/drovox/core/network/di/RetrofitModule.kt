package com.drovox.core.network.di

import com.drovox.core.network.service.GooglePlacesApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

private const val GOOGLE_PLACES_BASE_URL = "https://places.googleapis.com/v1/"

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Singleton
    @Provides
    fun providesGooglePlacesApiService(
        client: OkHttpClient,
        json: Json,
    ): GooglePlacesApiService {
        return Retrofit.Builder()
            .baseUrl(GOOGLE_PLACES_BASE_URL)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(GooglePlacesApiService::class.java)
    }
}
