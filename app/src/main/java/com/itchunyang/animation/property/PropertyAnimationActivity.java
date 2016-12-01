package com.itchunyang.animation.property;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.itchunyang.animation.R;
import com.itchunyang.animation.property.evaluator.Ball;
import com.itchunyang.animation.property.evaluator.Point;
import com.itchunyang.animation.property.evaluator.PointEvaluator;
import com.itchunyang.animation.property.evaluator.PointView;

/**
 * Property Animation故名思议就是通过动画的方式改变对象的属性了
 * 这个类是唯一的类ValueAnimator ! ObjectAnimator是ValueAnimator的子类!
 */
public class PropertyAnimationActivity extends AppCompatActivity {

    private ImageView iv;
    public static final String TAG = PropertyAnimationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation);

        iv = (ImageView) findViewById(R.id.iv);

        iv.setClickable(true);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PropertyAnimationActivity.this, "图片被点击", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * ValueAnimator 只是对指定的数字区间做动画运算,不是针对控件的。我们对过程进行监听,然后自己对控件做动画操作
     *
     * ValueAnimator有三个事件监听器
     * @param view
     */
    public void ofFloat(View view) {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 300);
        animator.setDuration(1000).setStartDelay(1000);//延时一秒
//        final float originX = iv.getX();
//        final float originY = iv.getY();

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //强制转换成哪种类型 要和上面的ofFloat ofInt等一致,否则报错
                float currValue = (float) animation.getAnimatedValue();
//                iv.setX(originX+currValue);
//                iv.setY(originY+currValue);
                iv.setTranslationX(currValue);
                iv.setTranslationY(currValue);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
//        animator.pause();
        animator.start();
    }

    public void ofInt(View view) {
        ValueAnimator animator = ValueAnimator.ofInt(0, 300).setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currValue = (int) animation.getAnimatedValue();
                //layout参数坐标是以屏幕坐标为基准的。所在会看到iv运动轨迹是从屏幕左上角开始
                iv.layout(currValue, currValue, currValue + iv.getWidth(), currValue + iv.getHeight());
            }
        });
        animator.start();
    }

    public void ofObject(View view) {
        //ofFloat ofInt传入的数据类型有限,ofObject可以传入任何类型的数据
        /**
         * 参数TypeEvaluator 告诉动画系统如何从初始值过度到结束值
         * ValueAnimator.ofFloat()方法就是实现了初始值与结束值之间的平滑过度，那么这个平滑过度是怎么做到的呢？
         * 其实就是系统内置了一个FloatEvaluator，它通过计算告知动画系统如何从初始值过度到结束值
         *
         * public Float evaluate(float fraction, Number startValue, Number endValue) {
         *      float startFloat = startValue.floatValue();
         *      return startFloat + fraction * (endValue.floatValue() - startFloat);
         * }
         * 第一个参数fraction非常重要，这个参数用于表示动画的完成度的,第二第三个参数分别表示动画的初始值和结束值
         * 上述代码的逻辑就比较清晰了,用结束值减去初始值，算出它们之间的差值，然后乘以fraction这个系数，再加上初始值，那么就得到当前动画的值了
         *
         */

        Point start = new Point(0,0);
        Point end = new Point(300,300);
        ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(),start,end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point point = (Point) animation.getAnimatedValue();
                Log.i(TAG, "onAnimationUpdate: "+point);
            }
        });
        animator.setDuration(1200);
        animator.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void ofArgb(View view) {
        ValueAnimator animator = ValueAnimator.ofArgb(Color.RED,Color.BLUE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int color = (int) animation.getAnimatedValue();
                iv.setBackgroundColor(color);
            }
        });
        animator.setDuration(1300).start();
    }


    /*********************** ObjectAnimator ***************************/
    /**
     * valueAnimator 有个缺点,只能对数值做计算,我们自己要监听动画过程,然后手动对控件进行操作。
     * 为了让动画可以直接与控件联系起来,让我们从监听动画的过程中解放出来,google在ValueAnimator基础上,派生了ObjectAnimator
     * 所有ValueAnimator能用的方法,在ObjectAnimator中都可以使用。
     * 但ObjectAnimator也重写了几个方法,比如ofInt  ofFloat等
     *
     *
     * 工作机制就是通过get set方法设定属性
     *
     * 常见的一些View属性
     *
     * 1、透明度：alpha
     * public void setAlpha(float alpha)
     *
     * 2、旋转度数：rotation、rotationX、rotationY
     * public void setRotation(float rotation)表示围绕Z旋转,rotation表示旋转度数
     * public void setRotationX(float rotationX)表示围绕X轴旋转，rotationX表示旋转度数
     * public void setRotationY(float rotationY)表示围绕Y轴旋转，rotationY表示旋转度数
     *
     * 3、平移：translationX、translationY
     * public void setTranslationX(float translationX) 以当前控件为原点，向右为正方向，参数translationX表示移动的距离。
     * public void setTranslationY(float translationY) 以当前控件为原点，向下为正方向，参数translationY表示移动的距离。
     *
     * 4、缩放：scaleX、scaleY
     * public void setScaleX(float scaleX)
     * public void setScaleY(float scaleY)
     */

    public void objectOfFloat(View view) {
//        ObjectAnimator animator = ObjectAnimator.ofFloat(iv,"alpha",0.0f,1.0f);
//        ObjectAnimator animator = ObjectAnimator.ofFloat(iv,"scaleX",0f,1.2f);
//        ObjectAnimator animator = ObjectAnimator.ofFloat(iv,"rotationX",0f,360);
//        ObjectAnimator animator = ObjectAnimator.ofFloat(iv,"rotationY",0f,360);
//        ObjectAnimator animator = ObjectAnimator.ofFloat(iv,"rotation",0f,360);//默认的就是围绕z轴转动

        ObjectAnimator animator = ObjectAnimator.ofFloat(iv,"translationX",0f,100f,50f);
        animator.setDuration(1400);
        animator.start();
    }

    public void objectOfInt(View view) {
        ObjectAnimator animator = ObjectAnimator.ofInt(iv,"BackgroundColor",Color.RED,Color.GREEN,Color.BLUE);
        animator.setEvaluator(new ArgbEvaluator());
        animator.setDuration(4000).start();
    }

    public void objectOfObject(View view) {
        //主要作用是根据一定的规则对目标对象的某个具体属性进行改变，从而使目标对象实现与该属性相关的动画效果
        //target 动画的实施对象  propertyName target的属性  TypeEvaluator补间器  Object... values 属性集,即属性的开始点，中途变化点，结束点的具体值
        Ball ball = new Ball(new Point(0,0));
        ObjectAnimator animator = ObjectAnimator.ofObject(ball,"point",new PointEvaluator(),new Point(10,10),new Point(100,100),new Point(0,0));
        animator.setDuration(2000).addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point point = (Point) animation.getAnimatedValue();
                Log.i(TAG, "onAnimationUpdate: "+point);
            }
        });
        animator.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void objectOfArb(View view) {
        ObjectAnimator.ofArgb(iv,"backgroundColor",Color.RED,Color.BLUE).setDuration(2000).start();
    }

    /*********************** AnimatorSet ***************************/

    public void set(View view) {
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(iv,"alpha",0.0f,1f);
        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(iv,"rotation",0.0f,360f);
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(iv,"scaleX",1.0f,1.5f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(iv,"scaleY",1.0f,1.5f);

        AnimatorSet set = new AnimatorSet();

        //alphaAnimator 和 rotateAnimator 一起播放
        //scaleXAnimator 和 scaleYAnimator 一起播放 并且在rotateAnimator播放之后才开始
        set.play(alphaAnimator).with(rotateAnimator);
        set.playSequentially();
        set.play(scaleXAnimator).after(rotateAnimator);
        set.play(scaleXAnimator).with(scaleYAnimator);

        //同时执行
//        set.playTogether(alphaAnimator,rotateAnimator);

        //按照传入动画的顺序开始执行
//        set.playSequentially();

        //延时多少秒后执行
//        set.play(scaleXAnimator).after(1000);

        //设置每个子动画的时间!!
        set.setDuration(2000);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Toast.makeText(PropertyAnimationActivity.this,"动画结束,缩放回原来大小!",Toast.LENGTH_SHORT).show();
                iv.setScaleX(1.0f);
                iv.setScaleY(1.0f);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.start();
    }

    /**
     * PropertyValuesHolder这个类的意义就是，它其中保存了动画过程中所需要操作的属性和对应的值
     * ValueAnimator和ObjectAnimator都具有ofPropertyValuesHolder（）函数，使用方法也差不多
     *
     * 我们通过ofFloat(Object target, String propertyName, float… values)构造的动画，ofFloat()的内部实现其实就是将传进来的参数封装成PropertyValuesHolder实例来保存动画状态
     *
     * public static PropertyValuesHolder ofFloat(String propertyName, float... values)
     * public static PropertyValuesHolder ofInt(String propertyName, int... values)
     * public static PropertyValuesHolder ofObject(String propertyName, TypeEvaluator evaluator,Object... values)
     * public static PropertyValuesHolder ofKeyframe(String propertyName, Keyframe... values)
     */
    public void valuesHolder(View view) {
        iv.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.phone));
        PropertyValuesHolder rotationHolder = PropertyValuesHolder.ofFloat("rotation",60f,-60f,40f,-40f,20f,-20f,10f,-10f,0f);
        PropertyValuesHolder scaleXHolder = PropertyValuesHolder.ofFloat("scaleX",1.0f,1.2f,1.0f);
        PropertyValuesHolder scaleYHolder = PropertyValuesHolder.ofFloat("scaleY",1.0f,1.2f,1.0f);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(iv,rotationHolder,scaleXHolder,scaleYHolder);
        animator.setDuration(2000);
        animator.start();
    }

    public void valuesHolder1(View view) {
        PropertyValuesHolder colorHolder = PropertyValuesHolder.ofInt("backgroundColor",Color.RED,Color.BLUE,Color.DKGRAY);
        colorHolder.setEvaluator(new ArgbEvaluator());
        PropertyValuesHolder rotationHolder = PropertyValuesHolder.ofFloat("rotation",0f,360f);

        ValueAnimator animator = ValueAnimator.ofPropertyValuesHolder(colorHolder,rotationHolder);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float rotation = (float) animation.getAnimatedValue("rotation");
                int color = (int) animation.getAnimatedValue("backgroundColor");
                iv.setRotation(rotation);
                iv.setBackgroundColor(color);
            }
        });
        animator.setDuration(2000);
        animator.start();
    }


    //为了解决方便的控制动画速率的问题，谷歌为了我等屁民定义了一个KeyFrame的类
    //使用Keyframe来构建动画，至少要有两个或两个以上帧
    public void keyFrame(View view) {
        //fraction：表示当前的显示进度，即从加速器中getInterpolation()函数的返回值；
        //value：表示当前应该在的位置
        Keyframe frame0 = Keyframe.ofFloat(0f, 0);//表示动画进度为0时，动画所在的数值位置为0
        Keyframe frame1 = Keyframe.ofFloat(0.1f, -20f);
//        frame1.setInterpolator();//可以设置差值器
        Keyframe frame2 = Keyframe.ofFloat(0.2f, 20f);
        Keyframe frame3 = Keyframe.ofFloat(0.3f, -20f);
        Keyframe frame4 = Keyframe.ofFloat(0.4f, 20f);
        Keyframe frame5 = Keyframe.ofFloat(0.5f, -20f);
        Keyframe frame6 = Keyframe.ofFloat(0.6f, 20f);
        Keyframe frame7 = Keyframe.ofFloat(0.7f, -20f);
        Keyframe frame8 = Keyframe.ofFloat(0.8f, 20f);
        Keyframe frame9 = Keyframe.ofFloat(0.9f, -20f);
        Keyframe frame10 = Keyframe.ofFloat(1, 0);
        PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("rotation",frame0,frame1,frame2,frame3,frame4,frame5,frame6,frame7,frame8,frame9,frame10);

        /**
         * scaleX放大1.1倍
         */
        Keyframe scaleXframe0 = Keyframe.ofFloat(0f, 1);
        Keyframe scaleXframe1 = Keyframe.ofFloat(0.1f, 1.1f);
        Keyframe scaleXframe2 = Keyframe.ofFloat(0.2f, 1.1f);
        Keyframe scaleXframe3 = Keyframe.ofFloat(0.3f, 1.1f);
        Keyframe scaleXframe4 = Keyframe.ofFloat(0.4f, 1.1f);
        Keyframe scaleXframe5 = Keyframe.ofFloat(0.5f, 1.1f);
        Keyframe scaleXframe6 = Keyframe.ofFloat(0.6f, 1.1f);
        Keyframe scaleXframe7 = Keyframe.ofFloat(0.7f, 1.1f);
        Keyframe scaleXframe8 = Keyframe.ofFloat(0.8f, 1.1f);
        Keyframe scaleXframe9 = Keyframe.ofFloat(0.9f, 1.1f);
        Keyframe scaleXframe10 = Keyframe.ofFloat(1, 1);
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofKeyframe("ScaleX",scaleXframe0,scaleXframe1,scaleXframe2,scaleXframe3,scaleXframe4,scaleXframe5,scaleXframe6,scaleXframe7,scaleXframe8,scaleXframe9,scaleXframe10);

        Keyframe scaleYframe0 = Keyframe.ofFloat(0f, 1);
        Keyframe scaleYframe1 = Keyframe.ofFloat(0.1f, 1.1f);
        Keyframe scaleYframe2 = Keyframe.ofFloat(0.2f, 1.1f);
        Keyframe scaleYframe3 = Keyframe.ofFloat(0.3f, 1.1f);
        Keyframe scaleYframe4 = Keyframe.ofFloat(0.4f, 1.1f);
        Keyframe scaleYframe5 = Keyframe.ofFloat(0.5f, 1.1f);
        Keyframe scaleYframe6 = Keyframe.ofFloat(0.6f, 1.1f);
        Keyframe scaleYframe7 = Keyframe.ofFloat(0.7f, 1.1f);
        Keyframe scaleYframe8 = Keyframe.ofFloat(0.8f, 1.1f);
        Keyframe scaleYframe9 = Keyframe.ofFloat(0.9f, 1.1f);
        Keyframe scaleYframe10 = Keyframe.ofFloat(1, 1);
        PropertyValuesHolder holder3 = PropertyValuesHolder.ofKeyframe("ScaleY",scaleYframe0,scaleYframe1,scaleYframe2,scaleYframe3,scaleYframe4,scaleYframe5,scaleYframe6,scaleYframe7,scaleYframe8,scaleYframe9,scaleYframe10);


        Animator animator = ObjectAnimator.ofPropertyValuesHolder(iv,holder,holder2,holder3);
        animator.setDuration(1000);
        animator.start();
    }

    public void pointView(View view) {
        PointView v = new PointView(this);
        setContentView(v);

        ObjectAnimator translation = ObjectAnimator.ofObject(v,"point",new PointEvaluator(),new Point(100,100),new Point(700,800));
        ObjectAnimator color = ObjectAnimator.ofObject(v,"color",new ArgbEvaluator(),Color.RED,Color.GREEN);


        AnimatorSet set = new AnimatorSet();
        set.setDuration(1500);
        set.playTogether(translation,color);
        set.start();
//        v.start(); //也可以这样
    }

    /*********************** Interpolator ***************************/

    /**
     * TimeInterpolator是整个动画插值器的最顶层接口,定义动画改变的速率，使得动画不一定要匀速改变，可以加速、减速。
     * public interface TimeInterpolator {
     *     float getInterpolation(float input);
     * }
     *
     * Interpolator.getInterpolation(float) 篡改了播放进度；
     * TypeEvaluator 拿着被篡改的进度计算当前的动画过程值 evaluate(float fraction, Object startValue, Object endValue)
     * 这样，动画就可以忽快忽慢，或者全程加速，或者全程减速，或者是某种奇特的时间曲线。
     *
     */
    //动画开始与结束的地方速率慢,中间快
    public void accelerateDecelerate(View view) {
        ValueAnimator animator = ValueAnimator.ofFloat(0,400);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(1500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float x = (float) animation.getAnimatedValue();
                iv.setTranslationX(x);
                iv.setTranslationY(x);
            }
        });
        animator.start();
    }

    //动画结束时弹起
    public void bounce(View view) {
        TranslateAnimation animation = (TranslateAnimation) AnimationUtils.loadAnimation(this,R.anim.translate);
        animation.setInterpolator(new BounceInterpolator());
//        animation.setInterpolator(new CycleInterpolator(1));
        iv.startAnimation(animation);
//        LinearInterpolator //线性
    }
}
