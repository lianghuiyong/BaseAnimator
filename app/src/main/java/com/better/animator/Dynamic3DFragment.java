package com.better.animator;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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

    @BindView(R.id.edit)
    EditText edit;

    @Override
    public int setViewId() {
        return R.layout.tablayout_test;
    }

    @Override
    public void initData() {
        edit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return false;
            }
        });
    }
}
