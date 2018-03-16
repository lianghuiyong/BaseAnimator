package com.better.animator.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2017, by Better, All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: BaseFragment.java
 * Author: lianghuiyong@outlook.com
 * Create: 2017/12/18 上午10:49
 *
 * Changes (from 2017/12/18)
 * -----------------------------------------------------------------
 * 2017/12/18 : Create BaseFragment.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public abstract class BaseFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(setViewId(), container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    /**
     * 子类实现此抽象方法返回View进行展示
     *
     * @return
     */
    public abstract int setViewId();


    /**
     * 子类在此方法中实现数据的初始化
     */
    public abstract void initData();

}
