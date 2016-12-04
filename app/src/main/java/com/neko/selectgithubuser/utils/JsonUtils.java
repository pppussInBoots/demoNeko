package com.neko.selectgithubuser.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neko.selectgithubuser.bean.GithubResult;
import java.util.List;


/**
 * Created by Administrator on 2016/12/3 0003.
 * 使用Gson解析JSON数据的工具类
 */
public class JsonUtils {
    private static Gson mGson = new Gson();

    public static GithubResult parseResult(String jsonString) {
        return mGson.fromJson(jsonString, GithubResult.class);
    }

    public static <T> List<T> parseArray(String string, TypeToken<List<T>> type) {
        String json = String.format("%s%s%s", "{ \"data\":", string, "}");
        Log.i("调试", json);
        return mGson.fromJson(json, type.getType());
    }
}
