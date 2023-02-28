package com.example.a111111.life

import android.os.Bundle
import com.example.a111111.BaseActivity
import com.example.a111111.databinding.ActivityTimedReminderBinding

class TimedReminderActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTimedReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}