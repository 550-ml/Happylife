package com.example.a111111

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.a111111.life.ChildLifeActivity

class WT_ChildActivity : WT_BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child_acctivity)
        val btnLife = findViewById<Button>(R.id.btn_life)
        val btnActivity = findViewById<Button>(R.id.btn_activity)
         val btnInteraction = findViewById<Button>(R.id.btn_interaction)
        val btnPersonal = findViewById<Button>(R.id.btn_personal)
        btnLife.setOnClickListener {
            val intent = Intent(this, ChildLifeActivity::class.java)
            startActivity(intent)
        }

        btnActivity.setOnClickListener {
            val intent = Intent(this, L_ChooseActivity::class.java)
            startActivity(intent)
        }

        btnInteraction.setOnClickListener {
            val intent = Intent(this, G_TestChoose::class.java)
            startActivity(intent)
        }

        btnPersonal.setOnClickListener {
            val intent = Intent(this, WT_ChSetActivityWT::class.java)
            startActivity(intent)
        }

    }
}