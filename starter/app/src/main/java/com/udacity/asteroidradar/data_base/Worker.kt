package com.udacity.asteroidradar.data_base

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.repo.AsteroidRepo
import retrofit2.HttpException

class Worker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

        override suspend fun doWork(): Result {
            val database = AsteroidDB.getDatabase(applicationContext)
            val repository = AsteroidRepo(database)

            return try {
                repository.refreshAsteroids()
                Result.success()
            } catch (exception: HttpException) {
                Result.retry()
            }
        }
}