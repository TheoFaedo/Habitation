package com.example.habitation.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CpuUsageInfo;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
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

    public Habitation(String nom){
        this.pieces = new HashMap<>();
        this.nom = nom;
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
        return this.pieces.get(nomPiece);
    }

    public Piece getPieceDepart(){
        return this.pieceDepart;
    }

    public void setPieceDepart(Piece pieceDep){
        this.pieceDepart = pieceDep;
    }

    public void save(Context context) {
        try{
            //Initialisation des objets JSON
            JSONObject json = new JSONObject();
            JSONArray arrayPieces = new JSONArray();

            //Ajout de chaques pièces dans l'array JSON
            for(Piece piece : this.pieces.values()){
                Log.i("test2", piece.getNom()+"");
                arrayPieces.put(piece.toJSON());
            }

            //Ajout de chaques propriété dans le champs correspondant à son nom;
            json.put("nom", this.nom);
            json.put("pieces", arrayPieces);
            json.put("pieceDepart", this.pieceDepart.getNom());

            //Ecriture des données dans un fichier
            FileOutputStream fos = context.openFileOutput("habitation.data", MODE_PRIVATE);
            fos.write(json.toString().getBytes(StandardCharsets.UTF_8));
            fos.flush();
            fos.close();
        }catch(IOException | JSONException e){
            Toast.makeText(context, "erreur dans la sauvegarde", Toast.LENGTH_LONG).show();
        }
    }

    public static Habitation load(Context context) {
        StringBuilder jsonStr = new StringBuilder();
        try {

            //Lecture du fichier contenant les informations de l'habitation
            FileInputStream fis = context.openFileInput("habitation.data");
            int i;
            while ((i = fis.read()) != -1) {
                jsonStr.append((char) i);
            }
            fis.close();

            //Transformation du JSON en Objets
            JSONObject json = new JSONObject(jsonStr.toString());

            String nom = json.getString("nom");

            Habitation hab = new Habitation(nom);
            JSONArray array = json.getJSONArray("pieces");
            for(int j = 0; j<array.length(); j++) hab.ajouterPiece(Piece.jsonToPiece(array.getJSONObject(j)));

            String pieceDep = json.getString("pieceDepart");
            hab.setPieceDepart(hab.getPiece(pieceDep));

            return hab;

        } catch (IOException | JSONException e) {
            Toast.makeText(context, "erreur dans le chargement", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return this.nom;
    }
}
