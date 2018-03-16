package com.better.animator;

import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.better.animator.base.BaseFragment;
import com.better.animator.utils.SoftHideKeyBoardUtil;

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


    @Override
    public int setViewId() {
        return R.layout.tablayout_test;
    }

    @Override
    public void initData() {

//        SoftHideKeyBoardUtil.assistActivity(root_layout);
    }
}
