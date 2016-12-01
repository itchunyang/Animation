package com.itchunyang.animation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.itchunyang.animation.frame.FrameAnimationActivity;
import com.itchunyang.animation.property.PropertyAnimationActivity;
import com.itchunyang.animation.tween.TweenAnimationActivity;

/**
 *
 * 逐帧动画(frame-by-frame animation)
 * 补间动画(tweened animation)  只是改变View的显示效果而已，并不会真正的改变View的属性 只作用于View上面
 * 属性动画(property animation)
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void tweenAnimation(View view) {
        startActivity(new Intent(this, TweenAnimationActivity.class));
    }

    public void frameAnimation(View view) {
        startActivity(new Intent(this, FrameAnimationActivity.class));
    }

    public void propertyAnimation(View view) {
        startActivity(new Intent(this, PropertyAnimationActivity.class));
    }
}
