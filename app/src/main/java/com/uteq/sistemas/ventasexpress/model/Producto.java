package com.uteq.sistemas.ventasexpress.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Producto {

    private String codigo;
    private String codigoCategoria;
    private String nombre;
    private String stockMaximo;
    private String stockMinimo;
    private String precioPublico;
    private String observacion;
    private String cantidad;

    public Producto() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(String codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
    }

    public String getStockMaximo() {
        return stockMaximo;
    }

    public void setStockMaximo(String stockMaximo) {
        this.stockMaximo = stockMaximo;
    }

    public String getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(String stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public String getPrecioPublico() {
        return precioPublico;
    }

    public void setPrecioPublico(String precioPublico) {
        this.precioPublico = precioPublico;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }


    public Producto(JSONObject a) throws JSONException {
        codigo = a.getString("codigo");
        nombre = a.getString("nombre");
        codigoCategoria = a.getString("codigo_categoria");
        stockMaximo = a.getString("stock_maximo");
        stockMinimo = a.getString("stock_minimo");
        precioPublico = a.getString("precio_publico");
        observacion = a.getString("observacion");
        cantidad = a.getString("cantidad");
    }

    public static ArrayList<Producto> JsonObjectsBuild(JSONArray datos)
            throws JSONException {
        ArrayList<Producto> productosListAdmin = new ArrayList<>();
        for (int i = 0; i < datos.length(); i++) {
            productosListAdmin.add(new Producto(datos.getJSONObject(i)));
        }
        return productosListAdmin;
    }
}