package com.example.habitation;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.habitation.models.Facade;
import com.example.habitation.models.GestionnaireNavigation;
import com.example.habitation.models.Habitation;
import com.example.habitation.models.Piece;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Habitation hab = GestionnaireNavigation.getInstance(this).getHabitation();

        Toast.makeText(this, hab.getPieceDepart().getNom(), Toast.LENGTH_SHORT).show();
    }

    public void OnEditClick(View v){
        startActivity(new Intent(this, EditActivity.class));
    }

    public void OnVisitClick(View v){
        startActivity(new Intent(this, VisitActivity.class));
    }
}