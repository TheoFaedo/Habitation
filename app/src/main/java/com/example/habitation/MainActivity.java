package com.example.habitation;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.habitation.models.Habitation;
import com.example.habitation.models.Piece;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Habitation hab = new Habitation();
        hab.ajouterPiece(new Piece());
        hab.save(MainActivity.this);
        Habitation.load(MainActivity.this);
    }

    public void OnEditClick(View v){
        startActivity(new Intent(this, EditActivity.class));
    }

    public void OnVisitClick(View v){
        startActivity(new Intent(this, VisitActivity.class));
    }
}