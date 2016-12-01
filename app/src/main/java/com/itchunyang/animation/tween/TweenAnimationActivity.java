package com.itchunyang.animation.tween;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.itchunyang.animation.R;

/**
 * fillBefore是指动画结束时画面停留在此动画的第一帧
 * fillAfter是指动画结束是画面停留在此动画的最后一帧。
 * duration 持续时间
 * repeatCount 重复次数 -1无限循环
 * repeatMode  重复模式 reverse倒叙 restart重新播放 要与repeatCount用时使用
 * interpolator 插值器
 */
public class TweenAnimationActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tween_animation);

        tv = (TextView) findViewById(R.id.tv);
    }

    public void alpha(View view) {
        AlphaAnimation animation = (AlphaAnimation) AnimationUtils.loadAnimation(this,R.anim.alpha);
        //Animation 只有一个监听器
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                System.out.println(tv.getAlpha());//1.0f
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        tv.startAnimation(animation);
    }

    /**
     * fromXDelta 属性为动画起始时 X坐标上的位置(当前位置 + fromXDelta) 可以是数值 百分数  百分数p
     * toXDelta   属性为动画结束时 X坐标上的位置
     * fromYDelta 属性为动画起始时 Y坐标上的位置
     * toYDelta   属性为动画结束时 Y坐标上的位置
     * @param view
     */
    public void translate(View view) {
        TranslateAnimation animation = (TranslateAnimation) AnimationUtils.loadAnimation(this,R.anim.translate);
        tv.startAnimation(animation);
    }

    /**
     * pivot 取值数值 百分数 百分数p
     * 数值(50):起始点坐标在控件左上角（坐标原点），向x轴正方向和y轴正方向都加上50像素；
     * 百分数(50%):表示在原点坐标(控件左上角)的基础上加上的自己宽度的50%
     * 百分数p(50%p):取值的基数是父控件
     * @param view
     */
    public void scale(View view) {
        ScaleAnimation animation = (ScaleAnimation) AnimationUtils.loadAnimation(this,R.anim.scale);
        tv.startAnimation(animation);
    }

    public void rotate(View view) {
        RotateAnimation animation = (RotateAnimation) AnimationUtils.loadAnimation(this,R.anim.rotate);
        tv.startAnimation(animation);
    }

    public void set(View view) {
        AnimationSet set = (AnimationSet) AnimationUtils.loadAnimation(this,R.anim.set);
        tv.startAnimation(set);
    }

    public void reset(View view) {
        tv.clearAnimation();
    }
}
