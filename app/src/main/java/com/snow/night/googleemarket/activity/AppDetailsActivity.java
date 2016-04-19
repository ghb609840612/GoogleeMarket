package com.snow.night.googleemarket.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.snow.night.googleemarket.R;
import com.snow.night.googleemarket.bean.AppDetailBean;
import com.snow.night.googleemarket.net.Urls;
import com.snow.night.googleemarket.utils.JsonUtil;
import com.snow.night.googleemarket.utils.Keys;
import com.snow.night.googleemarket.utils.LogUtil;
import com.snow.night.googleemarket.utils.NetUtil;
import com.snow.night.googleemarket.view.StateLayout;

import org.w3c.dom.Text;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/4/19.
 */
public class AppDetailsActivity extends ActionBarActivity {
    private static final int REQUESTAPPDETAIL = 301 ;
    private ImageView ivAppdetaiIcon;
    private TextView tvAppdetailName;
    private TextView tvAppdetailVersion;
    private TextView tvAppdetailDate;
    private TextView tvAppdetailNumber;
    private TextView tvAppdetailSize;
    private StateLayout appdetailState;
    private RatingBar rating;
    private ImageView[] imageViews;
    private TextView[] textViews;
    private LinearLayout llSafeImageviews;
    private LinearLayout llSafeContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appdetailState = new StateLayout(this);
        appdetailState.setContentView(R.layout.activity_appinfo);
        setContentView( appdetailState);
        initview();
        initdata();
        initlistener();
    }

    private void initview() {
//        应用信息
        ivAppdetaiIcon = (ImageView) findViewById(R.id.iv_appdetail_icon);
        tvAppdetailName = (TextView) findViewById(R.id.tv_appdetail_name);
        tvAppdetailVersion = (TextView) findViewById(R.id.tv_appdetail_version);
        tvAppdetailDate = (TextView) findViewById(R.id.tv_appdetail_date);
        tvAppdetailNumber = (TextView) findViewById(R.id.tv_appdetail_number);
        tvAppdetailSize = (TextView) findViewById(R.id.tv_appdetail_size);
        rating = (RatingBar) findViewById(R.id.rb_appdetail_rating);
//        安全信息
        imageViews = new ImageView[4];
        imageViews[0]= (ImageView) findViewById(R.id.iv_safe_1);
        imageViews[1] = (ImageView) findViewById(R.id.iv_safe_2);
        imageViews[2] = (ImageView) findViewById(R.id.iv_safe_3);
        imageViews[3] = (ImageView) findViewById(R.id.iv_safe_4);

        textViews = new TextView[4];
        textViews[0] = (TextView) findViewById(R.id.tv_safe_1);
        textViews[1] = (TextView) findViewById(R.id.tv_safe_2);
        textViews[2] = (TextView) findViewById(R.id.tv_safe_3);
        textViews[3] = (TextView) findViewById(R.id.tv_safe_4);
        llSafeImageviews = (LinearLayout) findViewById(R.id.ll_safe_imageviews);
        llSafeContent = (LinearLayout) findViewById(R.id.ll_safe_content);
        //默认content页面不显示
        llSafeContent.getLayoutParams().height =0;
        llSafeContent.requestLayout();
        for (int i =0; i<imageViews.length; i++){
            imageViews[i].setVisibility(View.INVISIBLE);
            textViews[i].setVisibility(View.INVISIBLE);
        }
    }

    private void initdata() {
        requestAsyncTask(REQUESTAPPDETAIL);
    }
    private boolean contentIsOpen;
    private void initlistener() {
        llSafeImageviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contentIsOpen){
                    //将content页面关闭

                }else{
                    //将content页面打开


                }
                contentIsOpen = !contentIsOpen;
            }
        });
    }

    /**
     * 请求联网获取数据的操作
     * @param requestType
     */
    void requestAsyncTask(final int requestType){
        new AsyncTask<Void,Void,Object>(){
            @Override
            protected Object doInBackground(Void... params) {
                return AppDetailsActivity.this.doInBackground(requestType);
            }
            @Override
            protected void onPostExecute(Object result) {
                AppDetailsActivity.this.onPostExecute(requestType,result);
            }
        }.execute();
    }

    private void onPostExecute(int requestType, Object result) {
            if(requestType ==REQUESTAPPDETAIL && result !=null){
                AppDetailBean appdetailInfo = JsonUtil.json2Bean((String) result, AppDetailBean.class);
                appdetailState.showContentview();
                showDataFirst(appdetailInfo);
                showDataSecond(appdetailInfo.safe);
            }else if(result== null)
            {
                appdetailState.showFail();
            }
    }



    private void showDataFirst(AppDetailBean appdetailInfo) {

        String url = Urls.IMAGE+"?name=" +appdetailInfo.getIconUrl();
        x.image().bind(ivAppdetaiIcon,url);
        tvAppdetailName.setText(appdetailInfo.getName());
        tvAppdetailVersion.setText(appdetailInfo.getVersion());
        tvAppdetailDate.setText(appdetailInfo.getDate());
        String size = android.text.format.Formatter.formatFileSize(this, appdetailInfo.getSize());
        tvAppdetailNumber.setText(appdetailInfo.getDownloadNum());
        tvAppdetailSize .setText(size);
        rating.setRating(appdetailInfo.getStars());
    }
    private void showDataSecond(ArrayList<AppDetailBean.SafeInfo> safes) {
        if(safes == null || safes.isEmpty()){
            return;
        }
        for (int i = 0; i <safes.size() ; i++) {
            imageViews[i].setVisibility(View.VISIBLE);
            x.image().bind(imageViews[i], Urls.IMAGE+"?name="+safes.get(i).getSafeUrl());
            textViews[i].setVisibility(View.VISIBLE);
            textViews[i].setText(safes.get(i).getSafeDes());
        }
    }
    private Object doInBackground(int requestType) {
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("packageName",getIntent().getStringExtra(Keys.PACKAGE_NAME));
        String getjson = NetUtil.getjson(Urls.DETAIL, params);
        return getjson;
    }
}
