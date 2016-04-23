package com.snow.night.googleemarket.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;
import com.snow.night.googleemarket.R;
import com.snow.night.googleemarket.bean.AppDetailBean;
import com.snow.night.googleemarket.bean.DownLoadInfo;
import com.snow.night.googleemarket.manager.DownLoadManager;
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
    private AppDetailBean appinfodownloadself;
    private ProgressBar pbDownLoad;
    private TextView tvDownLoad;

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
        //下载信息
        pbDownLoad = (ProgressBar) findViewById(R.id.pb_download);
        tvDownLoad = (TextView) findViewById(R.id.tv_download);


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
        //详情描述页面的点击事件
        llAppdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDescIsShow();
            }
        });
        //下载模块pb的点击事件

    }

    /**
     * 注册观察者的方法
     */




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
                AppDetailBean appDetailBean = JsonUtil.json2Bean((String) result, AppDetailBean.class);

                appdetailState.showContentview();
                showDataFirst(appDetailBean);
                showDataSecond(appDetailBean.safe);
                showDataThird(appDetailBean.screen);
                showDataFourth(appDetailBean);
                downloadapp(appDetailBean);
//                setdownloadAPP(appDetailBean);
                DownLoadInfo loadInfo = DownLoadManager.getInstance().getdownloadinfo(appDetailBean);
                if(loadInfo!=null){
                    processState(loadInfo);
                }
            }else if(result== null)
            {
                appdetailState.showFail();
            }

    }

    private void downloadapp(final AppDetailBean appinfodownload) {
        pbDownLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DownLoadManager downLoadManager = DownLoadManager.getInstance();
                DownLoadInfo downLoadInfo = downLoadManager.getdownloadinfo(appinfodownload);
                if(downLoadInfo ==null){
                    downLoadManager.download(appinfodownload);
                }else{
                    if(downLoadInfo.getState() == DownLoadManager.DOWNLOAD_STATE_DOWNLOADING ||
                            downLoadInfo.getState() == DownLoadManager.DOWNLOAD_STATE_WAITING ){
                        downLoadManager.pause(appinfodownload);
                    }else if(downLoadInfo.getState() == DownLoadManager.DOWNLOAD_STATE_PAUSE ||
                            downLoadInfo.getState() == DownLoadManager.DOWNLOAD_STATE_ERROR){
                        downLoadManager.download(appinfodownload);
                    }else if(downLoadInfo.getState() == DownLoadManager.DOWNLOAD_STATE_SUCCESS){
                        downLoadManager.install(appinfodownload);
                    }
                }
            }
        } );
        registDownloadObserver(appinfodownload);
    }

    public void registDownloadObserver(final AppDetailBean appinfodownload){
        DownLoadManager.getInstance().registObserver(new DownLoadManager.DownLoadObserver() {
            @Override
            public void onDownLoadinfoChange(DownLoadInfo info) {
                if(!info.getId().equals(appinfodownload.getId()))
                    return;
                processState(info);

            }
        });
    }

    /**
     * 下载按钮点击事件对应的处理
     * @param info
     */
    private void processState(DownLoadInfo info) {
        if(info ==null){return;}
        int progress = (int) (info.getCurrentposition()*100f/info.getSize());
        switch (info.getState()){
            case DownLoadManager.DOWNLOAD_STATE_DOWNLOADING:
                pbDownLoad.setProgress(progress);
                tvDownLoad.setText(progress+"%");
                tvDownLoad.setBackgroundResource(0);

                break;
            case DownLoadManager.DOWNLOAD_STATE_PAUSE:
                tvDownLoad.setText("继续下载");
                pbDownLoad.setProgress(progress);
                tvDownLoad.setBackgroundResource(0);

                break;
            case DownLoadManager.DOWNLOAD_STATE_ERROR:
                tvDownLoad.setText("重新下载");
                break;
            case DownLoadManager.DOWNLOAD_STATE_SUCCESS:
                tvDownLoad.setText("安装");
                break;
            case DownLoadManager.DOWNLOAD_STATE_WAITING:
                tvDownLoad.setText("等待");
                pbDownLoad.setProgress(progress);
                tvDownLoad.setBackgroundResource(0);
                break;
        }
    }

    private void showDataFirst(AppDetailBean appdetailInfoselef) {
        if(appdetailInfoselef ==null )
        {
            return;
        }
        String url = Urls.IMAGE+"?name=" +appdetailInfoselef.getIconUrl();
        x.image().bind(ivAppdetaiIcon,url);
        tvAppdetailName.setText(appdetailInfoselef.getName());
        tvAppdetailVersion.setText(appdetailInfoselef.getVersion());
        tvAppdetailDate.setText(appdetailInfoselef.getDate());
        String size = android.text.format.Formatter.formatFileSize(this, appdetailInfoselef.getSize());
        tvAppdetailNumber.setText(appdetailInfoselef.getDownloadNum());
        tvAppdetailSize .setText(size);
        rating.setRating(appdetailInfoselef.getStars());
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

    /**
     * 第二次进入界面时对下载模块对应组件的初始化
     * @param loadInfo
     */
    private void processStateSelf(DownLoadInfo loadInfo) {
        if(loadInfo ==null){return;}
        int progress = (int) (loadInfo.getCurrentposition()*100f/loadInfo.getSize());
        switch (loadInfo.getState()){
            case DownLoadManager.DOWNLOAD_STATE_DOWNLOADING:
                pbDownLoad.setProgress(progress);

                tvDownLoad.setBackgroundResource(0);

                break;
            case DownLoadManager.DOWNLOAD_STATE_PAUSE:
                tvDownLoad.setText("继续下载");
                tvDownLoad.setBackgroundResource(0);

                break;
            case DownLoadManager.DOWNLOAD_STATE_ERROR:
                tvDownLoad.setText("重新下载");
                break;
            case DownLoadManager.DOWNLOAD_STATE_SUCCESS:
                tvDownLoad.setText("安装");
                break;
            case DownLoadManager.DOWNLOAD_STATE_WAITING:
                tvDownLoad.setText("等待");
                tvDownLoad.setBackgroundResource(0);
                break;
        }
    }

}
