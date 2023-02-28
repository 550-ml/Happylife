package com.example.a111111.life

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a111111.databinding.RemindItemBinding

class RemindAdapter(val RemindList :List<Remind>) : RecyclerView.Adapter<RemindAdapter.RemindViewHolder>() {


    class RemindViewHolder(binding: RemindItemBinding): RecyclerView.ViewHolder(binding.root) {
        val remindTitle : TextView = binding.remindTitle
        val remindTime : TextView = binding.remindTime
        val remindContent : TextView = binding.remindContent
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemindViewHolder {
        val binding = RemindItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RemindViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RemindViewHolder, position: Int) {
        val remind = RemindList[position]
        holder.remindTitle.text = remind.title
        holder.remindTime.text = remind.time
        holder.remindContent.text = remind.content
    }

    override fun getItemCount(): Int = RemindList.size
}