package com.example.a111111

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.a111111.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class WT_LoginActivity : WT_BaseActivity() {
    private lateinit var binding: ActivityLoginBinding

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val account = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
111
            if (account.isNotEmpty() && password.isNotEmpty()) {
                GlobalScope.launch(Dispatchers.IO) {
                    var conn: Connection? = null
                    var statement: PreparedStatement? = null
                    var resultSet: ResultSet? = null

                    try {
                        // Connect to MySQL database
                        conn = DriverManager.getConnection(
                            "jdbc:mysql://39.101.79.219:3306/sgly2004?useSSL=false",
                            "sgly2004",
                            "sgly2004"
                        )

                        // Prepare SQL statement to retrieve user data
                        statement = conn.prepareStatement(
                            "SELECT * FROM users WHERE username = ? AND password = ?"
                        )
                        statement.setString(1, account)
                        statement.setString(2, password)

                        // Execute SQL statement
                        resultSet = statement.executeQuery()

                        if (resultSet.next()) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@WT_LoginActivity, "登录成功", Toast.LENGTH_SHORT).show()
                            }
                            conn.close()

                            // 创建一个 WT_User 对象，并将账号密码存储到该对象中
                            val user = WT_User(account, password)

                            val sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE)
                            sharedPreferences.edit()
                                .putString("username", user.username)
                                .putString("password", user.password)
                                .apply()

                            // 将该对象存储到 WT_Application 的 currentUser 属性中
                            WT_Application.currentUser = user

                            withContext(Dispatchers.Main) {
                                val intent = Intent(this@WT_LoginActivity, WT_Switch11Activity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@WT_LoginActivity, "用户名或密码错误", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: SQLException) {
                        e.printStackTrace()
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@WT_LoginActivity, "登录失败，发生错误", Toast.LENGTH_SHORT).show()
                        }
                    } finally {
                        resultSet?.close()
                        statement?.close()
                        conn?.close()
                    }
                }
            } else {
                Toast.makeText(this, "请填写用户名和密码", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnZhu.setOnClickListener{
            val intent = Intent(this, WT_RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
