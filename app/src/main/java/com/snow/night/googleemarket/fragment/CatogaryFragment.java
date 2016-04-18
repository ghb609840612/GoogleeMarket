package com.snow.night.googleemarket.fragment;

import android.os.SystemClock;
import android.view.View;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.snow.night.googleemarket.R;
import com.snow.night.googleemarket.adapter.CategoryAdapter;
import com.snow.night.googleemarket.adapter.HomeListAdapter;
import com.snow.night.googleemarket.base.BaseFragment;
import com.snow.night.googleemarket.bean.CategoryBean;
import com.snow.night.googleemarket.bean.HomeBean;
import com.snow.night.googleemarket.net.Urls;
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
public class CatogaryFragment extends BaseFragment {

    private ListView listView;
    private CategoryAdapter categoryAdapter;

    @Override
    public String getTitle() {
        return "分类";
    }

    @Override
    public View getContentView() {
        return null;
    }

    @Override
    public int getContentViewById() {
        return R.layout.fragment_category;
    }


    @Override
    protected void onPostExecute(int requestType, Object result) {
        Type type = new TypeToken<ArrayList<CategoryBean>>(){}.getType();
        ArrayList<CategoryBean> datas = JsonUtil.json2Bean((String) result, type);

        switch (requestType){
            case REQUEST_INIT_DATA:
                rootview.showContentview();
                if(checkDataIsShowStateView(datas)){
                    showData(datas);
                }
                break;

        }
    }

    private void showData(ArrayList<CategoryBean> datas) {
        ArrayList<Object> newDatas = new ArrayList<>();
        for (CategoryBean categoryinfo: datas) {
            newDatas.add(categoryinfo.title) ;
            newDatas.addAll(categoryinfo.infos);
        }
        categoryAdapter.getData().addAll(newDatas);
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    protected Object doInBackground(int requestType) {
        SystemClock.sleep(1000);
        switch (requestType){
            case REQUEST_INIT_DATA:
            case REQUEST_LOADING_DATA:
                //http://127.0.0.1:8090/home?index=0

                String json = NetUtil.getjson(Urls.CATEGORY,null);
                return json;
        }
        return null;
    }

    @Override
    public void initview() {
        listView = findView(R.id.lv_fragment_category);
        categoryAdapter = new CategoryAdapter(null);
        listView.setAdapter(categoryAdapter);
    }
    @Override
    public void initdata() {
        requestAsyncTask(REQUEST_INIT_DATA);
    }
    @Override
    public void initlistener() {

    }



}
