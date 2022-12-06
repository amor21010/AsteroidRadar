package com.udacity.asteroidradar.repo

import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.getSeventhDay
import com.udacity.asteroidradar.api.getToday
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.api.retrofit
import com.udacity.asteroidradar.asDomainModel
import com.udacity.asteroidradar.data_base.AsteroidDB
import com.udacity.asteroidradar.data_base.AsteroidDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject

class AsteroidRepo(private val database: AsteroidDB) {
    lateinit var list:ArrayList<AsteroidDTO>
    suspend fun refreshAsteroids(
        startDate: String = getToday(),
        endDate: String = getSeventhDay()
    ) {
        var networkList: ArrayList<Asteroid>
        withContext(Dispatchers.IO) {
            val body: ResponseBody =
                retrofit.AsteroidApi.retrofitService.getAllAsteroidsByTimeAsync(startDate, endDate)
                    .await()

            networkList = parseAsteroidsJsonResult(JSONObject(body.string()))

            database.dao.insertAll(*networkList.asDomainModel())
        }
    }

    suspend fun deletePreviousDayAsteroids() {
        withContext(Dispatchers.IO) {
            database.dao.deletePreviousDayAsteroids(getToday())
        }
    }
}