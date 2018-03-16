package com.better.animator.utils;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;

/**
 * Created by liang on 2018/3/15.
 */

public class SoftHideKeyBoardUtil {

    public static void assistActivity(View content) {
        new SoftHideKeyBoardUtil(content);
    }

    private View mChildOfContent;

    //键盘显示与隐藏时的高度
    private int viewHeightOnKeyShow;
    private int viewHeightOnKeyHide;

    //记录布局显示的宽高
    private int viewHeightOld;
    private int viewHeightNew;

    private boolean isFirst = true;

    private SoftHideKeyBoardUtil(View content) {
        mChildOfContent = content;
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                possiblyResizeChildOfContent();
            }
        });
    }

    private void possiblyResizeChildOfContent() {

        viewHeightNew = (int) (computeUsableHeight() - mChildOfContent.getY());

        if (!isFirst && viewHeightNew != viewHeightOld) {

            if (viewHeightOnKeyHide - viewHeightNew > 100) {

                // keyboard became visible
                viewHeightOnKeyShow = viewHeightNew;
                changeHeight(mChildOfContent, viewHeightOnKeyHide, viewHeightOnKeyShow);

            } else {
                // keyboard became hide
                changeHeight(mChildOfContent, viewHeightOnKeyShow, viewHeightOnKeyHide);
            }

            viewHeightOld = viewHeightNew;
        }

        if (isFirst) {
            viewHeightOnKeyHide = mChildOfContent.getHeight();
            isFirst = false;
        }
    }

    private void changeHeight(final View view, final int statHeight, final int endHeight) {

        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = statHeight + (int) (currentValue * (endHeight - statHeight));
                view.setLayoutParams(params);
            }
        });
        animator.setDuration(180).start();
    }

    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return r.bottom;
    }


}
