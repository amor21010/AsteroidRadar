package com.udacity.asteroidradar

import android.app.Application
import android.os.Build
import androidx.work.*
import com.udacity.asteroidradar.data_base.Worker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AsteroidApp : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }

    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()

        val repeatingRefreshRequest = PeriodicWorkRequestBuilder<Worker>(
            // once a day
            1,
            TimeUnit.DAYS
        ).setConstraints(constraints)
            .build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            "REFRESH_ASTEROID",
            ExistingPeriodicWorkPolicy.KEEP, // keep - will disregard the new request
            repeatingRefreshRequest
        )


    }
}