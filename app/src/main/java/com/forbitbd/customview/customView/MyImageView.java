package com.forbitbd.customview.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.forbitbd.customview.R;

public class MyImageView extends View {
    Bitmap icon = BitmapFactory.decodeResource(getResources(),
            R.drawable.instruction_1);

    private int width;

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       /* paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(80f);*/

        Rect source = new Rect(0, 0, icon.getWidth(), icon.getHeight());
        Rect bitmapRect = new Rect(200, 200, canvas.getWidth() -200,1000);
        canvas.drawBitmap(icon, source, bitmapRect, null);

       /* Log.d("HHHHH",icon.getWidth()+","+icon.getHeight());
        int height = icon.getHeight()*width/icon.getWidth();

        final int color = 0xff424242;

        Bitmap output = Bitmap.createBitmap(icon.getWidth(),
                icon.getHeight(), Bitmap.Config.ARGB_8888);

        final Rect rect = new Rect(0, 0, width, height);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(icon, null, rect, paint);
        *//*paint.setColor(color);
        canvas.drawCircle(width / 2, height / 2,
                width/6, paint);*//*

        canvas.clipRect(0,0,100,100);*/





//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
//        canvas.drawBitmap(icon,null,new RectF(0,0,width,height),null);
//        canvas.drawText("My Name",300,300,paint);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            canvas.clipOutRect(10,10,10,10);
        }*/

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.width = getMeasuredWidth();
        setMeasuredDimension(getMeasuredWidth(),getMeasuredHeight());
    }
}
