package com.example.a111111.life

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a111111.databinding.RemindItemBinding

class RemindAdapter(private val RemindList :ArrayList<Remind>) : RecyclerView.Adapter<RemindAdapter.RemindViewHolder>() {


    class RemindViewHolder(binding: RemindItemBinding): RecyclerView.ViewHolder(binding.root) {
        val remindTitle : TextView = binding.remindTitle
        val remindTime : TextView = binding.remindTime
        val remindContent : TextView = binding.remindContent
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private var mOnItemClickListener : OnItemClickListener? = null
    fun setOnItemClickListener(Listener: OnItemClickListener?) {
        mOnItemClickListener = Listener
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
        holder.itemView.setOnClickListener {
            mOnItemClickListener?.onItemClick(position)
        }
    }


    fun removeData(position:Int) {
        RemindList.removeAt(position)
        //删除动画
        notifyItemRemoved(position)
        notifyDataSetChanged();//为了数据同步防止错位
    }


    override fun getItemCount(): Int = RemindList.size
}


