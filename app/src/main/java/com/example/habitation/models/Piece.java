package com.example.habitation.models;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Piece implements ConvertibleJSON{

    private String nom;
    private Facade[] facades; //Ordre 0:Nord 1:Est 2:Sud 3:Ouest

    public Piece(Facade[] facades, String nom) {
        this.nom = nom;
        this.facades = facades;
    }

    public Piece(){

    }
    public String getNom(){
        return this.nom;
    }

    public Facade getGauche(Facade f){
        return null;
    }

    public Facade getDroite(Facade f){
        return null;
    }

    public Facade getFacadeNord(){
        return facades[0];
    }

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

    public static Piece jsonToPiece(JSONObject jsonObject) throws JSONException {
        String nom = (String) jsonObject.get("nompiece");
        Facade[] facades = new Facade[4];

        JSONArray array = (JSONArray) jsonObject.get("facades");
        for(int i = 0; i<array.length(); i++) facades[i] = Facade.jsonToFacade(array.getJSONObject(i));

        return new Piece(facades, nom);
    }
}
