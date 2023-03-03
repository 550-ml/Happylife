package com.example.a111111

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.sql.Connection
import java.sql.DriverManager

class G_Rule : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.g_rule)

        val position = intent.getIntExtra("position", 0)

        Thread {
            try {
                // 加载 MySQL JDBC 驱动程序
                Class.forName("com.mysql.jdbc.Driver")
                //连接到数据库并获取连接对象
                connection = DriverManager.getConnection(jdbcUrl, username, password)
                //SQL查询,从 test 表中查询name行，rule字段
                val resultSet =
                    connection?.createStatement()?.executeQuery("SELECT rule FROM test WHERE id=$position")
                val RuleTextView = findViewById<TextView>(R.id.RuleTextView)
                RuleTextView.text= resultSet.toString()
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
