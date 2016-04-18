package com.snow.night.googleemarket.adapter;

import android.support.design.internal.ForegroundLinearLayout;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.snow.night.googleemarket.MyApplication;
import com.snow.night.googleemarket.R;
import com.snow.night.googleemarket.net.Urls;
import com.snow.night.googleemarket.utils.LogUtil;

import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/17.
 */
public class BannerAdapterSelf extends PagerAdapter{
    private ArrayList<String>  imageUrls ;
    private ArrayList<ImageView>  picViews = new ArrayList<ImageView>() ;
    public BannerAdapterSelf(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
        for (String imageUrl: imageUrls) {
            ImageView imageView = new ImageView(MyApplication.getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setBackgroundResource(R.drawable.ic_default);
            picViews.add(imageView);

        }
    }

    @Override
    public int getCount() {
        return imageUrls.size()*1000*50;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position = position% imageUrls.size();
        ImageView imageView = picViews.get(position);
        String url = Urls.IMAGE + "?name="+imageUrls.get(position);
        LogUtil.e(this,url);
        x.image().bind(imageView,url);
        container.addView(imageView);

        return imageView;
    }
}
