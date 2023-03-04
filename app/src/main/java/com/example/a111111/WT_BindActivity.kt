package com.example.a111111

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a111111.databinding.ActivityWtBindBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class WT_BindActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWtBindBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWtBindBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get saved child username from SharedPreferences
        val sharedPref = getSharedPreferences("user_info", Context.MODE_PRIVATE)
        val childUsername = sharedPref.getString("username", "")

        // Assign the retrieved username to the variable
        binding.etBindUsername.setText(childUsername)

        binding.btnBind.setOnClickListener {
            val elderUsername = binding.etUsername.text.toString()
            val elderPassword = binding.etPassword.text.toString()

            if (childUsername != null) {
                if (elderUsername.isNotEmpty() && elderPassword.isNotEmpty() ) {
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

                            // Prepare SQL statement to retrieve elder user data
                            statement = conn.prepareStatement(
                                "SELECT * FROM users WHERE username = ? AND password = ? AND lei = '老人'"
                            )
                            statement.setString(1, elderUsername)
                            statement.setString(2, elderPassword)

                            // Execute SQL statement
                            resultSet = statement.executeQuery()

                            if (resultSet.next()) {
                                val bindingUsername = resultSet.getString("binding_username")
                                if (bindingUsername != null) {
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(
                                            this@WT_BindActivity,
                                            "该老人账号已被绑定，请更换账号",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    // Prepare SQL statement to retrieve child user data
                                    statement = conn.prepareStatement(
                                        "SELECT * FROM users WHERE username = ? AND lei = '子女'"
                                    )
                                    statement.setString(1, childUsername)

                                    // Execute SQL statement
                                    resultSet = statement.executeQuery()

                                    if (resultSet.next()) {
                                        // Update the binding_username of the elder user
                                        val updateStatement1 = conn.prepareStatement(
                                            "UPDATE users SET binding_username = ? WHERE username = ?"
                                        )
                                        updateStatement1.setString(1, childUsername)
                                        updateStatement1.setString(2, elderUsername)
                                        updateStatement1.executeUpdate()

                                        // Update the binding_username of the child user
                                        val updateStatement2 = conn.prepareStatement(
                                            "UPDATE users SET binding_username = ? WHERE username = ?"
                                        )
                                        updateStatement2.setString(1, elderUsername)
                                        updateStatement2.setString(2, childUsername)
                                        updateStatement2.executeUpdate()

                                        withContext(Dispatchers.Main) {
                                            Toast.makeText(
                                                this@WT_BindActivity,
                                                "绑定成功",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    } else {
                                        withContext(Dispatchers.Main) {
                                            Toast.makeText(
                                                this@WT_BindActivity,
                                                "该子女账号不存在",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }
                            } else {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        this@WT_BindActivity,
                                        "老人账号或密码错误，或非老人账号",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } catch (e: SQLException) {
                            e.printStackTrace()
                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@WT_BindActivity, "绑定失败 发生错误", Toast.LENGTH_SHORT).show()
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
        }
    }
}
