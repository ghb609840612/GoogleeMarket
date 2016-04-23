package com.snow.night.googleemarket.bean;

import com.snow.night.googleemarket.manager.DownLoadManager;

import org.xutils.DbManager;

/**
 * Created by Administrator on 2016/4/21.
 */
public class DownLoadInfo {
    private String id;
    private String name;
    private String downURL;
    private String path;
    private int state;
    private long currentposition;
    private long size;

    public String getId() {
        return id;
    }

    public void setId(String  id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDownURL() {
        return downURL;
    }

    public void setDownURL(String downURL) {
        this.downURL = downURL;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getCurrentposition() {
        return currentposition;
    }

    public void setCurrentposition(long currentposition) {
        this.currentposition = currentposition;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
    public  static DownLoadInfo clone(AppDetailBean appinfo)
    {
         DownLoadInfo downLoadInfo = new DownLoadInfo();
        downLoadInfo.setCurrentposition(0);
        downLoadInfo.setState(DownLoadManager.DOWNLOAD_STATE_INIT);
        //从bean对象中直接取得的数据
        downLoadInfo.setId(appinfo.getId());
        downLoadInfo.setDownURL(appinfo.getDownloadUrl());
        downLoadInfo.setName(appinfo.getName());
        downLoadInfo.setSize(appinfo.getSize());
        downLoadInfo.setPath(DownLoadManager.DOWNLOAD_URL+appinfo.getName()+".apk");


         return  downLoadInfo;
    }
    public  static DownLoadInfo clone(HomeBean.Appinfo appinfo)
    {
        DownLoadInfo downLoadInfo = new DownLoadInfo();
        downLoadInfo.setCurrentposition(0);
        downLoadInfo.setState(DownLoadManager.DOWNLOAD_STATE_INIT);
        //从bean对象中直接取得的数据
        downLoadInfo.setId(appinfo.getId());
        downLoadInfo.setDownURL(appinfo.getDownloadUrl());
        downLoadInfo.setName(appinfo.getName());
        downLoadInfo.setSize(appinfo.getSize());
        downLoadInfo.setPath(DownLoadManager.DOWNLOAD_URL+appinfo.getName()+".apk");


        return  downLoadInfo;
    }
}
