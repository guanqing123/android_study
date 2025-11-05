package com.example.datastore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datastore.database.ShoppingDBHelper;
import com.example.datastore.entity.GoodsInfo;

public class ShoppingDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_title;
    private TextView tv_count;
    private ImageView iv_goods_pic;
    private TextView tv_goods_price;
    private TextView tv_goods_desc;
    private ShoppingDBHelper dbHelper;
    private GoodsInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_detail);

        tv_title = findViewById(R.id.tv_title);
        tv_count = findViewById(R.id.tv_count);
        iv_goods_pic = findViewById(R.id.iv_goods_pic);
        tv_goods_price = findViewById(R.id.tv_goods_price);
        tv_goods_desc = findViewById(R.id.tv_goods_desc);

        dbHelper = ShoppingDBHelper.getInstance(this);

        // 购物车商品数量
        tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_cart).setOnClickListener(this);
        findViewById(R.id.btn_add_cart).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showDetail();
    }

    private void showDetail() {
        // 获取上一个页面传来的商品编号
        int goods_id = getIntent().getIntExtra("goods_id", 0);
        info = dbHelper.getGoodsInfo(goods_id);
        tv_title.setText(info.getName());
        tv_goods_price.setText("￥" + info.getPrice());
        tv_goods_desc.setText(info.getDescription());
        iv_goods_pic.setImageURI(Uri.parse(info.getPicPath()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back: {
                // 点击返回按钮, 跳转到商品频道页面
                finish();
                break;
            }
            case R.id.iv_cart: {
                // 点击购物车按钮, 跳转到购物车页面
                Intent intent = new Intent(this, ShoppingCartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            }
            case R.id.btn_add_cart: {
                // 添加购物车
                dbHelper.addCarInfo(info);
                int count = ++MyApplication.getInstance().goodsCount;
                tv_count.setText(String.valueOf(count));
                Toast.makeText(this, "加购成功", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}