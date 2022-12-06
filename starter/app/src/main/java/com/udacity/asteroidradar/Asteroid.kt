package com.udacity.asteroidradar

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.squareup.moshi.Json
import com.udacity.asteroidradar.data_base.AsteroidDTO
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Asteroid(

    val id: Long,
    val codeName: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
) : Parcelable {




}

fun ArrayList<Asteroid>.asDomainModel(): Array<AsteroidDTO> {
    return map {
        AsteroidDTO(
            id = it.id,
            codename = it.codeName,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
        .toTypedArray()
}