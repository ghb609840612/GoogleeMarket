package com.snow.night.googleemarket.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;
import com.snow.night.googleemarket.R;
import com.snow.night.googleemarket.bean.AppDetailBean;
import com.snow.night.googleemarket.net.Urls;
import com.snow.night.googleemarket.utils.JsonUtil;
import com.snow.night.googleemarket.utils.Keys;
import com.snow.night.googleemarket.utils.LogUtil;
import com.snow.night.googleemarket.utils.NetUtil;
import com.snow.night.googleemarket.view.StateLayout;

import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/4/19.
 */
public class AppDetailsActivity extends Activity {
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
    private  ValueAnimator animator;
    private ImageView[] imageviewscreens;
    private ImageView ivSafeArrow;
    private ImageView ivDescArrow;
    private LinearLayout llAppdetail;
    private ScrollView svAppdetailInfo;
    private TextView tvAppdetaiDesc;
    private AppDetailBean appdetailInfo;

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
        ivSafeArrow = (ImageView) findViewById(R.id.iv_safe_arrow);
        //默认content页面不显示
        llSafeContent.getLayoutParams().height =0;
        llSafeContent.requestLayout();
        for (int i =0; i<imageViews.length; i++){
            imageViews[i].setVisibility(View.GONE);
            textViews[i].setVisibility(View.GONE);
        }
        //截图信息
        LinearLayout llAppdetailScreen = (LinearLayout) findViewById(R.id.ll_appdetail_screen);
        imageviewscreens = new ImageView[llAppdetailScreen.getChildCount()];
        for (int i = 0; i <imageviewscreens.length ; i++) {
            imageviewscreens[i] = (ImageView) llAppdetailScreen.getChildAt(i);
            imageviewscreens[i].setVisibility(View.GONE);
        }
        //描述信息
        tvAppdetaiDesc = (TextView) findViewById(R.id.tv_appdetail_descself);
        ivDescArrow = (ImageView) findViewById(R.id.iv_desc_arrow);
        llAppdetail = (LinearLayout) findViewById(R.id.ll_appdetail_desc);
        svAppdetailInfo = (ScrollView) findViewById(R.id.sv_appdetailinfo);
    }

    private void initdata() {
        requestAsyncTask(REQUESTAPPDETAIL);
    }
    private boolean contentIsOpen;
    private void initlistener() {
        llSafeImageviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llSafeContent.measure(0,0);

                if(contentIsOpen){
                    //将content页面关闭
                    animator = ValueAnimator.ofInt(llSafeContent.getMeasuredHeight(),0 );
                    ivSafeArrow.setBackgroundResource(R.drawable.arrow_down);
                }else{
                    //将content页面打开
                    animator = ValueAnimator.ofInt(0,llSafeContent.getMeasuredHeight() );
                    ivSafeArrow.setBackgroundResource(R.drawable.arrow_up);
                }
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int heightvalue = (int) valueAnimator.getAnimatedValue();
                        //为llSafeContent的高赋值
                        llSafeContent.getLayoutParams().height = heightvalue;
                        llSafeContent.requestLayout();
                    }
                });
               animator.setDuration(350);
                animator.start();
                contentIsOpen = !contentIsOpen;
            }
        });
        llAppdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDescIsShow();
            }
        });
    }
    private  boolean tvDescIsshow = false;
    private void tvDescIsShow() {
        int alllinesheight = getAlllinesheight();
        int _5linesheight = get5linesheight();
        if(alllinesheight < _5linesheight){
            return;
        }
        ValueAnimator valueAnimator;
        if(!tvDescIsshow){
            //扩大tv的界面
            valueAnimator = ValueAnimator.ofInt(_5linesheight, alllinesheight);
            ivDescArrow.setBackgroundResource(R.drawable.arrow_up);


        }else{
            //缩小tv的界面
            valueAnimator = ValueAnimator.ofInt(alllinesheight, _5linesheight);
            ivDescArrow.setBackgroundResource(R.drawable.arrow_down);
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator val) {
                int animatedValue = (int) val.getAnimatedValue();
                tvAppdetaiDesc.getLayoutParams().height = animatedValue;
                tvAppdetaiDesc.requestLayout();



            }
        });

            valueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {}

                @Override
                public void onAnimationEnd(Animator animator) {
                    svAppdetailInfo.fullScroll(ScrollView.FOCUS_DOWN);

                }

                @Override
                public void onAnimationCancel(Animator animator) {}

                @Override
                public void onAnimationRepeat(Animator animator) {}
            });
        valueAnimator.setDuration(350);
        valueAnimator.start();
        tvDescIsshow = !tvDescIsshow;
        }




    private int get5linesheight() {
        TextView textView = new TextView(this);
        textView.setTextSize(14);
        textView.setText("/n/n/n/n");
        textView.measure(0,0);
      return   textView.getMeasuredHeight();
    }

    private int getAlllinesheight() {
        TextView textView = new TextView(this);
        textView.setTextSize(14);
        textView.setText(tvAppdetaiDesc.getText());

        int measureSpec = View.MeasureSpec.makeMeasureSpec(tvAppdetaiDesc.getWidth(), View.MeasureSpec.EXACTLY);
//        textView.measure(0,0);
        textView.measure(measureSpec,0);
        LogUtil.e(this,tvAppdetaiDesc.getText());
        return   textView.getMeasuredHeight();
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
                appdetailInfo = JsonUtil.json2Bean((String) result, AppDetailBean.class);
                appdetailState.showContentview();
                showDataFirst(appdetailInfo);
                showDataSecond(appdetailInfo.safe);
                showDataThird(appdetailInfo.screen);
                showDataFourth(appdetailInfo);
            }else if(result== null)
            {
                appdetailState.showFail();
            }
    }



    private void showDataFirst(AppDetailBean appdetailInfo) {
        if(appdetailInfo ==null )
        {
            return;
        }
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

    private void showDataThird(ArrayList<String> screens) {
        if(screens == null || screens.isEmpty()){
            return;
        }
        for (int i = 0; i < screens.size() ; i++) {
            imageviewscreens[i].setVisibility(View.VISIBLE);
           x.image().bind(imageviewscreens[i],Urls.IMAGE + "?name="+ screens.get(i));

        }
    }
    private boolean isFirstEnter = true;
    private void showDataFourth(AppDetailBean appinfo) {
        //TODO 以后还能优化么？
        tvAppdetaiDesc.setText(appinfo.getDes());
    }

    private Object doInBackground(int requestType) {

        HashMap<String,String> params = new HashMap<String,String>();
        params.put("packageName",getIntent().getStringExtra(Keys.PACKAGE_NAME));
        String getjson = NetUtil.getjson(Urls.DETAIL, params);
        return getjson;
    }
}
