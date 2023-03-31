package com.example.a111111

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import java.sql.Connection
import java.sql.DriverManager
import kotlin.math.log

class G_Rule : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.g_rule)

        val name = intent.getStringExtra("name")
        val RuleTextView = findViewById<TextView>(R.id.RuleTextView)

        Log.e("rule","到这了吗")
        Log.e("rule","$name")

        Thread {
            try {
                Log.e("rusult","到此一游")
                // 加载 MySQL JDBC 驱动程序
                Class.forName("com.mysql.jdbc.Driver")
                //连接到数据库并获取连接对象
                connection = DriverManager.getConnection(jdbcUrl, username, password)

                Log.e("rusult","连接完毕")

                val resultSet = connection?.createStatement()?.executeQuery("SELECT rule FROM test_choose WHERE test_name='$name'")
                if (resultSet != null) {
                    if (resultSet.next()) { // 需要先调用 next() 方法将指针移到第一行记录
                        runOnUiThread {
                            RuleTextView.text = resultSet.getString("rule")
                        }
                        Log.e("rusult","到了")
                    } else {
                        Log.e("rule","结果集中没有记录")
                    }
                } else {
                    Log.e("rule","查询结果为空")
                }


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
