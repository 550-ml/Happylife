package com.example.a111111.life

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a111111.BaseActivity
import com.example.a111111.databinding.ActivityTimedReminderBinding

class TimedReminderActivity : BaseActivity() {

    private val remindList = ArrayList<Remind>()

    private var adapter: RemindAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTimedReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.adapter = RemindAdapter(remindList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.btnSend.setOnClickListener {
            val title = binding.edtTitle.text.toString()
            val content = binding.edtContent.text.toString()
            val time = binding.edtTime.text.toString()
            if(title.isNotEmpty()||content.isNotEmpty()||time.isNotEmpty()){
                val remind = Remind(title, content, time)
                remindList.add(remind)
                adapter?.notifyItemInserted(remindList.size-1)
                binding.recyclerView.scrollToPosition(remindList.size-1)
                binding.edtTitle.setText("")
                binding.edtContent.setText("")
                binding.edtTime.setText("")
            }
        }
        TODO("将remind上传服务器数据库")
    }
}