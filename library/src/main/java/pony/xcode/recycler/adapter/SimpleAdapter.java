package pony.xcode.recycler.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import java.util.List;

import pony.xcode.recycler.holder.SimpleViewHolder;

/**
 * Created by pony on 2020/12/23.
 * Copyright (c) 2020 holike. All rights reserved.
 */
public abstract class SimpleAdapter<T> extends BaseAdapter<T, SimpleViewHolder> {

    public SimpleAdapter(Context context, List<T> data) {
        super(context, data);
    }

    @LayoutRes
    protected abstract int getItemLayoutId();

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SimpleViewHolder(mLayoutInflater.inflate(getItemLayoutId(), parent, false));
    }

    @Override
    SimpleViewHolder getViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SimpleViewHolder(mLayoutInflater.inflate(getItemLayoutId(), parent, false));
    }

    @Override
    void innerBindViewHolder(@NonNull SimpleViewHolder holder, int position) {
        final T t = mData.get(position);
        convert(holder, t, position);
    }

    protected abstract void convert(SimpleViewHolder holder, T t, int position);
}
