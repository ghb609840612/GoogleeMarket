package com.snow.night.googleemarket.adapter;

import com.snow.night.googleemarket.bean.DownLoadInfo;
import com.snow.night.googleemarket.manager.DownLoadManager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.snow.night.googleemarket.MyApplication;
import com.snow.night.googleemarket.R;
import com.snow.night.googleemarket.base.MyBaseAdapter;
import com.snow.night.googleemarket.bean.HomeBean;
import com.snow.night.googleemarket.net.Urls;
import com.snow.night.googleemarket.utils.CommonUtils;
import com.snow.night.googleemarket.view.ProgressArc;

import org.xutils.x;

import java.util.ArrayList;
import java.util.Formatter;

/**
 * Created by Administrator on 2016/4/14.
 */
public class HomeListAdapter extends MyBaseAdapter<HomeBean.Appinfo>{

    private HomeBean.Appinfo downloadhelp;
    private ProgressArc progressArc;


    public HomeListAdapter(ArrayList<HomeBean.Appinfo> datas) {
        super(datas);
    }

    @Override
    public int getLayoutResId(int position) {
        return R.layout.item_homefragment_applist;
    }

    @Override
    public Object createHolder(View convertView, int position) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_homefragment_item_title);
        viewHolder.tv_size = (TextView) convertView.findViewById(R.id.tv_homefragment_item_size);
        viewHolder.tv_desc = (TextView) convertView.findViewById(R.id.tv_homefragment_item_desc);
        viewHolder.iv_icon= (ImageView) convertView.findViewById(R.id.iv_homefragment_listitem_icon);
        viewHolder.rb_rating = (RatingBar) convertView.findViewById(R.id.rb_homefragment_item_rating);
        viewHolder.ll_download = (LinearLayout) convertView.findViewById(R.id.ll_homefragment_item_download);
        viewHolder.fl_download_logo= (FrameLayout) convertView.findViewById(R.id.fl_download_logo);
        viewHolder.tv_progress = (TextView) convertView.findViewById(R.id.tv_download_progress);
        progressArc = new ProgressArc(MyApplication.getContext());
        progressArc.setArcDiameter(CommonUtils.dip2px(27));
        progressArc.setBackgroundResource(R.drawable.ic_download);
        progressArc.setProgressColor(R.color.selfblue);
        viewHolder.fl_download_logo.addView(progressArc);
        viewHolder.pa_progressArcSelf = progressArc;
        return viewHolder;
    }

    @Override
    protected void showdata(int position, Object viewHolder, final HomeBean.Appinfo data) {
        ViewHolder holder = (ViewHolder) viewHolder;
        Context context = ((ViewHolder) viewHolder).tv_title.getContext();
        String url = Urls.IMAGE + "?name="+data.getIconUrl();
        x.image().bind(((ViewHolder) viewHolder).iv_icon,url);
        holder.tv_title.setText(data.getName());
        holder.tv_size.setText(android.text.format.Formatter.formatFileSize(context,data.getSize()));
        holder.tv_desc.setText(data.getDes());
        holder.rb_rating.setRating(data.getStars());
        holder.ll_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lldownloadclick(data);
            }
        });
        registDownloadObserver(data,holder,position);
    }

    /**
     * 点击事件的响应方法
     * @param data
     */
    private void lldownloadclick(HomeBean.Appinfo data) {
        DownLoadManager downLoadManager = DownLoadManager.getInstance();
        DownLoadInfo downLoadInfo = downLoadManager.getdownloadinfo(data.getId());

        if(downLoadInfo == null){
            downLoadManager.download(data);
        }else{
            switch (downLoadInfo.getState()) {
                case DownLoadManager.DOWNLOAD_STATE_DOWNLOADING:
                case DownLoadManager.DOWNLOAD_STATE_WAITING:
                    downLoadManager.pause(data);
                    break;
                case DownLoadManager.DOWNLOAD_STATE_PAUSE:
                case DownLoadManager.DOWNLOAD_STATE_ERROR:
                    downLoadManager.download(data);
                    break;
                case DownLoadManager.DOWNLOAD_STATE_SUCCESS:
                    downLoadManager.install(data);
                    break;
            }
        }
    }

    static class ViewHolder{
        TextView tv_title;
        TextView tv_size;
        TextView tv_desc;
        TextView tv_progress;
        ImageView iv_icon;
        RatingBar rb_rating;
        LinearLayout ll_download;
        FrameLayout fl_download_logo;
        ProgressArc  pa_progressArcSelf;
    }

    /**
     * 注册观察者
     */
    public void registDownloadObserver(final HomeBean.Appinfo downloaddata, final ViewHolder holder,int position) {
        DownLoadManager.getInstance().registObserver(new DownLoadManager.DownLoadObserver() {
            @Override
            public void onDownLoadinfoChange(DownLoadInfo info) {
                if(info.getId().equals(downloaddata.getId())){
                    //根据状态，更新界面
                    processState(info,holder);
                }
            }
        });
    }
    /**
     * 根据状态，更新界面
     * @param downLoadInfo
     */
    private void processState(DownLoadInfo downLoadInfo,ViewHolder holder) {
        //progress = 当前下载的大小 / 总大小

        float progress = downLoadInfo.getCurrentposition()*1f/downLoadInfo.getSize();
        int progressInt = (int) (progress*100);

        switch (downLoadInfo.getState()) {
            case DownLoadManager.DOWNLOAD_STATE_DOWNLOADING:
                //显示进度，显示百分比
                //设置ProgressArc的下载style

                holder.pa_progressArcSelf.setStyle(ProgressArc.PROGRESS_STYLE_DOWNLOADING);
                holder.pa_progressArcSelf.setProgress(progress, true);
                holder.pa_progressArcSelf.setBackgroundResource(R.drawable.ic_pause);
                holder.tv_progress.setText(progressInt+"%");
                break;
            case DownLoadManager.DOWNLOAD_STATE_ERROR:
                //文字该为重新下载
                holder.pa_progressArcSelf.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                holder.pa_progressArcSelf.setBackgroundResource(R.drawable.ic_redownload);
                holder.tv_progress.setText("重下");
                break;
            case DownLoadManager.DOWNLOAD_STATE_PAUSE:
                //文字改为 继续下载
                holder.pa_progressArcSelf.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                holder.pa_progressArcSelf.setBackgroundResource(R.drawable.ic_resume);
                holder.tv_progress.setText("继续");
                break;
            case DownLoadManager.DOWNLOAD_STATE_SUCCESS:
                //文字改为 安装
                holder.pa_progressArcSelf.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                holder.pa_progressArcSelf.setBackgroundResource(R.drawable.ic_install);
                holder.tv_progress.setText("安装");
                break;
            case DownLoadManager.DOWNLOAD_STATE_WAITING:
                //文字改为 等待
                holder.tv_progress.setText("等待");
                holder.pa_progressArcSelf.setStyle(ProgressArc.PROGRESS_STYLE_DOWNLOADING);
                holder.pa_progressArcSelf.setProgress(progress, true);
                holder.pa_progressArcSelf.setBackgroundResource(R.drawable.ic_download);
                break;
        }
    }

}
