package com.drovox.core.data.di


import com.drovox.core.data.repository.impl.DefaultGooglePlacesRepository
import com.drovox.core.data.repository.impl.DefaultLocationMarkerRepository
import com.drovox.core.data.repository.intf.GooglePlacesRepository
import com.drovox.core.data.repository.intf.LocationMarkerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindsLocationMarkerRepository(
        repository: DefaultLocationMarkerRepository,
    ): LocationMarkerRepository

    @Binds
    fun bindsGooglePlacesRepository(
        repository: DefaultGooglePlacesRepository,
    ): GooglePlacesRepository
}
