package com.better.animator;

import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.better.animator.base.BaseFragment;
import com.better.animator.base.BaseViewpagerAdapter;
import com.better.animator.view.DemoFragment;
import com.better.anime.dynamic2d.tablayout.SmartTabLayout;

import butterknife.BindView;

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

    int pageSize = 5;

    @Override
    public int setViewId() {
        return R.layout.tablayout_test;
    }

    @Override
    public void initData() {


        BaseViewpagerAdapter adapter = new BaseViewpagerAdapter(getChildFragmentManager());
        for (int i = 0; i < pageSize; i++) {
            adapter.addFragment(new DemoFragment());
        }


        viewpager.setAdapter(adapter);
        viewpagertab1.setViewPager(viewpager);
        viewpagertab2.setViewPager(viewpager);
        viewpagertab3.setViewPager(viewpager);
        viewpagertab4.setViewPager(viewpager);
        viewpagertab5.setViewPager(viewpager);

        settext(viewpagertab1);
        settext(viewpagertab2);
        settext(viewpagertab3);
        settext(viewpagertab4);
        settext(viewpagertab5);
    }

    private void settext(SmartTabLayout viewpagertab) {
        for (int i = 0; i < pageSize; i++) {
            TextView textView = viewpagertab.getTabAt(i).findViewById(R.id.text);

            switch (i) {
                case 0:
                    textView.setText("嗯");
                    break;

                case 1:
                    textView.setText("哦哦");
                    break;

                case 2:
                    textView.setText("阿凡达");
                    break;

                case 3:
                    textView.setText("通天帝国");
                    break;

                case 4:
                    textView.setText("三国");
                    break;
            }
        }
    }
}
