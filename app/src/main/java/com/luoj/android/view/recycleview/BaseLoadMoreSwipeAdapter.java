package com.luoj.android.view.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luoj.android.view.R;

import java.util.List;

/**
 * Created by 京 on 2016/11/7.
 */

public abstract class BaseLoadMoreSwipeAdapter<T,VH extends RecyclerView.ViewHolder> extends BaseSwipeRecyclerAdapter<T,RecyclerView.ViewHolder> implements ILoadMoreRecyclerAdapter{

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_NORMAL){
            return onCreateViewHolder(parent);
        }else if(viewType==TYPE_FOOTER){
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.layout_footer_load_more, parent, false);
            return new LoadMoreViewHolder(view);
        }
        return null;
    }

    public abstract VH onCreateViewHolder(ViewGroup parent);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof LoadMoreViewHolder){
            return;
        }
        onBindViewHolder((VH) viewHolder,position,TYPE_NORMAL);
    }

    public abstract void onBindViewHolder(VH viewHolder, int position,int type);

    @Override
    public int getItemViewType(int position) {
        Object o = get(position);
        if(null==o){
            return TYPE_FOOTER;
        }else{
            return TYPE_NORMAL;
        }
    }

    @Override
    public boolean isLoadMoreShowing(){
        return getLastItem()==null;
    }

    @Override
    public void showLoadMore(){
        if(!isLoadMoreShowing()){
            getDatas().add(null);
            notifyItemInserted(getLastItemIndex());
        }
    }

    @Override
    public void dismissLoadMore(){
        if(isLoadMoreShowing()){
            removeAndNotifyItemRemoved(getLastItemIndex());
        }
    }

    @Override
    public void addDataAndNotifyDataSetChanged(List newDatas) {
        if(isLoadMoreShowing()){
            dismissLoadMore();
        }
        super.addDataAndNotifyDataSetChanged(newDatas);
    }

}
