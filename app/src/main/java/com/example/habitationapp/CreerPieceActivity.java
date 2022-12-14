package com.example.habitationapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.habitationapp.models.Facade;
import com.example.habitationapp.models.GestionnaireNavigation;
import com.example.habitationapp.models.Habitation;
import com.example.habitationapp.models.Piece;
import com.example.habitationapp.views.CanvasBousole;
import com.example.habitationapp.views.FragmentPiece;
import com.google.android.material.textfield.TextInputLayout;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class CreerPieceActivity extends AppCompatActivity implements SensorEventListener {

    private ActivityResultLauncher<Intent> launcher;

    //Views
    private TextInputLayout inputNomPiece;
    private Button boutonConfirm;
    private LinearLayout listePiecesPortes;

    private CanvasBousole canvas;

    //Proprietes photo
    private Bitmap[] bitmapTable;
    private Bitmap noImage; //Image affichée par default si la photo n'a pas encore été prise
    private String orientationPhoto;

    //Capteurs
    private SensorManager sm;
    private Sensor sAccelerometre;
    private Sensor sMagnetometre;
    private float[] magneticVector = new float[3];
    private float[] accelerometerVector = new float[3];

    //Liste dont chaque index correpond à une facade et dont le contenu est la liste des pièce asjacentes à cette facade.
    private List<List<String>> listePortesParFacade; //0: facade Nord, 1: facade Est, ect...

    private long interval; //attribut qui permet de stocke un temps d'en déduire un interval afin de régler le taux de raffraichissement de la bousole

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initalisation de l'image par default
        noImage = BitmapFactory.decodeResource(getResources(), R.drawable.no_photo);

        //Initialisation du tableau des images
        bitmapTable = new Bitmap[]{
                null,
                null,
                null,
                null};


        //Initialisation de la liste des pièces adjacentes pour chacunes des 4 facades.
        listePortesParFacade = new ArrayList<>();
        for(int i=0; i<4; i++) listePortesParFacade.add(new ArrayList<>());

        //Initialisation du layout
        setContentView(R.layout.activity_creer_piece);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Initialisation des capteurs
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sAccelerometre = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sMagnetometre = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        //Enregistrement des capteurs
        sm.registerListener(this, sAccelerometre, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this, sMagnetometre, SensorManager.SENSOR_DELAY_NORMAL);

        //Launcher d'une activité de prise photo
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result!=null){
                        //récupération de l'image
                        Bundle extra = result.getData().getExtras();
                        Bitmap image = (Bitmap) extra.get("data");

                        //ajout dans le tableau
                        bitmapTable[pointCardToInt(this.orientationPhoto)] = image;
                        refreshImage(canvas.getPointCardinalActuel());
                    }
                }
        );

        //Assignation des vues
        inputNomPiece = findViewById(R.id.nom_piece_input);
        boutonConfirm = findViewById(R.id.button_confirm);
        listePiecesPortes = findViewById(R.id.listePiecesPorte);

        //On désactive le bouton de confirmation au départ
        boutonConfirm.setEnabled(false);

        //On initialise l'interval au temps actuel
        interval = System.currentTimeMillis();

        //On initialise la liste des portes possibles pour la facade
        HashMap<String, Piece> hashMapPieces = GestionnaireNavigation.getInstance(this).getHabitation().getPieces();
        if(!(hashMapPieces == null)){
            Set<String> nomPieces = hashMapPieces.keySet(); //On récupère le nom de toutes les pièce présentes dans l'habitation
            for(String s : nomPieces){
                listePiecesPortes.addView(new FragmentPiece(this, s)); //À chaque nom on ajoute une porte à la liste
            }
        }
        canvas = findViewById(R.id.canvas_bousole);
    }

    public void OnPhotoClick(View v){
        CanvasBousole canvas = findViewById(R.id.canvas_bousole);
        orientationPhoto = canvas.getPointCardinalActuel();
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        launcher.launch(i);
    }

    public void OnConfirmClick(View v){

        //Definition du nom de la pièce
        String nomPiece = "Piece lambda";
        nomPiece = Objects.requireNonNull(Objects.requireNonNull(inputNomPiece.getEditText()).getText()).toString();

        //Création des facades de la pièce
        Facade[] facadeActu = new Facade[4];
        for(int i =0; i< facadeActu.length; i++){
            facadeActu[i] = new Facade(nomPiece+"_"+i, this.listePortesParFacade.get(i));
        }

        //On enregistre les photo qui on été prise dans le téléphone
        enregistrerTableauBitmap(nomPiece);

        //On ajoute la pièce et ses facades à l'habitation
        Piece p = new Piece(facadeActu, nomPiece);
        Habitation hab = GestionnaireNavigation.getInstance(this).getHabitation();
        hab.ajouterPiece(p);
        hab.setPieceDepart(p);
        hab.setNom("maison");

        //On sauvegarde l'habitation
        hab.save(this);

        finish();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
        float[] resultMatrix = new float[9];
        float[] values = new float[3];

        //En fonction du sensor on assigne les valeurs aux attributs correspondants
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometerVector= sensorEvent.values;

        } else if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magneticVector= sensorEvent.values;
        }

        //On manipule les donnée afin de trouver l'orientation du téléphone
        SensorManager.getRotationMatrix(resultMatrix, null, accelerometerVector, magneticVector);
        SensorManager.getOrientation(resultMatrix, values);

        if((System.currentTimeMillis() - interval)>30) { //Tout les 30 millisecondes
            String oldOrientation = canvas.getPointCardinalActuel();
            canvas.setOrientation(values[0]);
            canvas.invalidate();
            if(!oldOrientation.equals(canvas.getPointCardinalActuel())){ //Si le point cardinal a changée
                remplirListePorteFacade(oldOrientation);
                refreshImage(canvas.getPointCardinalActuel());
                actualiserAffichagePortes(canvas.getPointCardinalActuel());
            }
            setActivationDuBouton();
            interval = System.currentTimeMillis();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /**
     * Méthode permettant d'actualiser l'image affichée au centre de la fenetre en fonction de l'orientation du telephone
     * @param pointCard : orientation actuelle du téléphone;
     */
    private void refreshImage(String pointCard){
        ImageView imageActu = findViewById(R.id.imageView);
        Bitmap bm = bitmapTable[pointCardToInt(pointCard)];

        if(bm==null) bm = noImage; //Si il n'y a pas d'image on mets l'image par default
        imageActu.setImageBitmap(bm);
    }

    /**
     * Méthode permettant de convertir le nom d'un point cardinal en son index dans les tableaux de l'application
     * @param pointCard : point cardinal à convertir
     * @return l'index correspondant à ce point cardinal
     */
    private int pointCardToInt(String pointCard){
        switch (pointCard){
            case "Nord":
                return 0;
            case "Est":
                return 1;
            case "Sud":
                return 2;
            case "Ouest":
                return 3;
        }
        return -1;
    }

    /**
     * Méthode qui vérifie si toutes les facades ont bien une photo associée
     * @return true si c'est le cas, false sinon
     */
    private boolean verifSiToutePhotosPrises(){
        for(Bitmap b : bitmapTable){
            if(b==null) return false;
        }
        return true;
    }

    /**
     * Méthode permettant d'enregistrer les image sur le téléphone afin de pouvoir les retrouvée par la suite via leurs nom
     * @param b : Image à enregistrer
     * @param nomImage : nom du fichier dans lequel l'image est encodée
     */
    private void enregistrerBitmap(Bitmap b, String nomImage){
        //compréssion et stockage de l'image (tester).
        try{
            FileOutputStream fos = openFileOutput(nomImage+".data", MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.PNG, 10, fos); //On compresse l'image récupérée
            fos.flush();
            fos.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Permet d'enregistrer les photos de chaques facades dans le téléphone
     * @param nomPiece : nom de la pièce à laquelle les facades appartiennent
     */
    private void enregistrerTableauBitmap(String nomPiece){
        for(int i=0; i<bitmapTable.length; i++){
            enregistrerBitmap(bitmapTable[i], nomPiece+"_"+i);
        }
    }

    /**
     * Méthode permettant d'activer le bouton de confirmation en verifiant si le nom de la pièce à bien été entré et si toutes les photos ont bien été prises
     */
    private void setActivationDuBouton(){
        if(verifSiToutePhotosPrises() && !Objects.requireNonNull(inputNomPiece.getEditText()).toString().equals("")){
            boutonConfirm.setEnabled(true);
        }else{
            boutonConfirm.setEnabled(false);
        }
    }

    /**
     * Méthodes qui actualise l'affichage du nom des pieces dans le linearlayout
     */
    private void actualiserAffichagePortes(String pointCard){
        List<String> listePortesDeLaFacadeActuelle = listePortesParFacade.get(pointCardToInt(pointCard));
        for(int i=0; i<listePiecesPortes.getChildCount(); i++) {
            FragmentPiece fragmentPieceActu = (FragmentPiece) listePiecesPortes.getChildAt(i);

            if(listePortesDeLaFacadeActuelle.contains(fragmentPieceActu.getText())) fragmentPieceActu.setActive();
            else fragmentPieceActu.setInactive();
        }
    }

    /**
     *  Méthode qui definis l'activation des différentes portes dans l'affichage en fonction de la facade actuelle
     */
    private void remplirListePorteFacade(String pointCard){
        List<String> listePortesDeLaFacadeActuelle = listePortesParFacade.get(pointCardToInt(pointCard));
        listePortesDeLaFacadeActuelle.clear();

        for(int i=0; i<listePiecesPortes.getChildCount(); i++) {
            FragmentPiece fragmentPieceActu = (FragmentPiece) listePiecesPortes.getChildAt(i);
            if(fragmentPieceActu.isPieceActive()) listePortesDeLaFacadeActuelle.add((String) fragmentPieceActu.getText());
        }
    }

}