package com.example.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_content_write).setOnClickListener(this);
        findViewById(R.id.btn_permission_lazy).setOnClickListener(this);
        findViewById(R.id.btn_permission_hungry).setOnClickListener(this);
        findViewById(R.id.btn_contact_add).setOnClickListener(this);
        findViewById(R.id.btn_monitor_sms).setOnClickListener(this);
        findViewById(R.id.btn_send_mms).setOnClickListener(this);
        findViewById(R.id.btn_provider_mms).setOnClickListener(this);
        findViewById(R.id.btn_provider_apk).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_content_write: {
                Intent intent = new Intent(this, ContentWriteActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_permission_lazy: {
                Intent intent = new Intent(this, PermissionLazyActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_permission_hungry: {
                Intent intent = new Intent(this, PermissionHungryActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_contact_add: {
                Intent intent = new Intent(this, ContactAddActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_monitor_sms: {
                Intent intent = new Intent(this, MonitorSmsActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_send_mms: {
                Intent intent = new Intent(this, SendMmsActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_provider_mms: {
                Intent intent = new Intent(this, ProviderMmsActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_provider_apk: {
                Intent intent = new Intent(this, ProviderApkActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}