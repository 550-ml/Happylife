package com.example.a111111

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.sql.Connection
import java.sql.DriverManager

class L_MyActivity : AppCompatActivity() {
    var datas = mutableListOf<L_Item_card>()
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)

        // 创建了一个 RecyclerView 控件，并将其与 com.example.happy-life.OlderAdapter 适配器绑定
        val recyclerView_up = findViewById<RecyclerView>(R.id.my_participation_recycler_view)
        val recyclerView_down = findViewById<RecyclerView>(R.id.new_activity_recycler_view)
        // 创建了一个 RecyclerView 控件实例
        recyclerView_up.layoutManager = LinearLayoutManager(this)
        recyclerView_down.layoutManager = LinearLayoutManager(this)
        //使用 layoutManager 属性来设置 RecyclerView 的布局管理器，这里使用的是 LinearLayoutManager（设置了滚动方式）
        val adapter_up = L_MyParticipationAdapter(this,datas)
        val adapter_down = L_NewActivityAdapter(this,datas)
        //使用 com.example.happy-life.OlderAdapter 类的构造函数来创建一个 Adapter 实例（将数据进行转化）
        recyclerView_up.adapter = adapter_up
        recyclerView_down.adapter = adapter_down
        //将这个实例设置给 RecyclerView 的 adapter 属性

        Thread {
            try {
                // 加载 MySQL JDBC 驱动程序
                Class.forName("com.mysql.jdbc.Driver")
                //连接到数据库并获取连接对象
                connection = DriverManager.getConnection(jdbcUrl, username, password)
                //SQL查询,从 activity 表中获取所有行
                val resultSetUp =
                    connection?.createStatement()?.executeQuery("SELECT * FROM participation")
                val resultSetDown =
                    connection?.createStatement()?.executeQuery("SELECT * FROM activities")
                //使用 resultSet.next() 遍历结果集并获取每一行的每一列的值

                for (i in 0..(resultSetUp?.row ?: 0)){
                    while (resultSetUp?.next() == true) {
                        val name = resultSetUp.getString("name") ?: ""
                        val item = L_Item_card(name, i)
                        datas.add(item)
                    }
                }
                for (i in 0..(resultSetDown?.row ?: 0)){
                    while (resultSetDown?.next() == true) {
                        val name = resultSetDown.getString("name") ?: ""
                        val item = L_Item_card(name, i)
                        datas.add(item)
                    }
                }

                recyclerView_up.post {
                    //通知 adapter 数据已经更改并调用 notifyDataSetChanged() 方法来更新视图
                    adapter_up.notifyDataSetChanged()
                }
                recyclerView_down.post {
                    adapter_down.notifyDataSetChanged()
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }.start()

        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener {
            val intent = Intent(this, L_Add::class.java)
            startActivity(intent)
        }
    }

    companion object {
        private const val jdbcUrl = "jdbc:mysql://39.101.79.219:3306/sgly2004?useSSL=false"
        private const val username = "sgly2004"
        private const val password = "sgly2004"
        var connection: Connection? = null
    }
}