package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class ActResponseActivity extends AppCompatActivity implements View.OnClickListener {

    private String mResposne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_response);

        Bundle bundle = getIntent().getExtras();
        String request_time = bundle.getString("request_time");
        String request_content = bundle.getString("request_content");
        String desc = "请求时间：" + request_time + "\n" + "请求内容：" + request_content;
        TextView tv_request = findViewById(R.id.tv_request);
        tv_request.setText(desc);

        findViewById(R.id.btn_response).setOnClickListener(this);

        TextView tv_response = findViewById(R.id.tv_response);
        mResposne = "我还没睡，我家里没人";
        tv_response.setText("回复的信息为: " + mResposne);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("response_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
        bundle.putString("response_content", mResposne);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}