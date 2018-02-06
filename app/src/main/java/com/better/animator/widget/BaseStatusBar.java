package com.better.animator.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.better.animator.R;
import com.better.animator.utils.BarUtils;
import com.better.animator.utils.RomUtils;

/**
 * Created by lianghuiyong on 2017/9/22.
 */

public class BaseStatusBar extends FrameLayout {

    private View common_status_bar;

    public BaseStatusBar(@NonNull Context context) {
        this(context, null);
    }

    public BaseStatusBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseStatusBar(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCustomAttrs(context, attrs, defStyleAttr);
    }

    public void initCustomAttrs(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.layout_status_bar_back, this);
        common_status_bar = layoutView.findViewById(R.id.common_status_bar);

        if (layoutView.isInEditMode()) {
            return;
        }
    }

    //适配Android状态栏的字体颜色为黑色，或者背景暗色
    public void setStatusBarDark(Activity activity) {
        if (RomUtils.isMiui() && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            BarUtils.MIUISetStatusBarFontDark(activity.getWindow(), true);
        } else if (RomUtils.isFlyme()) {
            BarUtils.FlymeSetStatusBarFontDark(activity.getWindow(), true);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            common_status_bar.setBackgroundResource(R.drawable.shape_base_status_bar_background);
        }
    }

    //适配Android状态栏的字体颜色为黑色，或者背景暗色
    public void setStatusBarLight(Activity activity) {
        if (RomUtils.isMiui() && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            BarUtils.MIUISetStatusBarFontDark(activity.getWindow(), false);
        } else if (RomUtils.isFlyme()) {
            BarUtils.FlymeSetStatusBarFontDark(activity.getWindow(), false);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }
}
