package com.example.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.client.util.PermissionUtil;

public class PermissionLazyActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String[] PERMISSIONS_CONTACTS = new String[]{
            Manifest.permission.READ_CONTACTS, // 通讯录的读权限
            Manifest.permission.WRITE_CONTACTS // 通讯录的写权限
    };

    private static final String[] PERMISSIONS_SMS = new String[]{
            Manifest.permission.READ_SMS, // 短信的读权限
            Manifest.permission.SEND_SMS // 短信的写权限
    };
    private static final int REQUEST_CODE_CONTACTS = 1;
    private static final int REQUEST_CODE_SMS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_lazy);

        findViewById(R.id.btn_contact).setOnClickListener(this);
        findViewById(R.id.btn_sms).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_contact:{
                PermissionUtil.checkPermissions(this, PERMISSIONS_CONTACTS, REQUEST_CODE_CONTACTS);
                break;
            }
            case R.id.btn_sms:{
                PermissionUtil.checkPermissions(this, PERMISSIONS_SMS, REQUEST_CODE_SMS);
                break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_CONTACTS: { // 通讯录权限
                if (PermissionUtil.checkGrant(grantResults)) {
                    // 获取权限成功
                    Log.d("gq", "通讯录权限获取成功");
                } else {
                    // 获取权限失败
                    Toast.makeText(this, "获取通讯录读写权限失败!", Toast.LENGTH_SHORT).show();
                    goToAppSetting();
                }
                break;
            }
            case REQUEST_CODE_SMS: { // 短信权限
                if (PermissionUtil.checkGrant(grantResults)) {
                    // 获取权限成功
                    Log.d("gq", "短信权限获取成功");
                } else {
                    // 获取
                    Toast.makeText(this, "获取短信读写权限失败!", Toast.LENGTH_SHORT).show();
                    goToAppSetting();
                }
            }
        }
    }

    // 跳转到应用设置页面
    private void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}