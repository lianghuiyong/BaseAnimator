package com.better.animator.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.better.animator.R;
import com.better.animator.base.BaseRecyclerFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: Dynamic2DAdapter.java
 * Author: Liang
 * Create: 2018/1/15 22:18
 * <p>
 * Changes (from 2018/1/15)
 * -----------------------------------------------------------------
 * 2018/1/15 : Create Dynamic2DAdapter.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public class Dynamic2DAdapter extends BaseQuickAdapter<BaseRecyclerFragment, BaseViewHolder>{
    private FragmentManager fragmentManager;

    public Dynamic2DAdapter(@NonNull FragmentManager fragmentManager) {
        super(R.layout.item_fragment_layout);
        this.fragmentManager = fragmentManager;
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseRecyclerFragment itemFragment) {

        // Add new fragment
        fragmentManager.beginTransaction().replace(R.id.container, itemFragment).commit();
    }
}
