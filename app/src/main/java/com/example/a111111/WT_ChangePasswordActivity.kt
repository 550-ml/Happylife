import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a111111.WT_Application
import com.example.a111111.databinding.ActivityWtChangePasswordBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.SQLException

class WT_ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWtChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWtChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            val oldPassword = binding.etOldPassword.text.toString()
            val newPassword = binding.etNewPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "请填写完整信息", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newPassword != confirmPassword) {
                Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
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

                    // Prepare SQL statement to update user password
                    statement = conn.prepareStatement(
                        "UPDATE users SET password = ? WHERE username = ? AND password = ?"
                    )
                    statement.setString(1, newPassword)
                    statement.setString(2, WT_Application.currentUser?.username)
                    statement.setString(3, oldPassword)

                    // Execute SQL statement
                    val rowsAffected = statement.executeUpdate()

                    withContext(Dispatchers.Main) {
                        if (rowsAffected > 0) {
                            Toast.makeText(this@WT_ChangePasswordActivity, "修改密码成功", Toast.LENGTH_SHORT).show()

                            // 更新本地存储的用户信息
                            val sharedPreferences =
                                getSharedPreferences("user_info", MODE_PRIVATE)
                            sharedPreferences.edit()
                                .putString("password", newPassword)
                                .apply()

                            // 更新当前用户对象的密码属性
                            WT_Application.currentUser?.password = newPassword

                            // 关闭当前页面，返回到上一个页面
                            finish()
                        } else {
                            Toast.makeText(this@WT_ChangePasswordActivity, "原密码错误，修改密码失败", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } catch (e: SQLException) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@WT_ChangePasswordActivity, "修改密码失败，发生错误", Toast.LENGTH_SHORT)
                            .show()
                    }
                } finally {
                    statement?.close()
                    conn?.close()
                }
            }
        }
    }
}
