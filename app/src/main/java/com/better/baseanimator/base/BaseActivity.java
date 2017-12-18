package com.better.baseanimator.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2017, by Better, All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: BaseActivity.java
 * Author: lianghuiyong@outlook.com
 * Create: 2017/12/18 上午10:49
 *
 * Changes (from 2017/12/18)
 * -----------------------------------------------------------------
 * 2017/12/18 : Create BaseActivity.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public abstract class BaseActivity extends AppCompatActivity {

    //初始化布局
    public abstract int setViewId();

    // 初始化数据
    public abstract void initData();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = getLayoutInflater().inflate(setViewId(), null);
        setContentView(view);
        ButterKnife.bind(this);
        initData();
    }
}
