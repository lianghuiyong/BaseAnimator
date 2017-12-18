package com.better.baseanimator;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;

import com.better.baseanimator.base.BaseActivity;
import com.better.baseanimator.base.BaseViewpagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2017, by Better, All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: HomePageActivity.java
 * Author: lianghuiyong@outlook.com
 * Create: 2017/12/18 上午10:49
 *
 * Changes (from 2017/12/18)
 * -----------------------------------------------------------------
 * 2017/12/18 : Create HomePageActivity.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public class HomePageActivity extends BaseActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @Override
    public int setViewId() {
        return R.layout.activity_home_page;
    }

    @Override
    public void initData() {
        BaseViewpagerAdapter viewpagerAdapter = new BaseViewpagerAdapter(getSupportFragmentManager());
        viewpagerAdapter.addFragment(new EvaluatorFragment());  // Evaluator
        viewpagerAdapter.addFragment(new Dynamic2DFragment());  // 动效2D
        viewpagerAdapter.addFragment(new Dynamic3DFragment());  // 动效3D
        viewPager.setAdapter(viewpagerAdapter);
        viewPager.setOffscreenPageLimit(2);

        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navigation.setSelectedItemId(navigation.getMenu().getItem(position).getItemId());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
