package com.example.a111111

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.Timestamp


class L_ChooseActivityDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_detail)

        val name = intent.getStringExtra("name")
Log.e("TAG","$name")

        //创建一个新线程
        Thread {
            //加载 MySQL JDBC 驱动程序
            Class.forName("com.mysql.jdbc.Driver")

            //连接到数据库并获取连接对象
            val connection = DriverManager.getConnection(jdbcUrl, username, password)

            // 使用 JDBC 驱动从数据库中读取数据
            val statement = connection.createStatement()
            val resultSet =
                statement.executeQuery("SELECT * FROM activities WHERE name = '$name'")
            //更新UI需要在主线程
            runOnUiThread {
                val activityNameTextView: TextView = findViewById(R.id.activityNameTextView)
                val timeTextView: TextView = findViewById(R.id.timeTextView)
                val locationTextView: TextView = findViewById(R.id.locationTextView)
                val workContentTextView: TextView = findViewById(R.id.workContentTextView)
                val childAdviceTextView: TextView = findViewById(R.id.childAdviceTextView)

                // 将数据填充到文本展示框中
                if (resultSet.next()) {
                    activityNameTextView.text = resultSet.getString("name")
                    timeTextView.text = resultSet.getString("time")
                    locationTextView.text = resultSet.getString("location")
                    workContentTextView.text = resultSet.getString("work_content")
                    childAdviceTextView.text = resultSet.getString("child_advice")
                    Log.e("choose","1")
                }
            }
        }.start()



        val participate : Button = findViewById(R.id.participate)

        participate.setOnClickListener {
            Thread {
            try {

                val sharedPreferences = this.getSharedPreferences("user_info", Context.MODE_PRIVATE)
                val currentUser = sharedPreferences.getString("username", "")
                Log.e("currentuser","$currentUser")

                Class.forName("com.mysql.jdbc.Driver")
                val connection = DriverManager.getConnection(jdbcUrl, username, password)
                val statement = connection.createStatement()

                val older_name = statement.executeQuery("SELECT binding_username FROM users WHERE username='$currentUser'")
                Log.e("oldername","$older_name")


                val resultSet =
                    statement.executeQuery("SELECT time,location,work_content,child_advice FROM activities WHERE name = '$name'")

                while (resultSet.next()) {
                    val location: String = resultSet.getString("location")
                    Log.e("location", location)

                    Log.e("choose", "2")


                    val time: Timestamp = resultSet.getTimestamp("time")
                    Log.e("time", "$time")
                    val work_content: String = resultSet.getString("work_content")
                    Log.e("work_content", work_content)
                    val child_advice: String = resultSet.getString("child_advice")
                    val youngmanname: String? = currentUser
                    Log.e("young", "$youngmanname")
                    val oldername: String = "yanshi"/*older_name.getString("binding_username")*/
                    Log.e("oldername", oldername)



                    val insertSQL =
                        "INSERT INTO participation (oldername, youngmanname, name,time,location,work_content,child_advice) values(?,?,?,?,?,?,?)"
                    val preparedStatement: PreparedStatement =
                        connection.prepareStatement(insertSQL)
                    preparedStatement.setString(1, oldername)
                    preparedStatement.setString(2, youngmanname)
                    preparedStatement.setString(3, name)
                    preparedStatement.setTimestamp(4, time)
                    preparedStatement.setString(5, location)
                    preparedStatement.setString(6, work_content)
                    preparedStatement.setString(7, child_advice)
                    preparedStatement.executeUpdate()
                    Log.e("choose", "4")
                }
            }catch (e: SQLException) {
                e.printStackTrace()
                Log.e("choose","坏了")
                Log.e("choose", "出现异常：${e.message}")
            }
            }.start()
        }
    }

    val jdbcUrl = "jdbc:mysql://39.101.79.219:3306/sgly2004?useSSL=false"
    val username = "sgly2004"
    val password = "sgly2004"
}
