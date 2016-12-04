package com.neko.selectgithubuser.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.neko.selectgithubuser.bean.GithubRepos;
import com.neko.selectgithubuser.bean.GithubResult;



/**
 * Created by Administrator on 2016/12/3 0003.
 * 使用Gson解析JSON数据的工具类
 */
public class JsonUtils {
    private static Gson mGson = new Gson();

    public static GithubResult parseResult(String jsonString) {
        return mGson.fromJson(jsonString, GithubResult.class);
    }

    public static GithubRepos parseArray(String string) {
        String json=String.format("%s%s%s", "{\"data\":", string, "}");
        Log.i("调试", json);
        return mGson.fromJson(json, GithubRepos.class);
    }
}
