package com.example.a111111

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.sql.Connection
import java.sql.DriverManager

class WT_ElderActivity : WT_BaseActivity() {

    //这里是主页卡片的代码
    private val nameList = mutableListOf<String>()
    private val cards = mutableListOf<Z_Card>()
    private lateinit var adapter: Z_CardAdapter
    //到这里结束

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_elder)
        val btnLife = findViewById<Button>(R.id.btn_life)
        val btnActivity = findViewById<Button>(R.id.btn_activity)
        val btnEmergency = findViewById<Button>(R.id.btn_emergency)
        val btnPersonal = findViewById<Button>(R.id.btn_personal)

        //这里是主页卡片的另一部分


        val recyclerView = findViewById<RecyclerView>(R.id.card_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val acting = Z_Acting()
        adapter = Z_CardAdapter(cards, acting)
        recyclerView.adapter = adapter

        Thread {
            try {
                // 加载 MySQL JDBC 驱动程序
                Class.forName("com.mysql.jdbc.Driver")
                //连接到数据库并获取连接对象
                val connection = DriverManager.getConnection(jdbcUrl, username, password)
                // 查询数据
                val query =
                    "SELECT id, title, content, test_name FROM question_choose WHERE status=1"
                val statement = connection.createStatement()
                val resultSet = statement.executeQuery(query)

                // 将查询结果转换为 Card 对象，并添加到 cards 列表中
                // 将查询结果转换为 Card 对象，并添加到 cards 列表中
                while (resultSet.next()) {
                    val id = resultSet.getInt("id")
                    val title = resultSet.getString("title")
                    val content = resultSet.getString("content")
                    val card = Z_Card.create(
                        id = id,
                        title = title,
                        content = content,
                        hasButtons = true,
                        onYesClickListener = {
                            val query =
                                "SELECT oldername, testname FROM test WHERE oldername=? AND testname=?"
                            val preparedStatement = connection.prepareStatement(query)
                            preparedStatement.setString(1, "当前用户姓名")
                            val result = preparedStatement.executeQuery()
                            if (result.next()) {
                                val id = result.getInt("id")
                                updateAnswer(connection, id, 1)
                                val card = cards.last()
                                card.setShowButtons(false)
                                adapter.notifyDataSetChanged()
                            }
                        },
                        onNoClickListener = {
                            val query =
                                "SELECT oldername, testname, id FROM test WHERE oldername=? AND testname=?"
                            val preparedStatement = connection.prepareStatement(query)
                            preparedStatement.setString(1, "当前用户姓名")
                            val result = preparedStatement.executeQuery()
                            if (result.next()) {
                                val id = result.getInt("id")
                                updateAnswer(connection, id, 0)
                                val card = cards.last()
                                card.setShowButtons(false)
                                adapter.notifyDataSetChanged()
                            }
                        }
                    )
                    cards.add(card)
                    nameList.add(title)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()


       //到这里结束

        btnLife.setOnClickListener {
            //跳转到生活界面
        }

        btnActivity.setOnClickListener {
            val intent = Intent(this, L_ViewActivity::class.java)
            startActivity(intent)
        }

        btnEmergency.setOnClickListener {
            //跳转到急救界面
        }

        btnPersonal.setOnClickListener {
            val intent = Intent(this, WT_ELSetActivity::class.java)
            startActivity(intent)
        }

    }


    //这是卡片的第三部分
    companion object {
        val jdbcUrl = "jdbc:mysql://39.101.79.219:3306/sgly2004?useSSL=false"
        val username = "sgly2004"
        val password = "sgly2004"

        private fun updateAnswer(connection: Connection, id: Int, answer: Int) {
            val updateQuery = "UPDATE question_choose SET answer4=? WHERE id=?"
            val updateStatement = connection.prepareStatement(updateQuery)
            updateStatement.setInt(1, answer)
            updateStatement.setInt(2, id)
            updateStatement.executeUpdate()
        }
    }
    //到这里结束
}
