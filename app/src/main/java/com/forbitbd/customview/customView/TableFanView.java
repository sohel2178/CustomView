package com.forbitbd.customview.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.forbitbd.customview.R;

public class TableFanView extends ImageView {
    private final int STROKE_WIDTH_DP  =6;

    private Paint paintBorder;
    private Bitmap bitmap;
    private int strokeWidthPx;
    private RectF rectF;
    private int rotationDegrees = 0;

    private int width,height;

    private String state="OFF";

    public TableFanView(Context context) {
        super(context);
        init();
    }

    public TableFanView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TableFanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void setState(String state){
        this.state = state;
        invalidate();
    }

    private void init(){
        this.bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fan_blade);
        setImageBitmap(bitmap);
//        this.bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fan_blade);
//        this.strokeWidthPx = (int) (STROKE_WIDTH_DP*getResources().getDisplayMetrics().density);
//        int halfStrokeWidthPx = strokeWidthPx / 2;
//
//        paintBorder = new Paint();
//        paintBorder.setStyle(Paint.Style.STROKE);
//        // Stroke width is in pixels.
//        paintBorder.setStrokeWidth(strokeWidthPx);
//        // Our color for the border.
//        paintBorder.setColor(Color.BLUE);
//
//        int totalWidth = bitmap.getWidth() + strokeWidthPx * 2;
//        int totalHeight = bitmap.getHeight()  + strokeWidthPx * 2;
//
//       this.rectF = new RectF(halfStrokeWidthPx,halfStrokeWidthPx,totalWidth-halfStrokeWidthPx,totalHeight-halfStrokeWidthPx);

    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

       /* canvas.drawRoundRect(rectF,40,40,paintBorder);
        Paint paint = new Paint();
        paint.setColor(Color.RED);*/
//        paint.setBlendMode(BlendMode.CLEAR);
//        paint.setShadowLayer(30, 10, 10, 0xFF999999);
       /* canvas.translate(canvas.getWidth()/2, canvas.getHeight()/2);
        // Rotate!
        canvas.rotate(rotation(3));
        // Put back to its original place.
        canvas.translate(-canvas.getWidth()/2, -canvas.getHeight()/2);*/
        // Invalidate the view.
//        canvas.save();
        if(this.state.equals("ON")){
            canvas.rotate(rotation(20),width/2f,height/2f);
            postInvalidateOnAnimation();
        }

        super.onDraw(canvas);
    }

    private int rotation(int delta){
        rotationDegrees = (rotationDegrees + delta) % 360;
        return rotationDegrees;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.width = getMeasuredWidth();
        this.height = getMeasuredHeight();

        setMeasuredDimension(this.width,this.height);
    }
}
