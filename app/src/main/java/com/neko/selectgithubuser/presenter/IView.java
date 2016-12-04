package com.neko.selectgithubuser.presenter;

/**
 * Created by Administrator on 2016/12/3 0003.
 */
public interface IView extends IViewBase<String, String> {
    void onFail(String msg);
}
