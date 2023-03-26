package com.example.a111111

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class G_AnalyzeAdapter(private val activity: G_Analyze, private val ItemLIst:List<G_Analyze_Card>) : RecyclerView.Adapter<G_AnalyzeAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //使用 findViewById 方法来获取布局文件中的文本展示框
        val testNameTextView: TextView = view.findViewById(R.id.TestNameTextView)
        val testScoreTextView:TextView= view.findViewById(R.id.TestScoreTextView)
    }

    //加载了卡片的布局文件，并创建了 ViewHolder 实例
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.g_analyze_card, parent, false)

        Log.e("analyze","创建了实例")

        return ViewHolder(view)
    }

    //返回数据库中记录的数量
    override fun getItemCount()= ItemLIst.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = ItemLIst[position]
        holder.testNameTextView.text = item.name
        holder.testScoreTextView.text = item.score.toString()

        val name = item.name
        //为卡片添加了点击事件处理器
        holder.itemView.setOnClickListener {

            val intent = Intent(activity, G_Rule::class.java)

            intent.putExtra("name", name)
            activity.startActivity(intent)

            Log.e("analyze","跳转666")
        }

        Log.e("analyze","绑定成功")
    }
}