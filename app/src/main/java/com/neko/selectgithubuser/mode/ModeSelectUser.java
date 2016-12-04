package com.neko.selectgithubuser.mode;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.reflect.TypeToken;
import com.neko.selectgithubuser.bean.GithubRepos;
import com.neko.selectgithubuser.bean.GithubResult;
import com.neko.selectgithubuser.bean.GithubUserInfo;
import com.neko.selectgithubuser.constants.UrlConstants;
import com.neko.selectgithubuser.utils.JsonUtils;
import com.neko.selectgithubuser.utils.NetClient;
import com.neko.selectgithubuser.utils.WorkFactory;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/12/3 0003.
 * 查找Github用户的mode层
 */
public class ModeSelectUser {
    private Handler mHandler = new Handler(Looper.getMainLooper());


    private IAction mIAction;

    public ModeSelectUser(IAction action) {
        mIAction = action;
    }

    public void doSelect(final String userName, final IActionSuccess<List<GithubUserInfo>> success) {
        mIAction.start("正在查找该用户的信息");
        WorkFactory.INSTANCE.mService.submit(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(String.format("%s%s", UrlConstants.BaseUrl, userName))
                        .build();
                Call call = NetClient.instance.mHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mIAction.fail(e.getMessage());
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //Log.i("调试", response.body().string());
                        final GithubResult user = JsonUtils.parseResult(response.body().string());
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                success.success(user.getUsers());
                            }
                        });
                    }
                });
            }
        });
    }

    private String mFavorLanguage = "";

    public void getFavor(final String url, final IActionSuccess<String> success) {
        final Map<String, Integer> languageMap = new HashMap<>();
        WorkFactory.INSTANCE.mService.submit(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder().url(url).build();
                Call call = NetClient.instance.mHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        List<GithubRepos.GithubUserRepos> repos=JsonUtils.parseArray(response.body().string(),new TypeToken<List<GithubRepos.GithubUserRepos>>(){});
                        int next, before = 0;
                        for (GithubRepos.GithubUserRepos r : repos
                                ) {
                            Integer i = languageMap.get(r.getLanguage());
                            languageMap.put(r.getLanguage(), (i == null) ? 1 : i + 1);
                        }
                        for (String key : languageMap.keySet()
                                ) {
                            next = languageMap.get(key);
                            if (next > before) {
                                mFavorLanguage = key;
                                before = next;
                            }
                        }
                    }
                });
            }
        });
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                success.success(mFavorLanguage);
            }
        });
    }
}
