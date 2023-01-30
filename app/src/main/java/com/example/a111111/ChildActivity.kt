package com.example.a111111

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ChildActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child_acctivity)
        val btnLife = findViewById<Button>(R.id.btn_life)
        val btnActivity = findViewById<Button>(R.id.btn_activity)
        val btnInteraction = findViewById<Button>(R.id.btn_interaction)
        val btnPersonal = findViewById<Button>(R.id.btn_personal)
        btnLife.setOnClickListener {
            //跳转到生活界面
        }

        btnActivity.setOnClickListener {
            //跳转到活动界面
        }

        btnInteraction.setOnClickListener {
            //跳转到互动界面
        }

        btnPersonal.setOnClickListener {
            val intent = Intent(this, ChSetActivity::class.java)
            startActivity(intent)
        }

    }
}