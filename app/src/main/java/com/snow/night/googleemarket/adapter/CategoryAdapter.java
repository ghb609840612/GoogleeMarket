package com.snow.night.googleemarket.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.snow.night.googleemarket.MyApplication;
import com.snow.night.googleemarket.R;
import com.snow.night.googleemarket.base.MyBaseAdapter;
import com.snow.night.googleemarket.bean.CategoryBean;
import com.snow.night.googleemarket.bean.CategoryBean.CategoryInfo;
import com.snow.night.googleemarket.net.Urls;

import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/18.
 */
public class CategoryAdapter  extends MyBaseAdapter<Object> {
    private static final int DATE_TYPE_TITLE = 0;
    private static final int DATA_TYPE_PIC =1;

    public CategoryAdapter(ArrayList<Object> datas) {
        super(datas);
    }

    @Override
    protected int getLayoutResId(int position) {
        return  getItemViewType(position)==DATA_TYPE_PIC? R.layout.item_category_pic:R.layout.item_category_title;
    }

    @Override
    protected Object createHolder(View convertView, int position) {
        ViewHolder viewHolder = new ViewHolder();
        int type = getItemViewType(position);
        if(type ==DATE_TYPE_TITLE){
            viewHolder.tv_title = (TextView) convertView;
        }else{
            LinearLayout containner = (LinearLayout) convertView;
            //第一个子条目
           viewHolder.ll_1 = (LinearLayout) containner.getChildAt(0);

            //第二个子条目
            viewHolder.ll_2 = (LinearLayout) containner.getChildAt(1);

            //第三个子条目
            viewHolder.ll_3 = (LinearLayout) containner.getChildAt(2);

        }
        return viewHolder;
    }

    @Override
    protected void showdata(int position, Object viewHolder, Object data) {
        int type = getItemViewType(position);

        ViewHolder holder = (ViewHolder) viewHolder;
            if(type ==DATE_TYPE_TITLE){
            holder.tv_title.setText((String)data);
        }else{
//            CategoryInfo info =( (CategoryInfo)(((ArrayList)data).get(position)));
                CategoryInfo info =( (CategoryInfo)data);
           String url1 = Urls.IMAGE + "?name=" + info.getUrl1();
           String url2 = Urls.IMAGE + "?name=" + info.getUrl2();
           String url3 = Urls.IMAGE + "?name=" + info.getUrl3();
            x.image().bind((ImageView) holder.ll_1.getChildAt(0),url1);
            x.image().bind((ImageView) holder.ll_2.getChildAt(0),url2);
            x.image().bind((ImageView) holder.ll_3.getChildAt(0),url3);

            ((TextView)holder.ll_1.getChildAt(1)).setText(info.getName1());
            ((TextView)holder.ll_2.getChildAt(1)).setText(info.getName2());
            ((TextView)holder.ll_3.getChildAt(1)).setText(info.getName3());
            holder.ll_1.setOnClickListener(monOnClickListener);
            holder.ll_2.setOnClickListener(monOnClickListener);
            holder.ll_3.setOnClickListener(monOnClickListener);

        }
    }
   View.OnClickListener monOnClickListener = new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           LinearLayout linearLayout = (LinearLayout) v;
           TextView text= (TextView) linearLayout.getChildAt(1);
           Toast.makeText(MyApplication.getContext(),text.getText(),Toast.LENGTH_SHORT).show();
       }
   };
    @Override
    public int getItemViewType(int position) {
        Object data = datas.get(position);
        return data instanceof String ? DATE_TYPE_TITLE: DATA_TYPE_PIC;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    static class ViewHolder{
        TextView tv_title;

        LinearLayout ll_1;
        LinearLayout ll_2;
        LinearLayout ll_3;

    }
}
