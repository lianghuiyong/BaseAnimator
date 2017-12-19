package com.better.baseanimator;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.better.baseanimator.adapter.InterpolatorAdapter;
import com.better.baseanimator.base.BaseFragment;
import com.better.baseanimator.view.CoordinateView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

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
 * File: InterpolatorFragment.java
 * Author: lianghuiyong@outlook.com
 * Create: 2017/12/18 上午10:49
 *
 * Changes (from 2017/12/18)
 * -----------------------------------------------------------------
 * 2017/12/18 : Create InterpolatorFragment.java (梁惠涌);
 * -----------------------------------------------------------------
 */
public class InterpolatorFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.coordinate)
    CoordinateView coordinate;

    @Override
    public int setViewId() {
        return R.layout.fragment_interpolator;
    }

    @Override
    public void initData() {
        InterpolatorAdapter adapter = new InterpolatorAdapter();
        recyclerView.setAdapter(adapter);

        List<String> list = new ArrayList<>();
        list.add("AnticipateInterpolator");
        list.add("DecelerateInterpolator");
        adapter.setNewData(list);

        ValueAnimator process = ObjectAnimator.ofFloat(coordinate, "process", 0.0f, 1.0f);
        process.setDuration(1000);
        //ValueAnimator time = ObjectAnimator.ofFloat(coordinate, "time", 0.0f, 1.0f);

        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                //设置标题
                coordinate.clear();
                coordinate.setTitle((String) adapter.getData().get(position));

                switch (position) {
                    case 0:
                        process.setInterpolator(new AnticipateInterpolator());
                        break;
                    case 1:
                        process.setInterpolator(new DecelerateInterpolator());
                        break;

                }
                //开启动画
                process.start();
            }
        });
    }
}
