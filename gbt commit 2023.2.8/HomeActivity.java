package com.example.helloword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class HomeActivity extends AppCompatActivity {

    private Button bt_userspace;
    private Button bt_call_help;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        bt_userspace = findViewById(R.id.btn_main_6);
        bt_call_help = findViewById(R.id.btn_main_3);
        //实现直接跳转方法一

        bt_userspace.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(HomeActivity.this,userspaceActivity.class);
                startActivity(intent);
            }
        });

        bt_call_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(HomeActivity.this,Call_HelpActivity.class);
                startActivity(intent);
            }
        });

        //匹配用户名密码的跳转登录


    }
}
