package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.data_base.AsteroidDB
import com.udacity.asteroidradar.databinding.FragmentMainBinding


class MainFragment : Fragment(), MenuProvider {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val application = requireNotNull(this.activity).application
        val dataSource = AsteroidDB.getDatabase(application)

        val viewModelFactory = MainFactory(dataSource, application)
        viewModel = ViewModelProvider(
            this, viewModelFactory
        )[MainViewModel::class.java]
        binding.viewModel = viewModel

        requireActivity().addMenuProvider(this, viewLifecycleOwner)
        val adapter = AsteroidRecycleAdapter {
            findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
        }
        binding.asteroidRecycler.adapter = adapter

        viewModel.asteroidList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        return binding.root
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_overflow_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.show_week->{viewModel.getWeek()}
            R.id.show_today->{viewModel.getToday()}
            R.id.show_saved->{viewModel.getAll()}

        }
        return true
    }
}
