package com.snow.night.googleemarket.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.snow.night.googleemarket.R;

/**
 * Created by Administrator on 2016/4/15.
 */
public class LoadMoreListView  extends ListView{
    private static final  int STATE_LOADING_MORE =1;
    private static final  int STATE_RETRY =2;
    private static final  int STATE_NOMORE_DATA =3;
    private int currentstate = STATE_LOADING_MORE;
    private OnLoadingMoreListener mOnLoadingMoreListener;

   private View footerview;
    private final ProgressBar pbFooterview;
    private final TextView tvFootview;
    private boolean isLoadingmore = false;

    public LoadMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        footerview = View.inflate(context, R.layout.footer_view_list,null);
        pbFooterview = (ProgressBar) footerview.findViewById(R.id.pb_footview_list);
        tvFootview = (TextView) footerview.findViewById(R.id.tv_footview_list);
        addFooterView(footerview);
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState ==OnScrollListener.SCROLL_STATE_IDLE && getLastVisiblePosition() == getCount()-1
                        && currentstate == STATE_LOADING_MORE
                        &&  !isLoadingmore
                        && mOnLoadingMoreListener!=null){
                    isLoadingmore =true;
                    mOnLoadingMoreListener.onLoadingMore();

                }else if(scrollState ==OnScrollListener.SCROLL_STATE_IDLE)
                {

                }else if(scrollState ==OnScrollListener.SCROLL_STATE_FLING){

                }


            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
         footerview.setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(currentstate==STATE_RETRY && mOnLoadingMoreListener!= null){
                     mOnLoadingMoreListener.onRetry();
                     showLoadingMore();
                 }
             }
         });
    }
    public void showLoadingMore()
    {
        currentstate =STATE_LOADING_MORE;
        pbFooterview.setVisibility(View.VISIBLE);
        tvFootview.setText("正在加载更多。。。");
    }
    public void showRetry()
    {
        currentstate =STATE_RETRY;
        pbFooterview.setVisibility(View.GONE);
        tvFootview.setText("retry");
    }
    public void showNoMore()
    {
        currentstate =STATE_NOMORE_DATA;
        pbFooterview.setVisibility(View.GONE);
        tvFootview.setText("no more data");
    }
    public void setonLoadingMoreListener(OnLoadingMoreListener mOnLoadingMoreListener){
        this.mOnLoadingMoreListener = mOnLoadingMoreListener;
    }



    public  interface  OnLoadingMoreListener{
        void onLoadingMore();

        void onRetry();
    }

    /**
     * 完成加载更多后将 是否加载更多的变量置为false
     */
    public void onCompleteLoadingMore() {
        isLoadingmore = false;
    }


}
