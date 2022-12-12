package com.example.habitationapp;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.habitationapp.models.GestionnaireNavigation;
import com.example.habitationapp.models.Habitation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(GestionnaireNavigation.getInstance(this).getHabitation() == null){
            Habitation hab = new Habitation("undefined");
            hab.save(MainActivity.this);
        }
    }

    public void OnEditClick(View v){
        startActivity(new Intent(this, EditActivity.class));
    }

    //Lorsque le bouton visite est cliqué on lance l'activité de visite de l'habitation sauf si celle-ci n'a pas encore de pièces
    public void OnVisitClick(View v){
        if(GestionnaireNavigation.getInstance(this).getHabitation().toString().equals("undefined")){
            Toast.makeText(this, "Aucune habitation n'a encore été créée", Toast.LENGTH_LONG).show();
        }else{
            startActivity(new Intent(this, VisitActivity.class));
        }
    }
}