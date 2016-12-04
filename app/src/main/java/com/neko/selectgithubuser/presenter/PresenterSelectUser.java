package com.neko.selectgithubuser.presenter;

import com.neko.selectgithubuser.bean.GithubUserInfo;
import com.neko.selectgithubuser.mode.IAction;
import com.neko.selectgithubuser.mode.IActionSuccess;
import com.neko.selectgithubuser.mode.ModeSelectUser;

import java.util.List;


/**
 * Created by Administrator on 2016/12/3 0003.
 * 处理查找用户的Presenter层
 */
public class PresenterSelectUser {
    private ModeSelectUser mModeSelectUser;
    private IView mIView;

    public PresenterSelectUser(IView iView) {
        mIView = iView;
        mModeSelectUser = new ModeSelectUser(new IAction() {

            @Override
            public void start(String s) {
                mIView.onStart(s);
            }

            @Override
            public void fail(String msg) {
                mIView.onFail(msg);
            }

            @Override
            public void error(String s) {
                mIView.onError(s);
            }
        });
    }


    public void doSelect(String userName, final IViewSuccess<List<GithubUserInfo>> viewSuccess) {
        mModeSelectUser.doSelect(userName, new IActionSuccess<List<GithubUserInfo>>() {
            @Override
            public void success(List<GithubUserInfo> githubUsers) {
                viewSuccess.onSuccess(githubUsers);
            }
        });
    }

    public void getFavorLanguage(String url, final IViewSuccess<String> viewSuccess) {
        mModeSelectUser.getFavor(url, new IActionSuccess<String>() {
            @Override
            public void success(String s) {
                viewSuccess.onSuccess(s);
            }
        });
    }
}
