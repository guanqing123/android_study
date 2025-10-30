package com.example.middlecontrols;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class LoginForgetActivity extends AppCompatActivity implements View.OnClickListener {

    private String verifyCode;
    private String phone;
    private EditText et_new_password;
    private EditText et_new_password_again;
    private EditText et_verify_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forget);
        // 从上一个页面获取要修改密码的手机号码
        phone = getIntent().getStringExtra("phone");
        et_new_password = findViewById(R.id.et_new_password);
        et_new_password_again = findViewById(R.id.et_new_password_again);
        et_verify_code = findViewById(R.id.et_verify_code);

        findViewById(R.id.btn_get_verify_code).setOnClickListener(this);
        findViewById(R.id.btn_confirm).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_verify_code: {
                // 生成6位随机数字的验证码
                verifyCode = String.format("%06d", new Random().nextInt(999999));
                // 以下弹出提醒对话框, 提示用户记住6位验证码数字
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("请记住验证码")
                        .setMessage("手机号" + phone + ", 本次验证码是" + verifyCode + ", 请输入验证码")
                        .setPositiveButton("好的", null)
                        .create()
                        .show();
                break;
            }
            case R.id.btn_confirm: {
                // 点击确定按钮
                String firstPwd = et_new_password.getText().toString();
                String secondPwd = et_new_password_again.getText().toString();
                if (firstPwd.length() < 6) {
                    Toast.makeText(this, "密码长度不能少于6位", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!firstPwd.equals(secondPwd)) {
                    Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!verifyCode.equals(et_verify_code.getText().toString())) {
                    Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(this, "修改密码成功", Toast.LENGTH_SHORT).show();
                // 以下把修改好的新密码返回给上一个页面
                Intent intent = new Intent();
                intent.putExtra("new_password", firstPwd);
                setResult(RESULT_OK, intent);
                finish();
                break;
            }
        }
    }
}