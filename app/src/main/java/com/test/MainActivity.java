package com.test;

import android.content.Context;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.test.base.adapter.R;
import com.test.base.adapter.databinding.ItemBindingBinding;

import java.util.ArrayList;
import java.util.List;

import pony.xcode.recycler.adapter.SimpleAdapter;
import pony.xcode.recycler.adapter.SimpleBindingAdapter;
import pony.xcode.recycler.holder.BindingViewHolder;
import pony.xcode.recycler.holder.SimpleViewHolder;
import pony.xcode.recycler.widget.XRecyclerView;

/**
 * Created by pony on 2020/12/23.
 */
public class MainActivity extends AppCompatActivity {
    XRecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(item -> {
            final int itemId = item.getItemId();
            if (itemId == R.id.simple) {
                setSimpleAdapter();
            } else if (itemId == R.id.binding) {
                setBindingAdapter();
            }
            return false;
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addHeaderView(getLayoutInflater().inflate(R.layout.header, new FrameLayout(this), false));
        recyclerView.addFooterView(getLayoutInflater().inflate(R.layout.footer, new FrameLayout(this), false));
        setSimpleAdapter();
    }

    private void setSimpleAdapter() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add("item" + (i + 1));
        }
        MyAdapter adapter = new MyAdapter(this, data);
        adapter.setItemClickListener((position, itemView) -> Toast.makeText(this, "您点击了第" + position + "条", Toast.LENGTH_SHORT).show());
        recyclerView.setAdapter(adapter);
    }

    private void setBindingAdapter() {
        List<SimpleBean> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add(new SimpleBean("title" + (i + 1), "content" + (i + 1)));
        }
        MyBindAdapter adapter = new MyBindAdapter(this, data);
        adapter.setItemClickListener((position, itemView) -> Toast.makeText(this, "您点击了第" + position + "条", Toast.LENGTH_SHORT).show());
        recyclerView.setAdapter(adapter);
    }

    private static class MyAdapter extends SimpleAdapter<String> {

        MyAdapter(Context context, List<String> data) {
            super(context, data);
        }

        @Override
        protected int getItemLayoutId(int viewType) {
            return R.layout.item_simple;
        }

        @Override
        protected void convert(SimpleViewHolder holder, String s, int position) {
            holder.setText(R.id.tv, s);
            holder.setVisibility(R.id.divider, position != getItemCount() - 1);
        }
    }

    private static class MyBindAdapter extends SimpleBindingAdapter<SimpleBean, ItemBindingBinding> {

        MyBindAdapter(Context context, List<SimpleBean> data) {
            super(context, data);
        }

        @Override
        protected int getItemLayoutId(int viewType) {
            return R.layout.item_binding;
        }

        @Override
        protected void convert(BindingViewHolder<ItemBindingBinding> holder, ItemBindingBinding binding, SimpleBean simpleBean, int position) {
            binding.setItemInfo(simpleBean);
            binding.setLastPosition(position == getItemCount() - 1);
        }
    }
}
