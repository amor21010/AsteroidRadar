package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.data_base.AsteroidDB

class MainFactory (
    private val dataSource: AsteroidDB,
    private val application: Application) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(dataSource, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}