package com.better.anime.dynamic2d;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
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
    private Paint mPaint;
    private Path path;
    private float currentPercent;
    private int color;
    private Bitmap bitmap;
    private Canvas mCanvas;

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
        init();
    }

    private void init() {
        //自定义颜色和文字
        color = Color.rgb(41, 163, 254);

        //图形及路径填充画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(color);
        mPaint.setDither(true);
        //闭合波浪路径
        path = new Path();

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPercent = animation.getAnimatedFraction();
                invalidate();
            }
        });
        animator.start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setShader(getShaderBitmap());
        canvas.drawRect(0, 0, mViewHeight, mViewWidth, mPaint);
        mPaint.setShader(null);
    }

    private BitmapShader getShaderBitmap() {
        bitmap.eraseColor(Color.TRANSPARENT);//把bitmap填充成透明色
        //生成闭合波浪路径
        path = getActionPath(currentPercent);
        //绘制蓝色波浪
        mCanvas.drawPath(path, mPaint);

        return new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        bitmap = Bitmap.createBitmap(mViewWidth, mViewHeight, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(bitmap);
    }

    //rQuadTo 参数，
    private Path getActionPath(float percent) {
        Path path = new Path();
        int x = -mViewWidth;
        //当前x点坐标（根据动画进度水平推移，一个动画周期推移的距离为一个mViewWidth）
        x += percent * mViewWidth;
        //波形的起点
        path.moveTo(x, mViewHeight / 2);
        //控制点的相对宽度
        int quadWidth = mViewWidth / 4;
        //控制点的相对高度
        int quadHeight = mViewHeight / 20 * 3;
        //第一个周期
        path.rQuadTo(quadWidth, quadHeight, quadWidth * 2, 0);
        path.rQuadTo(quadWidth, -quadHeight, quadWidth * 2, 0);
        //第二个周期
        path.rQuadTo(quadWidth, quadHeight, quadWidth * 2, 0);
        path.rQuadTo(quadWidth, -quadHeight, quadWidth * 2, 0);
        //右侧的直线
        path.lineTo(x + mViewWidth * 2, mViewHeight);
        //下边的直线
        path.lineTo(x, mViewHeight);
        //自动闭合补出左边的直线
        path.close();
        return path;
    }

}