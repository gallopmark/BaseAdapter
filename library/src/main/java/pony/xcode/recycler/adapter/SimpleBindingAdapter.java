package pony.xcode.recycler.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import java.util.List;

import pony.xcode.recycler.holder.BindingViewHolder;

/**
 * Created by pony on 2020/12/22.
 * Copyright (c) 2020 holike. All rights reserved.
 */
public abstract class SimpleBindingAdapter<T, DB extends ViewDataBinding> extends BaseAdapter<T, BindingViewHolder<DB>> {

    public SimpleBindingAdapter(Context context, List<T> data) {
        super(context, data);
    }

    @LayoutRes
    protected abstract int getItemLayoutId(int viewType);

    @Override
    BindingViewHolder<DB> getViewHolder(@NonNull ViewGroup parent, int viewType) {
        DB binding = DataBindingUtil.inflate(mLayoutInflater, getItemLayoutId(viewType), parent, false);
        return new BindingViewHolder<>(binding);
    }

    @Override
    void innerBindViewHolder(@NonNull BindingViewHolder<DB> holder, int position) {
        final T t = mData.get(position);
        convert(holder, holder.binding, t, position);
        holder.binding.executePendingBindings();
    }

    protected abstract void convert(BindingViewHolder<DB> holder, DB binding, T t, int position);
}
