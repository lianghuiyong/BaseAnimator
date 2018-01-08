package com.better.animator;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
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
    Unbinder unbinder;
    @BindView(R.id.wave)
    WaveTest wave;

    @Override
    public int setViewId() {
        return R.layout.fragment_dynamic_2d;
    }

    @Override
    public void initData() {
        waveView.setWaveColor(Color.parseColor("#e0d5c8"), Color.parseColor("#e0d2c3"));

        wave.post(() -> {
            ValueAnimator waveAnimator = ObjectAnimator.ofFloat(wave, "offset", 0, wave.getWidth());
            waveAnimator.setInterpolator(new LinearInterpolator());
            waveAnimator.setDuration(3000);
            waveAnimator.setRepeatCount(ValueAnimator.INFINITE);
            waveAnimator.setRepeatMode(ValueAnimator.RESTART);
            waveAnimator.start();
        });
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
