package com.better.anime.dynamic2d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.better.anime.base.BaseView;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: TestView.java
 * Author: Better
 * Create: 2018/3/2 16:08
 * <p>
 * Changes (from 2018/3/2)
 * -----------------------------------------------------------------
 * 2018/3/2 : Create TestView.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public class TestView extends BaseView {
    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initCustomView(@NonNull Context context, @NonNull AttributeSet attrs) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 创建画笔
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.GRAY);

        RectF rectF = new RectF(0, 0, mViewHeight, mViewHeight);
        canvas.drawRect(rectF,p);

        p.setShader(new RadialGradient(mViewHeight / 2, mViewHeight / 2, mViewHeight / 2,
                new int[]{Color.parseColor("#44000000"), Color.parseColor("#14000000"), Color.parseColor("#00000000")},
                new float[]{0f, 0.5f, 1f},
                Shader.TileMode.CLAMP));

        canvas.drawArc(rectF, 270, 0, true, p);
    }
}
