package com.example.habitation;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.habitation.models.Facade;
import com.example.habitation.models.Habitation;
import com.example.habitation.models.Piece;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Habitation hab = new Habitation();
        Facade[] facades = {
                new Facade("N", new ArrayList<>()),
                new Facade("N", new ArrayList<>()),
                new Facade("N", new ArrayList<>()),
                new Facade("N", new ArrayList<>()),
        };
        Piece p = new Piece(facades, "test");
        hab.ajouterPiece(p);
        hab.setPieceDepart(p);
        try {
            hab.save(MainActivity.this);
            Habitation.load(MainActivity.this);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void OnEditClick(View v){
        startActivity(new Intent(this, EditActivity.class));
    }

    public void OnVisitClick(View v){
        startActivity(new Intent(this, VisitActivity.class));
    }
}