package com.example.datastore;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.datastore.database.ShoppingDBHelper;
import com.example.datastore.entity.CartInfo;
import com.example.datastore.entity.GoodsInfo;

import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity {

    private TextView tv_count;
    private LinearLayout ll_cart;
    private ShoppingDBHelper mDBHelper;
    private List<CartInfo> cartInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("购物车");
        ll_cart = findViewById(R.id.ll_cart);

        tv_count = findViewById(R.id.tv_count);
        tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));

        mDBHelper = ShoppingDBHelper.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showCart();
    }

    // 展示购物车中的商品
    private void showCart() {
        // 移除下面的所有子视图
        ll_cart.removeAllViews();
        // 查询购物车数据库中所有的商品记录
        cartInfos = mDBHelper.queryAllCartInfo();
        if (cartInfos.size() == 0) {
            return;
        }

        for (CartInfo cartInfo : cartInfos) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_cart, null);
            ImageView iv_thumb = view.findViewById(R.id.iv_thumb);
            iv_thumb.setImageURI(Uri.parse(cartInfo.getGoodsInfo().getPicPath()));
            TextView tv_name = view.findViewById(R.id.tv_name);
            tv_name.setText(cartInfo.getGoodsInfo().getName());
            TextView tv_desc = view.findViewById(R.id.tv_desc);
            tv_desc.setText(cartInfo.getGoodsInfo().getDescription());
            TextView tv_count = view.findViewById(R.id.tv_count);
            tv_count.setText("x" + cartInfo.getCount());
            TextView tv_price = view.findViewById(R.id.tv_price);
            tv_price.setText(cartInfo.getGoodsInfo().getPrice()+"");
            TextView tv_sum = view.findViewById(R.id.tv_sum);
            tv_sum.setText(cartInfo.getGoodsInfo().getPrice() * cartInfo.getCount()+"");
            ll_cart.addView(view);
        }
    }
}