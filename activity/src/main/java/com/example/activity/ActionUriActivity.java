package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class ActionUriActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_uri);
        findViewById(R.id.btn_dial).setOnClickListener(this);
        findViewById(R.id.btn_sms).setOnClickListener(this);
        findViewById(R.id.btn_my).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dial: {
                Intent intent = new Intent();
                // 设置意图动作为准备拨号
                intent.setAction(Intent.ACTION_DIAL);
                String phone = "15857125375";
                // 声明一个拨号的uri
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
                break;
            }
            case R.id.btn_sms: {
                Intent intent = new Intent();
                // 设置意图动作为发送短信
                intent.setAction(Intent.ACTION_SENDTO);
                String phone = "15857125375";
                // 创建一个uri
                intent.setData(Uri.parse("smsto:" + phone));
                // 添加短信内容
                intent.putExtra("sms_body", "hello world");
                startActivity(intent);
                break;
            }
            case R.id.btn_my: {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.GQ");
                intent.addCategory("android.intent.category.DEFAULT");
                startActivity(intent);
                break;
            }
        }
    }
}