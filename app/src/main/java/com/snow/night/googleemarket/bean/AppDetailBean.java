package com.snow.night.googleemarket.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/19.
 */
public class AppDetailBean {
    private  String  id;
    private  String  name;
    private  String  packageName;
    private  String  iconUrl;
    private  long  stars;
    private  String  downloadNum;
    private  String  version;
    private  String  date;
    private  long  size;
    private  String  downloadUrl;
    private  String  des;
    private  String  author;
    public ArrayList<String> screen;
    public ArrayList<SafeInfo> safe;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public long getStars() {
        return stars;
    }

    public String getDownloadNum() {
        return downloadNum;
    }

    public String getVersion() {
        return version;
    }

    public String getDate() {
        return date;
    }

    public long getSize() {
        return size;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getDes() {
        return des;
    }

    public String getAuthor() {
        return author;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public void setStars(long stars) {
        this.stars = stars;
    }

    public void setDownloadNum(String downloadNum) {
        this.downloadNum = downloadNum;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public  class SafeInfo{
        private String  safeUrl;
        private String  safeDesUrl;
        private String  safeDes;
        private String  safeDesColor;

        public String getSafeUrl() {
            return safeUrl;
        }

        public void setSafeUrl(String safeUrl) {
            this.safeUrl = safeUrl;
        }

        public String getSafeDesUrl() {
            return safeDesUrl;
        }

        public void setSafeDesUrl(String safeDesUrl) {
            this.safeDesUrl = safeDesUrl;
        }

        public String getSafeDes() {
            return safeDes;
        }

        public void setSafeDes(String safeDes) {
            this.safeDes = safeDes;
        }

        public String getSafeDesColor() {
            return safeDesColor;
        }

        public void setSafeDesColor(String safeDesColor) {
            this.safeDesColor = safeDesColor;
        }
    }
}

