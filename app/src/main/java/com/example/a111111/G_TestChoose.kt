package com.example.a111111

import android.annotation.SuppressLint
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.sql.Connection
import java.sql.DriverManager

class G_TestChoose : AppCompatActivity() {
    private var datas = mutableListOf<G_Card>()
    @SuppressLint("NotifyDataSetChanged")
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
                        val Testname = resultSet.getString("test_name") ?: ""
                        val item = G_Card(Testname, i)
                        datas.add(item)
                        nameList.add(Testname)
                    }
                }
                Log.e("TestChoose","数据获取成功")
                recyclerView.post {
                    adapter.notifyDataSetChanged()
                Log.e("TestChoose","适配器调用成功")
                }

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    fun getConnection() = connection

    companion object {
        val jdbcUrl = "jdbc:mysql://39.101.79.219:3306/sgly2004?useSSL=false"
        val username = "sgly2004"
        val password = "sgly2004"
        var connection: Connection? = null
    }

}