package com.snow.night.googleemarket.adapter;


import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.snow.night.googleemarket.MyApplication;
import com.snow.night.googleemarket.stellar.StellarMap;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Administrator on 2016/4/17.
 */
public class StellerMapAdapter  implements StellarMap.Adapter {
    private ArrayList<String> datas;
     private   Random random = new Random();
    public StellerMapAdapter(ArrayList<String> datas) {
        this.datas = datas;
    }

    @Override
    public int getGroupCount() {
        return 2;
    }

    @Override
    public int getCount(int group) {
        return 20;
    }

    @Override
    public View getView(int group, int position, View convertView) {
        final TextView textView = new TextView(MyApplication.getContext());
        textView.setText(datas.get(position));
        float sizes =10 + random.nextInt(20);
        textView.setTextSize(sizes);
        int red = 30+ random.nextInt(180);
        int green= 30+ random.nextInt(180);
        int blue= 30+ random.nextInt(180);
        int color = Color.rgb(red,green,blue);
        textView.setTextColor(color);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),textView.getText(),Toast.LENGTH_SHORT).show();
            }
        });
        return textView;
    }

    @Override
    public int getNextGroupOnPan(int group, float degree) {
        return 0;
    }

    @Override
    public int getNextGroupOnZoom(int group, boolean isZoomIn) {
        return 0;
    }
}
