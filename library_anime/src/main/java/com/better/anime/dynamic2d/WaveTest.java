package com.better.anime.dynamic2d;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.LinearInterpolator;

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
public class WaveTest extends BaseCustomView {

    /**
     * 波浪的画笔
     */
    private Paint mWavePaint;

    /**
     * 平移偏移量
     */
    private float mOffset;

    /**
     * 一个屏幕内显示几个周期
     */
    private int mWaveCount = 2;


    /**
     * 波浪的路径
     */
    private Path mWavePath;

    public enum ShapeType {
        CIRCLE,
        SQUARE
    }

    public WaveTest(Context context) {
        super(context);
    }

    public WaveTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WaveTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initCustomView(Context context) {
        mWavePath = new Path();

        mWavePaint = new Paint();
        mWavePaint.setStrokeWidth(0);
        mWavePaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap bitmap = Bitmap.createBitmap(mViewWidth, mViewHeight, Bitmap.Config.ARGB_8888);
        Canvas waveCanvas = new Canvas(bitmap);

        drawWave1(waveCanvas);
        drawWave2(waveCanvas);

        // use the bitamp to create the shader
        BitmapShader mWaveShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        mWavePaint.setShader(mWaveShader);

        canvas.drawRect(0, 0, mViewWidth , mViewHeight , mWavePaint);

/*        switch (mShapeType) {
            case CIRCLE:
                if (borderWidth > 0) {
                    canvas.drawCircle(mViewWidth / 2f, mViewHeight / 2f,
                            (mViewWidth - borderWidth) / 2f - 1f, mBorderPaint);
                }
                float radius = mViewWidth / 2f - borderWidth;
                canvas.drawCircle(mViewWidth / 2f, mViewHeight / 2f, radius, mViewPaint);
                break;
            case SQUARE:*/
                //if (borderWidth > 0) {

               // }
               // canvas.drawRect(borderWidth, borderWidth, mViewWidth - borderWidth,
                       // mViewHeight - borderWidth, mViewPaint);
             //   break;
        //}
    }

    /**
     * 波形1
     *
     * @param canvas
     */
    private void drawWave1(Canvas canvas) {
        mWavePath.reset();

        //波形的起点
        mWavePath.moveTo(-mViewWidth + mOffset, mViewHeight / 2);

        //控制点的高度
        float quadHeight = mViewHeight / 10;
        for (int i = 0; i < mWaveCount; i++) {

            //rQuadTo参数  dx1, dy1：控制点相对起始点偏移量
            //rQuadTo参数  dx2, dy2：终点相对起始点偏移量
            mWavePath.rQuadTo(mViewWidth / 4, quadHeight, mViewWidth / 2, 0);
            mWavePath.rQuadTo(mViewWidth / 4, -quadHeight, mViewWidth / 2, 0);
        }

        mWavePath.lineTo(mViewWidth, mViewHeight);
        mWavePath.lineTo(0, mViewHeight);
        mWavePath.close();

        mWavePaint.setColor(Color.parseColor("#A0607D8B"));
//        Shader shader = new LinearGradient(0, 0, 0, mViewHeight / 2, Color.parseColor("#e0d5c8"), Color.parseColor("#00FFFFFF"), Shader.TileMode.CLAMP);
//        mWavePaint.setShader(shader);
        canvas.drawPath(mWavePath, mWavePaint);
//        mWavePaint.setShader(null);
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
        mWavePath.moveTo(-2 * mViewWidth + mOffsetCos + mViewWidth * 0.3f, mViewHeight / 2);

        //控制点的sin后偏移高度
        float quadHeightCos = (float) Math.sin(rad) * (mViewHeight / 50);
        //控制点的高度
        float quadHeight = mViewHeight / 10 + quadHeightCos;
        for (int i = 0; i < mWaveCount + 1; i++) {

            //rQuadTo参数  dx1, dy1：控制点相对起始点偏移量
            //rQuadTo参数  dx2, dy2：终点相对起始点偏移量
            mWavePath.rQuadTo(mViewWidth / 4, quadHeight, mViewWidth / 2, 0);
            mWavePath.rQuadTo(mViewWidth / 4, -quadHeight, mViewWidth / 2, 0);
        }

        mWavePath.lineTo(mViewWidth, mViewHeight);
        mWavePath.lineTo(0, mViewHeight);
        mWavePath.close();

        mWavePaint.setColor(Color.parseColor("#A0388E3C"));

//        Shader shader = new LinearGradient(0, 0, 0, mViewHeight / 2, Color.parseColor("#e0d2c3"), Color.parseColor("#00FFFFFF"), Shader.TileMode.CLAMP);
//        mWavePaint.setShader(shader);
        canvas.drawPath(mWavePath, mWavePaint);
//        mWavePaint.setShader(null);
    }

    public void setOffset(float Offset) {
        this.mOffset = Offset;
        invalidate();
    }
}