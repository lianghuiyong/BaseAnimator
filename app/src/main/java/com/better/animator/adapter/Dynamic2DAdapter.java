package com.better.animator.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.better.animator.R;
import com.better.animator.bean.MultiItem;
import com.better.anime.base.BaseCustomView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: Dynamic2DAdapter.java
 * Author: Liang
 * Create: 2018/1/15 22:18
 * <p>
 * Changes (from 2018/1/15)
 * -----------------------------------------------------------------
 * 2018/1/15 : Create Dynamic2DAdapter.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public class Dynamic2DAdapter extends BaseMultiItemQuickAdapter<MultiItem, BaseViewHolder> {


    public static List<MultiItem> initData() {

        List<MultiItem> data = new ArrayList<>();
        data.add(new MultiItem(0, R.layout.fragment_2d_waveview));
        data.add(new MultiItem(1, R.layout.fragment_2d_waveview));
        data.add(new MultiItem(1, R.layout.fragment_2d_waveview));
        data.add(new MultiItem(1, R.layout.fragment_2d_waveview));
        data.add(new MultiItem(1, R.layout.fragment_2d_waveview));

        return data;
    }

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public Dynamic2DAdapter(List<MultiItem> data) {
        super(data);

        for (MultiItem item : data) {
            addItemType(item.getItemType(), item.getResLayout());
        }
    }

    @Override
    protected void convert(BaseViewHolder holder, MultiItem item) {
        ViewGroup rootItem = holder.getView(R.id.root_item);

        rootItem.post(() -> {
            for (View itemView : getAllChildren(rootItem)) {
                if (itemView instanceof BaseCustomView) {
                    //((BaseCustomView) itemView).getAnimator().start();
                    Log.e("convert", "rootItem =[" + holder.getLayoutPosition() + "] | itemView =[" + itemView.getId() + "]");
                }
            }
        });
    }

    //列表项出现到可视界面的时候调用
    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        ViewGroup rootItem = holder.getView(R.id.root_item);

        rootItem.post(() -> {
            for (View itemView : getAllChildren(rootItem)) {
                if (itemView instanceof BaseCustomView) {
                    //((BaseCustomView) itemView).getAnimator().resume();
                    Log.e("onViewAttached", "rootItem =[" + holder.getLayoutPosition() + "] | itemView =[" + itemView.getId() + "]");
                }
            }
        });
    }

    //消失在可视界面时
    @Override
    public void onViewDetachedFromWindow(BaseViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

        ViewGroup rootItem = holder.getView(R.id.root_item);

        rootItem.post(() -> {
            for (View itemView : getAllChildren(rootItem)) {
                if (itemView instanceof BaseCustomView) {
                    ((BaseCustomView) itemView).getAnimator().pause();

                    Log.e("onViewDetached", "rootItem =[" + holder.getLayoutPosition() + "] | itemView =[" + itemView.getId() + "]");
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
