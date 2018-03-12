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
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.better.anime.R;


/**
 * ATTR	描述
 * tabIndicatorAlwaysInCenter	    如果设置为true，默认为false
 * tabIndicatorWithoutPadding	    如果设置为true，则绘制指示符，而不填充tab，默认为false
 * tabIndicatorInFront	            在下划线前绘制指标，默认为false
 *
 * tabIndicatorGravity	            指示器的绘图位置：“底部”或“顶部”或“中心”，默认“底部”
 *
 * tabIndicatorHeight	            指示灯的高度，默认为 b_dp2
 * tabIndicatorWidth	            指示灯的宽度，默认为“自动”
 *
 * topLineColor     	            顶线颜色
 * topLineHeight        	        顶线的厚度
 * bottomLineColor  	            底线颜色
 * bottomLineHeight     	        底线厚度
 *
 * tabDividerColor	                分隔线的颜色
 * tabDividerHeight	                分隔线的厚度
 *
 * tabBackground        	        每个标签的背景画。一般来说，它设置StateListDrawable
 * tabViewTextAllCaps       	    如果设置为true，则所有选项卡标题都将大写，默认为true
 * tabTextColor         	        默认情况下包含的选项卡的文本颜色
 * tabTextSize          	        默认情况下包含的选项卡的文本大小
 * tabViewTextHorizontalPadding 	默认情况下包含的选项卡的文本布局填充
 * tabTextMinWidth          	    标签的最小宽度
 * tabLayoutId              	    布局ID定义自定义选项卡。如果不指定布局，请使用默认选项卡
 * tabTextViewId        	        自定义选项卡布局中的文本视图ID。如果没有使用customTabTextLayoutId定义，则不起作用
 * tabDistributeEvenly	            如果设置为true，则每个选项卡的权重相同，默认为false
 * tabClickable 	                如果设置为false，则禁用选项卡单击的选择，默认为true
 * tabTitleOffset	                如果设置为“auto_center”，则中间的选项卡的滑动位置将保持在中间。如果指定尺寸，它将从左边缘偏移，默认为24dp
 * */

public class SmartTabLayout extends HorizontalScrollView {
    private static final int TITLE_OFFSET_AUTO_CENTER = -1;

    protected final SmartTabStrip tabStrip;
    private int tabTitleOffset;
    private int tabBackground;
    private boolean tabViewTextAllCaps;
    private int tabTextColor;
    private float tabTextSize;
    private int tabViewTextHorizontalPadding;
    private int tabTextMinWidth;
    private ViewPager viewPager;
    private ViewPager.OnPageChangeListener viewPagerPageChangeListener;
    private OnScrollChangeListener onScrollChangeListener;
    private TabProvider tabProvider;
    private InternalTabClickListener internalTabClickListener;
    private OnTabClickListener onTabClickListener;
    private boolean tabDistributeEvenly;

    public SmartTabLayout(Context context) {
        this(context, null);
    }

    public SmartTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmartTabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        // Disable the Scroll Bar
        setHorizontalScrollBarEnabled(false);

        int tabLayoutId;
        int tabTextViewId;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BetterTabLayout, defStyle, 0);
        tabBackground = a.getResourceId(R.styleable.BetterTabLayout_tabBackground, NO_ID);
        tabViewTextAllCaps = a.getBoolean(R.styleable.BetterTabLayout_tabTextAllCaps, true);
        tabTextColor = a.getColor(R.styleable.BetterTabLayout_tabTextColor, Color.parseColor("#FC000000"));
        tabTextSize = a.getDimension(R.styleable.BetterTabLayout_tabTextSize, getResources().getDimensionPixelSize(R.dimen.b_sp12));
        tabViewTextHorizontalPadding = a.getDimensionPixelSize(R.styleable.BetterTabLayout_tabTextHorizontalPadding, getResources().getDimensionPixelSize(R.dimen.b_dp16));
        tabTextMinWidth = a.getDimensionPixelSize(R.styleable.BetterTabLayout_tabTextMinWidth, 0);
        tabLayoutId = a.getResourceId(R.styleable.BetterTabLayout_tabLayoutId, NO_ID);
        tabTextViewId = a.getResourceId(R.styleable.BetterTabLayout_tabTextViewId, NO_ID);
        tabDistributeEvenly = a.getBoolean(R.styleable.BetterTabLayout_tabDistributeEvenly, false);
        boolean clickable = a.getBoolean(R.styleable.BetterTabLayout_tabClickable, true);
        tabTitleOffset = a.getLayoutDimension(R.styleable.BetterTabLayout_tabTitleOffset, getResources().getDimensionPixelSize(R.dimen.b_dp24));
        a.recycle();

        this.internalTabClickListener = clickable ? new InternalTabClickListener() : null;

        if (tabLayoutId != NO_ID) {
            setCustomTabView(tabLayoutId, tabTextViewId);
        }

        this.tabStrip = new SmartTabStrip(context, attrs);

        if (tabDistributeEvenly && tabStrip.isIndicatorAlwaysInCenter()) {
            throw new UnsupportedOperationException(
                    "'tabDistributeEvenly' and 'indicatorAlwaysInCenter' both use does not support");
        }

        // Make sure that the Tab Strips fills this View
        setFillViewport(!tabStrip.isIndicatorAlwaysInCenter());

        addView(tabStrip, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollChangeListener != null) {
            onScrollChangeListener.onScrollChanged(l, oldl);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (tabStrip.isIndicatorAlwaysInCenter() && tabStrip.getChildCount() > 0) {
            View firstTab = tabStrip.getChildAt(0);
            View lastTab = tabStrip.getChildAt(tabStrip.getChildCount() - 1);
            int start = (w - Utils.getMeasuredWidth(firstTab)) / 2 - Utils.getMarginStart(firstTab);
            int end = (w - Utils.getMeasuredWidth(lastTab)) / 2 - Utils.getMarginEnd(lastTab);
            tabStrip.setMinimumWidth(tabStrip.getMeasuredWidth());
            ViewCompat.setPaddingRelative(this, start, getPaddingTop(), end, getPaddingBottom());
            setClipToPadding(false);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        // Ensure first scroll
        if (changed && viewPager != null) {
            scrollToTab(viewPager.getCurrentItem(), 0);
        }
    }


    public void setIndicationInterpolator(SmartIndicationInterpolator interpolator) {
        tabStrip.setIndicationInterpolator(interpolator);
    }

    /**
     * Set the custom {@link TabColorizer} to be used.
     *
     * If you only require simple customisation then you can use
     * {@link #setSelectedIndicatorColors(int...)} and {@link #setDividerColors(int...)} to achieve
     * similar effects.
     */
    public void setCustomTabColorizer(TabColorizer tabColorizer) {
        tabStrip.setCustomTabColorizer(tabColorizer);
    }

    /**
     * Set the color used for styling the tab text. This will need to be called prior to calling
     * {@link #setViewPager(ViewPager)} otherwise it will not get set
     *
     * @param color to use for tab text
     */
    public void setDefaultTabTextColor(@ColorInt int color) {
        tabTextColor = color;
    }

    /**
     * Set the same weight for tab
     */
    public void setDistributeEvenly(boolean tabDistributeEvenly) {
        this.tabDistributeEvenly = tabDistributeEvenly;
    }

    /**
     * Sets the colors to be used for indicating the selected tab. These colors are treated as a
     * circular array. Providing one color will mean that all tabs are indicated with the same color.
     */
    public void setSelectedIndicatorColors(int... colors) {
        tabStrip.setSelectedIndicatorColors(colors);
    }

    /**
     * Sets the colors to be used for tab dividers. These colors are treated as a circular array.
     * Providing one color will mean that all tabs are indicated with the same color.
     */
    public void setDividerColors(int... colors) {
        tabStrip.setDividerColors(colors);
    }

    /**
     * Set the {@link ViewPager.OnPageChangeListener}. When using {@link SmartTabLayout} you are
     * required to set any {@link ViewPager.OnPageChangeListener} through this method. This is so
     * that the layout can update it's scroll position correctly.
     *
     * @see ViewPager#setOnPageChangeListener(ViewPager.OnPageChangeListener)
     */
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        viewPagerPageChangeListener = listener;
    }

    /**
     * Set {@link OnScrollChangeListener} for obtaining values of scrolling.
     *
     * @param listener the {@link OnScrollChangeListener} to set
     */
    public void setOnScrollChangeListener(OnScrollChangeListener listener) {
        onScrollChangeListener = listener;
    }

    /**
     * Set {@link OnTabClickListener} for obtaining click event.
     *
     * @param listener the {@link OnTabClickListener} to set
     */
    public void setOnTabClickListener(OnTabClickListener listener) {
        onTabClickListener = listener;
    }

    /**
     * Set the custom layout to be inflated for the tab views.
     *
     * @param layoutResId Layout id to be inflated
     * @param textViewId id of the {@link TextView} in the inflated view
     */
    public void setCustomTabView(int layoutResId, int textViewId) {
        tabProvider = new SimpleTabProvider(getContext(), layoutResId, textViewId);
    }

    /**
     * Set the custom layout to be inflated for the tab views.
     *
     * @param provider {@link TabProvider}
     */
    public void setCustomTabView(TabProvider provider) {
        tabProvider = provider;
    }

    /**
     * Sets the associated view pager. Note that the assumption here is that the pager content
     * (number of tabs and tab titles) does not change after this call has been made.
     */
    public void setViewPager(ViewPager viewPager) {
        tabStrip.removeAllViews();

        this.viewPager = viewPager;
        if (viewPager != null && viewPager.getAdapter() != null) {
            viewPager.addOnPageChangeListener(new InternalViewPagerListener());
            populateTabStrip();
        }
    }

    /**
     * Returns the view at the specified position in the tabs.
     *
     * @param position the position at which to get the view from
     * @return the view at the specified position or null if the position does not exist within the
     * tabs
     */
    public View getTabAt(int position) {
        return tabStrip.getChildAt(position);
    }

    /**
     * Create a default view to be used for tabs. This is called if a custom tab view is not set via
     * {@link #setCustomTabView(int, int)}.
     */
    protected TextView createDefaultTabView(CharSequence title) {
        TextView textView = new TextView(getContext());
        textView.setGravity(Gravity.CENTER);
        textView.setText(title);
        textView.setTextColor(tabTextColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSize);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));

        if (tabBackground != NO_ID) {
            textView.setBackgroundResource(tabBackground);
        } else {
            // If we're running on Honeycomb or newer, then we can use the Theme's
            // selectableItemBackground to ensure that the View has a pressed state
            TypedValue outValue = new TypedValue();
            getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground,
                    outValue, true);
            textView.setBackgroundResource(outValue.resourceId);
        }

        // If we're running on ICS or newer, enable all-caps to match the Action Bar tab style
        textView.setAllCaps(tabViewTextAllCaps);

        textView.setPadding(
                tabViewTextHorizontalPadding, 0,
                tabViewTextHorizontalPadding, 0);

        if (tabTextMinWidth > 0) {
            textView.setMinWidth(tabTextMinWidth);
        }

        return textView;
    }

    private void populateTabStrip() {
        final PagerAdapter adapter = viewPager.getAdapter();

        for (int i = 0; i < adapter.getCount(); i++) {

            final View tabView = (tabProvider == null)
                    ? createDefaultTabView(adapter.getPageTitle(i))
                    : tabProvider.createTabView(tabStrip, i, adapter);

            if (tabView == null) {
                throw new IllegalStateException("tabView is null.");
            }

            if (tabDistributeEvenly) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                lp.width = 0;
                lp.weight = 1;
            }

            if (internalTabClickListener != null) {
                tabView.setOnClickListener(internalTabClickListener);
            }

            tabStrip.addView(tabView);

            if (i == viewPager.getCurrentItem()) {
                tabView.setSelected(true);
            }

        }
    }

    private void scrollToTab(int tabIndex, float positionOffset) {
        final int tabStripChildCount = tabStrip.getChildCount();
        if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount) {
            return;
        }

        final boolean isLayoutRtl = Utils.isLayoutRtl(this);
        View selectedTab = tabStrip.getChildAt(tabIndex);
        int widthPlusMargin = Utils.getWidth(selectedTab) + Utils.getMarginHorizontally(selectedTab);
        int extraOffset = (int) (positionOffset * widthPlusMargin);

        if (tabStrip.isIndicatorAlwaysInCenter()) {

            if (0f < positionOffset && positionOffset < 1f) {
                View nextTab = tabStrip.getChildAt(tabIndex + 1);
                int selectHalfWidth = Utils.getWidth(selectedTab) / 2 + Utils.getMarginEnd(selectedTab);
                int nextHalfWidth = Utils.getWidth(nextTab) / 2 + Utils.getMarginStart(nextTab);
                extraOffset = Math.round(positionOffset * (selectHalfWidth + nextHalfWidth));
            }

            View firstTab = tabStrip.getChildAt(0);
            int x;
            if (isLayoutRtl) {
                int first = Utils.getWidth(firstTab) + Utils.getMarginEnd(firstTab);
                int selected = Utils.getWidth(selectedTab) + Utils.getMarginEnd(selectedTab);
                x = Utils.getEnd(selectedTab) - Utils.getMarginEnd(selectedTab) - extraOffset;
                x -= (first - selected) / 2;
            } else {
                int first = Utils.getWidth(firstTab) + Utils.getMarginStart(firstTab);
                int selected = Utils.getWidth(selectedTab) + Utils.getMarginStart(selectedTab);
                x = Utils.getStart(selectedTab) - Utils.getMarginStart(selectedTab) + extraOffset;
                x -= (first - selected) / 2;
            }

            scrollTo(x, 0);
            return;

        }

        int x;
        if (tabTitleOffset == TITLE_OFFSET_AUTO_CENTER) {

            if (0f < positionOffset && positionOffset < 1f) {
                View nextTab = tabStrip.getChildAt(tabIndex + 1);
                int selectHalfWidth = Utils.getWidth(selectedTab) / 2 + Utils.getMarginEnd(selectedTab);
                int nextHalfWidth = Utils.getWidth(nextTab) / 2 + Utils.getMarginStart(nextTab);
                extraOffset = Math.round(positionOffset * (selectHalfWidth + nextHalfWidth));
            }

            if (isLayoutRtl) {
                x = -Utils.getWidthWithMargin(selectedTab) / 2 + getWidth() / 2;
                x -= Utils.getPaddingStart(this);
            } else {
                x = Utils.getWidthWithMargin(selectedTab) / 2 - getWidth() / 2;
                x += Utils.getPaddingStart(this);
            }

        } else {

            if (isLayoutRtl) {
                x = (tabIndex > 0 || positionOffset > 0) ? tabTitleOffset : 0;
            } else {
                x = (tabIndex > 0 || positionOffset > 0) ? -tabTitleOffset : 0;
            }

        }

        int start = Utils.getStart(selectedTab);
        int startMargin = Utils.getMarginStart(selectedTab);
        if (isLayoutRtl) {
            x += start + startMargin - extraOffset - getWidth() + Utils.getPaddingHorizontally(this);
        } else {
            x += start - startMargin + extraOffset;
        }

        scrollTo(x, 0);

    }

    /**
     * Allows complete control over the colors drawn in the tab layout. Set with
     * {@link #setCustomTabColorizer(TabColorizer)}.
     */
    public interface TabColorizer {

        /**
         * @return return the color of the indicator used when {@code position} is selected.
         */
        int getIndicatorColor(int position);

        /**
         * @return return the color of the divider drawn to the right of {@code position}.
         */
        int getDividerColor(int position);

    }

    /**
     * Interface definition for a callback to be invoked when the scroll position of a view changes.
     */
    public interface OnScrollChangeListener {

        /**
         * Called when the scroll position of a view changes.
         *
         * @param scrollX Current horizontal scroll origin.
         * @param oldScrollX Previous horizontal scroll origin.
         */
        void onScrollChanged(int scrollX, int oldScrollX);
    }

    /**
     * Interface definition for a callback to be invoked when a tab is clicked.
     */
    public interface OnTabClickListener {

        /**
         * Called when a tab is clicked.
         *
         * @param position tab's position
         */
        void onTabClicked(int position);
    }

    public interface TabProvider {

        /**
         * @return Return the View of {@code position} for the Tabs
         */
        View createTabView(ViewGroup container, int position, PagerAdapter adapter);

    }

    private static class SimpleTabProvider implements TabProvider {

        private final LayoutInflater inflater;
        private final int tabViewLayoutId;
        private final int tabViewTextViewId;

        private SimpleTabProvider(Context context, int layoutResId, int textViewId) {
            inflater = LayoutInflater.from(context);
            tabViewLayoutId = layoutResId;
            tabViewTextViewId = textViewId;
        }

        @Override
        public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
            View tabView = null;
            TextView tabTitleView = null;

            if (tabViewLayoutId != NO_ID) {
                tabView = inflater.inflate(tabViewLayoutId, container, false);
            }

            if (tabViewTextViewId != NO_ID && tabView != null) {
                tabTitleView = (TextView) tabView.findViewById(tabViewTextViewId);
            }

            if (tabTitleView == null && TextView.class.isInstance(tabView)) {
                tabTitleView = (TextView) tabView;
            }

            if (tabTitleView != null) {
                tabTitleView.setText(adapter.getPageTitle(position));
            }

            return tabView;
        }

    }

    private class InternalViewPagerListener implements ViewPager.OnPageChangeListener {

        private int scrollState;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int tabStripChildCount = tabStrip.getChildCount();
            if ((tabStripChildCount == 0) || (position < 0) || (position >= tabStripChildCount)) {
                return;
            }

            tabStrip.onViewPagerPageChanged(position, positionOffset);

            scrollToTab(position, positionOffset);

            if (viewPagerPageChangeListener != null) {
                viewPagerPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            scrollState = state;

            if (viewPagerPageChangeListener != null) {
                viewPagerPageChangeListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (scrollState == ViewPager.SCROLL_STATE_IDLE) {
                tabStrip.onViewPagerPageChanged(position, 0f);
                scrollToTab(position, 0);
            }

            for (int i = 0, size = tabStrip.getChildCount(); i < size; i++) {
                tabStrip.getChildAt(i).setSelected(position == i);
            }

            if (viewPagerPageChangeListener != null) {
                viewPagerPageChangeListener.onPageSelected(position);
            }
        }

    }

    private class InternalTabClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < tabStrip.getChildCount(); i++) {
                if (v == tabStrip.getChildAt(i)) {
                    if (onTabClickListener != null) {
                        onTabClickListener.onTabClicked(i);
                    }
                    viewPager.setCurrentItem(i);
                    return;
                }
            }
        }
    }

}
