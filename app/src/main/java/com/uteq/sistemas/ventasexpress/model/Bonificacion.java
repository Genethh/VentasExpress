package com.uteq.sistemas.ventasexpress.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Bonificacion {

    private String codigo;
    private String id_empleado;
    private String id_pedido;
    private String total_pedido;
    private String tasa;
    private String bonificacion;
    private String fecha;


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getIdempleado() {
        return id_empleado;
    }

    public void setIdempleado(String idempleado) {
        this.id_empleado = idempleado;
    }

    public String getIdpedido() {
        return id_pedido;
    }

    public void setIdpedido(String idpedido) {
        this.id_pedido = idpedido;
    }

    public String getTotalpedido() {
        return total_pedido;
    }

    public void setTotalpedido(String totalpedido) {
        this.total_pedido = totalpedido;
    }

    public String getTasa() {
        return tasa;
    }

    public void setTasa(String tasa) {
        this.tasa = tasa;
    }

    public String getBonificacion() {
        return bonificacion;
    }

    public void setBonificacion(String bonificacion) {
        this.bonificacion = bonificacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) { this.fecha = fecha; }


    public Bonificacion(JSONObject a) throws JSONException {
        codigo = a.getString("codigo");
        id_empleado = a.getString("id_empleado");
        id_pedido = a.getString("id_pedido");
        total_pedido = a.getString("total_pedido");
        tasa = a.getString("tasa");
        bonificacion = a.getString("bonificacion");
        fecha = a.getString("fecha");
    }

    public static ArrayList<Bonificacion> JsonObjectsBuild(JSONArray datos)
            throws JSONException {
        ArrayList<Bonificacion> bon = new ArrayList<>();
        for (int i = 0; i < datos.length(); i++) {
            bon.add(new Bonificacion(datos.getJSONObject(i)));
        }
        return bon;
    }
}