package com.example.habitation.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;
import androidx.annotation.Nullable;
import com.example.habitation.models.Piece;

import java.util.ArrayList;
import java.util.List;

public class CanvasPorte extends View {

    private Paint paint;

    private List<Porte> listePortes;

    private List<String> listeNomPieces;

    public CanvasPorte(Context context) {
        super(context);
        init();
    }

    public CanvasPorte(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public String clicked(float x, float y){
        int[] location = new int[2];
        getLocationInWindow(location);
        for(Porte p : listePortes){
            if(p.dansPorte(x+location[0], y-location[1])) return p.getNomPiece();
        }
        return "introuvable";
    }

    public void setListePieces(List<String> listePieces){
        this.listeNomPieces.clear();
        this.listeNomPieces.addAll(listePieces);
        invalidate();
    }

    private void init(){
        this.paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setTextSize(80);
        this.listePortes = new ArrayList<>();
        this.listeNomPieces = new ArrayList<>();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();

        if(!listeNomPieces.isEmpty()){
            paint.setColor(Color.rgb(0.5f,0.1f,0.1f));
            this.listePortes.clear();
            int n = listeNomPieces.size();
            for(int i=0; i<n; i++){
                float dist = width/(n+1);
                Porte p = new Porte(i*dist+dist, height/2, listeNomPieces.get(i));
                canvas.drawRect(p.getRect(), paint);
                listePortes.add(p);
            }
        }
    }

    private static class Porte{
        private final float x,y, tailleX, tailleY;
        private final String nomPiece;

        public Porte(float x,float y, String nomPiece){
            this.x = x;
            this.y = y;
            this.tailleX = 140;
            this.tailleY = 280;
            this.nomPiece = nomPiece;
        }

       public RectF getRect(){
            return new RectF(x-tailleX/2, y-tailleY/2, x+tailleX/2, y+tailleY/2);
       }

       public boolean dansPorte(float xPoint, float yPoint){
            return getRect().contains(xPoint, yPoint);
       }

       public String getNomPiece(){
            return this.nomPiece;
       }
    }
}
