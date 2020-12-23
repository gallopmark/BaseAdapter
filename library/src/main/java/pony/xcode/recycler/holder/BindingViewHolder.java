package pony.xcode.recycler.holder;

import androidx.databinding.ViewDataBinding;

/**
 * Created by pony on 2020/12/22.
 * Copyright (c) 2020 holike. All rights reserved.
 */
public class BindingViewHolder<DB extends ViewDataBinding> extends BaseViewHolder {
    public final DB binding;

    public BindingViewHolder(DB binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
