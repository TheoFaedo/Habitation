package com.example.habitation;

import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.habitation.models.GestionnaireNavigation;
import com.example.habitation.models.Habitation;

public class VisitActivity extends AppCompatActivity {

    private GestionnaireNavigation gestionnaireNavigation;
    private ImageView imageActuelle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit);

        gestionnaireNavigation = GestionnaireNavigation.getInstance(this);
        gestionnaireNavigation.positionPieceDepart();

        this.imageActuelle = findViewById(R.id.image_actuelle);
        gestionnaireNavigation.actualiserAffichage(imageActuelle, this);
    }

    public void OnBoutonGauche(View v){
        gestionnaireNavigation.onGauche(v, this, imageActuelle);
    }
    public void OnBoutonDroit(View v){
        gestionnaireNavigation.onDroite(v, this, imageActuelle);
    }



}