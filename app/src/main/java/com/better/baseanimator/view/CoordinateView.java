package com.better.baseanimator.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
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
    private float process = 0;
    //时间
    private float time = 0;

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

        float[] pts1 = {pading, 0, pading, mHeight - pading, pading, mHeight - pading, mWidth, mHeight - pading};
        canvas.drawLines(pts1, paintLine);
        canvas.drawPath(dottedPath, dottedLine);
        canvas.drawPath(path, paintLine);

        //字体高度
        Paint.FontMetrics fontMetrics = paintText.getFontMetrics();
        float font_height = fontMetrics.bottom - fontMetrics.top;

        canvas.drawText("1", pading * 2 / 3, pading + font_height / 3, paintText);
        canvas.drawText("0", pading * 2 / 3, mHeight - pading + font_height, paintText);
        canvas.drawText("1", mWidth - pading + pading / 3, mHeight - pading + font_height, paintText);
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
        paintText.setTextAlign(Paint.Align.RIGHT);
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
    }

    public void setProcess(float process) {
        this.process = process;
        float x = (pading) + (mWidth - 2 * pading) * time;
        float y = (mHeight - pading) - (mHeight - 2 * pading) * process;
        path.lineTo(x, y);
        invalidate();
    }

    public void setTime(float time) {
        this.time = time;
    }

    public void initPath() {
        //实线
        path.reset();
        path.moveTo(pading, mHeight - pading);//起始点

        //虚线
        dottedPath.reset();
        dottedPath.moveTo(pading, pading);
        dottedPath.lineTo(mWidth - pading, pading);
        dottedPath.lineTo(mWidth - pading, mHeight - pading);

        setProcess(0.5f);
        invalidate();
    }

    public void clear() {
        path.reset();
        path.moveTo(mWidth - pading, mHeight - pading);//起始点

        invalidate();
    }
}
