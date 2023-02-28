package com.example.a111111

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.sql.Connection
import java.sql.DriverManager

class G_TestChoose : AppCompatActivity() {
    private var datas = mutableListOf<G_Card>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.g_test_choose)


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val nameList = mutableListOf<String>()



        val adapter = G_TestChooseAdapter(this, datas, nameList)

        recyclerView.adapter = adapter


        Thread {
            try {
                // 加载 MySQL JDBC 驱动程序
                Class.forName("com.mysql.jdbc.Driver")
                //连接到数据库并获取连接对象
                connection = DriverManager.getConnection(jdbcUrl, username, password)
                //SQL查询,从 activity 表中获取所有行
                val resultSet =
                    connection?.createStatement()?.executeQuery("SELECT * FROM test_choose")
                //使用 resultSet.next() 遍历结果集并获取每一行的每一列的值

                for (i in 0..(resultSet?.row ?: 0)){
                    while (resultSet?.next() == true) {
                        val testname = resultSet.getString("testname") ?: ""
                        val item = G_Card(testname, i)
                        datas.add(item)
                        nameList.add(testname)
                    }
                }

                //通知 adapter 数据已经更改并调用 notifyDataSetChanged() 方法来更新视图
                adapter.notifyDataSetChanged()
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