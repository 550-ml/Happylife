package com.example.a111111

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import java.sql.DriverManager

class L_Add : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add)

        val jdbcUrl = "jdbc:mysql://39.101.79.219:3306/sgly2004?useSSL=false"
        val username = "sgly2004"
        val password = "sgly2004"

        val submit_button: Button =findViewById(R.id.submit_button)
        submit_button.setOnClickListener {

            val snackbar = Snackbar.make(findViewById(android.R.id.content), "确定要提交吗？", Snackbar.LENGTH_LONG)

            snackbar.setAction("取消") {
                snackbar.dismiss()
            }.show()

            snackbar.setAction("确定") {
                // 点击确定按钮的处理逻辑
                // 获取文本框中的数据
                val sharedPreferences = this.getSharedPreferences("user_info", Context.MODE_PRIVATE)
                val youngmanname = sharedPreferences.getString("username", "")

                val nameEditText = findViewById<EditText>(R.id.input_name)
                val name = nameEditText.text.toString()

                val timeEditText = findViewById<EditText>(R.id.input_time)
                val time = timeEditText.text.toString()

                val locationEditText = findViewById<EditText>(R.id.input_location)
                val location = locationEditText.text.toString()

                val workcontentEditText = findViewById<EditText>(R.id.input_work_content)
                val workcontent = workcontentEditText.text.toString()

                val childAdviceEditText = findViewById<EditText>(R.id.input_child_advice)
                val childAdvice = childAdviceEditText.text.toString()

                // 创建一个新线程来处理数据库操作
                Thread {
                    //加载 MySQL JDBC 驱动程序
                    Class.forName("com.mysql.jdbc.Driver")

                    //连接到数据库并获取连接对象
                    val connection = DriverManager.getConnection(jdbcUrl, username, password)

                    //使用 connection 属性来获取到数据库连接
                    // 使用 JDBC 驱动从数据库中读取数据
                    val statement = connection.createStatement()
                    val sql = "INSERT INTO activities (youngmanname, name, time, location, work_content, child_advice) " +
                            "VALUES ('$youngmanname','$name', '$time', '$location', '$workcontent', '$childAdvice')"

                    // 将数据插入到数据库中
                    statement.executeUpdate(sql)

                    // 关闭连接
                    statement.close()
                    connection.close()
                }.start()

                // 跳转到 MyActivity 页面
                val intent = Intent(this, L_MyActivity::class.java)
                startActivity(intent)
            }.show()
        }
    }
}