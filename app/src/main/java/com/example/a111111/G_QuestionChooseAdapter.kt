package com.example.a111111

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a111111.G_Analyze.Companion.connection
import com.google.android.material.snackbar.Snackbar

class G_QuestionChooseAdapter(private val ItemLIst: List<G_Card>,val test_name: String, private val acting : Z_Acting) : RecyclerView.Adapter<G_QuestionChooseAdapter.ViewHolder>() {

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

        val question_content = item.name
        val testname = test_name


        holder.itemView.setOnClickListener {
            val snackbar = Snackbar.make(holder.itemView.findViewById(android.R.id.content), "确定要发送吗？", Snackbar.LENGTH_LONG)

            snackbar.setAction("取消") {
                snackbar.dismiss()
            }.show()

            val youngmanname = acting.getCurrentUser()
            val oldername = youngmanname?.let { it1 -> getOlderName(it1) }

            snackbar.setAction("确定") {
                connection?.use { conn ->
                    val stmt = conn.createStatement()
                    val sql =
                        "UPDATE test SET testname=$testname AND questioncontent=$question_content WHERE youngmanname='$youngmanname' AND oldername='$oldername'"
                    stmt.executeUpdate(sql)
                    stmt.close()
                }

            }.show()
        }

        Log.e("questionChooseAdapter","question的适配器启动了")
    }

    private fun getOlderName(username: String): String {
        var olderName = ""
        connection?.use { conn ->
            val sql = "SELECT binding_username FROM users WHERE username = ?"
            val stmt = conn.prepareStatement(sql)
            stmt.setString(1, username)
            val rs = stmt.executeQuery()
            if (rs.next()) {
                olderName = rs.getString("binding_username")
            }
            rs.close()
            stmt.close()
        }
        return olderName
    }


}