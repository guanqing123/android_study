package com.example.datastore;

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

public class ShoppingChannelActivity extends AppCompatActivity {

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

        dbHelper = ShoppingDBHelper.getInstance(this);
        dbHelper.openWriteLink();
        dbHelper.openReadLink();

        // 从数据库查询出商品信息,并展示
        showGoods();

    }

    private void showGoods() {
        List<GoodsInfo> goodsInfos = dbHelper.queryGoodsInfos();
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(screenWidth / 2,
                LinearLayout.LayoutParams.WRAP_CONTENT);
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

            gl_channel.addView(view, lp);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.closeLink();
    }
}