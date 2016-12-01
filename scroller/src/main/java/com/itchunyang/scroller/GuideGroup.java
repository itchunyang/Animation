package com.itchunyang.scroller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by luchunyang on 2016/12/1.
 * 1. 创建Scroller的实例
 * 2. 调用startScroll()方法来初始化滚动数据并刷新界面
 * 3. 重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
 */

public class GuideGroup extends FrameLayout {

    private Scroller mScroller;
    private float x, y;
    private int width,height;
    private int distanceX, distanceY;

    public GuideGroup(Context context) {
        this(context,null);
    }

    public GuideGroup(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GuideGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context,new BounceInterpolator());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    /**
     * computeScroll()是View类的一个空函数，在父容器重画自己的孩子时，它会调用孩子的computScroll方法
     */
    @Override
    public void computeScroll() {
        super.computeScroll();

        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }


    public void leftIn(){
        int startX = width;
        int startY = 0;
        int dx = -width;
        int dy = 0;

        //开始一个动画控制，由(startX , startY)在duration时间内前进(dx,dy)个单位，到达坐标为(startX+dx , startY+dy)处
        //平滑模式 通常用于：知道起点与需要改变的距离的平滑滚动等
        mScroller.startScroll(startX,startY,dx,dy,1000);

        //惯性滑动。 给定一个初始速度（velocityX，velocityY），该方法内部会根据这个速度去计算需要滑动的距离以及需要耗费的时间。通常用于：界面的惯性滑动等。
        //fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY)
        postInvalidate();
    }

    public void rightOut(){
        int startX = 0;
        int startY = 0;
        int dx = -width;
        int dy = 0;
        mScroller.startScroll(startX,startY,dx,dy,1000);
        postInvalidate();
    }

    public void upOut(){
        int startX = 0;
        int startY = 0;
        int dx = 0;
        int dy = height;
        mScroller.startScroll(startX,startY,dx,dy,1000);
        postInvalidate();
    }

    public void downIn(){
        int startX = 0;
        int startY = height;
        int dx = 0;
        int dy = -height;
        mScroller.startScroll(startX,startY,dx,dy,1000);
        postInvalidate();
    }

}
