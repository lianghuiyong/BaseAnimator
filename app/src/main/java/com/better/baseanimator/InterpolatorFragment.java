package com.better.baseanimator;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.better.baseanimator.adapter.InterpolatorAdapter;
import com.better.baseanimator.base.BaseFragment;
import com.better.baseanimator.bean.ListInfoBean;
import com.better.baseanimator.view.CoordinateView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


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

        List<ListInfoBean> list = new ArrayList<>();
        list.add(new ListInfoBean("AccelerateDecelerateInterpolator", "先加速再减速"));
        list.add(new ListInfoBean("LinearInterpolator", "匀速"));
        list.add(new ListInfoBean("AccelerateInterpolator", "持续加速"));
        list.add(new ListInfoBean("DecelerateInterpolator", "持续减速直到 0"));
        list.add(new ListInfoBean("AnticipateInterpolator", "先回拉再正常动画轨迹"));
        list.add(new ListInfoBean("OvershootInterpolator", "动画会超过目标值再弹回来"));
        list.add(new ListInfoBean("AnticipateOvershootInterpolator", "开始回拉，最后回弹"));
        list.add(new ListInfoBean("BounceInterpolator", "目标值处弹跳，像玻璃球掉在地板上的效果"));
        list.add(new ListInfoBean("CycleInterpolator", "正弦 / 余弦曲线动画，可以自定义曲线的周期"));
        list.add(new ListInfoBean("PathInterpolator", "自定义动画完成度 / 时间完成度曲线"));
        list.add(new ListInfoBean("FastOutLinearInInterpolator", "加速运动"));
        list.add(new ListInfoBean("FastOutSlowInInterpolator", "先加速再减速"));
        list.add(new ListInfoBean("LinearOutSlowInInterpolator", "持续减速"));
        adapter.setNewData(list);

        ValueAnimator process = ObjectAnimator.ofFloat(coordinate, "process", 0.0f, 1.0f);
        process.setDuration(1000);

        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                ListInfoBean infoBean = (ListInfoBean) adapter.getData().get(position);

                //设置标题
                coordinate.clear();
                coordinate.setTitle(infoBean.getTitle());
                switch (position) {
                    case 0:
                        process.setInterpolator(new AccelerateDecelerateInterpolator());
                        break;
                    case 1:
                        process.setInterpolator(new LinearInterpolator());
                        break;
                    case 2:
                        process.setInterpolator(new AccelerateInterpolator());
                        break;
                    case 3:
                        process.setInterpolator(new DecelerateInterpolator());
                        break;
                    case 4:
                        process.setInterpolator(new AnticipateInterpolator());
                        break;
                    case 5:
                        process.setInterpolator(new OvershootInterpolator());
                        break;
                    case 6:
                        process.setInterpolator(new AnticipateOvershootInterpolator());
                        break;
                    case 7:
                        process.setInterpolator(new BounceInterpolator());
                        break;
                    case 8:
                        process.setInterpolator(new CycleInterpolator(1));
                        break;
                    case 9:
                        //process.setInterpolator(new PathInterpolator());
                        break;
                    case 10:
                        process.setInterpolator(new FastOutLinearInInterpolator());
                        break;
                    case 11:
                        process.setInterpolator(new FastOutSlowInInterpolator());
                        break;
                    case 12:
                        process.setInterpolator(new LinearOutSlowInInterpolator());
                        break;

                }
                //process.setInterpolator(getInterpolator("android.view.animation." + infoBean.getTitle()));
                //开启动画
                process.start();
            }
        });
    }


    public Interpolator getInterpolator(String className) {
        Interpolator interpolator = new LinearInterpolator();
        // 获取对象或者类的Class实例
        try {
            Class<?> aClass = Class.forName(className);
            interpolator = (Interpolator) aClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return interpolator;
    }


}
