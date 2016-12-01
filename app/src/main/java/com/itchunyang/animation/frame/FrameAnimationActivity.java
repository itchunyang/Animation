package com.itchunyang.animation.frame;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.itchunyang.animation.R;

public class FrameAnimationActivity extends AppCompatActivity {

    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);

        iv = (ImageView) findViewById(R.id.iv);
    }

    public void start(View view) {
        AnimationDrawable drawable = (AnimationDrawable) ContextCompat.getDrawable(this,R.drawable.walk);
        iv.setImageDrawable(drawable);
        drawable.start();
    }

    public void stop(View view) {
        AnimationDrawable drawable = (AnimationDrawable) iv.getDrawable();
        drawable.stop();
    }
}
