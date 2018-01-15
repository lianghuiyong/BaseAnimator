package com.better.animator.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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

public class Dynamic2DAdapter extends BaseQuickAdapter<BaseRecyclerFragment, BaseViewHolder> {
    private FragmentManager fragmentManager;

    public Dynamic2DAdapter(@NonNull FragmentManager fragmentManager) {
        super(R.layout.item_fragment_layout);
        this.fragmentManager = fragmentManager;
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseRecyclerFragment itemFragment) {

/*        // Delete old fragment
        String containerTag = (String) helper.getView(R.id.container).getTag();// Get container id

        if (containerTag != null) {
            Fragment oldFragment = fragmentManager.findFragmentByTag(containerTag);
            fragmentManager.beginTransaction().remove(oldFragment).commit();
        }

        String newContainerTag = GetUniqueID();// My method
        helper.getView(R.id.container).setTag(newContainerTag);// Set container id*/

        // Add new fragment
        fragmentManager.beginTransaction().add(R.id.container, itemFragment, itemFragment.getClass().toString()+helper.getLayoutPosition()).commit();
    }

    private String GetUniqueID() {
        return 111 + (int) (Math.random() * 9999) +"";
    }
}
