package com.snow.night.googleemarket.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/14.
 */
public abstract  class MyBaseAdapter<T> extends BaseAdapter{
    protected ArrayList<T> datas;

    public MyBaseAdapter(ArrayList<T> datas) {
        this.datas = datas;
    }

    public   ArrayList<T> getData(){
        if(datas == null)
        {
            datas = new   ArrayList<T>();
        }
        return  datas;
    }
    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

//    public View getView(int position, View convertView, ViewGroup parent) {
//        if(convertView == null)
//        {
//            convertView = View.inflate(parent.getContext(),
//                    android.R.layout.simple_expandable_list_item_1,null);
//            viewHolder = new ViewHolder();
//            viewHolder.tv = (TextView) convertView.findViewById(android.R.id.text1);
//            convertView.setTag(viewHolder);
//        }else{
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//        String data =datas.get(position);
//        viewHolder.tv.setText(data);
//        return convertView;
//    }
//    public View getView(int position, View convertView, ViewGroup parent) {
//       Object viewHolder;
//        if(convertView == null)
//        {
//            convertView = View.inflate(parent.getContext(),
//                    getLayoutResId(position),null);
//            viewHolder = createHolder(convertView,position);
//            convertView.setTag(viewHolder);
//        }else{
//            viewHolder = convertView.getTag();
//        }
//        T data =datas.get(position);
//       showdata(position,viewHolder,data);
//        return convertView;
//    }

    protected abstract int getLayoutResId(int position);
    protected abstract Object createHolder(View convertView, int position);
    protected abstract void showdata(int position, Object viewHolder, T data);
}
