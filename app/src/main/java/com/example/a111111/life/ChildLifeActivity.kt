package com.example.a111111.life

import android.content.Intent
import android.os.Bundle
import com.example.a111111.WT_BaseActivity
import com.example.a111111.databinding.ActivityChildLifeBinding

class ChildLifeActivity : WT_BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityChildLifeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnToInstantReminder.setOnClickListener {
            val intent = Intent(this, InstantRemindActivity::class.java)
            startActivity(intent)
        }
        binding.btnToTimedReminder.setOnClickListener {
             val intent = Intent(this, TimedRemindActivity::class.java)
            startActivity(intent)
        }
    }
}