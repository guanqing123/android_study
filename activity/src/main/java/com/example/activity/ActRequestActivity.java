package com.example.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class ActRequestActivity extends AppCompatActivity implements View.OnClickListener {

    private String mRequest = "你睡了吗？来我家睡吧";
    private TextView tv_response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_request);

        TextView tv_request = findViewById(R.id.tv_request);
        tv_request.setText("待发送的消息为:" + mRequest);

        findViewById(R.id.btn_request).setOnClickListener(this);

        tv_response = findViewById(R.id.tv_response);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ActResponseActivity.class);
        // 创建Bundle对象
        Bundle bundle = new Bundle();
        bundle.putString("request_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
        bundle.putString("request_content", mRequest);
        intent.putExtras(bundle);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String response_time = bundle.getString("response_time");
            String response_content = bundle.getString("response_content");
            tv_response.setText("收到消息为:" + response_time + ":" + response_content);
        }
    }
}