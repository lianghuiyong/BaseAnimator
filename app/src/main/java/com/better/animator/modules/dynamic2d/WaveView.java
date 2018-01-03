package com.better.animator.modules.dynamic2d;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.better.animator.base.BaseCustomView;

import java.util.ArrayList;
import java.util.List;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2017, by Better, All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: WaveView.java
 * Author: lianghuiyong@outlook.com
 * Create: 2018/1/3 下午2:55
 *
 * Changes (from 2018/1/3)
 * -----------------------------------------------------------------
 * 2018/1/3 : Create WaveView.java (梁惠涌);
 * -----------------------------------------------------------------
 */
public class WaveView extends BaseCustomView {

    /**
     * +------------------------+
     * |<--wave length->        |______
     * |   /\          |   /\   |  |
     * |  /  \         |  /  \  | amplitude
     * | /    \        | /    \ |  |
     * |/      \       |/      \|__|____
     * |        \      /        |  |
     * |         \    /         |  |
     * |          \  /          |  |
     * |           \/           | water level
     * |                        |  |
     * |                        |  |
     * +------------------------+__|____
     */
    private static final float DEFAULT_AMPLITUDE_RATIO = 0.1f;
    private static final float DEFAULT_WATER_LEVEL_RATIO = 0.5f;
    private static final float DEFAULT_WAVE_LENGTH_RATIO = 1.0f;
    private static final float DEFAULT_WAVE_SHIFT_RATIO = 0.0f;

    public static final ShapeType DEFAULT_WAVE_SHAPE = ShapeType.SQUARE;

    // 包含重复波的着色器
    private BitmapShader mWaveShader;
    // 着色器矩阵
    private Matrix mShaderMatrix;
    // 波浪画笔
    private Paint mViewPaint;
    // 边框画笔
    private Paint mBorderPaint;

    public enum ShapeType {
        CIRCLE, //圆形
        SQUARE  //常规
    }

    private float mDefaultAmplitude;
    private float mDefaultWaterLevel;
    private float mDefaultWaveLength;
    private double mDefaultAngularFrequency;

    private float mAmplitudeRatio = DEFAULT_AMPLITUDE_RATIO;
    private float mWaveLengthRatio = DEFAULT_WAVE_LENGTH_RATIO;
    private float mWaterLevelRatio = DEFAULT_WATER_LEVEL_RATIO;
    private float mWaveShiftRatio = DEFAULT_WAVE_SHIFT_RATIO;

    private int mBehindWaveColor = Color.parseColor("#ffb500");
    private int mFrontWaveColor = Color.parseColor("#fcb200");
    private ShapeType mShapeType = DEFAULT_WAVE_SHAPE;

    public WaveView(Context context) {
        super(context);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initCustomView(Context context) {
        mShaderMatrix = new Matrix();
        mViewPaint = new Paint();
        mViewPaint.setAntiAlias(true);

        if (isInEditMode()) {
            return;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        createShader();
    }


    public void setWaveShiftRatio(float waveShiftRatio) {
            mWaveShiftRatio = waveShiftRatio;
            invalidate();
    }

    public void setWaterLevelRatio(float waterLevelRatio) {
            mWaterLevelRatio = waterLevelRatio;
            invalidate();
    }

    public void setAmplitudeRatio(float amplitudeRatio) {
            mAmplitudeRatio = amplitudeRatio;
            invalidate();
    }

    public void setBorder(int width, int color) {
        if (mBorderPaint == null) {
            mBorderPaint = new Paint();
            mBorderPaint.setAntiAlias(true);
            mBorderPaint.setStyle(Paint.Style.STROKE);
        }
        mBorderPaint.setColor(color);
        mBorderPaint.setStrokeWidth(width);

        invalidate();
    }

    /**
     * Create the shader with default waves which repeat horizontally, and clamp vertically
     */
    private void createShader() {
        mDefaultAngularFrequency = 2.0f * Math.PI / DEFAULT_WAVE_LENGTH_RATIO / mViewWidth;
        mDefaultAmplitude = mViewHeight * DEFAULT_AMPLITUDE_RATIO;
        mDefaultWaterLevel = mViewHeight * DEFAULT_WATER_LEVEL_RATIO;
        mDefaultWaveLength = mViewWidth;

        Bitmap bitmap = Bitmap.createBitmap(mViewWidth, mViewHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint wavePaint = new Paint();
        wavePaint.setStrokeWidth(0);
        wavePaint.setAntiAlias(true);

        // Draw default waves into the bitmap
        // y=Asin(ωx+φ)+h
        final int endX = mViewWidth + 1;
        final int endY = mViewHeight + 1;

        float[] waveY = new float[endX];

        Shader shader1 = new LinearGradient(0, 0, 0, getMeasuredHeight(), new int[]{mBehindWaveColor, android.R.color.transparent}, null, LinearGradient.TileMode.CLAMP);
        wavePaint.setShader(shader1);
        //wavePaint.setColor(mBehindWaveColor);
        for (int beginX = 0; beginX < endX; beginX++) {
            double wx = beginX * mDefaultAngularFrequency;
            float beginY = (float) (mDefaultWaterLevel + mDefaultAmplitude * Math.sin(wx));
            canvas.drawLine(beginX, beginY, beginX, endY, wavePaint);

            waveY[beginX] = beginY;
        }

        Shader shader2 = new LinearGradient(0, 0, 0, getMeasuredHeight(), new int[]{mFrontWaveColor, android.R.color.transparent}, null, LinearGradient.TileMode.CLAMP);
        wavePaint.setShader(shader2);
        //wavePaint.setColor(mFrontWaveColor);
        final int wave2Shift = (int) (mDefaultWaveLength / 4);
        for (int beginX = 0; beginX < endX; beginX++) {
            canvas.drawLine(beginX, waveY[(beginX + wave2Shift) % endX], beginX, endY, wavePaint);
        }

        // use the bitamp to create the shader
        mWaveShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        mViewPaint.setShader(mWaveShader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // first call after mShowWave, assign it to our paint
        if (mViewPaint.getShader() == null) {
            mViewPaint.setShader(mWaveShader);
        }

        // sacle shader according to mWaveLengthRatio and mAmplitudeRatio
        // this decides the size(mWaveLengthRatio for width, mAmplitudeRatio for height) of waves
        //mShaderMatrix.setScale(
        //        mWaveLengthRatio / DEFAULT_WAVE_LENGTH_RATIO,
        //        mAmplitudeRatio / DEFAULT_AMPLITUDE_RATIO,
        //        0,
        //        mDefaultWaterLevel);
        // translate shader according to mWaveShiftRatio and mWaterLevelRatio
        // this decides the start position(mWaveShiftRatio for x, mWaterLevelRatio for y) of waves
        //mShaderMatrix.postTranslate(
        //        mWaveShiftRatio * mViewWidth,
        //        (DEFAULT_WATER_LEVEL_RATIO - mWaterLevelRatio) * mViewHeight);

        // assign matrix to invalidate the shader
        //mWaveShader.setLocalMatrix(mShaderMatrix);

        float borderWidth = mBorderPaint == null ? 0f : mBorderPaint.getStrokeWidth();
        switch (mShapeType) {
            case CIRCLE:
                if (borderWidth > 0) {
                    canvas.drawCircle(mViewWidth / 2f, mViewHeight / 2f,
                            (mViewWidth - borderWidth) / 2f - 1f, mBorderPaint);
                }
                float radius = mViewWidth / 2f - borderWidth;
                canvas.drawCircle(mViewWidth / 2f, mViewHeight / 2f, radius, mViewPaint);
                break;
            case SQUARE:
                if (borderWidth > 0) {
                    canvas.drawRect(
                            borderWidth / 2f,
                            borderWidth / 2f,
                            mViewWidth - borderWidth / 2f - 0.5f,
                            mViewHeight - borderWidth / 2f - 0.5f,
                            mBorderPaint);
                }
                canvas.drawRect(borderWidth, borderWidth, mViewWidth - borderWidth,
                        mViewHeight - borderWidth, mViewPaint);
                break;
        }
    }
}
