package com.better.animator.adapter;

import com.better.animator.R;
import com.better.animator.bean.MultiItem;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

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

public class Dynamic2DAdapter extends BaseMultiItemQuickAdapter<MultiItem, BaseViewHolder> {


    public static List<MultiItem> initData() {

        List<MultiItem> data = new ArrayList<>();
        data.add(new MultiItem(0,R.layout.fragment_2d_waveview));
        data.add(new MultiItem(1,R.layout.fragment_2d_waveview));
        data.add(new MultiItem(1,R.layout.fragment_2d_waveview));
        data.add(new MultiItem(1,R.layout.fragment_2d_waveview));
        data.add(new MultiItem(1,R.layout.fragment_2d_waveview));

        return data;
    }

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public Dynamic2DAdapter(List<MultiItem> data) {
        super(data);

        for (MultiItem item : data) {
            addItemType(item.getItemType(), item.getResLayout());
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItem item) {
/*        switch (helper.getItemViewType()) {

        }*/
    }
}
