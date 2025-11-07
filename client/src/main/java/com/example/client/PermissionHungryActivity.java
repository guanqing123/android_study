package com.example.client;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.client.util.PermissionUtil;

public class PermissionHungryActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String[] permissions = new String[]{
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS
    };

    private static final int REQUEST_CODE_ALL = 1;
    private static final int REQUEST_CODE_CONTACTS = 2;
    private static final int REQUEST_CODE_SMS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_hungry);

        PermissionUtil.checkPermissions(this, permissions, REQUEST_CODE_ALL);

        findViewById(R.id.btn_contact).setOnClickListener(this);
        findViewById(R.id.btn_sms).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_contact:{
                PermissionUtil.checkPermissions(this, new String[]{permissions[0], permissions[1]}, REQUEST_CODE_CONTACTS);
                break;
            }
            case R.id.btn_sms:{
                PermissionUtil.checkPermissions(this, new String[]{permissions[2], permissions[3]}, REQUEST_CODE_SMS);
                break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_ALL:{
                if (PermissionUtil.checkGrant(grantResults)) {
                    // 获取权限成功
                    Log.d("gq", "所有权限获取成功");
                } else {
                    // 部分权限获取失败
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            // 判断是什么权限没有获取成功
                            switch (permissions[i]) {
                                case Manifest.permission.READ_CONTACTS:
                                case Manifest.permission.WRITE_CONTACTS: {
                                    // 通讯录权限没有获取成功
                                    Toast.makeText(this, "获取通信录读写权限失败", Toast.LENGTH_SHORT).show();
                                    goToAppSetting();
                                    break;
                                }
                                case Manifest.permission.READ_SMS:
                                case Manifest.permission.SEND_SMS: {
                                    // 通讯录权限没有获取成功
                                    Toast.makeText(this, "获取短信读写权限失败", Toast.LENGTH_SHORT).show();
                                    goToAppSetting();
                                    break;
                                }
                            }
                        }
                    }
                }
                break;
            }
            case REQUEST_CODE_CONTACTS:{
                if (PermissionUtil.checkGrant(grantResults)) {
                    // 获取权限成功
                    Log.d("gq", "通讯录权限获取成功");
                } else {
                    Toast.makeText(this, "获取通信录读写权限失败", Toast.LENGTH_SHORT).show();
                    goToAppSetting();
                }
                break;
            }
            case REQUEST_CODE_SMS:{
                if (PermissionUtil.checkGrant(grantResults)) {
                    // 获取权限成功
                    Log.d("gq", "短信权限获取成功");
                } else {
                    Toast.makeText(this, "获取短信读写权限失败", Toast.LENGTH_SHORT).show();
                    goToAppSetting();
                }
                break;
            }
        }
    }

    private void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}