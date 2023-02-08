package com.example.helloword;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Call_HelpActivity extends AppCompatActivity {

    private Button call_family;
    private Button call_phone110;
    private Button call_phone119;
    private Button call_phone120;
    private Button exit_home;
    private Button per_health;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_call_help);

        if(shouldAskPermissions()){

            askPermissions();

        }

        call_family=findViewById(R.id.call_family);
        call_phone110=findViewById(R.id.call_110);
        call_phone119=findViewById(R.id.call_119);
        call_phone120=findViewById(R.id.call_120);
        exit_home=findViewById(R.id.call_exit);
        per_health=findViewById(R.id.health_info);


        //返回主界面
        exit_home.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(Call_HelpActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        //个人健康信息界面
        per_health.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(Call_HelpActivity.this,Info_healthActicity.class);
                startActivity(intent);
            }
        });

        call_phone120.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:120"));
                startActivity(intent);
            }
        });

        call_phone119.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:119"));
                startActivity(intent);
            }
        });

        call_phone110.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:110"));
                startActivity(intent);
            }
        });

        call_family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:111"));
                startActivity(intent);
            }
        });

    }
    protected boolean shouldAskPermissions(){

        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);

    }
    protected void askPermissions() {

        String[] permissions = {

                "android.permission.CALL_PHONE"

        };
    }
}
