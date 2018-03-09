package com.better.animator;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.better.animator.base.BaseFragment;
import com.better.animator.view.DemoFragment;
import com.better.anime.dynamic2d.tablayout.SmartTabLayout;
import com.better.anime.dynamic2d.tablayout.v4.FragmentPagerItem;
import com.better.anime.dynamic2d.tablayout.v4.FragmentPagerItemAdapter;
import com.better.anime.dynamic2d.tablayout.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2017, by Better, All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: Dynamic3DFragment.java
 * Author: lianghuiyong@outlook.com
 * Create: 2017/12/18 上午10:49
 *
 * Changes (from 2017/12/18)
 * -----------------------------------------------------------------
 * 2017/12/18 : Create Dynamic3DFragment.java (梁惠涌);
 * -----------------------------------------------------------------
 */
public class Dynamic3DFragment extends BaseFragment {

    @BindView(R.id.viewpagertab1)
    SmartTabLayout viewpagertab1;
    @BindView(R.id.viewpagertab2)
    SmartTabLayout viewpagertab2;
    @BindView(R.id.viewpagertab3)
    SmartTabLayout viewpagertab3;
    @BindView(R.id.viewpagertab4)
    SmartTabLayout viewpagertab4;
    @BindView(R.id.viewpagertab5)
    SmartTabLayout viewpagertab5;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    public int setViewId() {
        return R.layout.tablayout_test;
    }

    @Override
    public void initData() {

        FragmentPagerItems pages = new FragmentPagerItems(getContext());
        for (int i = 1; i < 5; i++) {
            pages.add(FragmentPagerItem.of(i + "", DemoFragment.class));
        }
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getChildFragmentManager(), pages);

        viewpager.setAdapter(adapter);
        viewpagertab1.setViewPager(viewpager);
        viewpagertab2.setViewPager(viewpager);
        viewpagertab3.setViewPager(viewpager);
        viewpagertab4.setViewPager(viewpager);
        viewpagertab5.setViewPager(viewpager);
    }
}
