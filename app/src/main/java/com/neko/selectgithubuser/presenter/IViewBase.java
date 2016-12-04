package com.neko.selectgithubuser.presenter;

/**
 * Created by Administrator on 2016/12/3 0003.
 */
public interface IViewBase<Start, Error> {
    void onStart(Start start);

    void onError(Error error);
}
