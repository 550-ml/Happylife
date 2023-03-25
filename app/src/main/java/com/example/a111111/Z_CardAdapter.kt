package com.example.a111111

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.sql.Connection
import java.sql.DriverManager

class Z_CardAdapter(private val ItemLIst: List<Z_Card>, private val acting : Z_Acting) : RecyclerView.Adapter<Z_CardAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleView: TextView = itemView.findViewById(R.id.card_title)
        val contentView: TextView = itemView.findViewById(R.id.card_content)
        val yesButton: Button = itemView.findViewById(R.id.card_yes_button)
        val noButton: Button = itemView.findViewById(R.id.card_no_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.z_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount()= ItemLIst.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = ItemLIst[position]
        holder.titleView.text = card.title
        holder.contentView.text = card.content

        val olderName = acting.getCurrentUser()
        val testName = card.title
        val content = card.content

        Log.e("tag","$olderName")
        Log.e("tag", testName)
        Log.e("tag", content)


        if (card.hasButtons) {
            holder.yesButton.visibility = View.VISIBLE
            holder.noButton.visibility = View.VISIBLE
            Log.e("TAG","hasbutton0")
        } else {
            Log.e("TAG","nobutton")
            holder.yesButton.visibility = View.GONE
            holder.noButton.visibility = View.GONE
        }

        if (card.hasButtons) {

            Log.e("TAG","hasbutton")

            holder.yesButton.setOnClickListener {
            Log.e("TAG","yes")
                Thread {
                try {
                    Class.forName("com.mysql.jdbc.Driver")
                    connection = DriverManager.getConnection(jdbcUrl, username, password)
                    connection?.createStatement()?.executeUpdate("UPDATE test SET answer=1 WHERE testname='$testName' AND oldername='$olderName' AND questioncontent='$content'")
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("tag","坏了")
                    Log.e("taggg","$olderName")
                    Log.e("taggg", testName)
                    Log.e("taggg", content)
                } finally {
                    connection?.close()
                }
                }.start()
            }


            holder.noButton.setOnClickListener {
                Log.e("TAG","no")
                Thread {
                try {
                    Class.forName("com.mysql.jdbc.Driver")
                    connection = DriverManager.getConnection(jdbcUrl, username, password)
                    connection?.createStatement()?.executeUpdate("UPDATE test SET answer=0 WHERE testname='$testName' AND oldername='$olderName' AND questioncontent='$content'")

                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("tag","坏了")
                } finally {
                    connection?.close()
                }
                }.start()
            }
        }
    }
/*
    private fun updateResult(testName: String, olderName: String, result: Int, content: String) {
        try {
            Class.forName("com.mysql.jdbc.Driver")
            connection = DriverManager.getConnection(jdbcUrl, username, password)
            connection?.createStatement()?.executeUpdate("UPDATE test SET answer=$result WHERE testname='$testName' AND oldername='$olderName' AND questioncontent='$content'")

            // 保存要输出的信息
            val tag = "Z_CardAdapter"
            val message = "$testName, $olderName, $content"

            // 在主线程里通过 Log 输出
            Handler(Looper.getMainLooper()).post {
                Log.e(tag, message)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection?.close()
        }
    }

*/

    companion object {
        val jdbcUrl = "jdbc:mysql://39.101.79.219:3306/sgly2004?useSSL=false"
        val username = "sgly2004"
        val password = "sgly2004"
        var connection: Connection? = null
    }

/*     private fun getOlderName(username: String): String {
        var olderName = ""
        connection?.use { conn ->
            val stmt = conn.createStatement()
            val sql = "SELECT binding_username FROM users WHERE username='$username'"
            val rs = stmt.executeQuery(sql)
            if (rs.next()) {
                olderName = rs.getString("binding_username")
            }
            rs.close()
            stmt.close()
        }
        return olderName
    }
    */

/*    private fun isUserSubmitted(username: String, testName: String): Boolean {
        var isSubmitted = false
        connection?.use { conn ->
            val stmt = conn.createStatement()
            val sql =
                "SELECT * FROM test WHERE oldername='$username' AND testname='$testName'"
            val rs = stmt.executeQuery(sql)
            if (rs.next()) {
                isSubmitted = true
            }
            rs.close()
            stmt.close()
        }
        return isSubmitted
    }
*/


}


