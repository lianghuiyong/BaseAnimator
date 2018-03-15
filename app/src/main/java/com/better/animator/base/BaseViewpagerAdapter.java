package com.better.animator.base;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2017, by Better, All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: BaseViewpagerAdapter.java
 * Author: lianghuiyong@outlook.com
 * Create: 2017/12/18 上午10:49
 *
 * Changes (from 2017/12/18)
 * -----------------------------------------------------------------
 * 2017/12/18 : Create BaseViewpagerAdapter.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public class BaseViewpagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragments;

    public BaseViewpagerAdapter(FragmentManager fm) {
        super(fm);
        this.fragments = new ArrayList<>();
    }

    public void addFragment(BaseFragment fragment) {
        fragments.add(fragment);
    }

    @Override
    public BaseFragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
