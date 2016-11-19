package com.luoj.android.view.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.tosee.android.R;

/**
 * Created by 京 on 2016/11/7.
 */

public class LoadMoreViewHolder extends RecyclerView.ViewHolder {
    public final ProgressBar progressBar;
    public LoadMoreViewHolder(View itemView) {
        super(itemView);
        progressBar= (ProgressBar) itemView.findViewById(R.id.pb);
    }

}
