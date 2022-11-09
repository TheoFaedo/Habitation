package com.example.habitation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.habitation.models.Facade;
import com.example.habitation.models.GestionnaireNavigation;
import com.example.habitation.models.Piece;
import com.google.android.material.textfield.TextInputLayout;
import org.json.JSONObject;

import java.util.Objects;

public class CreerPieceActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> launcher;
    private Facade[] facadeActu;

    private int numFacadeActu;

    private Button boutonPhoto;
    private TextInputLayout inputNomPiece;
    private TextView textNombrePhoto;

    private TextView textFacePhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_piece);
        numFacadeActu = 0;
        facadeActu = new Facade[4];

        boutonPhoto = findViewById(R.id.prendre_facade_button);
        inputNomPiece = findViewById(R.id.nom_piece_input);
        textNombrePhoto = findViewById(R.id.text_nombre_photo);
        textFacePhoto = findViewById(R.id.text_prendre_photo);

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    //récupération de l'image
                    Bundle extra = result.getData().getExtras();
                    Bitmap image = (Bitmap) extra.get("data");

                    //ajout dans le tableau
                    facadeActu[numFacadeActu] = new Facade(image);
                    numFacadeActu++;
                    String text = numFacadeActu+"/4";
                    textNombrePhoto.setText(text);

                    text = "Prendre la photo de la facade " + pointCardinalActu();
                    textFacePhoto.setText(text);
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

        GestionnaireNavigation.getInstance().getHabitation().ajouterPiece(p);

        finish();
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