package com.snow.night.googleemarket.fragment;

import android.os.SystemClock;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.snow.night.googleemarket.R;
import com.snow.night.googleemarket.adapter.StellerMapAdapter;
import com.snow.night.googleemarket.base.BaseFragment;
import com.snow.night.googleemarket.net.Urls;
import com.snow.night.googleemarket.stellar.StellarMap;
import com.snow.night.googleemarket.utils.CommonUtils;
import com.snow.night.googleemarket.utils.JsonUtil;
import com.snow.night.googleemarket.utils.LogUtil;
import com.snow.night.googleemarket.utils.NetUtil;
import com.snow.night.googleemarket.view.LoadMoreListView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/4/13.
 */
public class HotFragment extends BaseFragment {
    private StellarMap stellarMap;
    private StellerMapAdapter stellerMapAdapter;
    @Override
    public String getTitle() {
        return "推荐";
    }

    @Override
    public View getContentView() {
        stellarMap = new StellarMap(context);
        int padding = CommonUtils.dip2px(6);
        stellarMap.setInnerPadding(padding,padding,padding,padding);
        stellarMap.setRegularity(5,15);
        return stellarMap;
    }
    @Override
    public int getContentViewById() {
        return R.layout.fragment_home;
    }  //这个不会调用了 所以不做修改

    @Override
    protected void onPostExecute(int requestType, Object result) {
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        ArrayList<String> datas = JsonUtil.json2Bean((String) result, type);


        switch (requestType){
            case REQUEST_INIT_DATA:
                rootview.showContentview();
                if(stellerMapAdapter == null){
                    stellerMapAdapter = new StellerMapAdapter(datas);
                    stellarMap.setAdapter(stellerMapAdapter);
                    stellarMap.setGroup(0,true);
                }
                break;
        }
    }

    @Override
    protected Object doInBackground(int requestType) {
        SystemClock.sleep(1000);
        switch (requestType){
            case REQUEST_INIT_DATA:
            case REQUEST_LOADING_DATA:
                //http://127.0.0.1:8090/home?index=0
                String json = NetUtil.getjson(Urls.RECOMMEND,null);
                LogUtil.e(this,json);
                return json;
        }
        return null;
    }

    @Override
    public void initview() {
    }
    @Override
    public void initdata() {
        requestAsyncTask(REQUEST_INIT_DATA);
    }
    @Override
    public void initlistener() {
    }

}
