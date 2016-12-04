package com.neko.selectgithubuser.ui.adapter;

import android.content.Context;
import android.view.View;


import com.neko.selectgithubuser.ui.adapter.adapterInterface.ClickCallback;

import java.util.ArrayList;
import java.util.List;



public abstract class SelectableAdapter<T> extends CommonAdapter<T> implements ClickCallback {
    private List<T> mSelectedList = new ArrayList<>();
    private ESelectMode mSelectMode;

    public SelectableAdapter(Context context, List data, int layoutId, ESelectMode selectMode) {
        super(context, data, layoutId);
        mSelectMode = selectMode;
    }

    @Override
    protected UniversalViewHolder setViewHolder(View convertView) {
        return new UniversalViewHolder(convertView, this);
    }

    @Override
    protected void setEvent(UniversalViewHolder holder, View convertView) {
        setListener(holder, convertView);
    }

    protected abstract void setListener(UniversalViewHolder holder, View convertView);


    public void doSelect(T item) {
        switch (mSelectMode) {
            case SINGLE:
                mSelectedList.clear();
                mSelectedList.add(item);
                break;
            case MULTI:
                if (!mSelectedList.contains(item)) {
                    mSelectedList.add(item);
                } else {
                    mSelectedList.remove(item);
                }
                break;
        }
        notifyDataSetChanged();  //最后更新数据显示
    }

    public List<T> getSelectList() {
        return mSelectedList;
    }

    public T getSelect() {
        if (!mSelectedList.isEmpty()) {
            return mSelectedList.get(0);
        }
        return null;
    }

    public boolean isSelected(T item) {
        return mSelectedList.contains(item);
    }

    public boolean isSelected(int position) {
        return mSelectedList.contains(getItem(position));
    }

    public enum ESelectMode {
        /**
         * 单选
         */
        SINGLE,

        /**
         * 多选
         */
        MULTI
    }
}
