package com.example.a111111

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement


class RegisterActivity : BaseActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        if (Build.VERSION.SDK_INT > 9) {
            val policy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        usernameEditText = findViewById(R.id.username_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text)
        registerButton = findViewById(R.id.register_button)

        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "密码不一致", Toast.LENGTH_SHORT).show()
            } else {
                Class.forName("com.mysql.jdbc.Driver")
                // Connect to MySQL database
                val conn: Connection? = DriverManager.getConnection(
                    "jdbc:mysql://39.101.79.219:3306/sgly2004?useSSL=false",
                    "sgly2004",
                    "sgly2004"
                )

                conn?.autoCommit = true

                // Prepare SQL statement to insert user data
                val statement: PreparedStatement? = conn?.prepareStatement(
                    "INSERT INTO users (username, password) VALUES (?, ?)"
                )
                statement?.setString(1, username)
                statement?.setString(2, password)

                // Execute SQL statement
                val rowsInserted: Int? = statement?.executeUpdate()
                if (rowsInserted != null && rowsInserted > 0) {
                    Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show()
                    conn.commit()
                    conn.close()
                    startActivity(Intent(this, LoginActivity::class.java))
                } else {
                    if (conn != null) {
                        conn.rollback()
                    }
                    if (conn != null) {
                        conn.close()
                    }
                    Toast.makeText(this, "注册失败，用户已存在", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
