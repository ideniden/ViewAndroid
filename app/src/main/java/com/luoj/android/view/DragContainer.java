package com.luoj.android.view;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by 京 on 2016/8/8.
 */
public class DragContainer extends FrameLayout{

    public DragContainer(Context context) {
        super(context);
        init();
    }

    public DragContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    ViewDragHelper viewDragHelper;
    View mDragView;
    boolean drag;

    Point dragViewPoint=new Point();
    boolean firstOnLayout=true;

    private void init() {
        viewDragHelper= ViewDragHelper.create(this,1.0f, new ViewDragHelper.Callback() {

            @Override
            public boolean tryCaptureView(View child, int pointerId) {
//                LogUtil.d("tryCaptureView");
                drag=true;
                firstOnLayout=true;
                return true;
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight()-child.getMeasuredHeight();
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
//                LogUtil.d("clampViewPositionVertical");
                final int topBound = getPaddingTop();
                final int bottomBound = getHeight() - mDragView.getHeight();
                final int newTop = Math.min(Math.max(top, topBound), bottomBound);
                return newTop;
//                return top;
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return super.getViewHorizontalDragRange(child);
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return super.clampViewPositionHorizontal(child, left, dx);
            }

            @Override
            public int getOrderedChildIndex(int index) {
                return super.getOrderedChildIndex(index);
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                super.onEdgeDragStarted(edgeFlags, pointerId);
            }

            @Override
            public boolean onEdgeLock(int edgeFlags) {
                return super.onEdgeLock(edgeFlags);
            }

            @Override
            public void onEdgeTouched(int edgeFlags, int pointerId) {
                super.onEdgeTouched(edgeFlags, pointerId);
            }

            @Override
            public void onViewCaptured(View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
            }

            @Override
            public void onViewDragStateChanged(int state) {
                super.onViewDragStateChanged(state);
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
//                LogUtil.d("onViewPositionChanged->"+left+","+top+","+dx+","+dy);
                super.onViewPositionChanged(changedView, left, top, dx, dy);
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                dragViewPoint.y=mDragView.getTop();
            }

        });
    }

    public void setDragViewPoint(int x,int y){
        dragViewPoint.x=x;
        dragViewPoint.y=y;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDragView=getChildAt(0);
        MarginLayoutParams layoutParams = (MarginLayoutParams) mDragView.getLayoutParams();
        setDragViewPoint(0,layoutParams.topMargin);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        LogUtil.d("onInterceptTouchEvent");
        final int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            viewDragHelper.cancel();
            return false;
        }
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        LogUtil.d("onTouchEvent");
        viewDragHelper.processTouchEvent(event);
        if(drag){
            if(event.getAction()==MotionEvent.ACTION_UP){
                drag=false;
            }
            return true;
        }else{
            return super.onTouchEvent(event);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        LogUtil.d("onLayout firstOnLayout->"+firstOnLayout);
//        if(firstOnLayout){
//            dragViewPoint.x=mDragView.getLeft();
//            dragViewPoint.y=mDragView.getTop();
//            firstOnLayout=false;
//        }
//        LogUtil.d("onLayout point->"+dragViewPoint.x+","+dragViewPoint.y);
        mDragView.layout(dragViewPoint.x,dragViewPoint.y,
                dragViewPoint.x+mDragView.getMeasuredWidth(),
                dragViewPoint.y+mDragView.getMeasuredHeight());

    }


}
