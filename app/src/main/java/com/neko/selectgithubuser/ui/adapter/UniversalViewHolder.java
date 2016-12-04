package com.neko.selectgithubuser.ui.adapter;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.neko.selectgithubuser.ui.adapter.adapterInterface.CheckedChangedCallback;
import com.neko.selectgithubuser.ui.adapter.adapterInterface.ClickCallback;
import com.neko.selectgithubuser.ui.adapter.adapterInterface.ClickLongCallback;


public class UniversalViewHolder implements View.OnClickListener, View.OnLongClickListener, CompoundButton.OnCheckedChangeListener {
    private final String mTag = "UniversalViewHolder";
    protected SparseArray<View> mViews;
    protected View mConvertView;
    protected int mPosition;
    protected ClickCallback mClickCallback;
    protected ClickLongCallback mClickLongCallback;
    protected CheckedChangedCallback mCheckedChangedCallback;

    /*    public void setClickCallback(ClickCallback clickCallback) {
            mClickCallback = clickCallback;
        }

        public void setClickLongCallback(ClickLongCallback clickLongCallback) {
            mClickLongCallback = clickLongCallback;
        }

        public void setCheckedChangedCallback(CheckedChangedCallback checkedChangedCallback) {
            mCheckedChangedCallback = checkedChangedCallback;
        }*/
    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    //构造方法
    public UniversalViewHolder(View convertView) {
        this(convertView, null, null, null);
    }

    public UniversalViewHolder(View convertView, ClickCallback clickCallback) {
        this(convertView, clickCallback, null, null);
    }

    public UniversalViewHolder(View convertView, ClickCallback clickCallback, ClickLongCallback clickLongCallback) {
        this(convertView, clickCallback, clickLongCallback, null);
    }

    public UniversalViewHolder(View convertView, ClickCallback clickCallback, ClickLongCallback clickLongCallback, CheckedChangedCallback checkedChangedCallback) {
        mClickCallback = clickCallback;
        mClickLongCallback = clickLongCallback;
        mCheckedChangedCallback = checkedChangedCallback;
        mConvertView = convertView;
        mViews = new SparseArray<>();
        if (mConvertView != null) {
            mConvertView.setTag(this);
            convertView.setTag(this);
        }
    }


    public <T extends View> T findView(int viewId) {
        View view = null;
        if (mConvertView != null) {
            view = mConvertView.findViewById(viewId);
        }
        if (view != null) {
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view != null) {
            return (T) view;
        }
        view = findView(viewId);
        return (T) view;
    }


    public void setOnClickListener(int viewID) {
        View view = getView(viewID);
        if (view != null) {
            setClickListener(ClickType.click, view);
        }

    }

    public void setOnClickListener(View... views) {
        setClickListener(ClickType.click, views);
    }

    public void setLongClickListener(int viewID) {
        View view = getView(viewID);
        if (view != null) {
            setClickListener(ClickType.longClick, view);
        }
    }

    public void setLongClickListener(View... views) {
        setClickListener(ClickType.longClick, views);
    }

    public void setBothClickListener(View... views) {
        setClickListener(ClickType.both, views);
    }


    private void setCheckedChangeListener(Checkable... checkableList) {
        if (isViewIllegal(checkableList)) {
            for (Checkable checkable : checkableList
                    ) {
                setCheckedChangeListener(checkable);
            }
        }
    }

    private void setCheckedChangeListener(Checkable checkable) {
        if (isViewIllegal(checkable)) {
            return;
        }
        if (checkable instanceof CompoundButton) {
            ((CompoundButton) checkable).setOnCheckedChangeListener(this);
        } else {
            Log.e(mTag, "setCheckedChangeListener : Currently only support CompoundButton !!!");
        }
    }


    private void setClickListener(ClickType type, View view) {
        if (isViewIllegal(view)) {
            return;
        }
        switch (type) {
            case click:
                view.setOnClickListener(this);
                break;
            case longClick:
                view.setOnLongClickListener(this);
                break;
            case both:
                view.setOnClickListener(this);
                view.setOnLongClickListener(this);
                break;
            case none:
                Log.d(mTag, "ClickType is none!");
                break;
        }
    }

    private void setClickListener(ClickType type, View... views) {
        if (isViewIllegal(views)) {
            Log.e(mTag, "Error, null Pointer!!");
            return;
        }
        for (View v : views
                ) {
            setClickListener(type, v);
        }
    }

    @Override
    public void onClick(View v) {
        if (mClickCallback != null) {
            mClickCallback.onClickCallBack(v, mPosition, this);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mClickLongCallback != null) {
            mClickLongCallback.onLongClick(v, mPosition, this);
        }
        return false;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (mCheckedChangedCallback != null) {
            mCheckedChangedCallback.onCheckedChanged(buttonView, isChecked, mPosition);
        }
    }

    public UniversalViewHolder setViewText(int viewId, CharSequence text) {
        TextView view = getView(viewId);
        if (view != null) {
            view.setText(text);
        }
        return this;
    }

    public UniversalViewHolder setViewPic(int viewId, int picID) {
        ImageView view = getView(viewId);
        if (view != null) {
            view.setImageResource(picID);
        }
        return this;
    }

    public UniversalViewHolder setVisible(boolean visible, int viewID) {
        View view = getView(viewID);
        if (view != null) {
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
        return this;
    }


    public UniversalViewHolder setImageBitmap(Bitmap bitmap, int viewID) {
        if (bitmap == null) {
            Log.e(mTag, "Error,bitmap is null");
        }
        View view = getView(viewID);
        if (view != null && view instanceof ImageView) {
            ((ImageView) view).setImageBitmap(bitmap);
        }
        return this;
    }

    public enum ClickType {
        /**
         * 短按
         */
        click,
        /**
         * 长按
         */
        longClick,
        /**
         * 短按和长按都有
         */
        both,
        /**
         * 没有点击事件
         */
        none
    }

    //判断方法============================================

    private boolean isViewIllegal(View... views) {
        boolean result;
        if (views == null || views.length == 0) {
            Log.e(mTag, "Error : views... is Empty !!!");
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    private boolean isViewIllegal(Checkable... views) {
        boolean result;
        if (views == null || views.length == 0) {
            Log.e(mTag, "Error : Checkable... is Empty !!!");
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    protected boolean isViewIllegal(int... viewIds) {
        boolean result;
        if (viewIds.length == 0) {
            Log.e(mTag, "Error : viewIds... is Empty !!!");
            result = true;
        } else {
            result = false;
        }
        return result;
    }
}
