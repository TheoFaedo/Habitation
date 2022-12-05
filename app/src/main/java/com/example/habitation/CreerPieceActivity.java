package com.example.habitation;

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
import android.widget.Button;
import android.widget.ImageView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.habitation.models.Facade;
import com.example.habitation.models.GestionnaireNavigation;
import com.example.habitation.models.Habitation;
import com.example.habitation.models.Piece;
import com.example.habitation.views.Canvas;
import com.google.android.material.textfield.TextInputLayout;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class CreerPieceActivity extends AppCompatActivity implements SensorEventListener {

    private ActivityResultLauncher<Intent> launcher;

    //Views
    private TextInputLayout inputNomPiece;

    private Button boutonConfirm;

    //Proprietes photo
    private Bitmap[] bitmapTable;
    private Bitmap noImage;
    private String orientationPhoto;

    //Capteurs
    private SensorManager sm;
    private Sensor sAccelerometre;
    private Sensor sMagnetometre;
    private float[] magneticVector = new float[3];
    private float[] accelerometerVector = new float[3];

    private long interval;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        noImage = BitmapFactory.decodeResource(getResources(), R.drawable.no_photo);

        bitmapTable = new Bitmap[]{
                null,
                null,
                null,
                null};

        setContentView(R.layout.activity_creer_piece);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sAccelerometre = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sMagnetometre = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sm.registerListener(this, sAccelerometre, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this, sMagnetometre, SensorManager.SENSOR_DELAY_NORMAL);


        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result!=null){
                        //récupération de l'image
                        Bundle extra = result.getData().getExtras();
                        Bitmap image = (Bitmap) extra.get("data");

                        //ajout dans le tableau
                        bitmapTable[pointCardToInt(this.orientationPhoto)] = image;
                    }
                }
        );

        inputNomPiece = findViewById(R.id.nom_piece_input);
        boutonConfirm = findViewById(R.id.button_confirm);

        boutonConfirm.setEnabled(false);
        interval = System.currentTimeMillis();
    }

    public void OnPhotoClick(View v){
        Canvas canvas = findViewById(R.id.canvas_bousole);
        orientationPhoto = canvas.getPointCardinalActuel();
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        launcher.launch(i);
    }

    public void OnConfirmClick(View v){
        String nomPiece = "Piece lambda";
        nomPiece = Objects.requireNonNull(Objects.requireNonNull(inputNomPiece.getEditText()).getText()).toString();
        Facade[] facadeActu = new Facade[4];
        for(int i =0; i< facadeActu.length; i++){
            facadeActu[i] = new Facade(nomPiece+"_"+i);
        }
        enregistrerTableauBitmap(nomPiece);
        Piece p = new Piece(facadeActu, nomPiece);
        Habitation hab = GestionnaireNavigation.getInstance(this).getHabitation();
        hab.ajouterPiece(p);
        if(hab.getPieceDepart()==null) hab.setPieceDepart(p);
        hab.save(this);

        finish();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent){

        Canvas canvas = findViewById(R.id.canvas_bousole);

        float[] resultMatrix = new float[9];
        float[] values = new float[3];


        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometerVector= sensorEvent.values;

        } else if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magneticVector= sensorEvent.values;
        }

        SensorManager.getRotationMatrix(resultMatrix, null, accelerometerVector, magneticVector);
        SensorManager.getOrientation(resultMatrix, values);

        if((System.currentTimeMillis() - interval)>30) {
            canvas.setOrientation(values[0]);
            canvas.invalidate();
            refreshImage(canvas.getPointCardinalActuel());
            setActivationDuBouton();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void refreshImage(String pointCard){
        ImageView imageActu = findViewById(R.id.imageView);
        Bitmap bm = bitmapTable[pointCardToInt(pointCard)];
        if(bm==null) bm = BitmapFactory.decodeResource(getResources(), R.drawable.no_photo);
        imageActu.setImageBitmap(bm);
    }

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

    private boolean verifSiToutePhotosPrises(){
        for(Bitmap b : bitmapTable){
            if(b==null) return false;
        }
        return true;
    }

    private void enregistrerBitmap(Bitmap b, String nomImage){
        //compréssion et stockage de l'image (tester).
        try{
            FileOutputStream fos = openFileOutput(nomImage+".data", MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.PNG, 100, fos); //On compresse l'image récupérée
            fos.flush();
            fos.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void enregistrerTableauBitmap(String nomPiece){
        for(int i=0; i<bitmapTable.length; i++){
            enregistrerBitmap(bitmapTable[i], nomPiece+"_"+i);
        }
    }

    private void setActivationDuBouton(){
        if(verifSiToutePhotosPrises() && !Objects.requireNonNull(inputNomPiece.getEditText()).toString().equals("")){
            boutonConfirm.setEnabled(true);
        }else{
            boutonConfirm.setEnabled(false);
        }
    }

}