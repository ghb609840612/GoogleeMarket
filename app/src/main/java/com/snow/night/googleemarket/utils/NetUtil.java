package com.snow.night.googleemarket.utils;

import android.os.SystemClock;
import android.text.TextUtils;

import com.snow.night.googleemarket.MyApplication;
import com.snow.night.googleemarket.net.HttpHelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

/**
 * 根据给定的url和params获取json
 * Created by Administrator on 2016/4/15.
 */
public class NetUtil {
    public static String getjson(String url, HashMap<String, String> params) {
        //拼接URl和params
     String requestUrl = createRequestUrl(url,params);

     String json = getJsonFromLocal(requestUrl);

   if(TextUtils.isEmpty(json)){
         json = getJsonFromNet(requestUrl);
    }else{
       LogUtil.e(NetUtil.class,"本地数据");
   }
        LogUtil.e(NetUtil.class,json);
        return  json;
    }

    /**
     * 从本地获取缓冲数据
     * @param requestUrl
     * @return
     */
    private static String getJsonFromLocal(String requestUrl) {
        File catchfile = getcatchfile(requestUrl);
        if(!catchfile.exists())
        {
            return null;
        }
        BufferedReader br=null;
        try {
            br = new BufferedReader(new FileReader(catchfile));
            String validTimeString = br.readLine();
            long validtime = Long.parseLong(validTimeString);
            //缓冲数据在有效期内时 将其读取出来
            if(System.currentTimeMillis()<validtime)
            {   //将数据读到buffer字符数组中 在将其写入字符内存中
                CharArrayWriter writer = new CharArrayWriter();
                char[] buffer =new char[2048];
                int len;
                if((len = br.read(buffer))!= -1){
                    writer.write(buffer,0,len);
                }
                return writer.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            IOUtils.close(br);
        }
        return null;
    }

    /**
     * 根据给定的请求地址从网络获取数据
     * @param requestUrl
     * @return
     */
    private static String getJsonFromNet(String requestUrl) {

        HttpHelper.HttpResult httpResult = HttpHelper.get(requestUrl);
        if(httpResult!= null){
            String json = httpResult.getString();
//            saveJson2Local(json,requestUrl);
//            LogUtil.e(NetUtil.class ,json);
            return  json;
        }
        return null;
    }

    /**
     * 把json数据保存到本地
     * @param json
     * @param requestUrl
     */
    private static void saveJson2Local(String json, String requestUrl) {
        if(TextUtils.isEmpty(json)){
            return;
        }
        BufferedWriter bw = null;
        try {
            File catchfile = getcatchfile(requestUrl);
            bw = new BufferedWriter(new FileWriter(catchfile));
            long tenMinutes = 10*60*1000;
            long validTime = System.currentTimeMillis()+ tenMinutes;
            bw.write(String.valueOf(validTime));  //将有效期写入第一行
            bw.newLine();
            bw.write(json);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.close(bw);
        }

    }

    /**
     * 用来获取保存json数据的文件
     * @param requestUrl
     * @return
     */
    private static File getcatchfile(String requestUrl) {
        String filename;
        try {
            filename = URLEncoder.encode(requestUrl,"UTF-8");  //防止乱码情况的发生
            File file = new File(MyApplication.getContext().getCacheDir(),filename);
            return  file;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据给定的url和params 完成请求路径
     * @param url
     * @param params
     * @return
     */
    private static String createRequestUrl(String url, HashMap<String, String> params) {
        //取出set集合中的keyset键值对
        Set<String> keySet = params.keySet();
        //由于键值对是无序的 但我们的请求路径是有序的 而且保存文件时
        // 也需要路径来命名 如果路径名不唯一 将会取不到缓冲数据 将keyset转换成arraylist
        ArrayList<String>  keys = new ArrayList(keySet);
        //然后通过collection工具类对 keys 进行排序
        Collections.sort(keys);
        //index=0&name=zs&age=45
        StringBuffer sb = new StringBuffer();
        for (String key: keys) {
            sb.append("&").append(key).append("=").append(params.get(key));
        }
        sb.deleteCharAt(0);

        String requesturl = url + "?" +sb.toString();
        return  requesturl;
    }
}
