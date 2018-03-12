/**
 * Copyright (C) 2015 ogaclejapan
 * Copyright (C) 2013 The Android Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.better.anime.dynamic2d.tablayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.better.anime.R;

/**
 * <p>
 * Forked from Google Samples &gt; SlidingTabsBasic &gt;
 * <a href="https://developer.android.com/samples/SlidingTabsBasic/src/com.example.android.common/view/SlidingTabLayout.html">SlidingTabStrip</a>
 */
class SmartTabStrip extends LinearLayout {

    private static final int GRAVITY_BOTTOM = 0;
    private static final int GRAVITY_TOP = 1;
    private static final int GRAVITY_CENTER = 2;

    private final int topLineHeight;
    private final int topLineColor;
    private final int bottomLineHeight;
    private final int bottomLineColor;
    private final Paint borderPaint;
    private final RectF indicatorRectF = new RectF();
    private final boolean tabIndicatorWithoutPadding;
    private final boolean tabIndicatorAlwaysInCenter;
    private boolean tabIndicatorInFront;
    private final int tabIndicatorHeight;

    private final int tabIndicatorWidth;
    private final int tabIndicatorGravity;
    private final Paint indicatorPaint;
    private final int tabDividerHeight;
    private final Paint dividerPaint;
    private final float dividerHeight;
    private final SimpleTabColorizer defaultTabColorizer;
    private final boolean shouldDrowLine;

    private int lastPosition;
    private int selectedPosition;
    private float selectionOffset;
    private SmartIndicationInterpolator indicationInterpolator;
    private SmartTabLayout.TabColorizer customTabColorizer;

    SmartTabStrip(Context context, AttributeSet attrs) {
        super(context);
        setWillNotDraw(false);

        int tabIndicatorColor;
        int tabDividerColor;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BetterTabLayout);
        tabIndicatorAlwaysInCenter = a.getBoolean(R.styleable.BetterTabLayout_tabIndicatorAlwaysInCenter, false);
        tabIndicatorWithoutPadding = a.getBoolean(R.styleable.BetterTabLayout_tabIndicatorWithoutPadding, false);
        tabIndicatorInFront = a.getBoolean(R.styleable.BetterTabLayout_tabIndicatorInFront, false);

        tabIndicatorGravity = a.getInt(R.styleable.BetterTabLayout_tabIndicatorGravity, GRAVITY_BOTTOM);
        tabIndicatorColor = a.getColor(R.styleable.BetterTabLayout_tabIndicatorColor, Color.parseColor("#33B5E5"));

        tabIndicatorHeight = a.getDimensionPixelSize(R.styleable.BetterTabLayout_tabIndicatorHeight, getResources().getDimensionPixelSize(R.dimen.b_dp2));
        tabIndicatorWidth = a.getLayoutDimension(R.styleable.BetterTabLayout_tabIndicatorWidth, -1);

        topLineHeight = a.getDimensionPixelSize(R.styleable.BetterTabLayout_tabTopLineHeight, 0);
        topLineColor = a.getColor(R.styleable.BetterTabLayout_tabTopLineColor, Color.TRANSPARENT);

        bottomLineColor = a.getColor(R.styleable.BetterTabLayout_tabBottomLineColor, Color.TRANSPARENT);
        bottomLineHeight = a.getDimensionPixelSize(R.styleable.BetterTabLayout_tabBottomLineHeight, 0);

        //间隔线
        tabDividerColor = a.getColor(R.styleable.BetterTabLayout_tabDividerColor, Color.parseColor("#33B5E5"));
        tabDividerHeight = a.getDimensionPixelSize(R.styleable.BetterTabLayout_tabDividerHeight, 0);
        shouldDrowLine = !(tabDividerHeight > 0 || tabIndicatorHeight > 0 || topLineHeight > 0);
        a.recycle();

        final int[] dividerColors = new int[]{tabDividerColor};

        this.defaultTabColorizer = new SimpleTabColorizer();
        this.defaultTabColorizer.setIndicatorColors(tabIndicatorColor);
        this.defaultTabColorizer.setDividerColors(dividerColors);

        this.borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        this.indicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        this.dividerHeight = 0.5f;
        this.dividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.dividerPaint.setStrokeWidth(tabDividerHeight);

        this.indicationInterpolator = new SmartIndicationInterpolator();
    }

    /**
     * Set the alpha value of the {@code color} to be the given {@code alpha} value.
     */
    private static int setColorAlpha(int color, byte alpha) {
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
    }

    /**
     * Blend {@code color1} and {@code color2} using the given ratio.
     *
     * @param ratio of which to blend. 1.0 will return {@code color1}, 0.5 will give an even blend,
     *              0.0 will return {@code color2}.
     */
    private static int blendColors(int color1, int color2, float ratio) {
        final float inverseRation = 1f - ratio;
        float r = (Color.red(color1) * ratio) + (Color.red(color2) * inverseRation);
        float g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRation);
        float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * inverseRation);
        return Color.rgb((int) r, (int) g, (int) b);
    }

    void setIndicationInterpolator(SmartIndicationInterpolator interpolator) {
        indicationInterpolator = interpolator;
        invalidate();
    }

    void setCustomTabColorizer(SmartTabLayout.TabColorizer customTabColorizer) {
        this.customTabColorizer = customTabColorizer;
        invalidate();
    }

    void setSelectedIndicatorColors(int... colors) {
        // Make sure that the custom colorizer is removed
        customTabColorizer = null;
        defaultTabColorizer.setIndicatorColors(colors);
        invalidate();
    }

    void setDividerColors(int... colors) {
        // Make sure that the custom colorizer is removed
        customTabColorizer = null;
        defaultTabColorizer.setDividerColors(colors);
        invalidate();
    }

    void onViewPagerPageChanged(int position, float positionOffset) {
        selectedPosition = position;
        selectionOffset = positionOffset;
        if (positionOffset == 0f && lastPosition != selectedPosition) {
            lastPosition = selectedPosition;
        }
        invalidate();
    }

    boolean isIndicatorAlwaysInCenter() {
        return tabIndicatorAlwaysInCenter;
    }

    SmartTabLayout.TabColorizer getTabColorizer() {
        return (customTabColorizer != null) ? customTabColorizer : defaultTabColorizer;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!shouldDrowLine) {
            drawDecoration(canvas);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (shouldDrowLine) {
            drawDecoration(canvas);
        }
    }

    private void drawDecoration(Canvas canvas) {
        final int height = getHeight();
        final int width = getWidth();
        final int tabCount = getChildCount();
        final SmartTabLayout.TabColorizer tabColorizer = getTabColorizer();
        final boolean isLayoutRtl = Utils.isLayoutRtl(this);

        if (tabIndicatorInFront) {
            drawOverline(canvas, 0, width);
            drawUnderline(canvas, 0, width, height);
        }

        // Thick colored underline below the current selection
        if (tabCount > 0) {
            View selectedTab = getChildAt(selectedPosition);
            int selectedStart = Utils.getStart(selectedTab, tabIndicatorWithoutPadding);
            int selectedEnd = Utils.getEnd(selectedTab, tabIndicatorWithoutPadding);
            int left;
            int right;
            if (isLayoutRtl) {
                left = selectedEnd;
                right = selectedStart;
            } else {
                left = selectedStart;
                right = selectedEnd;
            }

            int color = tabColorizer.getIndicatorColor(selectedPosition);
            float thickness = tabIndicatorHeight;

            if (selectionOffset > 0f && selectedPosition < (getChildCount() - 1)) {
                int nextColor = tabColorizer.getIndicatorColor(selectedPosition + 1);
                if (color != nextColor) {
                    color = blendColors(nextColor, color, selectionOffset);
                }

                // Draw the selection partway between the tabs
                float startOffset = indicationInterpolator.getLeftEdge(selectionOffset);
                float endOffset = indicationInterpolator.getRightEdge(selectionOffset);
                float thicknessOffset = indicationInterpolator.getThickness(selectionOffset);

                View nextTab = getChildAt(selectedPosition + 1);
                int nextStart = Utils.getStart(nextTab, tabIndicatorWithoutPadding);
                int nextEnd = Utils.getEnd(nextTab, tabIndicatorWithoutPadding);
                if (isLayoutRtl) {
                    left = (int) (endOffset * nextEnd + (1.0f - endOffset) * left);
                    right = (int) (startOffset * nextStart + (1.0f - startOffset) * right);
                } else {
                    left = (int) (startOffset * nextStart + (1.0f - startOffset) * left);
                    right = (int) (endOffset * nextEnd + (1.0f - endOffset) * right);
                }
                thickness = thickness * thicknessOffset;
            }

            drawIndicator(canvas, left, right, height, thickness, color);

        }

        if (!tabIndicatorInFront) {
            drawOverline(canvas, 0, width);
            drawUnderline(canvas, 0, getWidth(), height);
        }

        // Vertical separators between the titles
        drawSeparator(canvas, height, tabCount);

    }

    private void drawSeparator(Canvas canvas, int height, int tabCount) {
        if (tabDividerHeight <= 0) {
            return;
        }

        final int dividerHeightPx = (int) (Math.min(Math.max(0f, dividerHeight), 1f) * height);
        final SmartTabLayout.TabColorizer tabColorizer = getTabColorizer();

        // Vertical separators between the titles
        final int separatorTop = (height - dividerHeightPx) / 2;
        final int separatorBottom = separatorTop + dividerHeightPx;

        final boolean isLayoutRtl = Utils.isLayoutRtl(this);
        for (int i = 0; i < tabCount - 1; i++) {
            View child = getChildAt(i);
            int end = Utils.getEnd(child);
            int endMargin = Utils.getMarginEnd(child);
            int separatorX = isLayoutRtl ? end - endMargin : end + endMargin;
            dividerPaint.setColor(tabColorizer.getDividerColor(i));
            canvas.drawLine(separatorX, separatorTop, separatorX, separatorBottom, dividerPaint);
        }
    }

    private void drawIndicator(Canvas canvas, int left, int right, int height, float thickness,
                               int color) {
        if (tabIndicatorHeight <= 0 || tabIndicatorWidth == 0) {
            return;
        }

        float center;
        float top;
        float bottom;

        switch (tabIndicatorGravity) {
            case GRAVITY_TOP:
                center = tabIndicatorHeight / 2f;
                top = center - (thickness / 2f);
                bottom = center + (thickness / 2f);
                break;
            case GRAVITY_CENTER:
                center = height / 2f;
                top = center - (thickness / 2f);
                bottom = center + (thickness / 2f);
                break;
            case GRAVITY_BOTTOM:
            default:
                center = height - (tabIndicatorHeight / 2f);
                top = center - (thickness / 2f);
                bottom = center + (thickness / 2f);
        }

        indicatorPaint.setColor(color);
        if (tabIndicatorWidth == -1) {
            indicatorRectF.set(left, top, right, bottom);
        } else {
            float padding = (Math.abs(left - right) - tabIndicatorWidth) / 2f;
            indicatorRectF.set(left + padding, top, right - padding, bottom);
        }

        canvas.drawRoundRect(
                indicatorRectF, tabIndicatorHeight / 2,
                tabIndicatorHeight / 2, indicatorPaint);
    }

    private void drawOverline(Canvas canvas, int left, int right) {
        if (topLineHeight <= 0) {
            return;
        }
        // Thin overline along the entire top edge
        borderPaint.setColor(topLineColor);
        canvas.drawRect(left, 0, right, topLineHeight, borderPaint);
    }

    private void drawUnderline(Canvas canvas, int left, int right, int height) {
        if (bottomLineHeight <= 0) {
            return;
        }
        // Thin underline along the entire bottom edge
        borderPaint.setColor(bottomLineColor);
        canvas.drawRect(left, height - bottomLineHeight, right, height, borderPaint);
    }

    private static class SimpleTabColorizer implements SmartTabLayout.TabColorizer {

        private int[] indicatorColors;
        private int[] dividerColors;

        @Override
        public final int getIndicatorColor(int position) {
            return indicatorColors[position % indicatorColors.length];
        }

        @Override
        public final int getDividerColor(int position) {
            return dividerColors[position % dividerColors.length];
        }

        void setIndicatorColors(int... colors) {
            indicatorColors = colors;
        }

        void setDividerColors(int... colors) {
            dividerColors = colors;
        }
    }
}
