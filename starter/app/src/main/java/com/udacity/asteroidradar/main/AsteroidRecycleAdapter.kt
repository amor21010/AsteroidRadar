package com.udacity.asteroidradar.main;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.data_base.AsteroidDTO
import com.udacity.asteroidradar.databinding.AsteroidItemBinding


class AsteroidRecycleAdapter(

    val itemClick: (AsteroidDTO) -> Unit
) :
    ListAdapter<AsteroidDTO, AsteroidRecycleAdapter.AsteroidViewHolder>(AsteroidDTO.AsteroidDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder.from(this, parent)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindView(item)
    }


    class AsteroidViewHolder private constructor(
        private val view: AsteroidItemBinding,
        val itemClick: (AsteroidDTO) -> Unit
    ) :
        RecyclerView.ViewHolder(view.root) {

        fun bindView(item: AsteroidDTO) {
            with(item) {
                itemView.setOnClickListener { itemClick(this) }
                view.asteroid = item
            }
        }

        companion object {
            fun from(
                asteroidRecycleAdapter: AsteroidRecycleAdapter,
                parent: ViewGroup
            ): AsteroidViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view =
                    AsteroidItemBinding
                        .inflate(layoutInflater, parent, false)
                return AsteroidViewHolder(view, asteroidRecycleAdapter.itemClick)
            }
        }
    }


}
