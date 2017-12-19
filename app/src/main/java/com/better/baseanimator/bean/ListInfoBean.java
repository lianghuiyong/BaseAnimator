package com.better.baseanimator.bean;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2017, by Better, All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: ListInfoBean.java
 * Author: lianghuiyong@outlook.com
 * Create: 2017/12/19 下午3:42
 *
 * Changes (from 2017/12/19)
 * -----------------------------------------------------------------
 * 2017/12/19 : Create ListInfoBean.java (梁惠涌);
 * -----------------------------------------------------------------
 */
public class ListInfoBean {
    private String title;
    private String subTitle;

    public ListInfoBean(String title, String subTitle) {
        this.title = title;
        this.subTitle = subTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
