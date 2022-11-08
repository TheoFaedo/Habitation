package com.example.habitation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.habitation.models.Facade;
import com.example.habitation.models.Piece;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class CreerPieceActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> launcher;
    private Facade[] facadeActu;
    private String nomPiece;

    private int numFacadeActu;

    private Button boutonPhoto;
    private TextInputLayout inputNomPiece;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_piece);
        numFacadeActu = 0;
        facadeActu = new Facade[4];

        boutonPhoto = findViewById(R.id.prendre_facade_button);
        inputNomPiece = findViewById(R.id.nom_piece_input);

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    //récupération de l'image
                    Bundle extra = result.getData().getExtras();
                    Bitmap image = (Bitmap) extra.get("data");

                    //ajout dans le tableau
                    facadeActu[numFacadeActu] = new Facade(image);
                    numFacadeActu++;
                }
        );
    }

    public void OnPhotoClick(View v){
        if(numFacadeActu>=3) boutonPhoto.setEnabled(false); //Si on est à la dernière photo, on desactive le bouton

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        launcher.launch(i);
    }

    public void OnConfirmClick(View v){
        String nomPiece = "Piece lambda";
        nomPiece = Objects.requireNonNull(Objects.requireNonNull(inputNomPiece.getEditText()).getText()).toString();
        Piece p = new Piece(facadeActu, nomPiece);
    }
}