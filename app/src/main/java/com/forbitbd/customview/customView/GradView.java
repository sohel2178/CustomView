package com.forbitbd.customview.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class GradView extends View {
    private int width;
    private int height;

    private RadialGradient mRadialGradient;
    private RadialGradient mBulbGradient;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint bublPaing = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float radialScaleDirection;
    private Matrix matrix;
    private float radialScale;

    private String state="OFF";

    public GradView(Context context) {
        super(context);
        init();
    }

    public GradView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GradView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        matrix = new Matrix();
        bublPaing.setColor(Color.parseColor("#F4FF81"));
        bublPaing.setStyle(Paint.Style.FILL);
        paint.setStyle(Paint.Style.FILL);

    }

    public void setState(String state){
        this.state = state;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(mRadialGradient==null){
            mRadialGradient = new RadialGradient(this.width/2f,this.height/2f,this.width/2f,new int[] { Color.parseColor("#10EEFF41"),Color.parseColor("#80EEFF41"),Color.parseColor("#20EEFF41"),Color.parseColor("#60EEFF41"),Color.parseColor("#01EEFF41")}, null, Shader.TileMode.MIRROR);
            paint.setShader(mRadialGradient);
        }

        int[] shader;
        if(this.state.equals("ON")){
            shader = new int[]{Color.parseColor("#aaEEFF41"),Color.parseColor("#99EEFF41"),Color.parseColor("#80EEFF41")};
            float scale = getScale(0.01f);
            matrix.setScale(scale, scale, width/2, height/2);
            mRadialGradient.setLocalMatrix(matrix);
            canvas.drawCircle(width/2f,height/2f,width/2f,paint);
        }else {
            shader = new int[]{Color.parseColor("#aaaaaaaa"),Color.parseColor("#99aaaaaa"),Color.parseColor("#80aaaaaa")};
        }

        mBulbGradient = new RadialGradient(this.width/2f,this.height/2f,this.width*.7f/2f,shader, null, Shader.TileMode.MIRROR);

        bublPaing.setShader(mBulbGradient);
        canvas.drawCircle(width/2f,height/2f,width*0.7f/2f,bublPaing);


        /*if(radialScale==0.9f){
            radialScale =1.1f;
        }else {
            radialScale =0.9f;
        }*/

       /* if(this.state.equals("ON")){
            bublPaing.setShader(mBulbGradient);
            canvas.drawCircle(width/2f,height/2f,width/2f,paint);
            canvas.drawCircle(width/2f,height/2f,width*0.7f/2f,bublPaing);
        }else{
            bublPaing.setShader(null);
            bublPaing.setColor(Color.parseColor("#777777"));
            canvas.drawCircle(width/2f,height/2f,width*0.7f/2f,bublPaing);
        }*/




        postInvalidateOnAnimation();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.width = getMeasuredWidth();
        this.height = getMeasuredHeight();

        setMeasuredDimension(this.width,this.height);
    }

    private float getScale(float delta){
        radialScale = (radialScale + delta * radialScaleDirection);
        if (radialScale <= 0.7f) {
            radialScaleDirection = 1;
            radialScale = 0.7f;
        } else if (radialScale >= 1) {
            radialScaleDirection = -1;
            radialScale = 1f;
        }

        return radialScale;
    }
}
