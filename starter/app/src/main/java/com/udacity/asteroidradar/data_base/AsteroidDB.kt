package com.udacity.asteroidradar.data_base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [AsteroidDTO::class],version = 2,exportSchema = false)
abstract class AsteroidDB : RoomDatabase() {
    abstract val dao: AsteroidDao

    companion object {
        @Volatile
        private lateinit var Instance: AsteroidDB
        fun getDatabase(context: Context): AsteroidDB {
            synchronized(AsteroidDB::class.java) {
                if (!::Instance.isInitialized) {
                    Instance = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDB::class.java,
                        "asteroids"
                    ).build()
                }
            }
            return Instance
        }
    }
}