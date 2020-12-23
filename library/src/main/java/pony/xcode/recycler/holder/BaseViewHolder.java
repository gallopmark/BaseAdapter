package pony.xcode.recycler.holder;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by pony on 2020/12/23.
 * Copyright (c) 2020 holike. All rights reserved.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> mViews;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    /**
     * 获取子控件
     *
     * @return </T>
     */
    public final <T extends View> T findViewById(@IdRes int id) {
        View view = mViews.get(id);
        if (view != null) //noinspection unchecked
            return (T) view;
        view = itemView.findViewById(id);
        mViews.put(id, view);
        //noinspection unchecked
        return (T) view;
    }

    /**
     * 文本控件赋值
     */
    public void setText(@IdRes int id, CharSequence text) {
        ((TextView) findViewById(id)).setText(text);
    }

    public void setTypeface(@IdRes int id, Typeface tf) {
        ((TextView) findViewById(id)).setTypeface(tf);
    }

    public void setDrawableLeft(@IdRes int id, @DrawableRes int resId) {
        ((TextView) findViewById(id)).setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0);
    }

    public void setDrawableLeft(@IdRes int id, @Nullable Drawable drawable) {
        ((TextView) findViewById(id)).setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }

    public void setDrawableTop(@IdRes int id, @DrawableRes int resId) {
        ((TextView) findViewById(id)).setCompoundDrawablesWithIntrinsicBounds(0, resId, 0, 0);
    }

    public void setDrawableTop(@IdRes int id, @Nullable Drawable drawable) {
        ((TextView) findViewById(id)).setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
    }

    public void setDrawableRight(@IdRes int id, @Nullable Drawable drawable) {
        ((TextView) findViewById(id)).setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
    }

    public void setDrawableRight(@IdRes int id, @DrawableRes int resId) {
        ((TextView) findViewById(id)).setCompoundDrawablesWithIntrinsicBounds(0, 0, resId, 0);
    }

    public void setDrawableBottom(@IdRes int id, @DrawableRes int resId) {
        ((TextView) findViewById(id)).setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, resId);
    }

    public void setDrawableBottom(@IdRes int id, @Nullable Drawable drawable) {
        ((TextView) findViewById(id)).setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable);
    }

    public void setTextSize(@IdRes int id, float size) {
        ((TextView) findViewById(id)).setTextSize(size);
    }

    public void setTextSize(@IdRes int id, int unit, float size) {
        ((TextView) findViewById(id)).setTextSize(unit, size);
    }

    //可以直接引用 R.color.xxx
    public void setTextColor(@IdRes int id, @ColorInt int color) {
        ((TextView) findViewById(id)).setTextColor(color);
    }

    public void setTextColorRes(@IdRes int id, @ColorRes int color) {
        TextView view = findViewById(id);
        view.setTextColor(ContextCompat.getColor(view.getContext(), color));
    }

    public void setImageResource(@IdRes int id, @DrawableRes int resId) {
        ((ImageView) findViewById(id)).setImageResource(resId);
    }

    public void setScaleType(@IdRes int id, ImageView.ScaleType scaleType){
        ((ImageView) findViewById(id)).setScaleType(scaleType);
    }

    public void setVisibility(@IdRes int id, int visibility) {
        findViewById(id).setVisibility(visibility);
    }

    public void setVisibility(@IdRes int id, boolean isVisible) {
        View view = findViewById(id);
        view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public void setBackgroundColor(@IdRes int id, @ColorInt int color) {
        findViewById(id).setBackgroundColor(color);
    }

    public void setBackgroundColorRes(@IdRes int id, @ColorRes int color) {
        View view = findViewById(id);
        view.setBackgroundColor(ContextCompat.getColor(view.getContext(), color));
    }

    public void setBackgroundResource(@IdRes int id, @DrawableRes int resId) {
        findViewById(id).setBackgroundResource(resId);
    }

    public void setChecked(@IdRes int id, boolean isChecked) {
        if (findViewById(id) instanceof CheckBox) {
            ((CheckBox) findViewById(id)).setChecked(isChecked);
        } else if (findViewById(id) instanceof RadioButton) {
            ((RadioButton) findViewById(id)).setChecked(isChecked);
        } else {
            ((Checkable) findViewById(id)).setChecked(isChecked);
        }
    }

    public void setProgress(@IdRes int id, int progress) {
        ((ProgressBar) findViewById(id)).setProgress(progress);
    }

    public void setProgress(@IdRes int id, int progress, int max) {
        ProgressBar progressBar = findViewById(id);
        progressBar.setProgress(progress);
        progressBar.setMax(max);
    }

    public void setMax(@IdRes int id, int max) {
        ((ProgressBar) findViewById(id)).setMax(max);
    }

    public void setOnClickListener(@IdRes int id, View.OnClickListener onClickListener) {
        findViewById(id).setOnClickListener(onClickListener);
    }

    public void setEnabled(@IdRes int id, boolean enabled) {
        findViewById(id).setEnabled(enabled);
    }
}
