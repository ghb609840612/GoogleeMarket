package com.snow.night.googleemarket.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.snow.night.googleemarket.R;
import com.snow.night.googleemarket.base.MyBaseAdapter;
import com.snow.night.googleemarket.bean.HomeBean;
import com.snow.night.googleemarket.bean.SubjectBean;
import com.snow.night.googleemarket.net.Urls;
import com.snow.night.googleemarket.utils.LogUtil;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/14.
 */
public class SubjectListAdapter extends MyBaseAdapter<SubjectBean>{

    public SubjectListAdapter(ArrayList<SubjectBean> datas) {
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
        return R.layout.item_subject_adapter;
    }

    @Override
    public Object createHolder(View convertView, int position) {
        ViewHolder viewHolder = new ViewHolder();

        viewHolder.tv_desc = (TextView) convertView.findViewById(R.id.tv_subject_desc);
        viewHolder.iv_photo= (ImageView) convertView.findViewById(R.id.iv_subject_pic);

        return viewHolder;
    }

    @Override
    protected void showdata(int position, Object viewHolder, SubjectBean data) {
        ViewHolder holder = (ViewHolder) viewHolder;

        ImageOptions imageOptions = new ImageOptions.Builder()
                .setSize(DensityUtil.dip2px(280), DensityUtil.dip2px(120))//图片大小
                .setRadius(DensityUtil.dip2px(5))//ImageView圆角半径
                .setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.drawable.ic_default)//加载中默认显示图片
                .setFailureDrawableId(R.drawable.ic_default)//加载失败后默认显示图片
                .build();
        String url = Urls.IMAGE + "?name="+data.getUrl();
        x.image().bind(((ViewHolder) viewHolder).iv_photo,url);
        holder.tv_desc.setText(data.getDes());
    }




    static class ViewHolder{
       ImageView iv_photo;
        TextView tv_desc;

    }
}
