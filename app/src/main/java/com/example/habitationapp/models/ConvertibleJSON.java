package com.example.habitationapp.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Les classes implémentant cette interface sont des classes dont leurs instances sont convertibles en json via la méthode toJSON()
 */
public interface ConvertibleJSON {
    JSONObject toJSON() throws JSONException;

}
