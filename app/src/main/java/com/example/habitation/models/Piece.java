package com.example.habitation.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Piece implements ConvertibleJSON<Piece>{

    private String nom;
    private Facade[] facades; //Ordre 0:Nord 1:Est 2:Sud 3:Ouest

    public Piece(Facade[] facades, String nom) {
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

        for (Facade facade : facades) {
            jsonFacades.put(facade.toJSON());
        }

        json.put("nompiece", this.nom);
        json.put("facades", jsonFacades);

        return json;
    }

    @Override
    public Piece toObject(JSONObject jsonObject) throws JSONException {
        this.nom = (String) jsonObject.get("nompiece");
        this.facades = new Facade[4];
        JSONArray array = (JSONArray) jsonObject.get("facades");
        return null;
    }
}
