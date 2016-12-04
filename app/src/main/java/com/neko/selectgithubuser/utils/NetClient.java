package com.neko.selectgithubuser.utils;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2016/12/3 0003.
 * OkHttp的单例
 */
public enum NetClient {
    instance,;
    public OkHttpClient mHttpClient;

    NetClient() {
        mHttpClient = new OkHttpClient();
    }

}
