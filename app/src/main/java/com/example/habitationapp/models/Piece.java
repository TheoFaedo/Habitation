package com.example.habitationapp.models;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class Piece implements ConvertibleJSON{

    private String nom; //Nom de la Pièce
    private Facade[] facades; //Tableau des 4 facades de la pièce.   Ordre 0:Nord 1:Est 2:Sud 3:Ouest

    /**
     * Constructeur d'une Pièce à partir du tableau de ses facades et de son nom
     * @param facades : tableau contenant les 4 facades de la pièce
     * @param nom : nom de la pièce
     */
    public Piece(Facade[] facades, String nom) {
        this.nom = nom;
        this.facades = facades;
    }

    /**
     * Méthode permettant de convertir une instance de Pièce au format JSON
     * @return cette instance de Pièce au format JSON
     * @throws JSONException
     */
    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        JSONArray jsonFacades = new JSONArray();

        Log.i("test", this.nom+"");
        for (Facade facade : facades) {
            jsonFacades.put(facade.toJSON());
        }

        json.put("nompiece", this.nom);
        json.put("facades", jsonFacades);

        return json;
    }

    /**
     * Méthode permettant de rendre une instance de Pièce à partir d'une facade au format JSON
     * @param jsonObject : JSON d'une Pièce
     * @return une instance de Pièce correspondant au JSON fournis
     * @throws JSONException
     */
    public static Piece jsonToPiece(JSONObject jsonObject) throws JSONException {
        String nom = (String) jsonObject.get("nompiece");
        Facade[] facades = new Facade[4];

        JSONArray array = (JSONArray) jsonObject.get("facades");
        for(int i = 0; i<array.length(); i++) facades[i] = Facade.jsonToFacade(array.getJSONObject(i));

        return new Piece(facades, nom);
    }

    /*******************************************************
     *
     *                  GETTERS
     *
     ******************************************************/

    public String getNom(){
        return this.nom;
    }

    public Facade getGauche(Facade f){
        List<Facade> facadeList = Arrays.asList(this.facades);
        int indexFacade = facadeList.indexOf(f);

        if(indexFacade==0) indexFacade = 3;
        else indexFacade--;
        return facadeList.get(indexFacade);
    }

    public Facade getDroite(Facade f){
        List<Facade> facadeList = Arrays.asList(this.facades);
        int indexFacade = facadeList.indexOf(f);

        if(indexFacade==3) indexFacade = 0;
        else indexFacade++;
        return facadeList.get(indexFacade);
    }

    public Facade getFacadeNord(){
        return facades[0];
    }

}
