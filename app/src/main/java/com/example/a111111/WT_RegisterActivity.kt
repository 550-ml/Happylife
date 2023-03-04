package com.example.a111111

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.SQLException

class WT_RegisterActivity : WT_BaseActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        usernameEditText = findViewById(R.id.username_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text)
        registerButton = findViewById(R.id.register_button)

        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "请填写完整信息", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "密码不一致", Toast.LENGTH_SHORT).show()
            } else {
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

                        conn.autoCommit = false

                        // Prepare SQL statement to insert user data
                        statement = conn.prepareStatement(
                            "INSERT INTO users (username, password) VALUES (?, ?)"
                        )
                        statement.setString(1, username)
                        statement.setString(2, password)

                        // Execute SQL statement
                        val rowsInserted: Int = statement.executeUpdate()

                        if (rowsInserted > 0) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@WT_RegisterActivity, "注册成功", Toast.LENGTH_SHORT).show()
                            }
                            conn.commit()
                            conn.close()

                            withContext(Dispatchers.Main) {
                                startActivity(Intent(this@WT_RegisterActivity, WT_LoginActivity::class.java))
                            }
                        } else {
                            conn.rollback()
                            conn.close()
                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@WT_RegisterActivity, "注册失败，用户已存在", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: SQLException) {
                        e.printStackTrace()
                        if (conn != null) {
                            conn.rollback()
                            conn.close()
                        }
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@WT_RegisterActivity, "注册失败，发生错误", Toast.LENGTH_SHORT).show()
                        }
                    } finally {
                        statement?.close()
                        conn?.close()
                    }
                }
            }
        }
    }
}
