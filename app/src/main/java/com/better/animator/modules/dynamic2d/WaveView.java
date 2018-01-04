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

    public static final int DEFAULT_BEHIND_WAVE_COLOR = Color.parseColor("#28FFFFFF");
    public static final int DEFAULT_FRONT_WAVE_COLOR = Color.parseColor("#3CFFFFFF");
    public static final ShapeType DEFAULT_WAVE_SHAPE = ShapeType.SQUARE;

    public enum ShapeType {
        CIRCLE,
        SQUARE
    }

    // if true, the shader will display the wave
    private boolean mShowWave = true;

    // shader containing repeated waves
    private BitmapShader mWaveShader;
    // shader matrix
    private Matrix mShaderMatrix;
    // paint to draw wave
    private Paint mViewPaint;
    // paint to draw border
    private Paint mBorderPaint;

    private float mDefaultWaterLevel;
    private double mDefaultAngularFrequency;

    //波浪的大小, 高度的0.1。
    private float mAmplitudeRatio = 0.1f;
    private float mWaveLengthRatio = 1f;
    private float mWaterLevelRatio = 0.5f;          //波浪高度
    private float mWaveShiftRatio = 0f;

    private int mBehindWaveColor = DEFAULT_BEHIND_WAVE_COLOR;
    private int mFrontWaveColor = DEFAULT_FRONT_WAVE_COLOR;
    private ShapeType mShapeType = DEFAULT_WAVE_SHAPE;

    public WaveView(Context context) {
        super(context);
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setWaveShiftRatio(float waveShiftRatio) {
        mWaveShiftRatio = waveShiftRatio;
        invalidate();
    }

    public void setWaterLevelRatio(float waterLevelRatio) {
        if (mWaterLevelRatio != waterLevelRatio) {
            mWaterLevelRatio = waterLevelRatio;
            invalidate();
        }
    }

    public void setAmplitudeRatio(float amplitudeRatio) {
        mAmplitudeRatio = amplitudeRatio;
        invalidate();
    }

    public void setWaveLengthRatio(float waveLengthRatio) {
        mWaveLengthRatio = waveLengthRatio;
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

    public void setWaveColor(int behindWaveColor, int frontWaveColor) {
        mBehindWaveColor = behindWaveColor;
        mFrontWaveColor = frontWaveColor;

        if (getWidth() > 0 && getHeight() > 0) {
            // need to recreate shader when color changed
            mWaveShader = null;
            createShader();
            invalidate();
        }
    }

    public void setShapeType(ShapeType shapeType) {
        mShapeType = shapeType;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        createShader();
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

    /**
     * Create the shader with default waves which repeat horizontally, and clamp vertically
     */
    private void createShader() {
        mDefaultAngularFrequency = 2.0f * Math.PI / mWaveLengthRatio / getWidth();

        //默认波浪水位高度
        mDefaultWaterLevel = mViewHeight * 0.5f;

        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint wavePaint = new Paint();
        wavePaint.setStrokeWidth(0);
        wavePaint.setAntiAlias(true);

        // Draw default waves into the bitmap
        // y=Asin(ωx+φ)+h
        final int endX = mViewWidth + 1;
        final int endY = mViewHeight + 1;

        float[] waveY = new float[endX];

        //wavePaint.setColor(mBehindWaveColor);
        for (int beginX = 0; beginX < endX; beginX++) {
            double wx = beginX * mDefaultAngularFrequency;

            //水波高度+波浪高度
            float beginY = (float) (mViewHeight * 0.5f + mViewHeight * mAmplitudeRatio * Math.sin(wx));

            Shader shader = new LinearGradient(beginX, beginY, beginX, endY, mBehindWaveColor, Color.parseColor("#FFFFFF"), Shader.TileMode.CLAMP);
            wavePaint.setShader(shader);
            canvas.drawLine(beginX, beginY, beginX, endY, wavePaint);

            waveY[beginX] = beginY;
        }

        //wavePaint.setColor(mFrontWaveColor);
        final int wave2Shift = mViewWidth / 4;
        for (int beginX = 0; beginX < endX; beginX++) {
            Shader shader = new LinearGradient(beginX, waveY[(beginX + wave2Shift) % endX], beginX, endY, mFrontWaveColor, Color.parseColor("#FFFFFF"), Shader.TileMode.CLAMP);
            wavePaint.setShader(shader);
            canvas.drawLine(beginX, waveY[(beginX + wave2Shift) % endX], beginX, endY, wavePaint);
        }

        // use the bitamp to create the shader
        mWaveShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        mViewPaint.setShader(mWaveShader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
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

/*    @Override
    protected void onDraw(Canvas canvas) {
        // modify paint shader according to mShowWave state
        if (mWaveShader != null) {
            // first call after mShowWave, assign it to our paint
            if (mViewPaint.getShader() == null) {
                mViewPaint.setShader(mWaveShader);
            }

            // sacle shader according to mWaveLengthRatio and mAmplitudeRatio
            // this decides the size(mWaveLengthRatio for width, mAmplitudeRatio for height) of waves
            mShaderMatrix.setScale(
                    mWaveLengthRatio / 1f,
                    mAmplitudeRatio / 0.1f,
                    0,
                    mViewHeight * 0.5f ); //水波高度
            // translate shader according to mWaveShiftRatio and mWaterLevelRatio
            // this decides the start position(mWaveShiftRatio for x, mWaterLevelRatio for y) of waves
            mShaderMatrix.postTranslate(
                    mWaveShiftRatio * mViewWidth,
                    (0.5f - mWaterLevelRatio) * mViewHeight);

            // assign matrix to invalidate the shader
            mWaveShader.setLocalMatrix(mShaderMatrix);

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
        } else {
            mViewPaint.setShader(null);
        }
    }*/
}
