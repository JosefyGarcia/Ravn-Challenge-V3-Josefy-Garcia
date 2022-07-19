package com.example.mystarwarsapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.mystarwarsapplication.R
import com.example.mystarwarsapplication.data.model.PersonData

class PersonAdapter: PagingDataAdapter<PersonData, PersonViewHolder>(UtilCallback()) {

    private lateinit var itemListenerItem : OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(id:String)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        itemListenerItem = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PersonViewHolder(layoutInflater.inflate(R.layout.item_people, parent, false), itemListenerItem)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.render(item)
    }

}