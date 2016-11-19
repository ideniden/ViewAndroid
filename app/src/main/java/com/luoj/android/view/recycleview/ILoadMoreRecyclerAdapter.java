package com.luoj.android.view.recycleview;

import java.util.List;

/**
 * Created by 京 on 2016/11/7.
 */

public interface ILoadMoreRecyclerAdapter {

    public final static int TYPE_FOOTER = 2;
    public final static int TYPE_NORMAL = 1;

    boolean isLoadMoreShowing();

    void showLoadMore();

    void dismissLoadMore();

    void addDataAndNotifyDataSetChanged(List newDatas);
}
