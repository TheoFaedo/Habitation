package com.example.habitation;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.habitation.models.Facade;
import com.example.habitation.models.GestionnaireNavigation;
import com.example.habitation.models.Piece;
import com.example.habitation.views.Canvas;
import com.google.android.material.textfield.TextInputLayout;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CreerPieceActivity extends AppCompatActivity implements SensorEventListener {

    private ActivityResultLauncher<Intent> launcher;
    private Facade[] facadeActu;

    private int numFacadeActu;

    private TextInputLayout inputNomPiece;

    private List<ImageView> images;
    private ImageView imageActu;

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
        setContentView(R.layout.activity_creer_piece);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sAccelerometre = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sMagnetometre = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sm.registerListener(this, sAccelerometre, SensorManager.SENSOR_DELAY_FASTEST);
        sm.registerListener(this, sMagnetometre, SensorManager.SENSOR_DELAY_FASTEST);


        numFacadeActu = 0;
        facadeActu = new Facade[4];
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //récupération de l'image
                    Bundle extra = result.getData().getExtras();
                    Bitmap image = (Bitmap) extra.get("data");

                    //ajout dans le tableau
                    imageActu.setImageBitmap(image);
                }
        );
        inputNomPiece = findViewById(R.id.nom_piece_input);

        interval = System.currentTimeMillis();
    }

    public void OnPhotoClick(View v){
        imageActu = (ImageView) v;
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        launcher.launch(i);
    }

    public void OnConfirmClick(View v){
        String nomPiece = "Piece lambda";
        nomPiece = Objects.requireNonNull(Objects.requireNonNull(inputNomPiece.getEditText()).getText()).toString();
        Piece p = new Piece(facadeActu, nomPiece);

        GestionnaireNavigation.getInstance().getHabitation().ajouterPiece(p);

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

        if((System.currentTimeMillis() - interval)>16) {
            canvas.setOrientation(values[0]);
            canvas.invalidate();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private String pointCardinalActu(){
        switch(numFacadeActu){
            case 0:
                return "Nord";
            case 1 :
                return "Est";
            case 2 :
                return "Sud";
            case 3 :
                return "Ouest";
        }
        return "Ouest";
    }

}