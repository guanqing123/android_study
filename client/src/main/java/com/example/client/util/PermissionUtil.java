package com.example.client.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtil {

    // 检查多个权限。返回true表示已完全启用权限,返回false表示未完全启用权限
    public static boolean checkPermissions(Activity activity, String[] permissions, int requestCode) {
        // Android6.0之后开始采用动态权限管理
        /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int check = PackageManager.PERMISSION_GRANTED;
            for (String permission : permissions) {
                check = ContextCompat.checkSelfPermission(activity, permission);
                if (check != PackageManager.PERMISSION_GRANTED) {
                    break;
                }
            }
            // 未开启该权限,则请求系统弹窗,好让用户选择是否立即开启权限
            if (check != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, permissions, requestCode);
                return false;
            }
        }
        return true;
    }

    // 检查权限请求结果, 返回true表示已完全启用权限,返回false表示未完全启用权限
    public static boolean checkGrant(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
