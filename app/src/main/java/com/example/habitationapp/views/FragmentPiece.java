package com.example.habitationapp.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import androidx.annotation.Nullable;

public class FragmentPiece extends androidx.appcompat.widget.AppCompatTextView {

    boolean pieceActive;

    public FragmentPiece(Context context, String nomPiece) {
        super(context);

        pieceActive = false;
        setText(nomPiece);
        setTextColor(Color.BLACK);
        setTextAlignment(TEXT_ALIGNMENT_CENTER);

        setOnClickListener(view -> toggleActive());
    }

    public FragmentPiece(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        pieceActive = false;
        setText("piecedeouf");
    }

    public void toggleActive(){
        if(pieceActive){
            pieceActive = false;
            setBackgroundColor(Color.valueOf(0xf1f1f1).toArgb());
        }
        else{
            pieceActive = true;
            setBackgroundColor(Color.GRAY);
        }
    }

    public boolean isPieceActive(){
        return pieceActive;
    }

    public void setInactive(){
        if(pieceActive) toggleActive();
    }

    public void setActive(){
        if(!pieceActive) toggleActive();
    }
}
