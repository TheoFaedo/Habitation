package com.example.habitationapp;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.habitationapp.models.GestionnaireNavigation;
import com.example.habitationapp.views.CanvasPorte;

public class VisitActivity extends AppCompatActivity {

    private GestionnaireNavigation gestionnaireNavigation;
    private ImageView imageActuelle;

    private CanvasPorte canvasPorte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit);

        gestionnaireNavigation = GestionnaireNavigation.getInstance(this);
        gestionnaireNavigation.positionPieceDepart();

        canvasPorte = findViewById(R.id.canvasPorte);

        this.imageActuelle = findViewById(R.id.image_actuelle);
        gestionnaireNavigation.actualiserAffichage(imageActuelle, this);
        actualiserPorte();
    }

    public void OnBoutonGauche(View v){
        gestionnaireNavigation.onGauche(this, imageActuelle);
        actualiserPorte();
    }
    public void OnBoutonDroit(View v){
        gestionnaireNavigation.onDroite(this, imageActuelle);
        actualiserPorte();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestionnaireNavigation.setPieceActuelle(canvasPorte.clicked(event.getX(), event.getY()));
        gestionnaireNavigation.actualiserAffichage(imageActuelle, this);
        actualiserPorte();
        return super.onTouchEvent(event);
    }

    private void actualiserPorte(){
        canvasPorte.setListePieces(gestionnaireNavigation.getFacadeActuelle().getPieceAdjacentes());
    }
}