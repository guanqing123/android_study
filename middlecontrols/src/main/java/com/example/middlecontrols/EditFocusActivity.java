package com.example.middlecontrols;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class EditFocusActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    private EditText et_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_focus);

        et_phone = findViewById(R.id.et_phone);
        et_phone.addTextChangedListener(new HideTextWatcher(et_phone, 11));
        EditText et_password = findViewById(R.id.et_password);
        et_password.setOnFocusChangeListener(this);
        et_password.addTextChangedListener(new HideTextWatcher(et_password, 6));
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            String phone = et_phone.getText().toString();
            if (TextUtils.isEmpty(phone) || phone.length() < 11) {
                // 手机号码框编辑请求焦点,也就是把焦点移动到手机号码框
                et_phone.requestFocus();
                Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
            }
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
                hideKeyboard(EditFocusActivity.this, et);
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