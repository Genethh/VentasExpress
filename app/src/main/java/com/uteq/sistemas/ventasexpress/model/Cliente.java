package com.uteq.sistemas.ventasexpress.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Cliente {
    private String identificacion;
    private String idEmpleado;
    private String negocio;
    private String propietario;
    private String email;
    private String telefono;
    private String direccion;
    private String latlong;
    private String estado;

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNegocio() {
        return negocio;
    }

    public void setNegocio(String negocio) {
        this.negocio = negocio;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLatlong() {
        return latlong;
    }

    public void setLatlong(String latlong) {
        this.latlong = latlong;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    public Cliente(JSONObject a) throws JSONException {
        identificacion = a.getString("identificacion");
        idEmpleado = a.getString("id_empleado");
        negocio = a.getString("negocio");
        propietario = a.getString("propietario");
        email = a.getString("email");
        telefono = a.getString("telefono");
        direccion = a.getString("direccion");
        latlong = a.getString("latlong");
        estado = a.getString("estad");
    }

    public static ArrayList<Cliente> JsonObjectsBuild(JSONArray datos)
            throws JSONException {
        ArrayList<Cliente> rutas = new ArrayList<>();
        for (int i = 0; i < datos.length(); i++) {
            rutas.add(new Cliente(datos.getJSONObject(i)));
        }
        return rutas;
    }

}
