package com.snow.night.googleemarket.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.snow.night.googleemarket.base.BaseFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/4/13.
 */
public class MainAdapterSelf extends FragmentPagerAdapter {
    private List<BaseFragment>  fragments;
    public MainAdapterSelf(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        BaseFragment baseFragment = fragments.get(position);
        return baseFragment.getTitle();
    }
}
