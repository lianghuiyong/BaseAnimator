package com.better.baseanimator.adapter;

import com.better.baseanimator.R;
import com.better.baseanimator.bean.ListInfoBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2017, by Better, All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: InterpolatorAdapter.java
 * Author: lianghuiyong@outlook.com
 * Create: 2017/12/18 下午7:05
 *
 * Changes (from 2017/12/18)
 * -----------------------------------------------------------------
 * 2017/12/18 : Create InterpolatorAdapter.java (梁惠涌);
 * -----------------------------------------------------------------
 */
public class InterpolatorAdapter  extends BaseQuickAdapter<ListInfoBean, BaseViewHolder> {
    public InterpolatorAdapter() {
        super(R.layout.item_text_info);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListInfoBean item) {
        helper.setText(R.id.tv_info, item.getTitle());
        helper.setText(R.id.tv_sub, item.getSubTitle());
    }
}
