package com.uteq.sistemas.ventasexpress.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Empleado {

    private int id;
    private String identificacion;
    private String nombres;
    private String apellidos;
    private String usuario;
    private String email;
    private String telefono;
    private String direccion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
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

    public Empleado(JSONObject a) throws JSONException {
        identificacion = a.getString("identificacion");
        nombres = a.getString("nombres");
        apellidos = a.getString("apellidos");
        usuario = a.getString("email");
        email = a.getString("email");
        telefono = a.getString("telefono");
        direccion = a.getString("direccion");
    }

    public static ArrayList<Empleado> JsonObjectsBuild(JSONArray datos)
            throws JSONException {
        ArrayList<Empleado> rutas = new ArrayList<>();
        for (int i = 0; i < datos.length(); i++) {
            rutas.add(new Empleado(datos.getJSONObject(i)));
        }
        return rutas;
    }
}