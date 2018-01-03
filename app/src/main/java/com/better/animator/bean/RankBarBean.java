package com.better.animator.bean;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2017, by Better, All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: RankBarBean.java
 * Author: lianghuiyong@outlook.com
 * Create: 2018/1/2 上午11:14
 *
 * Changes (from 2018/1/2)
 * -----------------------------------------------------------------
 * 2018/1/2 : Create RankBarBean.java (梁惠涌);
 * -----------------------------------------------------------------
 */
public class RankBarBean {
    private String title;
    private int value;

    public RankBarBean(String title, int value) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
