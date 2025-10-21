package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ActReceiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_receive);
        TextView tv = findViewById(R.id.tv_receive);
        Intent intent = getIntent();
        String request_time = intent.getStringExtra("request_time");
        String request_content = intent.getStringExtra("request_content");
        String desc = String.format("请求时间：%s\n请求内容：%s", request_time, request_content);
        tv.setText(desc);
    }
}