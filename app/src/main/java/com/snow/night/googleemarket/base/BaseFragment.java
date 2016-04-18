package com.snow.night.googleemarket.base;


import android.app.Activity;

import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.snow.night.googleemarket.R;
import com.snow.night.googleemarket.view.LoadMoreListView;
import com.snow.night.googleemarket.view.StateLayout;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/13.
 */
public abstract class BaseFragment extends Fragment {
        //处理共同操作
        //提供代码规范
        //提供一些常用的变量和常用的方法 避免代码重复
    protected Activity context;
    protected StateLayout rootview;
    protected  final int REQUEST_INIT_DATA = 80 ;
    protected  final int REQUEST_LOADING_DATA = 90 ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context =getActivity();
        rootview = new StateLayout(context);
        View contentview = getContentView();
        if(getContentView()!= null)
        {
            rootview.setContentView(getContentView());
        }else{
            rootview.setContentView(getContentViewById());
        }
        initview();
//        initdata();
        initlistener();
    return rootview;
}
    public String getTitle(){
        return  getClass().getSimpleName();
    }
    //base类中提供一些常用的方法
    /**
     * 为了省略强转操作
     * @param id
     * @param <T>
     * @return
     */
    public <T> T findView (int id)
    {
        T view = (T) rootview.findViewById(id);
        return  view;
    }
    /**
     * 开启子线程进行耗时的操作
     * @param requestType
     */
    public void requestAsyncTask(final int requestType){
            new AsyncTask<Void,Void,Object>(){
                @Override
                protected Object doInBackground(Void... params) {
                    return BaseFragment.this.doInBackground(requestType);
                }
                @Override
                protected void onPostExecute(Object o) {
                   BaseFragment.this.onPostExecute(requestType,o);
                }
            }.execute();
    }

    /**
     * 判断返回的数据 确定statelayout 显示哪种状态的view
     * @param datas
     * @return
     */
    public boolean checkDataIsShowStateView(ArrayList datas){
        if(datas == null)
        {
            rootview.showFail();
        }else if(datas.isEmpty()){
            rootview.showEmpty();
        }else{
            return  true;
        }
        return  false;
    }
    /**
     * 检查返回来的数据是否满足加载更多
     * @param datas
     * @param listView
     * @return
     */
    public  boolean checkDataIsLoadingMore(ArrayList datas, LoadMoreListView listView){
        if(datas == null)
        {
            listView.showRetry();
        }else if(datas.isEmpty()){
            listView.showNoMore();
        }else{
            return  true;
        }
        return  false;
    }
    /**
     *    联网之后更新Ui的操作
     */
    protected abstract void onPostExecute(int requestType, Object result);
    /**
     *   具体的子线程的联网操作
     */
    protected abstract Object doInBackground(int requestType);

    /**
     * 初始化界面
     */
    public abstract  void initview();
    /**
     * 初始化数据
     */
    public abstract void initdata();

    /**
     * 初始化监听器
     */
    public abstract void initlistener();

    /**
     * 提供获取view的方法 由子类实现
     */
    public abstract View getContentView();

    /**
     * 通过资源id获取view的方法 由子类实现
     */
    public abstract int getContentViewById();
}
