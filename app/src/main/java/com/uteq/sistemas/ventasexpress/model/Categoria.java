package com.uteq.sistemas.ventasexpress.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Categoria {
    private String codigo;
    private String detalle;

    public String getCodigo() {
        return codigo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Categoria() {
    }

    public Categoria(JSONObject a) throws JSONException {
        codigo = a.getString("codigo");
        detalle = a.getString("detalle");
    }

    public static ArrayList<Categoria> JsonObjectsBuild(JSONArray datos)
            throws JSONException {
        ArrayList<Categoria> categorias = new ArrayList<>();
        for (int i = 0; i < datos.length(); i++) {
            categorias.add(new Categoria(datos.getJSONObject(i)));
        }
        return categorias;
    }
}
