package com.better.animator;


import android.support.v7.widget.RecyclerView;

import com.better.animator.adapter.Dynamic2DAdapter;
import com.better.animator.base.BaseFragment;
import com.better.animator.base.BaseRecyclerFragment;
import com.better.animator.modules.dynamic2d.WaveViewFragment1;
import com.better.animator.modules.dynamic2d.WaveViewFragment2;
import com.better.animator.modules.dynamic2d.WaveViewFragment3;
import com.better.animator.modules.dynamic2d.WaveViewFragment4;
import com.better.animator.modules.dynamic2d.WaveViewFragment5;
import com.better.animator.modules.dynamic2d.WaveViewFragment6;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


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

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private Dynamic2DAdapter adapter;

    @Override
    public int setViewId() {
        return R.layout.fragment_dynamic_2d;
    }

    @Override
    public void initData() {
        adapter =new Dynamic2DAdapter(getFragmentManager());
        recyclerView.setAdapter(adapter);

        List<BaseRecyclerFragment> list= new ArrayList<>();
        list.add(new WaveViewFragment1());
        list.add(new WaveViewFragment2());
        list.add(new WaveViewFragment3());
        list.add(new WaveViewFragment4());
        list.add(new WaveViewFragment5());
        list.add(new WaveViewFragment6());

        adapter.setNewData(list);
    }
}
