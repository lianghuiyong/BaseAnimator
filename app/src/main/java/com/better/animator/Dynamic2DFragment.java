package com.better.animator;


import android.animation.Animator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.better.animator.adapter.Dynamic2DAdapter;
import com.better.animator.base.BaseFragment;
import com.better.anime.base.BaseCustomView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.support.v7.widget.RecyclerView.*;


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
    private LinearLayoutManager layoutManager;

    @Override
    public int setViewId() {
        return R.layout.fragment_dynamic_2d;
    }

    @Override
    public void initData() {
        adapter = new Dynamic2DAdapter(Dynamic2DAdapter.initData());
        adapter.openLoadAnimation();
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                int itemPosition = layoutManager.findFirstVisibleItemPosition();
                switch (newState) {
                    case SCROLL_STATE_IDLE:
                        //静止,没有滚动

                        startAnimator(itemPosition);
                        break;

                    case SCROLL_STATE_DRAGGING:
                        //正在被外部拖拽,一般为用户正在用手指滚动

                        //能向下滚动&&能向上滚动
                        if (recyclerView.canScrollVertically(1) && recyclerView.canScrollVertically(-1)) {
                            stopAnimator(itemPosition);
                        }
                        break;

                    case SCROLL_STATE_SETTLING:
                        //自动滚动开始
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dx == 0 && dy == 0) {
                    startAnimator(0);
                }
            }
        });
    }

    public void startAnimator(int itemPosition) {

        Log.e("lhy", "startAnimator = " + itemPosition);

        recyclerView.post(() -> {
            for (View itemView : getAllChildren(layoutManager.findViewByPosition(itemPosition))) {
                if (itemView instanceof BaseCustomView) {
                    Animator animator = ((BaseCustomView) itemView).getAnimator();
                    if (animator.isPaused()) {
                        animator.resume();
                    } else {
                        animator.start();
                    }
                }
            }
        });
    }

    public void stopAnimator(int itemPosition) {

        Log.e("lhy", "stopAnimator = " + itemPosition);

        recyclerView.post(() -> {
            for (View itemView : getAllChildren(layoutManager.findViewByPosition(itemPosition))) {
                if (itemView instanceof BaseCustomView) {
                    ((BaseCustomView) itemView).getAnimator().pause();
                }
            }
        });
    }


    private List<View> getAllChildren(View v) {

        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            return viewArrayList;
        }

        ArrayList<View> result = new ArrayList<View>();

        ViewGroup viewGroup = (ViewGroup) v;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {

            View child = viewGroup.getChildAt(i);

            //Do not add any parents, just add child elements
            result.addAll(getAllChildren(child));
        }
        return result;
    }
}
