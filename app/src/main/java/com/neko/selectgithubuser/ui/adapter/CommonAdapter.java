package com.neko.selectgithubuser.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;



public abstract class CommonAdapter<T> extends BaseCommonAdapter<T> {
    private int mLayoutId;

    public CommonAdapter(Context context, List<T> data) {
        super(context, data);
    }


    public CommonAdapter(Context context, List<T> data, int layoutId) {
        super(context, data);
        mLayoutId = layoutId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UniversalViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(mLayoutId, parent, false);
            holder = setViewHolder(convertView);
            setEvent(holder, convertView);
        } else {
            holder = (UniversalViewHolder) convertView.getTag();
        }
        holder.setPosition(position);
        setViewData(getItem(position), holder);
        return convertView;
    }

    protected void setEvent(UniversalViewHolder holder, View convertView) {

    }

    protected abstract void setViewData(T item, UniversalViewHolder holder);
}
