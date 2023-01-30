package com.example.a111111

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a111111.databinding.ActivitySwitch11Binding


class Switch11Activity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySwitch11Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnElder.setOnClickListener {
            val intent = Intent(this, ElderActivity::class.java)
            startActivity(intent)
        }

        binding.btnChild.setOnClickListener {
            val intent = Intent(this, ChildActivity::class.java)
            startActivity(intent)
        }
    }
}