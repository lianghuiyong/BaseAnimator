package com.better.anime.dynamic2d;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;


import com.better.anime.R;

import java.util.Arrays;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: RountImageView.java
 * Author: Better
 * Create: 2018/1/23 14:28
 * <p>
 * Changes (from 2018/1/23)
 * -----------------------------------------------------------------
 * 2018/1/23 : Create RountImageView.java (梁惠涌);
 * -----------------------------------------------------------------
 */

/**
 *  Circle  app:shape_mode="circle"
 *          app:stroke_color="#009688"
 *          app:stroke_width="3dp"
 *
 *  Round Rect
 *          app:shape_mode="round_rect"
 *          app:round_radius="20dp"
 *          app:stroke_color="#009688"
 *          app:stroke_width="3dp"
 * */

public class RoundImageView extends AppCompatImageView {


    public RoundImageView(Context context) {
        super(context);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public static final int SHAPE_MODE_ROUND_RECT = 1;
    public static final int SHAPE_MODE_CIRCLE = 2;

    private static final int LAYER_FLAGS = Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG;

    private int mShapeMode = 0;
    private float mRadius = 0;
    private int mStrokeColor = 0x26000000;
    private float mStrokeWidth = 0;
    private boolean mShapeChanged;

    private Path mPath;
    private Shape mShape, mStrokeShape;
    private Paint mPaint, mStrokePaint, mPathPaint;
    private Bitmap mStrokeBitmap;

    private PathExtension mExtension;


    private void init(AttributeSet attrs) {
        setLayerType(LAYER_TYPE_HARDWARE, null);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BaseView);
            mShapeMode = a.getInt(R.styleable.BaseView_better_mode, 0);
            mRadius = a.getDimension(R.styleable.BaseView_better_round_radius, 0);

            mStrokeWidth = a.getDimension(R.styleable.BaseView_better_border_width, 0);
            mStrokeColor = a.getColor(R.styleable.BaseView_better_border_color, mStrokeColor);
            a.recycle();
        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setFilterBitmap(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokePaint.setFilterBitmap(true);
        mStrokePaint.setColor(Color.BLACK);

        mPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPathPaint.setFilterBitmap(true);
        mPathPaint.setColor(Color.BLACK);
        mPathPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        mPath = new Path();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed || mShapeChanged) {
            mShapeChanged = false;

            int width = getMeasuredWidth();
            int height = getMeasuredHeight();

            switch (mShapeMode) {
                case SHAPE_MODE_ROUND_RECT:
                    break;
                case SHAPE_MODE_CIRCLE:
                    int min = Math.min(width, height);
                    mRadius = (float) min / 2;
                    break;
            }

            if (mShape == null || mRadius != 0) {
                float[] radius = new float[8];
                Arrays.fill(radius, mRadius);
                mShape = new RoundRectShape(radius, null, null);
                mStrokeShape = new RoundRectShape(radius, null, null);
            }
            mShape.resize(width, height);
            mStrokeShape.resize(width - mStrokeWidth * 2, height - mStrokeWidth * 2);

            makeStrokeBitmap();

            if (mExtension != null) {
                mExtension.onLayout(mPath, width, height);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mStrokeWidth > 0 && mStrokeShape != null && mStrokeBitmap != null) {
            int i = canvas.saveLayer(0, 0, getMeasuredWidth(), getMeasuredHeight(), null, Canvas.ALL_SAVE_FLAG);
            mStrokePaint.setXfermode(null);
            canvas.drawBitmap(mStrokeBitmap, 0, 0, mStrokePaint);
            canvas.translate(mStrokeWidth, mStrokeWidth);
            mStrokePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            mStrokeShape.draw(canvas, mStrokePaint);
            canvas.restoreToCount(i);
        }

        if (mExtension != null) {
            canvas.drawPath(mPath, mPathPaint);
        }

        switch (mShapeMode) {
            case SHAPE_MODE_ROUND_RECT:
            case SHAPE_MODE_CIRCLE:
                if (mShape != null) {
                    mShape.draw(canvas, mPaint);
                }
                break;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mStrokeBitmap == null) makeStrokeBitmap();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        releaseStrokeBitmap();
    }

    private Bitmap makeStrokeBitmap() {
        if (mStrokeWidth <= 0) return null;

        int w = getMeasuredWidth();
        int h = getMeasuredHeight();

        if (w == 0 || h == 0) return null;

        releaseStrokeBitmap();

        mStrokeBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(mStrokeBitmap);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(mStrokeColor);
        c.drawRect(new RectF(0, 0, w, h), p);
        return mStrokeBitmap;
    }

    private void releaseStrokeBitmap() {
        if (mStrokeBitmap != null) {
            mStrokeBitmap.recycle();
            mStrokeBitmap = null;
        }
    }

    public void setExtension(PathExtension extension) {
        mExtension = extension;
        requestLayout();
    }

    public void setStroke(int strokeColor, float strokeWidth) {
        if (mStrokeWidth <= 0) return;

        if (mStrokeWidth != strokeWidth) {
            mStrokeWidth = strokeWidth;

            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            mStrokeShape.resize(width - mStrokeWidth * 2, height - mStrokeWidth * 2);

            postInvalidate();
        }

        if (mStrokeColor != strokeColor) {
            mStrokeColor = strokeColor;

            makeStrokeBitmap();
            postInvalidate();
        }
    }

    public void setStrokeColor(int strokeColor) {
        setStroke(strokeColor, mStrokeWidth);
    }

    public void setStrokeWidth(float strokeWidth) {
        setStroke(mStrokeColor, strokeWidth);
    }

    public void setShape(int shapeMode, float radius) {
        mShapeChanged = mShapeMode != shapeMode || mRadius != radius;

        if (mShapeChanged) {
            mShapeMode = shapeMode;
            mRadius = radius;

            mShape = null;
            mStrokeShape = null;
            requestLayout();
        }
    }

    public void setShapeMode(int shapeMode) {
        setShape(shapeMode, mRadius);
    }

    public void setShapeRadius(float radius) {
        setShape(mShapeMode, radius);
    }

    public interface PathExtension {
        void onLayout(Path path, int width, int height);
    }

}