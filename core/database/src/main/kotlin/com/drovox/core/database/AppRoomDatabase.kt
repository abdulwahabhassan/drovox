package com.drovox.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.drovox.core.database.dao.LocationMarkerDao
import com.drovox.core.database.model.LocationMarkerLocalData

@Database(
    entities = [LocationMarkerLocalData::class],
    version = 1,
    exportSchema = false
)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun locationMarkerDao(): LocationMarkerDao

    companion object {
        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getDatabase(context: Context): AppRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppRoomDatabase::class.java,
                    "drovox_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}