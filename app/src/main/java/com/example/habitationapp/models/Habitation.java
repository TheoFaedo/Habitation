package com.example.habitationapp.models;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Classe représentant une Habitation composée de pièces
 */
public class Habitation {

    private Piece pieceDepart; //Pièce dans laquelle on commence la visite
    private HashMap<String, Piece> pieces; //Dictionnaire qui contenant pour un nom de pièce, sa pièce correpondante
    private String nom; //Nom de l'habitation

    /**
     * Constructeur d'une habitation à partir de son nom
     * @param nom : nom de l'habitation
     */
    public Habitation(String nom){
        this.pieces = new HashMap<>();
        this.nom = nom;
        this.pieceDepart = null;
    }

    /**
     * Méthode permettant d'ajouter une pièce à l'Habitation
     * @param p : Pièce qu'on souhaite ajouter
     */
    public void ajouterPiece(Piece p){
        pieces.put(p.getNom(), p);
    }

    /**
     * Permet de sauvegarder au sein d'un fichier au format JSON une instance d'Habitation
     * @param context
     */
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

            String nomPieceDepart;
            if(this.pieceDepart==null){
                nomPieceDepart = "undefined";
            }else{
                nomPieceDepart = this.pieceDepart.getNom();
            }
            json.put("pieceDepart", nomPieceDepart);

            //Ecriture des données dans un fichier
            FileOutputStream fos = context.openFileOutput("habitation.data", MODE_PRIVATE);
            fos.write(json.toString().getBytes(StandardCharsets.UTF_8));
            fos.flush();
            fos.close();
            Toast.makeText(context, "sauvegarde", Toast.LENGTH_LONG).show();
        }catch(IOException | JSONException e){
            Toast.makeText(context, "erreur dans la sauvegarde", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Permet de charger un fichier contenant une habitation au format JSON et de rendre une instance correspondante
     * @param context
     * @return l'instance de l'habitation qui a été chargée
     */
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
            return null;
        }
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return this.nom;
    }

    /*******************************************************
     *
     *                  GETTERS
     *
     ******************************************************/


    public HashMap<String, Piece> getPieces(){
        return pieces;
    }

    /**
     * Permet de récupérer une pièce à partir de son nom
     * @param nomPiece : nom de la Pièce qu'on souhaite récupérer
     * @return la Pièce correpondante
     */
    public Piece getPiece(String nomPiece){
        return this.pieces.get(nomPiece);
    }

    public Piece getPieceDepart(){
        return this.pieceDepart;
    }

    /*******************************************************
     *
     *                  SETTERS
     *
     ******************************************************/

    public void setPieceDepart(Piece pieceDep){
        this.pieceDepart = pieceDep;
    }

    public void setNom(String nom){
        this.nom = nom;
    }
}
