package com.udacity.asteroidradar.data_base

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.Asteroid
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class AsteroidDTO(
    @PrimaryKey
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
):Parcelable{

    class AsteroidDiffUtil : DiffUtil.ItemCallback<AsteroidDTO>() {
        override fun areItemsTheSame(oldItem: AsteroidDTO, newItem: AsteroidDTO): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AsteroidDTO, newItem: AsteroidDTO): Boolean {
            return oldItem == newItem
        }
    }
}
