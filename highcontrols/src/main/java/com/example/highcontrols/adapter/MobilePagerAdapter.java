package com.example.highcontrols.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.highcontrols.bean.GoodsInfo;
import com.example.highcontrols.fragment.DynamicFragment;

import java.util.List;

public class MobilePagerAdapter extends FragmentPagerAdapter {

    private final List<GoodsInfo> goodsList;

    public MobilePagerAdapter(@NonNull FragmentManager fm, List<GoodsInfo> goodsList) {
        // 会将当前fragment设置为Resume的状态，把上个fragment设置成Start的状态。
        // 从而可以通过fragment的onResume()来懒加载数据。
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.goodsList = goodsList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        GoodsInfo info = goodsList.get(position);
        return DynamicFragment.newInstance(position, info.getPic(), info.getDescription());
    }

    @Override
    public int getCount() {
        return goodsList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return goodsList.get(position).getName();
    }
}
