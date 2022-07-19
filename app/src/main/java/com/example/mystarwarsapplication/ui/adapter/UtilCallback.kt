package com.example.mystarwarsapplication.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.mystarwarsapplication.data.model.PersonData


class UtilCallback : DiffUtil.ItemCallback<PersonData>() {
    override fun areItemsTheSame(oldItem: PersonData, newItem: PersonData): Boolean {
        return oldItem.name == newItem.name

    }

    override fun areContentsTheSame(oldItem: PersonData, newItem: PersonData): Boolean {
        return oldItem.name == newItem.name
    }

}