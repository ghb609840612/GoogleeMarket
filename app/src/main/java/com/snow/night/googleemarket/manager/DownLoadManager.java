package com.snow.night.googleemarket.manager;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.snow.night.googleemarket.MyApplication;
import com.snow.night.googleemarket.bean.AppDetailBean;
import com.snow.night.googleemarket.bean.DownLoadInfo;
import com.snow.night.googleemarket.bean.HomeBean;
import com.snow.night.googleemarket.net.HttpHelper;
import com.snow.night.googleemarket.net.Urls;
import com.snow.night.googleemarket.utils.CommonUtils;
import com.snow.night.googleemarket.utils.IOUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

/**下载管理类
 * Created by Administrator on 2016/4/21.
 */
public class DownLoadManager {
    /**
     * 下载状态
     *
     */
    //保存下载信息的map
    private Map<String,DownLoadInfo> downloadInfos = new HashMap<String,DownLoadInfo>();
    private Map<String,DownLoadTask> downloadTasks = new HashMap<String,DownLoadTask>();
   //保存观察者的集合
    private  ArrayList<DownLoadObserver> observers = new ArrayList<DownLoadObserver>();
    public static final String  DOWNLOAD_URL = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+
            "googlemarket"+File.separator+"download"+File.separator;
    public  static final int DOWNLOAD_STATE_INIT = 0;
    public  static final int DOWNLOAD_STATE_WAITING = 1;
    public  static final int DOWNLOAD_STATE_DOWNLOADING = 2;
    public  static final int DOWNLOAD_STATE_PAUSE = 3;
    public  static final int DOWNLOAD_STATE_ERROR = 4;
    public  static final int DOWNLOAD_STATE_SUCCESS = 5;
    //单例模式 饿汉式
    private  static  DownLoadManager downLoadManager = new DownLoadManager();
    private HttpHelper.HttpResult httpResult;

    private DownLoadManager(){
        File file = new File(DOWNLOAD_URL);
        if(!file.exists()){
            file.mkdirs();
        }
    }
    public static DownLoadManager getInstance(){
      return  downLoadManager;
    }

    public void download(AppDetailBean appinfo){
        DownLoadInfo downLoadInfo = downloadInfos.get(appinfo.getId());
        if(downLoadInfo == null)
        {
            downLoadInfo= DownLoadInfo.clone(appinfo);
            downloadInfos.put(appinfo.getId(),downLoadInfo);
        }
        downLoadInfo.setState(DOWNLOAD_STATE_WAITING);

        DownLoadTask downLoadTask = new DownLoadTask(downLoadInfo);
        // 2  创建下载任务dTask(downLoadInfo);
        ThreadManager.getInstance().execute(downLoadTask);

        downloadTasks.put(appinfo.getId(),downLoadTask);
    }
    public void download(HomeBean.Appinfo appinfo){
        DownLoadInfo downLoadInfo = downloadInfos.get(appinfo.getId());
        if(downLoadInfo == null)
        {
            downLoadInfo= DownLoadInfo.clone(appinfo);
            downloadInfos.put(appinfo.getId(),downLoadInfo);
        }
        downLoadInfo.setState(DOWNLOAD_STATE_WAITING);

        DownLoadTask downLoadTask = new DownLoadTask(downLoadInfo);
        // 2  创建下载任务dTask(downLoadInfo);
        ThreadManager.getInstance().execute(downLoadTask);

        downloadTasks.put(appinfo.getId(),downLoadTask);
    }

    public void pause(AppDetailBean appinfo){
        DownLoadInfo downLoadInfo = downloadInfos.get(appinfo.getId());
        if(downLoadInfo.getState()==DOWNLOAD_STATE_DOWNLOADING || downLoadInfo.getState() ==DOWNLOAD_STATE_WAITING){
            downLoadInfo.setState(DOWNLOAD_STATE_PAUSE);
            notifyObserver(downLoadInfo);
            DownLoadTask downLoadTask = downloadTasks.get(downLoadInfo.getId());
            ThreadManager.getInstance().cancel(downLoadTask);
            downloadTasks.remove(downLoadInfo.getId());
        }
    }
    public void pause(HomeBean.Appinfo appinfo){
        DownLoadInfo downLoadInfo = downloadInfos.get(appinfo.getId());
        if(downLoadInfo.getState()==DOWNLOAD_STATE_DOWNLOADING || downLoadInfo.getState() ==DOWNLOAD_STATE_WAITING){
            downLoadInfo.setState(DOWNLOAD_STATE_PAUSE);
            notifyObserver(downLoadInfo);
            DownLoadTask downLoadTask = downloadTasks.get(downLoadInfo.getId());
            ThreadManager.getInstance().cancel(downLoadTask);
            downloadTasks.remove(downLoadInfo.getId());
        }
    }
    public void install(AppDetailBean appinfo){
        DownLoadInfo downLoadInfo = downloadInfos.get(appinfo.getId());
        downLoadInfo.getDownURL();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://"+downLoadInfo.getPath()),"application/vnd.android.package-archive");
        MyApplication.getContext().startActivity(intent);
    }
    public void install(HomeBean.Appinfo appinfo){
        DownLoadInfo downLoadInfo = downloadInfos.get(appinfo.getId());
        downLoadInfo.getDownURL();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://"+downLoadInfo.getPath()),"application/vnd.android.package-archive");
        MyApplication.getContext().startActivity(intent);
    }
    public DownLoadInfo getdownloadinfo(AppDetailBean appinfo){

        if(appinfo!=null){
            DownLoadInfo  downLoadInfo = downloadInfos.get(appinfo.getId());
            return downLoadInfo;
        }
        return null;

    }
    public DownLoadInfo getdownloadinfo(String appinfoid){

        if(appinfoid!=null){
            DownLoadInfo  downLoadInfo = downloadInfos.get(appinfoid);
            return downLoadInfo;
        }
        return null;

    }
    class DownLoadTask implements  Runnable{

        private DownLoadInfo downLoadInfo;

        public DownLoadTask(DownLoadInfo downLoadInfo) {

            this.downLoadInfo = downLoadInfo;
        }

        @Override
        public void run() {
            // 3 更新状态
            downLoadInfo.setState(DOWNLOAD_STATE_DOWNLOADING);
            notifyObserver(downLoadInfo);
            // 4 从网络获取数据  从头开始  断点续传
            File file = new File(downLoadInfo.getPath());
            if(!file.exists() || file.length() != downLoadInfo.getCurrentposition()){
                file.delete();
                downLoadInfo.setCurrentposition(0);
                String url = Urls.DOWNLOAD +"?name=" +downLoadInfo.getDownURL();
                httpResult =  HttpHelper.download(url);
            }else {
                //断点续传
                String url = Urls.DOWNLOAD +"?name=" +downLoadInfo.getDownURL()
                        +"&range="+downLoadInfo.getCurrentposition();
                httpResult = HttpHelper.download(url);
            }
          //写入文件 将文件保存到本地
            if(httpResult !=null && httpResult.getInputStream()!=null){
                InputStream inputStream= null ;
                FileOutputStream fileOutputStream= null;
                try {
                    inputStream = httpResult.getInputStream();
                     fileOutputStream = new FileOutputStream(file, true);
                    int len = 0;
                    byte[] buffer = new byte[1024];
                    while((len= inputStream.read(buffer))!=-1 && downLoadInfo.getState() ==DOWNLOAD_STATE_DOWNLOADING){
                        fileOutputStream.write(buffer,0,len);
                        fileOutputStream.flush();
                        downLoadInfo.setCurrentposition(len +downLoadInfo.getCurrentposition());
                        notifyObserver(downLoadInfo);
                    }
                } catch (Exception e) {
                    processError(file,downLoadInfo);
                }finally {
                    IOUtils.close(inputStream);
                    IOUtils.close(fileOutputStream);
                }
            }else{
                processError(file,downLoadInfo);
            }
            if(file.length() ==downLoadInfo.getSize()){
                downLoadInfo.setState(DOWNLOAD_STATE_SUCCESS);
                notifyObserver(downLoadInfo);
            }else if(downLoadInfo.getState() ==DOWNLOAD_STATE_PAUSE){
                notifyObserver(downLoadInfo);
            }else{
                processError(file,downLoadInfo);
            }

            downloadTasks.remove(downLoadInfo.getId());
        }
    }

    private void processError(File file, DownLoadInfo downLoadInfo) {
        file.delete();
        downLoadInfo.setCurrentposition(0);
        downLoadInfo.setState(DOWNLOAD_STATE_ERROR);
    }

    public interface  DownLoadObserver{
        public void onDownLoadinfoChange(DownLoadInfo info);
    }

    public void registObserver(DownLoadObserver observer){
        if(observer != null && !observers.contains(observer)){
            observers.add(observer);
        }
    }
    public void notifyObserver(final DownLoadInfo info){
        CommonUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                for (DownLoadObserver observer:observers
                        ) {
                    observer.onDownLoadinfoChange(info);
                }
            }
        });

    }
    public void unregistObserver(DownLoadObserver observer){
        if(observer != null&& observers.contains(observer)){
            observers.remove(observer);
        }
    }
}
