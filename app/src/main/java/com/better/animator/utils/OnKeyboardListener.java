package com.better.animator.utils;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: OnKeyboardListener.java
 * Author: Better
 * Create: 2018/3/16 10:16
 * <p>
 * Changes (from 2018/3/16)
 * -----------------------------------------------------------------
 * 2018/3/16 : Create OnKeyboardListener.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public interface OnKeyboardListener {
    /**
     * On keyboard change.
     *
     * @param isPopup        the is popup  是否弹出
     * @param keyboardHeight the keyboard height  软键盘高度
     */
    void onKeyboardChange(boolean isPopup, int keyboardHeight);
}