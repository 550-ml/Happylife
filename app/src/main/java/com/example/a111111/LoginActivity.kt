package com.example.a111111

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.a111111.databinding.ActivityLoginBinding


class LoginActivity : BaseActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
//对登录界面进行监控
        binding.btnLogin.setOnClickListener {
            val account = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            val dbHelper = DatabaseHelper(this)
            val db = dbHelper.readableDatabase
            val cursor = db.rawQuery("SELECT * FROM users WHERE username = ?", arrayOf(account))
            if (cursor.moveToFirst()) {
                val username = cursor.getString(cursor.getColumnIndex("username"))
                val pwd = cursor.getString(cursor.getColumnIndex("password"))
                if (password == pwd) {
                    val intent = Intent(this, Switch11Activity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "用户不存在", Toast.LENGTH_SHORT).show()
            }
            cursor.close()
            db.close()
        }
//对注册按钮进行监控
        binding.btnZhu.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}