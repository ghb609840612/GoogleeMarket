package com.snow.night.googleemarket.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/15.
 */
public class HomeBean {
    public ArrayList<String> picture;
    public ArrayList<Appinfo> list;

    public class Appinfo{
        private String id;
        private String name;
        private String packageName;
        private String iconUrl;
        private float stars;
        private long size;
        private String downloadUrl;
        private String des;

        public String getId() {
            return id;
        }

        public String getPackageName() {
            return packageName;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public float getStars() {
            return stars;
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

        public String getName() {
            return name;
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

        public void setStars(float stars) {
            this.stars = stars;
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

        public  Appinfo(String id, String name, String packageName, String iconUrl, float stars, long size, String downloadUrl, String des) {
            this.id = id;
            this.name = name;
            this.packageName = packageName;
            this.iconUrl = iconUrl;
            this.stars = stars;
            this.size = size;
            this.downloadUrl = downloadUrl;
            this.des = des;
        }
    }
}
