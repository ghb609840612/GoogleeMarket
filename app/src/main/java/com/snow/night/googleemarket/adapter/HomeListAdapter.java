package com.snow.night.googleemarket.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.snow.night.googleemarket.base.MyBaseAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/14.
 */
public class HomeListAdapter extends MyBaseAdapter<String>{



    public HomeListAdapter(ArrayList<String> datas) {
        super(datas);
    }


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
    }
    @Override
    public int getLayoutResId(int position) {
        return android.R.layout.simple_expandable_list_item_1;
    }

    @Override
    public Object createHolder(View convertView, int position) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.tv = (TextView) convertView.findViewById(android.R.id.text1);
        return viewHolder;
    }

    @Override
    protected void showdata(int position, Object viewHolder, String data) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tv.setText(data);
    }
    static class ViewHolder{
        TextView tv;
    }
}
