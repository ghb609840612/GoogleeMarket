package com.snow.night.googleemarket.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.snow.night.googleemarket.R;
import com.snow.night.googleemarket.base.MyBaseAdapter;
import com.snow.night.googleemarket.bean.HomeBean;

import java.util.ArrayList;
import java.util.Formatter;

/**
 * Created by Administrator on 2016/4/14.
 */
public class HomeListAdapter extends MyBaseAdapter<HomeBean.Appinfo>{

    public HomeListAdapter(ArrayList<HomeBean.Appinfo> datas) {
        super(datas);
    }
/*
        private  ViewHolder viewHolder;
        public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            convertView = View.inflate(parent.getContext(),
                    android.R.layout.simple_expandable_list_item_1,null);
            viewHolder = new ViewHolder();
            viewHolder.tv = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String data =datas.get(position);
       viewHolder.tv.setText(data);
        return convertView;
    }*/
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
        return viewHolder;
    }

    @Override
    protected void showdata(int position, Object viewHolder, HomeBean.Appinfo data) {
        ViewHolder holder = (ViewHolder) viewHolder;
        Context context = ((ViewHolder) viewHolder).tv_title.getContext();
        holder.tv_title.setText(data.getName());
        holder.tv_size.setText(android.text.format.Formatter.formatFileSize(context,data.getSize()));
        holder.tv_desc.setText(data.getDes());
        holder.rb_rating.setRating(data.getStars());
    }


    static class ViewHolder{
        TextView tv_title;
        TextView tv_size;
        TextView tv_desc;
        ImageView iv_icon;
        RatingBar rb_rating;
        LinearLayout ll_download;
    }
}
