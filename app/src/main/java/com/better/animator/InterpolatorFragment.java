package com.better.animator;


import android.os.Build;
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
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.Toast;

import com.better.anime.interpolator.BreatheInterpolator;
import com.better.animator.adapter.InterpolatorAdapter;
import com.better.animator.base.BaseFragment;
import com.better.animator.bean.ListInfoBean;
import com.better.animator.widget.CoordinateView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.widget.Toast.LENGTH_SHORT;


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
        list.add(new ListInfoBean("AccelerateDecelerateInterpolator", "【系统默认】先加速再减速"));
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
        list.add(new ListInfoBean("BreatheInterpolator", "【自定义】拟合呼吸变化"));
        adapter.setNewData(list);

        recyclerView.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                  ListInfoBean infoBean = (ListInfoBean) adapter.getData().get(position);
                coordinate.setTitle(infoBean.getTitle());
                switch (position) {
                    case 0:
                        coordinate.setInterpolator(new AccelerateDecelerateInterpolator());
                        break;
                    case 1:
                        coordinate.setInterpolator(new LinearInterpolator());
                        break;
                    case 2:
                        coordinate.setInterpolator(new AccelerateInterpolator());
                        break;
                    case 3:
                        coordinate.setInterpolator(new DecelerateInterpolator());
                        break;
                    case 4:
                        coordinate.setInterpolator(new AnticipateInterpolator());
                        break;
                    case 5:
                        coordinate.setInterpolator(new OvershootInterpolator());
                        break;
                    case 6:
                        coordinate.setInterpolator(new AnticipateOvershootInterpolator());
                        break;
                    case 7:
                        coordinate.setInterpolator(new BounceInterpolator());
                        break;
                    case 8:
                        coordinate.setInterpolator(new CycleInterpolator(0.25f));
                        break;
                    case 9:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            coordinate.setInterpolator(new PathInterpolator(0.2f, 1f));
                        } else {
                            Toast.makeText(getContext(), "Api 21 以上支持", LENGTH_SHORT).show();
                        }
                        break;
                    case 10:
                        coordinate.setInterpolator(new FastOutLinearInInterpolator());
                        break;
                    case 11:
                        coordinate.setInterpolator(new FastOutSlowInInterpolator());
                        break;
                    case 12:
                        coordinate.setInterpolator(new LinearOutSlowInInterpolator());
                        break;
                    case 13:
                        coordinate.setInterpolator(new BreatheInterpolator());
                        break;

                }
            }
        });
    }
}
