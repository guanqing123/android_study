package com.example.datastore;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.datastore.database.ShoppingDBHelper;
import com.example.datastore.entity.CartInfo;

import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_count;
    private LinearLayout ll_cart;
    private ShoppingDBHelper mDBHelper;
    private List<CartInfo> cartInfos;
    private TextView tv_total_price;
    private LinearLayout ll_content;
    private LinearLayout ll_empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("购物车");
        tv_total_price = findViewById(R.id.tv_total_price);
        ll_cart = findViewById(R.id.ll_cart);
        ll_content = findViewById(R.id.ll_content);
        ll_empty = findViewById(R.id.ll_empty);

        tv_count = findViewById(R.id.tv_count);
        showCount();

        mDBHelper = ShoppingDBHelper.getInstance(this);

        // 返回
        findViewById(R.id.iv_back).setOnClickListener(this);
        // 逛逛手机商场
        findViewById(R.id.btn_shopping_channel).setOnClickListener(this);
        // 清空
        findViewById(R.id.btn_clear).setOnClickListener(this);
        // 结算
        findViewById(R.id.btn_settle).setOnClickListener(this);
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
            // 设置商品总价
            tv_sum.setText(cartInfo.getGoodsInfo().getPrice() * cartInfo.getCount()+"");

            // 给商品行添加长按事件。长按商品行就删除该商品
            view.setOnLongClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingCartActivity.this);
                builder.setTitle("是否从购物车删除" + cartInfo.getGoodsInfo().getName() + "？")
                        .setPositiveButton("确定", (dialog, which) -> {
                            // 移除当前视图
                            //ll_cart.removeView(v);
                            // 删除该商品
                            deleteGoods(cartInfo);
                        })
                        .setNegativeButton("取消", null)
                        .create()
                        .show();
                return true;
            });

            // 给商品行添加点击事件。点击商品行就跳转到商品详情页面
            view.setOnClickListener(v -> {
                Intent intent = new Intent(ShoppingCartActivity.this, ShoppingDetailActivity.class);
                intent.putExtra("goods_id", cartInfo.getGoodsId());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            });

            // 添加到购物车
            ll_cart.addView(view);
        }

        // 重新计算购物车的商品总金额
        refreshTotalPrice();
    }

    private void deleteGoods(CartInfo cartInfo) {
        MyApplication.getInstance().goodsCount -= cartInfo.getCount();
        // 从购物车的数据库中删除该商品
        mDBHelper.deleteGoods(cartInfo.getGoodsId());
        // 显示最新的商品数量
        showCount();
        // 刷新购物车
        showCart();
        Toast.makeText(this, "已从购物车中删除" + cartInfo.getGoodsInfo().getName(), Toast.LENGTH_SHORT).show();
    }

    private void showCount() {
        tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));
        // 购物车中没有商品,显示"空空如也"
        if (MyApplication.getInstance().goodsCount == 0) {
            ll_empty.setVisibility(View.VISIBLE);
            ll_content.setVisibility(View.GONE);
        } else {
            ll_empty.setVisibility(View.GONE);
            ll_content.setVisibility(View.VISIBLE);
        }
    }

    private void refreshTotalPrice() {
        // 计算商品总金额 getPrice()*getCount() 的和
        double sum = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
             sum = cartInfos.stream().mapToDouble(cartInfo -> cartInfo.getGoodsInfo().getPrice() * cartInfo.getCount()).sum();
        } else {
            for (CartInfo cartInfo : cartInfos) {
                sum += cartInfo.getGoodsInfo().getPrice() * cartInfo.getCount();
            }
        }
        tv_total_price.setText(String.valueOf(sum));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:{
                finish();
                break;
            }
            case R.id.btn_shopping_channel:{
                // 从购物车跳转商品列表
                Intent intent = new Intent(this, ShoppingChannelActivity.class);
                // 避免重复跳转
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            }
            case R.id.btn_clear:{
                // 清空购物车
                mDBHelper.clearCart();
                MyApplication.getInstance().goodsCount = 0;
                // 显示最新的商品数量
                showCount();
                // 刷新购物车
                showCart();
                Toast.makeText(this, "已清空购物车", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.btn_settle:{
                // 结算
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("结算商品")
                        .setMessage("客官抱歉,支付功能尚未开通,请下次再来")
                        .setPositiveButton("我知道了", null)
                        .create()
                        .show();
                break;
            }
        }
    }
}