package com.neko.selectgithubuser.bean;

import com.google.gson.annotations.SerializedName;
import com.neko.selectgithubuser.constants.ProtocolKey;

import java.util.List;

/**
 * Created by Administrator on 2016/12/3 0003.
 */
public class GithubResult {
    @SerializedName(ProtocolKey.Count)
    private int mUsersCount;
    @SerializedName(ProtocolKey.Items)
    private List<GithubUserInfo> mUsers;

    public List<GithubUserInfo> getUsers() {
        return mUsers;
    }

    public void setUsers(List<GithubUserInfo> users) {
        mUsers = users;
    }

    public int getUsersCount() {
        return mUsersCount;
    }

    public void setUsersCount(int usersCount) {
        mUsersCount = usersCount;
    }
}
