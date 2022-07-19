package com.example.mystarwarsapplication.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val peopleViewModel: PeopleViewModel by viewModels()
    private lateinit var personAdapter: PersonAdapter
    lateinit var shared: SharedPreferences

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

        shared = getSharedPreferences(PeopleViewModel.SHARE_PRE_NAME, Context.MODE_PRIVATE)

        binding.floatingActionButton.setOnClickListener {
            val favoriteSaved = shared.getString(PeopleViewModel.SH_NAME, "")
            if (favoriteSaved!!.isEmpty()) {
                Snackbar.make(binding.root, "You haven't saved your favorite character yet", Snackbar.LENGTH_LONG).show()
            } else {
                intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
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

            binding.save.setOnClickListener {
                val edit = shared.edit()
                edit.clear()
                edit.putString(PeopleViewModel.SH_NAME, personDetails.name)
                edit.putString(PeopleViewModel.SH_BIRTH, personDetails.birthYear)
                edit.putString(PeopleViewModel.SH_EYE, personDetails.eyeColor)
                edit.putString(PeopleViewModel.SH_SKIN, personDetails.skinColor)
                edit.putString(PeopleViewModel.SH_HAIR, personDetails.hairColor)
                val vehicles: MutableSet<String> = HashSet(personDetails.vehicles)
                edit.putStringSet(PeopleViewModel.SH_VEHICLES, vehicles)
                edit.apply()

                Snackbar.make(binding.root, "Favorite character successfully updated!!", Snackbar.LENGTH_LONG).show()
            }

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