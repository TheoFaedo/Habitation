package com.example.habitation;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OnEditClick(View v){
        startActivity(new Intent(this, EditActivity.class));
    }

    public void OnVisitClick(View v){
        startActivity(new Intent(this, VisitActivity.class));
    }
}