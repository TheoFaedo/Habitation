package com.example.habitation.models;

import org.json.JSONException;
import org.json.JSONObject;

public interface ConvertibleJSON {
    public JSONObject toJSON() throws JSONException;

}
