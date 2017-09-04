package com.bawei.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;

import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Zhang on 2017/9/4.
 */

public class MyYuanView extends View{
    //颜色
    private int pscolor;
    private int secondcolor;
    //宽度
    private int pswidth;
    //画笔
    private Paint mpaint;
    //进度
    private int mprogress;
    //速度
    private int mSpeed;

    private boolean isNext = false;

    public MyYuanView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public MyYuanView(Context context) {
        this(context,null);
    }


    public void setPscolor(int pscolor) {
        this.pscolor = pscolor;
    }

    public void setSecondcolor(int secondcolor) {
        this.secondcolor = secondcolor;
    }

    public void setmSpeed(int mSpeed) {
        this.mSpeed = mSpeed;
    }

    public MyYuanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Myyuan, defStyleAttr, 0);
        int n = typedArray.getIndexCount();
        for(int i = 0;i<n;i++){
            int attr = typedArray.getIndex(i);
            switch (attr){
                case R.styleable.Myyuan_y_color:
                    pscolor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.Myyuan_y_width:
                    pswidth =  typedArray.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,20,getResources().getDisplayMetrics()));
                    break;
                case R.styleable.Myyuan_y_speed:
                    mSpeed = typedArray.getInt(attr,20);
                    break;
                case R.styleable.Myyuan_secondColor:
                    secondcolor = typedArray.getColor(attr, Color.BLACK);
                    break;
            }
        }
        typedArray.recycle();
        mpaint = new Paint();
        new Thread()
        {
            public void run() {
                while (true){
                    mprogress++;

                    if(mprogress==360){
                        mprogress = 0;
                        if(!isNext){
                            isNext = true;
                        }else{
                            isNext = false;
                        }
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(mSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }

    @Override
    protected  void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int center = getWidth()/2;
        int radius = center - pswidth / 2;
        mpaint.setStrokeWidth(pswidth);
        mpaint.setAntiAlias(true);
        mpaint.setStyle(Paint.Style.STROKE);
        RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius);
        if(!isNext){
            mpaint.setColor(pscolor);
            canvas.drawCircle(center,center,radius,mpaint);
            mpaint.setColor(secondcolor);
            canvas.drawArc(oval,-90,mprogress,false,mpaint);
        }else{
            mpaint.setColor(secondcolor);
            canvas.drawCircle(center,center,radius,mpaint);
            mpaint.setColor(pscolor);
            canvas.drawArc(oval,-90,mprogress,false,mpaint);
        }



    }
}
