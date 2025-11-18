package com.example.highcontrols.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.highcontrols.fragment.LaunchFragment;

public class LaunchImproveAdapter extends FragmentPagerAdapter {

    private final int[] launchImageArray;

    public LaunchImproveAdapter(@NonNull FragmentManager fm, int[] launchImageArray) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.launchImageArray = launchImageArray;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return LaunchFragment.newInstance(position, launchImageArray.length, launchImageArray[position]);
    }

    @Override
    public int getCount() {
        return launchImageArray.length;
    }
}
