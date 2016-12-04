package com.neko.selectgithubuser.utils;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/12/3 0003.
 * 线程池工具  单例
 */
public enum  WorkFactory {
    INSTANCE;
    public  ExecutorService mService;
    WorkFactory(){
        mService = Executors.newCachedThreadPool();
    }
}
