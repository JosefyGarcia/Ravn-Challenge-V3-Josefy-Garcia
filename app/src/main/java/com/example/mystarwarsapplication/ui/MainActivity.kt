package com.example.mystarwarsapplication.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mystarwarsapplication.databinding.ActivityMainBinding
import com.example.mystarwarsapplication.ui.adapter.PersonAdapter
import com.example.mystarwarsapplication.ui.adapter.VehiclesAdapter
import com.example.mystarwarsapplication.ui.viewmodel.PeopleViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val peopleViewModel: PeopleViewModel by viewModels()
    private lateinit var personAdapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        personAdapter = PersonAdapter()
        personAdapter.setOnItemClickListener(object : PersonAdapter.OnItemClickListener {
            override fun onItemClick(id: String) {
                peopleViewModel.loadPersonDetails(id)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        })

        peopleViewModel.initViewModel()
        initRecyclerView()

        lifecycleScope.launch {
            peopleViewModel.pager.collect {
                personAdapter.submitData(it)
            }
        }
        lifecycleScope.launch {
            personAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.progressBar.isVisible = loadStates.refresh is LoadState.Loading
                binding.lblError.isVisible = loadStates.refresh is LoadState.Error
            }
        }
    }

    private fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, manager.orientation)

        binding.recyclerview.layoutManager = manager

        peopleViewModel.personDetails.observe(this) { personDetails ->
            title = personDetails.name
            binding.eyeColor.text = personDetails.eyeColor
            binding.hairColor.text = personDetails.hairColor
            binding.skinColor.text = personDetails.skinColor
            binding.birthYear.text = personDetails.birthYear

            val adapterVehicles = VehiclesAdapter()
            binding.recyclerviewVehicles.layoutManager = LinearLayoutManager(this)
            binding.recyclerviewVehicles.adapter = adapterVehicles
            adapterVehicles.vehicles = personDetails.vehicles!!

            if (personDetails.vehicles!!.isEmpty()) {
                binding.lblHeaderVehicles.visibility = View.GONE
            } else {
                binding.lblHeaderVehicles.visibility = View.VISIBLE
            }

            peopleViewModel.isDetailsViewActive.postValue(true)

        }
        peopleViewModel.isDetailsViewActive.observe(this) {
            if (it) {
                binding.constraintLayoutDetail.visibility = View.VISIBLE
                binding.recyclerview.visibility = View.GONE
            } else {
                binding.constraintLayoutDetail.visibility = View.GONE
                binding.recyclerview.visibility = View.VISIBLE
                title = "People of Star Wars"
            }
        }

        binding.recyclerview.adapter = personAdapter
        binding.recyclerview.addItemDecoration(decoration)

    }

    override fun onBackPressed() {
        peopleViewModel.isDetailsViewActive.postValue(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        peopleViewModel.isDetailsViewActive.postValue(false)
        return true
    }
}