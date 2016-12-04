package com.neko.selectgithubuser.mode;

import android.os.Handler;
import android.os.Looper;

import com.neko.selectgithubuser.bean.GithubRepos;
import com.neko.selectgithubuser.bean.GithubResult;
import com.neko.selectgithubuser.bean.GithubUserInfo;
import com.neko.selectgithubuser.constants.UrlConstants;
import com.neko.selectgithubuser.utils.JsonUtils;
import com.neko.selectgithubuser.utils.NetClient;
import com.neko.selectgithubuser.utils.WorkFactory;


import java.io.IOException;
import java.util.ArrayList;
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

    private String mFavorLanguage = "";

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
                        final List<GithubUserInfo> list = new ArrayList<>();
                        List<GithubUserInfo> infos = user.getUsers();
                        int y = user.getUsersCount();
                        if (y>6){
                            y = 6; //普通用户每小时只能访问60,因此限制获取偏爱语言的次数
                        }
                        for (int i = 0,size=y; i < size; i++) {
                            getFavor(infos.get(i).getReposUrl());
                            GithubUserInfo io = new GithubUserInfo();
                            io.setAvatarUrl(infos.get(i).getAvatarUrl());
                            io.setName(infos.get(i).getName());
                            io.setFavorLanguage(mFavorLanguage);
                            io.setReposUrl(infos.get(i).getReposUrl());
                            list.add(io);
                        }
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                success.success(list);
                            }
                        });
                    }
                });
            }
        });
    }


    public void getFavor(final String url) {
        final Map<String, Integer> languageMap = new HashMap<>();
        Request request = new Request.Builder().url(url).build();
        Call call = NetClient.instance.mHttpClient.newCall(request);
        try {
            Response response = call.execute();
            GithubRepos repos = JsonUtils.parseArray(response.body().string());
            int next, before = 0;
            for (GithubRepos.GithubUserRepos r : repos.getGithubUserReposes()
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
