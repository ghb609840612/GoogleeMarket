package com.snow.night.googleemarket.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.snow.night.googleemarket.R;

/**
 * Created by Administrator on 2016/4/13.
 */
public abstract class BaseFragment extends Fragment {

    private TextView tvBrootview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     View rootview = inflater.inflate(R.layout.fragmentbase, null);
        tvBrootview = (TextView) rootview.findViewById(R.id.tv_baseFragment);

        tvBrootview.setText(getTvtext());
    return  rootview;
}
    public abstract  String getTvtext();

    public String getTitle(){
        return  getClass().getSimpleName();
    }
}
