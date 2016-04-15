package com.snow.night.googleemarket.view;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.snow.night.googleemarket.R;

/** 一个framelayout容器封装了4个view
 * Created by Administrator on 2016/4/14.
 */
public class StateLayout extends FrameLayout{

    private final LinearLayout failView;
    private final ImageView emptyView;
    private final ProgressBar loadingView;
    private final FrameLayout container;

    public StateLayout(Context context) {
        super(context);

        container = (FrameLayout) View.inflate(getContext(), R.layout.state_layout,null);

        failView = (LinearLayout) container.findViewById(R.id.ll_state_fail);
        emptyView = (ImageView) container.findViewById(R.id.iv_state_empty);
        loadingView = (ProgressBar) container.findViewById(R.id.pb_state_loading);

        showLoading();
        this.addView(container);
    }

    /**
     * 显示加载的view
     */
    public void showLoading()
    {
        showView(loadingView);
    }

    /**
     * 显示空的页面
     */
    public void showEmpty()
    {
        showView(emptyView);
    }

    /**
     * 显示加载失败的页面
     */
    public void showFail()
    {
        showView(failView);
    }

    private View contentview;
    /**
     * 动态设置加载成功的页面
     */
    public void setContentView(int layoutResId)
    {
         setContentView(View.inflate(getContext(),layoutResId,null));
    }
    public void setContentView(View view)
    {
        contentview = view;
        contentview.setVisibility(View.GONE);
        container.addView(contentview);
    }


    /**
     * 显示加载成功的页面
     */
    public void showContentview()
    {
        showView(contentview);
    }
    /**
     * 显示指定的view
     * @param view
     */
    private void showView(View view) {
       for (int i = 0 ;i< container.getChildCount();i ++)
       {
           View child = container.getChildAt(i);
//           if(child == view)
//           {
//               child.setVisibility(View.VISIBLE);
//           }else{
//               child.setVisibility(View.GONE);
//           }
//           if else 的语句都可以写成三元运算
          child.setVisibility(child == view ? View.VISIBLE:View.GONE);
       }

    }
}
