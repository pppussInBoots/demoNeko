package com.neko.selectgithubuser.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.neko.selectgithubuser.R;
import com.neko.selectgithubuser.bean.GithubUserInfo;
import com.neko.selectgithubuser.presenter.IView;
import com.neko.selectgithubuser.presenter.IViewSuccess;
import com.neko.selectgithubuser.presenter.PresenterSelectUser;
import com.neko.selectgithubuser.ui.adapter.CommonAdapter;
import com.neko.selectgithubuser.ui.adapter.UniversalViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private PresenterSelectUser mPresenterSelectUser;
    private EditText mETToSelectName;
    private ListView mListView;
    private Button mDoSelect;
    private CommonAdapter<GithubUserInfo> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
        setData();
    }


    private void initView() {
        mETToSelectName = (EditText) findViewById(R.id.main_et_v);
        mListView = (ListView) findViewById(R.id.main_lv);
        mDoSelect = (Button) findViewById(R.id.doSelect);
        mPresenterSelectUser = new PresenterSelectUser(new IView() {
            @Override
            public void onFail(String msg) {

            }

            @Override
            public void onStart(String s) {

            }

            @Override
            public void onError(String s) {

            }
        });
    }


    private void setListener() {
        mDoSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenterSelectUser.doSelect(mETToSelectName.getText().toString(), new IViewSuccess<List<GithubUserInfo>>() {
                    @Override
                    public void onSuccess(List<GithubUserInfo> githubUser) {
                        mAdapter.updateData(githubUser);
                    }
                });
            }
        });
    }

    private void setData() {
        mAdapter = new CommonAdapter<GithubUserInfo>(getApplication(), null, R.layout.list_item) {
            @Override
            protected void setViewData(GithubUserInfo item, final UniversalViewHolder holder) {
                ImageView imageView = holder.getView(R.id.user_avatar_im_v);
                mPresenterSelectUser.getFavorLanguage(item.getReposUrl(), new IViewSuccess<String>() {
                    @Override
                    public void onSuccess(String s) {
                        holder.setViewText(R.id.user_favorLanguage_tx_v, s);
                        mAdapter.notifyDataSetChanged();
                    }
                });
                holder.setViewText(R.id.user_name_tx_v, item.getName());
                String avatarUrl = item.getAvatarUrl();
                Glide.with(getApplication()).load(avatarUrl).diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .placeholder(R.mipmap.ic_launcher).into(imageView);
            }
        };
        mListView.setAdapter(mAdapter);
    }
}
