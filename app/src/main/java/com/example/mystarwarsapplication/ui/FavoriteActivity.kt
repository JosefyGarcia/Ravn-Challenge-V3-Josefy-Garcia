package com.example.mystarwarsapplication.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mystarwarsapplication.databinding.ActivityFavoriteBinding
import com.example.mystarwarsapplication.ui.adapter.VehiclesAdapter
import com.example.mystarwarsapplication.ui.viewmodel.PeopleViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    lateinit var shared: SharedPreferences
    private val peopleViewModel: PeopleViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        shared = getSharedPreferences(PeopleViewModel.SHARE_PRE_NAME, Context.MODE_PRIVATE)
        title = shared.getString(PeopleViewModel.SH_NAME, "")
        binding.eyeColor.text = shared.getString(PeopleViewModel.SH_EYE, "")
        binding.hairColor.text = shared.getString(PeopleViewModel.SH_HAIR, "")
        binding.skinColor.text = shared.getString(PeopleViewModel.SH_SKIN, "")
        binding.birthYear.text = shared.getString(PeopleViewModel.SH_BIRTH, "")

        val vehicle = shared.getStringSet(PeopleViewModel.SH_VEHICLES, HashSet())
        val adapterVehicles = VehiclesAdapter()
        binding.recyclerviewVehicles.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewVehicles.adapter = adapterVehicles
        adapterVehicles.vehicles = vehicle!!.toList()

        if (vehicle!!.isEmpty()) {
            binding.lblHeaderVehicles.visibility = View.GONE
        } else {
            binding.lblHeaderVehicles.visibility = View.VISIBLE
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
