package com.snow.night.googleemarket.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.snow.night.googleemarket.MyApplication;

/**
 * Created by Administrator on 2016/4/13.
 */
public class CommonUtils {
    // string id  转成  String 对象
    public static String getString(int id){
        Context mContext = MyApplication.getContext();
        Resources resources = mContext.getResources();
        return resources.getString(id);
    }



    public static Context getContext(){
        return MyApplication.getContext();
    }

    /**
     * xml布局 转成 View对象
     * @param id
     * @return
     */
    public static View inflate(int id){
//		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		LayoutInflater.from(getContext()).inflate(id, null);

        return View.inflate(getContext(), id, null);
    }

    /**
     * dip 转 px
     * px = dip * density
     * @param dip
     * @return
     */
    public static int dip2px(int dip){
        float density = getContext().getResources().getDisplayMetrics().density;
        // float -> int  1.1 1  1.6 1
        //				 1.6 1  2.1 2
        return (int) (dip * density + 0.5f);
    }

    public static int px2dip(int px){
        float density = getContext().getResources().getDisplayMetrics().density;
        // float -> int  1.1 1  1.6 1
        //				 1.6 1  2.1 2
        return (int) (px / density + 0.5f);
    }

    /**
     * 运行一个任务在主线程
     */
    public static void runInMainThread(Runnable task){
        //判断当前线程是否是主线程
        if(isMainThread()){
            //当前是主线程
            task.run();
        }else{
            Handler handler = MyApplication.getHandler();
            handler.post(task);
        }
    }

    /**
     * 判断当前线程是否是主线程
     * @return
     */
    public static boolean isMainThread(){
//		if(android.os.Process.myTid() == MyApplication.getMainThreadId()){
//			return true;
//		}else{
//			return false;
//		}

        return android.os.Process.myTid() == MyApplication.getMainThreadId();
    }



}
