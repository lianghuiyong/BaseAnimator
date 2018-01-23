package com.better.anime.dynamic2d;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import com.better.anime.R;
import com.better.anime.base.BaseCustomView;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2017, by Better, All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: WaveTest.java
 * Author: lianghuiyong@outlook.com
 * Create: 2018/1/5 上午9:37
 *
 * Changes (from 2018/1/5)
 * -----------------------------------------------------------------
 * 2018/1/5 : Create WaveTest.java (梁惠涌);
 * -----------------------------------------------------------------
 */
public class WaveView extends BaseCustomView {

    //波浪的画笔
    private Paint mWavePaint;
    // 边框线画笔
    private Paint mBorderPaint;
    //平移偏移量
    private float mOffset;
    //一个屏幕内显示几个周期
    private int mWaveCount = 2;
    //波浪显示高度
    private float waveLevel = 0.5f;
    //波浪的路径
    private Path mWavePath;

    private int color;
    private int color_top;
    private int color_bottom;

    private int waveType;

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public Animator getAnimator() {
        ValueAnimator waveAnimator = ObjectAnimator.ofFloat(this, "offset", 0, mViewWidth);
        waveAnimator.setInterpolator(new LinearInterpolator());
        waveAnimator.setDuration(3000);
        waveAnimator.setRepeatCount(ValueAnimator.INFINITE);
        waveAnimator.setRepeatMode(ValueAnimator.RESTART);
        waveAnimator.start();

        return waveAnimator;
    }

    @Override
    public void initCustomView(@NonNull Context context, @NonNull AttributeSet attrs) {

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BaseCustomView);
        waveType = typedArray.getInt(R.styleable.BaseCustomView_better_mode, 0);

        float border_width = typedArray.getDimension(R.styleable.BaseCustomView_better_border_width, 0);
        int border_color = typedArray.getColor(R.styleable.BaseCustomView_better_border_color, Color.parseColor("#000000"));

        color = typedArray.getColor(R.styleable.BaseCustomView_better_color, Color.parseColor("#80000000"));
        color_top = typedArray.getColor(R.styleable.BaseCustomView_better_color_top, 0);
        color_bottom = typedArray.getColor(R.styleable.BaseCustomView_better_color_bottom, 0);

        typedArray.recycle();

        //init border
        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setColor(border_color);
        mBorderPaint.setStrokeWidth(border_width);

        //init path
        mWavePath = new Path();

        //init paint
        mWavePaint = new Paint();
        mWavePaint.setStrokeWidth(0);
        mWavePaint.setAntiAlias(true);
    }

    private void createWaveCanvas() {
        Bitmap waveBitmap = Bitmap.createBitmap(mViewWidth, mViewHeight, Bitmap.Config.ARGB_8888);
        waveBitmap.eraseColor(Color.TRANSPARENT);//把bitmap填充成透明色
        Canvas waveCanvas = new Canvas(waveBitmap);

        drawWave1(waveCanvas);
        drawWave2(waveCanvas);

        BitmapShader mWaveShader = new BitmapShader(waveBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        mWavePaint.setShader(mWaveShader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制波浪
        createWaveCanvas();

        float borderWidth = mBorderPaint.getStrokeWidth();
        switch (waveType) {
            //矩形 0
            case 0:
                //波浪
                canvas.drawRect(borderWidth, borderWidth, mViewWidth - borderWidth, mViewHeight - borderWidth, mWavePaint);

                //边框
                if (borderWidth != 0) {
                    canvas.drawRect(borderWidth / 2f, borderWidth / 2f,
                            mViewWidth - borderWidth / 2f - 0.5f, mViewHeight - borderWidth / 2f - 0.5f, mBorderPaint);
                }
                break;

            //圆形 1
            case 1:
                //波浪
                canvas.drawCircle(mViewWidth / 2f, mViewHeight / 2f, mViewWidth / 2f - borderWidth, mWavePaint);

                //边框
                if (borderWidth != 0) {
                    canvas.drawCircle(mViewWidth / 2f, mViewHeight / 2f, (mViewWidth - borderWidth) / 2f, mBorderPaint);
                }
                break;
        }
    }

    /**
     * 波形1
     *
     * @param canvas
     */
    private void drawWave1(Canvas canvas) {
        mWavePath.reset();

        //波形的起点
        float waveHeight = mViewHeight * 19 / 20 - (mViewHeight * 19) / 20 * waveLevel;
        mWavePath.moveTo(-mViewWidth + mOffset, waveHeight);

        //控制点的高度
        float quadHeight = mViewHeight / 15 - Math.abs(0.5f - waveLevel) * (mViewHeight / 20);
        for (int i = 0; i < mWaveCount; i++) {

            //rQuadTo参数  dx1, dy1：控制点相对起始点偏移量
            //rQuadTo参数  dx2, dy2：终点相对起始点偏移量
            mWavePath.rQuadTo(mViewWidth / 4, quadHeight, mViewWidth / 2, 0);
            mWavePath.rQuadTo(mViewWidth / 4, -quadHeight, mViewWidth / 2, 0);
        }

        mWavePath.lineTo(mViewWidth, mViewHeight);
        mWavePath.lineTo(0, mViewHeight);
        mWavePath.close();

        if (color_top != 0) {
            Shader shader = new LinearGradient(0, waveHeight, 0, mViewHeight, color_top, color_bottom, Shader.TileMode.CLAMP);
            mWavePaint.setShader(shader);
        } else {
            mWavePaint.setColor(color);
        }
        canvas.drawPath(mWavePath, mWavePaint);
        mWavePaint.setShader(null);
    }

    /**
     * 波形2
     *
     * @param canvas
     */
    private void drawWave2(Canvas canvas) {
        mWavePath.reset();

        //角度
        float deg = (mOffset + (mViewWidth / 4)) / mViewWidth * 360;
        //角度转弧度
        Double rad = deg * Math.PI / 180;

        //波形的起点sin后偏移量
        float mOffsetCos = mOffset + (float) Math.sin(rad) * (mViewWidth * 0.06f);

        //波形的起点
        float waveHeight = mViewHeight * 19 / 20 - (mViewHeight * 19) / 20 * waveLevel;
        mWavePath.moveTo(-2 * mViewWidth + mOffsetCos + mViewWidth * 0.3f, waveHeight);

        //控制点的sin后偏移高度
        float quadHeightCos = (float) Math.sin(rad) * (mViewHeight / 50);
        //控制点的高度
        float quadHeight = mViewHeight / 15 - Math.abs(0.5f - waveLevel) * (mViewHeight / 20) + quadHeightCos;
        for (int i = 0; i < mWaveCount + 1; i++) {

            //rQuadTo参数  dx1, dy1：控制点相对起始点偏移量
            //rQuadTo参数  dx2, dy2：终点相对起始点偏移量
            mWavePath.rQuadTo(mViewWidth / 4, quadHeight, mViewWidth / 2, 0);
            mWavePath.rQuadTo(mViewWidth / 4, -quadHeight, mViewWidth / 2, 0);
        }

        mWavePath.lineTo(mViewWidth, mViewHeight);
        mWavePath.lineTo(0, mViewHeight);
        mWavePath.close();

        if (color_top != 0) {
            Shader shader = new LinearGradient(0, waveHeight, 0, mViewHeight, color_top, color_bottom, Shader.TileMode.CLAMP);
            mWavePaint.setShader(shader);
        } else {
            mWavePaint.setColor(color);
        }
        canvas.drawPath(mWavePath, mWavePaint);
        mWavePaint.setShader(null);
    }

    public void setOffset(float Offset) {
        this.mOffset = Offset;
        invalidate();
    }

    public void setWaveLevel(float waveLevel) {
        this.waveLevel = waveLevel;

        invalidate();
    }
}