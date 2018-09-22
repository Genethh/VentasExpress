package com.uteq.sistemas.ventasexpress.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Ruta {
        private String id;
        private String idempleado;
        private String dia;
/*    private String estado;
    private String fecha;
    private String sector;*/

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIdempleado() {
            return idempleado;
        }

        public void setIdempleado(String idempleado) {
            this.idempleado = idempleado;
        }

        public String getDia() {
            return dia;
        }

        public void setDia(String dia) {
            this.dia = dia;
        }

    /*public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }*/

        public Ruta() {

        }

        public Ruta(JSONObject a) throws JSONException {
            id = a.getString("id");
            idempleado = a.getString("id_empleado");
            dia = a.getString("dia");
       /* fecha = a.getString("fecha");
        estado = a.getString("estado");
        sector = a.getString("sector");*/
        }

        public static ArrayList<Ruta> JsonObjectsBuild(JSONArray datos)
                throws JSONException {
            ArrayList<Ruta> rutas = new ArrayList<>();
            for (int i = 0; i < datos.length(); i++) {
                rutas.add(new com.uteq.sistemas.ventasexpress.model.Ruta(datos.getJSONObject(i)));
            }
            return rutas;
        }
}