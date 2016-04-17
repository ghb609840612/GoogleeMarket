package com.snow.night.googleemarket.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/15.
 */
public class SubjectBean {
        private String des;
        private String url;

    public SubjectBean(String des, String url) {
        this.des = des;
        this.url = url;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDes() {
        return des;
    }

    public String getUrl() {
        return url;
    }
}
