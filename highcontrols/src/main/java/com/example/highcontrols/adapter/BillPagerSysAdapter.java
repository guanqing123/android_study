package com.example.highcontrols.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.highcontrols.fragment.BillPagerSysFragment;

public class BillPagerSysAdapter extends FragmentPagerAdapter {

    private final int year;

    public BillPagerSysAdapter(@NonNull FragmentManager fm, int year) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.year = year;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        String yearMonth = String.format("%d-%02d", year, position + 1);
        return BillPagerSysFragment.newInstance(yearMonth);
    }

    @Override
    public int getCount() {
        return 12;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return (position + 1) + "月份";
    }
}
