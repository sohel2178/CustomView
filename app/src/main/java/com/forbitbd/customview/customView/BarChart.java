package com.forbitbd.customview.customView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class BarChart extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private GestureDetector detector;

    private int offset =0;
    private int width;




    public BarChart(Context context) {
        super(context);
        init(context);

    }

    public BarChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BarChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        detector = new GestureDetector(context,new MyListener());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawRect(20,20,this.width-offset,80,paint);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        this.width = getMeasuredWidth();

        setMeasuredDimension(getMeasuredWidth(),getMeasuredHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = detector.onTouchEvent(event);
        Log.d("HHHHH","DOWN Called "+result);

        if(!result){
            if(event.getAction()==MotionEvent.ACTION_UP){
                result = true;
            }
        }
        return result;
    }

    class MyListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d("HHHHH","Fling Called");
            Log.d("HHHHH",e1.getX()+","+e1.getY()+"|"+e2.getX()+","+e2.getY());
            Log.d("HHHHH",velocityX+","+velocityY);

            ValueAnimator valueAnimator = ValueAnimator.ofInt(300);
            valueAnimator.setDuration(1000);
            valueAnimator.setupEndValues();

            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (int) valueAnimator.getAnimatedValue();
                    Log.d("HHHHH","Fling Called"+val);
                    //offset =offset +val;
                    postInvalidate();

                }


            });

            valueAnimator.start();

            return true;
//            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}
