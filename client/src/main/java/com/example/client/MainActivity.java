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
        }
    }
}