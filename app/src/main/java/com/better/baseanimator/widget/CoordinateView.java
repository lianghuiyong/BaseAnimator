package com.better.baseanimator.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2017, by Better, All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: CoordinateView.java
 * Author: lianghuiyong@outlook.com
 * Create: 2017/12/18 下午2:16
 *
 * Changes (from 2017/12/18)
 * -----------------------------------------------------------------
 * 2017/12/18 : Create CoordinateView.java (梁惠涌);
 * -----------------------------------------------------------------
 */
public class CoordinateView extends View {

    //实线
    private Path path;
    private Paint paintLine;        //坐标横线、曲线画笔

    //虚线
    private Paint dottedLine;
    private Path dottedPath;

    //宽高
    private int mWidth;
    private int mHeight;

    //文字画笔
    private Paint paintText;
    //内容间距
    private float pading;
    //进度
    private float progress = 0;
    //时间
    private float time = 0;

    private float point_x;
    private float point_y;

    private Paint circlePaint;

    private AnimatorSet animatorSet;

    //标题
    private String title = "";

    public CoordinateView(Context context) {
        super(context);
        init();
    }

    public CoordinateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CoordinateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float[] pts1 = {pading, 2 * pading, pading, mHeight - 3 * pading, pading, mHeight - 3 * pading, mWidth, mHeight - 3 * pading};
        //坐标实线
        canvas.drawLines(pts1, paintLine);
        //坐标虚线
        canvas.drawPath(dottedPath, dottedLine);

        //字体高度
        canvas.drawText("1", pading / 2, 3 * pading, paintText);
        canvas.drawText("0", pading / 2, mHeight - 2.3f * pading, paintText);
        canvas.drawText("1", mWidth - pading, mHeight - 2.3f * pading, paintText);
        //弧度
        path.lineTo(point_x, point_y);
        canvas.drawPath(path, paintLine);
        //圆球
        canvas.drawCircle(mWidth - pading, point_y, pading / 4, circlePaint);
        //标题
        canvas.drawText(title, mWidth / 2, 3 * pading / 2, paintText);
    }

    public void setTitle(String title) {
        this.title = title;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        initPath();
    }

    private void init() {
        //初始化间距
        pading = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18, getResources().getDisplayMetrics());

        float line_width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());

        path = new Path();
        paintLine = new Paint();
        paintLine.setColor(Color.BLUE);
        paintLine.setStrokeWidth(line_width);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setAntiAlias(true);

        //文字画笔
        float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());
        paintText = new Paint();
        paintText.setColor(Color.BLUE);
        /**
         * 设置绘制文字时起始点X坐标的位置
         * CENTER:以文字的宽度的中心点为起始点向两边绘制
         * LEFT:以文字左边为起始点向右边开始绘制
         * RIGHT:以文字宽度的右边为起始点向左边绘制
         */
        paintText.setTextAlign(Paint.Align.CENTER);
        paintText.setTextSize(textSize);
        paintText.setStyle(Paint.Style.FILL);   // 设置样式
        paintText.setAntiAlias(true);

        //虚线
        dottedLine = new Paint();
        dottedLine.setColor(Color.BLUE);
        dottedLine.setAntiAlias(true);
        dottedLine.setStrokeWidth(line_width / 2);
        dottedLine.setStyle(Paint.Style.STROKE);
        dottedPath = new Path();
        dottedLine.setPathEffect(new DashPathEffect(new float[]{5, 5}, 0));

        //圆球
        circlePaint = new Paint();
        circlePaint.setColor(Color.RED);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL);
    }

    public void setProgress(float progress) {
        this.progress = progress;

        point_y = (mHeight - 3 * pading) - (mHeight - 6 * pading) * progress;

        invalidate();
    }

    public void setTime(float time) {
        this.time = time;

        point_x = (pading) + (mWidth - 2 * pading) * time;

        invalidate();
    }

    public void initPath() {
        //初始化起点
        point_x = pading;
        point_y = mHeight - 3 * pading;

        //实线
        path.reset();
        path.moveTo(point_x, point_y);//起始点

        //虚线
        dottedPath.reset();
        dottedPath.moveTo(point_x, 3 * pading);
        dottedPath.lineTo(mWidth - point_x, 3 * pading);
        dottedPath.lineTo(mWidth - point_x, point_y);

        invalidate();
    }

    public void setInterpolator(TimeInterpolator value) {
        clear();

        ValueAnimator processAnimator = ObjectAnimator.ofFloat(this, "progress", 0.0f, 1.0f);
        ValueAnimator timeAnimator = ObjectAnimator.ofFloat(this, "time", 0.0f, 1.0f);
        processAnimator.setInterpolator(value);

        //init AnimatorSet
        animatorSet = new AnimatorSet();
        animatorSet.setDuration(1000);
        animatorSet.play(processAnimator).with(timeAnimator);
        animatorSet.start();
    }

    public void clear() {
        if (animatorSet != null){
            //结束动画
            animatorSet.cancel();
        }

        //初始化起点
        point_x = pading;
        point_y = mHeight - 3 * pading;

        path.reset();
        path.moveTo(point_x, point_y);//起始点

        invalidate();
    }
}
