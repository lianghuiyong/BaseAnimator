package com.better.animator.view;

import android.widget.TextView;

import com.better.animator.R;
import com.better.animator.base.BaseFragment;

import butterknife.BindView;

public class DemoFragment extends BaseFragment {

    @BindView(R.id.item_subtitle)
    TextView itemSubtitle;
    @BindView(R.id.item_title)
    TextView itemTitle;

    @Override
    public int setViewId() {
        return R.layout.fragment_demo;
    }

    @Override
    public void initData() {
        itemSubtitle.setText("222");
    }

}
