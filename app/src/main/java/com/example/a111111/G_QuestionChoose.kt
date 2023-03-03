package com.example.a111111

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.sql.Connection
import java.sql.DriverManager

class G_QuestionChoose : AppCompatActivity() {
    private var datas = mutableListOf<G_Card>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.g_question_choose)

        // 创建了一个 RecyclerView 控件，并将其与 com.example.happy-life.OlderAdapter 适配器绑定
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // 创建了一个 RecyclerView 控件实例
        recyclerView.layoutManager = LinearLayoutManager(this)
        //使用 layoutManager 属性来设置 RecyclerView 的布局管理器，这里使用的是 LinearLayoutManager（设置了滚动方式）
        val adapter = G_QuestionChooseAdapter(datas)
        //使用 com.example.happy-life.OlderAdapter 类的构造函数来创建一个 Adapter 实例（将数据进行转化）
        recyclerView.adapter = adapter
        //将这个实例设置给 RecyclerView 的 adapter 属性

        val name = intent.getIntExtra("name", 0)

        Thread {
            try {
                // 加载 MySQL JDBC 驱动程序
                Class.forName("com.mysql.jdbc.Driver")
                //连接到数据库并获取连接对象
                connection = DriverManager.getConnection(jdbcUrl, username, password)
                //SQL查询,从 activity 表中获取所有行
                val resultSet =
                    connection?.createStatement()?.executeQuery("SELECT * FROM activities WHERE id=$name")

                //使用 resultSet.next() 遍历结果集并获取每一行的每一列的值

                for (i in 0..(resultSet?.row ?: 0)){
                    while (resultSet?.next() == true) {
                        val question = resultSet.getString("question") ?: ""
                        val item = G_Card(question, i)
                        datas.add(item)
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