package com.example.habitation.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Facade implements ConvertibleJSON{

    private String refImage; //Lorsque qu'on prends la photo, on la stocke avec un certains nom, (<nompiece><orientationFacade>) qui correspond donc à cet attribut
    private List<String> piecesAdjacentes; //On enregristre le nom de chaques pieces

    public Facade(String refImage){
        this.refImage = refImage;
        this.piecesAdjacentes = new ArrayList<>();
    }

    public Facade(String refImage, List<String> listeNomPieces){
        this.refImage = refImage;
        this.piecesAdjacentes = listeNomPieces;
    }

    public List<String> getPieceAdjacentes(){
        return piecesAdjacentes;
    }

    public void ajouterPieceAdjacente(Piece p){
        this.piecesAdjacentes.add(p.getNom());
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        JSONArray jsonPieces = new JSONArray();

        for(String nom : piecesAdjacentes){
            jsonPieces.put(nom);
        }

        json.put("image", refImage);
        json.put("pieces", jsonPieces);

        return json;
    }

    public static Facade jsonToFacade(JSONObject jsonObject) throws JSONException {
        String nomimage = jsonObject.getString("image");
        JSONArray jsonPieces = jsonObject.getJSONArray("pieces");

        List<String> liste = new ArrayList<>();
        for(int i = 0; i<jsonPieces.length(); i++)  liste.add((String) jsonPieces.get(i));

        return new Facade(nomimage, liste);
    }
}
