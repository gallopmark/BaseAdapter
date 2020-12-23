package pony.xcode.recycler.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by pony on 2020/12/23.
 * Copyright (c) 2020 holike. All rights reserved.
 */
public class XRecyclerView extends RecyclerView {
    // 包裹了一层的头部底部Adapter
    private WrapRecyclerAdapter mWrapRecyclerAdapter;
    // 这个是列表数据的Adapter
    private Adapter<?> mAdapter;
    private final SparseArray<View> mHeaderViews = new SparseArray<>();
    private final SparseArray<View> mFooterViews = new SparseArray<>();

    // 基本的头部类型开始位置  用于viewType
    private static int BASE_ITEM_TYPE_HEADER = 10000000;
    // 基本的底部类型开始位置  用于viewType
    private static int BASE_ITEM_TYPE_FOOTER = 20000000;

    public XRecyclerView(@NonNull Context context) {
        super(context);
    }

    public XRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 添加头部
    public void addHeaderView(View view) {
        int position = mHeaderViews.indexOfValue(view);
        if (position < 0) {
            mHeaderViews.put(BASE_ITEM_TYPE_HEADER++, view);
        }
        if (mWrapRecyclerAdapter != null) {
            mWrapRecyclerAdapter.notifyDataSetChanged();
        }
    }

    // 添加底部
    public void addFooterView(View view) {
        int position = mFooterViews.indexOfValue(view);
        if (position < 0) {
            mFooterViews.put(BASE_ITEM_TYPE_FOOTER++, view);
        }
        if (mWrapRecyclerAdapter != null) {
            mWrapRecyclerAdapter.notifyDataSetChanged();
        }
    }

    // 移除头部
    public void removeHeaderView(View view) {
        int index = mHeaderViews.indexOfValue(view);
        if (index < 0) return;
        mHeaderViews.removeAt(index);
        if (mWrapRecyclerAdapter != null) {
            mWrapRecyclerAdapter.notifyDataSetChanged();
        }
    }

    // 移除底部
    public void removeFooterView(View view) {
        int index = mFooterViews.indexOfValue(view);
        if (index < 0) return;
        mFooterViews.removeAt(index);
        if (mWrapRecyclerAdapter != null) {
            mWrapRecyclerAdapter.notifyDataSetChanged();
        }
    }

    private final AdapterDataObserver mDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            if (mAdapter == null) return;
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyDataSetChanged没效果
            if (mWrapRecyclerAdapter != mAdapter)
                mWrapRecyclerAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            if (mAdapter == null) return;
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyDataSetChanged没效果
            if (mWrapRecyclerAdapter != mAdapter)
                mWrapRecyclerAdapter.notifyItemRemoved(positionStart);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            if (mAdapter == null) return;
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyItemMoved没效果
            if (mWrapRecyclerAdapter != mAdapter)
                mWrapRecyclerAdapter.notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            if (mAdapter == null) return;
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyItemChanged没效果
            if (mWrapRecyclerAdapter != mAdapter)
                mWrapRecyclerAdapter.notifyItemChanged(positionStart);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            if (mAdapter == null) return;
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyItemChanged没效果
            if (mWrapRecyclerAdapter != mAdapter)
                mWrapRecyclerAdapter.notifyItemChanged(positionStart, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            if (mAdapter == null) return;
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyItemInserted没效果
            if (mWrapRecyclerAdapter != mAdapter)
                mWrapRecyclerAdapter.notifyItemInserted(positionStart);
        }
    };

    @SuppressWarnings("rawtypes")
    private class WrapRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        /**
         * 数据列表的Adapter
         */
        private final Adapter mAdapter;

        WrapRecyclerAdapter(Adapter adapter) {
            this.mAdapter = adapter;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // viewType 可能就是 SparseArray 的key
            if (isHeaderViewType(viewType)) {
                View headerView = mHeaderViews.get(viewType);
                return createHeaderFooterViewHolder(headerView);
            }
            if (isFooterViewType(viewType)) {
                View footerView = mFooterViews.get(viewType);
                return createHeaderFooterViewHolder(footerView);
            }
            return mAdapter.onCreateViewHolder(parent, viewType);
        }

        /**
         * 是不是底部类型
         */
        private boolean isFooterViewType(int viewType) {
            int position = mFooterViews.indexOfKey(viewType);
            return position >= 0;
        }

        /**
         * 创建头部或者底部的ViewHolder
         */
        private RecyclerView.ViewHolder createHeaderFooterViewHolder(View view) {
            return new RecyclerView.ViewHolder(view) {
            };
        }

        /**
         * 是不是头部类型
         */
        private boolean isHeaderViewType(int viewType) {
            int position = mHeaderViews.indexOfKey(viewType);
            return position >= 0;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (isHeaderPosition(position) || isFooterPosition(position)) {
                return;
            }
            // 计算一下位置
            final int adapterPosition = position - mHeaderViews.size();
            //noinspection unchecked
            mAdapter.onBindViewHolder(holder, adapterPosition);
        }

        @Override
        public int getItemViewType(int position) {
            if (isHeaderPosition(position)) {
                // 直接返回position位置的key
                return mHeaderViews.keyAt(position);
            }
            if (isFooterPosition(position)) {
                // 直接返回position位置的key
                position = position - mHeaderViews.size() - mAdapter.getItemCount();
                return mFooterViews.keyAt(position);
            }
            // 返回列表Adapter的getItemViewType
            position = position - mHeaderViews.size();
            return mAdapter.getItemViewType(position);
        }

        /**
         * 是不是底部位置
         */
        private boolean isFooterPosition(int position) {
            return position >= (mHeaderViews.size() + mAdapter.getItemCount());
        }

        /**
         * 是不是头部位置
         */
        private boolean isHeaderPosition(int position) {
            return position < mHeaderViews.size();
        }

        @Override
        public int getItemCount() {
            // 条数三者相加 = 底部条数 + 头部条数 + Adapter的条数
            return mAdapter.getItemCount() + mHeaderViews.size() + mFooterViews.size();
        }

    }


    @Override
    public void setAdapter(Adapter adapter) {
        // 为了防止多次设置Adapter
        if (mAdapter != null) {
            mAdapter.unregisterAdapterDataObserver(mDataObserver);
            mAdapter = null;
        }
        this.mAdapter = adapter;
        if (adapter instanceof WrapRecyclerAdapter) {
            mWrapRecyclerAdapter = (WrapRecyclerAdapter) adapter;
        } else {
            mWrapRecyclerAdapter = new WrapRecyclerAdapter(adapter);
        }
        super.setAdapter(mWrapRecyclerAdapter);
        // 注册一个观察者
        mAdapter.registerAdapterDataObserver(mDataObserver);
        // 解决GridLayout添加头部和底部也要占据一行
        adjustSpanSize();
    }

    /**
     * 解决GridLayoutManager添加头部和底部不占用一行的问题
     */
    private void adjustSpanSize() {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    boolean isHeaderOrFooter = mWrapRecyclerAdapter.isHeaderPosition(position)
                            || mWrapRecyclerAdapter.isFooterPosition(position);
                    return isHeaderOrFooter ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }
    }
}
