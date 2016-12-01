package com.itchunyang.scroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GuideActivity extends AppCompatActivity {

    private GuideGroup guideGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        guideGroup = (GuideGroup) findViewById(R.id.guide);
    }

    public void left(View view) {
        guideGroup.leftIn();
    }

    public void right(View view) {
        guideGroup.rightOut();
    }

    public void up(View view) {
        guideGroup.upOut();
    }

    public void down(View view) {
        guideGroup.downIn();
    }
}
