package com.snow.night.googleemarket.fragment;

import android.os.SystemClock;
import android.view.View;

import com.snow.night.googleemarket.R;
import com.snow.night.googleemarket.base.BaseFragment;

/**
 * Created by Administrator on 2016/4/13.
 */
public class GameFragment extends BaseFragment {



    @Override
    public View getContentView() {
        return null;
    }

    @Override
    public int getContentViewById() {
        return R.layout.fragmentbase;
    }
    @Override
    public void initview() {

    }
    @Override
    public void initdata() {
        rootview.showFail();
    }
    @Override
    public void initlistener() {

    } @Override
    protected void onPostExecute(int requestType, Object o) {
        switch (requestType){
            case REQUEST_INIT_DATA:
                break;
        }
    }

    @Override
    protected Object doInBackground(int requestType) {
        switch (requestType){
            case REQUEST_INIT_DATA:

                break;
        }

        return null;
    }

}
