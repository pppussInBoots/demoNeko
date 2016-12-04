package com.neko.selectgithubuser.mode;

/**
 * Created by Administrator on 2016/12/3 0003.
 */
public interface IActionBase<Start,Error> {
    void start(Start start);

    void error(Error error);
}
