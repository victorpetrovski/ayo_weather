package com.ayocodetest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Victor on 9/19/17.
 */

public abstract class EmptyAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 0;
    private static final int VIEW_TYPE_EMPTY = 1;

    public Context context;

    public EmptyAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_EMPTY) {
            View itemView = LayoutInflater.from(parent.getContext())
                                    .inflate(getLayout(), parent, false);

            return new EmptyAdapter.ViewHolder(itemView);
        } else
            return createStandardViewHolder(parent, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        if (getCount() == 0) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getCount() == 0) {
            Log.d("OnBindViewHolder", "isEmpty");
        } else {
            bindView((T) holder, position);
            Log.d("OnBindViewHolder", "not Empty");
        }
    }

    @Override
    public int getItemCount() {
        return getCount() == 0 ? 1 : getCount();
    }

    abstract int getCount();

    abstract int getLayout();

    public abstract T createStandardViewHolder(ViewGroup parent, int viewType);

    public abstract void bindView(T holder, int position);

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public Context getContext(){
        return context;
    }
}
