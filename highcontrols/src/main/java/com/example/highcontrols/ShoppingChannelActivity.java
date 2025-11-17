package com.example.highcontrols;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.highcontrols.adapter.GoodsAdapter;
import com.example.highcontrols.bean.GoodsInfo;
import com.example.highcontrols.database.ShoppingDBHelper;

import java.util.List;

public class ShoppingChannelActivity extends AppCompatActivity implements View.OnClickListener, GoodsAdapter.AddCartListener {

    // 声明一个商品数据库的帮助器对象
    private ShoppingDBHelper dbHelper;
    private TextView tv_count;
    private GridView gv_channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_channel);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("手机商场");

        tv_count = findViewById(R.id.tv_count);
        gv_channel = findViewById(R.id.gv_channel);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_cart).setOnClickListener(this);

        dbHelper = ShoppingDBHelper.getInstance(this);
        dbHelper.openWriteLink();
        dbHelper.openReadLink();

        // 从数据库查询出商品信息,并展示
        showGoods();
    }

    // 购物车返回列表会调用这个方法
    @Override
    protected void onResume() {
        super.onResume();
        showCartInfoTotal();
    }

    // 查询购物车商品总数,并展示
    private void showCartInfoTotal() {
        MyApplication.getInstance().goodsCount = dbHelper.getCartCount();
        tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));
    }

    private void showGoods() {
        // 查询商品数据库中的所有商品记录
        List<GoodsInfo> goodsInfos = dbHelper.queryGoodsInfos();
        GoodsAdapter adapter = new GoodsAdapter(this, goodsInfos, this);
        gv_channel.setAdapter(adapter);
    }

    @Override
    public void addCart(GoodsInfo info) {
        dbHelper.addCarInfo(info);
        int count = ++MyApplication.getInstance().goodsCount;
        tv_count.setText(String.valueOf(count));
        Toast.makeText(this, "加购成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.closeLink();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:{
                // 点击返回按钮, 关闭当前页面
                finish();
                break;
            }
            case R.id.iv_cart: {
                Intent intent = new Intent(this, ShoppingCartActivity.class);
                // 设置启动标志, 避免多次返回同一个页面
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            }
        }
    }
}