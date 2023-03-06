package com.example.a111111.life

import android.os.Bundle
import android.widget.Toast
import com.example.a111111.WT_BaseActivity
import com.example.a111111.databinding.ActivityInstantRemindBinding
import java.sql.DriverManager

class InstantRemindActivity : WT_BaseActivity() {

    val jdbcUrl = "jdbc:mysql://39.101.79.219:3306/sgly2004?useSSL=false"
    val username = "sgly2004"
    val password = "sgly2004"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityInstantRemindBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSend.setOnClickListener {
            val title = binding.edtTitle.text.toString()
            val content = binding.edtContent.text.toString()
            if(title.isNotEmpty()||content.isNotEmpty()) {
                Thread {

                    //加载 MySQL JDBC 驱动程序
                    Class.forName("com.mysql.jdbc.Driver")

                    //连接到数据库并获取连接对象
                    val connection = DriverManager.getConnection(jdbcUrl, username, password)

                    //使用 connection 属性来获取到数据库连接
                    // 使用 JDBC 驱动从数据库中读取数据
                    val statement = connection.createStatement()
                    val sql = "INSERT INTO remind (title,content) VALUES ('$title', '$content')"

                    // 将数据插入到数据库中
                    statement.executeUpdate(sql)

                    // 关闭连接
                    statement.close()
                    connection.close()
                }.start()
            }
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show()
        }
    }
}