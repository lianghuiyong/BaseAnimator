package com.better.animator;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;

import com.better.animator.base.BaseFragment;
import com.better.animator.modules.dynamic2d.WaveView;
import com.better.anime.dynamic2d.WaveTest;

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
    @BindView(R.id.wave)
    WaveTest wave;

    @Override
    public int setViewId() {
        return R.layout.fragment_dynamic_2d;
    }

    @Override
    public void initData() {
        waveView.setWaveColor(Color.parseColor("#e0d5c8"), Color.parseColor("#e0d2c3"));

        wave.setBorder(5, Color.parseColor("#660066"));
        wave.post(() -> {
            ValueAnimator waveAnimator1 = ObjectAnimator.ofFloat(wave, "offset", 0, wave.getWidth());
            ValueAnimator waveAnimator2 = ObjectAnimator.ofFloat(wave, "waveLevel", 0, 1);

            waveAnimator1.setRepeatCount(ValueAnimator.INFINITE);
            waveAnimator1.setRepeatMode(ValueAnimator.RESTART);
            waveAnimator1.setDuration(3000);
            waveAnimator2.setDuration(12000);


            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setInterpolator(new LinearInterpolator());
            animatorSet.play(waveAnimator1).with(waveAnimator2);
            animatorSet.start();
        });
    }
}
