package com.better.anime.dynamic2d;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.better.anime.R;
import com.better.anime.base.BaseCustomView;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: CircleView.java
 * Author: Better
 * Create: 2018/1/22 17:32
 * <p>
 * Changes (from 2018/1/22)
 * -----------------------------------------------------------------
 * 2018/1/22 : Create CircleView.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public class CircleView extends BaseCustomView {

    private Paint paint;

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void initCustomView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseCustomView);
        int color = typedArray.getColor(R.styleable.BaseCustomView_color, Color.parseColor("#ececec"));

        paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mViewHeight / 2, mViewWidth, mViewHeight / 2, paint);
    }
}
