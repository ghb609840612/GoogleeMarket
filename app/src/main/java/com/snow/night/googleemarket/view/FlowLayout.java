package com.snow.night.googleemarket.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.snow.night.googleemarket.utils.CommonUtils;
import com.snow.night.googleemarket.utils.LogUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/20.
 */
public class FlowLayout extends ViewGroup {
    //只在代码中用
    public FlowLayout(Context context) {
        super(context);
    }
    private  ArrayList<ArrayList<View>>  alllines = new ArrayList<ArrayList<View>>();
    private  int VerticlaSpacing = CommonUtils.dip2px(6);
    private  int HorizotalSpacing = CommonUtils.dip2px(6);
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        alllines.clear();
        ArrayList<View> oneline =null;
        int containerWidth = MeasureSpec.getSize(widthMeasureSpec);
        LogUtil.e(this,containerWidth);
        for (int i = 0; i <  getChildCount(); i++) {
            View view = getChildAt(i);
            view.measure(0,0);

            if(i==0 || view.getMeasuredWidth() >getUseableWidth(containerWidth,oneline)){
                oneline= new ArrayList<View>( );
                alllines.add(oneline);
            }
            oneline.add(view);
        }
        int containerHeight = getAlllinesheight()+getPaddingTop()+getPaddingBottom()+(alllines.size()-1)*VerticlaSpacing;
        LogUtil.e(this,containerHeight);
        setMeasuredDimension(containerWidth,containerHeight);
    }

    private int getUseableWidth(int containerWidth, ArrayList<View> oneline) {
       int onelinewidth = 0;
        for (View view: oneline
             ) {
            onelinewidth += view.getMeasuredWidth();
        }
        return containerWidth - onelinewidth-getPaddingBottom()*2 -(oneline.size()-1)*HorizotalSpacing;
    }


    private int getAlllinesheight() {
        if(alllines.isEmpty())
        {return 0;}
        return getChildAt(0).getMeasuredHeight()*alllines.size();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int tempBottom = getPaddingBottom();
        for (int i = 0; i < alllines.size() ; i++) {
            ArrayList<View> oneline = alllines.get(i);
            int tempRight = getPaddingRight();
            int averageWidth = getUseableWidth(getMeasuredWidth(),oneline)/oneline.size();
            for (int j = 0; j < oneline.size(); j++) {
                View view = oneline.get(j);
                //先执行measure方法 所以测量孩子的高和宽    可以获得
                int measuredWidth = view.getMeasuredWidth();
                int measuredHeight = view.getMeasuredHeight();
                int left =( j ==0?  tempRight : tempRight+HorizotalSpacing);
                int top = (i == 0? tempBottom : tempBottom +VerticlaSpacing);
                int right = left+measuredWidth+averageWidth;
                int bottom = top+ measuredHeight;
                view.layout(left,top,right,bottom);
                tempRight = right;
            }
            tempBottom = oneline.get(0).getBottom();
        }


    }
}
