package com.example.a111111.life


import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a111111.WT_BaseActivity
import com.example.a111111.databinding.ActivityElderRemindBinding

class ElderRemindActivity : WT_BaseActivity() {

    private val remindList =ArrayList<Remind>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityElderRemindBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter  = RemindAdapter(remindList)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

    }
}