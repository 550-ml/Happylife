package com.example.a111111

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.a111111.databinding.ActivitySwitch11Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.SQLException

class WT_Switch11Activity : WT_BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySwitch11Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnElder.setOnClickListener {
            saveUserTypeToDatabase("老人")
            val intent = Intent(this, WT_ElderActivity::class.java)
            startActivity(intent)
        }

        binding.btnChild.setOnClickListener {
            saveUserTypeToDatabase("子女")
            val intent = Intent(this, WT_ChildActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveUserTypeToDatabase(userType: String) {
        val currentUser = WT_Application.currentUser
        if (currentUser == null) {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show()
            return
        }
        GlobalScope.launch(Dispatchers.IO) {
            var conn: Connection? = null
            var statement: PreparedStatement? = null

            try {
                // Connect to MySQL database
                conn = DriverManager.getConnection(
                    "jdbc:mysql://39.101.79.219:3306/sgly2004?useSSL=false",
                    "sgly2004",
                    "sgly2004"
                )

                // Prepare SQL statement to update user type
                statement = conn.prepareStatement(
                    "UPDATE users SET lei = ? WHERE username = ?"
                )
                statement.setString(1, userType)
                statement.setString(2, currentUser.username)

                // Execute SQL statement
                val rowsAffected = statement.executeUpdate()

                withContext(Dispatchers.Main) {
                    if (rowsAffected > 0) {
                        Toast.makeText(this@WT_Switch11Activity, "保存用户类型成功", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@WT_Switch11Activity, "保存用户类型失败", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: SQLException) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@WT_Switch11Activity, "保存用户类型失败，发生错误", Toast.LENGTH_SHORT).show()
                }
            } finally {
                statement?.close()
                conn?.close()
            }
        }
    }

}
