package com.better.animator.bean;

import android.support.annotation.LayoutRes;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: MultiItem.java
 * Author: Better
 * Create: 2018/1/22 11:47
 * <p>
 * Changes (from 2018/1/22)
 * -----------------------------------------------------------------
 * 2018/1/22 : Create MultiItem.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public class MultiItem implements MultiItemEntity {

    private int itemType;
    private int resLayout;

    public MultiItem(int itemType, @LayoutRes int resLayout) {
        this.itemType = itemType;
        this.resLayout = resLayout;
    }


    public int getResLayout() {
        return resLayout;
    }

    public void setResLayout(int resLayout) {
        this.resLayout = resLayout;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
