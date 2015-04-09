package com.f3rog.alf.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Class {@link com.f3rog.alf.adapter.AViewHolder}
 *
 * @author f3rog
 * @version 2015-02-06
 */
public abstract class AViewHolder extends RecyclerView.ViewHolder {

    public AViewHolder(LayoutInflater inflater, ViewGroup parent, int layoutRes) {
        super(inflater.inflate(layoutRes, parent, false));
        ButterKnife.inject(this, itemView);
    }

}