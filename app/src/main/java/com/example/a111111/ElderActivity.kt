package com.example.a111111

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ElderActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_elder)
        val btnLife = findViewById<Button>(R.id.btn_life)
        val btnActivity = findViewById<Button>(R.id.btn_activity)
        val btnEmergency = findViewById<Button>(R.id.btn_emergency)
        val btnPersonal = findViewById<Button>(R.id.btn_personal)
        btnLife.setOnClickListener {
            //跳转到生活界面
        }

        btnActivity.setOnClickListener {
            //跳转到活动界面
        }

        btnEmergency.setOnClickListener {
            //跳转到急救界面
        }

        btnPersonal.setOnClickListener {
            val intent = Intent(this, SetActivity::class.java)
            startActivity(intent)
        }

    }
}