package com.example.habitation.models;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class Piece implements ConvertibleJSON{

    private String nom;
    private Facade[] facades; //Ordre 0:Nord 1:Est 2:Sud 3:Ouest

    public Piece(Facade[] facades, String nom) {
        this.nom = nom;
        this.facades = facades;
    }

    public Piece(String nom){
        this.nom = nom;
        this.facades = new Facade[]{new Facade("this.nom" + 0), new Facade("this.nom" + 1), new Facade("this.nom" + 2), new Facade("this.nom" + 3)};
    }

    public Piece(){
        this.facades = new Facade[4];
    }
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
