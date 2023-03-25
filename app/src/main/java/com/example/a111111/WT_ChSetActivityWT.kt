package com.example.a111111



import android.content.Intent
import android.os.Bundle
import android.widget.Button

class WT_ChSetActivityWT : WT_BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ch_set)
        val accountAndSecurity = findViewById<Button>(R.id.btn_account_security)
        val personalInfo = findViewById<Button>(R.id.btn_personal_info)
        val messageNotification = findViewById<Button>(R.id.btn_message_notification)
        val privacy = findViewById<Button>(R.id.btn_privacy)
        val general = findViewById<Button>(R.id.btn_general)
        val logout = findViewById<Button>(R.id.btn_logout)
        val bindingold = findViewById<Button>(R.id.btn_binding) // 获取绑定按钮

        accountAndSecurity.setOnClickListener {
            //跳转到账号与安全界面
            val intent = Intent(this,WT_ChangeActivity::class.java)
            startActivity(intent)
        }
        personalInfo.setOnClickListener {
            //跳转到个人信息界面
            val intent = Intent(this,WT_PersonalSetActivity::class.java)
            startActivity(intent)
        }
        bindingold.setOnClickListener {
            //跳转到绑定界面
            val intent = Intent(this, WT_BindActivity::class.java)
            startActivity(intent)
        }

        messageNotification.setOnClickListener {
            //跳转到消息通知界面
        }
        privacy.setOnClickListener {
            //跳转到隐私界面
        }
        general.setOnClickListener {
            //跳转到通用界面
        }
        logout.setOnClickListener {
            val intent = Intent(this, WT_LoginActivity::class.java)
            startActivity(intent)
            WT_ActivityCollector.finishAll()
            finish()
        }
    }
}