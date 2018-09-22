package com.uteq.sistemas.ventasexpress.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Pedido {
    private int id;
    private String idCliente;
    private String Negocio;
    private String idEmpleado;
    private String fechaInicio;
    private String fechaEntrega;
    private String estado;
    private String observacion;
    private String latlong;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getNegocio() {
        return Negocio;
    }

    public void setNegocio(String negocio) {
        Negocio = negocio;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getLatlong() { return latlong; }

    public void setLatlong(String latlong) { this.latlong = latlong; }
    public Pedido(JSONObject a) throws JSONException {
        setId(a.getInt("id"));
        setIdCliente(a.getString("id_cliente"));
        setNegocio(a.getString("negocio"));
        setIdEmpleado(a.getString("id_empleado"));
        setFechaInicio(a.getString("fecha_inicio"));
        setFechaEntrega(a.getString("fecha_entrega"));
        setEstado(a.getString("estado"));
        setObservacion(a.getString("observacion"));
        setLatlong(a.getString("latlong"));
    }

    public static ArrayList<Pedido> JsonObjectsBuild(JSONArray datos)
            throws JSONException {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        for (int i = 0; i < datos.length(); i++) {
            pedidos.add(new Pedido(datos.getJSONObject(i)));
        }
        return pedidos;
    }

}