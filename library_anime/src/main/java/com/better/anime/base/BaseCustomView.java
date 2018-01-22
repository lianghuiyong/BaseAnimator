package com.better.anime.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2017, by Better, All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: BaseCustomView.java
 * Author: lianghuiyong@outlook.com
 * Create: 2017/12/25 上午10:45
 *
 * Changes (from 2017/12/25)
 * -----------------------------------------------------------------
 * 2017/12/25 : Create BaseCustomView.java (梁惠涌);
 * -----------------------------------------------------------------
 */
public abstract class BaseCustomView extends View {
    /**
     * the width of current view.
     */
    protected int mViewWidth;

    /**
     * the height of current view.
     */
    protected int mViewHeight;

    public BaseCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initCustomView(context, attrs);
    }

    public BaseCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCustomView(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initCustomView(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
    }

    public abstract void initCustomView(Context context, AttributeSet attrs);
}
