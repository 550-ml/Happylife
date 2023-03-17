package com.example.a111111.life


import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a111111.WT_BaseActivity
import com.example.a111111.databinding.ActivityElderRemindBinding
import java.sql.DriverManager

class ElderRemindActivity : WT_BaseActivity() {

    private val remindList =ArrayList<Remind>()
    val jdbcUrl = "jdbc:mysql://39.101.79.219:3306/sgly2004?useSSL=false"
    val username = "sgly2004"
    val password = "sgly2004"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityElderRemindBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Thread {

            //加载 MySQL JDBC 驱动程序
            Class.forName("com.mysql.jdbc.Driver")

            //连接到数据库并获取连接对象
            val connection = DriverManager.getConnection(jdbcUrl, username, password)

            //使用 connection 属性来获取到数据库连接
            // 使用 JDBC 驱动从数据库中读取数据
            val sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE)
            val childname = sharedPreferences.getString("binding","")
            val sql ="SELECT *FROM remind WHERE child_name= '$childname'"
            val statement = connection.prepareStatement(sql)
            statement.setString(1, childname)
            // 执行查询语句，获取结果集
            val resultSet = statement.executeQuery()

            // 遍历结果集，将查询到的记录保存到一个 List 中
            val childList = ArrayList<Remind>()
            while (resultSet.next()) {
                val title = resultSet.getString("title")
                val content = resultSet.getString("content")
                val time = resultSet.getString("time")
                // ... 根据表中的字段，继续获取其他信息
                val child = Remind(title, content, time)
                childList.add(child)
            }

            // 关闭结果集、PreparedStatement 和数据库连接
            resultSet.close()
            statement.close()
            connection.close()
        }.start()

        val adapter  = RemindAdapter(remindList)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

    }
}