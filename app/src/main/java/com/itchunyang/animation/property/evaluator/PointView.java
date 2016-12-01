package com.itchunyang.animation.property.evaluator;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by luchunyang on 2016/12/1.
 */

public class PointView extends View {
    private Point point;
    private int color = Color.RED;
    private Paint paint;

    public PointView(Context context) {
        this(context,null);
    }

    public PointView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(point != null){
            paint.setColor(color);
            canvas.drawCircle(point.getX(),point.getY(),100,paint);
        }
    }


    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
        postInvalidate();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void start(){
        ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(),new Point(50,50),new Point(500,800));
        animator.setDuration(5000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                point = (Point) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }
}
