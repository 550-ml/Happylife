package com.example.a111111.life

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a111111.databinding.RemindItemBinding

class RemindAdapter(private val remindList: ArrayList<Remind>) : RecyclerView.Adapter<RemindAdapter.RemindViewHolder>() {

    private var mOnItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mOnItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemindViewHolder {
        val binding = RemindItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RemindViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RemindViewHolder, position: Int) {
        val remind = remindList[position]
        holder.bind(remind)
        holder.itemView.setOnClickListener {
            mOnItemClickListener?.onItemClick(position)
        }
    }

    override fun getItemCount(): Int = remindList.size

    fun removeData(position: Int) {
        remindList.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class RemindViewHolder(private val binding: RemindItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(remind: Remind) {
            binding.remindTitle.text = remind.title
            binding.remindContent.text = remind.content
        }
    }
}


