package com.forbitbd.customview.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.forbitbd.customview.R;

import java.util.ArrayList;
import java.util.List;

public class MadView extends View {
    private int size=400;
    private float radius=0;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int centerCircleColor = Color.YELLOW;

    private List<MyPoint> pointList;

    private int[] colors;
    private List<String> stringList;
    private MyListener listener;
    private String refrence;
    private int selectedPosition =0;
    public MadView(Context context) {
        super(context);
        init(context);
    }

    public MadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public MadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void setMyListener(MyListener myListener){
        this.listener = myListener;
    }

    public void setRefrence(String refrence){
        this.refrence = refrence;
    }

    private void init(Context context){
        this.colors = context.getResources().getIntArray(R.array.colors);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.centerCircleColor = context.getResources().getColor(R.color.ref_color,null);
        }
        this.pointList = new ArrayList<>();
        this.stringList = new ArrayList<>();

    }

    public void setStringList(List<String> stringList){
        this.stringList = stringList;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        drawCenterCircle(canvas);
        drawOtherCircles(canvas);


    }

    private void drawOtherCircles(Canvas canvas) {
       /* canvas.save();
        canvas.rotate(-90,size/2f,size/2f);*/
        paint.setStyle(Paint.Style.FILL);

        float bigRadius = size/2f-radius*1.1f;

        float refX = size/2f;
        float refY = size/2f;

        float x=0,y=0;
       /* if(otherCircles<=1){
            x = refX +(float) (bigRadius*Math.cos(0));
            y = refY+(float) (bigRadius*Math.sin(0));
            canvas.drawCircle(x,y,radius,paint);
        }else{



        }*/
        double angleDiff = 2*Math.PI/stringList.size();

        for (int i=0;i<stringList.size();i++){
            paint.setColor(colors[i%colors.length]);
            x = refX +(float) (bigRadius*Math.sin(i*angleDiff));
            y = refY-(float) (bigRadius*Math.cos(i*angleDiff));

            if(i==selectedPosition){
                canvas.drawCircle(x,y,radius*1.1f,paint);
            }else{
                canvas.drawCircle(x,y,radius*0.9f,paint);
            }



            pointList.add(new MyPoint(x,y));

           /* canvas.save();
            canvas.rotate(90,x,y);*/

            paint.setColor(Color.BLACK);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(30f);

            canvas.drawText(stringList.get(i),x,y,paint);

//            canvas.restore();

            //paint.setColor(otherCircleColor);
        }

//        canvas.restore();
//

    }

    private void drawCenterCircle(Canvas canvas) {
        paint.setColor(centerCircleColor);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(size/2f,size/2f,radius*1.2f,paint);
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(20f);
        if(this.refrence!=null){
            canvas.drawText(refrence,size/2f,size/2f,paint);
        }else{
            canvas.drawText("Cash",size/2f,size/2f,paint);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        size = Math.min(getMeasuredWidth(),getMeasuredHeight());
        this.radius = size/10f;
        setMeasuredDimension(size,size);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       // return super.onTouchEvent(event);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //Log.d("hhhhhh","Touch");
                //event.getX()
                MyPoint touchPoint = new MyPoint(event.getX(),event.getY());

                for (MyPoint x:pointList){
                    if(x.getDistance(touchPoint)<=radius){
                        Log.d("hhhhhh","Touch Yoooo "+pointList.indexOf(x));
                        this.selectedPosition = pointList.indexOf(x);

                        if(this.listener !=null){
                            listener.itemTouch(pointList.indexOf(x));
                        }

                        invalidate();
                        return true;
                    }
                }
                return false;
            default:
                return false;
        }
    }

    class MyPoint{
        float x;
        float y;

        public MyPoint(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public float getDistance(MyPoint point){
            return (float) Math.sqrt(Math.pow((this.getX()-point.getX()),2)+Math.pow((this.getY()-point.getY()),2));
        }
    }


    public interface MyListener{
        void itemTouch(int position);
    }
}
