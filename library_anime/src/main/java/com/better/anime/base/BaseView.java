package com.better.anime.base;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;

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
public abstract class BaseView extends View {

    public abstract void initCustomView(@NonNull Context context, @NonNull AttributeSet attrs);

    protected int mViewWidth;
    protected int mViewHeight;
    protected Animator animator;

    public BaseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initCustomView(context, attrs);
        initAnimator();
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCustomView(context, attrs);
        initAnimator();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initCustomView(context, attrs);

    }

    public void initAnimator() {
    }

    public Animator getAnimator() {
        return animator;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;

        initAnimator();
    }
}
