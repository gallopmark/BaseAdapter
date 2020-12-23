package pony.xcode.recycler.holder;


import androidx.databinding.ViewDataBinding;

/**
 * Created by pony on 2020/7/28.
 * Copyright (c) 2020 holike. All rights reserved.
 */
public class MultiBindingViewHolder extends BindingViewHolder<ViewDataBinding> {

    public MultiBindingViewHolder(ViewDataBinding binding) {
        super(binding);
    }

    public <DB extends ViewDataBinding> DB cast() {
        //noinspection unchecked
        return (DB) binding;
    }
}
