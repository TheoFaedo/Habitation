package com.example.habitation.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Habitation {

    private Piece pieceDepart;
    private HashMap<String, Piece> pieces;
    private String nom;

    public Habitation(){
        this.pieceDepart = null;
        this.pieces = new HashMap<>();
        this.nom = "maison";
    }

    public Piece getPremierePiece(){
        return pieceDepart;
    }

    public void ajouterPiece(Piece p){
        pieces.put(p.getNom(), p);
    }

    public HashMap<String, Piece> getPieces(){
        return pieces;
    }

    public Piece getPiece(String nomPiece){

    }

    public void save(Context context) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("nom", this.nom);
        json.put("pieces", this.pieces);
        json.put("pieceDepart", this.pieceDepart);
        try{
            FileOutputStream fos = context.openFileOutput("habitation.data", MODE_PRIVATE);
            fos.write(json.toString().getBytes(StandardCharsets.UTF_8));
            fos.flush();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public static Habitation load(Context context) throws JSONException {
        StringBuilder jsonStr = new StringBuilder();
        try {
            FileInputStream fis = context.openFileInput("habitation.data");
            int i;
            while ((i = fis.read()) != -1) {
                jsonStr.append((char) i);
            }
            fis.close();
        } catch (IOException e) {
            Toast.makeText(context, "erreur dans le chargement", Toast.LENGTH_LONG).show();
        }
        Toast.makeText(context, jsonStr.toString(), Toast.LENGTH_SHORT).show();
        JSONObject json = new JSONObject(jsonStr.toString());
        return new Habitation();
    }

}
