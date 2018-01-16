package com.better.animator.modules.dynamic2d;

import com.better.animator.R;
import com.better.animator.base.BaseRecyclerFragment;
import com.better.anime.dynamic2d.WaveView;

import butterknife.BindView;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: WaveViewFragment6.java
 * Author: Liang
 * Create: 2018/1/15 22:40
 * <p>
 * Changes (from 2018/1/15)
 * -----------------------------------------------------------------
 * 2018/1/15 : Create WaveViewFragment6.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public class WaveViewFragment2 extends BaseRecyclerFragment {
    @BindView(R.id.wave1)
    WaveView wave1;

    @Override
    public int setViewId() {
        return R.layout.fragment_2d_waveview;
    }

    @Override
    public void initData() {
/*
        wave1.setBorder(5, Color.parseColor("#660066"));
        wave1.post(() -> {
            ValueAnimator waveAnimator1 = ObjectAnimator.ofFloat(wave1, "offset", 0, wave1.getWidth());
            ValueAnimator waveAnimator2 = ObjectAnimator.ofFloat(wave1, "waveLevel", 0, 1);

            waveAnimator1.setRepeatCount(ValueAnimator.INFINITE);
            waveAnimator1.setRepeatMode(ValueAnimator.RESTART);
            waveAnimator1.setDuration(3000);
            waveAnimator2.setDuration(12000);


            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setInterpolator(new LinearInterpolator());
            animatorSet.play(waveAnimator1).with(waveAnimator2);
            animatorSet.start();
        });
*/

    }
}
