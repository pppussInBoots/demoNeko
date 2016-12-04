package com.neko.selectgithubuser.ui.adapter;

import android.content.Context;
import android.view.View;


import com.neko.selectgithubuser.ui.adapter.adapterInterface.ClickCallback;
import com.neko.selectgithubuser.ui.adapter.adapterInterface.ClickLongCallback;

import java.util.List;


public abstract class ClickableAdapter<T> extends CommonAdapter<T> implements ClickCallback, ClickLongCallback {


    public ClickableAdapter(Context context, List<T> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    protected UniversalViewHolder setViewHolder(View convertView) {
        UniversalViewHolder holder = new UniversalViewHolder(convertView, this, this);
        return holder;
    }

    @Override
    protected void setEvent(UniversalViewHolder holder, View convertView) {
        setListener(holder,convertView);
    }

    public abstract void setListener(UniversalViewHolder holder,View convertView);
}
