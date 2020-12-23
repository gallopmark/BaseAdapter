package pony.xcode.recycler.callback;

import android.view.View;

import androidx.annotation.NonNull;

/**
 * Created by pony on 2020/12/22.
 * Copyright (c) 2020 holike. All rights reserved.
 */
public interface OnItemLongClickListener {
    void onItemLongClick(int position, @NonNull View itemView);
}
