package com.example.a111111

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class G_QuestionChooseAdapter(private val ItemLIst: List<G_Card>) : RecyclerView.Adapter<G_QuestionChooseAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //使用 findViewById 方法来获取布局文件中的文本展示框
        val activityNameTextView: TextView = view.findViewById(R.id.activityNameTextView)
    }

    //加载了卡片的布局文件，并创建了 ViewHolder 实例
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.g_card, parent, false)
        return ViewHolder(view)
    }


    //返回数据库中记录的数量
    override fun getItemCount()= ItemLIst.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = ItemLIst[position]
        holder.activityNameTextView.text = item.name

        //为卡片添加了点击事件处理器
        holder.itemView.setOnClickListener {
            val snackbar = Snackbar.make(holder.itemView.findViewById(android.R.id.content), "确定要发送吗？", Snackbar.LENGTH_LONG)

            snackbar.setAction("取消") {
                snackbar.dismiss()
            }.show()

            snackbar.setAction("确定") {
                //发送逻辑


            }.show()
        }
    }

}