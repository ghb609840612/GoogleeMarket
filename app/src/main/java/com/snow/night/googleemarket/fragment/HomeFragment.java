package com.snow.night.googleemarket.fragment;

import android.os.SystemClock;
import android.view.View;

import com.snow.night.googleemarket.R;
import com.snow.night.googleemarket.adapter.HomeListAdapter;
import com.snow.night.googleemarket.base.BaseFragment;
import com.snow.night.googleemarket.bean.HomeBean;
import com.snow.night.googleemarket.net.Urls;
import com.snow.night.googleemarket.utils.JsonUtil;
import com.snow.night.googleemarket.utils.LogUtil;
import com.snow.night.googleemarket.utils.NetUtil;
import com.snow.night.googleemarket.view.LoadMoreListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/4/13.
 */
public class HomeFragment extends BaseFragment {


    private HomeListAdapter homeListAdapter;
    private LoadMoreListView listView;

    @Override
    public String getTitle() {
        return "首页";
    }

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
    protected void onPostExecute(int requestType, Object result) {

         HomeBean homeBean = JsonUtil.json2Bean((String) result, HomeBean.class);
        ArrayList<HomeBean.Appinfo> datas = null;
        if(homeBean!= null)
        {
            datas = homeBean.list;
        }
        switch (requestType){
            case REQUEST_INIT_DATA:
                //模拟数据
//                 datas = new ArrayList<String>();
//                for(int i=0; i< 20;i++){
//                    datas.add("~_~猴子来了"+i+"次~_~");
//                }
                rootview.showContentview();
                if(checkDataIsShowStateView(datas)){

                    homeListAdapter.getData().addAll(datas);
                    homeListAdapter.notifyDataSetChanged();
                }
                break;
            case REQUEST_LOADING_DATA:

                //模拟数据 根据count的不同值 赋予datas不同数据 达到加载的不同状态（没有数据、加载失败、没有更多,成功加载到数据）
//                if(count == 1)                              //成功加载到数据
//                {
//                    datas = new ArrayList<String>();
//                    datas.add("猴子请来的救兵");
//                }else if(count == 2){                       //加载失败
//                     datas = null;
//                }else if(count == 3){                       //点击重试之后的数据
//                    datas = new ArrayList<String>();
//                    datas.add("这救兵太弱了");
//                }else{                                      //没有更多数据
//                    datas = new ArrayList<String>();
//                }
//                count++;
//                listView.onCompleteLoadingMore();
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
            case REQUEST_LOADING_DATA:
                  //http://127.0.0.1:8090/home?index=0
                HashMap<String,String> params = new HashMap<String,String>();
//                params.put("index",homeListAdapter.getData().size()+"");  //有时空指针
                params.put("index",homeListAdapter.getData().size()+"");
                   String json = NetUtil.getjson(Urls.HOME,params);
                   LogUtil.e(this,json);
               return json;

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
