package com.example.habitation.models;

import java.util.List;

public class Habitation {

    private Piece pieceDepart;
    private List<Piece> pieces;

    public Piece getPremierePiece(){
        return pieceDepart;
    }

    public void ajouterPiece(Piece p){
        pieces.add(p);
    }

    public List<Piece> getPieces(){
        return pieces;
    }

    public void save(){

    }

    public static Habitation load(){
        return null;
    }

}
