package com.better.animator.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by liang on 2018/3/15.
 */

public class DisplayUtil {
    /**
     * 获取手机屏幕高度,以px为单位
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    /**
     * 获取手机屏幕宽度，以px为单位
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * 返回程序window宽度
     */
    public static int getWindowWidth(Activity activity) {
        return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getWidth();
    }

    /**
     * 返回程序window高度，不包括通知栏和标题栏
     */
    public static int getWindowContentHeight(Activity activity) {
        return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getHeight();
    }

    /**
     * 返回程序window高度，不包括通知栏
     */
    public static int getWindowHeight(Activity activity) {
        return getScreenHeight(activity) - getStatusBarHeight(activity);
    }

    /**
     * 返回屏幕像素密度
     */
    public static float getPixelDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 返回真实屏幕像素密度
     */
    public static float getRealPixelDensity(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float averageDpi = (metrics.xdpi + metrics.ydpi) / 2.0F;
        return averageDpi / 160f;
    }

    /**
     * 返回状态栏高度
     */
    public static int getStatusBarHeight(Activity activity) {
        Rect outRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        return outRect.top;
    }

    public static int getTitleBarHeight(Activity activity) {
        return getScreenHeight(activity) - getWindowContentHeight(activity) - getStatusBarHeight(
                activity);
    }

    /**
     * 单位转换，将dip转换为px
     */
    public static int dip2px(float dp, Context context) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 单位转换，将px转换为dip
     */
    public static int px2dip(float px, Context context) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

}
