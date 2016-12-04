package com.neko.selectgithubuser.mode;

/**
 * Created by Administrator on 2016/12/3 0003.
 */
public interface IAction extends IActionBase<String,String> {
    void fail(String msg);
}
