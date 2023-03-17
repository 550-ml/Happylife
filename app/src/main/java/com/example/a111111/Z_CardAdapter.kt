package com.example.a111111

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a111111.G_Analyze.Companion.connection

class Z_CardAdapter(private val ItemLIst: List<Z_Card>, private val acting : Z_Acting) : RecyclerView.Adapter<Z_CardAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleView: TextView = itemView.findViewById(R.id.card_title)
        val contentView: TextView = itemView.findViewById(R.id.card_content)
        val yesButton: Button = itemView.findViewById(R.id.card_yes_button)
        val noButton: Button = itemView.findViewById(R.id.card_no_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount()= ItemLIst.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = ItemLIst[position]
        holder.titleView.text = card.title
        holder.contentView.text = card.content

        if (card.showButtons) {
            holder.yesButton.visibility = View.VISIBLE
            holder.noButton.visibility = View.VISIBLE
        } else {
            holder.yesButton.visibility = View.GONE
            holder.noButton.visibility = View.GONE
        }

        if (card.hasButtons) {
            holder.yesButton.setOnClickListener {
                val currentUser = acting.getCurrentUser()
                val testName = card.title
                val olderName = currentUser?.let { it1 -> getOlderName(it1) }

                // 检查当前用户是否已经提交过这个测试
                if (!currentUser?.let { it1 -> isUserSubmitted(it1, testName) }!!) {
                    // 将结果保存到数据库
                    olderName?.let { it1 -> updateResult(testName, it1, 1) }
                }
            }

            holder.noButton.setOnClickListener {
                val currentUser = "Bob" // 替换成当前用户的用户名
                val testName = card.title
                val olderName = getOlderName(currentUser)

                // 检查当前用户是否已经提交过这个测试
                if (!isUserSubmitted(currentUser, testName)) {
                    // 将结果保存到数据库
                    updateResult(testName, olderName, 0)
                }
            }
        }
    }

    private fun getOlderName(username: String): String {
        var olderName = ""
        connection?.use { conn ->
            val stmt = conn.createStatement()
            val sql = "SELECT oldername FROM user_info WHERE username='$username'"
            val rs = stmt.executeQuery(sql)
            if (rs.next()) {
                olderName = rs.getString("oldername")
            }
            rs.close()
            stmt.close()
        }
        return olderName
    }

    private fun isUserSubmitted(username: String, testName: String): Boolean {
        var isSubmitted = false
        connection?.use { conn ->
            val stmt = conn.createStatement()
            val sql =
                "SELECT * FROM question_choose WHERE oldername='$username' AND test_name='$testName'"
            val rs = stmt.executeQuery(sql)
            if (rs.next()) {
                isSubmitted = true
            }
            rs.close()
            stmt.close()
        }
        return isSubmitted
    }

    private fun updateResult(testName: String, olderName: String, result: Int) {
        connection?.use { conn ->
            val stmt = conn.createStatement()
            val sql =
                "UPDATE question_choose SET choose_result=$result WHERE test_name='$testName' AND oldername='$olderName' AND choose_result IS NULL LIMIT 1"
            stmt.executeUpdate(sql)
            stmt.close()
        }
    }
}

