package com.example.a111111

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.sql.Connection
import java.sql.DriverManager

class G_Analyze : AppCompatActivity() {
    private var datas = mutableListOf<G_Analyze_Card>()
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.g_analyze)


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val nameList = mutableListOf<String>()

        val adapter = G_AnalyzeAdapter(this, datas)

        recyclerView.adapter = adapter


        Thread {
            try {
                // 加载 MySQL JDBC 驱动程序
                Class.forName("com.mysql.jdbc.Driver")
                //连接到数据库并获取连接对象
                connection = DriverManager.getConnection(jdbcUrl, username, password)
                //SQL查询,从 activity 表中获取所有行
                val resultSet =
                    connection?.createStatement()?.executeQuery("SELECT testname, answer FROM test")

                // 声明一个 map 来存储不同的 testname 对应的总分数和数量
                val scoreMap = mutableMapOf<String, Pair<Int, Int>>()

                while (resultSet?.next() == true) {
                    val testname = resultSet.getString("testname") ?: ""
                    val answer = resultSet.getInt("answer")

                    Log.e("testname", testname)
                    Log.e("answer","answer")

                    // 判断是否已经存在这个 testname
                    if (scoreMap.containsKey(testname)) {
                        // 已经存在，则将该 testname 对应的总分数和数量加上当前的 answer
                        val (totalScore, count) = scoreMap[testname] ?: Pair(0, 0)
                        scoreMap[testname] = Pair(totalScore + answer, count + 1)
                    } else {
                        // 不存在，则添加一个新的 testname
                        scoreMap[testname] = Pair(answer, 1)
                    }
                }

                // 遍历 map，计算每个 testname 对应的平均分，并添加到 datas 和 nameList 中
                var i = 0
                scoreMap.forEach { (testname, scorePair) ->
                    val totalScore = scorePair.first
                    val count = scorePair.second
                    val avgScore = if (count == 0) 0 else totalScore / count
                    val item = G_Analyze_Card(testname, i, avgScore)
                    datas.add(item)
                    nameList.add(testname)
                    i++

                    Log.e("ave","$avgScore")
                }
                recyclerView.post {
                    adapter.notifyDataSetChanged()
                    Log.e("analyze","analyze适配器被调用了")
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    companion object {
        val jdbcUrl = "jdbc:mysql://39.101.79.219:3306/sgly2004?useSSL=false"
        val username = "sgly2004"
        val password = "sgly2004"
        var connection: Connection? = null
    }

}