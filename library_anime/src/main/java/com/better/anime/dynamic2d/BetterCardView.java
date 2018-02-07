package com.better.anime.dynamic2d;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import com.better.anime.R;
import com.better.anime.base.BaseGroup;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: ShadowLayout.java
 * Author: Better
 * Create: 2018/1/19 19:05
 * <p>
 * Changes (from 2018/1/19)
 * -----------------------------------------------------------------
 * 2018/1/19 : Create ShadowLayout.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public class BetterCardView extends BaseGroup {

    private int SIZE_DEFAULT = 0;

    private float cornerRadiusTL = 8;
    private float cornerRadiusTR = 8;
    private float cornerRadiusBL = 8;
    private float cornerRadiusBR = 8;

    public BetterCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BetterCardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int shadowColor;
    private int foregroundColor;
    private int backgroundColor;
    private int shadowRadius;
    private float shadowDx = 0f;
    private float shadowDy = 0f;
    private Paint paint;

    @Override
    public void initCustomView(@NonNull Context context, @NonNull AttributeSet attrs) {

        Log.e("lhy", "initCustomView");

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BetterCardView);

        shadowColor = typedArray.getColor(R.styleable.BetterCardView_shadowColor, Color.parseColor("#000000"));
        foregroundColor = typedArray.getColor(R.styleable.BetterCardView_foregroundColor, Color.parseColor("#1f000000"));
        backgroundColor = typedArray.getColor(R.styleable.BetterCardView_backgroundColor, Color.WHITE);
        shadowDx = typedArray.getDimensionPixelSize(R.styleable.BetterCardView_shadowDx, 0);
        shadowDy = typedArray.getDimensionPixelSize(R.styleable.BetterCardView_shadowDy, 1);
        shadowRadius = typedArray.getDimensionPixelSize(R.styleable.BetterCardView_cardElevation, SIZE_DEFAULT);


        Log.e("lhy", "shadowColor = " + shadowColor);
        Log.e("lhy", "foregroundColor = " + foregroundColor);
        Log.e("lhy", "backgroundColor = " + backgroundColor);
        Log.e("lhy", "shadowDx = " + shadowDx);
        Log.e("lhy", "shadowDy = " + shadowDy);
        Log.e("lhy", "shadowRadius = " + shadowRadius);

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(backgroundColor);
        paint.setAntiAlias(true);
        paint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor);

        setLayerType(LAYER_TYPE_SOFTWARE, null);
        setWillNotDraw(false);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.e("lhy", "onDraw");
        Log.e("lhy", "mViewWidth = " + mViewWidth);
        Log.e("lhy", "mViewHeight = " + mViewHeight);

        Path path = roundedRect(0, 0, mViewWidth, mViewHeight
                , cornerRadiusTL
                , cornerRadiusTR
                , cornerRadiusBR
                , cornerRadiusBL);
        canvas.drawPath(path, paint);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

/*        Log.e("lhy", "draw");

        canvas.save();

        Path path = roundedRect(0, 0, mViewWidth, mViewHeight
                , cornerRadiusTL
                , cornerRadiusTR
                , cornerRadiusBR
                , cornerRadiusBL);

        canvas.clipPath(path);
        //drawForeground(canvas);
        canvas.restore();*/
    }

    private Path roundedRect(float left, float top, float right, float bottom, float tl, float tr, float br, float bl) {

        Log.e("lhy", "33333333333333");

        Path path = new Path();
        Float width = right - left;
        Float height = bottom - top;
        if (tl > Math.min(width, height) / 2) tl = Math.min(width, height) / 2;
        if (tr > Math.min(width, height) / 2) tr = Math.min(width, height) / 2;
        if (br > Math.min(width, height) / 2) br = Math.min(width, height) / 2;
        if (bl > Math.min(width, height) / 2) bl = Math.min(width, height) / 2;

        Log.e("lhy", "left = "+left);
        Log.e("lhy", "top = "+top);
        Log.e("lhy", "right = "+right);
        Log.e("lhy", "bottom = "+bottom);
        Log.e("lhy", "tl = "+tl);
        Log.e("lhy", "tr = "+tr);
        Log.e("lhy", "br = "+br);
        Log.e("lhy", "bl = "+bl);

        path.moveTo(right, top + tr);
        if (tr > 0)
            path.rQuadTo(0f, -tr, -tr, -tr);
        else {
            path.rLineTo(0f, -tr);
            path.rLineTo(-tr, 0f);
        }
        path.rLineTo(-(width - tr - tl), 0f);
        if (tl > 0)
            path.rQuadTo(-tl, 0f, -tl, tl);
        else {
            path.rLineTo(-tl, 0f);
            path.rLineTo(0f, tl);
        }
        path.rLineTo(0f, height - tl - bl);

        if (bl > 0)
            path.rQuadTo(0f, bl, bl, bl);
        else {
            path.rLineTo(0f, bl);
            path.rLineTo(bl, 0f);
        }

        path.rLineTo(width - bl - br, 0f);
        if (br > 0)
            path.rQuadTo(br, 0f, br, -br);
        else {
            path.rLineTo(br, 0f);
            path.rLineTo(0f, -br);
        }

        path.rLineTo(0f, -(height - br - tr));

        Log.e("lhy", "22222222");
        path.close();

        return path;
    }
}
