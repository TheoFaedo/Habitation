package com.example.habitation.models;

import android.view.View;

public class GestionnaireNavigation {

    private static GestionnaireNavigation instance;

    private GestionnaireNavigation(){

    }

    public static GestionnaireNavigation getInstance(){
        if(instance == null) instance = new GestionnaireNavigation();
        return instance;
    }

    private Habitation habitation;
    private Piece pieceActuelle;
    private Facade facadeActuelle;

    public void actualiserAffichage(Facade facadeActuelle){

    }

    public void onGauche(View v){

    }

    public void onDroite(View v){

    }

    public Habitation getHabitation() {
        return habitation;
    }
}
