package com.example.a111111.life


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a111111.WT_BaseActivity
import com.example.a111111.databinding.ActivityElderLifeBinding
import java.sql.DriverManager

class ElderLifeActivity : WT_BaseActivity() {

    private val remindList =ArrayList<Remind>()
    val jdbcUrl = "jdbc:mysql://39.101.79.219:3306/sgly2004?useSSL=false"
    val username = "sgly2004"
    val password = "sgly2004"

    override fun onResume() {
        remindList.clear()
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityElderLifeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.e("remind","5")
        val adapter  = RemindAdapter(remindList)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        Thread {

            try {

            //加载 MySQL JDBC 驱动程序
            Class.forName("com.mysql.jdbc.Driver")

            //连接到数据库并获取连接对象
            val connection = DriverManager.getConnection(jdbcUrl, username, password)

            Log.e("remind1","1")

            //使用 connection 属性来获取到数据库连接
            // 使用 JDBC 驱动从数据库中读取数据
            val sharedPreferences = this.getSharedPreferences("user_info", Context.MODE_PRIVATE)
            val childName = sharedPreferences.getString("username","")
            val sql ="SELECT *FROM remind WHERE child_name= ?"
            val statement = connection.prepareStatement(sql)
            statement.setString(1, childName)
            // 执行查询语句，获取结果集
            val resultSet = statement.executeQuery()

            // 遍历结果集，将查询到的记录保存到一个 List 中

            Log.e("remind2","2")
            if (resultSet.next()==false){Log.e("remind","没进去")}else{Log.e("remind","进去了")}


            while (resultSet.next()) {
                val title = resultSet.getString("title")
                Log.e("remind", title)
                val content = resultSet.getString("content")
                Log.e("remind", content)
                // ... 根据表中的字段，继续获取其他信息
                val child = Remind(title, content)
                remindList.add(child)

                Log.e("remind3","3")

            }

            // 关闭结果集、PreparedStatement 和数据库连接
            resultSet.close()
            statement.close()
            connection.close()

                binding.recyclerView.post {
                    adapter.notifyDataSetChanged()
                    Log.e("TestChoose","适配器调用成功")
                }


            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                Log.e("choose", "出现异常：${e.message}")
            }

        }.start()





    }
}