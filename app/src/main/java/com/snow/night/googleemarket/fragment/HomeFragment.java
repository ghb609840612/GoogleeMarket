package com.snow.night.googleemarket.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.snow.night.googleemarket.MyApplication;
import com.snow.night.googleemarket.R;
import com.snow.night.googleemarket.activity.AppDetailsActivity;
import com.snow.night.googleemarket.adapter.BannerAdapterSelf;
import com.snow.night.googleemarket.adapter.HomeListAdapter;
import com.snow.night.googleemarket.base.BaseFragment;
import com.snow.night.googleemarket.bean.HomeBean;
import com.snow.night.googleemarket.net.Urls;
import com.snow.night.googleemarket.utils.CommonUtils;
import com.snow.night.googleemarket.utils.JsonUtil;
import com.snow.night.googleemarket.utils.Keys;
import com.snow.night.googleemarket.utils.LogUtil;
import com.snow.night.googleemarket.utils.NetUtil;
import com.snow.night.googleemarket.view.LoadMoreListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/4/13.
 */
public class HomeFragment extends BaseFragment {

    private static final int SWITCHBANNER = 100;
    private HomeListAdapter homeListAdapter ;
    private LoadMoreListView listView;
    private ViewPager bannerViewPager;
    private LinearLayout llBannerDots;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SWITCHBANNER:
                    switchPager ();
                    break ;
            }
        }
    };

    private void switchPager() {
        int currentItem = bannerViewPager.getCurrentItem();
        currentItem++;
        bannerViewPager.setCurrentItem(currentItem);
        //发消息前先取消之前的消息
        handler.removeMessages(SWITCHBANNER);
        handler.sendEmptyMessageDelayed(SWITCHBANNER,3000);
    }

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
    @Override
    protected void onPostExecute(int requestType, Object result) {
        HomeBean homeBean = JsonUtil.json2Bean((String) result, HomeBean.class);
        ArrayList<HomeBean.Appinfo> datas = null;
        if(homeBean!= null)
        {
            datas = homeBean.list;
            showbanner(homeBean.picture);
        }
        switch (requestType){
            case REQUEST_INIT_DATA:
                rootview.showContentview();
                if(checkDataIsShowStateView(datas)){
                    homeListAdapter.getData().addAll(datas);
                    homeListAdapter.notifyDataSetChanged();
                }
                break;
            case REQUEST_LOADING_DATA:
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

    private void showbanner(ArrayList<String> pictures) {
        for(int i=0; i<pictures.size();i++){
          View view = new View(MyApplication.getContext());
            int size = CommonUtils.dip2px(5);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size,size);
            params.leftMargin = size;
            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.dot_selector);
            llBannerDots.addView(view);
        }
        //默认选中第一个点
        llBannerDots.getChildAt(0).setSelected(true);
        bannerViewPager.setAdapter(new BannerAdapterSelf(pictures));
        bannerViewPager.setCurrentItem(bannerViewPager.getAdapter().getCount()/2);
        handler.sendEmptyMessageDelayed(SWITCHBANNER,3000);
    }

    @Override
    protected Object doInBackground(int requestType) {
        SystemClock.sleep(1000);
        switch (requestType){
            case REQUEST_INIT_DATA:
            case REQUEST_LOADING_DATA:
                  //http://127.0.0.1:8090/home?index=0
                HashMap<String,String> params = new HashMap<String,String>();
//                params.put("index",homeListAdapter.getData().size()+"");  //有时空指针
//                int index =homeListAdapter.getData().size();
//                String indexString = String.valueOf(index);
                if(homeListAdapter.getData().size()==0){
                    params.put("index","0");
                }else{
                    params.put("index",homeListAdapter.getData().size()+"");
                }
//                 params.put("index",homeListAdapter.getData().size()+"");
                   String json = NetUtil.getjson(Urls.HOME,params);
                   LogUtil.e(this,json);
               return json;
        }
        return null;
    }
    @Override
    public void initview() {
        listView = findView(R.id.lv_fragment_home);
        if(homeListAdapter == null){
            homeListAdapter = new HomeListAdapter(null);
            listView.setAdapter(homeListAdapter);
        }else{
            homeListAdapter.notifyDataSetChanged();
        }


        View headerview = View.inflate(context,R.layout.headview_homefragment_item,null);
        bannerViewPager = (ViewPager) headerview.findViewById(R.id.vp_banner_viewpager);
        llBannerDots = (LinearLayout) headerview.findViewById(R.id.ll_banner_dots);
        bannerViewPager.addOnPageChangeListener(new MypagerchangeListener());
        listView.addHeaderView(headerview);
    }
     public class MypagerchangeListener implements ViewPager.OnPageChangeListener{
         @Override
         public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

         }

         @Override
         public void onPageSelected(int position) {
             position = position % llBannerDots.getChildCount();
            llBannerDots.getChildAt(position).setSelected(true);
             for(int i =0; i< llBannerDots.getChildCount(); i++){
                 if(i != position){
                     llBannerDots.getChildAt(i).setSelected(false);
                 }
             }
         }
         @Override
         public void onPageScrollStateChanged(int state) {

         }
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HomeBean.Appinfo appinfo = (HomeBean.Appinfo) parent.getItemAtPosition(position);
                String packageName = appinfo.getPackageName();

                Intent intent = new Intent(MyApplication.getContext(),AppDetailsActivity.class);
                intent.putExtra(Keys.PACKAGE_NAME,packageName);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeMessages(SWITCHBANNER);
    }
}
