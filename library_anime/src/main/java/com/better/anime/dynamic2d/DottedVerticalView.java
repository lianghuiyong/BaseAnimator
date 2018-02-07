package com.better.anime.dynamic2d;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.better.anime.R;
import com.better.anime.base.BaseView;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: DottedHorizontalView.java
 * Author: Better
 * Create: 2018/1/22 15:47
 * <p>
 * Changes (from 2018/1/22)
 * -----------------------------------------------------------------
 * 2018/1/22 : Create DottedHorizontalView.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public class DottedVerticalView extends BaseView {

    private Paint paint;

    public DottedVerticalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DottedVerticalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DottedVerticalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void initCustomView(Context context, AttributeSet attrs) {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseView);
        int color = typedArray.getColor(R.styleable.BaseView_better_color, Color.parseColor("#ececec"));

        final DisplayMetrics metrics = getResources().getDisplayMetrics();

        float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, metrics);
        float dashGap = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, metrics);
        float dashWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, metrics);

        paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(width);
        paint.setAntiAlias(true);
        paint.setPathEffect(new DashPathEffect(new float[]{dashWidth, dashGap}, 0));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(mViewWidth / 2, 0, mViewWidth / 2, mViewHeight, paint);
    }
}
