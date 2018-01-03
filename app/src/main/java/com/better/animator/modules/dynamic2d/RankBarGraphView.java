package com.better.animator.modules.dynamic2d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.better.animator.R;
import com.better.animator.base.BaseCustomView;
import com.better.animator.bean.RankBarBean;

import java.util.ArrayList;
import java.util.List;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2017, by Better, All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: RankBarGraphView.java
 * Author: lianghuiyong@outlook.com
 * Create: 2017/12/29 下午4:47
 * 等级柱状图
 *
 * Changes (from 2017/12/29)
 * -----------------------------------------------------------------
 * 2017/12/29 : Create RankBarGraphView.java (梁惠涌);
 * -----------------------------------------------------------------
 */
public class RankBarGraphView extends BaseCustomView {

    //文字画笔
    private Paint paintText;

    //虚线
    private Paint dottedPaint;
    private Path dottedPath;

    //圆球
    private Paint circlePaint;

    //条形图画笔
    protected Paint mShadowPaint;

    private List<RankBarBean> mData;

    public RankBarGraphView(Context context) {
        super(context);
    }

    public RankBarGraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RankBarGraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initCustomView(Context context) {

        //文字画笔
        float textSize = getResources().getDimensionPixelSize(R.dimen.b_sp12);
        paintText = new Paint();
        paintText.setColor(Color.parseColor("#666666"));
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

        mShadowPaint = new Paint();
        mShadowPaint.setColor(Color.parseColor("#dcdcdc"));
        mShadowPaint.setAntiAlias(true);

        //虚线
        dottedPaint = new Paint();
        dottedPaint.setColor(Color.parseColor("#999999"));
        dottedPaint.setAntiAlias(true);
        dottedPaint.setStrokeWidth(2);
        dottedPaint.setStyle(Paint.Style.STROKE);
        dottedPaint.setPathEffect(new DashPathEffect(new float[]{10, 5}, 0));
        dottedPath = new Path();

        //圆球
        circlePaint = new Paint();
        circlePaint.setColor(Color.parseColor("#fac440"));
        circlePaint.setStrokeWidth(4);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL);

        mData = new ArrayList<>();
        mData.add(new RankBarBean("V.1", 0));
        mData.add(new RankBarBean("V.2", 5));
        mData.add(new RankBarBean("V.3", 10));
        mData.add(new RankBarBean("V.4", 20));
        mData.add(new RankBarBean("V.5", 30));
        mData.add(new RankBarBean("V.6", 40));
        mData.add(new RankBarBean("V.7", 60));
        mData.add(new RankBarBean("V.8", 80));
        mData.add(new RankBarBean("V.9", 100));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //获取最大值
        float max_value = 0;
        for (RankBarBean barBean : mData) {
            if (barBean.getValue() > max_value) {
                max_value = barBean.getValue();
            }
        }

        for (RankBarBean barBean : mData) {

            int rankBar_w = getResources().getDimensionPixelSize(R.dimen.b_dp7);
            float rankBar_value_text_h = 50;

            float rankBar_view_w = mViewWidth / getShowBars();
            //减去上下两个text高度还要减去最小显示的高度
            float rankBar_view_h = (mViewHeight * 4 / 5 - rankBar_value_text_h * 2 - getMiniHeight()) / max_value * barBean.getValue() + getMiniHeight();

            float rankBar_view_x = (mViewWidth / getShowBars()) * mData.indexOf(barBean) + rankBar_view_w / 2;
            float rankBar_view_y = mViewHeight;

            canvas.drawText(barBean.getValue() + "", rankBar_view_x, rankBar_view_y - rankBar_view_h - rankBar_value_text_h / 4, paintText);
            canvas.drawText(barBean.getTitle(), rankBar_view_x, rankBar_view_y - rankBar_value_text_h / 4, paintText);

            RectF rectF = new RectF(rankBar_view_x - rankBar_w / 2, rankBar_view_y - rankBar_view_h, rankBar_view_x + rankBar_w / 2, rankBar_view_y - rankBar_value_text_h);
            canvas.drawRoundRect(rectF, rankBar_w / 2, rankBar_w / 2, mShadowPaint);

            //虚线部分
            float dotted_x = rankBar_view_x;
            float dotted_y = rankBar_view_y - rankBar_view_h - rankBar_value_text_h - 20;
            if (mData.indexOf(barBean) == 0) {
                dottedPath.reset();
                dottedPath.moveTo(dotted_x, dotted_y);

                //圆球1
                circlePaint.setStyle(Paint.Style.STROKE);
                canvas.drawCircle(dotted_x, dotted_y, 8, circlePaint);
            }
            dottedPath.lineTo(dotted_x, dotted_y);
        }
        canvas.drawPath(dottedPath, dottedPaint);

    }

    private int getShowBars() {
        //return mData.size() > 10 ? 10 : mData.size();
        return mData.size();
    }

    private int getMiniHeight() {
        return mViewHeight / 9;
    }

    public void setData(List<RankBarBean> mData) {
        this.mData = mData;
        invalidate();
    }
}
