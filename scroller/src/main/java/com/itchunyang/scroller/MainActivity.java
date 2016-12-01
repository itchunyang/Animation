package com.itchunyang.scroller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Scroller是一个专门用于处理滚动效果的工具类,大家所熟知的控件在内部都是使用Scroller来实现的，如ViewPager、ListView等
 * Scroller和OverScroller，这两个是AndroidUI框架下实现滚动效果的最关键的类.ScrollView内部的实现也是使用的OverScroller
 *
 *
 * 先撇开Scroller类不谈，其实任何一个控件都是可以滚动的，因为在View类当中有scrollTo()和scrollBy()这两个方法
 *
 * 不管是scrollTo()还是scrollBy()方法，滚动的都是该View内部的内容
 *
 * 目前使用这两个方法完成的滚动效果是跳跃式的，没有任何平滑滚动的效果。没错，只靠scrollTo()和scrollBy()这两个方法是很难完成ViewPager这样的效果的，
 * 因此我们还需要借助另外一个关键性的工具，也就我们今天的主角Scroller
 */
public class MainActivity extends AppCompatActivity {

    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = (LinearLayout) findViewById(R.id.layout);
    }

    /**
     * 第一个参数x表示相对于当前位置横向移动的距离，正值向左移动，负值向右移动，单位是像素
     * 第二个参数y表示相对于当前位置纵向移动的距离，正值向上移动，负值向下移动，单位是像素
     *
     * 让View相对于初始的位置滚动某段距离，由于View的初始位置是不变的，因此不管我们点击多少次scrollTo按钮滚动到的都将是同一个位置
     */
    public void scrollTo(View view) {
        layout.scrollTo(-40,-130);
    }

    /**
     * 让View相对于当前的位置滚动某段距离，那每当我们点击一次scrollBy按钮，View的当前位置都进行了变动，因此不停点击会一直向右下方移动。
     */
    public void scrollBy(View view) {
        layout.scrollBy(-40,-130);
    }

    public void guide(View view) {
        startActivity(new Intent(this,GuideActivity.class));
    }
}
