package com.example.habitationapp.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

/**
 * Singleton permettant de gérer la navigation au sein d'une habitation
 */
public class GestionnaireNavigation {

    private static GestionnaireNavigation instance;

    private Habitation habitation; //Habitation actuellement visitée
    private Piece pieceActuelle; //Piece actuellement visitée
    private Facade facadeActuelle; //Facade actuellement regardée

    /**
     * Constructeur d'un GestionnaireNavigation appelé s'il n'en existe pas encore un
     * @param context
     */
    private GestionnaireNavigation(Context context){
        this.habitation = Habitation.load(context);
        if(this.habitation == null) this.habitation = new Habitation("undefined");
    }

    /**
     * Permet de récupérer l'instance de GestionnaireNavigation
     * @param context
     * @return l'instance de gestionnaire navigation
     */
    public static GestionnaireNavigation getInstance(Context context){
        if(instance == null) instance = new GestionnaireNavigation(context);
        return instance;
    }

    /**
     * Méthode permettant de se positionner à la pièce de départ de l'habitation
     */
    public void positionPieceDepart(){
        this.pieceActuelle = habitation.getPieceDepart();
        this.facadeActuelle = this.pieceActuelle.getFacadeNord();
    }

    /**
     * Méthode permettant d'actualiser l'image de la facade qui affichée
     * @param vueImage : vue dans laquelle l'image de la facade doit être actualisée
     * @param context : context
     */
    public void actualiserAffichage(View vueImage, Context context){
        ((ImageView)vueImage).setImageBitmap(Bitmap.createScaledBitmap(this.facadeActuelle.getBitmap(context), 1000, 1600, false));
    }

    /**
     * Méthode appelée pour passer la facade actuelle à celle de gauche et actualise en même temps l'affichage
     * @param context : context
     * @param imageActuelle : ImageView qu'on souhaitera actualiser
     */
    public void onGauche(Context context, ImageView imageActuelle){
        this.facadeActuelle = this.pieceActuelle.getGauche(this.facadeActuelle);
        actualiserAffichage(imageActuelle, context);
    }

    /**
     * Méthode appelée pour passer la facade actuelle à celle de gauche et actualise en même temps l'affichage
     * @param context : context
     * @param imageActuelle : ImageView qu'on souhaitera actualiser
     */
    public void onDroite(Context context, ImageView imageActuelle){
        this.facadeActuelle = this.pieceActuelle.getDroite(this.facadeActuelle);
        actualiserAffichage(imageActuelle, context);
    }

    /*******************************************************
     *
     *                  SETTERS
     *
     ******************************************************/

    public void setPieceActuelle(String nomDeLaPiece){
        this.pieceActuelle = this.habitation.getPiece(nomDeLaPiece);
        this.facadeActuelle = this.pieceActuelle.getFacadeNord();
    }

    public void setHabitation(Habitation hab){
        this.habitation = hab;
    }

    /*******************************************************
     *
     *                  GETTERS
     *
     ******************************************************/

    public Facade getFacadeActuelle(){
        return this.facadeActuelle;
    }

    public Habitation getHabitation() {
        return habitation;
    }
}
