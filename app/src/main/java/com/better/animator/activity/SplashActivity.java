package com.better.animator.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.better.animator.R;
import com.better.animator.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: SplashActivity.java
 * Author: Liang
 * Create: 2018/1/27 19:52
 * <p>
 * Changes (from 2018/1/27)
 * -----------------------------------------------------------------
 * 2018/1/27 : Create SplashActivity.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public class SplashActivity extends BaseActivity {
    @BindView(R.id.text)
    TextView text;

    @Override
    public int setViewId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initData() {
        ValueAnimator animator = ObjectAnimator.ofFloat(text, "alpha", 1, 0.5f);
        animator.setDuration(2000);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                startActivity(new Intent(getContext(), HomePageActivity.class));
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_splash_enter,R.anim.activity_splash_exit);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
