package com.forbitbd.customview.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class SudokuControl extends View {

    private int mainWidth;
    private int mainHeight;

    private float blockSize;

    private int normal= Color.parseColor("#212121");
    private int shaded= Color.parseColor("#616161");

    private Paint paint = new Paint();


    public SudokuControl(Context context) {
        super(context);
    }

    public SudokuControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(shaded);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawRect(0,0,mainWidth,mainHeight-blockSize,paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        this.mainWidth = getMeasuredWidth();
        this.mainHeight = getMeasuredHeight();

        this.blockSize = this.mainWidth/9f;

        Log.d("HHHH",getMeasuredHeight()+"");
        Log.d("HHHH",getMeasuredWidth()+"");
        setMeasuredDimension(mainWidth,mainHeight);
    }
}
