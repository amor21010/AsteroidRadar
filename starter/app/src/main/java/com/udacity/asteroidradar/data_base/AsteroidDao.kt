package com.udacity.asteroidradar.data_base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteroidDao {
    @Query("SELECT * FROM AsteroidDTO WHERE closeApproachDate >= :startDate AND closeApproachDate <= :endDate ORDER BY closeApproachDate ASC")
    fun getAsteroidsByCloseApproachDate(startDate: String, endDate: String): Flow<List<AsteroidDTO>>

    @Query("SELECT * FROM AsteroidDTO ORDER BY closeApproachDate ASC")
    fun getAllAsteroids(): Flow<List<AsteroidDTO>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: AsteroidDTO)

    @Query("DELETE FROM AsteroidDTO WHERE closeApproachDate < :today")
    fun deletePreviousDayAsteroids(today: String): Int
}