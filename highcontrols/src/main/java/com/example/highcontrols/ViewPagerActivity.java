package com.example.highcontrols;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import com.example.highcontrols.adapter.ImagePagerAdapter;
import com.example.highcontrols.bean.GoodsInfo;

import java.util.List;

public class ViewPagerActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private List<GoodsInfo> goodsInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        ViewPager vp_content = findViewById(R.id.vp_content);
        goodsInfos = GoodsInfo.getDefaultList();
        ImagePagerAdapter adapter = new ImagePagerAdapter(this, goodsInfos);
        vp_content.setAdapter(adapter);
        // 给翻页视图添加页面变更监听器
        vp_content.addOnPageChangeListener(this);
    }

    // 翻页状态改变时触发.state取值说明为: 0表示静止,1:表示正在滑动,2:表示滑动完毕
    // 在翻页过程中,状态值变化依次为: 正在滑动->滑动完毕->静止
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    // 在翻页过程中触发。该方法的三个参数取值说明为：第一个参数表示当前页面的序号
    // 第二个参数表示页面偏移的百分比，取值为0到1；第三个参数表示页面的偏移距离
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    // 在翻页结束后触发。position表示当前滑到了哪一个页面
    @Override
    public void onPageSelected(int position) {
        Toast.makeText(this, "您翻到的手机品牌是: " + goodsInfos.get(position).getName(), Toast.LENGTH_SHORT).show();
    }
}