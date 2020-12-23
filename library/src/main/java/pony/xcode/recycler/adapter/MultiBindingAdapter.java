package pony.xcode.recycler.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import java.util.List;

import pony.xcode.recycler.holder.BindingViewHolder;
import pony.xcode.recycler.holder.MultiBindingViewHolder;

/**
 * Created by pony on 2020/12/23.
 * Copyright (c) 2020 holike. All rights reserved.
 */
public abstract class MultiBindingAdapter<T> extends SimpleBindingAdapter<T, ViewDataBinding> {

    public MultiBindingAdapter(Context context, List<T> data) {
        super(context, data);
    }

    @Override
    public int getItemViewType(int position) {
        return getItemTypeByPosition(position);
    }

    public abstract int getItemTypeByPosition(int position);

    @Override
    BindingViewHolder<ViewDataBinding> getViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MultiBindingViewHolder(DataBindingUtil.inflate(mLayoutInflater, getItemLayoutId(viewType), parent, false));
    }
}
