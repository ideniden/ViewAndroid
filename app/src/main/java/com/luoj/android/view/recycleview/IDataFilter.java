package com.luoj.android.view.recycleview;

/**
 * Created by LuoJ on 2016/1/12.
 */
public interface IDataFilter<T> {

    boolean filter(T data);

}
