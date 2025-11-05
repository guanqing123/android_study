package com.example.datastore;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datastore.database.ShoppingDBHelper;
import com.example.datastore.entity.GoodsInfo;

import java.util.ArrayList;
import java.util.List;

public class ShoppingChannelActivity extends AppCompatActivity implements View.OnClickListener {

    // 声明一个商品数据库的帮助器对象
    private ShoppingDBHelper dbHelper;
    private TextView tv_count;
    private GridLayout gl_channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_channel);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("手机商场");

        tv_count = findViewById(R.id.tv_count);
        gl_channel = findViewById(R.id.gl_channel);
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
        // 商品条目是一个线性布局; 设置布局的宽度为屏幕宽度的一半
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(screenWidth / 2,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        // 查询商品数据库中的所有商品记录
        List<GoodsInfo> goodsInfos = dbHelper.queryGoodsInfos();

        // 移除下面的所有子视图
        gl_channel.removeAllViews();
       /* ArrayList<View> imageViews = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            goodsInfos.forEach(info -> {
                ImageView iv_pic = new ImageView(this);
                iv_pic.setImageBitmap(BitmapFactory.decodeFile(info.getPicPath()));
                imageViews.add(iv_pic);
            });
        } else {
            for (GoodsInfo info : goodsInfos) {
                ImageView iv_pic = new ImageView(this);
                iv_pic.setImageBitmap(BitmapFactory.decodeFile(info.getPicPath()));
                imageViews.add(iv_pic);
            }
        }
        gl_channel.removeAllViews();
        gl_channel.addChildrenForAccessibility(imageViews);*/
        for (GoodsInfo info : goodsInfos) {
            // 获取布局文件 item_goods.xml 的根视图
            View view = LayoutInflater.from(this).inflate(R.layout.item_goods, null);
            ImageView iv_thumb = view.findViewById(R.id.iv_thumb);
            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_price = view.findViewById(R.id.tv_price);
            // 设置商品图片、名称、价格
            iv_thumb.setImageBitmap(BitmapFactory.decodeFile(info.getPicPath()));
            tv_name.setText(info.getName());
            tv_price.setText("￥" + info.getPrice());

            // 商品图片点击
            iv_thumb.setOnClickListener(v -> {
                Intent intent = new Intent(ShoppingChannelActivity.this, ShoppingDetailActivity.class);
                intent.putExtra("goods_id", info.getId());
                startActivity(intent);
            });

            // 加入购物车按钮点击
            view.findViewById(R.id.btn_add).setOnClickListener(v -> {
                addCart(info);
            });

            gl_channel.addView(view, lp);
        }
    }

    private void addCart(GoodsInfo info) {
        dbHelper.addCarInfo(info);
        int count = ++MyApplication.getInstance().goodsCount;
        tv_count.setText(String.valueOf(count));
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