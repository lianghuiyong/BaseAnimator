package com.better.anime.dynamic2d;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.better.anime.R;
import com.better.anime.base.BaseGroup;

import org.jetbrains.annotations.NotNull;

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

    private Drawable foregroundDraw;
    private Rect selfBounds = new Rect();
    private Rect overlayBounds = new Rect();
    private int shadowColor;
    private int foregroundColor;
    private int backgroundColor;
    private float shadowRadius;
    private float shadowDx;
    private float shadowDy;

    private float cornerRadiusTL;
    private float cornerRadiusTR;
    private float cornerRadiusBL;
    private float cornerRadiusBR;

    private Paint paint;

    private float shadowMarginTop;
    private float shadowMarginLeft;
    private float shadowMarginRight;
    private float shadowMarginBottom;

    private Paint mCornerShadowPaint;


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

    public final float getCornerRadiusTL() {
        return cornerRadiusTL;
    }

    public final void setCornerRadiusTL(float var1) {
        cornerRadiusTL = var1;
    }

    public final float getCornerRadiusTR() {
        return cornerRadiusTR;
    }

    public final void setCornerRadiusTR(float var1) {
        cornerRadiusTR = var1;
    }

    public final float getCornerRadiusBL() {
        return cornerRadiusBL;
    }

    public final void setCornerRadiusBL(float var1) {
        cornerRadiusBL = var1;
    }

    public final float getCornerRadiusBR() {
        return cornerRadiusBR;
    }

    public final void setCornerRadiusBR(float var1) {
        cornerRadiusBR = var1;
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

    public final void setShadowMarginRight(float value) {
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

/*        paint.setShader(new LinearGradient(0, innerBounds.top, 0, outerBounds.top,
                new int[]{Color.parseColor("#44000000"), Color.parseColor("#14000000"), Color.parseColor("#00000000")},
                new float[]{0f, .5f, 1f}, Shader.TileMode.CLAMP));*/
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
        layoutChildren();
    }

    private void layoutChildren() {
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
            setShadowMarginTop(shadowMargin * 0.25f);
            setShadowMarginLeft(shadowMargin * 0.5f);
            setShadowMarginRight(shadowMargin * 0.5f);
            setShadowMarginBottom(shadowMargin);
        }

        float cornerRadius = (float) typedArray.getDimensionPixelSize(R.styleable.BetterCardView_cardCornerRadius, SIZE_UNSET);
        if (cornerRadius >= 0) {
            cornerRadiusTL = cornerRadius;
            cornerRadiusTR = cornerRadius;
            cornerRadiusBL = cornerRadius;
            cornerRadiusBR = cornerRadius;
        } else {
            cornerRadiusTL = typedArray.getDimensionPixelSize(R.styleable.BetterCardView_cardCornerRadiusTL, 0);
            cornerRadiusTR = (float) typedArray.getDimensionPixelSize(R.styleable.BetterCardView_cardCornerRadiusTR, 0);
            cornerRadiusBL = (float) typedArray.getDimensionPixelSize(R.styleable.BetterCardView_cardCornerRadiusBL, 0);
            cornerRadiusBR = (float) typedArray.getDimensionPixelSize(R.styleable.BetterCardView_cardCornerRadiusBR, 0);
        }
        typedArray.recycle();

        paint.setColor(backgroundColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        mCornerShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mCornerShadowPaint.setStyle(Paint.Style.FILL);

        setBackground(null);
    }

    protected void onDraw(@Nullable Canvas canvas) {
/*        if (canvas != null) {
            Path path = getRoundedPath();


            canvas.drawPath(path, paint);
        }*/


        drawShadow(canvas);
        super.onDraw(canvas);
    }

    private void drawShadow(Canvas canvas) {

        //canvas.drawPath(getRoundedPath(), mCornerShadowPaint);

        final int rotateSaved = canvas.save();


        mCornerShadowPaint.setShader(new LinearGradient(
                0, 0, 0, shadowMarginBottom / 2,
                new int[]{Color.parseColor("#00000000"), Color.parseColor("#14000000"), Color.parseColor("#44000000")},
                new float[]{0f, 0.5f, 1f},
                Shader.TileMode.CLAMP));

        // line_shadow_top
        int saved = canvas.save();
        canvas.drawRect(shadowMarginLeft + cornerRadiusTL, 0, mViewWidth - shadowMarginRight - cornerRadiusTR, shadowMarginTop + 1, mCornerShadowPaint);
        canvas.restoreToCount(saved);

        // line_shadow_right
        saved = canvas.save();
        canvas.rotate(90f);
        canvas.translate(0, -mViewWidth);
        canvas.drawRect(shadowMarginTop + cornerRadiusTL, 0, mViewHeight - shadowMarginBottom - cornerRadiusBL, shadowMarginRight, mCornerShadowPaint);
        canvas.restoreToCount(saved);

        // line_shadow_left
        saved = canvas.save();
        canvas.rotate(-90f);
        canvas.translate(-mViewHeight, 0);
        canvas.drawRect(shadowMarginBottom + cornerRadiusBL, 0, mViewHeight - shadowMarginTop - cornerRadiusTL, shadowMarginLeft, mCornerShadowPaint);
        canvas.restoreToCount(saved);

        // line_shadow_bottom
        saved = canvas.save();
        canvas.rotate(180f);
        canvas.translate(-mViewWidth, -mViewHeight);
        canvas.drawRect(shadowMarginLeft + cornerRadiusBL, 0, mViewWidth - shadowMarginRight - cornerRadiusBR, shadowMarginBottom, mCornerShadowPaint);
        canvas.restoreToCount(saved);

        //Top_left_circle
        mCornerShadowPaint.setShader(new RadialGradient(0, 0, shadowRadius,
                new int[]{Color.parseColor("#00000000"), Color.parseColor("#14000000"), Color.parseColor("#44000000")},
                new float[]{0f, 0.5f, 1f},
                Shader.TileMode.CLAMP));
/*
        RectF rectF = new RectF(0, 0, mViewHeight, mViewHeight);
        canvas.drawArc(rectF, 270, 0, true, mCornerShadowPaint);*/

        canvas.restoreToCount(rotateSaved);
    }

    //限制子布局视图
    @Override
    protected void dispatchDraw(Canvas canvas) {
        Path path = getRoundedPath();
        canvas.clipPath(path);
        super.dispatchDraw(canvas);
    }

    //按下的水波样式
    public void draw(@Nullable Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.save();
            Path path = getRoundedPath();
            canvas.clipPath(path);
            drawForeground(canvas);
            canvas.restore();
        }
    }

    public final void drawForeground(@Nullable Canvas canvas) {
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

    @org.jetbrains.annotations.Nullable
    public Drawable getForeground() {
        return foregroundDraw;
    }

    protected boolean verifyDrawable(@NotNull Drawable who) {
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
            Drawable var10000 = foregroundDraw;
            if (foregroundDraw != null) {
                Drawable var3 = var10000;
                var3.setHotspot(x, y);
            }
        }

    }

    private Path getRoundedPath() {

        float left = shadowMarginLeft;
        float top = shadowMarginTop;
        float right = getMeasuredWidth() - shadowMarginRight;
        float bottom = getMeasuredHeight() - shadowMarginBottom;
        float tl = cornerRadiusTL;
        float tr = cornerRadiusTR;
        float br = cornerRadiusBR;
        float bl = cornerRadiusBL;

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
