package com.example.habitation.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.example.habitation.R;

public class Canvas extends View{
    private Paint paint;
    private float orientationBousole;

    float time;

    public Canvas(Context context) {
        super(context);
        init();
    }

    public Canvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init(){
        paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        this.orientationBousole = 0;
    }

    @Override
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);

        float middlex = getWidth()/2;
        float middley = getHeight()/2;

        canvas.drawColor(Color.WHITE);
        paint.setColor(Color.GRAY);
        canvas.drawCircle(middlex, middley, middlex-10, paint);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(middlex, middley, middlex-20, paint);
        paint.setColor(Color.RED);


        float compasX = (float) -Math.sin(this.orientationBousole)*80;
        float compasY = (float) -Math.cos(this.orientationBousole)*80;
        canvas.drawLine(middlex, middley, middlex+(compasX*5), middley+(compasY*5), paint);

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.wind_rose);
        canvas.drawBitmap(bmp, 0, 0, null);
    }

    public void setOrientation(float or){
        this.orientationBousole = or;
    }
}
