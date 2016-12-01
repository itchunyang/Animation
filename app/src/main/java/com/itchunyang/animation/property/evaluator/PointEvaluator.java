package com.itchunyang.animation.property.evaluator;

import android.animation.TypeEvaluator;

/**
 * Created by luchunyang on 2016/11/30.
 */

public class PointEvaluator implements TypeEvaluator<Point> {

    @Override
    public Point evaluate(float fraction, Point startValue, Point endValue) {

        //ObjectAnimator animator = ObjectAnimator.ofObject(ball,"point",new PointEvaluator(),new Point(10,10),new Point(100,100),new Point(0,0));
        //startValue 和 endValue是分段的,从new Point(10,10)--->new Point(100,100) startValue是(10,10),endValue是(100,100)
        //new Point(100,100)--->new Point(0,0) startValue是(100,100),endValue是(0,0)
//        System.out.println("start->"+startValue);
//        System.out.println("endValue->"+endValue);

        int x = (int) (startValue.getX() + fraction * (endValue.getX() - startValue.getX()));
        int y = (int) (startValue.getY() + fraction * (endValue.getY() - startValue.getY()));

        return new Point(x,y);
    }
}
