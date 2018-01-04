package com.better.animator;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.better.animator.base.BaseFragment;
import com.better.animator.modules.dynamic2d.WaveView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2017, by Better, All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: Dynamic2DFragment.java
 * Author: lianghuiyong@outlook.com
 * Create: 2017/12/18 上午10:49
 *
 * Changes (from 2017/12/18)
 * -----------------------------------------------------------------
 * 2017/12/18 : Create Dynamic2DFragment.java (梁惠涌);
 * -----------------------------------------------------------------
 */
public class Dynamic2DFragment extends BaseFragment {

    @BindView(R.id.wave_view)
    WaveView waveView;
    Unbinder unbinder;

    @Override
    public int setViewId() {
        return R.layout.fragment_dynamic_2d;
    }

    @Override
    public void initData() {
        List<Animator> animators = new ArrayList<>();

        // 让波形一直向右移动，效果就是波形一直在波动。
        ObjectAnimator waveShiftAnim = ObjectAnimator.ofFloat(
                waveView, "waveShiftRatio", 0f, 1f);
        waveShiftAnim.setRepeatCount(ValueAnimator.INFINITE);
        waveShiftAnim.setDuration(2000);
        waveShiftAnim.setInterpolator(new LinearInterpolator());
        animators.add(waveShiftAnim);

        // 让水位从0逐渐涨到WaveView高度的一半。
        ObjectAnimator waterLevelAnim = ObjectAnimator.ofFloat(waveView, "waterLevelRatio", 0.3f, 0.5f);
        waterLevelAnim.setRepeatCount(ValueAnimator.INFINITE);
        waterLevelAnim.setRepeatMode(ValueAnimator.REVERSE);
        waterLevelAnim.setDuration(10000);
        waterLevelAnim.setInterpolator(new DecelerateInterpolator());
        animators.add(waterLevelAnim);

        // 行波浪的大小从大变小，再从小变大。
        ObjectAnimator amplitudeAnim = ObjectAnimator.ofFloat(
                waveView, "amplitudeRatio", 0.05f, 0.2f);
        amplitudeAnim.setRepeatCount(ValueAnimator.INFINITE);
        amplitudeAnim.setRepeatMode(ValueAnimator.REVERSE);
        amplitudeAnim.setDuration(10000);
        amplitudeAnim.setInterpolator(new LinearInterpolator());
        animators.add(amplitudeAnim);

        AnimatorSet mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(animators);

        waveView.setWaveColor(Color.parseColor("#e0d5c8"), Color.parseColor("#e0d2c3"));
        mAnimatorSet.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
