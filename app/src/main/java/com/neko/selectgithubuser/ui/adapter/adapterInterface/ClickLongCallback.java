package com.neko.selectgithubuser.ui.adapter.adapterInterface;

import android.view.View;

import com.neko.selectgithubuser.ui.adapter.UniversalViewHolder;



public interface ClickLongCallback {
    boolean onLongClick(View beLongClickView, int position, UniversalViewHolder holder);
}
