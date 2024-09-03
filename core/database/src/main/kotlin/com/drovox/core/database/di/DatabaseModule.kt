package com.drovox.core.database.di

import android.content.Context
import com.drovox.core.database.AppRoomDatabase
import com.drovox.core.database.dao.LocationMarkerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(
        @ApplicationContext appContext: Context,
    ): AppRoomDatabase = AppRoomDatabase.getDatabase(appContext)

    @Provides
    @Singleton
    fun providesLocationMarkerDao(appDatabase: AppRoomDatabase): LocationMarkerDao =
        appDatabase.locationMarkerDao()
}
