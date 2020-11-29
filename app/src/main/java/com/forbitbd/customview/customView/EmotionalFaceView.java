package com.forbitbd.customview.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.forbitbd.customview.R;

public class EmotionalFaceView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int faceColor = Color.YELLOW;
    private int eyesColor = Color.BLACK;
    private int mouthColor = Color.BLACK;
    private int borderColor = Color.BLACK;
    // Face border width in pixels
    private float borderWidth = 4.0f;
    // View size in pixels
    private int size = 320;

    private int emotion =0;



    public EmotionalFaceView(Context context) {
        super(context);
    }

    public EmotionalFaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);

    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.EmotionalFaceView,
                0, 0);

        try {
            borderColor = a.getColor(R.styleable.EmotionalFaceView_borderColor, Color.BLACK);
            mouthColor = a.getColor(R.styleable.EmotionalFaceView_mouthColor, Color.BLACK);
            eyesColor = a.getColor(R.styleable.EmotionalFaceView_eyesColor, Color.BLACK);
            faceColor = a.getColor(R.styleable.EmotionalFaceView_faceColor, Color.YELLOW);
            emotion = a.getInteger(R.styleable.EmotionalFaceView_state, 0);
        } finally {
            a.recycle();
        }
    }

    public EmotionalFaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public EmotionalFaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawFaceBackGround(canvas);
        drawEyes(canvas);
        drawMouth(canvas);
    }

    private void drawFaceBackGround(Canvas canvas) {
        paint.setColor(faceColor);
        paint.setStyle(Paint.Style.FILL);

        float radius = size/2f;

        canvas.drawCircle(size/2f,size/2f,radius,paint);

        paint.setColor(borderColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderWidth);

        canvas.drawCircle(size / 2f, size / 2f, radius - borderWidth / 2f, paint);


    }

    private void drawEyes(Canvas canvas) {

        paint.setColor(eyesColor);
        paint.setStyle(Paint.Style.FILL);

        RectF leftEye = new RectF(size * 0.32f, size * 0.23f, size * 0.43f, size * 0.50f);
        canvas.drawOval(leftEye,paint);


        RectF rightEye = new RectF(size * 0.57f, size * 0.23f, size * 0.68f, size * 0.50f);
        canvas.drawOval(rightEye,paint);

    }

    private void drawMouth(Canvas canvas) {
        Path mouthPath = new Path();

        mouthPath.reset();

        mouthPath.moveTo(size * 0.22f, size * 0.7f);

        if(emotion==0){
            mouthPath.quadTo(size * 0.50f, size * 0.80f, size * 0.78f, size * 0.70f);
            mouthPath.quadTo(size * 0.50f, size * 0.90f, size * 0.22f, size * 0.70f);
        }else{
            mouthPath.quadTo(size * 0.50f, size * 0.50f, size * 0.78f, size * 0.70f);
            mouthPath.quadTo(size * 0.50f, size * 0.60f, size * 0.22f, size * 0.70f);
        }


        paint.setColor(mouthColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(mouthPath,paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        size = Math.min(getMeasuredWidth(), getMeasuredHeight());
// 2
        setMeasuredDimension(size, size);
    }
}
