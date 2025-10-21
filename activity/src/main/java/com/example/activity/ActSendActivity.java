package com.example.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;

public class ActSendActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_send);
        tv_send = findViewById(R.id.tv_send);
        findViewById(R.id.btn_send).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_send) {
            Intent intent = new Intent(this, ActReceiveActivity.class);
            // 创建一个新的包裹
            Bundle bundle = new Bundle();
            bundle.putString("request_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
            bundle.putString("request_content", tv_send.getText().toString());
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}