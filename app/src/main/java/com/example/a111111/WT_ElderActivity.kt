package com.example.a111111

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a111111.gbt.Call_HelpActivity
import com.example.a111111.life.ElderLifeActivity
import java.sql.DriverManager

class WT_ElderActivity : WT_BaseActivity() {

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_elder)
        val btnLife = findViewById<Button>(R.id.btn_life)
        val btnActivity = findViewById<Button>(R.id.btn_activity)
        val btnEmergency = findViewById<Button>(R.id.btn_emergency)
        val btnPersonal = findViewById<Button>(R.id.btn_personal)

        //这里是主页卡片的部分

        val cards = mutableListOf<Z_Card>()

        val recyclerView = findViewById<RecyclerView>(R.id.card_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val acting = Z_Acting(this)
        val adapter = Z_CardAdapter(cards, acting)
        recyclerView.adapter = adapter

        Thread {
            try {
                val sharedPreferences = this.getSharedPreferences("user_info", Context.MODE_PRIVATE)
                val currentUser = sharedPreferences.getString("username", "")

                // 加载 MySQL JDBC 驱动程序
                Class.forName("com.mysql.jdbc.Driver")
                //连接到数据库并获取连接对象
                val connection = DriverManager.getConnection(jdbcUrl, username, password)
                val statement = connection.createStatement()

                // 查询数据
                    val query =
                        "SELECT id, testname, questioncontent FROM test WHERE oldername = '$currentUser'"
                    val resultSet = statement.executeQuery(query)
                    // 将查询结果转换为 Card 对象，并添加到 cards 列表中
                    while (resultSet.next()) {
                        val id = resultSet.getInt("id")
                        val title = resultSet.getString("testname")
                        val content = resultSet.getString("questioncontent")
                        val card = Z_Card(
                            id = id,
                            title = title,
                            content = content,
                            hasButtons = true)
                        cards.add(card)
                    }
/*
                    val query2 =
                        "SELECT id, title, content FROM remind WHERE older_name = '$currentUser'"
                    val resultSet2 = statement.executeQuery(query2)
                    // 将查询结果转换为 Card 对象，并添加到 cards 列表中
                    while (resultSet2.next()) {
                        val id2 = resultSet2.getInt("id")
                        val title2  = resultSet2.getString("testname")
                        val content2  = resultSet2.getString("questioncontent")
                        val card2 =Z_Card(id = id2,
                            title = title2,
                            content = content2,
                            hasButtons = false)
                        cards.add(card2)
                    }
*/
                    recyclerView.post {
                        adapter.notifyDataSetChanged()
                    }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
       //到这里结束

        btnLife.setOnClickListener {
            val intent = Intent(this, ElderLifeActivity::class.java)
            startActivity(intent)
        }

        btnActivity.setOnClickListener {
            val intent = Intent(this, L_ViewActivity::class.java)
            startActivity(intent)
        }

        btnEmergency.setOnClickListener {
            val intent = Intent(this, Call_HelpActivity::class.java)
            startActivity(intent)
        }

        btnPersonal.setOnClickListener {
            val intent = Intent(this, WT_ELSetActivity::class.java)
            startActivity(intent)
        }

    }

    //这是卡片的第二部分

    fun getConnection() = L_ChooseActivity.connection

    companion object {
        val jdbcUrl = "jdbc:mysql://39.101.79.219:3306/sgly2004?useSSL=false"
        val username = "sgly2004"
        val password = "sgly2004"
    }
    //到这里结束
}
