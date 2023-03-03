package com.example.a111111.life

import android.os.Bundle
import android.widget.Toast
import com.example.a111111.BaseActivity
import com.example.a111111.databinding.ActivityInstantReminderBinding

class InstantReminderActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityInstantReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSend.setOnClickListener {
            val title = binding.edtTitle.text.toString()
            val content = binding.edtContent.text.toString()
            if(title.isNotEmpty()||content.isNotEmpty()) {
                val remind = Remind(title, content, "0")
            }
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show()
        }
    }
}