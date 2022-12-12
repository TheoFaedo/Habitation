package com.example.habitation.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class CanvasBousole extends View{
    private Paint paint;
    private float orientationBousole;

    float time;

    public CanvasBousole(Context context) {
        super(context);
        init();
    }

    public CanvasBousole(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init(){
        paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setTextSize(80);
        this.orientationBousole = 1.5f;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);

        float middlex = getWidth()/2f;
        float middley = getHeight()/2f;

        paint.setColor(Color.GRAY);
        canvas.drawCircle(middlex, middley, middlex-60, paint);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(middlex, middley, middlex-70, paint);
        paint.setColor(Color.BLACK);
        canvas.drawLine(middlex, middley, middlex, middley-(middlex-70), paint);

        paint.setColor(Color.RED);

        float x = (float) -Math.sin(this.orientationBousole);
        float y = (float) -Math.cos(this.orientationBousole);

        x = x*(middlex-60);
        y = y*(middlex-60);

        canvas.drawLine(middlex, middley, middlex+x, middley+y, paint);

        paint.setColor(Color.RED);
        paint.setTextSize(38);

        @SuppressLint("DrawAllocation")
        Rect textBounds = new Rect();
        paint.getTextBounds("N", 0, 1, textBounds);
        canvas.drawText("N", middlex+(x*1.14f) - textBounds.exactCenterX(), middley+(y*1.14f) - textBounds.exactCenterY(), paint);


        Paint paint2 = new Paint();
        paint2.setTextSize(46);
        paint2.setColor(Color.WHITE);
        canvas.drawCircle(middlex, middley, middlex*0.25f, paint2);

        paint2.setColor(Color.BLACK);
        textBounds = new Rect();
        paint2.getTextBounds(getPointCardinalActuel(), 0, getPointCardinalActuel().length(), textBounds);
        canvas.drawText(getPointCardinalActuel(), middlex - textBounds.exactCenterX(), middley - textBounds.exactCenterY(), paint2);
    }

    public void setOrientation(float or){
        this.orientationBousole = or;
    }

    public String getPointCardinalActuel(){
        double degrees = Math.toDegrees(this.orientationBousole);
        if(degrees>=45 && degrees<135){
            return "Est";
        }else if(degrees>=135 || degrees<=-135){
            return "Sud";
        }else if(degrees<-45 && degrees>-135){
            return "Ouest";
        }else{
            return "Nord";
        }
    }
}
