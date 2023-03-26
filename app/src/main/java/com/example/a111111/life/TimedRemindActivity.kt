package com.example.a111111.life

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a111111.R
import com.example.a111111.WT_BaseActivity
import com.example.a111111.databinding.ActivityTimedRemindBinding
import java.sql.DriverManager

class TimedRemindActivity : WT_BaseActivity() {

    private val remindList = ArrayList<Remind>()
    val jdbcUrl = "jdbc:mysql://39.101.79.219:3306/sgly2004?useSSL=false"
    val username = "sgly2004"
    val password = "sgly2004"

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
            if(title.isNotEmpty()||content.isNotEmpty()){
                val remind = Remind(title, content)
                remindList.add(remind)
                adapter.notifyItemInserted(remindList.size-1)
                binding.recyclerView.scrollToPosition(remindList.size-1)
                binding.edtTitle.setText("")
                binding.edtContent.setText("")
                Thread {

                    //加载 MySQL JDBC 驱动程序
                    Class.forName("com.mysql.jdbc.Driver")

                    //连接到数据库并获取连接对象
                    val connection = DriverManager.getConnection(jdbcUrl, username, password)

                    //使用 connection 属性来获取到数据库连接
                    // 使用 JDBC 驱动从数据库中读取数据
                    val statement = connection.createStatement()
                    val sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE)
                    val username = sharedPreferences.getString("username","")
                    val sql = "INSERT INTO remind (child_name,title,content,time) VALUES ('$username','$title', '$content')"

                    // 将数据插入到数据库中
                    statement.executeUpdate(sql)

                    // 关闭连接
                    statement.close()
                    connection.close()
                }.start()
            }

        }
        adapter.setOnItemClickListener(object :RemindAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                AlertDialog.Builder(this@TimedRemindActivity).apply {
                    setTitle("是否要删除此条提醒")
                    setMessage("")
                    setCancelable(false)
                    setPositiveButton("确定") { _, _ ->
                        binding.recyclerView.getChildAt(position)?.let {
                            val title = it.findViewById<TextView>(R.id.remindTitle)
                            Thread {

                                //加载 MySQL JDBC 驱动程序
                                Class.forName("com.mysql.jdbc.Driver")

                                //连接到数据库并获取连接对象
                                val connection = DriverManager.getConnection(jdbcUrl, username, password)

                                //使用 connection 属性来获取到数据库连接
                                // 使用 JDBC 驱动从数据库中读取数据
                                val statement = connection.createStatement()
                                val sql = "DELETE FROM remind WHERE title='$title'"

                                // 将数据插入到数据库中
                                statement.executeUpdate(sql)

                                // 关闭连接
                                statement.close()
                                connection.close()
                            }.start()
                        }
                        adapter.removeData(position)

                    }
                    setNegativeButton("取消") { _, _ -> }
                    show()
                }
            }
        }
        )
    }
}