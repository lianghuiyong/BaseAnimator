package com.better.animator.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.lang.reflect.Method;

/**
 * Created by liang on 2018/3/15.
 */

public class SoftHideKeyBoardUtil {
    // For more information, see https://code.google.com/p/android/issues/detail?id=5497
    // To use this class, simply invoke assistActivity() on an Activity that already has its content view set.


    public static void assistActivity(View content) {
        new SoftHideKeyBoardUtil(content);
    }

    private View mChildOfContent;
    private int usableHeightPrevious;
    private int firstViewHeight;
    private boolean isFirst = true;
    private ViewGroup.LayoutParams frameLayoutParams;

    private SoftHideKeyBoardUtil(View content) {
        mChildOfContent = content;
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {

                Log.e("lhy", "computeUsableHeight() = " + computeUsableHeight());
                possiblyResizeChildOfContent();
            }
        });
        frameLayoutParams = mChildOfContent.getLayoutParams();
    }

    private void possiblyResizeChildOfContent() {


        int usableHeightNow = (int) (computeUsableHeight() - mChildOfContent.getY());

        if (!isFirst && usableHeightNow != usableHeightPrevious) {


            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard / 4)) {
                // keyboard became visible
                frameLayoutParams.height = usableHeightNow - BarUtils.getStatusBarHeight(mChildOfContent.getContext());
            } else {
                // keyboard became hide
                frameLayoutParams.height = firstViewHeight;
            }

            frameLayoutParams.height = usableHeightNow;
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }

        if (isFirst) {
            firstViewHeight = frameLayoutParams.height;
            isFirst = false;
        }
    }

    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return r.bottom - r.top;
    }


}
