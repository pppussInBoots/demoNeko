package com.neko.selectgithubuser.ui;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.neko.selectgithubuser.utils.WorkFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    private PresenterSelectUser mPresenterSelectUser;
    private EditText mETToSelectName;
    private ListView mListView;
    private Button mDoSelect;
    private CommonAdapter<GithubUserInfo> mAdapter;
    private String no1 = "";
    private ProgressDialog mDialog;

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
            public void onStart(String s) {
                if (mDialog == null) {
                    mDialog = new ProgressDialog(getApplication());
                }
                mDialog.setMessage(s);
                mDialog.show();
            }

            @Override
            public void onFail(String msg) {
                Toast.makeText(getApplication(), msg, Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
            }

            @Override
            public void onError(String s) {
                Toast.makeText(getApplication(), s, Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
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
                        statistics();
                        mDialog.dismiss();
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
                holder.setViewText(R.id.user_name_tx_v, item.getName());
                if (item.getFavorLanguage() != null) {
                    holder.setViewText(R.id.user_favorLanguage_tx_v, String.format("%s%s", "偏爱语言：", item.getFavorLanguage()));
                } else {
                    holder.setViewText(R.id.user_favorLanguage_tx_v, "该用户无偏爱语言");
                }
                String avatarUrl = item.getAvatarUrl();
                Glide.with(getApplication()).load(avatarUrl).diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .placeholder(R.mipmap.ic_launcher).into(imageView);
            }
        };
        mListView.setAdapter(mAdapter);
    }

    private void statistics() {
        final Map<String, Integer> map = new HashMap<>();
        WorkFactory.INSTANCE.mService.submit(new Runnable() {
            @Override
            public void run() {
                List<GithubUserInfo> list = mAdapter.getAdapterData();
                for (GithubUserInfo info : list
                        ) {
                    Integer i = map.get(info.getFavorLanguage());
                    map.put(info.getFavorLanguage(), (i == null) ? 1 : i + 1);
                }
                int next, before = 0;

                for (String key : map.keySet()
                        ) {
                    next = map.get(key);
                    if (next > before) {
                        no1 = key;
                        before = next;
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplication(), "这些用户中出现频率最高的语言是：" + no1, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
