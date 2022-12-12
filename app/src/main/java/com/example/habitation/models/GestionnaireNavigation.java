package com.example.habitation.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

public class GestionnaireNavigation {

    private static GestionnaireNavigation instance;

    private Habitation habitation;
    private Piece pieceActuelle;
    private Facade facadeActuelle;

    private GestionnaireNavigation(Context context){
        this.habitation = Habitation.load(context);
        if(this.habitation == null) this.habitation = new Habitation("undefined");
    }

    public static GestionnaireNavigation getInstance(Context context){
        if(instance == null) instance = new GestionnaireNavigation(context);
        return instance;
    }

    public void positionPieceDepart(){
        this.pieceActuelle = habitation.getPieceDepart();
        this.facadeActuelle = this.pieceActuelle.getFacadeNord();
    }

    public void actualiserAffichage(View vueImage, Context context){
        ((ImageView)vueImage).setImageBitmap(Bitmap.createScaledBitmap(this.facadeActuelle.getBitmap(context), 1000, 1600, false));
    }

    public void onGauche(View v, Context context, ImageView imageActuelle){
        this.facadeActuelle = this.pieceActuelle.getGauche(this.facadeActuelle);
        actualiserAffichage(imageActuelle, context);
    }

    public void onDroite(View v, Context context, ImageView imageActuelle){
        this.facadeActuelle = this.pieceActuelle.getDroite(this.facadeActuelle);
        actualiserAffichage(imageActuelle, context);
    }

    public void setPieceActuelle(String nomDeLaPiece){
        this.pieceActuelle = this.habitation.getPiece(nomDeLaPiece);
        this.facadeActuelle = this.pieceActuelle.getFacadeNord();
    }

    public Facade getFacadeActuelle(){
        return this.facadeActuelle;
    }

    public Habitation getHabitation() {
        return habitation;
    }

    public void setHabitation(Habitation hab){
        this.habitation = hab;
    }
}
