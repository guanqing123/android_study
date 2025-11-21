package com.example.broadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.example.broadcast.receiver.StandardReceiver;

public class BroadStandardActivity extends AppCompatActivity implements View.OnClickListener {

    private StandardReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_standard);
        findViewById(R.id.btn_send_standard_broad).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_standard_broad: {
                // 发送标准广播
                Intent intent = new Intent(StandardReceiver.STANDARD_ACTION);
                sendBroadcast(intent);
                break;
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        receiver = new StandardReceiver();
        // 创建一个意图过滤器，只处理STANDARD_ACTION的广播
        IntentFilter filter = new IntentFilter(StandardReceiver.STANDARD_ACTION);
        // 注册接收器，注册之后才能正常接收广播
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 注销接收器，注销之后将不能再接收广播
        unregisterReceiver(receiver);
    }
}