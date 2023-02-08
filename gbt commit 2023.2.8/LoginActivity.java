package com.example.helloword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtLogin;
    private EditText metuser;
    private EditText metpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mBtLogin = findViewById(R.id.btn_1);
        metuser = findViewById(R.id.et_1);
        metpassword = findViewById(R.id.et_2);
        //实现直接跳转方法一

        /*mBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(MainActivity.this,FunctionActivity.class);
                startActivity(intent);
            }
        });*/

        //匹配用户名密码的跳转登录


        mBtLogin.setOnClickListener(this);
    }

    public void onClick(View v){

        String username = metuser.getText().toString();
        String password = metpassword.getText().toString();
        //弹出内容设置
        String ok_login="登录成功！";
        String faile_login="账号或密码有误，请重新输入";
        Intent intent = null;

        //对比匹配密码和用户名
        if(username.equals("gbt")&&password.equals("20180701")){
            //用户名和密码匹配成功
            intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);

            Toast.makeText(getApplicationContext(),ok_login,Toast.LENGTH_SHORT).show();
        }
        else{
            //弹出登录失败
            Toast toastcenter = Toast.makeText(getApplicationContext(),faile_login,Toast.LENGTH_SHORT);
            toastcenter.setGravity(Gravity.CENTER,0,0);
            toastcenter.show();
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}