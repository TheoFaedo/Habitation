package com.example.habitation.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Facade implements ConvertibleJSON{

    private String refImage; //Lorsque qu'on prends la photo, on la stocke avec un certains nom, (<nompiece><orientationFacade>) qui correspond donc à cet attribut
    private List<Piece> piecesAdjacentes;

    public Facade(String refImage){
        this.refImage = refImage;
        this.piecesAdjacentes = new ArrayList<Piece>();
    }

    public List<Piece> getPieceAdjacentes(){
        return piecesAdjacentes;
    }

    public void ajouterPieceAdjacente(Piece p){
        this.piecesAdjacentes.add(p);
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        JSONObject jsonPieces = new JSONObject();

        int i = 0;
        for(Piece p : piecesAdjacentes){
            jsonPieces.put(i+"", p.getNom());
            i++;
        }

        json.put("image", refImage);

        return json;
    }
}
