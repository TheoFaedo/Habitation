package com.example.habitation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.habitation.models.GestionnaireNavigation;
import com.example.habitation.models.Piece;
import com.example.habitation.views.CanvasPorte;

import java.util.Arrays;

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
        gestionnaireNavigation.onGauche(v, this, imageActuelle);
        actualiserPorte();
    }
    public void OnBoutonDroit(View v){
        gestionnaireNavigation.onDroite(v, this, imageActuelle);
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