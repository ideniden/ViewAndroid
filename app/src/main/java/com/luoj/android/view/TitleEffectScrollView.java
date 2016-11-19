package com.luoj.android.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by 京 on 2016/9/18.
 * 联动标题栏，滑动时，标题栏渐变
 */
public class TitleEffectScrollView extends ScrollView {

    private View mByWhichView;
    private View mTitleView;
    @ColorRes
    int bgColor;
    private boolean shouldSlowlyChange = true;

    public TitleEffectScrollView(Context context) {
        super(context);
    }

    public TitleEffectScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TitleEffectScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TitleEffectScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public void scrollTo(int x, int y) {
        //这是为了修复noScrllListView嵌套在srcollview时就自动滑动到noscrolllistview的顶部的bug，不影响使用
        if (x == 0 && y == 0 || y <= 0) {
            super.scrollTo(x, y);
        }
    }

//    public void setListener(OnScrollListener listener) {
//        this.mListener = listener;
//    }

    public void setShouldSlowlyChange(boolean slowlyChange) {
        this.shouldSlowlyChange = slowlyChange;
    }

    /**
     * 设置透明度渐变的标题view
     *
     * @param view
     */
    public void setupTitleView(View view, @ColorRes int bgColor) {
        this.mTitleView = view;
        this.bgColor = bgColor;
    }

    /**
     * 跟随的view
     *
     * @param view
     */
    public void setupByWhichView(View view) {
        mByWhichView = view;
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);

        if (scrollY >= mByWhichView.getTop() + mByWhichView.getMeasuredHeight()) {
            mTitleView.setBackgroundColor(getResources().getColor(bgColor));
        } else if (scrollY >= 0) {
            if (!shouldSlowlyChange) {
                mTitleView.setBackgroundColor(Color.TRANSPARENT);
            } else {
                float persent = scrollY * 1f / (mByWhichView.getTop() + mByWhichView.getMeasuredHeight());
                int alpha = (int) (255 * persent);
                int color = Color.argb(alpha, 38, 41, 46);//26 29 2E
                mTitleView.setBackgroundColor(color);
            }
        }
//        if (mListener != null) {
//            mListener.onScroll(scrollX, scrollY);
//        }
    }

}
