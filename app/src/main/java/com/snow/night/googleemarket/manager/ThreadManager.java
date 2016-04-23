package com.snow.night.googleemarket.manager;

import android.app.ActivityManager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/4/21.
 */
public class ThreadManager {
    //懒汉式 单例模式
    private  static ThreadManager threadManager;
    private final ThreadPoolExecutor executor;
    private static int corePoolSize = Runtime.getRuntime().availableProcessors()*2+1;
    private  static int maximumPoolSize = corePoolSize;
   //int corePoolSize 核心线程数 一般为 cpu核心数*2+1；
    //私有化构造函数,创建静态的实例对象，提供一个静态的获取实例对象方法 双重判空
    private  ThreadManager(int corePoolSize,int maximumPoolSize){
        //通过构造函数创建线程池的类
        executor = new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque(5),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    };
    public static ThreadManager getInstance(){
        //双重判空
        if(threadManager ==null){
            synchronized (ThreadManager.class){
                if(threadManager == null){
                    threadManager = new ThreadManager(corePoolSize,maximumPoolSize);
                }
            }
        }
        return  threadManager;
    };

    /**
     * 执行任务的方法 调用线程池中执行任务的方法
     * @param task
     */
    public  void execute (Runnable task){
        if(task == null){
            return;
        }
        if(executor != null && !executor.isShutdown()){
                executor.execute(task);
        }
    }
    public void cancel(Runnable task){
        if(task == null){
            return;
        }
        if(executor != null && !executor.isShutdown()){
            executor.getQueue().remove(task);
        }
    }
}
