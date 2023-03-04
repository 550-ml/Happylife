package com.example.a111111.life

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a111111.BaseActivity
import com.example.a111111.databinding.ActivityTimedRemindBinding

class TimedRemindActivity : BaseActivity() {

    private val remindList = ArrayList<Remind>()

    private var adapter: RemindAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTimedRemindBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = RemindAdapter(remindList)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.btnSend.setOnClickListener {
            val title = binding.edtTitle.text.toString()
            val content = binding.edtContent.text.toString()
            val time = binding.edtTime.text.toString()
            if(title.isNotEmpty()||content.isNotEmpty()||time.isNotEmpty()){
                val remind = Remind(title, content, time)
                remindList.add(remind)
                adapter.notifyItemInserted(remindList.size-1)
                binding.recyclerView.scrollToPosition(remindList.size-1)
                binding.edtTitle.setText("")
                binding.edtContent.setText("")
                binding.edtTime.setText("")
            }
        }
        adapter.setOnItemClickListener(object :RemindAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                AlertDialog.Builder(this@TimedRemindActivity).apply {
                    setTitle("是否要删除此条提醒")
                    setMessage("")
                    setCancelable(false)
                    setPositiveButton("确定") { dialog, which ->
                        adapter.removeData(position)
                    }
                    setNegativeButton("取消") { dialog, which -> }
                    show()
                }
            }
        }
        )
    }
}