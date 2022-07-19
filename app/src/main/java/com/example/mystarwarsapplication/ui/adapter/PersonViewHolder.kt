package com.example.mystarwarsapplication.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.mystarwarsapplication.data.model.PersonData
import com.example.mystarwarsapplication.databinding.ItemPeopleBinding


class PersonViewHolder(view: View, listener: PersonAdapter.OnItemClickListener) : RecyclerView.ViewHolder(view) {
    private val binding = ItemPeopleBinding.bind(view)
    private val listener = listener

    fun render(personModel: PersonData) {
        binding.lblTitleItem.text = personModel.name
        binding.lblSubtitleItem.text = "${personModel.species} from ${personModel.planet}"

        itemView.setOnClickListener {
            personModel.id?.let { item -> listener.onItemClick(item) }
        }
    }
}