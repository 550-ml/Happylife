package com.example.a111111

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.sql.Connection
import java.sql.DriverManager


class L_ViewActivity() : AppCompatActivity() {

    var datas = mutableListOf<L_Item_card>()
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        // 创建了一个 RecyclerView 控件，并将其与 com.example.happy-life.OlderAdapter 适配器绑定
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // 创建了一个 RecyclerView 控件实例
        recyclerView.layoutManager = LinearLayoutManager(this)
        //使用 layoutManager 属性来设置 RecyclerView 的布局管理器，这里使用的是 LinearLayoutManager（设置了滚动方式）
        val adapter = L_ViewActivityAdapter(this,datas)
        //使用 com.example.happy-life.OlderAdapter 类的构造函数来创建一个 Adapter 实例（将数据进行转化）
        recyclerView.adapter = adapter
        //将这个实例设置给 RecyclerView 的 adapter 属性

        Thread {
            try {
                // 加载 MySQL JDBC 驱动程序
                Class.forName("com.mysql.jdbc.Driver")
                //连接到数据库并获取连接对象
                connection = DriverManager.getConnection(jdbcUrl, username, password)
                //SQL查询,从 activity 表中获取所有行

                val sharedPreferences = this.getSharedPreferences("user_info", Context.MODE_PRIVATE)
                val currentUser = sharedPreferences.getString("username", "")

                val query = "SELECT * FROM participation WHERE oldername='$currentUser'"
                val resultSet = connection?.createStatement()?.executeQuery(query)

                for (i in 0..(resultSet?.row ?: 0)){
                    while (resultSet?.next() == true) {
                        val name = resultSet.getString("name") ?: ""
                        val item = L_Item_card(name, i)
                        datas.add(item)
                    }
                }
                recyclerView.post {
                    //通知 adapter 数据已经更改并调用 notifyDataSetChanged() 方法来更新视图
                    adapter.notifyDataSetChanged()
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }.start()
    }


    companion object {
        private const val jdbcUrl = "jdbc:mysql://39.101.79.219:3306/sgly2004?useSSL=false"
        private const val username = "sgly2004"
        private const val password = "sgly2004"
        var connection: Connection? = null
    }
}