package com.better.anime.dynamic2d;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.better.anime.R;
import com.better.anime.base.BaseGroup;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: ShadowLayout.java
 * Author: Better
 * Create: 2018/1/19 19:05
 * <p>
 * Changes (from 2018/1/19)
 * -----------------------------------------------------------------
 * 2018/1/19 : Create ShadowLayout.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public class BetterCardView2 extends BaseGroup {

    private int SIZE_UNSET;
    private int SIZE_DEFAULT;

    private Drawable foregroundDraw;
    private Rect selfBounds = new Rect();
    private Rect overlayBounds = new Rect();
    private int shadowColor;
    private int foregroundColor;
    private int backgroundColor;
    private float shadowRadius;
    private float shadowDx;
    private float shadowDy;

    //波浪的画笔
    private Paint mWavePaint;
    private Bitmap waveBitmap;
    private Canvas waveCanvas;
    private BitmapShader mWaveShader;
    // 边框线画笔
    private Paint mBorderPaint;

    private float cornerRadius;

    private Paint paint;

    private float shadowMarginTop;
    private float shadowMarginLeft;
    private float shadowMarginRight;
    private float shadowMarginBottom;

    public final int getShadowColor() {
        return shadowColor;
    }

    public final void setShadowColor(int value) {
        shadowColor = value;
        updatePaintShadow(shadowRadius, shadowDx, shadowDy, value);
    }

    public final int getForegroundColor() {
        return foregroundColor;
    }

    public final void setForegroundColor(int value) {
        foregroundColor = value;
        updateForegroundColor();
    }

    public final int getBackgroundClr() {
        return backgroundColor;
    }

    public final void setBackgroundClr(int value) {
        backgroundColor = value;
        paint.setColor(value);
        invalidate();
    }

    public final float getShadowRadius() {
        return shadowRadius;
    }

    public final void setShadowRadius(float value) {
        shadowRadius = value;
        updatePaintShadow(value, shadowDx, shadowDy, shadowColor);
    }

    public final float getShadowDx() {
        return shadowDx;
    }

    public final void setShadowDx(float value) {
        shadowDx = value;
        updatePaintShadow(shadowRadius, value, shadowDy, shadowColor);
    }

    public final float getShadowDy() {
        return shadowDy;
    }

    public final void setShadowDy(float value) {
        shadowDy = value;
        updatePaintShadow(shadowRadius, shadowDx, value, shadowColor);
    }

    public final float getShadowMarginTop() {
        return shadowMarginTop;
    }

    public final void setShadowMarginTop(float value) {
        shadowMarginTop = value;
        updatePaintShadow();
    }

    public final float getShadowMarginLeft() {
        return shadowMarginLeft;
    }

    public final void setShadowMarginLeft(float value) {
        shadowMarginLeft = value;
        updatePaintShadow();
    }

    public final float getShadowMarginRight() {
        return shadowMarginRight;
    }

    public final void setShadowMarginRight(int value) {
        shadowMarginRight = value;
        updatePaintShadow();
    }

    public final float getShadowMarginBottom() {
        return shadowMarginBottom;
    }

    public final void setShadowMarginBottom(float value) {
        shadowMarginBottom = value;
        updatePaintShadow();
    }

    private final void updatePaintShadow() {
        updatePaintShadow(shadowRadius, shadowDx, shadowDy, shadowColor);
    }

    private final void updatePaintShadow(float radius, float dx, float dy, int color) {
        paint.setShadowLayer(radius, dx, dy, color);
        invalidate();
    }


    public BetterCardView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BetterCardView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int maxHeight = 0;
        int maxWidth = 0;
        int childState = 0;
        this.setMeasuredDimension(FrameLayout.getDefaultSize(0, widthMeasureSpec), FrameLayout.getDefaultSize(0, heightMeasureSpec));
        boolean shadowMeasureWidthMatchParent = this.getLayoutParams().width == ViewGroup.LayoutParams.MATCH_PARENT;
        boolean shadowMeasureHeightMatchParent = this.getLayoutParams().height == ViewGroup.LayoutParams.MATCH_PARENT;
        int widthSpec = widthMeasureSpec;
        int heightSpec;
        if (shadowMeasureWidthMatchParent) {
            heightSpec = this.getMeasuredWidth() - (int) shadowMarginRight - (int) shadowMarginLeft;
            widthSpec = MeasureSpec.makeMeasureSpec(heightSpec, MeasureSpec.EXACTLY);
        }

        heightSpec = heightMeasureSpec;
        if (shadowMeasureHeightMatchParent) {
            int childHeightSize = this.getMeasuredHeight() - (int) shadowMarginTop - (int) shadowMarginBottom;
            heightSpec = MeasureSpec.makeMeasureSpec(childHeightSize, MeasureSpec.EXACTLY);
        }

        View child = this.getChildAt(0);
        if (child != null && child.getVisibility() != View.GONE) {
            this.measureChildWithMargins(child, widthSpec, 0, heightSpec, 0);
            ViewGroup.MarginLayoutParams var10000 = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
            if (var10000 == null) {
                return;
            }

            MarginLayoutParams lp = (MarginLayoutParams) var10000;
            maxWidth = shadowMeasureWidthMatchParent ? Math.max(maxWidth, child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin) : (int) Math.max(maxWidth, child.getMeasuredWidth() + this.shadowMarginLeft + this.shadowMarginRight + lp.leftMargin + lp.rightMargin);
            maxHeight = shadowMeasureHeightMatchParent ? Math.max(maxHeight, child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin) : (int) Math.max(maxHeight, child.getMeasuredHeight() + this.shadowMarginTop + this.shadowMarginBottom + lp.topMargin + lp.bottomMargin);
            childState = View.combineMeasuredStates(childState, child.getMeasuredState());
        }

        maxWidth += this.getPaddingLeft() + this.getPaddingRight();
        maxHeight += this.getPaddingTop() + this.getPaddingBottom();
        maxHeight = Math.max(maxHeight, this.getSuggestedMinimumHeight());
        maxWidth = Math.max(maxWidth, this.getSuggestedMinimumWidth());
        Drawable drawable = this.getForeground();
        if (drawable != null) {
            maxHeight = Math.max(maxHeight, drawable.getMinimumHeight());
            maxWidth = Math.max(maxWidth, drawable.getMinimumWidth());
        }

        this.setMeasuredDimension(View.resolveSizeAndState(maxWidth, shadowMeasureWidthMatchParent ? widthMeasureSpec : widthSpec, childState), View.resolveSizeAndState(maxHeight, shadowMeasureHeightMatchParent ? heightMeasureSpec : heightSpec, childState << 16));
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        for (int i = 0; i < getChildCount(); ++i) {
            View child = getChildAt(i);
            if (child != null && child.getVisibility() != View.GONE) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp == null) {
                    break;
                }

                int childLeft = (int) (getPaddingLeft() + lp.leftMargin + shadowMarginLeft);
                int childTop = (int) (getPaddingTop() + lp.topMargin + shadowMarginTop);

                int groupWidth = (int) (mViewWidth - shadowMarginLeft - shadowMarginRight);
                int groupHeight = (int) (mViewHeight - shadowMarginTop - shadowMarginBottom);

                int childRight = childLeft + (child.getMeasuredWidth() > groupWidth ? groupWidth : child.getMeasuredWidth());
                int childBottom = childTop + (child.getMeasuredHeight() > groupHeight ? groupHeight : child.getMeasuredHeight());
                child.layout(childLeft, childTop, childRight, childBottom);
            }
        }
    }


    @Override
    public void initCustomView(@NonNull Context context, @NonNull AttributeSet attrs) {
        SIZE_UNSET = -1;

        selfBounds = new Rect();
        overlayBounds = new Rect();
        paint = new Paint();

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BetterCardView);

        setShadowColor(typedArray.getColor(R.styleable.BetterCardView_shadowColor, Color.parseColor("#778899")));
        setForegroundColor(typedArray.getColor(R.styleable.BetterCardView_foregroundColor, Color.parseColor("#1f000000")));
        setBackgroundClr(typedArray.getColor(R.styleable.BetterCardView_backgroundColor, Color.WHITE));
        setShadowDx(typedArray.getFloat(R.styleable.BetterCardView_shadowDx, 0.0F));
        setShadowDy(typedArray.getFloat(R.styleable.BetterCardView_shadowDy, 1.0F));
        setShadowRadius(typedArray.getDimensionPixelSize(R.styleable.BetterCardView_cardElevation, 0));
        Drawable drawable = typedArray.getDrawable(R.styleable.BetterCardView_android_foreground);
        if (drawable != null) {
            setForeground(drawable);
        }

        int shadowMargin = typedArray.getDimensionPixelSize(R.styleable.BetterCardView_cardShadowMargin, SIZE_UNSET);
        if (shadowMargin >= 0) {
            setShadowMarginTop(shadowMargin);
            setShadowMarginLeft(shadowMargin);
            setShadowMarginRight(shadowMargin);
            setShadowMarginBottom(shadowMargin);
        } else {
            setShadowMarginTop(typedArray.getDimensionPixelSize(R.styleable.BetterCardView_cardShadowMarginTop, SIZE_DEFAULT));
            setShadowMarginLeft(typedArray.getDimensionPixelSize(R.styleable.BetterCardView_cardShadowMarginLeft, SIZE_DEFAULT));
            setShadowMarginRight(typedArray.getDimensionPixelSize(R.styleable.BetterCardView_cardShadowMarginRight, SIZE_DEFAULT));
            setShadowMarginBottom(typedArray.getDimensionPixelSize(R.styleable.BetterCardView_cardShadowMarginBottom, SIZE_DEFAULT));
        }

        cornerRadius = (float) typedArray.getDimensionPixelSize(R.styleable.BetterCardView_cardCornerRadius, SIZE_UNSET);

        typedArray.recycle();

        paint.setColor(backgroundColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        setWillNotDraw(false);
        setBackground(null);

        //init border
        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        waveBitmap = Bitmap.createBitmap(mViewWidth, mViewHeight, Bitmap.Config.ARGB_8888);
        waveBitmap.eraseColor(Color.TRANSPARENT);//把bitmap填充成透明色
        waveCanvas = new Canvas(waveBitmap);

        mWaveShader = new BitmapShader(waveBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
    }

    protected void onDraw(@Nullable Canvas canvas) {
        super.onDraw(canvas);

        if (waveCanvas != null) {
            Path path = getRoundedPath();
            waveCanvas.drawPath(path, paint);
        }


    }

    public void draw(@Nullable Canvas canvas) {

        super.draw(canvas);

        canvas.save();
        Path path = getRoundedPath();
        canvas.clipPath(path);
        drawForeground(canvas);
        canvas.restore();
    }

    /**
     * 绘制圆角控件. 修复使用clipPath有锯齿问题.
     */
    private void drawShadowGroup(Canvas canvas) {

        Bitmap waveBitmap = Bitmap.createBitmap(mViewWidth, mViewHeight, Bitmap.Config.ARGB_8888);
        waveBitmap.eraseColor(Color.TRANSPARENT);//把bitmap填充成透明色
        Canvas waveCanvas = new Canvas(waveBitmap);

        BitmapShader mWaveShader = new BitmapShader(waveBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
    }

    public final void drawForeground(@org.jetbrains.annotations.Nullable Canvas canvas) {
        Drawable var10000 = foregroundDraw;
        if (foregroundDraw != null) {
            Drawable var2 = var10000;
            int w = getRight() - getLeft();
            int h = getBottom() - getTop();
            selfBounds.set(getPaddingLeft(), getPaddingTop(), w - getPaddingRight(), h - getPaddingBottom());
            Gravity.apply(119, var2.getIntrinsicWidth(), var2.getIntrinsicHeight(), selfBounds, overlayBounds);
            var2.setBounds(overlayBounds);
            var2.draw(canvas);
        }

    }

    @Nullable
    public Drawable getForeground() {
        return foregroundDraw;
    }

    protected boolean verifyDrawable(@NonNull Drawable who) {
        return super.verifyDrawable(who) || who == foregroundDraw;
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable var10000 = foregroundDraw;
        if (foregroundDraw != null) {
            Drawable var1 = var10000;
            var1.jumpToCurrentState();
        }

    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable var10000 = foregroundDraw;
        if (foregroundDraw != null) {
            Drawable var1 = var10000;
            var10000 = var1.isStateful() ? var1 : null;
            if (var10000 != null) {
                var1 = var10000;
                var1.setState(getDrawableState());
            }
        }

    }

    public void setForeground(@Nullable Drawable drawable) {
        if (foregroundDraw != null) {
            Drawable var10000 = foregroundDraw;
            if (foregroundDraw != null) {
                var10000.setCallback((Drawable.Callback) null);
            }

            unscheduleDrawable(foregroundDraw);
        }

        foregroundDraw = drawable;
        updateForegroundColor();
        if (drawable != null) {
            setWillNotDraw(false);
            drawable.setCallback((Drawable.Callback) this);
            if (drawable.isStateful()) {
                drawable.setState(getDrawableState());
            }

            Rect padding = new Rect();
            drawable.getPadding(padding);
        }

        requestLayout();
        invalidate();
    }

    private final void updateForegroundColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            RippleDrawable var10000 = (RippleDrawable) foregroundDraw;
            if ((RippleDrawable) foregroundDraw != null) {
                var10000.setColor(ColorStateList.valueOf(foregroundColor));
            }
        } else {
            Drawable var1 = foregroundDraw;
            if (foregroundDraw != null) {
                var1.setColorFilter(foregroundColor, PorterDuff.Mode.SRC_ATOP);
            }
        }

    }

    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);
        if (Build.VERSION.SDK_INT >= 21) {
            if (foregroundDraw != null) {
                foregroundDraw.setHotspot(x, y);
            }
        }
    }

    private Path getRoundedPath() {

        float left = shadowMarginLeft;
        float top = shadowMarginTop;
        float right = getMeasuredWidth() - shadowMarginRight;
        float bottom = getMeasuredHeight() - shadowMarginBottom;
        float tl = cornerRadius;
        float tr = cornerRadius;
        float br = cornerRadius;
        float bl = cornerRadius;

        Path path = new Path();
        Float width = right - left;
        Float height = bottom - top;
        if (tl > Math.min(width, height) / 2) tl = Math.min(width, height) / 2;
        if (tr > Math.min(width, height) / 2) tr = Math.min(width, height) / 2;
        if (br > Math.min(width, height) / 2) br = Math.min(width, height) / 2;
        if (bl > Math.min(width, height) / 2) bl = Math.min(width, height) / 2;

        path.moveTo(right, top + tr);
        if (tr > 0)
            path.rQuadTo(0f, -tr, -tr, -tr);
        else {
            path.rLineTo(0f, -tr);
            path.rLineTo(-tr, 0f);
        }
        path.rLineTo(-(width - tr - tl), 0f);
        if (tl > 0)
            path.rQuadTo(-tl, 0f, -tl, tl);
        else {
            path.rLineTo(-tl, 0f);
            path.rLineTo(0f, tl);
        }
        path.rLineTo(0f, height - tl - bl);

        if (bl > 0)
            path.rQuadTo(0f, bl, bl, bl);
        else {
            path.rLineTo(0f, bl);
            path.rLineTo(bl, 0f);
        }

        path.rLineTo(width - bl - br, 0f);
        if (br > 0)
            path.rQuadTo(br, 0f, br, -br);
        else {
            path.rLineTo(br, 0f);
            path.rLineTo(0f, -br);
        }

        path.rLineTo(0f, -(height - br - tr));

        path.close();

        return path;
    }
}
