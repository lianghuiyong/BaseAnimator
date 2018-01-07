package com.better.anime.dynamic2d;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
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
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.better.anime.R;
import com.better.anime.base.BaseCustomView;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2017, by Better, All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: WaveAntiAliased_Shader.java
 * Author: lianghuiyong@outlook.com
 * Create: 2018/1/5 上午9:15
 *
 * Changes (from 2018/1/5)
 * -----------------------------------------------------------------
 * 2018/1/5 : Create WaveAntiAliased_Shader.java (梁惠涌);
 * -----------------------------------------------------------------
 */
public class WaveAntiAliased_Shader extends BaseCustomView {
    private PorterDuffXfermode xfermode_text = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
    private Paint mPaint;
    private Paint textPaint;
    private Path path;
    private float currentPercent;
    private int color;
    private String text = "百";
    private int textSize;
    private Bitmap bitmap;
    private Canvas mCanvas;

    public WaveAntiAliased_Shader(Context context) {
        this(context, null);
    }

    public WaveAntiAliased_Shader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveAntiAliased_Shader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initCustomView(Context context) {
        init();
    }

    private void init() {
        //自定义颜色和文字
        color = Color.parseColor("#330033");

        //图形及路径填充画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(color);
        mPaint.setDither(true);
        //文字画笔
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setXfermode(xfermode_text);
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
        //底部的字
        textPaint.setColor(color);
        drawCenterText(canvas, textPaint, text);
        mPaint.setShader(getShaderBitmap());
        canvas.drawCircle(mViewWidth / 2, mViewHeight / 2, mViewWidth / 2, mPaint);
        mPaint.setShader(null);
    }


    private BitmapShader getShaderBitmap() {
        bitmap.eraseColor(Color.TRANSPARENT);//把bitmap填充成透明色
        //上层的字
        textPaint.setColor(Color.BLACK);
        //生成闭合波浪路径
        path = getActionPath(currentPercent);
        //绘制蓝色波浪
        mCanvas.drawPath(path, mPaint);

        drawCenterText(mCanvas, textPaint, text);

        return new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        bitmap = Bitmap.createBitmap(mViewWidth, mViewHeight, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(bitmap);
        textSize = Math.min(mViewWidth, mViewHeight) / 2;
        textPaint.setTextSize(textSize);
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

    private void drawCenterText(Canvas canvas, Paint textPaint, String text) {
        Rect rect = new Rect(0, 0, mViewWidth, mViewHeight);
        textPaint.setTextAlign(Paint.Align.CENTER);

        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        //文字框距顶部文字基线的距离
        float top = fontMetrics.top;
        //文字框底部距文字基线的距离
        float bottom = fontMetrics.bottom;
        int centerY = (int) (rect.centerY() - top / 2 - bottom / 2);

        canvas.drawText(text, rect.centerX(), centerY, textPaint);
    }
}

