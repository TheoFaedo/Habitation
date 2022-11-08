package com.example.habitation.models;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Facade {

    private Bitmap image;
    private List<Piece> piecesAdjacentes;

    public Facade(Bitmap image){
        this.image = image;
        this.piecesAdjacentes = new ArrayList<Piece>();
    }

    public List<Piece> getPieceAdjacentes(){
        return piecesAdjacentes;
    }

    public void ajouterPieceAdjacente(Piece p){
        this.piecesAdjacentes.add(p);
    }
}
