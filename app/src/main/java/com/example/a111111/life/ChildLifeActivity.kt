package com.example.a111111.life

import android.content.Intent
import android.os.Bundle
import com.example.a111111.BaseActivity
import com.example.a111111.databinding.ActivityChildLifeBinding

class ChildLifeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityChildLifeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnToInstantReminder.setOnClickListener {
            val intent = Intent(this, InstantReminderActivity::class.java)
            startActivity(intent)
        }
        binding.btnToTimedReminder.setOnClickListener {
             val intent = Intent(this, TimedReminderActivity::class.java)
            startActivity(intent)
        }
    }
}