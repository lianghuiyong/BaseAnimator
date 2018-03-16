package com.better.animator.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import com.better.animator.Dynamic2DFragment;
import com.better.animator.Dynamic3DFragment;
import com.better.animator.InterpolatorFragment;
import com.better.animator.R;
import com.better.animator.base.BaseActivity;
import com.better.animator.base.BaseViewpagerAdapter;
import com.better.animator.utils.ImmersionBar;
import com.better.animator.utils.SoftHideKeyBoardUtil;
import com.better.animator.widget.BaseStatusBar;

import butterknife.BindView;

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
    @BindView(R.id.status_bar)
    BaseStatusBar statusBar;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    private long exitTime = 0;  // 双击返回键退出

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        overridePendingTransition(R.anim.activity_splash_enter,R.anim.activity_splash_exit);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setViewId() {
        return R.layout.activity_home_page;
    }

    @Override
    public void initData() {

        SoftHideKeyBoardUtil.assistActivity(viewPager);

        statusBar.setStatusBarDark(this);

        BaseViewpagerAdapter viewpagerAdapter = new BaseViewpagerAdapter(getSupportFragmentManager());
        viewpagerAdapter.addFragment(new InterpolatorFragment());   // Interpolator
        viewpagerAdapter.addFragment(new Dynamic2DFragment());      // 动效2D
        viewpagerAdapter.addFragment(new Dynamic3DFragment());      // 动效3D
        viewPager.setAdapter(viewpagerAdapter);
        viewPager.setOffscreenPageLimit(2);

        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_1:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_2:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_3:
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime > 1000)) {
                exitTime = System.currentTimeMillis();
            } else {


                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
