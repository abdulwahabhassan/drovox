package com.drovox.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.drovox.core.database.model.LocationMarkerLocalData
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationMarkerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocationMarker(locationMarker: LocationMarkerLocalData)

    @Query("SELECT * FROM location_marker")
    fun getLocalMarkers(): Flow<List<LocationMarkerLocalData>>

    @Query("DELETE FROM location_marker WHERE id = :locationMarkerId")
    suspend fun deleteLocationMarker(locationMarkerId: Int)
}