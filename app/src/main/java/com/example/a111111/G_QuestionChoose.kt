package com.example.a111111

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.sql.Connection
import java.sql.DriverManager

class G_QuestionChoose : AppCompatActivity() {
    private var datas = mutableListOf<G_Card>()
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.g_question_choose)

        val test_name = intent.getStringExtra("name")
        Log.e("test_name","$test_name")


        // 创建了一个 RecyclerView 控件，并将其与 com.example.happy-life.OlderAdapter 适配器绑定
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // 创建了一个 RecyclerView 控件实例
        recyclerView.layoutManager = LinearLayoutManager(this)
        //使用 layoutManager 属性来设置 RecyclerView 的布局管理器，这里使用的是 LinearLayoutManager（设置了滚动方式）
        val acting = Z_Acting(this)
        val adapter = test_name?.let { G_QuestionChooseAdapter(datas, it,acting) }
        //使用 com.example.happy-life.OlderAdapter 类的构造函数来创建一个 Adapter 实例（将数据进行转化）
        recyclerView.adapter = adapter
        //将这个实例设置给 RecyclerView 的 adapter 属性

        Log.e("QuestionChoose","QuestionChoose被调用了")

        Thread {
            try {

                Log.e("1","1")
                // 加载 MySQL JDBC 驱动程序
                Class.forName("com.mysql.jdbc.Driver")
                //连接到数据库并获取连接对象
                connection = DriverManager.getConnection(jdbcUrl, username, password)

                val resultSet = connection?.createStatement()?.executeQuery("SELECT * FROM question_choose WHERE test_name='$test_name'")

                if (resultSet != null)Log.e("result","result!=null")

                for (i in 0..(resultSet?.row ?: 0)){
                    Log.e("i","$i")
                    if (resultSet?.next() == true) {
                        do {
                            val question = resultSet.getString("question_content") ?: ""
                            val item = G_Card(question, i)
                            datas.add(item)
                            Log.e("question", question)
                        } while (resultSet.next())
                    }
                }

                recyclerView.post {
                    adapter?.notifyDataSetChanged()
                    Log.e("QuestionChoose","Question的适配器被调用了")
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