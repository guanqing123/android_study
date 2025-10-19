package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firt_layout);
        Log.i("guanqing", "系统启动成功");
        TextView tv = findViewById(R.id.tv);
        tv.setText("你好, 官青! ");

        Button jumpBtn = findViewById(R.id.jump);
        jumpBtn.setOnClickListener(btn -> {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        });
    }
}