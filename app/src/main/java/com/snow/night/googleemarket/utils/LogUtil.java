package com.snow.night.googleemarket.utils;

import android.util.Log;

/**
 * 封装万能log 任意传参数
 * Created by Administrator on 2016/4/15.
 */
public class LogUtil {
    public static boolean isShowLog = true;
    public static void e(Object objtag,Object objmsg){
        if(!isShowLog){
            return;
        }
        String tag;
        String msg;
        if(objtag instanceof String){
            tag = (String) objtag;
        }else if(objtag instanceof  Class){
            tag = ((Class) objtag).getSimpleName();
        }else{
            tag = objtag.getClass().getSimpleName();
        }
        msg= (objmsg ==null || objmsg.toString()== null)? "null": objmsg.toString();
        Log.e(tag,msg);
    }
}
