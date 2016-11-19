package com.luoj.android.view.recycleview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.tosee.android.view.RecyclerViewEmptySupport;

import java.util.List;

/**
 * Created by 京 on 2016/11/7.
 */

public class LoadMoreRecyclerView extends RecyclerViewEmptySupport implements ILoadMoreRecyclerAdapter{

    public LoadMoreRecyclerView(Context context) {
        super(context);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public static final int LOAD_MORE_THRESHOLD=3;

    int PAGE_SIZE=20;

    ILoadMoreListener loadMoreListener;

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if(adapter instanceof ILoadMoreRecyclerAdapter){
            addOnScrollListener(new OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                    int totalItemCount = layoutManager.getItemCount();
                    if(totalItemCount<PAGE_SIZE){
                        return;
                    }

                    int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                    ILoadMoreRecyclerAdapter loadMoreAdapter = getLoadMoreAdapter();
                    if (!loadMoreAdapter.isLoadMoreShowing() && totalItemCount < (lastVisibleItem + LOAD_MORE_THRESHOLD)) {
                        if(null!=loadMoreListener){
                            loadMoreListener.onLoadMore(loadMoreAdapter);
                        }
                        loadMoreAdapter.showLoadMore();
                    }
                }
            });
        }
    }

    public void setOnLoadMoreListener(ILoadMoreListener loadMoreListener){
        this.loadMoreListener=loadMoreListener;
    }

    public ILoadMoreRecyclerAdapter getLoadMoreAdapter(){
        Adapter adapter = getAdapter();
        if(adapter instanceof ILoadMoreRecyclerAdapter){
            return (ILoadMoreRecyclerAdapter) adapter;
        }
        return null;
    }

    @Override
    public boolean isLoadMoreShowing() {
        ILoadMoreRecyclerAdapter loadMoreAdapter = getLoadMoreAdapter();
        return null!=loadMoreAdapter?loadMoreAdapter.isLoadMoreShowing():false;
    }

    @Override
    public void showLoadMore() {
        post(new Runnable() {
            @Override
            public void run() {
                ILoadMoreRecyclerAdapter loadMoreAdapter = getLoadMoreAdapter();
                if(null!=loadMoreAdapter){
                    loadMoreAdapter.showLoadMore();
                }
            }
        });
    }

    @Override
    public void dismissLoadMore() {
        post(new Runnable() {
            @Override
            public void run() {
                ILoadMoreRecyclerAdapter loadMoreAdapter = getLoadMoreAdapter();
                if(null!=loadMoreAdapter){
                    loadMoreAdapter.dismissLoadMore();
                }
            }
        });
    }

    @Override
    public void addDataAndNotifyDataSetChanged(final List newDatas) {
        post(new Runnable() {
            @Override
            public void run() {
                ILoadMoreRecyclerAdapter loadMoreAdapter = getLoadMoreAdapter();
                if(null!=loadMoreAdapter){
                    loadMoreAdapter.addDataAndNotifyDataSetChanged(newDatas);
                }
            }
        });
    }

}
