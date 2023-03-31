package com.example.a111111

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a111111.G_Analyze.Companion.connection
import com.google.android.material.snackbar.Snackbar
import java.sql.Connection
import java.sql.DriverManager
class G_QuestionChooseAdapter(
    private val itemList: List<G_Card>,
    private val testName: String,
    private val acting: Z_Acting,
    private val olderName: String
) : RecyclerView.Adapter<G_QuestionChooseAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val activityNameTextView: TextView = view.findViewById(R.id.activityNameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.g_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.activityNameTextView.text = item.name
        val questionContent = item.name

        holder.itemView.setOnClickListener {
            val snackbar = Snackbar.make(holder.itemView, "确定要发送吗？", Snackbar.LENGTH_LONG)
            snackbar.setAction("取消") {
                snackbar.dismiss()
            }.show()

            val youngManName = acting.getCurrentUser()
            snackbar.setAction("确定") {
                Thread {
                    try {
                        youngManName?.let { it1 ->
                            insertDataIntoDB(testName, questionContent,
                                it1, olderName)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }.start()
            }.show()
        }
    }

    private fun insertDataIntoDB(testName: String, questionContent: String, youngManName: String, olderName: String) {
        Class.forName("com.mysql.jdbc.Driver")
        val conn = DriverManager.getConnection(jdbcUrl, username, password)

        val sql = "INSERT INTO test (testname, questioncontent, youngmanname, oldername) " +
                "VALUES (?, ?, ?, ?)"

        conn.prepareStatement(sql).use { stmt ->
            stmt.setString(1, testName)
            stmt.setString(2, questionContent)
            stmt.setString(3, youngManName)
            stmt.setString(4, olderName)
            stmt.executeUpdate()
        }

        conn.close()
    }

    companion object {
        const val jdbcUrl = "jdbc:mysql://39.101.79.219:3306/sgly2004?useSSL=false"
        const val username = "sgly2004"
        const val password = "sgly2004"
    }
}
