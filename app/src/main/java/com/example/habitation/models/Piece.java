package com.example.habitation.models;

public class Piece {

    private String nom;
    private Facade[] facades;

    public Piece(Facade[] facades, String nom) {
        this.facades = facades;
    }

    public Piece(){

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
}
