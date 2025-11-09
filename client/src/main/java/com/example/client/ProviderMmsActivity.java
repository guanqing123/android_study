package com.example.client;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.client.entity.ImageInfo;
import com.example.client.util.FileUtil;
import com.example.client.util.PermissionUtil;
import com.example.client.util.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProviderMmsActivity extends AppCompatActivity {

    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private static final int REQUEST_CODE_PERMISSION = 1;

    private List<ImageInfo> imageList = new ArrayList<>();
    private GridLayout gl_appendix;
    private EditText et_phone;
    private EditText et_title;
    private EditText et_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_mms);

        gl_appendix = findViewById(R.id.gl_appendix);

        et_phone = findViewById(R.id.et_phone);
        et_title = findViewById(R.id.et_title);
        et_content = findViewById(R.id.et_content);

        // 手动让MediaStore扫描入库（因为我们在目录下上传图片后，MediaStore不会自动扫描入库）
        MediaScannerConnection.scanFile(this,
                new String[]{
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(),
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()},
                null, (path, uri) -> {
                    // 涉及不在主线程刷新视图的问题
                    /*if (PermissionUtil.checkPermissions(this, PERMISSIONS, REQUEST_CODE_PERMISSION)) {
                        // 加载图片列表
                        loadImageList();
                        // 显示图片网格
                        showImageGrid();
                    }*/
                });

        if (PermissionUtil.checkPermissions(this, PERMISSIONS, REQUEST_CODE_PERMISSION)) {
            // 加载图片列表
            loadImageList();
            // 显示图片网格
            showImageGrid();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION: { // 外部读取
                if (PermissionUtil.checkGrant(grantResults)) {// 获取权限成功
                    // 加载图片列表
                    loadImageList();
                    // 显示图片网格
                    showImageGrid();
                } else {
                    // 获取权限失败
                    Toast.makeText(this, "获取存储卡去读权限失败!", Toast.LENGTH_SHORT).show();
                    goToAppSetting();
                }
                break;
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

    // 显示图片网格
    private void showImageGrid() {
        gl_appendix.removeAllViews();
        for (ImageInfo image : imageList) {
            // image -> ImageView
            ImageView iv_appendix = new ImageView(this);
            Bitmap bitmap = BitmapFactory.decodeFile(image.path);
            // image.path=/storage/emulated/0/Download/IMG_0037.jpg
            Log.d("gq", "image.path=" + image.path);
            iv_appendix.setImageBitmap(bitmap);
            // 设置图像视图的缩放类型
            iv_appendix.setScaleType(ImageView.ScaleType.FIT_CENTER);
            // 设置图像视图的布局参数
            // 屏幕宽度除以3
            int screenWidth = getResources().getDisplayMetrics().widthPixels;
            int px = screenWidth / 3;
            //int px = Utils.dip2px(this, 110);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(px, px);
            iv_appendix.setLayoutParams(params);
            // 设置图像视图的内部间距
            int padding = Utils.dip2px(this, 5);
            iv_appendix.setPadding(padding, padding, padding, padding);
            iv_appendix.setOnClickListener(v -> {
                sendMms(et_phone.getText().toString(),
                        et_title.getText().toString(),
                        et_content.getText().toString(),
                        image.path);
            });
            // 把图像视图添加到网格布局中
            gl_appendix.addView(iv_appendix);
        }
    }

    // 发送带图片的彩信
    private void sendMms(String phone, String title, String content, String path) {
        // 根据指定路径创建一个Uri对象
        Uri uri = Uri.parse(path);
        // 兼容Android 7.0，把访问文件的Uri方式改为FileProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 通过FileProvider获得文件的Uri访问方式
            uri = FileProvider.getUriForFile(this, getString(R.string.file_provider), new File(path));
            Log.d("gq", String.format("new uri=%s", uri.toString()));
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Intent 的接受者将被准许读取Intent 携带的URI数据
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 彩信发送的目标号码
        intent.putExtra("address", phone);
        // 彩信的标题
        intent.putExtra("subject", title);
        // 彩信的内容
        intent.putExtra("sms_body", content);
        // 彩信的图片附件
        intent.putExtra(Intent.EXTRA_STREAM,  uri);
        // 彩信的附件为图片
        intent.setType("image/*");
        // 因为未指定要打开哪个页面,所以系统会在底部弹出选择窗口
        startActivity(intent);
        Toast.makeText(this, "请在弹窗中选择短信或者信息应用", Toast.LENGTH_SHORT).show();
    }

    // 加载图片列表
    private void loadImageList() {
        // MediaStore
        String[] columns = new String[]{
                MediaStore.Images.Media._ID, // 图片编号
                MediaStore.Images.Media.TITLE, // 图片标题
                MediaStore.Images.Media.SIZE, // 图片大小
                MediaStore.Images.Media.DATA // 图片路径
        };
        // 图片大小在300KB以内
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                columns,
                MediaStore.Images.Media.SIZE + " < ?",
                new String[]{300 * 1024 * 1024 + ""},
                "_size DESC");
        if (cursor != null) {
            imageList.clear();
            int count = 0;
            while (cursor.moveToNext() && count < 9) {
                ImageInfo image = new ImageInfo();
                image.id = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                image.title = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.TITLE));
                image.size = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
                image.path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                if (FileUtil.checkFileUri(this, image.path)) {
                    count++;
                    imageList.add(image);
                }
            }
        }
    }
}