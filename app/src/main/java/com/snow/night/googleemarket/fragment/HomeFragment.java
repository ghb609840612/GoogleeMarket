package com.snow.night.googleemarket.fragment;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.snow.night.googleemarket.R;
import com.snow.night.googleemarket.adapter.HomeListAdapter;
import com.snow.night.googleemarket.base.BaseFragment;
import com.snow.night.googleemarket.view.LoadMoreListView;
import com.snow.night.googleemarket.view.StateLayout;

import java.net.DatagramSocket;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/13.
 */
public class HomeFragment extends BaseFragment {


    private HomeListAdapter homeListAdapter;
    private LoadMoreListView listView;

    @Override
    public View getContentView() {
        return null;
    }

    @Override
    public int getContentViewById() {
        return R.layout.fragment_home;
    }


    int count = 1;
    @Override
    protected void onPostExecute(int requestType, Object o) {
        ArrayList<String> datas = null;
        switch (requestType){
            case REQUEST_INIT_DATA:
                //模拟数据
                 datas = new ArrayList<String>();
                for(int i=0; i< 20;i++){
                    datas.add("~_~猴子来了"+i+"次~_~");
                }
                if(checkDataIsShowStateView(datas)){
                    rootview.showContentview();
                    homeListAdapter.getData().addAll(datas);
                    homeListAdapter.notifyDataSetChanged();
                }
                break;
            case REQUEST_LOADING_DATA:

                //模拟数据 根据count的不同值 赋予datas不同数据 达到加载的不同状态（没有数据、加载失败、没有更多,成功加载到数据）
                if(count == 1)                              //成功加载到数据
                {
                    datas = new ArrayList<String>();
                    datas.add("猴子请来的救兵");
                }else if(count == 2){                       //加载失败
                     datas = null;
                }else if(count == 3){                       //点击重试之后的数据
                    datas = new ArrayList<String>();
                    datas.add("这救兵太弱了");
                }else{                                      //没有更多数据
                    datas = new ArrayList<String>();
                }
                count++;
                listView.onCompleteLoadingMore();
                //实际开发中根据服务器返回的datas的数据状态进行footerview的显示
                //每个页面去加载数据都要对返回的数据进行判断
                // 只有数据是Ok的情况下 我们才展示我们自己的页面 所有对判断操作进行抽取
                if(checkDataIsLoadingMore(datas,listView)){
                    homeListAdapter.getData().addAll(datas);
                    homeListAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    protected Object doInBackground(int requestType) {
        SystemClock.sleep(3000);
        switch (requestType){
            case REQUEST_INIT_DATA:

                break;
            case REQUEST_LOADING_DATA:

                break;

        }

        return null;
    }

    @Override
    public void initview() {
        listView = findView(R.id.lv_fragment_home);
        homeListAdapter = new HomeListAdapter(null);
        listView.setAdapter(homeListAdapter);
    }
    @Override
    public void initdata() {
        requestAsyncTask(REQUEST_INIT_DATA);
    }
    @Override
    public void initlistener() {
       listView.setonLoadingMoreListener(new LoadMoreListView.OnLoadingMoreListener() {
           @Override
           public void onLoadingMore() {
               requestAsyncTask(REQUEST_LOADING_DATA);
           }

           @Override
           public void onRetry() {
               requestAsyncTask(REQUEST_LOADING_DATA);
           }
       });
    }



}
