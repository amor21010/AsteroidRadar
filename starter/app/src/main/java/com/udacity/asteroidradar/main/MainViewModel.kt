package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.retrofit
import com.udacity.asteroidradar.data_base.AsteroidDB
import com.udacity.asteroidradar.data_base.AsteroidDTO
import com.udacity.asteroidradar.repo.AsteroidRepo
import kotlinx.coroutines.launch


class MainViewModel(private val db: AsteroidDB, app: Application) : AndroidViewModel(app) {


    private val asteroidRepository = AsteroidRepo(db)


    private val _asteroidList = MutableLiveData<List<AsteroidDTO>>()
    val asteroidList: LiveData<List<AsteroidDTO>>
        get() = _asteroidList

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay


    init {
        viewModelScope.launch {
            asteroidRepository.refreshAsteroids()
            getAsteroidList()
           try {
               getPictureOfDay()

           } catch (e:Exception){
               return@launch
           }

        }
    }

    private suspend fun getAsteroidList() {

        db.dao.getAllAsteroids().collect {
            _asteroidList.value = it
        }
    }

    private suspend fun getPictureOfDay() {
        val pictureOfDay = retrofit.AsteroidApi.retrofitService.getPictureOfDayAsync().await()
        if (pictureOfDay.mediaType == "image") {
            _pictureOfDay.value = pictureOfDay
        }

    }

}
