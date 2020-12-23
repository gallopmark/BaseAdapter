package pony.xcode.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pony.xcode.recycler.callback.OnItemClickListener;
import pony.xcode.recycler.callback.OnItemLongClickListener;
import pony.xcode.recycler.holder.BaseViewHolder;

/**
 * Created by pony on 2020/12/23.
 * Copyright (c) 2020 holike. All rights reserved.
 */
abstract class BaseAdapter<T, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {

    LayoutInflater mLayoutInflater;
    protected final Context mContext;
    protected final List<T> mData;
    private OnItemClickListener mItemClickListener;
    private OnItemLongClickListener mItemLongClickListener;

    public BaseAdapter(Context context, List<T> data) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mData = data;
    }

    public void set(int position, T t) {
        if (mData != null) {
            mData.set(position, t);
            notifyDataSetChanged();
        }
    }

    public void add(T t) {
        if (mData != null) {
            mData.add(t);
            notifyDataSetChanged();
        }
    }

    public void addAll(List<T> data) {
        addAll(data, true);
    }

    public void addAll(List<T> data, boolean removeAll) {
        if (mData != null) {
            if (removeAll) {
                this.mData.clear();
            }
            this.mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        if (mData != null) {
            mData.clear();
            notifyDataSetChanged();
        }
    }

    public void setItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public void setItemLongClickListener(OnItemLongClickListener listener) {
        this.mItemLongClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getViewHolder(parent, viewType);
    }

    abstract VH getViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        innerBindViewHolder(holder, position);
        final int curPos = position;
        holder.itemView.setOnClickListener(v -> {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(curPos, v);
            }
        });
        holder.itemView.setOnLongClickListener(v -> {
            if (mItemLongClickListener != null) {
                mItemLongClickListener.onItemLongClick(curPos, v);
                return true;
            }
            return false;
        });
    }

    abstract void innerBindViewHolder(@NonNull VH holder, int position);
}
