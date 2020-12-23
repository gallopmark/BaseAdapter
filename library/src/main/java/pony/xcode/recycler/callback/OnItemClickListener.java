package pony.xcode.recycler.callback;

import android.view.View;

import androidx.annotation.NonNull;

/**
 * Created by pony on 2020/12/22.
 * Copyright (c) 2020 holike. All rights reserved.
 */
public interface OnItemClickListener {

    void onItemClick(int position, @NonNull View itemView);
}
