package com.example.middlecontrols;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class LoginMainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private TextView tv_password;
    private EditText et_password;
    private Button btn_forget;
    private CheckBox ck_remember;
    private EditText et_phone;
    private RadioButton rb_password;
    private RadioButton rb_verify_code;
    private String verifyCode;
    private String password = "111111";
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        // 密码登录和验证码登录 单选框
        RadioGroup rg_login = findViewById(R.id.rg_login);
        rb_password = findViewById(R.id.rb_password);
        rb_verify_code = findViewById(R.id.rb_verify_code);

        et_phone = findViewById(R.id.et_phone);
        tv_password = findViewById(R.id.tv_password);
        et_password = findViewById(R.id.et_password);
        btn_forget = findViewById(R.id.btn_forget);
        ck_remember = findViewById(R.id.ck_remember);
        // 给rg_login设置单选监听器
        rg_login.setOnCheckedChangeListener(this);
        // 给et_phone,et_password设置文本监听器
        et_phone.addTextChangedListener(new HideTextWatcher(et_phone, 11));
        et_password.addTextChangedListener(new HideTextWatcher(et_password, 6));
        // 忘记密码
        btn_forget.setOnClickListener(this);
        // 登录
        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        // 保存登录信息
        preferences = getSharedPreferences("rememberMe", Context.MODE_PRIVATE);
        loadRememberMe();
    }

    private void loadRememberMe() {
        boolean remember = preferences.getBoolean("remember", false);
        if (remember) {
            String phone = preferences.getString("phone", "");
            if (!phone.isEmpty()) et_phone.setText(phone);
            String password = preferences.getString("password", "");
            if (!password.isEmpty()) et_password.setText(password);
            ck_remember.setChecked(true);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            // 选择了密码登录
            case R.id.rb_password: {
                tv_password.setText("登录密码：");
                et_password.setHint("请输入密码");
                btn_forget.setText("忘记密码");
                ck_remember.setVisibility(View.VISIBLE);
                break;
            }
            // 选择了验证码登录
            case R.id.rb_verify_code: {
                tv_password.setText("    验证码：");
                et_password.setHint("请输入验证码");
                btn_forget.setText("获取验证码");
                ck_remember.setVisibility(View.GONE);
                break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        String phone = et_phone.getText().toString();
        // 忘记密码
        if (phone.length() != 11) {
            Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (v.getId()) {
            case R.id.btn_forget: {
                // 选择了密码方式校验,此时要跳转到找到回密码的界面
                if (rb_password.isChecked()) {
                    // 以下携带手机号码跳转到找回密码的界面
                    Intent intent = new Intent(LoginMainActivity.this, LoginForgetActivity.class);
                    intent.putExtra("phone", phone);
                    startActivityForResult(intent, 1);
                } else if (rb_verify_code.isChecked()) {
                    // 生成6位随机数字的验证码
                    verifyCode = String.format("%06d", new Random().nextInt(999999));
                    // 以下弹出提醒对话框, 提示用户记住6位验证码数字
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("请记住验证码")
                            .setMessage("手机号" + phone + ", 本次验证码是" + verifyCode + ", 请输入验证码")
                            .setPositiveButton("好的", null)
                            .create()
                            .show();
                }
                break;
            }
            case R.id.btn_login: {
                // 密码方式校验
                if (rb_password.isChecked()) {
                    if (!password.equals(et_password.getText().toString())) {
                        Toast.makeText(this, "请输入正确的密码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // 是否密码登录 且记住密码
                    if (ck_remember.isChecked()) {
                        SharedPreferences.Editor edit = preferences.edit();
                        edit.putString("phone", phone);
                        edit.putString("password", et_password.getText().toString());
                        edit.putBoolean("remember",ck_remember.isChecked());
                        edit.commit();
                    }
                    // 提示用户登录成功
                    loginSuccess();
                } else if (rb_verify_code.isChecked()) {
                    // 验证码方式校验
                    if (!verifyCode.equals(et_password.getText().toString())) {
                        Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // 提示用户登录成功
                    loginSuccess();
                }
                break;
            }
        }
    }

    // 登录成功
    private void loginSuccess() {
        String desc = String.format("您的手机号码是%s,恭喜你通过登录验证,点击 '确定' 按钮返回上个页面", et_phone.getText().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("登录成功")
                .setMessage(desc)
                .setPositiveButton("确定返回", (dialog, which) -> {
                    // 结束当前的活动页面
                    finish();
                })
                .setNegativeButton("我再看看", null)
                .create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // 获取从找回密码的界面传递过来的手机号码
            String new_password = data.getStringExtra("new_password");
            password = new_password;
        }
    }

    private class HideTextWatcher implements TextWatcher {
        private EditText et;
        private int maxLength;
        public HideTextWatcher(EditText et, int maxLength) {
            this.et = et;
            this.maxLength = maxLength;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == maxLength) {
                // 隐藏输入法
                hideKeyboard(LoginMainActivity.this, et);
            }
        }
    }

    public static void hideKeyboard(Activity act, View v) {
        // 从系统服务中获取输入法管理器
        InputMethodManager imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 关闭屏幕上的输入法软键盘
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}