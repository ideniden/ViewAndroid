package com.luoj.android.view.recycleview;

import android.support.v7.widget.RecyclerView;

import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 京 on 2016/8/23.
 */
public abstract class BaseSwipeRecyclerAdapter<T,VH extends RecyclerView.ViewHolder> extends RecyclerSwipeAdapter<VH>{

    /**数据源*/
    protected List<T> datas=new ArrayList<T>();//全量数据
    protected List<T> queryData=new ArrayList<T>();//检索数据
    private boolean showQueryData =false;

    public List<T> getDatas(){
        return datas;
    }

    public List<T> getQueryDatas(){
        return queryData;
    }

    public T get(int index){
        return showQueryData ?queryData.get(index):datas.get(index);
    }

    public void remove(int position){
        datas.remove(position);
    }

    public void removeAndNotifyItemRemoved(int position){
        remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(0,getItemCount());
    }

    public boolean isEmpty(){
        return showQueryData ?queryData.isEmpty():datas.isEmpty();
    }

    @Override
    public int getItemCount(){
        return showQueryData ?queryData.size():datas.size();
    }

    public int getLastItemIndex(){
        return getItemCount()-1;
    }

    public T getLastItem(){
        return get(getLastItemIndex());
    }

    public void refreshDataAndNotifyDataSetChanged(List<? extends T> newDatas){
        if(null==newDatas||newDatas.isEmpty()){
            notifyDataSetChanged();
            return;
        }
        datas.clear();
        datas.addAll(newDatas);
        showQueryData =false;
        notifyDataSetChanged();
    }

    public void refreshQueryDataAndNotifyDataSetChanged(List<? extends T> newDatas){
        if(null==newDatas||newDatas.isEmpty()){
            notifyDataSetChanged();
            return;
        }
        queryData.clear();
        queryData.addAll(newDatas);
        showQueryData =true;
        notifyDataSetChanged();
    }

    public void addDataAndNotifyDataSetChanged(List<? extends T> newDatas){
        if(null==newDatas||newDatas.isEmpty())return;
        datas.addAll(newDatas);
        showQueryData =false;
        notifyDataSetChanged();
    }

    public void addDataAndNotifyDataSetChanged(T newDatas){
        if(null==newDatas)return;
        datas.add(newDatas);
        showQueryData =false;
        notifyDataSetChanged();
    }

    public BaseSwipeRecyclerAdapter<T,VH> clearDatas(){
        datas.clear();
        return this;
    }

    public List<T> filterData(IDataFilter<T> filter){
        queryData.clear();
        for (T t:datas){
            if(filter.filter(t)){
                queryData.add(t);
            }
        }
        showQueryData =true;
        notifyDataSetChanged();
        return queryData;
    }

    public void recoveryFullData(){
        queryData.clear();
        showQueryData =false;
        notifyDataSetChanged();
    }

    public void showQueryData(boolean show){
        showQueryData=show;
        notifyDataSetChanged();
    }

}
