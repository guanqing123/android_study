package com.example.client;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SendMmsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_appendix;
    private EditText et_phone;
    private EditText et_title;
    private EditText et_content;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mms);

        et_phone = findViewById(R.id.et_phone);
        et_title = findViewById(R.id.et_title);
        et_content = findViewById(R.id.et_content);

        iv_appendix = findViewById(R.id.iv_appendix);
        iv_appendix.setOnClickListener(this);
        findViewById(R.id.btn_send_mms).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_appendix: {
                // 跳转到系统相册,选择图片,并返回
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                // 设置内容类型为图片类型
                intent.setType("image/*");
                startActivityForResult(intent, 1);
                break;
            }
            case R.id.btn_send_mms: {
                // 发送带图片的彩信
                sendMms(et_phone.getText().toString(),
                        et_title.getText().toString(),
                        et_content.getText().toString());
                break;
            }
        }
    }

    // 发送带图片的彩信
    private void sendMms(String phone, String title, String content) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // 获取图片的uri
            uri = data.getData();
            iv_appendix.setImageURI(uri);
        }
    }
}