package com.better.animator.modules.dynamic2d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.better.animator.base.BaseCustomView;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2017, by Better, All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: RankBarGraphView.java
 * Author: lianghuiyong@outlook.com
 * Create: 2017/12/29 下午4:47
 * 等级柱状图
 *
 * Changes (from 2017/12/29)
 * -----------------------------------------------------------------
 * 2017/12/29 : Create RankBarGraphView.java (梁惠涌);
 * -----------------------------------------------------------------
 */
public class RankBarGraphView  extends BaseCustomView{

    //文字画笔
    private Paint paintText;

    public RankBarGraphView(Context context) {
        super(context);
    }

    public RankBarGraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RankBarGraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initCustomView(Context context) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
