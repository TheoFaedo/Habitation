package com.example.habitation.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Habitation {

    private Piece pieceDepart;
    private List<Piece> pieces;
    private String nom;

    public Habitation(){
        this.pieceDepart = null;
        this.pieces = new ArrayList<>();
        this.nom = "maison";
    }

    public Piece getPremierePiece(){
        return pieceDepart;
    }

    public void ajouterPiece(Piece p){
        pieces.add(p);
    }

    public List<Piece> getPieces(){
        return pieces;
    }

    public void save(Context context){

    }

    public static Habitation load(Context context){
        try {
            FileInputStream fis = context.openFileInput("habitation.data");

            Toast.makeText(context, fis.toString(), Toast.LENGTH_SHORT).show();
            fis.close();
        } catch (IOException e) {
            Toast.makeText(context, "erreur dans le chargement", Toast.LENGTH_SHORT).show();
        }
        return new Habitation();
    }

}
