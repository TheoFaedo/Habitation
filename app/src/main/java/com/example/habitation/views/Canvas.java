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
    private Bitmap image;

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
        paint.setTextSize(80);
        this.orientationBousole = -2.5f;
        this.image = BitmapFactory.decodeResource(getResources(), R.drawable.wind_rose);
    }

    @Override
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);

        float middlex = getWidth()/2;
        float middley = getHeight()/2;

        canvas.drawColor(Color.WHITE);
        paint.setColor(Color.GRAY);
        canvas.drawCircle(middlex, middley, middlex-80, paint);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(middlex, middley, middlex-90, paint);
        paint.setColor(Color.BLACK);
        canvas.drawLine(middlex, middley, middlex, middley-(middlex-90), paint);

        Rect textBounds = new Rect();
        paint.getTextBounds(getPointCardinalActuel()+"", 0, 1, textBounds);
        canvas.drawText(getPointCardinalActuel()+"", middlex - textBounds.exactCenterX(), middley - middlex - textBounds.exactCenterY(), paint);


        paint.setColor(Color.RED);

        float x = (float) -Math.sin(this.orientationBousole);
        float y = (float) -Math.cos(this.orientationBousole);

        x = x*(middlex-90);
        y = y*(middlex-90);

        canvas.drawLine(middlex, middley, middlex+x, middley+y, paint);



        paint.setTextSize(50);
        textBounds = new Rect();
        paint.getTextBounds("N", 0, 1, textBounds);
        canvas.drawText("N", middlex+(x*1.2f) - textBounds.exactCenterX(), middley+(y*1.2f) - textBounds.exactCenterY(), paint);
    }

    public void setOrientation(float or){
        this.orientationBousole = or;
    }

    public char getPointCardinalActuel(){
        double degrees = Math.toDegrees(this.orientationBousole);
        if(degrees>=45 && degrees<135){
            return 'E';
        }else if(degrees>=135 || degrees<=-135){
            return 'S';
        }else if(degrees<-45 && degrees>-135){
            return 'O';
        }else{
            return 'N';
        }
    }
}
