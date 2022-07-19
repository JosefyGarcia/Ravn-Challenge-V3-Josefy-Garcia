package com.example.mystarwarsapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mystarwarsapplication.R

class VehiclesAdapter : RecyclerView.Adapter<VehiclesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val lblVehicle: TextView = view.findViewById(R.id.lbVehicle)

        fun bind(vehicle: String) {
            lblVehicle.text = vehicle
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_vehicle, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val vehicle = vehicles[position]
        viewHolder.bind(vehicle)


    }

    override fun getItemCount() = vehicles.size

    var vehicles = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

}
