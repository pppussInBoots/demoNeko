package com.neko.selectgithubuser.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;



public abstract class BaseCommonAdapter<T> extends BaseAdapter {
    protected List<T> mData = new ArrayList<>();
    protected LayoutInflater mInflater;
    protected Context mContext;

    public BaseCommonAdapter(Context context, List<T> data) {
        if (data != null) {
            mData.addAll(data);
        }
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void updateData(List<T> upData) {
        mData.clear();
        if (upData != null) {
            mData.addAll(upData);
        }
        notifyDataSetChanged();
    }

    public void append(T data) {
        if (data != null) {
            mData.add(data);
            notifyDataSetChanged();
        }
    }

    public void append(List<T> data) {
        if (data == null) {
            return;
        }
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        if (position >= 0 && position < getCount()) {
            return  mData.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    protected UniversalViewHolder setViewHolder(View convertView) {
        return new UniversalViewHolder(convertView);
    }

    public List<T> getAdapterData() {
        return mData;
    }


}
