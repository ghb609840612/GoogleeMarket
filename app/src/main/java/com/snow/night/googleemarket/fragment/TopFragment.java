package com.snow.night.googleemarket.fragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.snow.night.googleemarket.MyApplication;
import com.snow.night.googleemarket.R;
import com.snow.night.googleemarket.adapter.HomeListAdapter;
import com.snow.night.googleemarket.base.BaseFragment;
import com.snow.night.googleemarket.bean.HomeBean;
import com.snow.night.googleemarket.net.Urls;
import com.snow.night.googleemarket.utils.CommonUtils;
import com.snow.night.googleemarket.utils.JsonUtil;
import com.snow.night.googleemarket.utils.LogUtil;
import com.snow.night.googleemarket.utils.NetUtil;
import com.snow.night.googleemarket.view.FlowLayout;
import com.snow.night.googleemarket.view.LoadMoreListView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Administrator on 2016/4/13.
 */
public class TopFragment extends BaseFragment {

    private HomeListAdapter homeListAdapter;
    private LoadMoreListView listView;
    private FlowLayout flowLayout;

    @Override
    public String getTitle() {
        return "排行";
    }

    @Override
    public View getContentView() {
        ScrollView scrollView = new ScrollView(MyApplication.getContext());
        flowLayout = new FlowLayout(MyApplication.getContext());
        int padding = CommonUtils.dip2px(6);
        flowLayout.setPadding(padding,padding,padding,padding);

        scrollView.addView(flowLayout);
        return scrollView;
    }

    @Override
    public int getContentViewById() {
        return R.layout.fragment_home;
    }



    @Override
    protected void onPostExecute(int requestType, Object result) {
        Type type = new TypeToken< ArrayList<String>>(){}.getType();
        ArrayList<String> datas = JsonUtil.json2Bean((String) result, type);
        if(checkDataIsShowStateView(datas)){
            rootview.showContentview();
            for(int i = 0; i< datas.size();i++){
                final TextView tv = createRandomSelectorTextview();

                tv.setText(datas.get(i));
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MyApplication.getContext(),tv.getText(),Toast.LENGTH_SHORT).show();
                    }
                });
                flowLayout.addView(tv);
            }
        }
        }

    private TextView createRandomSelectorTextview() {
        TextView textview = new TextView(MyApplication.getContext());
        textview.setTextColor(Color.WHITE);
        int size = CommonUtils.dip2px(6);
        textview.setPadding(size,size,size,size);
        textview.setGravity(Gravity.CENTER);
        textview.setBackgroundDrawable(creatrandomSelector());
        return textview;
    }

    /**
     * 获取随机的状态选择器
     * @return
     */
    private Drawable creatrandomSelector() {
        StateListDrawable stateListDrawable =  new StateListDrawable();
        int[] pressedState = new int[]{android.R.attr.state_pressed,android.R.attr.state_enabled};
        int[] nomalState = new int[]{};
        stateListDrawable.addState(pressedState,createRandomDrawable());
        stateListDrawable.addState(nomalState,createRandomDrawable());
        return stateListDrawable;
    }

    /**
     * 获取随机的颜色图片
     * @return
     */
    private Drawable createRandomDrawable() {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(CommonUtils.dip2px(6));
        gradientDrawable.setColor(createRandomColor());
        return  gradientDrawable;
    }

    /**
     * 获取随机的颜色
     * @return
     */
    private int createRandomColor() {
        Random ran = new Random();
        int red =30+ ran.nextInt(200) ;
        int green=30+ ran.nextInt(200) ;
        int blue=30+ ran.nextInt(200);
        int color = Color.rgb(red, green, blue);
        return color;
    }


    @Override
    protected Object doInBackground(int requestType) {
        SystemClock.sleep(1000);
        switch (requestType){
            case REQUEST_INIT_DATA:
                String result = NetUtil.getjson(Urls.TOP, null);

                return  result;
        }
        return null;
    }

    @Override
    public void initview() {

    }
    @Override
    public void initdata() {
        requestAsyncTask(REQUEST_INIT_DATA);
    }
    @Override
    public void initlistener() {

    }



}
