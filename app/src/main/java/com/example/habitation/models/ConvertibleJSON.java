package com.example.habitation.models;

import org.json.JSONException;
import org.json.JSONObject;

public interface ConvertibleJSON<T> {
    public JSONObject toJSON() throws JSONException;

    public T toObject(JSONObject jsonObject) throws JSONException;
}
