package com.example.habitationapp.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class Facade implements ConvertibleJSON{

    private String refImage; //Lorsque qu'on prends la photo, on la stocke avec un certains nom, (<nompiece><orientationFacade>) qui correspond donc à cet attribut
    private List<String> piecesAdjacentes; //Liste des noms de chaques pieces asdjacentes (accessibles via une porte)

    /**
     * Constructeur d'une Facade prenant en parametre la reference de son image
     * @param refImage : référence de l'image
     */
    public Facade(String refImage){
        this.refImage = refImage;
        this.piecesAdjacentes = new ArrayList<>();
    }

    /**
     * Constructeur d'une Facade prenant en parametre la reference de son image et la liste du nom de ses pièces adjacentes
     * @param refImage : référence de l'image
     * @param listeNomPieces : liste du nom des pièces adjacentes à la facade
     */
    public Facade(String refImage, List<String> listeNomPieces){
        this.refImage = refImage;
        this.piecesAdjacentes = listeNomPieces;
    }

    /**
     * Permet de récupérer le Bitmap correspondant à l'image de la Facade
     * @param context
     * @return un Bitmap de l'image correspondant à la Facade
     */
    public Bitmap getBitmap(Context context){
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(this.refImage+".data"); //On récupère l'image qui à été enregistrée
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return BitmapFactory.decodeStream(fis);
    }

    /**
     * Méthode permettant de convertir une instance de Facade au format JSON
     * @return cette instance de facade au format JSON
     * @throws JSONException
     */
    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        JSONArray jsonPieces = new JSONArray();

        for(String nom : piecesAdjacentes){
            jsonPieces.put(nom);
        }

        json.put("image", refImage);
        json.put("pieces", jsonPieces);

        return json;
    }

    /**
     * Méthode permettant de rendre u e instance de facade à partir d'une facade au format JSON
     * @param jsonObject : JSON d'une Facade
     * @return une instance de Facade correspondant au JSON fournis
     * @throws JSONException
     */
    public static Facade jsonToFacade(JSONObject jsonObject) throws JSONException {
        String nomimage = jsonObject.getString("image");
        JSONArray jsonPieces = jsonObject.getJSONArray("pieces");

        List<String> liste = new ArrayList<>();
        for(int i = 0; i<jsonPieces.length(); i++)  liste.add((String) jsonPieces.get(i));

        return new Facade(nomimage, liste);
    }

    /*******************************************************
     *
     *                  GETTERS
     *
     ******************************************************/

    public List<String> getPieceAdjacentes(){
        return piecesAdjacentes;
    }

    public String getRefImage(){
        return this.refImage;
    }
}
