package com.uteq.sistemas.ventasexpress.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BonificacionTasa {

    private int tasa;


    public int getTasa() {
        return tasa;
    }
    public void setTasa(int tasa) {
        this.tasa = tasa;
    }


    public BonificacionTasa(JSONObject a) throws JSONException {
        tasa = a.getInt("tasa");
    }

    public static ArrayList<BonificacionTasa> JsonObjectsBuild(JSONArray datos)
            throws JSONException {
        ArrayList<BonificacionTasa> bonificacion = new ArrayList<>();
        for (int i = 0; i < datos.length(); i++) {
            bonificacion.add(new BonificacionTasa(datos.getJSONObject(i)));
        }
        return bonificacion;
    }
}