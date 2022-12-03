package com.example.habitation.models;

import android.content.Context;
import android.view.View;

public class GestionnaireNavigation {

    private static GestionnaireNavigation instance;

    private Habitation habitation;
    private Piece pieceActuelle;
    private Facade facadeActuelle;

    private GestionnaireNavigation(Context context){
        this.habitation = Habitation.load(context);
    }

    public static GestionnaireNavigation getInstance(Context context){
        if(instance == null) instance = new GestionnaireNavigation(context);
        return instance;
    }

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
