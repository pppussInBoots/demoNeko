package com.neko.selectgithubuser.bean;

import com.google.gson.annotations.SerializedName;
import com.neko.selectgithubuser.constants.ProtocolKey;

/**
 * Created by Administrator on 2016/12/3 0003.
 * 用户类
 */
public class GithubUserInfo {
    @SerializedName(ProtocolKey.UserName)
    private String mName;
    @SerializedName(ProtocolKey.AvatarUrl)
    private String mAvatarUrl;
    @SerializedName(ProtocolKey.Repos)
    private String mReposUrl;

    private String mFavorLanguage;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        mAvatarUrl = avatarUrl;
    }

    public String getReposUrl() {
        return mReposUrl;
    }

    public void setReposUrl(String reposUrl) {
        mReposUrl = reposUrl;
    }

    public String getFavorLanguage() {
        return mFavorLanguage;
    }

    public void setFavorLanguage(String favorLanguage) {
        mFavorLanguage = favorLanguage;
    }
}
